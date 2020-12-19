package webserver;

import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class WebServer extends Thread {
	protected Socket clientSocket;
	static final File WEB_ROOT = new File("D:\\ProiectVVS");
	static final String DEFAULT_FILE = "home.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	static public enum ServerStatus{Stopped, Running, Maintenance}; 
	static private ServerStatus stare=ServerStatus.Running;
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10008);
			System.out.println("Connection Socket Created");
			try {
				while (true) {
					if(stare!=ServerStatus.Stopped) {
						System.out.println("Waiting for Connection");
						new WebServer(serverSocket.accept());
					}
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10008.");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: 10008.");
				System.exit(1);
			}
		}
	}

	public WebServer(Socket clientSoc) {
		clientSocket = clientSoc;
		start();
	}

	public void run() {
		System.out.println("New Communication Thread Started");
		String fileRequested = null;
		byte[] fileData=null;
		int fileLength=0;
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine=in.readLine();

			BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

			
			StringTokenizer parse = new StringTokenizer(inputLine);
			String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
			fileRequested = parse.nextToken().toLowerCase();
			
			System.out.println("fileRequested: "+fileRequested );
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);

				if (inputLine.trim().equals(""))
					break;
			}
			
			if(stare==ServerStatus.Running) 
			{

			if (method.equals("GET")) {

				if (fileRequested.endsWith("/")) {
					fileRequested += DEFAULT_FILE;
				}
						
				File file = new File(WEB_ROOT, fileRequested);
				if((fileLength = (int) file.length())==0)
				{
					fileRequested=fileRequested.replaceAll("%20", " ");
					file = new File(WEB_ROOT, fileRequested);
					fileLength = (int) file.length();
				}
				String content = getContentType(fileRequested);
				System.out.println("WEB_ROOT+file: "+file+"         content: "+content);
				try {
					int n;
					if((n=fileRequested.indexOf('?'))<0)
						fileData = readFileData(file, fileLength);

					else
					{
						String t="Input text box: "+fileRequested.substring(n+1);
						t=t.replaceAll("\\+", " ");
						fileData=t.getBytes("utf-8");
						fileLength=fileData.length;
					}
					System.out.println("readFileData - pass");
				//  send HTTP Headers with data to client
				out.println("HTTP/1.1 200 OK r n");
				out.println("status: \"200\"");
				out.println("Server: Java HTTP Server");
				out.println("Content-type: " + content);
				out.println("Content-length: " + fileLength);
				out.println();  // blank line between headers and content
				out.flush(); 
				
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				}catch(FileNotFoundException fnfe) {
					System.err.println("Fisierul nu a fost gasit "+file);
					out.println("HTTP/1.1 404 File Not Found");
					out.println("status: \"404\"");
					out.println(); 
					out.flush(); 
			}
			}	
			else
			{
				System.err.println("Metoda nu este implementata");
				out.println("HTTP/1.1 501 Not Implemented");
				out.println("status: \"501\"");
				out.println(); 
				out.flush(); 
			}
			}else {
				out.println("HTTP/1.1 503 Service Unavailable");
				out.println("Server: Java HTTP Server");
				out.println("Status: \"503\"");
				out.println(); 
				out.println("<!DOCTYPE html><html><p> Server is in Maintenance</p></html>");
				out.flush();
				
			}
			
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	// return supported Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			if(fileRequested.endsWith(".css"))
				return "text/css";
		else
			return "text/plain";
	}

	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
}
