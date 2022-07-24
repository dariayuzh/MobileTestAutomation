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

    @Override
    protected void tearDown() throws Exception {
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();
        int amountOfSavedArticles = myListsPageObject.getAmountOfArticlesInList();
        for (int i = 0; i < amountOfSavedArticles; i++) {
            myListsPageObject.swipeByArticleToDelete();
        }
        super.tearDown();
    }


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
    public void testSaveTwoArticlesToList() {
        // ex 5, 11, 17
        String folderName = "Programming languages";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForArticleIsOpened();
        String firstArticleName = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToNewList(folderName);

        if (Platform.getInstance().isMobileWeb()) {
            AuthorizationPageObject authPageObject = new AuthorizationPageObject(driver);
            authPageObject.clickAuthButton();
            authPageObject.enterLoginData(login, password);
            authPageObject.submitForm();

            articlePageObject.waitForElementTitle();

            assertEquals("We are not on the same page after login", firstArticleName, articlePageObject.getArticleTitle());
        }

        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.clickArticleWithSubstring("General-purpose programming language");

        articlePageObject.waitForArticleIsOpened();
        articlePageObject.addArticleToExistedList(folderName);

        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        navigationUI.openNavigation();
        navigationUI.clickMyLists();
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        } else if (Platform.getInstance().isIOS()) {
            myListsPageObject.closeSyncArticlesSuggestion();
        }

        int amountOfArticles = myListsPageObject.getAmountOfArticlesInList();
        assertEquals("Expected amount of articles in list = 2, actual = " + amountOfArticles,
                2, amountOfArticles);
        myListsPageObject.swipeByArticleToDelete("Python (programming language)");
        navigationUI.waitForMyListPageIsOpened();
        amountOfArticles = myListsPageObject.getAmountOfArticlesInList();
        assertEquals("Expected amount of articles after deleting from list = 1, actual = " + amountOfArticles,
                1, amountOfArticles);
    }
}
