import java.rmi.*;

public class SampleClient  {
	public static void main(String args[]) {
		try {
			if (args.length < 2) {
				System.err.println("usage: java SampleClient port string... \n");
				System.exit(1);
			}
			int port = Integer.parseInt(args[0]);
			String url = "//localhost:" + port + "/Sample";
			System.out.println("looking up " + url);
			SampleInterface sample = (SampleInterface)Naming.lookup(url);
			for (int i=1; i < args.length; ++i)
				// call the remote method and print the return
				System.out.println(sample.invert(args[i]));
		} catch(Exception e) {
			System.out.println("SampleClient exception: " + e);
			//s
		}
	}
}
