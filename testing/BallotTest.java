import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BallotTest {
    String ballotString = ",,1,,,,";
    private static File OPL_FILE = new File("./testing/opl_ballot_test.csv");
    private static String OPL_FILE_NAME = "./testing/opl_ballot_test.csv";
    private static File CPL_FILE = new File("./testing/cpl_ballot_test.csv");
    private static String CPL_FILE_NAME = "./testing/cpl_ballot_test.csv";

    @Test //Test ID: VCS_23
    public void score() {
        Ballot ballot = new Ballot(ballotString);
        assertEquals(2, ballot.score());
    }


    @Test
    public void checkSpeedCpl() throws IOException {
        long startTime = System.currentTimeMillis();
        String[] CPLargs = {CPL_FILE_NAME};
        ElectionDriver.main(CPLargs);

        long endtime = System.currentTimeMillis();
        long totalTime = endtime-startTime;
        System.out.println(totalTime);
        assertTrue(totalTime<(300000)); //300000 milliseconds = 5 mins
    }

    @Test
    public void checkSpeedOpl() throws IOException {
        long startTime = System.currentTimeMillis();
        String[] OPLargs = {OPL_FILE_NAME};
        ElectionDriver.main(OPLargs);

        long endtime = System.currentTimeMillis();
        long totalTime = endtime-startTime;
        System.out.println(totalTime);
        assertTrue(totalTime<(300000)); //300000 milliseconds = 5 mins


    }

}
