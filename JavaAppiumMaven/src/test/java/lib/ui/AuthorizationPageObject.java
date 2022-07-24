package lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationPageObject extends MainPageObject {
    private static final String
        LOGIN_BUTTON = "xpath://div/a[text()='Log in']",
        LOGIN_INPUT = "css:input[name='wpName']",
        PASSWORD_INPUT = "css:input[name='wpPassword']",
        SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        waitForElementToBeClickable(LOGIN_BUTTON, "Cannot find auth button", 5);
        waitForElementAndClick(LOGIN_BUTTON, "Cannot click auth button", 5);
    }

    public void enterLoginData(String login, String password) {
        waitForElementAndSendKeys(LOGIN_INPUT, login,"Cannot send login into login input", 5);
        waitForElementAndSendKeys(PASSWORD_INPUT, password,"Cannot send password into login input", 5);
    }

    public void submitForm() {
        waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit button", 5);
    }
}
