import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * <h1>Output</h1>
 * <p>Output class is and abstract class used to build
 * the Audit, Media, Results classes and the Output Interface.</p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
public abstract class Output {
    protected String fileName;
    protected BufferedWriter writer;
    protected static String dateTime = "";
    protected static boolean isClosed = false;
    /**
     * Constructor for the Media Class. Takes in no parameters
     */
    protected Output() {
    }


    protected void LOG(String event) {
        /**
         * This function is used to write to the output file.
         * @throws IOException
         * @return void
         **/
//        if(!isClosed){
            synchronized (writer) {
                try {
                    writer.write(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//        }
    }

    protected void setFileName(String fileName) {
        /**
         * This function sets the name of the output file and creates the file so the logs can write to it.
         * @throws IOException
         * @return void
         **/
        try {
            File file = new File(fileName);
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void close() {
        /**
         * This function closes the current output file.
         * @throws IOException
         * @return void
         **/

        try {
            writer.close();
            isClosed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static String generateFileName(String name) {
        /**
         * This function generates the name of the output file.
         * @return String - the name of the generated file
         **/
        if (Output.dateTime.equals("")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
            LocalDateTime now = LocalDateTime.now();
            Output.dateTime = dtf.format(now);
        }
        return "./outputFiles/" + name + Output.dateTime + ".txt";
    }

}

/**
 * <h1>Audit</h1>
 * <p>Utility Class for building audit file.</p>
 *
 * @author Griffin Higley
 * @version 1.0
 */

class Audit extends Output {
    private static Audit audit = null;

    /**
     * Constructor for the Audit Class. Takes in no parameters
     *
     * @throws IOException
     */
    private Audit() throws IOException {
        super();
        FileDialog fd = new FileDialog(new Frame(), "Choose a audit file name", FileDialog.SAVE);
        boolean fileNameValid = false;
        while(!fileNameValid){
            fd.setVisible(true);
            if (fd.getFile() == null) {
                System.out.println("You cancelled the choice");
                System.exit(0);
            }
            if(fd.getFile().endsWith(".txt") && fd.getDirectory() != null){
                setFileName(fd.getDirectory() + fd.getFile());
                fd.dispose();
                fileNameValid = true;
            }
        }

    }

    public static Audit getInstance() {
        if (audit == null)
            try {
                audit = new Audit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return audit;
    }

    protected void LOG(String event) {
        /**
         * This function is used to write to the output file.
         * @throws IOException
         * @return void
         **/
        if(!isClosed){
            synchronized (writer) {
                try {
                    writer.write(event);
                    writer.write("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * <h1>Media</h1>
 * <p>Utility class for building media output file</p>
 *
 * @author Griffin Higley
 * @version 1.0
 */

class Media extends Output {
    private Election election;

    /**
     * Constructor for the Media Class. Takes in one parameter
     *
     * @param election Current election object being run whose results we are printing
     * @throws IOException
     */
    public Media(Election election) {
        super();
        this.election = election;
        setFileName(Output.generateFileName("Media_"));
    }


    public void Report(){
        /**
         * This function generates a report of election statistics as a TXT file.
         * @return void
         **/
        StringBuilder strBuiler = new StringBuilder();
        strBuiler.append("=============================\n\n");
        strBuiler.append("Number of seats \t");
        strBuiler.append(election.electionInfo.getNumberOfSeats() + "\n");
        strBuiler.append("Number of Candidates \t");
        strBuiler.append(election.electionInfo.getNumberOfCandidates() + "\n");
        strBuiler.append("Number of Ballots \t");
        strBuiler.append(election.electionInfo.numberOfBallots + "\n");
        strBuiler.append("Winners of election \t");
        ArrayList<Candidate> winners = election.PickWinners();
        strBuiler.append(strBuiler.toString());
        winners.stream().forEach(winner -> {
            strBuiler.append("winner\t");
            strBuiler.append(winner.toString() + "\n");
        });
        this.LOG("Party Statistic \t");
        ArrayList<Party> parties = election.getPartyList();
        parties.stream().forEach(party -> {
            strBuiler.append(party.toString() + "\n");
        });
        strBuiler.append("=============================\n");
        this.LOG(strBuiler.toString());
        this.close();
    }

    public String getFileName() {
        return fileName;
    }
}

/**
 * <h1>Results</h1>
 * <p>Utility class for generating election results text.</p>
 *
 * @author Griffin Higley
 * @version 1.0
 */

class Results extends Output {
    private Election election;

    /**
     * Constructor for the Results Class. Takes in one parameter
     *
     * @param election Current election object being run whose results we are printing
     * @throws IOException
     */
    public Results(Election election) throws IOException {
        super();
        this.election = election;
        setFileName(Output.generateFileName("Results_"));
    }

    // TODO route output to terminal
    public void Report() throws IOException {
        /**
         * This function generates a report of election statistics to stdout.
         * @throws IOException
         * @return void
         **/
        StringBuilder strBuiler = new StringBuilder();
        strBuiler.append("=============================\n");
        strBuiler.append("Winners of election \n");
        ArrayList<Candidate> winners = election.PickWinners();
        winners.stream().forEach(winner -> {
            strBuiler.append("winner\n");
            strBuiler.append(winner.toString() + "\n");
        });
        strBuiler.append("=============================\n");
        System.out.println(strBuiler.toString());
        this.LOG(strBuiler.toString());
        this.close();
    }

    public String getFileName() {
        return fileName;
    }

}
