package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath://div[@class='page-heading']/h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page-actions-watch a#ca-watch";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@title='Remove this page from your watchlist']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
