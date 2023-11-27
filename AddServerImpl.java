import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class AddServerImpl extends UnicastRemoteObject
        implements AddServerIntf {

    public AddServerImpl() throws RemoteException {
    }

    public double add(double d1, double d2) throws RemoteException {

        return d1 + d2;
    }


    public boolean registo(String email, String password, String passwordRepetida) throws RemoteException {

        final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
        File fileUserPass = new File(pathUserPass);

        boolean devolve = true;

        try {
            if (!password.equals(passwordRepetida)) {
                devolve = false;
            } else {
                try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fileUserPass, true))) {
                    escritor.write(email + "," + password);
                    escritor.newLine();
                }
            }

        } catch (IOException ignored) {
        }
        return devolve;
    }

    public boolean login(String email, String password) throws RemoteException {

        final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";

        boolean logado = false;
        boolean devolve = true;

        try {
            try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

                String linha;

                while ((linha = leitor.readLine()) != null) {

                    String[] userPass = linha.split(",");
                    if (userPass[0].equals(email)) {

                        if (userPass[1].equals(password)) {
                            logado = true;
                        }

                    }

                }

                if (!logado) {
                    devolve = false;
                }

            }
        } catch (IOException ignored) {
        }

        return devolve;
    }


}
