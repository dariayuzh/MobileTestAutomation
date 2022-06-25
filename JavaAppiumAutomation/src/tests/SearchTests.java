package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testFindArticlesAndCancelSearch() {
        // ex 3
        String searchLine = "Summer";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int searchResultAmount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Cannot find several articles by word " + searchLine, searchResultAmount > 1);

        searchPageObject.clickCancelSearch();
        searchPageObject.assertEmptyResultOfSearch();
    }

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Not enough elements found", amount > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "aszgfdhffggdf";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertEmptyResultOfSearch();
    }
}
