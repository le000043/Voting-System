import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class RegressionTest {
    @Test
    public void RegressionTestOpl() {
        String guiargs[] = {""};
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        try {
            ElectionDriver.main(guiargs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String opl_output = myOut.toString();
        File opl_regression_got = new File("./testing/temp_opl_regression.txt");
        try {
            if (opl_regression_got.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("./testing" + opl_regression_got.getName()));
                writer.write(opl_output);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paths.get(".").toAbsolutePath().normalize().toString();
        File opl_expected = new File("./testing/opl_regression_expected.txt");
        boolean areFilesIdentical = true;
        try {
            FileInputStream fis1 = new FileInputStream(opl_regression_got);
            FileInputStream fis2 = new FileInputStream(opl_expected);
            int i1 = fis1.read();
            int i2 = fis2.read();
            while (i1 != -1) {
                if (i1 != i2) {
                    areFilesIdentical = false;
                    break;
                }
                i1 = fis1.read();
                i2 = fis2.read();
            }
            fis1.close();
            fis2.close();
        } catch (IOException e) {
            System.out.println("IO exception");
            areFilesIdentical = false;
        }
        assertTrue("The files the same", areFilesIdentical);
    }

    @Test
    public void RegressionTestCpl() {
        String guiargs[] = {""};
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        try {
            ElectionDriver.main(guiargs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String opl_output = myOut.toString();
        File cpl_regression_got = new File("./testing/temp_cpl_regression.txt");
        try {
            if (cpl_regression_got.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("./testing/" + cpl_regression_got.getName()));
                writer.write(opl_output);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paths.get(".").toAbsolutePath().normalize().toString();
        File cpl_expected = new File("./testing/cpl_regression_expected.txt");
        boolean areFilesIdentical = true;
        try {
            FileInputStream fis1 = new FileInputStream(cpl_regression_got);
            FileInputStream fis2 = new FileInputStream(cpl_expected);
            int i1 = fis1.read();
            int i2 = fis2.read();
            while (i1 != -1) {
                if (i1 != i2) {
                    areFilesIdentical = false;
                    break;
                }
                i1 = fis1.read();
                i2 = fis2.read();
            }
            fis1.close();
            fis2.close();
        } catch (IOException e) {
            System.out.println("IO exception");
            areFilesIdentical = false;
        }
        assertTrue("The files the same", areFilesIdentical);


    }
}
