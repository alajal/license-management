import static org.junit.Assert.fail;

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
import org.openqa.selenium.support.ui.Select;
/**
 * Use Case 4: Licensing manager can add data about applicant to the system.
 */
public class AddLicenseSelenium {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAddLicenseWebDriver() throws Exception {
        java.util.Date date = new java.util.Date();
        String ts = new Timestamp(date.getTime()).toString();
        driver.get(baseUrl + "#/");
        driver.findElement(By.linkText("Start licensing process")).click();
        driver.findElement(By.id("nameOrganization")).clear();
        driver.findElement(By.id("nameOrganization")).sendKeys(ts);
        driver.findElement(By.id("applicationArea")).clear();
        driver.findElement(By.id("applicationArea")).sendKeys("ApplicationAreaTest");
        driver.findElement(By.id("nameContact")).clear();
        driver.findElement(By.id("nameContact")).sendKeys("NameTest");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("email@test");
        driver.findElement(By.id("skype")).clear();
        driver.findElement(By.id("skype")).sendKeys("SkypeTest");
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("PhoneTest");
        driver.findElement(By.cssSelector("div.form-group > button.btn.btn-default")).click();
        new Select(driver.findElement(By.id("product"))).selectByVisibleText("i-Voting 11.2");
        driver.findElement(By.cssSelector("form[name=\"chooseProductForm\"] > div.form-group > button.btn.btn-default"))
                .click();
        driver.findElement(By.id("validFrom")).clear();
        driver.findElement(By.id("validFrom")).sendKeys("2020-10-15");
        driver.findElement(By.id("validTill")).clear();
        driver.findElement(By.id("validTill")).sendKeys("2020-10-15");
        driver.findElement(By.id("addLicenseSubmit")).click();
        //Check if created license exists
        driver.findElement(By.linkText("Licenses")).click();
        driver.findElement(By.linkText("View licenses")).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + ts + "')]"));
        Assert.assertTrue("License not found!", list.size() > 0);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
