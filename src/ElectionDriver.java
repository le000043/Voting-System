/**
 * <h1>Election Driver</h1>
 * The Election Driver is a static class with the main event loop for the Election Voting system.
 * This Class handles setting up the election voting system as well as user ballot file input and light parsing.
 * <p>
 *
 * @author Griffin Higley
 * @version 1.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Election Driver</h1>
 *
 * <p>
 * Election Driver is the Driver class that runs the VCS.
 * Election Driver links all other classes to the UI as well as calls the Election Builder to create the VCS
 * </p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
public class ElectionDriver {
    private static File electionInputFile;
    private static Election election;
    private static String electionType;
    private static String fileName;
    private static ArrayList<String> candidateList = new ArrayList<>();
    private static ArrayList<String> ballotList = new ArrayList<>();
    private static ArrayList<String> electionInformation = new ArrayList<>();
    private static String CPLPartyList = "";
    private static Audit audit;

    public static void clearDriver() {
        /**
         * This function is used to clear out the static data held by the election driver class.
         * @return void
         **/
        ElectionDriver.electionInputFile = null;
        ElectionDriver.election = null;
        ElectionDriver.electionType = "";
        ElectionDriver.fileName = "";
        ElectionDriver.candidateList = new ArrayList<>();
        ElectionDriver.ballotList = new ArrayList<>();
        ElectionDriver.electionInformation = new ArrayList<>();
        ElectionDriver.CPLPartyList = "";
    }

    public static void main(String[] args) throws IOException {
        /**
         * This function is used to run the main event loop for the VCS
         * This function also handles getting the ballot file from the users CLA
         * The function sets up the Driver and gets the ball rolling
         * @param args This is the name of the ballot file
         * @return void
         **/
        Input input = new Input((args.length > 0)? args[0] : "");
        audit = Audit.getInstance();
        audit.LOG("Election Driver starting up");
        // This can be used if an absolute path to a file is needed
//        String current = new java.io.File(".").getCanonicalPath();
        ElectionDriver.SetElectionFileAndFileName(input.GetFileName());
        try {
            ElectionDriver.Init();
            ElectionDriver.Run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void SetElectionFileAndFileName(String fileName) {
        /**
         * This functions job is to make sure that the file can be oppend
         * and to set up the File io for the Election Driver.
         * We also store a copy of the file name just in case we need it later
         * @param fileName This is the name that main got from the user
         * @return void
         **/
        try {
            ElectionDriver.fileName = fileName;
            ElectionDriver.electionInputFile = new File(fileName);
            audit.LOG("File " + fileName + " being parsed");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void Init() throws FileNotFoundException {
        /**
         * This functions job is to setup all the data for the Election driver
         * all the function calls for data of objects that must be made or
         * read into the system before being used should be called here.
         * @throws FileNotFoundException
         * @return void
         **/
        Scanner sc = new Scanner(ElectionDriver.electionInputFile);
        if (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            ElectionDriver.electionType = nextLine;
            audit.LOG("Election type found:\t" + electionType + "\n");
        }
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            // Case matches candidate formatting
            if (nextLine.contains("[")) {
                // This handles the case for the first [ in the cpl file being the party list
                if (ElectionDriver.electionType.toUpperCase().equals("CPL") && ElectionDriver.CPLPartyList.equals("")) {
                    ElectionDriver.CPLPartyList = nextLine;
                    audit.LOG("Found Election information:\t" + nextLine + "\n");
                } else {
                    candidateList.add(nextLine);
                    audit.LOG("Candidate found:\t" + nextLine + "\n");
                }
                // Case matches ballot formatting
            } else if (nextLine.contains("1") && nextLine.contains(",")) {
                ballotList.add(nextLine);
                audit.LOG("Ballot found:\t" + nextLine + "\n");
            } else {
                // Case for default general election information (like seat counts)
                electionInformation.add(nextLine);
            }
        }
        if (ElectionDriver.getElectionType().toLowerCase().equals("opl")) {
            ElectionDriver.election = ElectionBuilder.build(
                    ElectionDriver.getElectionType(),
                    ElectionDriver.getElectionInformation(),
                    ElectionDriver.getCandidateList(),
                    ElectionDriver.getBallotList()
            );
        } else {
            ElectionDriver.election = ElectionBuilder.build(
                    ElectionDriver.getElectionType(),
                    ElectionDriver.getElectionInformation(),
                    ElectionDriver.getCandidateList(),
                    ElectionDriver.getBallotList(),
                    ElectionDriver.getCPLPartyList()
            );
        }
    }

    private static int Run() {
        /**
         * This function calls to the built elections run file to start running the election
         * @throws FileNotFoundException
         * @return int This value represents if the election ran with (-1) or without an error (0)
         **/
        try {
            audit.LOG("++++++++++++++++++++++++++");
            ElectionDriver.election.Run();
            audit.LOG("++++++++++++++++++++++++++");
            audit.close();
            //System.exit(0);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static File getElectionInputFile() {
        return electionInputFile;
    }

    public static Election getElection() {
        return election;
    }

    public static void setElection(Election election) {
        ElectionDriver.election = election;
    }

    public static String getElectionType() {
        return electionType;
    }

    public static String getFileName() {
        return fileName;
    }

    public static ArrayList<String> getCandidateList() {
        return candidateList;
    }

    public static ArrayList<String> getElectionInformation() {
        return electionInformation;
    }

    public static ArrayList<String> getBallotList() {
        return ballotList;
    }

    public static String getCPLPartyList() {
        return CPLPartyList;
    }
}
