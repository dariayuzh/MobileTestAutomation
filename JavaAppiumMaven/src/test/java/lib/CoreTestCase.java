package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase {
    protected RemoteWebDriver driver;
    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        createAllurePropertyFile();
        rotateScreenPortrait();
        skipWelcomePageIOS();
        openWikiWebPage();
    }

    @After
    @Step("Remove driver and session")
    public void tearDown() {
        driver.quit();
    }

    @Step("Rotate screen to portrait mode")
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

    @Step("Rotate screen to landscape mode")
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

    @Step("Send mobile app to background (does not work on mobile web)")
    protected void backgroundApp(int seconds) {
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.runAppInBackground(Duration.ofSeconds(seconds));
        }
    }

    @Step("Skip welcome screen for iOS")
    private void skipWelcomePageIOS() {
        if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            WelcomePageObject pageObject = new WelcomePageObject(iosDriver);
            pageObject.clickSkip();
        }
    }

    @Step("Open Wikipedia URL for Mobile Web (does not work on Android and iOS")
    protected void openWikiWebPage() {
        if (Platform.getInstance().isMobileWeb()) {
            driver.get("https://en.m.wikipedia.org");
        }
        else {
            System.out.println("Method openWikiWebPage does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    private void createAllurePropertyFile() {
        String path = System.getProperty("allure.results.directory");
        try {
            Properties properties = new Properties();
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/environment.properties");
            properties.setProperty("environment", Platform.getInstance().getPlatformVar());
            properties.store(fileOutputStream, "See https://github.com/allure-framework/allure-app/wiki/Environment");
            fileOutputStream.close();
        }
        catch (Exception e) {
            System.err.println("IO problem while writing allure properties file");
            e.printStackTrace();
        }
    }
}
