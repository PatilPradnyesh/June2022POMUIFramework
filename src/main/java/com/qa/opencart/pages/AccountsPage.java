package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementsUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementsUtil eleUtil;
	
	private By accSecHeader = By.cssSelector("div#content h2");
	private By logoutLink = By.linkText("Logout");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
    public AccountsPage (WebDriver driver) {
    	this.driver = driver;
    	eleUtil = new ElementsUtil(driver);
    }
    
    @Step("getAccPageTitle......")
    public String getAccPageTitle() {
    	String title = eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACC_PAGE_TITLE);
		System.out.println("Accounts page title is : "+ title);
		return title;
    }
    
    @Step("getAccPageUrl.....")
    public boolean getAccPageUrl() {
    	String url = eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACC_PAGE_URL_PARAM);
    	System.out.println("Accounts page Url is : "+ url);
    	if(url.contains(AppConstants.ACC_PAGE_URL_PARAM)) {
    		return true;
    	}
    	return false; 
    }
    
    @Step("isLogoutLinkExist.....")
    public boolean isLogoutLinkExist() {
    	return eleUtil.doEleIsDisplayed(logoutLink);
    }
    
    @Step("isSearchExist.....")
    public boolean isSearchExist() {
    	return eleUtil.doEleIsDisplayed(search);
    }
    
    @Step("performSearch.....{0}")
    public SearchResultsPage performSearch(String productKey) {
    	System.out.println("Product key is : " +productKey);
    	if(isSearchExist()) {
    		eleUtil.doSendKeys(search, productKey);
            eleUtil.doClick(searchIcon);
            return new SearchResultsPage(driver);
    	}
    	else {
    		System.out.println("Search Field is not Present on the page");
    		return null;
    	}
    }
    
    @Step("getAccSecHeadersList.....")
    public ArrayList<String> getAccHeaderList() {
    	List <WebElement> headerList = eleUtil.waitForElementsToBeVisible(accSecHeader, AppConstants.DEFAULT_LARGE_TIME_OUT);
    	System.out.println("Total Sections Headers: " + headerList.size());
    	ArrayList <String> accHeaderList = new ArrayList<String>();
    	for(WebElement e: headerList) {
    		String text = e.getText();
    		accHeaderList.add(text);
    	}
    	return accHeaderList;
    }
    
    
}
