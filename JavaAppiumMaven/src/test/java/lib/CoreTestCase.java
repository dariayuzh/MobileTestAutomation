package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase {
    protected RemoteWebDriver driver;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        rotateScreenPortrait();
        skipWelcomePageIOS();
        openWikiWebPage();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        else {
            System.out.println("Method rotate screen portrait does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void rotateScreenLanscape() {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.rotate(ScreenOrientation.LANDSCAPE);
        } else if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else {
            System.out.println("Method rotate screen landscape does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void backgroundApp(int seconds) {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.runAppInBackground(Duration.ofSeconds(seconds));
        }
    }

    private void skipWelcomePageIOS() {
        if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            WelcomePageObject pageObject = new WelcomePageObject(iosDriver);
            pageObject.clickSkip();
        }
    }

    protected void openWikiWebPage() {
        if (Platform.getInstance().isMobileWeb()) {
            driver.get("https://en.m.wikipedia.org");
        }
        else {
            System.out.println("Method openWikiWebPage does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
