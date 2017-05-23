package modules;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EthComClientServer {

	
	public static void main(String[] args) {



	}

	private void xmlServerTest() throws IOException {
		
		InputStream in = null;
		OutputStream out = null;

		System.out.println("server starts");
		ServerSocket ssock = new ServerSocket(30008); // open socket
		Socket sock = ssock.accept(); // accept connection
		in = sock.getInputStream();// Receive from socket
		out = new FileOutputStream("d:/Transfer/UserXMLs/testxmldump.xml");
		byte[] bytes = new byte[16 * 1024];
		int count;
		System.out.println("Listening");
		while ((count = in.read(bytes)) > 0) {
			out.write(bytes, 0, count);
			System.out.println(bytes);
		}

		out.close();
		in.close();
		sock.close();
		ssock.close();
		System.out.println("Done");

	}

	
	
}
