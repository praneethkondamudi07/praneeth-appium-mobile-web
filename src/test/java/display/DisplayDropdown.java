package display;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class DisplayDropdown {
	
	int invocationCount;
	public AndroidDriver<AndroidElement> driver;
	
    @Parameters({ "invocationCount" })
    @BeforeClass
    void BeginClass( @Optional("1") String invocationCount) throws MalformedURLException {
        this.invocationCount = Integer.parseInt(invocationCount);
        DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    }

    // It will return a 2D array of size 3x1
    @DataProvider(name="results_per_page")
    private Object[][] getURLs() {
    	return new Object[][] {
    	    {"4"},
    	    {"8"},
    	    {"12"}
    	  };
    }

    /* Since Data provider for this test method returns 2D array of size
     (3*invocationCount)x1, this test method will run 3*invocationCount 
     times **automatically** with 1 parameter every time. */
    @Test(dataProvider="results_per_page")
    private void notePrice(String results_per_page) throws InterruptedException {
    	driver.navigate().to("http://demowebshop.tricentis.com/login");
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='icon']")).click();
		driver.findElement(By.xpath("//ul[@class='mob-top-menu show']//a[contains(text(),'Electronics')]")).click();
		driver.findElement(By.partialLinkText("Camera")).click();
		Thread.sleep(4000);
		Select displayDropdown = new Select(driver.findElement(By.xpath("//select[@id='products-pagesize']")));
		displayDropdown.selectByVisibleText(results_per_page);
		Thread.sleep(4000);
		Select sortnew = new Select(driver.findElement(By.xpath("//select[@id='products-pagesize']")));
		System.out.println(sortnew.getFirstSelectedOption().getText());
		Assert.assertEquals(sortnew.getFirstSelectedOption().getText(), results_per_page,
				"Failed to validate dispaly filter as " + results_per_page);
    }
    
    @AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
