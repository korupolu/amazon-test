package annotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.core.SubstringMatcher;

import cucumber.api.java.en.Given; 
import cucumber.api.java.en.Then; 
import cucumber.api.java.en.When; 

public class AmazonTest {
	  WebDriver driver = null; 
	   @Given("^User opened Amazon home page \"(.*)\"$") 
	   public void goToAmazonHomePage(String homepageUrl) { 
	      driver = new ChromeDriver(); 
	      driver.navigate().to(homepageUrl); 
	   }
		
	   @When("^I enter search text as \"(.*)\"$") 
	   public void eneterSearchItem(String searchItem) {   
		   //wait till search box displayed.
		   WebElement searchElement = (new WebDriverWait(driver, AmazonConstants.MAX_WAIT_TIME))
				   .until(ExpectedConditions.presenceOfElementLocated(By.id(AmazonConstants.SEARCH_XPATH_BY_ID)));
	       //send search string
		   searchElement.sendKeys(searchItem);
		   takeScreenShot();
		   //press enter key for search
		   searchElement.sendKeys(Keys.ENTER);
	   }
		
	   @When ("^I sorted results from highest price to slowest$") 
	   public void sortResults() {
		   //wait till sort box displayed.
		   WebElement sortElement = (new WebDriverWait(driver, AmazonConstants.MAX_WAIT_TIME))
				   .until(ExpectedConditions.presenceOfElementLocated(By.id(AmazonConstants.SORT_XAPTH_BY_ID)));
		   //select the sort option
		   Select sortSelect=new Select(sortElement);
		   sortSelect.selectByIndex(3);
		   takeScreenShot();
	   } 
		
	   @Then("^Second product topic should contains text \"(.*)\"$")
	   public void verifyTestResult(String expected) {  
		   //wait till updated results displayed.
		   WebElement secondResult = (new WebDriverWait(driver, AmazonConstants.MAX_WAIT_TIME))
				   .until(ExpectedConditions.presenceOfElementLocated(By.id(AmazonConstants.SECOND_RESULT_XPATH_BY_ID)));
		   //verify that second result contains expected text
		   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondResult);
		   takeScreenShot();
		   try
		   {
			   assertThat(secondResult.getText(), containsString(expected));
		   }
		   catch(Exception ex)
		   {
		   }
		   finally
		   {
			   driver.close();
		   }
	       
	   } 
		
	   /*
	    * Function to take browser screenshot
	    */
	   public void takeScreenShot()  {
		   try
		   {
		    File scr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.jpg'").format(new Date());
		    File dest = new File("filePath/" + filename);
		    FileUtils.copyFile(scr, dest);
		   }
		   catch(Exception ex)
		   {
			   System.out.println("Exception while taking screenshot" + ex.getMessage());
			   ex.printStackTrace();
		   }
		}
}
 