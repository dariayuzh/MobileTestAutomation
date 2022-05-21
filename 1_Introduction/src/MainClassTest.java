import org.junit.Test;
import static org.junit.Assert.*;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        MainClass mainClass = new MainClass();
        assertEquals("getLocalNumber does not return 14", 14, mainClass.getLocalNumber());
    }
}
