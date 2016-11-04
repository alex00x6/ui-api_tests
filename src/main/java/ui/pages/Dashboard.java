package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Storm on 27.10.2016.
 */
public class Dashboard {

    private WebDriver driver;

    public Dashboard(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(){
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
    }

    public void clickCreate(){driver.findElement(By.xpath("//*[@id=\"create_link\"]")).click();}


}
