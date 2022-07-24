package lib.ui.mobileWeb;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "css:a[class='menu__item--watchlist']";
        MY_LISTS_PAGE = "xpath://div[@class='page-heading']//h1[text()='Watchlist']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";

    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}