import static org.junit.Assert.fail;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Use Case 4: Licensing manager can add data about applicant to the system.
 */
public class AddLicenseSeleniumTest {
    private WebDriver driver;
    private String host = System.getProperty("hostName");
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    public static final String USERNAME = "aivarl";
    public static final String ACCESS_KEY = "03e23a94-5f50-49d7-a305-94e5bb49fc8e";
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    @Before
    public void setUp() throws Exception {
        if (host != null && host.equals("localhost")) {
            baseUrl = "http://localhost:8080/";
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        else{
            baseUrl = "http://license-management.herokuapp.com/";
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            caps.setCapability("platform", "Linux");
            caps.setCapability("version", "42.0");
            driver = new RemoteWebDriver(new URL(URL), caps);
        }
    }

    @Test
    public void runnableMethod(){
    }
//    @Test
//    public void testAddLicenseWebDriver() throws Exception {
//        java.util.Date date = new java.util.Date();
//        String ts = new Timestamp(date.getTime()).toString();
//        driver.get(baseUrl + "#/");
//        driver.findElement(By.linkText("Start licensing process")).click();
//        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
//        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("e");
//        driver.findElement(By.linkText("Example University")).click();
//        driver.findElement(By.cssSelector("span.input-group-btn > button.btn.btn-default")).click();
//        new Select(driver.findElement(By.id("product"))).selectByVisibleText("i-Voting");
//        driver.findElement(By.cssSelector("div.form-group > button.btn.btn-default")).click();
//        driver.findElement(By.id("contractNumber")).clear();
//        driver.findElement(By.id("contractNumber")).sendKeys(ts);
//        new Select(driver.findElement(By.id("state"))).selectByVisibleText("WAITING_FOR_SIGNATURE");
//        driver.findElement(By.id("addLicenseSubmit")).click();
//
//        // Check if created license exists
//        driver.findElement(By.linkText("Licenses")).click();
//        driver.findElement(By.linkText("View licenses")).click();
//        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + ts + "')]"));
//        Assert.assertTrue("License not found!", list.size() > 0);
//    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
