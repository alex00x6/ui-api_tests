package ui.utils;

import org.openqa.selenium.WebDriver;

/**
 * Created by Storm on 11.11.2016.
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
}