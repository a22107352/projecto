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

            String addServerURL = "rmi://" + "127.0.0.1" + "/AddServer";
            AddServerIntf addServerIntf =
                    (AddServerIntf) Naming.lookup(addServerURL);

            Scanner myObj = new Scanner(System.in);
            String temConta, email = "", password = "", passwordRepetida = "", opcao;
            int idUser = 0;
            boolean existeEmail = false;

            final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
            final String pathsombrinhas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\SombrinhasDisp.txt";
            final String pathReservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";

            File fileUserPass = new File(pathUserPass);
            File fileSombrinhas = new File(pathsombrinhas);
            File fileReservas = new File(pathReservas);

            Object[] server;

            System.out.println("Bem vindo!");

            do {
                System.out.println("\n" + "Já tem conta? (s/n)");

                temConta = myObj.nextLine();

                if (temConta.equals("n")) {

                    System.out.println("Faça o registo.");

                    do {

                        System.out.println("Insira o seu email:");
                        email = myObj.nextLine();

                        if (email.equals(",")) {
                            break;
                        }

                        System.out.println("Insira a sua password:");
                        password = myObj.nextLine();

                        System.out.println("Repita a sua password:");
                        passwordRepetida = myObj.nextLine();

                        server = addServerIntf.registo(email, password, passwordRepetida, existeEmail, idUser);
                        idUser = (int) server[2];
                        System.out.println(server[1]);

                    } while (!((boolean) server[0]));


                }

                if (temConta.equals("s") || email.equals(",")) {


                    do {
                        System.out.println("Faça o login!");

                        System.out.println("Insira o seu email:");
                        email = myObj.nextLine();

                        System.out.println("Insira a sua password:");
                        password = myObj.nextLine();

                        server = addServerIntf.login(email, password);
                        idUser = (int) server[2];
                        System.out.println(server[1]);

                    } while (!((boolean) server[0]));


                }

                if (!temConta.equals("n") && !temConta.equals("s")) {

                    System.out.println("Valores incorretos, por favor introduza outra vez");

                }

            } while (!temConta.equals("n") && !temConta.equals("s"));

            label:
            do {
                System.out.println("\n" + "Escolha uma opção:" + "\n" + "R - Reservar uma sombrinha" + "\n" + "L - Listar sombrinhas não reservadas" + "\n" + "C - Cancelar uma sombrinha reservada" + "\n" + "S - Sair");
                opcao = myObj.nextLine();

                switch (opcao) {
                    case "R": {
                        int y = 1;
                        String hora, num, letra;

                        int hora_inicio = 0, num_pessoas = 0;

                        Object[] returnObj_praia, returnObj_hora, returnObj_num;
                        do {
                            System.out.println("Escolha a praia: (A, B ou C)");
                            letra = myObj.nextLine();

                            returnObj_praia = addServerIntf.valida_escolha(letra);
                            System.out.print(returnObj_praia[1]);

                        } while (returnObj_praia[0].equals(false));


                        do {

                            System.out.println("Escolha a hora de ínicio(8h - 20h)");
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
                        System.out.println("correto");

                        //faz_reserva(letra,num,hora);


                        break;
                    }
                    case "L":
                        try (BufferedReader leitor = new BufferedReader(new FileReader(pathsombrinhas))) {
                            String linha;
                            String praia;
                            do{
                                System.out.println("Escolha a praia: (A, B ou C)");
                                praia = myObj.nextLine();

                            }while(praia!="C"||praia!="B"||praia!="A");

                            while ((linha = leitor.readLine()) != null) {
                                String[] praias = linha.split(",");
                                if (praias[0].equals(praia)&& praias[2].equals("0")) {

                                    System.out.println(linha);
                                }
                            }
                        }
                        break;
                    case "C": { //esta a acontecer,ID,Hora,Praia,sombrinha numero

                        String letra;

                        Object[] returnObj_praia, returnObj_final;

                        do {
                            System.out.println("Escolha a praia: (A, B ou C)");
                            letra = myObj.nextLine();

                            returnObj_praia = addServerIntf.valida_escolha(letra);
                            System.out.print(returnObj_praia[1]);

                        } while (returnObj_praia[0].equals(false));

                        returnObj_final = addServerIntf.func_C(letra, idUser);

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