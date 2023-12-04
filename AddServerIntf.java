import java.rmi.*;
public interface AddServerIntf extends Remote {
  double add(double d1, double d2) throws RemoteException;
  public String registo(String email, String password, String passwordRepetida, boolean existeEmail, int idUser) throws RemoteException, Error;
}
