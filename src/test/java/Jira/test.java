package Jira;

/**
 * Created by Storm on 08.11.2016.
 */
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class test {
    @Test
    public void docTest(){
        URL hostURL = null;
        try {
            hostURL = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome" );
        capability.setPlatform(Platform.LINUX);
        WebDriver driver = new RemoteWebDriver(hostURL, capability);
        //driver = WebDriverFactory.getDriver(gridHubUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("https://google.com");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Random rn = new Random();
        int randInt = rn.nextInt();
        //try {
          //  FileUtils.copyFile(screen, new File("F:\\DockerTest\\" + randInt + ".png"));
       // } catch (IOException e) {
       //     e.printStackTrace();
        //}

        System.out.println("AAAA");
        driver.quit();
    }

}