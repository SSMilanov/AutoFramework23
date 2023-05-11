package testPOM;

import base.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

public class ProductTest extends TestUtil { //TestUtil provides the driver and initialization

    @Test
    public void addItemToTheCart(){
    LoginPage loginTest = new LoginPage(driver);
        ProductPage productPage = loginTest.login("standard_user", "secret_sauce");

        productPage.addItemToTheCart("bike-light");
        Assert.assertEquals(productPage.getItemsInCart(),1);

        productPage.addItemToTheCart("onesie");
        Assert.assertEquals(productPage.getItemsInCart(),2);

        productPage.removeItemFromTheCart("onesie");
    }
}
