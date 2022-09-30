package StepDefn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Steps {
	
	public WebDriver driver;
	private static String firstname = null;
	private static String lastname = null;
	private static String zip=null;
	

@Given("the user hit the api and capture firstname, lastname and zip")
public void the_user_hit_the_api_and_capture_firstname_lastname_and_zip() {
	RestAssured.baseURI = "https://randomuser.me/api/";
	Response getResponse = RestAssured
			.given()
			.when()
			.get()
			.then()
			.statusCode(200)
			.extract()
			.response();
	
	firstname = JsonPath.read(getResponse.asString(), "$..first").toString();
System.out.println(firstname);			

lastname = JsonPath.read(getResponse.asString(), "$..last").toString();
System.out.println(lastname);			

zip = JsonPath.read(getResponse.asString(), "$..postcode").toString();
System.out.println(zip);			

File configFile = new File("config.properties");

try {
    Properties props = new Properties();
    props.setProperty("firstname", firstname);
    props.setProperty("lastname", lastname);
    props.setProperty("zip", zip);
    
    FileWriter writer = new FileWriter(configFile);
    props.store(writer, "API data");
    writer.close();
} catch (FileNotFoundException ex) {
    // file does not exist
} catch (IOException ex) {
    // I/O error
}
}




	@Given("the user launches {string}")
	public void the_user_launches(String url) {
		// Write code here that turns the phrase above into concrete actions
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.get(url);
		
	}
	@Then("the user enters {string} as username")
	public void the_user_enters_as_username(String username) {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("user-name")).sendKeys(username);
	}
	@Then("the user enters {string} as password")
	public void the_user_enters_as_password(String password) {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("password")).sendKeys(password);
	}
	@Then("the users clicks on login button")
	public void the_users_clicks_on_login_button() {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("login-button")).click();
	}
	@Then("the user Filter items based on Price {string}")
	public void the_user_filter_items_based_on_price(String string) {
		// Write code here that turns the phrase above into concrete actions
	//	throw new io.cucumber.java.PendingException();
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@data-test=\"product_sort_container\"]")));
		dropdown.selectByValue("hilo");
	}
	@Then("the user Scroll to {string}")
	public void the_user_scroll_to(String string) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[text()='Sauce Labs Onesie']")));
	}
	@Then("the user Click on Add to cart and verify items")
	public void the_user_click_on_add_to_cart_and_verify_items() {
		driver.findElement(By.xpath("//div[text()='Sauce Labs Onesie']/ancestor::div[@class='inventory_item_description']//button")).click();
		driver.findElement(By.xpath("//a[@class=\"shopping_cart_link\"]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class=\"inventory_item_name\"]")).getText().equals("Sauce Labs Onesie"));
	}
	@Then("the user Click the Checkout button")
	public void the_user_click_the_checkout_button() {
		driver.findElement(By.id("checkout")).click();
	}
	@Then("the user provides value from API")
	public void the_user_provides_value_from_api() {
		// Write code here that turns the phrase above
		File configFile = new File("config.properties");
		 
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		    driver.findElement(By.id("first-name")).sendKeys( props.getProperty("firstname").replaceAll("[^\\dA-Za-z ]", ""));
			driver.findElement(By.id("last-name")).sendKeys( props.getProperty("lastname").replaceAll("[^\\dA-Za-z ]", ""));
			driver.findElement(By.id("postal-code")).sendKeys( props.getProperty("zip").replaceAll("[^\\dA-Za-z ]", ""));
		    reader.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		
		
	}
	@Then("user Click the continue button")
	public void user_click_the_continue_button() {
		driver.findElement(By.id("continue")).click();
	}
	@Then("user Click the Finish button")
	public void user_click_the_finish_button() {
		driver.findElement(By.id("finish")).click();
	}
	@Then("user Verifies the order success message.")
	public void user_verifies_the_order_success_message() {
		Assert.assertTrue(driver.findElement(By.xpath("//h2")).getText().equals("THANK YOU FOR YOUR ORDER"));
	}



}
