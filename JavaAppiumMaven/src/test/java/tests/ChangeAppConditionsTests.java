package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Epic("Tests for changing conditions while app is running")
public class ChangeAppConditionsTests extends CoreTestCase {
    @Test
    @Features(value = {@Feature(value = "Article"), @Feature(value = "Conditions changing")})
    @DisplayName("Assert article has expected title after changing screen orientation")
    @Description("Open article 'Java' and make sure that title is not changed after changing orientation")
    @Step("Starting test testChangeScreenOrientation")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testChangeScreenOrientation() {
        if (Platform.getInstance().isMobileWeb()) return;
        String searchLine = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLanscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        assertEquals("title changed!", titleAfterRotation, titleBeforeRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        assertEquals("title changed!", titleAfterRotation, titleAfterSecondRotation);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Conditions changing")})
    @DisplayName("Assert search result contains the same result after running in background")
    @Description("Search by word 'Java' and make sure that search results are the same after running in background")
    @Step("Starting test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCheckSearchArticleInBackground() {
        if (Platform.getInstance().isMobileWeb()) return;
        String searchLine = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
