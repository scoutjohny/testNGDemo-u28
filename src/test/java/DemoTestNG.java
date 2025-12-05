import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class DemoTestNG {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        Thread.sleep(2000);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void loginToSauceDemo(){
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.findElement(By.cssSelector(".title")).getText(),"Products");
    }

    @Test
    public void loginWithNoUsername(){

        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        softAssert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
        softAssert.assertAll();
    }

    @Test
    public void loginWithNoPassword(){

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        softAssert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Password is required");
        softAssert.assertAll();
    }

    @Test
    public void loginWithNoCredentials(){

        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        softAssert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
        softAssert.assertAll();
    }

    @Test
    public void loginWithWrongUsername(){
        driver.findElement(By.id("user-name")).sendKeys("standard_user1");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        softAssert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
        softAssert.assertAll();
    }

    @Test
    @Parameters({"imeParametra"})
    public void loginWithWrongPassword(@Optional String imeParametra){
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce1");
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        softAssert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
        softAssert.assertAll();
    }


}
