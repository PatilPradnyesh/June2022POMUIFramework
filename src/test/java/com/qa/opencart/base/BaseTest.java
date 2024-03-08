package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	DriverFactory df;
	 
	public Properties prop;
    public WebDriver driver;
    
    public LoginPage loginpage;
    public AccountsPage accPage;
    public SearchResultsPage searchResultsPage; 
    public ProductInfoPage productInfoPage;
    public RegisterPage registerPage;
    
	@Parameters({"browser"})
    @BeforeTest
	public void setup(String browser) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browser != null) {
		prop.setProperty("browser", browser);	
		}
		
		driver = df.initDriver(prop);
		
		loginpage = new LoginPage(driver);
	}
    
	
    @AfterTest
	public void tearDown() {
		driver.quit();
	}
}
