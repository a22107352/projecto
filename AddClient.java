import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
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
      String temConta, email, password, passwordRepetida;

      final String outputFilePath = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\UsernamePass.txt";
      File file = new File(outputFilePath);
      BufferedWriter bf;


      System.out.println("Bem vindo!\n" + "\n" + "Já tem conta? (s/n)");

      do{

        temConta = myObj.nextLine();

        if(temConta.equals("n")){
          System.out.println("Por favor, faça o registo");

          do{

            System.out.println("\n"+"Insira o seu email:");
            email = myObj.nextLine();

            System.out.println("\n"+"Insira a sua password:");
            password = myObj.nextLine();

            System.out.println("\n"+"Repita a sua password:");
            passwordRepetida = myObj.nextLine();

            if(!password.equals(passwordRepetida)){
              System.out.println("\n"+"Passwords incorretas");

            }else{
              BufferedWriter escritor = new BufferedWriter(new FileWriter(file));
              escritor.write(email + "," + password);
            }

          }while (!password.equals(passwordRepetida));



        } else if (temConta.equals("s")) {

          System.out.println("\n"+"Insira o seu email:");
          email = myObj.nextLine();

          System.out.println("\n"+"Insira a sua password:");
          password = myObj.nextLine();



        }else{

          System.out.println("Valores incorretos, por favor introduza outra vez");

        }

      }while(!temConta.equals("n") && !temConta.equals("s"));




     // System.out.println("The sum is: " + addServerIntf.add(1, 2));
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}