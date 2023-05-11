package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage{
    private final static String PRODUCT_ID = "add-to-cart-sauce-labs-";
    private final static String REMOVE_PROD_ID = "remove-sauce-labs-";

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement userMenuBtn;

    @FindBy(id = "shopping_cart_container")
    private WebElement shoppingCart;


    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartBadge;



    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this); //initializing the web page
    }


    //Methods

    //Method for adding an item to the cart
    public void addItemToTheCart (String itemName){
        WebElement itemToBeAdded = driver.findElement(By.id(PRODUCT_ID + itemName));
        itemToBeAdded.click();
    }

    public int getItemsInCart(){
    return Integer.parseInt(shoppingCartBadge.getText());
    }

    public void removeItemFromTheCart (String itemName){
        WebElement itemToBeRemoved = driver.findElement(By.id(REMOVE_PROD_ID + itemName));
    }

}
