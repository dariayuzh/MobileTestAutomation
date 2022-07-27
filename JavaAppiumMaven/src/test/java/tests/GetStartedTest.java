package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for welcome screen (iOS)")
public class GetStartedTest extends CoreTestCase {
    @Test
    @Feature(value = "Welcome Screen")
    @DisplayName("Click all welcome screen till main page (iOS)")
    @Description("Click all welcome screen till main page (iOS)")
    @Step("Starting test testPassThroughWelcome")
    @Severity(value = SeverityLevel.NORMAL)
    public void testPassThroughWelcome() throws Exception {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMobileWeb()) return;

        WelcomePageObject welcomePage = new WelcomePageObject(driver);
        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextButton();

        welcomePage.waitForNewWaysToExploreText();
        welcomePage.clickNextButton();

        welcomePage.waitForAddOrEditPreferredLangText();
        welcomePage.clickNextButton();

        welcomePage.waitForLearnMoreAboutDataCollectedText();
        welcomePage.clickGetStartedButton();
    }
}
