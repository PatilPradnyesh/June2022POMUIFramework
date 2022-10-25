package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final int DEFAULT_TIME_OUT = 5;
	
	public static final int DEFAULT_LARGE_TIME_OUT  = 10;
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	
	public static final String ACC_PAGE_TITLE = "My Account";
	
	public static final String LOGIN_PAGE_URL_PARAM = "route=account/login";
	
	public static final String ACC_PAGE_URL_PARAM = "route=account/account";
	
	public static final List<String> ACC_PAGE_SEC_HEADERS = 
			                                                      Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");

	public static final int MACBOOK_AIR_IMAGES_COUNT = 4;
	public static final int MACBOOK_IMAC_IMAGES_COUNT = 3;
	public static final int SAMSUNG_SYNCMASTER_IMAGES_COUNT = 1;
	public static final int SAMSUNG_GALAXY_TAB_COUNT = 7;
	
	public static final String REGISTRATION_SUCCESS_MESSAGE = "Your Account Has Been Created!";
	
	//************************SHEET NAME***********************************//
	public static final String REGISTER_SHEET_NAME = "register";
}
