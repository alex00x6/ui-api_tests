package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.Helpers;

public class LoginPage {

    private WebDriver driver;
    Helpers helpers = new Helpers();
    private final String url_login = "http://soft.it-hillel.com.ua:8080/login.jsp";
    private final String xpath_login_username_form = "//input[@id='login-form-username']";
    private final String xpath_login_password_form = "//input[@id='login-form-password']";
    private final String xpath_login_submit_button = "//input[@id='login-form-submit']";

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }


    @Step("open login page")
    public void openPage(){
        driver.get(url_login);
    }


    @Step("enter login")
    public void enterLogin(String login){
        helpers.waitForVisibilityByXpath(driver, xpath_login_username_form);
        driver.findElement(By.xpath(xpath_login_username_form)).sendKeys(login);
    }


    @Step("enter password")
    public void enterPassword(String password){
        driver.findElement(By.xpath(xpath_login_password_form)).sendKeys(password);
    }


    @Step("click submit")
    public void clickSubmit(){
        driver.findElement(By.xpath(xpath_login_submit_button)).click();
    }

    public void completeLogin(String login, String password){
        openPage();
        enterLogin(login);
        enterPassword(password);
        clickSubmit();
    }
}