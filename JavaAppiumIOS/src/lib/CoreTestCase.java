package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        rotateScreenPortrait();
        skipWelcomePageIOS();
    }

    @Override
    protected void tearDown() throws Exception {
        super.setUp();
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    protected void rotateScreenLanscape() {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.rotate(ScreenOrientation.LANDSCAPE);
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
            WelcomePageObject pageObject = new WelcomePageObject(driver);
            pageObject.clickSkip();
        }
    }
}
