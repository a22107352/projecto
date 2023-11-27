import java.io.*;
import java.rmi.*;
import java.util.Objects;
import java.util.Scanner;

public class AddClient {
    public static void main(String[] args) {
        try {
      /*
      String addServerURL = "rmi://" + args[0] + "/AddServer";
      AddServerIntf addServerIntf =
                    (AddServerIntf)Naming.lookup(addServerURL);
      */
            Scanner myObj = new Scanner(System.in);
            String temConta, email, password, passwordRepetida, opcao;

            final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
            final String pathsombrinhas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\SombrinhasDisp.txt";

            File fileUserPass = new File(pathUserPass);
            File fileSombrinhas = new File(pathsombrinhas);
            BufferedWriter bf;


            System.out.println("Bem vindo!\n" + "\n" + "Já tem conta? (s/n)");

            do {

                temConta = myObj.nextLine();

                if (temConta.equals("n")) {
                    System.out.println("Por favor, faça o registo.");

                    do {

                        System.out.println("Insira o seu email:");
                        email = myObj.nextLine();

                        System.out.println("Insira a sua password:");
                        password = myObj.nextLine();

                        System.out.println("Repita a sua password:");
                        passwordRepetida = myObj.nextLine();

                        if(email.contains(",")){

                            System.out.println("Email não pode conter uma vígula.");

                        }else if(password.contains(",")){

                            System.out.println("Password não pode conter uma vígula.");

                        }else if (!password.equals(passwordRepetida)) {

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
                                id++;

                                escritor.write(id + "," +email + "," + password);
                                escritor.newLine();
                            }
                        }

                    } while (!password.equals(passwordRepetida) || email.contains(",") || password.contains(","));


                } else if (temConta.equals("s")) {
                    boolean logado = false;

                    do {
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
                                        logado = true;
                                    }

                                }

                            }


                            if(logado){
                                System.out.println("Login feito com sucesso!");
                            }else{
                                System.out.println("Credencias erradas!");
                            }

                        }
                    } while (!logado);


                } else {

                    System.out.println("Valores incorretos, por favor introduza outra vez");

                }

            } while (!temConta.equals("n") && !temConta.equals("s"));

            do {
                System.out.println("\n" + "Escolha uma opção:" + "\n" + "R - Reservar uma sombrinha" + "\n" + "L - Listar sombrinhas não reservadas" + "\n" + "C - Cancelar uma sombrinha reservada");
                opcao = myObj.nextLine();

                if (opcao.equals("R")) {
                    int y = 1;
                    String letra, hora, num;
                    int hora_inicio, num_pessoas;

                    do {
                        System.out.println("Escolha a praia:");
                        letra = myObj.nextLine();

                    } while (!letra.equals("A") && !letra.equals("B") && !letra.equals("C"));


                    do{
                        System.out.println("Escolha a hora de ínicio(8h - 20h)");
                        hora = myObj.nextLine();
                        hora_inicio = Integer.parseInt(hora);

                    }while(hora_inicio < 8 && hora_inicio > 20 );

                    do{
                        System.out.println("Escolha o número de pessoas (1-4)");
                        num = myObj.nextLine();
                        num_pessoas = Integer.parseInt(num);

                    }while(num_pessoas < 1 && num_pessoas > 4);

                    if(num_pessoas >= 1 && num_pessoas <= 2){

                    }else if(num_pessoas == 3){

                    }else if(num_pessoas == 4){

                    }






                } else if (opcao.equals("L")) {
                    try (BufferedReader leitor = new BufferedReader(new FileReader(pathsombrinhas))) {
                        String linha;
                        while ((linha = leitor.readLine()) != null) {
                            String[] praias = linha.split(",");
                            if (praias[2].equals("N")) {
                                System.out.println(linha);
                            }
                        }
                    }
                } else if (opcao.equals("C")) {
                    String letra;
                    do{

                        System.out.println("Escolha a praia (A,B,C)");
                        letra = myObj.nextLine();

                    }while(letra != "A" && letra != "B" && letra != "C");

                } else {
                    System.out.println("Escola uma opção correta");
                }
            } while (!opcao.equals("R") || !opcao.equals("L") || !opcao.equals("C"));


            // System.out.println("The sum is: " + addServerIntf.add(1, 2));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}