
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class ElectionDriverTest {
    // OPL SETUP INFO
    private static File OPL_FILE = new File("./testing/test_opl.csv");
    private static String OPL_FILE_NAME = "./testing/test_opl.csv";
    private static Election OPL_ELECTION = null;
    private static String OPL_ELECTION_TYPE = "OPL";
    private static ArrayList<String> OPL_CANDIDATE_LIST = new ArrayList<>();
    private static ArrayList<String> OPL_BALOT_LIST = new ArrayList<>();
    private static ArrayList<String> OPL_ELECTION_INFO = new ArrayList<>();

    // CPL SETUP INFO
    private static String CPL_FILE_NAME = "./testing/test_cpl.csv";
    private static String CPL_PARTY_LIST = "";
    private static File CPL_FILE = new File(CPL_FILE_NAME);
    private static String CPL_ELECTION_TYPE = "CPL";
    private static Election CPL_ELECTION = null;
    private static ArrayList<String> CPL_CANDIDATE_LIST = new ArrayList<>();
    private static ArrayList<String> CPL_BALOT_LIST = new ArrayList<>();
    private static ArrayList<String> CPL_ELECTION_INFO = new ArrayList<>();

    @Test //Test ID: VCS_1
    public void main() throws IOException {
        // OPL TEST
        String[] OPLargs = {OPL_FILE_NAME};
        ElectionDriver.main(OPLargs);
        buildAssets(OPL_FILE, OPL_CANDIDATE_LIST, OPL_BALOT_LIST, OPL_ELECTION_INFO);

        OPL_ELECTION = ElectionBuilder.build(
                OPL_ELECTION_TYPE,
                OPL_ELECTION_INFO,
                OPL_CANDIDATE_LIST,
                OPL_BALOT_LIST
                );
        // TODO Create OPL AND CPL ELECTION
        assertEquals(OPL_FILE, ElectionDriver.getElectionInputFile());
        OPL_ELECTION.Run();
        assertEquals(OPL_ELECTION.toString(), ElectionDriver.getElection().toString());
        assertEquals(OPL_ELECTION_TYPE, ElectionDriver.getElectionType());
        assertEquals(OPL_FILE_NAME, ElectionDriver.getFileName());


        assertEquals(OPL_CANDIDATE_LIST, ElectionDriver.getCandidateList());
        assertEquals(OPL_BALOT_LIST, ElectionDriver.getBallotList());
        assertEquals(OPL_ELECTION_INFO, ElectionDriver.getElectionInformation());
        ElectionDriver.clearDriver();

        // CPL Test
        String[] CPLargs = {CPL_FILE_NAME};

        ElectionDriver.main(CPLargs);
        buildAssets(CPL_FILE, CPL_CANDIDATE_LIST, CPL_BALOT_LIST, CPL_ELECTION_INFO);
        CPL_ELECTION = ElectionBuilder.build(
                CPL_ELECTION_TYPE,
                CPL_ELECTION_INFO,
                CPL_CANDIDATE_LIST,
                CPL_BALOT_LIST,
                CPL_PARTY_LIST
        );
        CPL_ELECTION.Run();
        assertEquals(CPL_FILE, ElectionDriver.getElectionInputFile());
        assertEquals(CPL_ELECTION.toString(), ElectionDriver.getElection().toString());
        assertEquals(CPL_ELECTION_TYPE, ElectionDriver.getElectionType());
        assertEquals(CPL_FILE_NAME, ElectionDriver.getFileName());


        assertEquals(CPL_CANDIDATE_LIST, ElectionDriver.getCandidateList());
        assertEquals(CPL_BALOT_LIST, ElectionDriver.getBallotList());
        assertEquals(CPL_ELECTION_INFO, ElectionDriver.getElectionInformation());
        ElectionDriver.clearDriver();

    }

    public void buildAssets(File file, ArrayList<String> candidateList, ArrayList<String> balotList,
                            ArrayList<String> electionInformation) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            // Case matches candidate formatting
            if (nextLine.contains("[")) {
                if (ElectionDriver.getElectionType().toUpperCase().equals("CPL") && CPL_PARTY_LIST.equals("")) {
                    ElectionDriverTest.CPL_PARTY_LIST = nextLine;
                } else {
                    candidateList.add(nextLine);
                }
                // Case matches ballot formatting
            } else if (nextLine.contains("1") && nextLine.contains(",")) {
                balotList.add(nextLine);
            } else {
                // Case for default general election information (like seat counts)
                electionInformation.add(nextLine);
            }
        }
        sc.close();
    }
}
