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
      /*
      String addServerURL = "rmi://" + args[0] + "/AddServer";
      AddServerIntf addServerIntf =
                    (AddServerIntf)Naming.lookup(addServerURL);
      */
            Scanner myObj = new Scanner(System.in);
            String temConta, email = "", password = "", passwordRepetida = "", opcao;
            int idUser = 0;
            boolean existeEmail = false;

            final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
            final String pathsombrinhas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\SombrinhasDisp.txt";
            final String reservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";

            File fileUserPass = new File(pathUserPass);
            File fileSombrinhas = new File(pathsombrinhas);


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

            do {
                System.out.println("\n" + "Escolha uma opção:" + "\n" + "R - Reservar uma sombrinha" + "\n" + "L - Listar sombrinhas não reservadas" + "\n" + "C - Cancelar uma sombrinha reservada" + "\n" + "S - Sair");
                opcao = myObj.nextLine();

                if (opcao.equals("R")) {
                    int y = 1;
                    String letra, hora, num;
                    int hora_inicio = 0, num_pessoas = 0;


                    do {
                        System.out.println("Escolha a praia: (A, B ou C)");
                        letra = myObj.nextLine();

                        if (!letra.equals("A") && !letra.equals("B") && !letra.equals("C")) {
                            System.out.println("Por favor, intrduza uma praia válida");
                        }
                    } while (!letra.equals("A") && !letra.equals("B") && !letra.equals("C"));


                    do {
                        System.out.println("Escolha a hora de ínicio(8h - 20h)");
                        hora = myObj.nextLine();


                        if (!hora.matches("\\d+")) {
                            System.out.println("Introduza uma hora válida");
                        } else {
                            hora_inicio = Integer.parseInt(hora);

                            if (hora_inicio < 8 || hora_inicio > 20) {
                                System.out.println("Por favor, introduza uma hora válida");
                            }
                        }

                    } while (hora_inicio < 8 || hora_inicio > 20);

                    do {
                        System.out.println("Escolha o número de pessoas (1-4)");
                        num = myObj.nextLine();

                        if (!num.matches("\\d+")) {
                            System.out.println("Introduza um número válido");
                        } else {
                            num_pessoas = Integer.parseInt(num);

                            if (num_pessoas < 8 || num_pessoas > 20) {
                                System.out.println("Por favor, introduza um numero válido");
                            }

                        }
                    } while (num_pessoas < 1 || num_pessoas > 4);


                    if (num_pessoas <= 2) {

                    } else if (num_pessoas == 3) {

                    } else if (num_pessoas == 4) {

                    }


                } else if (opcao.equals("L")) { //atrasado mental, lê o enunciado
                    try (BufferedReader leitor = new BufferedReader(new FileReader(pathsombrinhas))) {
                        String linha;
                        while ((linha = leitor.readLine()) != null) {
                            String[] praias = linha.split(",");
                            if (praias[2].equals("0")) {
                                System.out.println(linha);
                            }
                        }
                    }
                } else if (opcao.equals("C")) { //ID,Hora,Praia,sombrinha numero

                    String letra;

                    do {
                        System.out.println("Escolha a praia que têm reserva: (A, B ou C)");
                        letra = myObj.nextLine();

                        if (!letra.equals("A") && !letra.equals("B") && !letra.equals("C")) {
                            System.out.println("Por favor, intrduza uma praia válida");
                        }
                    } while (!letra.equals("A") && !letra.equals("B") && !letra.equals("C"));

                    try (BufferedReader leitor = new BufferedReader(new FileReader(reservas))) {

                        String linha;
                        int linhaAtual = 0;
                        boolean reserva = false;

                        while ((linha = leitor.readLine()) != null) {

                            String[] praias = linha.split(",");

                            if (praias[0].equals(Integer.toString(idUser))) {

                                reserva = true;

                                try (BufferedWriter escritor = new BufferedWriter(new FileWriter(reservas, true))) {
                                    //falta apagar as coisas do ficheiro
                                }

                            }
                            linhaAtual++;
                        }

                        if(!reserva){
                            System.out.println("Reserva não encontrada");
                        }
                    }


                } else if (opcao.equals("S")) {
                    System.out.println("Obrigado! Até a proxima!");
                    break;

                } else {
                    System.out.println("Opção incorreta, escolha uma opção válida");
                }

            } while (true);


            // System.out.println("The sum is: " + addServerIntf.add(1, 2));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}