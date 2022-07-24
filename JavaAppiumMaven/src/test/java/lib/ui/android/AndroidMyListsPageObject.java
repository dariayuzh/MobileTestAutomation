package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[contains(@text, '{FOLDERNAME}')]";
        ARTICLE_BY_TITLE_TPL = "xpath://*[contains(@text, '{TITLE}')]";
        AMOUNT_OF_ARTICLES_IN_LIST = "xpath://*[@resource-id='org.wikipedia:id/reading_list_contents']/[@resource-id='org.wikipedia:id/page_list_item_container']";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
