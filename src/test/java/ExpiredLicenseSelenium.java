import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
 * Use Case 2: The system reminds the licensing manager in time of expiring licenses.
 */
public class ExpiredLicenseSelenium {
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
        Date now = new java.util.Date();
        String ts = new Timestamp(now.getTime()).toString();
        String validTill = addDays(now, 15);

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
        driver.findElement(By.id("validFrom")).sendKeys("1900-01-01");
        driver.findElement(By.id("validTill")).clear();
        driver.findElement(By.id("validTill")).sendKeys(validTill);
        driver.findElement(By.id("addLicenseSubmit")).click();
        //Check if license is expiring
        driver.findElement(By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul[2]/li[2]/a/span/i[2]")).click();
        //TODO Add a check based on id instead of validFrom/validTill dates
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "1900-01-01" + "')]"));
        Assert.assertTrue("License not found!", list.size() > 0);
        List<WebElement> list2 = driver.findElements(By.xpath("//*[contains(text(),'" + validTill + "')]"));
        Assert.assertTrue("License not found!", list2.size() > 0);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public static String addDays(Date date, int days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return sdf.format(cal.getTime());
    }
}
