package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testAssertTitle() {
        // ex6
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("olympic games");
        searchPageObject.clickArticleWithSubstring("Major international sport event");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        // changed initial test: now we are waiting for the title to appear
        String title = articlePageObject.getArticleTitle();
        assertNotNull("Title does not exist on the page!", title);
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForElementTitle();
        String articleTitle = articlePageObject.getArticleTitle();
        assertEquals("We see unexpected title",
                "Java (programming language)",
                articleTitle);

    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForElementTitle();
        articlePageObject.swipeToFooter();
    }
}
