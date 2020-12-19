package seleniumtests;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SeleniumWebServerTest {
    private String driverPath = "D:\\Selenium\\geckodriver.exe";
    public WebDriver driver;
    
    @Before
    public void startBrowser() {
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
    }
    
    @After
    public void endTest() {
        driver.close();
    }

    @Test
    public void titlu_test() { // verificam titlul pagini
  	  String baseUrl = "http://localhost:10008/home.html";
      String expectedTitle = "WEB server test project";
      String actualTitle = "";
      
      driver.get(baseUrl); // launch Fire fox and direct it to the Base URL
      actualTitle = driver.getTitle();
      Assert.assertEquals(expectedTitle, actualTitle); 	
    }
    
    @Test
    public void textlink_test() { // verificam legatura la Pagina 1
  	  String baseUrl = "http://localhost:10008/home.html";
      String expectedTitle = "Pagina 1";
      String actualTitle = "";
      
      driver.get(baseUrl); // launch Fire fox and direct it to the Base URL
      driver.findElement(By.partialLinkText("ptest1")).click();	
      actualTitle = driver.getTitle();
      Assert.assertEquals(expectedTitle, actualTitle); 	
    }

    @Test
    public void test_img1() { // verificam exista figura jpg
  	  String baseUrl = "http://localhost:10008/home.html";
      driver.get(baseUrl); // launch Fire fox and direct it to the Base URL
      try {
    	  driver.findElement(By.id("jpg1"));
    	  //assert(true);
      }catch(NoSuchElementException e ) {
    	  fail(); 
      }
    }
    
    @Test
    public void test_gif1() { // verificam exista figura gif
  	  String baseUrl = "http://localhost:10008/home.html";
      driver.get(baseUrl);
      try {
    	  driver.findElement(By.id("gif1"));
    	  //assert(true);
      }catch(NoSuchElementException e ) {
    	  fail(); 
      }
    }
    

	@Test
	public void testInputTextBox() {
		try {
			String input=new String("test input text");
			driver.get("http://localhost:10008/home.html");
			WebElement e = driver.findElement(By.id("nume" ));
			e.sendKeys(input);
			Assert.assertEquals(input, e.getAttribute("value"));
		}catch(Exception e) {
			fail();
		}
	}
    
 
	@Test
	public void testInputButon() {
		try {
			String input=new String("test input text");
			driver.get("http://localhost:10008/home.html");
			WebElement e = driver.findElement(By.id("nume" ));
			e.sendKeys(input);
			driver.findElement(By.id("sendButton")).click();
			}catch(Exception e) {
			fail();
		}
	}
    
}
