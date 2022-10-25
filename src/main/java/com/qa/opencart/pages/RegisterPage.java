package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementsUtil;

public class RegisterPage {
	private WebDriver driver;
	private ElementsUtil eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//input[@name='newsletter' and @value='1']");
	private By subscribeNo = By.xpath("//input[@name='newsletter' and @value='0']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.cssSelector("input.btn.btn-primary");
	
	private By registerSuccessMessage = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);
	}
	
	/**
	 * user registration
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param telephone
	 * @param password
	 * @param subscribe
	 */
	
	public String userRegister(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.doSendKeysWithWaitForVisibleEle(this.firstName, AppConstants.DEFAULT_LARGE_TIME_OUT, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else if(subscribe.equalsIgnoreCase("no")) {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String actSuccMessage = eleUtil.waitForElementVisible(registerSuccessMessage, AppConstants.DEFAULT_LARGE_TIME_OUT).getText();
		System.out.println("Success Messsggggg=====>" + actSuccMessage);
		
		eleUtil.doClick(logoutLink);
		eleUtil.doClickWithWait(registerLink, AppConstants.DEFAULT_LARGE_TIME_OUT);
		
		return actSuccMessage;
	}

}
