package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {
    private static final String
        STEP_LEARN_MORE_LINK = "id:Learn more about wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
        STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
        NEXT_BUTTON = "id:Next",
        GET_STARTED_BUTTON = "id:Get started",
        SKIP_BUTTON = "xpath://XCUIElementTypeButton[@name='Skip']";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about wikipedia' link", 10);
    }

    public void waitForNewWaysToExploreText() {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link", 10);
    }

    public void waitForAddOrEditPreferredLangText() {
        waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton() {
        waitForElementAndClick(NEXT_BUTTON, "Cannot find and click 'Next' button", 10);
    }

    public void clickGetStartedButton() {
        waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click 'Get started' button", 10);
    }

    public void clickSkip() {
        waitForElementAndClick(SKIP_BUTTON, "Cannot find and click 'Skip' button", 10);
    }
}
