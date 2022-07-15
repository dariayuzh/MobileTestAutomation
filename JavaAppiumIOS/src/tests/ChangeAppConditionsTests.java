package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionsTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientation() throws Exception {
        String searchLine = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLanscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        assertEquals("title changed!", titleAfterRotation, titleBeforeRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        assertEquals("title changed!", titleAfterRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground() throws Exception {
        String searchLine = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
