package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            EXISTED_LIST_TITLE_TPL;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public static String getExistedListXpathByName(String folderName) {
        return EXISTED_LIST_TITLE_TPL.replace("{LIST_NAME}", folderName);
    }

    @Step("Wait for article to be opened by searching the title (Android & Web) or the footer (iOS)")
    public WebElement waitForArticleIsOpened() {
        if ((Platform.getInstance().isAndroid() || Platform.getInstance().isMobileWeb())) {
            return waitForElementTitle();
        } else {
            return waitForFooterAppear();
        }
    }

    @Step("Wait for the article title to appear")
    public WebElement waitForElementTitle() {
        return waitForElementPresent(TITLE, "Cannot find article title on the page!", 20);
    }

    @Step("Get article title")
    public String getArticleTitle() {
        WebElement title = waitForElementTitle();
        screenshot(takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title.getAttribute("name");
        } else {
            return title.getText();
        }
    }

    @Step("Swipe article till footer appear")
    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of the article!", 20);
        } else if (Platform.getInstance().isIOS()) {
            swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of the article!", 20);
        } else if (Platform.getInstance().isMobileWeb()) {
            scrollWebPageTillElementVisible(FOOTER_ELEMENT, "Cannot find the end of article", 20);
        }
    }

    @Step("Wait for article footer to appear")
    public WebElement waitForFooterAppear() {
        return waitForElementPresent(FOOTER_ELEMENT, "Cannot find the footer of the article - article is not opened!",
                10);
    }

    @Step("Add article to list")
    public void addArticleToMySaved() {
        if (Platform.getInstance().isMobileWeb()) {
            removeArticleFromSavedAndAddAgain();
        } else {
            waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add article to saved list", 10);
        }
    }

    @Step("Add article to newly created list")
    public void addArticleToNewList(String folderName) {
        if (Platform.getInstance().isAndroid()) {
            waitForElementAndClick(OPTIONS_BUTTON,
                    "Cannot find More options button",
                    5);
            waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find 'Add to reading list' option",
                    10);
            waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                    "Cannot find 'GOT IT!' button",
                    5);
            waitForElementAndClear(MY_LIST_NAME_INPUT,
                    "Cannot clear element 'My reading list'",
                    5);
            waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                    folderName,
                    "Cannot put text into article folder input",
                    5);
            waitForElementAndClick(MY_LIST_OK_BUTTON,
                    "Cannot find 'OK' button",
                    5);
        } else {
            addArticleToMySaved();
        }

    }

    @Step("Remove article from list if and add again (for Mobile Web)")
    public void removeArticleFromSavedAndAddAgain() {
        if (isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click button to remove article from saved", 5);
        }
        waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add article to saved after removing", 5);
    }

    @Step("Add article to existed list")
    public void addArticleToExistedList(String folderName) {
        if (Platform.getInstance().isAndroid()) {
            waitForElementAndClick(OPTIONS_BUTTON,
                    "Cannot find More options button",
                    5);
            waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find 'Add to reading list' option",
                    10);
            System.out.println("getExistedListXpathByName(folderName) = " + getExistedListXpathByName(folderName));
            waitForElementAndClick(getExistedListXpathByName(folderName),
                    "Cannot add article to list " + folderName,
                    10);
        }
        else {
            addArticleToMySaved();
        }
    }

    @Step("Close article")
    public void closeArticle() {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                    "Cannot close the article",
                    5);
        }
    }

}
