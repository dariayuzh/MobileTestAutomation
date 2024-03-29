package lib.ui;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    public RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Step("Get amount of elements with specified locator")
    public int getAmountOfElements(String locator) {
        By by = getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    @Step("Get element attribute")
    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    @Step("Check if element presents on the page")
    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    @Step("Wait for element to be interactable and click it")
    public void tryClickElementWithAttempts(String locator, String errorMessage, int attempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;
        while (needMoreAttempts) {
            try {
                waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            }
            catch (Exception e) {
                if (currentAttempts > attempts) {
                    waitForElementAndClick(locator, errorMessage, 1);
                }
            }
            ++currentAttempts;
        }
    }

    @Step("Assert an element is not present")
    public void assertElementNotPresent(String locator, String errorMessage) {
        int amount = getAmountOfElements(locator);
        String defaultMessage = "An element '" + locator + "' supposed to be not present";
        if (amount > 0) {
            throw new AssertionError(defaultMessage + ", " + errorMessage);
        }
    }

    @Step("Wait for an element to present with specified timeout")
    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Step("Wait for an element to be clickable")
    public WebElement waitForElementToBeClickable(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    @Step("Wait for an element to be visible")
    public WebElement waitForElementToBeVisible(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Wait for an element to present")
    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    @Step("Wait for an element and click")
    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementToBeClickable(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    @Step("Wait for an element to appear and send keys")
    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    @Step("Wait for an element to not present")
    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Step("Wait for an element to appear and clear text")
    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    @Step("Swipe up")
    public void swipeUp(int timeOfSwipe) {
        TouchAction action;
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            action = new TouchAction(androidDriver);
        } else if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            action = new TouchAction(iosDriver);
        } else {
            System.out.println("Method swipeUp does nothing for platform " + Platform.getInstance().getPlatformVar());
            return;
        }
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        action
                .press(PointOption.point(x, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY))
                .release()
                .perform();
    }

    @Step("Swipe element to left")
    public void swipeElementToLeft(String locator, String errorMessage) {
        TouchAction action;
        if (Platform.getInstance().isAndroid()) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            action = new TouchAction(androidDriver);
        } else if (Platform.getInstance().isIOS()) {
            IOSDriver iosDriver = (IOSDriver) driver;
            action = new TouchAction(iosDriver);
        } else {
            System.out.println("Method swipeElementToLeft does nothing for platform " + Platform.getInstance().getPlatformVar());
            return;
        }
        WebElement element = waitForElementPresent(locator, errorMessage, 10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();

    }

    @Step("Scroll page up (for Mobile Web)")
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMobileWeb()) {
            driver.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swipe page till element is visible")
    public void scrollWebPageTillElementVisible(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        WebElement element = waitForElementPresent(locator, errorMessage);
        while (!isElementLocatedOnScreen(locator)) {
            scrollWebPageUp();
            alreadySwiped ++;
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    @Step("Swipe up quick")
    public void swipeUpQuick() {
        swipeUp(200);
    }

    @Step("Swipe up till elements appear")
    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        By by = getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up\n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            alreadySwiped++;
        }
    }

    @Step("Swipe up till element appear")
    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        By by = getLocatorByString(locator);
        int alreadySwiped = 0;
        while (!isElementLocatedOnScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, isElementLocatedOnScreen(locator));
            }
            swipeUpQuick();
            alreadySwiped++;
        }

    }

    @Step("Check if element is located on the screen")
    public boolean isElementLocatedOnScreen(String locator) {
        int elementLocationY = waitForElementPresent(locator, "Cannot find element by locator",
                5).getLocation().getY();
        if (Platform.getInstance().isMobileWeb()) {
            Object jsREsult = driver.executeScript("return window.pageYOffset");
            elementLocationY = Integer.parseInt(jsREsult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationY < screenSizeByY;
    }

    @Step("Get locator by type and name")
    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        switch (byType) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            case "css":
                return By.cssSelector(locator);
            default:
                throw new IllegalArgumentException("Cannot get type of locator. Locator = " + byType);
        }
    }

    @Step("Take screenshot")
    public String takeScreenshot(String name) {
        TakesScreenshot ts = driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        }
        catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        }
        catch (IOException e) {
            System.out.println("cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }

}
