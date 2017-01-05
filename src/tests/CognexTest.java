package tests;


public class CognexTest {



	public static void main(String[] args) {
		try {
			CognexLogin telnet = new CognexLogin("172.31.1.69","admin","");
			telnet.readUntil("User Logged In");
			telnet.write("SW8");
			telnet.readResponse(1);
			System.out.println("Done");
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
