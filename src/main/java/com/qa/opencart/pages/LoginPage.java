package com.qa.opencart.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementsUtil;

import io.qameta.allure.Step;


public class LoginPage {
	//private WebDriver to avoid access and page should have no assertions and test cases.
	private WebDriver driver;
	private ElementsUtil eleUtil;
	
	//1)By Locators (Private in nature as no one can make any changes)
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.cssSelector("input.btn.btn-primary");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.className("img-responsive");
	private By registerLink = By.linkText("Register");
	
	private static final Logger LOG = Logger.getLogger(LoginPage.class);
	
	//2)Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);
	}
	
	//3)Page Actions
	@Step("Waiting for login page title and fetching the title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE);
		System.out.println("Login Page Title is : "+ title);
		LOG.info("Login Page Title is : "+ title);
		return title;
	}
	
	@Step("Waiting for login page url and fetching the url")
	public boolean getLoginPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_PARAM);
		System.out.println("Login Page Url is : "+ url);
		if(url.contains(AppConstants.LOGIN_PAGE_URL_PARAM)) {
			return true;
		}
		return false;
	}
	
	@Step("checking forgot pwd link is displayed on login page")
	public boolean isForgotPwdLinkExist() {
		boolean flag = eleUtil.doEleIsDisplayed(forgotPwdLink);
		if(flag) {
			return true;
		}
		return false;
	}
	
	@Step("login with username : {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("user creds are : "+username +" : "+pwd);
		eleUtil.doSendKeysWithWaitForVisibleEle(emailId, AppConstants.DEFAULT_LARGE_TIME_OUT, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		System.out.println("navigating to register page.....");
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
