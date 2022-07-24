package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            CLOSE_SYNC_SUGGESTION,
            DELETE_ARTICLE_FROM_LIST_BUTTON,
            REMOVE_FROM_SAVED_BUTTON,
            REMOVE_FROM_SAVED_BUTTON_GENERAL,
            ARTICLE_BY_TITLE_TPL,
            AMOUNT_OF_ARTICLES_IN_LIST;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDERNAME}", folderName);
    }

    public static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    public int getAmountOfArticlesInList() {
        return getAmountOfElements(AMOUNT_OF_ARTICLES_IN_LIST);
    }

    public void openFolderByName(String folderName) {
        String folderNameXpath = getFolderXpathByName(folderName);
        System.out.println("folderNameXpath " + folderNameXpath);
        waitForElementAndClick(folderNameXpath,
                "Cannot find folder with name " + folderName,
                10);
    }

    public void closeSyncArticlesSuggestion() {
        waitForElementAndClick(CLOSE_SYNC_SUGGESTION,
                "Cannot close sync articles suggestion",
                10);
    }

    public void clickDeleteArticleFromListButton() {
        waitForElementAndClick(DELETE_ARTICLE_FROM_LIST_BUTTON,
                "Cannot find delete article button",
                10);
    }

    public void swipeByArticleToDelete() {
        if (Platform.getInstance().isMobileWeb()) {
            waitForElementAndClick(REMOVE_FROM_SAVED_BUTTON_GENERAL, "Cannot click button to remove article from saved", 5);
            driver.navigate().refresh();
        }
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            swipeElementToLeft(articleTitleXpath,
                    "Cannot find saved article with title " + articleTitle);
            if (Platform.getInstance().isIOS()) {
                clickDeleteArticleFromListButton();
            }

        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            waitForElementAndClick(removeLocator, "Cannot click button to remove article from saved", 5);
            driver.navigate().refresh();
            driver.navigate().refresh();
        }

        waitArticleToDisappearByTitle(articleTitle);

    }

    public void waitArticleToAppearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementPresent(articleTitleXpath,
                "Cannot find saved article by title " + articleTitle,
                15);
    }

    public void waitArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(getSavedArticleXpathByTitle(articleTitle));
        waitForElementNotPresent(articleTitleXpath,
                "Saved article with the title " + articleTitle + " is not deleted from folder",
                15);
    }

    public void clickArticleInList(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementAndClick(articleTitleXpath,
                "Cannot find and click saved article with the title " + articleTitle,
                5);
    }
}
