package tests;

import org.junit.Assert;

import org.junit.Test;

public class WebServerTest {
	private String recString, expected;
	private WebServerConnection wbcon;  
	
	@Test 
	public void testResponse_localhost_10008() { 
		wbcon=new WebServerConnection();     // pagina home
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test 
	public void testResponse_ptest1() {
		wbcon=new WebServerConnection("http://localhost:10008/ptest1.html");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test 
	public void testResponse_subfolder() {
		wbcon=new WebServerConnection("http://localhost:10008/FOLDER_A/ptest1.html");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test 
	public void testResponse_spatiu() {
		wbcon=new WebServerConnection("http://localhost:10008/FOLDER_A/a%201.html");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test 
	public void testResponse_proc20() {
		wbcon=new WebServerConnection("http://localhost:10008/FOLDER_A/a%20.html");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(200,code);
	}
	
	@Test 
	public void content_html() {		// tipul fisierului home
		wbcon=new WebServerConnection();
		
		recString=wbcon.contentType();
		System.out.println(recString);
		Assert.assertEquals("text/html",recString);
	}
	
	@Test 
	public void content_css() {
		wbcon=new WebServerConnection("http://localhost:10008/style_base.css");
		
		recString=wbcon.contentType();
		System.out.println(recString);
		Assert.assertEquals("text/css",recString);
	}
	
	
	@Test 
	public void content_text() {
		wbcon=new WebServerConnection("http://localhost:10008/fis1.txt");
		
		recString=wbcon.contentType();
		System.out.println(recString);
		Assert.assertEquals("text/plain",recString);
	}
	
	@Test 
	public void content_inputTextbox() { // continutul textBox se afiseaza ca fisier text/plain
		wbcon=new WebServerConnection("http://localhost:10008/?");
		
		recString=wbcon.contentType();
		System.out.println(recString);
		Assert.assertEquals("text/plain",recString);
	}
	
	@Test 
	public void fileLength_text() {
		wbcon=new WebServerConnection("http://localhost:10008/FOLDER_A/ptest2.html");
		
		recString=wbcon.fileLength();
		System.out.println( "fileLength_text "+recString);
		Assert.assertEquals("540",recString);
	}
	
	@Test 
	public void testResponse404() {
		wbcon=new WebServerConnection("http://localhost:10008/abc.html");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(404,code);
	}
	
	
	@Test 
	public void testResponse501() {
		wbcon=new WebServerConnection("http://localhost:10008","POST");
		
		int code=wbcon.responseCode();
		System.out.println(code);
		Assert.assertEquals(501,code);
	}
	
	
	@Test 
	public void testResponse_maintenance() {  
		wbcon=new WebServerConnection();
		
		int code=wbcon.responseCode();
		System.out.println(code);
		//Assert.assertEquals(503,code); // test pass for ServerStatus.Maintenance;  
		Assert.assertNotSame(503,code);  // test pass for ServerStatus.Running; 
		
	}
	
	@Test
	public void maintenancetest_string() {   
    	try {
    		wbcon=new WebServerConnection();
    		expected=new String("Server is in Maintenance");
    		recString=wbcon.httpUrlConnection("http://localhost:10008/");
			//Assert.assertEquals(expected,recString); // test pass for ServerStatus.Maintenance;  
    		Assert.assertNotSame(expected,recString);  // test pass for ServerStatus.Running; 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	

}
