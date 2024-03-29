package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESC_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_LABEL;

    public SearchPageObject(RemoteWebDriver driver) {
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

    @Step("Initializing the search field")
    public void initSearchInput() {
        waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", 10);
        waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Send keys to the search field")
    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 15);
    }

    @Step("Wait for search result to appear")
    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementPresent(searchResultXpath, "Cannot find search result with substring " + substring);
    }

    @Step("Choose and click article with specified substring")
    public void clickArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Wait for cancel button to appear")
    public void waitForCancelButtonToAppear() {
        waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Wait for cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Click button to cancel search")
    public void clickCancelSearch() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Get amount of articles in search result")
    public int getAmountOfFoundArticles() {
        waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request " + SEARCH_RESULT_ELEMENT,
                15);
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for empty search result")
    public void waitForEmptyResultsLabel() {
        waitForElementPresent(SEARCH_EMPTY_RESULT_LABEL,
                "Cannot find empty result label",
                15);
    }

    @Step("Assert that search result is empty")
    public void assertEmptyResultOfSearch() {
        assertElementNotPresent(SEARCH_RESULT_ELEMENT,
                "Some elements were found by request!");
    }

    @Step("Wait for search result with specified title and description")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getResultSearchElement(title, description);
        waitForElementPresent(searchResultXpath, "Cannot find search result with title " + title + " and description " + description);
    }


}
