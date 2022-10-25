package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementsUtil;

public class SearchResultsPage {
	private WebDriver driver;
	private ElementsUtil eleUtil;
	
	private By productSearchLayout = By.cssSelector("div.product-layout.product-grid.col-lg-3.col-md-3.col-sm-6.col-xs-12");
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);
	}
	
	public boolean isSearchSuccesful() {
		List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productSearchLayout, AppConstants.DEFAULT_LARGE_TIME_OUT);
		if(searchList.size()>0) {
			System.out.println("Search is Succesfully done....");
			return true;
		}
			return false;
	
	}
	
	public ProductInfoPage selectproduct(String mainProductName) {
		By mainPrName = By.linkText(mainProductName);
		eleUtil.doClickWithWaitForVisibleEle(mainPrName, AppConstants.DEFAULT_LARGE_TIME_OUT);
		return new ProductInfoPage(driver);
	}

}
