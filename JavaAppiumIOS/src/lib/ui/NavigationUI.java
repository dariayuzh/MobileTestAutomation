package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject {
    private static final String
            MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        waitForElementAndClick(MY_LISTS_LINK,
                "Cannot open lists of articles",
                5);
    }
}