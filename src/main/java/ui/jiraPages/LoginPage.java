package ui.jiraPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.DriverManager;
import ui.utils.Helpers;

public class LoginPage extends Helpers{

    private WebDriver driver;
    //Helpers helpers = new Helpers();
    private final String url_login = "http://soft.it-hillel.com.ua:8080/login.jsp";
    private final String xpath_login_username_form = "//input[@id='login-form-username']";
    private final String xpath_login_password_form = "//input[@id='login-form-password']";
    private final String xpath_login_submit_button = "//input[@id='login-form-submit']";
    private final String xpath_login_rememberMe_button = "//*[@id=\"login-form-remember-me\"]";

    public LoginPage(){
        this.driver = DriverManager.getDriver();
    }


    @Step("open login page")
    public LoginPage openPage(){
        this.driver.get(url_login);
        return this;
    }

    @Step("enter login")
    public void enterLogin(String login){
        waitForClickableByXpath(xpath_login_username_form);
        driver.findElement(By.xpath(xpath_login_username_form)).sendKeys(login);
    }

    @Step("enter password")
    public void enterPassword(String password){
        waitForClickableByXpath(xpath_login_password_form);
        driver.findElement(By.xpath(xpath_login_password_form)).sendKeys(password);
    }

    @Step("click remember me") //(ffs, why the fuck i need this? it worked fine without it.)
    public void clickRememberMe(){
        waitForClickableByXpath(xpath_login_rememberMe_button);
        driver.findElement(By.xpath(xpath_login_rememberMe_button)).click();
    }

    @Step("click submit")
    public void clickSubmit(){
        waitForClickableByXpath(xpath_login_submit_button);
        driver.findElement(By.xpath(xpath_login_submit_button)).click();
    }

    public void completeLogin(String login, String password){
        enterLogin(login);
        enterPassword(password);
        clickRememberMe();
        clickSubmit();
    }
}