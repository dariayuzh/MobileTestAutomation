package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {
    private static final String
        STEP_LEARN_MORE_LINK = "id:Learn more about wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
        STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
        NEXT_BUTTON = "id:Next",
        GET_STARTED_BUTTON = "id:Get started";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() throws Exception {
        waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about wikipedia' link", 10);
    }

    public void waitForNewWaysToExploreText() throws Exception {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link", 10);
    }

    public void waitForAddOrEditPreferredLangText() throws Exception {
        waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() throws Exception {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton() throws Exception {
        waitForElementAndClick(NEXT_BUTTON, "Cannot find and click 'Next' button", 10);
    }

    public void clickGetStartedButton() throws Exception {
        waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click 'Get started' button", 10);
    }
}
