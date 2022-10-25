package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementsUtil {
	private WebDriver driver;
	private Select select;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	private static final String ELEMENT_NOT_FOUND_ERROR = "element is not available on the page : ";
	
	public ElementsUtil(WebDriver driver){
		this.driver=driver;
		act=new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}
	
	public List<WebElement> getElements(By locator){
		return driver.findElements(locator);
	}
	
	public void doSendKeys(By locator,String key ) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(key);
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	public boolean doEleIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public boolean isSingleElementPresnt(By locator) {
		List<WebElement> list = getElements(locator);
		System.out.println(list.size());
		if (list.size() == 1) {
			System.out.println("single search ele is present on the page");
			return true;
		} else {
			System.out.println("no search or multiple search present on the page");
			return false;
		}
	}
	
	public int getElementsCount (By locator) {
		return getElements(locator).size();
	}
	
	public ArrayList <String> getElementsTextList(By locator) {
		List <WebElement> eleList = getElements(locator);
		ArrayList <String> eleTextList = new ArrayList<String>();
		
		for(WebElement e :eleList ) {
			String text = e.getText();
			
			if(text.length() != 0){
			 eleTextList.add(text);	
			}
		}
		return eleTextList;
	}
	
	public void doFileUpload(By locator,String path) {
    	getElement(locator).sendKeys(path);
    }
	
	//************************Drop Down Utils************************************\\
	//only when tag name is <select>
	public void doSelectDropDownByIndex(By locator, int index) {
		select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public void doSelectDropDownByVisibleText(By locator, String text) {
		select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public void doSelectDropDownByValue(By locator, String value) {
		select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public List<WebElement> getDropDownOptions(By locator) {
		select = new Select(getElement(locator));
		return select.getOptions();
	}
	
	public int getDropDownOptionsCount(By locator) {
		return getDropDownOptions(locator).size();
	}
	
	public ArrayList <String> getDropDownOptionsText(By locator){
		List <WebElement> dropDownOptionsList = getDropDownOptions(locator);
		ArrayList <String> dropDownOptionsListText = new ArrayList<String>();
		
		for(WebElement e : dropDownOptionsList) {
			String text = e.getText();
			dropDownOptionsListText.add(text);
		}
		return dropDownOptionsListText;
	}
	
	//without using select class
	
	public void doSelectDropDownWithoutSelectClass(By locator, String value) {
		List <WebElement> optionsList = getElements(locator);
		
		System.out.println(optionsList.size());
		
		for(WebElement e: optionsList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(value)) {
				e.click();
				break;
			}
		}
		
	}
	public void search(By searchLocator, String searchValue, By suggLocator, String suggValue ) throws InterruptedException {
		getElement(searchLocator).sendKeys(searchValue);
		Thread.sleep(3000);
		
		List <WebElement> suggestionList = getElements(suggLocator);
		System.out.println(suggestionList.size()-1);
		
		for(WebElement e: suggestionList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(suggValue)) {
				e.click();
				break;
			}
		}
	}
	
	public void doSearch(String tagName, String text) {
		By suggLocator = By.xpath("//"+tagName+"[text()='"+text+"']");
	    getElement(suggLocator).click();
	}
	
	public ArrayList<String> getFooterLinksList(By locator){
		List<WebElement>footerLinkList = getElements(locator);
		System.out.println("Size of Footer is : " +footerLinkList.size());
		
		ArrayList <String> footerLinksList = new ArrayList<String>();
		
		for(WebElement e : footerLinkList) {
			String text = e.getText();
			footerLinksList.add(text);
		}
		return footerLinksList;
	}
	
	public boolean checkElementIsMandatory(String jsScript) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String man_text = js.executeScript(jsScript).toString();

		System.out.println(man_text);

		if (man_text.contains("*")) {
			System.out.println("ele is a mandatory field");
			return true;
		} else {
			System.out.println("FN is not a mandatory field");
			return false;
		}

	}
	
	//************************Actions Utils *****************************//
		public void handleLevel1MenuItems(By parentMenu, By childMenu) throws InterruptedException {
			act.moveToElement(getElement(parentMenu)).build().perform();
			Thread.sleep(3000);
			//getElement(childMenu).click();
			doClick(childMenu);
		}
		
		public void doActionsRightClick(By locator) {
		     WebElement rightClickEle = getElement(locator);   
		     act.contextClick(rightClickEle).build().perform();
		}
		
		public void doActionsDragAndDrop(By sourceEle, By targetEle) {
			act.dragAndDrop(getElement(sourceEle), getElement(targetEle)).build().perform();
		}
		
		public void doActionsSendKeys(By sendKeysEleLocator, String key) {
	        act.sendKeys(getElement(sendKeysEleLocator), key).build().perform();
	    }
	    
	    public void doActionsClick(By clicEleLocator) {
	        act.click(getElement(clicEleLocator)).build().perform();
	    }

	    //***********************************Wait Utils********************************************//
	    /**
		 * An expectation for checking that an element is present on the DOM of a page.
		 * This does notnecessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
	    
	    public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
	    
	    public void doSendKeysWithWait(By locator, int timeOut, String value) {
			waitForElementPresence(locator, timeOut).sendKeys(value);
		}
	    
	    public void doClickWithWait(By locator, int timeOut) {
			waitForElementPresence(locator, timeOut).click();
		}
	    
	    public String getElementTextWithWait(By locator, int timeOut) {
			return waitForElementPresence(locator, timeOut).getText();
		}
	    
	    public String getAttributeWithWait(By locator, int timeOut, String attrName) {
			return waitForElementPresence(locator, timeOut).getAttribute(attrName);
		}
	    
	    public Boolean doEleIsDisplayedWithWait(By locator, int timeOut) {
			return waitForElementPresence(locator, timeOut).isDisplayed();
		}
	    
	    /**
		 * An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * Default polling time 500 ms
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page
		 * and visible. Visibility means that the element is not only displayed but also
		 * has a height and width that is greater than 0.
		 * Default polling time = customized
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locator are visible. 
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		public void doSendKeysWithWaitForVisibleEle(By locator, int timeOut, String value) {
			waitForElementVisible(locator, timeOut).sendKeys(value);
		}
	    
	    public void doClickWithWaitForVisibleEle(By locator, int timeOut) {
			waitForElementVisible(locator, timeOut).click();
		}
	    
	    public String getElementTextWithWaitForVisibleEle(By locator, int timeOut) {
			return waitForElementVisible(locator, timeOut).getText();
		}
	    
	    public String getAttributeWithWaitForVisibleEle(By locator, int timeOut, String attrName) {
			return waitForElementVisible(locator, timeOut).getAttribute(attrName);
		}
	    
	    public Boolean doEleIsDisplayedWithWaitForVisibleEle(By locator, int timeOut) {
			return waitForElementVisible(locator, timeOut).isDisplayed();
		}
	    
	    public void clickWhenReady (By locator, int timeOut) {
	    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();	
	    }
	    
	    public Alert waitForAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait (driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public String getAlertTextWithWait(int timeOut) {
			return waitForAlert(timeOut).getText();
		}
		
		public void acceptAlertWithWait(int timeOut) {
			waitForAlert(timeOut).accept();
		}
		
		public void dismissAlertWithWait(int timeOut) {
			waitForAlert(timeOut).dismiss();
		}
		
		public void doSendKeysAlertWithWait(int timeOut, String value) {
			waitForAlert(timeOut).sendKeys(value);
		}
		
		public String waitForTitleContains(int timeOut, String titleFraction) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
			else {
				return null;
			}
		}
		
		public String waitForTitleIs(int timeOut, String title) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if(wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
			else {
				return null;
			}
		}
		
		public String waitForUrlContains(int timeOut, String urlFraction) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
			else {
				return null;
			}
		}
		
		public String waitForUrlIs(int timeOut, String urlFraction) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if(wait.until(ExpectedConditions.urlToBe(urlFraction))) {
				return driver.getCurrentUrl();
			}
			else {
				return null;
			}
		}
		
		 public void waitForFrame(int timeOut, int frameIndex) {
		    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		    }
		    
		 public void waitForFrame(int timeOut, String nameOrId) {
		    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
		    }
		    
		 public void waitForFrame(int timeOut, WebElement frameElement) {
		    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		    }
		    
		 public void waitForFrame(int timeOut, By locator) {
		    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		    }
		 
		 public WebElement waitForElementToBeVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(timeOut))
							.pollingEvery(Duration.ofSeconds(pollingTime))
								.ignoring(NoSuchElementException.class)
									.ignoring(StaleElementReferenceException.class)
										.ignoring(ElementNotInteractableException.class)
									.withMessage(ElementsUtil.ELEMENT_NOT_FOUND_ERROR + locator);
				
				return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
			}
		 
		 public WebElement retryingElement(By locator, int timeOut) {

				WebElement element = null;

				int attempts = 0;

				while (attempts < timeOut) {

					try {
						element = getElement(locator);
						System.out.println("element is found in attempt: " + attempts);
						break;
					} catch (NoSuchElementException e) {
						System.out.println("element is not found in attempt : " + attempts + " : " + " for " + locator);

						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					attempts++;

				}
				
				if (element == null) {
					try {
						throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
					} catch (Exception e) {
						System.out.println("element is not found exception ...tried for : " + timeOut + " secs"
								+ " with the interval of : " + 500 + " ms");

					}

				}

				return element;

			}
			
			public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

				WebElement element = null;

				int attempts = 0;

				while (attempts < timeOut) {

					try {
						element = getElement(locator);
						System.out.println("element is found in attempt: " + attempts);
						break;
					} catch (NoSuchElementException e) {
						System.out.println("element is not found in attempt : " + attempts + " : " + " for " + locator);

						try {
							Thread.sleep(pollingTime);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					attempts++;

				}
				
				if (element == null) {
					try {
						throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
					} catch (Exception e) {
						System.out.println("element is not found exception ...tried for : " + timeOut + " secs"
								+ " with the interval of : " + pollingTime + " ms");

					}

				}

				return element;

			}

}
