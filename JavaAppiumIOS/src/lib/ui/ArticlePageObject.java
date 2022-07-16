package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    public static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTED_LIST_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public static String getExistedListXpathByName(String folderName) {
        return EXISTED_LIST_TITLE_TPL.replace("{LIST_NAME}", folderName);
    }

    public WebElement waitForElementTitle() {
        return waitForElementPresent(TITLE, "Cannot find article title on the page!", 20);
    }

    public String getArticleTitle() {
        WebElement title = waitForElementTitle();
        return title.getAttribute("text");
    }

    public void swipeToFooter() {
        swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of the article!", 20);
    }

    public void addArticleToNewList(String folderName) {
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
    }

    public void addArticleToExistedList(String folderName) {
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

    public void closeArticle() {
        waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Cannot close the article",
                5);
    }

}
