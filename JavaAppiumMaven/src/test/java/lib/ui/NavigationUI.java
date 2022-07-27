package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class NavigationUI extends MainPageObject {
    protected static String
        MY_LISTS_LINK,
        MY_LISTS_PAGE,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Click on saved lists")
    public void clickMyLists() {
        if (Platform.getInstance().isMobileWeb()) {
            tryClickElementWithAttempts(MY_LISTS_LINK, "Cannot find and click open navigation button", 5);
            waitForMyListPageIsOpened();
        }
        else {
            waitForElementAndClick(MY_LISTS_LINK,
                    "Cannot open lists of articles",
                    5);
        }

    }

    @Step("Wait for saved list to be opened")
    public void waitForMyListPageIsOpened() {
        waitForElementToBeVisible(MY_LISTS_PAGE, "Cannot switch to my lists page", 5);
    }

    @Step("Click navigation button (for Mobile Web)")
    public void openNavigation() {
        if (Platform.getInstance().isMobileWeb()) {
            waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5);
        }
        else {
            System.out.println("Method openNavigation does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
