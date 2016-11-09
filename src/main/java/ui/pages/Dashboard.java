package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Storm on 27.10.2016.
 */
public class Dashboard {

    private WebDriver driver;
    private final String url_dashboard = "http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa";
    private final String xpath_dashboard_create_button = "//*[@id=\"create_link\"]";

    public Dashboard(WebDriver driver){
        this.driver = driver;
    }

    @Step("open dashboard page")
    public Dashboard openPage(){
        this.driver.get(url_dashboard);
        return this;
    }

    @Step("click create issue button")
    public void clickCreate(){driver.findElement(By.xpath(xpath_dashboard_create_button)).click();}


}
