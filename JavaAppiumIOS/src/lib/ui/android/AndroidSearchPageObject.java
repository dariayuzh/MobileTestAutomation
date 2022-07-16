package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{TITLE}']";
        SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESC_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[android.widget.TextView[@text='{TITLE}'] and android.widget.TextView[@text='{DESCRIPTION}']]";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_LABEL = "xpath://*[@text='No results found']";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }


}
