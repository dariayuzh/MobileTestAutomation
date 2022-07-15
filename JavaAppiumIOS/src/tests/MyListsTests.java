package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() throws Exception {
        String folderName = "Learning programming";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForElementTitle();
        String articleTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToNewList(folderName);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToList() throws Exception {
        // ex 5
        String folderName = "Programming languages";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        String articleToDeleteTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToNewList(folderName);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.clickArticleWithSubstring("General-purpose programming language");

        String articleToSaveTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToExistedList(folderName);
        articlePageObject.closeArticle();

        navigationUI.clickMyLists();

        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(articleToDeleteTitle);
        myListsPageObject.clickArticleInList(articleToSaveTitle);
        String articleInListTitle = articlePageObject.getArticleTitle();
        assertEquals("Title of saved article changed!", articleToSaveTitle, articleInListTitle);
    }
}
