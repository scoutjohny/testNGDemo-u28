import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.plaf.TableHeaderUI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementiICekanja {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        //Grubo čekanje - zamrzava program na određeni broj milisekundi
//        Thread.sleep(2000);

        driver.manage().window().maximize();
        //Implicitno čekanje - dinamički čeka da se svaki element učita odreženi broj sekundi
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://demoqa.com/select-menu");
    }

    @AfterMethod
    public void tearDown(){
            driver.quit();
    }

    @Test
    public void demoQaSelectAndWaitTest() throws InterruptedException {
        //Eksplicitno čekanje - dinamički čeka da se odeđeni elemnt učita određeni broj sekundi
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#oldSelectMenu")));

        //SELECT - Padajući meni
        Select select = new Select(driver.findElement(By.cssSelector("#oldSelectMenu")));
        select.selectByVisibleText("White");

        //Radio dugmići - mogu da se obeleže ali ne mogu da se odobeleže
        driver.get("https://demoqa.com/radio-button");
        if(!driver.findElement(By.cssSelector("#yesRadio")).isSelected()){
            driver.findElement(By.cssSelector("[for='yesRadio']")).click();
        }

        Assert.assertEquals(driver.findElement(By.cssSelector(".mt-3")).getText(),"You have selected Yes");

        //Checkbox - Suštinski isti element kao i radio dugme ali on može da se odobeleži (odčekira)
        driver.get("https://demoqa.com/checkbox");
        if(!driver.findElement(By.cssSelector(".rct-checkbox")).isSelected()){
            driver.findElement(By.cssSelector(".rct-checkbox")).click();
        }

        //Otvaranje novog tab-a preko JavaScript-a
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");

        //Menjanje tab-ova preko liste otvorenih tabova
        js.executeScript("window.open()");
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(1));
        driver.get("https://www.google.rs");
        Thread.sleep(1500);
        js.executeScript("window.open()");
        List<String> windowHandles1 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles1.get(2));
        driver.get("https://www.tehnomanija.rs");
        Thread.sleep(1000);
        driver.switchTo().window(windowHandles1.get(1));
        driver.close();
        Thread.sleep(1000);
        List<String> windowHandles2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles2.get(2));

        //Logika za otvaranje više tabova i odlazak na URL na svakom od njih
        for(int i=0; i<10; i++){
            js.executeScript("window.open()");
            List<String> windowHandles3 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(windowHandles3.get(i+1));
            driver.get("https://www.gigatron.rs");
        }
    }

    @Test
    public void alert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(1000);
        driver.switchTo().alert().accept();

        driver.findElement(By.cssSelector("#confirmButton")).click();
        Thread.sleep(1000);
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText, "Do you confirm action?");
        driver.switchTo().alert().accept();

        driver.findElement(By.cssSelector("#promtButton")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.switchTo().alert().getText(), "Please enter your name");
        driver.switchTo().alert().sendKeys("Test");
        Thread.sleep(500);
        driver.switchTo().alert().accept();
    }
}
