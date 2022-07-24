package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        CLOSE_SYNC_SUGGESTION = "id:Close";
        DELETE_ARTICLE_FROM_LIST_BUTTON = "id:swipe action delete";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
        AMOUNT_OF_ARTICLES_IN_LIST = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
    }

    public iOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
