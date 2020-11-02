package tests;

import org.junit.Assert;

import org.junit.Test;
import webserver.WebServer;



public class WebServerTest {
	private String sendStrig, recString, expected;
	private WebServerConnection wbcon;    
	
	@Test 
	public void testResponse() {
		wbcon=new WebServerConnection();
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(503,code);
	}
	
	
	@Test 
	public void testResponseOK() {
		wbcon=new WebServerConnection();
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test
	public void maintenancetest() {
    	try {
    		wbcon=new WebServerConnection();
    		expected=new String("Server is in Maintenance");
    		recString=wbcon.httpUrlConnection("http://localhost:10008/");
			Assert.assertEquals(expected,recString);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
}
