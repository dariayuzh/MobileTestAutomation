package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    public static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTED_LIST_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public static String getExistedListXpathByName(String folderName) {
        return EXISTED_LIST_TITLE_TPL.replace("{LIST_NAME}", folderName);
    }

    public WebElement waitForElementTitle() {
        return waitForElementPresent(By.id(TITLE), "Cannot find article title on the page!", 20);
    }

    public String getArticleTitle() {
        WebElement title = waitForElementTitle();
        return title.getAttribute("text");
    }

    public void swipeToFooter() {
        swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of the article!", 20);
    }

    public void addArticleToNewList(String folderName) {
        waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find More options button",
                5);
        waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' option",
                10);
        waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'GOT IT!' button",
                5);
        waitForElementAndClear(By.id(MY_LIST_NAME_INPUT),
                "Cannot clear element 'My reading list'",
                5);
        waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT),
                folderName,
                "Cannot put text into article folder input",
                5);
        waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find 'OK' button",
                5);
    }

    public void addArticleToExistedList(String folderName) {
        waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find More options button",
                5);
        waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' option",
                10);
        System.out.println("getExistedListXpathByName(folderName) = " + getExistedListXpathByName(folderName));
        waitForElementAndClick(By.xpath(getExistedListXpathByName(folderName)),
                "Cannot add article to list " + folderName,
                10);
    }

    public void closeArticle() {
        waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close the article",
                5);
    }

}
