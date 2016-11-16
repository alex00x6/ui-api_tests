package ui.utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Storm on 11.11.2016.
 */
public class DriverConfigurator {
    public static WebDriver createInstance(String browserName, Boolean useGrid) {
        URL hostURL = null;
        try {
            hostURL = new URL("http://127.0.0.1:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = null;
        WebDriver driver = null;
        if (useGrid) {
            if (browserName.toLowerCase().contains("firefox")) {
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                capability.setPlatform(Platform.LINUX);
            }
            if (browserName.toLowerCase().contains("internet")) {
                capability = DesiredCapabilities.internetExplorer();
            }
            if (browserName.toLowerCase().contains("chrome")) {
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName("chrome");
                capability.setPlatform(Platform.LINUX);
            }
            driver = new RemoteWebDriver(hostURL, capability);
        }
        else{
            if (browserName.toLowerCase().contains("firefox")) {
               driver = new FirefoxDriver();
                System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
            }
            if (browserName.toLowerCase().contains("internet")) {
                System.out.println("OOOOOPS! Not configured yet");
            }
            if (browserName.toLowerCase().contains("chrome")) {
                driver = new ChromeDriver();
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            }
        }


        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}
