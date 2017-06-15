package modules;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class CognexIIWA_FTPlib {
	
	private String serverAddress = null;
	private int serverPort;
	private String username = null;
	private String password = null;
	private String ftpLocalFileName = null;
	private String ftpLocalDownloadPath = null;
	private String ftpRemoteFileName = null;
	private String ftpFileExtension = null;
	/**
	 *Cognex supports .jpg and .bmp
	 */
	public enum EfileExtension {jpg, bmp};
	
	/**
	 * Server default port set to 21
	 * @param serverAddress	String e.g "172.31.1.148"
	 * @param username		String e.g "JohnDoe"
	 * @param password		String e.g "password123"
	 */
	public CognexIIWA_FTPlib(String serverAddress, String username, String password) {
		this.setServerAddress(serverAddress);
		this.setUsername(username);
		this.setPassword(password);	
		this.setServerPort(21);
	}
	public CognexIIWA_FTPlib(String serverAddress, int serverPort, String username, String password) {
		this.setServerAddress(serverAddress);
		this.setUsername(username);
		this.setPassword(password);	
		this.setServerPort(serverPort);
	}
	
	public void downloadFile() throws InterruptedException {
		// TODO Auto-generated method stub
		
		if (this.getFtpLocalDownloadPath() == null) {
			System.err.println("Warning: local download path not set, defaults to \"c:\" <CognexIIWA_FTPlib.java>");
			this.setFtpLocalDownloadPath("c:/");
		} 
		if (this.getFtpLocalFileName() == null){
			System.err.println("Warning: local filaname not set, defaults to \"date/time/.jpg\"  <CognexIIWA_FTPlib.java>");
			this.setFtpLocalFileName(".jpg");
		}
		if (this.getFtpRemoteFileName() == null) {
			System.err.println("Warning: remote filaname not set, defaults to \"image.jpg\"  <CognexIIWA_FTPlib.java>");
			this.setFtpRemoteFileName("Image.jpg");
		}
		if (this.ftpFileExtension == null) {
			System.err.println("Warning: remote filaname extension not set, defaults to \"image.jpg\"  <CognexIIWA_FTPlib.java>");
			this.ftpFileExtension = ".jpg";
		}
		
		
		
		String server = this.getServerAddress();
		int port = getServerPort();
		String username = this.getUsername();
		String password = this.getPassword();
		boolean binaryTransfer = true;
		boolean localActive = false;
		boolean listFile = false; //this will list the file before downloading, only useful for debug
		
		FTPClient ftp = new FTPClient(); //regular ftp (no http, no ftps) appache.commons offers different kinds
		FTPClientConfig config = new FTPClientConfig();
		
		Calendar myDate = Calendar.getInstance(); // date and time
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss"); // formated to your likings
		
		//local file output format and path
		String currDate = mySdf.format(myDate.getTime()) + "_";
		String localOutputPath = this.getFtpLocalDownloadPath() + currDate + this.getFtpLocalFileName() + this.getFileExtension();	
		
		//remote Cognex ftp file name 
		String remoteFilename = this.getFtpRemoteFileName() + this.getFileExtension();
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
            	for (FTPFile f : ftp.listFiles("/")) {
            		System.out.println(f.getRawListing());
            		//System.out.println(f.toFormattedString(displayTimeZoneId));
            	}
            }
            {
                OutputStream output;

                output = new FileOutputStream(localOutputPath);

                ftp.retrieveFile(remoteFilename, output);

                output.close();
                System.out.println("Sunrise --> File: " + localOutputPath + " downloaded succesfully");
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
	public void setFileExtension(EfileExtension fileExtension) {
		this.ftpFileExtension = "." + fileExtension.toString();
	}	
	public String getFileExtension() {
		return this.ftpFileExtension;
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
	public String getFtpLocalDownloadPath() {
		return ftpLocalDownloadPath;
	}
	public void setFtpLocalDownloadPath(String ftpLocalDownloadPath) {
		this.ftpLocalDownloadPath = ftpLocalDownloadPath;
	}
	public String getFtpRemoteFileName() {
		return ftpRemoteFileName;
	}
	public void setFtpRemoteFileName(String ftpRemoteFileName) {
		this.ftpRemoteFileName = ftpRemoteFileName;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
