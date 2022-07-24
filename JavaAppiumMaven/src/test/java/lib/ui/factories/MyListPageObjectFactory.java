package lib.ui.factories;

import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.iOSMyListsPageObject;
import lib.ui.mobileWeb.MWMyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {
    public static MyListsPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSMyListsPageObject(driver);
        } else if (Platform.getInstance().isMobileWeb()) {
            return new MWMyListsPageObject(driver);
        } else {
            throw new IllegalStateException("Unknown platform");
        }
    }
}
