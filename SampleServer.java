import java.rmi.*;
import java.rmi.server.*;

public class SampleServer {
	public static void main(String args[]) {
		if (args.length != 1) {
			System.err.println("usage: java SampleServer rmi_port");
			System.exit(1);
		}
		// Create and install a security manager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		try {
			int port = Integer.parseInt(args[0]);
			String url = "//localhost:" + port + "/Sample";
			Naming.rebind(url, new Sample());
			System.out.println("server " + url + " is running...");
		}
		catch (Exception e) {
			System.out.println("Sample server failed:" + e.getMessage());
		}
	}
}
