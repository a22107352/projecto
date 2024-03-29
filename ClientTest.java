import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class ClientTest {
    public static void main(String[] args) {
        try {
            Scanner myObj = new Scanner(System.in);
            String temConta, email = "", password = "", passwordRepetida = "", opcao;
            int idUser = 0;
            boolean existeEmail = false;

            final String pathUserPass = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
            final String pathReservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";

            File fileUserPass = new File(pathUserPass);
            File fileReservas = new File(pathReservas);


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
                System.out.println("""

                        Escolha uma opção:
                        R - Reservar uma sombrinha
                        L - Listar sombrinhas não reservadas
                        C - Cancelar uma sombrinha reservada
                        S - Sair""");
                opcao = myObj.nextLine();


                switch (opcao) {//praia,id sombrinha,hora,n pessoas,iduser reservou, reservado
                    case "R": {
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
                            System.out.println("Escolha a hora de ínicio: (8h - 19h)");
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
                            System.out.println("Escolha o número de pessoas: (1-4)");
                            num = myObj.nextLine();

                            if (!num.matches("\\d+")) {
                                System.out.println("Introduza um número válido");
                            } else {
                                num_pessoas = Integer.parseInt(num);

                                if (num_pessoas < 1 || num_pessoas > 4) {
                                    System.out.println("Por favor, introduza um numero válido");
                                }

                            }
                        } while (num_pessoas < 1 || num_pessoas > 4);

                        int reserva = 0;

                        try (BufferedReader leitor = new BufferedReader(new FileReader(pathReservas))) {//praia,id sombrinha,hora,n pessoas,iduser reservou, reservado
                            List<String> linhas = new ArrayList<>();

                            String linha;
                            while ((linha = leitor.readLine()) != null) {
                                String[] praias = linha.split(",");

                                if (praias[0].equals(letra) && praias[2].equals(hora) && num_pessoas <= Integer.parseInt(praias[3]) && praias[5].equals("0") && reserva == 0) {
                                    praias[praias.length - 1] = "1";
                                    praias[praias.length - 2] = String.valueOf(idUser);
                                    reserva = 1;
                                }
                                linhas.add(String.join(",", praias));
                            }

                            if (reserva == 1) {
                                System.out.println("Reserva feito com sucesso.");
                            } else {
                                System.out.println("Praia nao disponivel");
                            }

                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileReservas))) {
                                for (String linhaAtualizada : linhas) {
                                    bw.write(linhaAtualizada);
                                    bw.newLine();
                                }
                            }
                        }

                        break;
                    }
                    case "L": {
                        String linha;

                        String letra, hora;
                        int hora_inicio = 0;

                        do {
                            System.out.println("Escolha a praia que têm reserva: (A, B ou C)");
                            letra = myObj.nextLine();

                            if (!letra.equals("A") && !letra.equals("B") && !letra.equals("C")) {
                                System.out.println("Por favor, introduza uma praia válida");
                            }
                        } while (!letra.equals("A") && !letra.equals("B") && !letra.equals("C"));

                        do {
                            System.out.println("Escolha a hora de ínicio: (8h - 19h)");
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

                        try (BufferedReader leitorReservas = new BufferedReader(new FileReader(pathReservas))) {

                            boolean ocupado = false;

                            while ((linha = leitorReservas.readLine()) != null) {
                                String[] reservas = linha.split(",");
                                if (reservas[5].equals(Integer.toString(1)) && reservas[0].equals(letra) && String.valueOf(reservas[2]).equals(hora)) {
                                    ocupado = true;
                                }
                            }

                            if (ocupado) {
                                System.out.println("Sombrinha ocupada");
                            } else {
                                System.out.println("Sombrinha disponivel");
                            }
                        }
                    }

                    break;
                    case "C": {

                        String letra, hora;
                        int hora_inicio = 0;

                        do {
                            System.out.println("Escolha a praia que têm reserva: (A, B ou C)");
                            letra = myObj.nextLine();

                            if (!letra.equals("A") && !letra.equals("B") && !letra.equals("C")) {
                                System.out.println("Por favor, introduza uma praia válida");
                            }
                        } while (!letra.equals("A") && !letra.equals("B") && !letra.equals("C"));

                        do {
                            System.out.println("Escolha a hora de ínicio: (8h - 19h)");
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

                        try (BufferedReader leitor = new BufferedReader(new FileReader(fileReservas))) {

                            String linha;
                            int linhaAtual = 0;
                            boolean reserva = false;
                            StringBuilder conteudo = new StringBuilder();

                            while ((linha = leitor.readLine()) != null) {

                                String[] praias = linha.split(",");

                                if (letra.equals(praias[0]) && praias[4].equals(Integer.toString(idUser)) && praias[2].equals(Integer.toString(hora_inicio))) {

                                    reserva = true;

                                    linha = "\n" + praias[0] + "," + praias[1] + "," + praias[2] + "," + praias[3] + ",0,0";

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
                                System.out.println("Reserva não encontrada");
                            } else {

                                try (PrintWriter writer = new PrintWriter(fileReservas)) {
                                    writer.write(conteudo.toString());
                                } catch (FileNotFoundException e) {
                                    System.err.println("Erro: " + e.getMessage());
                                }

                                System.out.println("Reserva cancelada com sucesso");
                            }
                        }
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

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }
}