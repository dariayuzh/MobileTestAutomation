package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject {
    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDERNAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDERNAME}", folderName);
    }

    public static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String folderName) throws Exception {
        String folderNameXpath = getFolderXpathByName(folderName);
        System.out.println("folderNameXpath " + folderNameXpath);
        waitForElementAndClick(folderNameXpath,
                "Cannot find folder with name " + folderName,
                10);
    }

    public void swipeByArticleToDelete(String articleTitle) throws Exception {
        waitArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        swipeElementToLeft(articleTitleXpath,
                "Cannot find saved article with title " + articleTitle);
        waitArticleToDisappearByTitle(articleTitle);
    }

    public void waitArticleToAppearByTitle(String articleTitle) throws Exception {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementPresent(articleTitleXpath,
                "Cannot find saved article by title " + articleTitle,
                15);
    }

    public void waitArticleToDisappearByTitle(String articleTitle) throws Exception {
        String articleTitleXpath = getSavedArticleXpathByTitle(getFolderXpathByName(articleTitle));
        waitForElementNotPresent(articleTitleXpath,
                "Saved article with the title " + articleTitle + " is not deleted from folder",
                15);
    }

    public void clickArticleInList(String articleTitle) throws Exception {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementAndClick(articleTitleXpath,
                "Cannot find and click saved article with the title " + articleTitle,
                5);
    }
}
