import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class AddServerImpl extends UnicastRemoteObject
        implements AddServerIntf {

    public AddServerImpl() throws RemoteException {
    }

    public Object[] registo(String email, String password, String passwordRepetida, boolean existeEmail, int idUser) throws RemoteException {

        Object[] returnObj = new Object[3];
        returnObj[0] = (false);

        final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
        File fileUserPass = new File(pathUserPass);

        try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                if(!linha.isEmpty()){
                    String[] userPass = linha.split(",");
                    if (userPass[1].equals(email)) {

                        existeEmail = true;

                    }
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
                returnObj[2] = ++id;

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

    public Object[] login(String email, String password) throws RemoteException {

        final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
        final String pathReservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";
        boolean logado = false;

        Object[] returnObj = new Object[3];


        try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                if(!linha.isEmpty()){
                    String[] userPass = linha.split(",");
                    if (userPass[1].equals(email)) {

                        if (userPass[2].equals(password)) {
                            returnObj[2] = Integer.parseInt(userPass[0]);
                            logado = true;
                        }

                    }
                }


            }


            if (logado) {
                returnObj[0] = (true);
                returnObj[1] = ("Login feito com sucesso!");
            } else {
                returnObj[0] = (false);
                returnObj[1] = ("Credencias erradas!");
                returnObj[2] = 1;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnObj;
    }


    public Object[] valida_escolha(String letra)throws RemoteException {

        Object[] returnObj = new Object[2];

        if (!letra.equals("A") && !letra.equals("B") && !letra.equals("C")) {

            returnObj[0] = false;
            returnObj[1] = "Por favor, introduza uma praia válida\n";

        } else {
            returnObj[0] = true;
            returnObj[1] = "";
        }

        return returnObj;

    }


    public Object[] valida_hora(String hora)throws RemoteException {

        Object[] returnObj_hora = new Object[2];
        int hora_inicio;

        if (!hora.matches("\\d+")) {
            returnObj_hora[0] = false;
            returnObj_hora[1] = "Introduza uma hora válida\n";
        } else {
            hora_inicio = Integer.parseInt(hora);

            if (hora_inicio < 8 || hora_inicio > 20) {
                returnObj_hora[0] = false;
                returnObj_hora[1] = "Introduza uma hora válida\n";
            } else {
                returnObj_hora[0] = true;
                returnObj_hora[1] = "";
            }
        }
        return returnObj_hora;
    }


    public Object[] valida_num(String num) throws RemoteException {

        Object[] returnObj_num = new Object[2];
        int num_pessoas;

        if (!num.matches("\\d+")) {
            returnObj_num[0] = false;
            returnObj_num[1] = "Introduza um número válido\n";
        } else {
            num_pessoas = Integer.parseInt(num);

            if (num_pessoas < 1 || num_pessoas > 4) {
                returnObj_num[0] = false;
                returnObj_num[1] = "Introduza um número válido\n";
            } else {
                returnObj_num[0] = true;
                returnObj_num[1] = "";
            }
        }
        return returnObj_num;
    }
    
    /*public String faz_reserva (Str){
    
        
            if(num < 3){
                            try (BufferedReader leitor = new BufferedReader(new FileReader(pathReservas))) {

                            String linha;

                            while ((linha = leitor.readLine()) != null) {

                                String[] userPass = linha.split(",");
                                if (userPass[3].equals(letra)) {                                    

                                        if(userPass[0] == 0) {
                                            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fileUserPass, true))) {
                                            
                                                            int id = Integer.parseInt(ultimoID);
                                                            returnObj[2] = ++id;
                                            
                                                            escritor.write(id + "," + email + "," + password);
                                                            escritor.newLine();
                                                        } catch (IOException e) {
                                                            throw new RuntimeException(e);
            }
                                                                                        
                                        }else{
                                            return "Sombra ocupada";
                                        }
                                }else{
                                    //faz reserva
                                }

                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
            }else if(num == 3){

            }else{

            }
    }*/

    public Object[] func_C(String letra, int idUser) throws RemoteException {

        final String pathReservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";
        File fileReservas = new File(pathReservas);

        Object[] returnObj = new Object[2];

        try (BufferedReader leitor = new BufferedReader(new FileReader(fileReservas))) {

            String linha;
            int linhaAtual = 0;
            boolean reserva = false;
            StringBuilder conteudo = new StringBuilder();

            while ((linha = leitor.readLine()) != null) {

                String[] praias = linha.split(",");

                if (letra.equals(praias[3]) && praias[1].equals(Integer.toString(idUser)) && Integer.parseInt(praias[0]) == 1) {

                    reserva = true;

                    linha = "";

                    linhaAtual--;
                } else {
                    if (linhaAtual != 0) {
                        conteudo.append("\n");
                    }

                }
                conteudo.append(linha);
                linhaAtual++;
            }

            if (!reserva) {
                returnObj[0] = (false);
                returnObj[1] = ("Reserva não encontrada");
            } else {

                try (PrintWriter writer = new PrintWriter(fileReservas)) {
                    writer.write(conteudo.toString());
                } catch (FileNotFoundException ignored) {
                }
                returnObj[0] = (true);
                returnObj[1] = ("Reserva cancelada com sucesso");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnObj;
    }
}
