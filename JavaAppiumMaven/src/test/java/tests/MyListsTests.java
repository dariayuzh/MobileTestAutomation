package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String folderName = "Learning programming";
    private static final String
            login = "Daria Yuzh",
            password = "P6jWfRuhVSswpqD";


    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String articleTitle = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToNewList(folderName);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMobileWeb()) {
            AuthorizationPageObject authPageObject = new AuthorizationPageObject(driver);
            authPageObject.clickAuthButton();
            authPageObject.enterLoginData(login, password);
            authPageObject.submitForm();

            articlePageObject.waitForElementTitle();

            assertEquals("We are not on the same page after login", articleTitle, articlePageObject.getArticleTitle());
        }
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            articlePageObject.closeArticle();
        }

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        } else if (Platform.getInstance().isIOS()) {
            myListsPageObject.closeSyncArticlesSuggestion();
        }
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToList() throws InterruptedException {
        // ex 5, 11
        String folderName = "Programming languages";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForElementTitle();
            articlePageObject.addArticleToNewList(folderName);
        } else {
            articlePageObject.waitForFooterAppear();
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        Thread.sleep(1000);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.clickArticleWithSubstring("General-purpose programming language");


        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForElementTitle();
            articlePageObject.addArticleToExistedList(folderName);
        } else {
            articlePageObject.waitForFooterAppear();
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        navigationUI.clickMyLists();
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        } else {
            myListsPageObject.closeSyncArticlesSuggestion();
        }

        int amountOfArticles = myListsPageObject.getAmountOfArticlesInList();
        assertEquals("Expected amount of articles in list = 2, actual = " + amountOfArticles,
                2, amountOfArticles);
        myListsPageObject.swipeByArticleToDelete("Python (programming language)");
        amountOfArticles = myListsPageObject.getAmountOfArticlesInList();
        assertEquals("Expected amount of articles after deleting from list = 1, actual = " + amountOfArticles,
                1, amountOfArticles);
    }
}
