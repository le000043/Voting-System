import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSelectorTest {
    String fileName = "/testing/test_cpl.csv";
    @Test
    void openDialog() {
        FileSelector fileSelector= new FileSelector();
        fileSelector.OpenDialog();
        assertEquals(true, fileSelector.GetFileName().endsWith(fileName));
    }

    @Test
    void getFileName() {
        FileSelector fileSelector= new FileSelector();
        fileSelector.OpenDialog();
        assertEquals(true, fileSelector.GetFileName().endsWith(fileName));
    }
}