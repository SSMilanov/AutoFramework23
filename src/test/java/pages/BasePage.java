package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    //Constructor ->
    public BasePage(WebDriver driver){
        this.driver = driver;
    }
}
