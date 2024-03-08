package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.model.Log;
import com.qa.opencart.error.AppError;
import com.qa.opencart.exception.FrameworkException;
import com.qa.opencart.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	public WebDriver driver;
	public Properties prop;
	
	private static final Logger  LOG = Logger.getLogger(DriverFactory.class);
	
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver>tlDriver = new ThreadLocal<WebDriver>(); 
	
	public static String highlight;
	
	/**
	 * this method is used to init driver on the basis of prop
	 * @param prop
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser").toLowerCase();
        System.out.println("Launching browser "+browserName);
        LOG.info("Launching browser "+browserName);
        
		highlight = prop.getProperty("highlight").trim();
		
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equals("chrome")) {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				initRemoteWebDriver("chrome");
			}
			else {
				WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));	
			}
		}
		else if(browserName.equals("firefox")) {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				initRemoteWebDriver("firefox");
			}
			else {
				WebDriverManager.firefoxdriver().setup();
			//driver = new ChromeDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));	
			}
		}
		else if(browserName.equals("edge")) {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				initRemoteWebDriver("edge");
			}
			else {
				WebDriverManager.edgedriver().setup();
			//driver = new ChromeDriver();
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));	
			}
		}
		else {
			System.out.println("Plz pass the right browser name: " + browserName);
			LOG.error("Plz pass the right browser name: " + browserName);
			throw new FrameworkException(AppError.BROWSER_NOT_FOUND);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	private void initRemoteWebDriver(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(browser.equalsIgnoreCase("edge")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
		    LOG.error("Please pass correct browser for remote execution");
		}
		
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * this method is used to init the config properties
	 * @return
	 */
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		// mvn clean install -Denv="dev"
		// mvn clean install
		
		//String envName = System.getenv("env");// stage/uat/qa/dev
		//String envName = System.getenv("env");
		String envName = System.getProperty("env");
		System.out.println("------->Running test cases on enviorment--------> : " +envName);
		LOG.info("------->Running test cases on enviorment--------> : " +envName);
		
		if(envName == null) {
			System.out.println("No env is given..hence running it on QA env.....");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
		try {
		switch (envName) {
		case "qa":
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			break;
			
		case "stage":
			ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
			break;
			
		case "dev":
			ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
			break;
			
		case "uat":
			ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
			break;
			
		case "prod":
			ip = new FileInputStream("./src/test/resources/config/config.properties");
			break;	
			
		default:
			System.out.println("Please pass valid enviorment" +envName);
			throw new FrameworkException(AppError.ENV_NOT_FOUND);
			//break;
		}
		
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return prop;
	}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
		
	}

}
