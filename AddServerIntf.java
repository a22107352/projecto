import java.rmi.*;
public interface AddServerIntf extends Remote {
  public Object[] registo(String email, String password, String passwordRepetida, boolean existeEmail, int idUser) throws RemoteException;
  public Object[] login(String email, String password) throws RemoteException;
  public Object[] valida_escolha(String letra) throws RemoteException;
  public Object[] valida_hora(String hora) throws RemoteException;
  public Object[] valida_num(String num) throws RemoteException;

}
