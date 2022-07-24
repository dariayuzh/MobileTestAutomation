package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class NavigationUI extends MainPageObject {
    protected static String
        MY_LISTS_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        if (Platform.getInstance().isMobileWeb()) {
            tryClickElementWithAttempts(MY_LISTS_LINK, "Cannot find and click open navigation button", 5);
        }
        else {
            waitForElementAndClick(MY_LISTS_LINK,
                    "Cannot open lists of articles",
                    5);
        }
    }

    public void openNavigation() {
        if (Platform.getInstance().isMobileWeb()) {
            waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5);
        }
        else {
            System.out.println("Method openNavigation does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
