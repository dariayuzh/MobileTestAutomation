package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private final static String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{TITLE}']",
            SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESC_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/*[android.widget.TextView[@text='{TITLE}'] and android.widget.TextView[@text='{DESCRIPTION}']]",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_LABEL = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String title) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{TITLE}", title);
    }

    private static String getResultSearchElement(String title, String description) {
        return SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESC_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
        waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input", 15);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring " + substring);
    }

    public void clickArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with substring " + substring, 10);
    }

    public void waitForCancelButtonToAppear() {
        waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by request " + SEARCH_RESULT_ELEMENT,
                15);
        return getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_LABEL),
                "Cannot find empty result label",
                15);
    }

    public void assertEmptyResultOfSearch() {
        assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Some elements were found by request!");
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getResultSearchElement(title, description);
        waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with title " + title + " and description " + description);
    }


}
