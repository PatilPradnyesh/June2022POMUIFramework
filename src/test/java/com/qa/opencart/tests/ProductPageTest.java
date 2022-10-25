package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class ProductPageTest extends BaseTest{
	
	@BeforeClass
	public void prodInfoSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductKeys() {
		return new Object[][] {
			{"MacBook","MacBook Air"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getProductKeys")
	public void productHeaderTest(String productKey, String mainProductName) {
		searchResultsPage = accPage.performSearch(productKey);
		productInfoPage = searchResultsPage.selectproduct(mainProductName);
		String actHeader = productInfoPage.getProductHeader(mainProductName);
		Assert.assertEquals(actHeader, mainProductName);
	}
	
	@DataProvider
	public Object[][] getProductInfoData() {
		return new Object[][] {
			{"MacBook","MacBook Air", AppConstants.MACBOOK_AIR_IMAGES_COUNT},
			{"iMac","iMac", AppConstants.MACBOOK_IMAC_IMAGES_COUNT},
			{"Samsung","Samsung SyncMaster 941BW", AppConstants.SAMSUNG_SYNCMASTER_IMAGES_COUNT},
			{"Samsung","Samsung Galaxy Tab 10.1", AppConstants.SAMSUNG_GALAXY_TAB_COUNT}
		};
	}
	
	@Test (dataProvider = "getProductInfoData")
	public void productImagesCountTest(String searchKey, String mainProductName, int imagesCount) {
		searchResultsPage = accPage.performSearch(searchKey);
		productInfoPage = searchResultsPage.selectproduct(mainProductName);
		int prodImageCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual Product Images : " +prodImageCount);
		Assert.assertEquals(prodImageCount, imagesCount);
	}
	
	@Test
	public void productMetaDataTest() {
		searchResultsPage = accPage.performSearch("MacBook");
		productInfoPage = searchResultsPage.selectproduct("MacBook Pro");
		Map <String,String> actProductMetaDataMap = productInfoPage.getProductMetaData();
		Assert.assertEquals(actProductMetaDataMap.get("Brand"), "Apple");
		Assert.assertEquals(actProductMetaDataMap.get("Product Code"), "Product 18");
		Assert.assertEquals(actProductMetaDataMap.get("Reward Points"), "800");
		Assert.assertEquals(actProductMetaDataMap.get("Availability"), "In Stock");
	}

}
