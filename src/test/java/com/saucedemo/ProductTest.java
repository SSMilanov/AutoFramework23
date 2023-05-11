package com.saucedemo;

import base.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductTest extends TestUtil {
    private final static String PRODUCT_ID = "add-to-cart-sauce-labs-";// setting product ID variable for 3/4 of the present product Ids in the page.

    @Test (dataProvider = "items list")
    public void AddItemIntoCart(String itemName) {
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

        WebElement itemToBeAdded = driver.findElement(By.id(PRODUCT_ID + itemName));
        itemToBeAdded.click();

        WebElement shoppingCartBadge = driver.findElement(By.className("shopping_cart_badge"));

        Assert.assertEquals(shoppingCartBadge.getText(), "1");
    }
    @DataProvider (name = "items list")
    public Object[][] getItems(){
        return new Object[][]{
            {"bike-light"},
                {"bolt-t-shirt"}
        };
    }

}
