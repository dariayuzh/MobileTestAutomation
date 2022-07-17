package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String folderName = "Learning programming";

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

        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        } else {
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

        /* Добавлен Thread.sleep, т.к. не успевает сразу найти элемент Search wikipedia... после
        закрытия первой статьи. В версии приложения 6.9.2 элемент, который ищется в initSearchInput
        и элемент, в который отправляется строка запроса typeSearchLine совершенно идентичны
        по всем атрибутам (в лекциях это разные элементы). При клике в initSearchInput этот элемент
        анимируется (сдвигается немного вверх).
        При возвращении на главную страницу из поиска элемент снова сдвигается (теперь уже
        вниз) и без sleep он ищется быстрее, чем происходит эта анимация из-за чего появляется
        ошибка org.openqa.selenium.StaleElementReferenceException:
        The previously found element ""Search Wikipedia" SearchField" is not present in the current view anymore.
        Make sure the application UI has the expected state.
        */
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
