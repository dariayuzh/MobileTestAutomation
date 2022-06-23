import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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


public class AdvancedAndroidTest {
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

    String articlesListName = "Programming languages";
    String firstArticleName = "Java (programming language)";
    String secondArticleName = "Python (programming language)";

    @Test
    public void saveTwoArticlesToList() {
        searchByWord("Java");
        clickArticle("Object-oriented programming language");
        addArticleToList(true, articlesListName);
        closeArticle(firstArticleName);

        searchByWord("Python");
        clickArticle("General-purpose programming language");
        addArticleToList(false, articlesListName);
        closeArticle(secondArticleName);

        openArticlesList(articlesListName);
        removeArticleFromList(firstArticleName);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + secondArticleName + "']"),
                "Cannot find in the list article title '" + secondArticleName + "'",
                5);
        assertArticleHasTitle(secondArticleName);
    }

    private void searchByWord(String searchWord) {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input", 5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchWord,
                "Cannot find search input for searching by word'" + searchWord + "'", 5);
    }

    private void clickArticle(String text) {
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + text + "']"),
                "Cannot find article with description '" + text + "'",
                5);
        waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title with description '" + text + "'",
                15);
    }

    private void addArticleToList(boolean createList, String listName) {
        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find More options button",
                5);
        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' option",
                5);
        if (createList) {
            waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                    "Cannot find 'GOT IT!' button while creating list",
                    5);
            waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                    "Cannot clear element 'My reading list' in new list title",
                    5);
            waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                    listName,
                    "Cannot name new article list",
                    5);
            waitForElementAndClick(By.xpath("//*[@text='OK']"),
                    "Cannot find 'OK' button",
                    5);
        } else {
            waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + listName + "']"),
                    "Cannot add second article to list " + listName,
                    5);
        }
    }

    private void closeArticle(String articleName) {
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close the article with the name " + articleName,
                15);
    }

    private void openArticlesList(String listName) {
        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot open lists of articles",
                15);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + listName + "']"),
                "Cannot find list with name " + listName,
                15);
    }

    private void removeArticleFromList(String articleName) {
        swipeElementToLeft(By.xpath("//*[@text='" + articleName + "']"),
                "Cannot find saved article with name '" + articleName + "' to remove");
        waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Article with the name '" + articleName + "'is not deleted from list",
                5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private void assertArticleHasTitle(String articleName) {
        WebElement element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title = '" + articleName + "'",
                20);
        Assert.assertEquals("Expected title is " + articleName + ", in fact title is " + element.getText(), element.getText(), articleName);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(150)
                .moveTo(leftX, middleY)
                .release()
                .perform();

    }
}
