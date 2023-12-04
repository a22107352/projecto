import java.rmi.*;
import java.rmi.server.*;

// this is the class with remote methods

public class Sample
  extends UnicastRemoteObject
  implements SampleInterface {

	public Sample() throws RemoteException {
	}

	public String invert(String m) throws RemoteException {
		// return input message with characters reversed
		return new StringBuffer(m).reverse().toString();
	}
}
