package com.saucedemo;

import base.TestUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class FirstSeleniumTest extends TestUtil {

    @Test (dataProvider = "correctUsers")
    public void successfulLoginSauceDemo(String username, String password) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //driver.manage().window().fullscreen();  -- we can manage the driver after initializing it
        driver.get("https://saucedemo.com");

        //Starting with web element search for us to find the input place for username
        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.click();
        userNameInput.clear(); // important to clear after click so the space is confirmed to be empty/clear
        userNameInput.sendKeys(username);

        //web element search for us to find the input place for password
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//input[@name='login-button']"));
        loginBtn.click();
        //Thread.sleep(5000);

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
        //Thread.sleep(1000);
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));//explicit wait -
        wait2.until(ExpectedConditions.visibilityOf(logoutLink));

        FluentWait fluentWait = new FluentWait(driver) //Fluent wait example - again Explicit wait
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoreAll(Collections.singleton(NoSuchElementException.class));// Ignore exception when it occurs
        fluentWait.until(ExpectedConditions.elementToBeClickable(logoutLink));

        Assert.assertTrue(logoutLink.isDisplayed());//Validating that the logoutLink is present.


    }

    @DataProvider (name = "wrongUsers")
    public Object [][] getUsers(){
        return  new Object[][]{
                {"wrongUsername", "secret_sauce"},
                {"standard_user", "wrongPassword"},
                {"wrong","wrong"}
        };
    }


    @Test(dataProvider = "wrongUsers")
    public void unsuccessfulLogin(String username, String password){

        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(username);


        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//input[@name='login-button']"));
        loginBtn.click();


        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed());

        //WebElement errorMessageText = driver.findElement(By.className("error-button"));
        // Assert.assertEquals(errorMessageText,"Epic sadface: Username and password do not match any user in this service");
    }

    @DataProvider(name = "correctUsers")
    public Object[][] readUsersFromCsv() {
        try {
            CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/correctUsers.csv"));
            List<String []> csvData = csvReader.readAll();
            Object [] [] csvDataObj = new Object[csvData.size()][2];

            for (int i = 0; i < csvData.size(); i++) {
                csvDataObj[i] = csvData.get(i);
            }
            return csvDataObj;

        } catch (IOException e) {
            System.out.println("Not Possible to find the file!");
            return null;
        } catch (CsvException e){
            return null;
        }

    }
}
