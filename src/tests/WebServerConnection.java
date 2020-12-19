package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class WebServerConnection {
	private String url_name;
	private String method;
	public WebServerConnection()
	{
		 url_name="http://localhost:10008";
		 method="GET";
	}
	
	public WebServerConnection(String name)
	{
		 url_name=name;
		 method="GET";
	}
	
	public WebServerConnection(String name, String method)
	{
		 url_name=name;
		 this.method=method;
	}
	
	protected int responseCode() {
		 try {
		    URL myUrl = new URL(url_name);
			
		    HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
			connection.setRequestMethod(method);
		    connection.connect();
		    return connection.getResponseCode();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected String contentType()
	{
		return hederField(3);
	}
	
	protected String fileLength()
	{
		return hederField(4);
	}
	
	private String hederField(int n) { // extrage campul n din heder
		 try {
			    URL myUrl = new URL(url_name);

			    HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
				connection.setRequestMethod(method);
			    connection.connect();
			    return connection.getHeaderField(n);
			} catch (ProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			return "0";	
	}
	
	
	  protected String httpUrlConnection(String desiredUrl)
	  throws Exception
	  {
	    URL url = new URL(desiredUrl);
	    BufferedReader reader = null;
	    StringBuilder stringBuilder;

	    try
	    {
	      
	      url = new URL(desiredUrl);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      
	     
	      connection.setRequestMethod("GET");
	   
	      connection.setReadTimeout(15*1000);
	      connection.connect();

	      
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      stringBuilder = new StringBuilder();

	      String line = null;
	      while ((line = reader.readLine()) != null)
	      {
	    	  stringBuilder.append(line + "\n");
	    	  //System.out.println(line);
	      }
	      return stringBuilder.toString();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw e;
	    }
	    finally
	    {
	     
	      if (reader != null)
	      {
	        try
	        {
	          reader.close();
	        }
	        catch (IOException ioe)
	        {
	          ioe.printStackTrace();
	        }
	      }
	    }
	  }
}
