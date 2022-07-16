package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        CLOSE_SYNC_SUGGESTION = "id:Close";
        DELETE_ARTICLE_FROM_LIST_BUTTON = "id:swipe action delete";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
    }

    public iOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
