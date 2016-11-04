package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.utils.Helpers;

public class LoginPage {

    private WebDriver driver;
    Helpers helpers = new Helpers();

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(){
        driver.get("http://soft.it-hillel.com.ua:8080/login.jsp");
    }

    public void enterLogin(String login){
        helpers.waitForVisibilityByXpath(driver, "//input[@id='login-form-username']");
        driver.findElement(By.xpath("//input[@id='login-form-username']")).sendKeys(login);
    }

    public void enterPassword(String password){
        driver.findElement(By.xpath("//input[@id='login-form-password']")).sendKeys(password);
    }

    public void clickSubmit(){
        driver.findElement(By.xpath("//input[@id='login-form-submit']")).click();
    }
}
