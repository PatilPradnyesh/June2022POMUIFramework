package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterTest extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginpage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object[][]registerData = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return registerData;
	}
	
	public String getRandomEmail() {
		Random random = new Random();
		String email = "automationTest" + random.nextInt(10000) +"@gmail.com";
		return email;
	}
	
	@Test(dataProvider = "getRegTestData")
	public void registerUserTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		String actSuccMessage = registerPage.userRegister(firstName, lastName, getRandomEmail(), telephone, password, subscribe);
		Assert.assertEquals(actSuccMessage, AppConstants.REGISTRATION_SUCCESS_MESSAGE);
	}

}
