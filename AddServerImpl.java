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

    public Object[] registo(String email, String password, String passwordRepetida, boolean existeEmail, int idUser) throws RemoteException, Error {

        Object[] returnObj = new Object[2];
        returnObj[0] = (false);

        final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
        File fileUserPass = new File(pathUserPass);

        try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] userPass = linha.split(",");
                if (userPass[1].equals(email)) {

                    existeEmail = true;

                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (email.contains(",")) {

            returnObj[1] = "Email não pode conter uma vígula.";

        } else if (existeEmail) {
            returnObj[1] = ("Esse email já está registado, por favor faça o login" + "\n" + "Para fazer login, basta escrever ','");


        } else if (password.contains(",")) {

            returnObj[1] = ("Password não pode conter uma vígula.");

        } else if (!password.equals(passwordRepetida)) {

            returnObj[1] = ("Passwords não são iguais.");

        } else {
            String ultimoID = "";

            try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

                String linha;

                while ((linha = leitor.readLine()) != null) {

                    String[] userPass = linha.split(",");
                    ultimoID = userPass[0];

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fileUserPass, true))) {

                int id = Integer.parseInt(ultimoID);
                idUser = ++id;

                escritor.write(id + "," + email + "," + password);
                escritor.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            returnObj[0] = (true);
            returnObj[1] = ("Registo feito com sucesso!");

        }
        return returnObj;
    }



}
