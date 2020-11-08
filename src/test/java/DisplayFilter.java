import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.annotations.BeforeClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class DisplayFilter {
	public AndroidDriver<AndroidElement> driver;

	@Test
	public void verifySort() throws InterruptedException {
		driver.navigate().to("http://demowebshop.tricentis.com/login");
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='icon']")).click();
		driver.findElement(By.xpath("//ul[@class='mob-top-menu show']//a[contains(text(),'Electronics')]")).click();
		driver.findElement(By.partialLinkText("Camera")).click();
		Thread.sleep(4000);
		Select displayDropdown = new Select(driver.findElement(By.xpath("//select[@id='products-pagesize']")));
		displayDropdown.selectByVisibleText("12");
		Thread.sleep(4000);
		Select sortnew = new Select(driver.findElement(By.xpath("//select[@id='products-pagesize']")));
		System.out.println(sortnew.getFirstSelectedOption().getText());
		Assert.assertEquals(sortnew.getFirstSelectedOption().getText(), "12",
				"Failed to validate dispaly filter as 12");
	}

	@BeforeClass
	public void testSetupCapabilites() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS); 
	}

	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}