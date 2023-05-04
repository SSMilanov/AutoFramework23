import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.TestNGUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class FirstSeleniumTest  {
    public WebDriver driver;

    @AfterTest //annotation to close the driver and page never mind the test result.
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void successfulLoginSauceDemo() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        //driver.manage().window().fullscreen();  -- we can manage the driver after initializing it
        driver.get("https://saucedemo.com");

        //Starting with web element search for us to find the input place for username
        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.click();
        userNameInput.clear(); // important to clear after click so the space is confirmed to be empty/clear
        userNameInput.sendKeys("standard_user");

        //web element search for us to find the input place for password
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys("secret_sauce");

        WebElement loginBtn = driver.findElement(By.xpath("//input[@name='login-button']"));
        loginBtn.click();

        //Login validation searching for web elements to confirm that we successfully logged in.
        WebElement productsPageTitle = driver.findElement(By.cssSelector(".title"));
        WebElement prPageTitle = driver.findElement(By.className("title"));//it would be hard to find other elements within the class.
        WebElement prsPageTitle = driver.findElement(By.xpath("//span[@class='title']"));//same as above but with xpath.

        Assert.assertTrue(productsPageTitle.isDisplayed());

        WebElement menuBtn = driver.findElement(By.id("react-burger-menu-btn"));
        Assert.assertTrue(menuBtn.isEnabled());
        menuBtn.click();

        WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));

        //Never use thread.sleep for waiting!!!
        Thread.sleep(1000);
        Assert.assertTrue(logoutLink.isDisplayed());//Validating that the logoutLink is present.

    }
}
