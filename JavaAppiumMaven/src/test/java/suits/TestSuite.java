package suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ArticleTests;
import tests.ChangeAppConditionsTests;
import tests.MyListsTests;
import tests.SearchTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArticleTests.class,
        ChangeAppConditionsTests.class,
        MyListsTests.class,
        SearchTests.class
})
public class TestSuite {
}
