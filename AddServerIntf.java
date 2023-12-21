import java.rmi.*;
public interface AddServerIntf extends Remote {
  public Object[] valida_praia(String letra) throws RemoteException;
  public Object[] valida_hora(String hora) throws RemoteException;
  public Object[] valida_num(String num) throws RemoteException;

  public Object[] funcReservar(String letra, int idUser, String hora, int num_pessoas) throws RemoteException;

  public Object[] funcListar(String letra, int idUser, String hora) throws RemoteException;

  public Object[] funcCancelar(String letra, int idUser) throws RemoteException;


}
