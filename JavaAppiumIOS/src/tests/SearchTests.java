package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SearchTests extends CoreTestCase {
    @Test
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
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin park discography";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Not enough elements found", amount > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "aszgfdhffggdf";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertEmptyResultOfSearch();
    }

    @Test
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
