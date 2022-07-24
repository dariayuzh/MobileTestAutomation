package lib.ui.mobileWeb;

import lib.ui.MainPageObject;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class, 'mw-mf-watchlist-page-list')]//h3[contains(text(), '{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class, 'mw-mf-watchlist-page-list')]//h3[contains(text(), '{TITLE}')]/../../a[contains(@class, 'watched')]";
        AMOUNT_OF_ARTICLES_IN_LIST = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
