import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;

public class AddServerImpl extends UnicastRemoteObject
        implements AddServerIntf {

    public AddServerImpl() throws RemoteException {
    }
    final String pathReservas = "C:\\Users\\filip\\OneDrive\\Documentos\\Faculdade\\3º ano\\1º Semestre\\Computação distribuida\\projecto\\reservas.txt";

    File fileReservas = new File(pathReservas);

    public Object[] valida_praia(String letra)throws RemoteException {

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


    public Object[] funcReservar(String letra, int idUser, String hora, int num_pessoas) throws RemoteException {

        Object[] returnObj = new Object[2];

        boolean reserva = false;


        try (BufferedReader leitor = new BufferedReader(new FileReader(pathReservas))) {//praia,id sombrinha,hora,n pessoas,iduser reservou, reservado
            List<String> linhas = new ArrayList<>();

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] praias = linha.split(",");

                if (praias[0].equals(letra) && praias[2].equals(hora) && num_pessoas <= Integer.parseInt(praias[3]) && praias[5].equals("0") && !reserva) {
                    praias[praias.length - 1] = "1";
                    praias[praias.length - 2] = String.valueOf(idUser);
                    reserva = true;
                }
                linhas.add(String.join(",", praias));
            }

            if (reserva) {
                returnObj[0] = (true);
                returnObj[1] = ("Reserva feito com sucesso.");

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileReservas))) {
                    for (String linhaAtualizada : linhas) {
                        bw.write(linhaAtualizada);
                        bw.newLine();
                    }
                }

            } else {
                returnObj[0] = (false);
                returnObj[1] = ("Praia não disponivel.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnObj;
    }


    public Object[] funcListar(String letra, int idUser, String hora) throws RemoteException {

        Object[] returnObj = new Object[2];

        try (BufferedReader leitorReservas = new BufferedReader(new FileReader(pathReservas))) {

            boolean ocupado = false;
            String linha;
            while ((linha = leitorReservas.readLine()) != null) {
                String[] reservas = linha.split(",");
                if (reservas[5].equals(Integer.toString(1)) && reservas[0].equals(letra) && String.valueOf(reservas[2]).equals(hora)) {
                    ocupado = true;
                }
            }

            if (ocupado) {
                returnObj[0] = (false);
                returnObj[1] = ("Sombrinha ocupada.");
            } else {
                returnObj[0] = (true);
                returnObj[1] = ("Sombrinha disponivel.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnObj;
    }

    public Object[] funcCancelar(String letra, int idUser) throws RemoteException {

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
