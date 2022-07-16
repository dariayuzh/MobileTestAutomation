package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "//*[contains(@text, '{FOLDERNAME}')]";
        ARTICLE_BY_TITLE_TPL = "//*[contains(@text, '{TITLE}')]";
    }

    public AndroidMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
