package lib.ui.factories;

import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.ios.iOSArticlePageObject;
import lib.ui.mobileWeb.MWArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePageObjectFactory {
    public static ArticlePageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidArticlePageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSArticlePageObject(driver);
        } else if (Platform.getInstance().isMobileWeb()) {
            return new MWArticlePageObject(driver);
        }else {
            throw new IllegalStateException("Unknown platform");
        }
    }
}
