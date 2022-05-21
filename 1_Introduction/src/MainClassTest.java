import org.junit.Test;

import static org.junit.Assert.*;

public class MainClassTest {
    MainClass mainClass = new MainClass();

    @Test
    public void testGetLocalNumber() {
        assertEquals("getLocalNumber does not return 14", 14, mainClass.getLocalNumber());
    }

    @Test
    public void testGetClassNumber() {
        assertTrue("getClassNumber returns a number less than 45", mainClass.getClassNumber() > 45);
    }
}
