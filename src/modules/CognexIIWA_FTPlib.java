package modules;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class CognexIIWA_FTPlib {
	
	private String serverAddress = null;
	private String username = null;
	private String password = null;
	private String ftpLocalFileName = null;
	
	public CognexIIWA_FTPlib(String serverAddress, String username, String password) {
		this.setServerAddress(serverAddress);
		this.setUsername(username);
		this.setPassword(password);
		
	}
	public void downloadFile() throws InterruptedException {
		// TODO Auto-generated method stub
		
		String server = this.getServerAddress();
		int port = 21;
		String username = this.getUsername();
		String password = this.getPassword();
		String remote = "image.jpg";
		boolean binaryTransfer = true;
		boolean localActive = false;
		boolean listFile = false; //this will list the file before downloading, only useful for debug
		
		FTPClient ftp = new FTPClient(); //regular ftp (no http, no ftps) appache.commons offers different kinds
		FTPClientConfig config = new FTPClientConfig();
		
		Calendar myDate = Calendar.getInstance(); // date and time
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss"); // formated to your likings
		
		//local file output format and path
		String currDate = mySdf.format(myDate.getTime()) + "_";
		
		String localPath = "c:/KUKA/KUKA Projects/RandD_Cell/KSAF IIWA NutRunner/pics/";
		String localOutputPath = localPath + currDate + this.getFtpLocalFileName();	
		
		ftp.setListHiddenFiles(false);
		//Line below enables detail server responses, usefull for debug
		//ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
		ftp.configure(config);
		
		//Attempt connection to server
		try {
			ftp.connect(server, port);
			System.out.println("Sunrise --> FTP connection to: " + server + " port: " + (port>0 ? port : ftp.getDefaultPort()));
			
			// After connection attempt, you should check the reply code to verify
            // success.
            int reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
			
		} catch (SocketException e) {
			System.out.println("SocketException?");
			e.printStackTrace();
		} catch (IOException e) {
			 if (ftp.isConnected())
	            {
	                try
	                {
	                    ftp.disconnect();
	                }
	                catch (IOException f)
	                {
	                    // do nothing
	                }
	            }
	            System.err.println("Could not connect to server.");
	            e.printStackTrace();
	            System.exit(1);
		}
		
		
		//Login user and download file
		try
        {
            if (!ftp.login(username, password))
            {
                ftp.logout();
                System.err.println("does not like my credentials :)");
            }
            
            //Server response when login successful
            //System.out.println("Remote system is " + ftp.getSystemType());

            if (binaryTransfer) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            } else {
                // in theory this should not be necessary as servers should default to ASCII
                // but they don't all do so - see NET-500
                ftp.setFileType(FTP.ASCII_FILE_TYPE);
            }

            // Use passive mode as default because most of us are
            // behind firewalls these days.
            if (localActive) {
                ftp.enterLocalActiveMode();
            } else {
                ftp.enterLocalPassiveMode();
            }
            
            if (listFile) {
            	for (FTPFile f : ftp.listFiles(remote)) {
            		System.out.println(f.getRawListing());
            		//System.out.println(f.toFormattedString(displayTimeZoneId));
            	}
            }
            {
                OutputStream output;

                output = new FileOutputStream(localOutputPath);

                ftp.retrieveFile(remote, output);

                output.close();
                System.out.println("Sunrise --> File: " + currDate + ftpLocalFileName + " downloaded succesfully");
            }

            ftp.noop(); // check that control connection is working OK

            ftp.logout();
        }
        catch (FTPConnectionClosedException e)
        {
            
            System.err.println("Server closed connection.");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (ftp.isConnected())
            {
                try
                {
                    ftp.disconnect();
                }
                catch (IOException f)
                {
                    // do nothing
                }
            }
        }
		
		Thread.sleep(100);
		try {
			ftp.disconnect();
			System.out.println("Sunrise --> FTP disconnected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFtpLocalFileName() {
		return ftpLocalFileName;
	}
	public void setFtpLocalFileName(String ftpLocalFileName) {
		this.ftpLocalFileName = ftpLocalFileName;
	}

}
