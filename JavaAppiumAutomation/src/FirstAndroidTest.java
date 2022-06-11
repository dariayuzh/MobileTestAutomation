import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstAndroidTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:/MyProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkInputFieldText() {
        assertElementHasText(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView"),
                "Search Wikipedia",
                "Cannot find text Search Wikipedia in input line");
    }

    @Test
    public void findArticlesAndCancelSearch() {
        searchByWord("Summer");

        List<WebElement> searchResult = waitForListOfElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find articles by word Summer", 10);
        Assert.assertTrue("Cannot find several articles by word Summer", searchResult.size() > 1);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5);

        waitForElementNotPresent(By.id("org.wikipedia:id/search_results_list"),
                "Articles are still on the page", 10);

    }

    @Test
    public void checkSearchResults() {
        searchByWord("Summer");
        driver.navigate().back();
        List<WebElement> searchResult = waitForListOfElementsPresent(By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find articles by word Summer", 10);
        for (WebElement element : searchResult) {
            assertElementContainsText(element, "Summer", "Cannot find word Summer in article name = " + element.getText());
        }

    }

    private void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = driver.findElement(by);
        String elementText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, elementText);
    }

    private void assertElementContainsText(WebElement element, String expectedText, String errorMessage) {
        String elementText = element.getText();
        Assert.assertTrue(errorMessage, elementText.contains(expectedText));
    }

    private void waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
    }

    private void waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
    }

    private List<WebElement> waitForListOfElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(d -> driver.findElements(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private void searchByWord(String word) {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input", 5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), word,
                "Cannot find search input", 5);
    }

}
