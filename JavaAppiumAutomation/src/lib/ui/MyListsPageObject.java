package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDERNAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDERNAME}", folderName);
    }

    public static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String folderName) {
        String folderNameXpath = getFolderXpathByName(folderName);
        System.out.println("folderNameXpath " + folderNameXpath);
        waitForElementAndClick(By.xpath(folderNameXpath),
                "Cannot find folder with name " + folderName,
                10);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        swipeElementToLeft(By.xpath(articleTitleXpath),
                "Cannot find saved article with title " + articleTitle);
        waitArticleToDisappearByTitle(articleTitle);
    }

    public void waitArticleToAppearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementPresent(By.xpath(articleTitleXpath),
                "Cannot find saved article by title " + articleTitle,
                15);
    }

    public void waitArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(getFolderXpathByName(articleTitle));
        waitForElementNotPresent(By.xpath(articleTitleXpath),
                "Saved article with the title " + articleTitle + " is not deleted from folder",
                15);
    }

    public void clickArticleInList(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementAndClick(By.xpath(articleTitleXpath),
                "Cannot find and click saved article with the title " + articleTitle,
                5);
    }
}
