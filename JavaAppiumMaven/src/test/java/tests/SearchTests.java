package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@Epic("Tests for searching")
public class SearchTests extends CoreTestCase {
    @Test
    @Feature(value = "Search")
    @DisplayName("Find several articles by search word 'Summer' and cancel it")
    @Description("Find several articles by search word 'Summer' and cancel it")
    @Step("Starting test testFindArticlesAndCancelSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testFindArticlesAndCancelSearch() {
        // ex 3
        String searchLine = "Summer";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int searchResultAmount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Cannot find several articles by word " + searchLine, searchResultAmount > 1);

        searchPageObject.clickCancelSearch();
        searchPageObject.assertEmptyResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Find search results by search word 'Java'")
    @Description("Find articles by word 'Java' and make sure expected article exists")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Click on search field and cancel it")
    @Description("Click on search field and make sure to return back on he main page after cancelling search")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check that search results exist")
    @Description("Search by line 'Linkin park discography' and make sure search result is not empty")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park discography";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Not enough elements found", amount > 0);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check that search result is empty")
    @Description("Search by line 'aszgfdhffggdf' and make sure search result is empty")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        String searchLine = "aszgfdhffggdf";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertEmptyResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search results by title and description")
    @Description("Search by line 'Apricot' and check search results by title and description")
    @Step("Starting test testCheckTitleAndDescription")
    @Severity(value = SeverityLevel.MINOR)
    public void testCheckTitleAndDescription() {
        String searchLine = "Apricot";
        HashMap<String, String> titleAndDescriptions = new HashMap<>();
        titleAndDescriptions.put("Apricot", "Cultivated fruit");
        titleAndDescriptions.put("Apricot Computers", "Known as Applied Computer Techniques until 1985, a computer services company");
        titleAndDescriptions.put("Apricot kernel", "Seed of an apricot fruit");

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        for (Map.Entry<String, String> map : titleAndDescriptions.entrySet()) {
            searchPageObject.waitForElementByTitleAndDescription(map.getKey(), map.getValue());
        }
    }
}
