import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class AuditTest {

    @Test //Test ID: VCS_21
    public void getInstance() {
        Audit a1 = null;
        Audit a2 = null;
        assertEquals(null, a1);
        assertEquals(null, a2);
        a1 = Audit.getInstance();
        a2 = Audit.getInstance();
        assertNotNull(a1);
        assertNotNull(a2);
        assertEquals(true, a1.equals(a2));
    }
    @Test //Test ID: VCS_21
    public void Log() {
        String filename = "./testing/audit_test.txt";
        Audit a1 = null;
        assertEquals(null, a1);
        a1 = Audit.getInstance();
        assertNotNull(a1);
        String logString = "TESTING";
        a1.LOG(logString);
        a1.close();
        File file = new File(filename);
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(filename)));
            assertTrue(data.contains(logString + "\n"), "Files are the same");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}