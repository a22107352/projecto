import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class AddClient {
    public static void main(String[] args) {
        try {
            //127.0.0.1 ip windows
            //192.168.56.101 linux windows
            String addServerURL = "rmi://" + "127.0.0.1" + "/AddServer";
            AddServerIntf addServerIntf =
                    (AddServerIntf) Naming.lookup(addServerURL);

            Scanner myObj = new Scanner(System.in);
            String temConta, email = "", password = "", passwordRepetida = "", opcao;
            int idUser = 0;
            boolean existeEmail = false;

            final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";

            File fileUserPass = new File(pathUserPass);

            System.out.println("Bem vindo!");

            do {
                System.out.println("\n" + "Já tem conta? (s/n)");

                temConta = myObj.nextLine();

                if (temConta.equals("n")) {

                    System.out.println("Faça o registo.");


                    do {

                        existeEmail = false;

                        System.out.println("Insira o seu email:");
                        email = myObj.nextLine();

                        if (email.equals(",")) {
                            break;
                        }

                        System.out.println("Insira a sua password:");
                        password = myObj.nextLine();

                        System.out.println("Repita a sua password:");
                        passwordRepetida = myObj.nextLine();


                        try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

                            String linha;

                            while ((linha = leitor.readLine()) != null) {

                                String[] userPass = linha.split(",");
                                if (userPass[1].equals(email)) {

                                    existeEmail = true;

                                }

                            }

                        }

                        if (email.contains(",")) {

                            System.out.println("Email não pode conter uma vígula.");

                        } else if (existeEmail) {

                            System.out.println("Esse email já está registado, por favor faça o login" + "\n" + "Para fazer login, basta escrever ','");

                        } else if (password.contains(",")) {

                            System.out.println("Password não pode conter uma vígula.");

                        } else if (!password.equals(passwordRepetida)) {

                            System.out.println("Passwords não são iguais.");

                        } else {
                            String ultimoID = "";

                            try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

                                String linha;

                                while ((linha = leitor.readLine()) != null) {

                                    String[] userPass = linha.split(",");
                                    ultimoID = userPass[0];

                                }
                            }

                            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fileUserPass, true))) {

                                int id = Integer.parseInt(ultimoID);
                                idUser = ++id;

                                escritor.write(id + "," + email + "," + password);
                                escritor.newLine();
                            }

                            System.out.println("Registo feito com sucesso!");
                        }

                    } while (!password.equals(passwordRepetida) || email.contains(",") || password.contains(",") || existeEmail);


                }

                if (temConta.equals("s") || email.equals(",")) {
                    boolean logado = false;

                    do {
                        System.out.println("Faça o login!");

                        System.out.println("Insira o seu email:");
                        email = myObj.nextLine();

                        System.out.println("Insira a sua password:");
                        password = myObj.nextLine();

                        try (BufferedReader leitor = new BufferedReader(new FileReader(pathUserPass))) {

                            String linha;

                            while ((linha = leitor.readLine()) != null) {

                                String[] userPass = linha.split(",");
                                if (userPass[1].equals(email)) {

                                    if (userPass[2].equals(password)) {
                                        idUser = Integer.parseInt(userPass[0]);
                                        logado = true;
                                    }

                                }

                            }

                            if (logado) {
                                System.out.println("Login feito com sucesso!");
                            } else {
                                System.out.println("Credencias erradas!");
                            }

                        }
                    } while (!logado);


                }

                if (!temConta.equals("n") && !temConta.equals("s")) {

                    System.out.println("Valores incorretos, por favor introduza outra vez");

                }

            } while (!temConta.equals("n") && !temConta.equals("s"));

            label:
            do {
                System.out.println("\n" + "Escolha uma opção:" + "\n" + "R - Reservar uma sombrinha" + "\n" + "L - Listar sombrinhas não reservadas" + "\n" + "C - Cancelar uma sombrinha reservada" + "\n" + "S - Sair");
                opcao = myObj.nextLine();

                switch (opcao) { //praia,id sombrinha,hora,n pessoas,iduser reservou, reservado
                    case "R": {

                        String hora, num, letra;

                        int num_pessoas = 0;

                        Object[] returnObj_praia, returnObj_hora, returnObj_num, returnObj_final;

                        do {
                            System.out.println("Escolha a praia: (A, B ou C)");
                            letra = myObj.nextLine();

                            returnObj_praia = addServerIntf.valida_praia(letra);
                            System.out.print(returnObj_praia[1]);

                        } while (returnObj_praia[0].equals(false));

                        do {

                            System.out.println("Escolha a hora de ínicio(8h - 19h)");
                            hora = myObj.nextLine();

                            returnObj_hora = addServerIntf.valida_hora(hora);
                            System.out.print(returnObj_hora[1]);

                        } while (returnObj_hora[0].equals(false));

                        do {

                            System.out.println("Escolha o número de pessoas (1-4)");
                            num = myObj.nextLine();

                            returnObj_num = addServerIntf.valida_num(num);
                            System.out.print(returnObj_num[1]);

                        } while (returnObj_num[0].equals(false));

                        returnObj_final = addServerIntf.funcReservar(letra, idUser, hora, num_pessoas);

                        System.out.println(returnObj_final[1]);

                        break;
                    }
                    case "L": { //praia,id sombrinha,hora,n pessoas,iduser reservou, reservado

                        String hora, letra;

                        Object[] returnObj_praia, returnObj_hora, returnObj_final;

                        do {
                            System.out.println("Escolha a praia: (A, B ou C)");
                            letra = myObj.nextLine();

                            returnObj_praia = addServerIntf.valida_praia(letra);
                            System.out.print(returnObj_praia[1]);

                        } while (returnObj_praia[0].equals(false));

                        do {

                            System.out.println("Escolha a hora de ínicio(8h - 19h)");
                            hora = myObj.nextLine();

                            returnObj_hora = addServerIntf.valida_hora(hora);
                            System.out.print(returnObj_hora[1]);

                        } while (returnObj_hora[0].equals(false));

                        returnObj_final = addServerIntf.funcListar(letra, idUser, hora);

                        System.out.println(returnObj_final[1]);

                    }
                    break;
                    case "C": { //praia,id sombrinha,hora,n pessoas,iduser reservou, reservado

                        String letra;

                        Object[] returnObj_praia, returnObj_final;

                        do {
                            System.out.println("Escolha a praia: (A, B ou C)");
                            letra = myObj.nextLine();

                            returnObj_praia = addServerIntf.valida_praia(letra);
                            System.out.print(returnObj_praia[1]);

                        } while (returnObj_praia[0].equals(false));

                        returnObj_final = addServerIntf.funcCancelar(letra, idUser);

                        System.out.println(returnObj_final[1]);

                        break;
                    }
                    case "S":
                        System.out.println("Obrigado! Até a proxima!");
                        break label;

                    default:
                        System.out.println("Opção incorreta, escolha uma opção válida");
                        break;
                }

            } while (true);


            // System.out.println("The sum is: " + addServerIntf.add(1, 2));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}