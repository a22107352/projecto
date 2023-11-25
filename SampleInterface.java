import java.rmi.*;

public interface SampleInterface extends Remote {
	public String invert(String msg) throws RemoteException;
}
