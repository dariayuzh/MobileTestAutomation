package lib.ui;

import io.appium.java_client.AppiumDriver;

public abstract class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESC_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_LABEL;

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

    public void initSearchInput() throws Exception {
        waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
        waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String searchLine) throws Exception {
        waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 15);
    }

    public void waitForSearchResult(String substring) throws Exception {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementPresent(searchResultXpath, "Cannot find search result with substring " + substring);
    }

    public void clickArticleWithSubstring(String substring) throws Exception {
        String searchResultXpath = getResultSearchElement(substring);
        waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public void waitForCancelButtonToAppear() throws Exception {
        waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() throws Exception {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() throws Exception {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() throws Exception {
        waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request " + SEARCH_RESULT_ELEMENT,
                15);
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() throws Exception {
        waitForElementPresent(SEARCH_EMPTY_RESULT_LABEL,
                "Cannot find empty result label",
                15);
    }

    public void assertEmptyResultOfSearch() throws Exception {
        assertElementNotPresent(SEARCH_RESULT_ELEMENT,
                "Some elements were found by request!");
    }

    public void waitForElementByTitleAndDescription(String title, String description) throws Exception {
        String searchResultXpath = getResultSearchElement(title, description);
        waitForElementPresent(searchResultXpath, "Cannot find search result with title " + title + " and description " + description);
    }


}
