import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;


/**
 * <h1>createBallot</h1>
 *
 * <p>
 * createBallot is used when the user chooses to enter election info manually.
 * This class handles prompting user for info, parsing user info, and creating the input file from user input
 * </p>
 *
 * @author Erin Seichter, Dan Belair
 * @version 1.0
 */


public class CreateBallot implements GUIInput {
    private String fileName;
    private String manualInput() {
        /**
         * This function takes in user input to create an input file
         * It also prompts user with what information is needed
         * @return String - name of input file if successful, null if unsuccessful.
         **/
        Scanner input = new Scanner(System.in);
        //ask election type
        System.out.println("Enter the Election Type (OPL or CPL):");
        String electionType = input.nextLine();
        electionType = electionType.toUpperCase();

        while(electionType.isEmpty() || (!electionType.equals("OPL") && !electionType.equals("CPL"))){
            System.out.println("That is not a valid election type. Try again.");
            electionType = input.nextLine();
            electionType = electionType.toUpperCase();
        }
        if (electionType.equals("OPL")) {
            return manualOpl();
        } else { //cpl
            return manualCpl();
        }

    }


    /**
     * This function prompts the user through the command line and takes input.
     * Input is formatted and placed into local variables and strings.
     *
     * @return String
     */
    private String manualCpl() {
        Scanner input = new Scanner(System.in);

        String type = "CPL";
        int numParties;
        String allParties = "";
        ArrayList<String> parties = new ArrayList<>();
        int seats;
        int totCands;
        ArrayList<String> candidateInfo = new ArrayList<>();

        String line; //use this to gather input and error check

        //get number of parties and list of parties
        System.out.println("Enter the number of Parties");
        line = input.nextLine();
        //error check the input
        while (line.isEmpty() || !checkNumber(line)){
            System.out.println("That is not a valid entry. Try again with a positive number");
            line = input.nextLine();
        }
        numParties = Integer.parseInt(line); //pull number from input string

        for (int i = 1; i <= numParties; i++) {
            String partyName;
            System.out.println("Enter the name of party " + i);
            line = input.nextLine();
            //error check the input
            line=checkString(line, input);
            partyName = line;
            parties.add(partyName);
        }

        //get number of seats
        System.out.println("Please enter the number of seats available");
        line = input.nextLine();
        //error check the input
        while (line.isEmpty() || !checkNumber(line)){
            System.out.println("That is not a valid entry. Try again with a positive number");
            line = input.nextLine();
        }
        seats = Integer.parseInt(line);

        //get number of candidates
        System.out.println("Please enter the total number Candidates");
        //error check the input
        line=input.nextLine();
        while (line.isEmpty() || !checkNumber(line)){
            System.out.println("That is not a valid entry. Try again with a positive number");
            line = input.nextLine();
        }
        totCands = Integer.parseInt(line); //pull int from input string

        if (totCands<seats){
            System.out.println("The number of seats cannot exceed the number of candidates. This election will be terminated.");
            System.exit(0);
        }

        //used to determine candidate's rank within a party
        int[] partyRanks = new int[parties.size()];

        //get candidate information
        System.out.println("The candidates will be automatically ranked for their party. " +
                "Enter the candidates in order from top choice to last choice within a party.");

        ArrayList<String> candParties = new ArrayList<>();
        for (int i = 0; i < totCands; i++) {
            //obtain candidate name and party
            System.out.println("Please enter the name of Candidate");
            line = input.nextLine();
            //error check the input
            line=checkString(line, input);

            String candidateName = line;

            System.out.println("Please enter the Party of candidate " + candidateName);
            line = input.nextLine();
            //error check the input
            line=checkString(line, input);
            String candParty = line;

            //loop through error until user enters a valid party
            while (!parties.contains(candParty)) {
                System.out.println("That party doesn't exist in this election. Enter one of the following parties:");
                for (String party : parties) {
                    System.out.print(party + " ");
                }
                System.out.println();
                line = input.nextLine(); //take another party input from user
                //error check the input
                line=checkString(line, input);
                candParty = line;
            }
            candParties.add(candParty);

            //determine candidate's rank
            int index = parties.indexOf(candParty);
            partyRanks[index]++;
            int rank = partyRanks[index];

            //put together the string for the file
            String candInfo = "[" + candidateName + "," + candParty + "," + rank + "]";
            candidateInfo.add(candInfo);
        }
        //make sure parties has all candidates' parties and no extras
        parties=checkParties(parties,candParties);
        allParties = "[" + String.join(",", parties) + "]";

        //read through the ballots
        ArrayList<String> votes = collectVotes(numParties);
        int totVotes = votes.size();


        //send to function that creates input file
        try {
            return outputCPL(type, numParties, allParties, seats, totVotes, totCands, candidateInfo, votes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * This function prompts the user through the command line and takes input.
     * Input is formatted and placed into local variables and strings.
     *
     * @return String
     */
    private String manualOpl() {
        Scanner input = new Scanner(System.in);
        String line; //use this to gather input and error check
        String type = "OPL";
        int totCands;
        ArrayList<String> candInfo = new ArrayList<>();

        System.out.println("Please enter the total number of Seats");
        line=input.nextLine();
        while (line.isEmpty() || !checkNumber(line)){
            System.out.println("That is not a valid entry. Try again with a positive number");
            line = input.nextLine();
        }
        int numSeats = Integer.parseInt(line);

        //get number of candidates
        System.out.println("Please enter the total number Candidates");
        line=input.nextLine();
        while (line.isEmpty() || !checkNumber(line)){
            System.out.println("That is not a valid entry. Try again with a positive number");
            line = input.nextLine();
        }
        totCands = Integer.parseInt(line);

        if (totCands<numSeats){
            System.out.println("The number of seats cannot exceed the number of candidates. This election will be terminated.");
            System.exit(0);
        }

        // get each candidate,party pair
        for (int i = 0; i < totCands; i++) {
            String name;
            String party;

            System.out.println("Please enter the name of candidate " + (i+1));
            line = input.nextLine();
            //error check the input
            line=checkString(line, input);
            name = line;

            System.out.println("Please enter the Party of candidate " + name);
            line = input.nextLine();
            //error check the input
            line=checkString(line, input);
            party = line;

            String canInfo = "[" + name + "," + party + "]";
            candInfo.add(canInfo);
        }

        //read through the ballots
        ArrayList<String> votes = collectVotes(totCands);
        int totVotes = votes.size();

        //send to function that creates input file
        try {
            return outputOPL(type, numSeats, totCands, totVotes, candInfo, votes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // return file name to election driver
    }

    /**
     * This function checks user input to ensure they are entering a valid number
     *
     * @param str is the input string to be checked
     * @return boolean - false if string contains a char that isn't a positive digit, true otherwise
     */
    private boolean checkNumber(String str){
        if (str==null){
            return false;
        }
        char [] nums = str.toCharArray();
        for (char n : nums){
            if (n<'0' || n>'9'){
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks the input string to ensure it is a valid entry (not empty)
     * asks for more input until a valid string is given
     *
     * @param  line is the input string to be checked
     * @param input is the Scanner manualOPL/CPL is using
     * @return string - null if string is empty, true otherwise
     */
    private String checkString(String line, Scanner input){
        while (line==null || line.trim().isEmpty()){
            System.out.println("That is not a valid entry. Try again.");
            line=input.nextLine();
        }
        return line;
    }

    /**
     * This function checks through the list of parties given by the user and those entered with candidates
     * makes sure these lists are the same
     * adjust parties if not the same (candidateParties is not used after this)
     *
     * @param  parties is the list of parties provided by the user
     * @param candidateParties is the list of parties inputted with candidates
     * @return string - null if string is empty, true otherwise
     */
    private ArrayList<String> checkParties(ArrayList<String> parties, ArrayList<String> candidateParties){

        for (String party:candidateParties){
            if (!parties.contains(party)){
                parties.add(party);
            }
        }
        for (int i=0; i<parties.size(); i++){
            if (!candidateParties.contains(parties.get(i))){
                parties.remove(i);
                i--;
            }
        }
        return parties;
    }

    /**
     * @param numOptions
     * @return
     */
    private ArrayList<String> collectVotes(int numOptions) { //numOptions is either the number of parties or candidates, depending on opl or cpl
        Scanner input = new Scanner(System.in);
        ArrayList<String> ballots = new ArrayList<>();
        int voteCount = 0; //variable to keep track of number of votes
        while (true) {
            System.out.println("Enter vote " + (voteCount+1) + " or type 'Done' to terminate voting");
            String line = input.nextLine().toUpperCase();
            if (line.compareTo("DONE") == 0) { //if user ends voting
                break; //stop collecting votes
            } else {
                //input has to a 1, must contain commas if more than 1 candidate, should be the length of numOptions
                if (!line.contains("1") || (numOptions > 1 && !line.contains(",")) || line.length() != numOptions) {
                    System.out.println("The input was not a valid ballot and will not be counted.");
                } else {
                    ballots.add(line);
                    voteCount++;
                }
            }
        }
        return ballots;
    }

    /**
     * Function writes to a comma delim file for the CPL type of election
     *
     * @param elType   the type of electino
     * @param nParties int the numbr of parties
     * @param parties  string of the parties
     * @param seats    int number of seats up for election
     * @param nVotes   int number of votes placed in election
     * @param tCands   int total number of candidates in the election
     * @param cands    List of candidate names
     * @param votes    List indicating the votes
     * @return the filename of the csv file
     * @throws IOException
     */
    private String outputCPL(String elType, int nParties, String parties, int seats,
                             int nVotes, int tCands, ArrayList cands, ArrayList votes) throws IOException {
        String fileName = "manual_cpl.csv";
        PrintWriter writer = new PrintWriter(fileName);

        writer.println(elType);
        writer.println(nParties);
        //print all parties
        writer.println(parties);
        writer.println(seats);
        writer.println(nVotes);
        writer.println(tCands);
        //print candidate info for all
        cands.forEach(writer::println);
        //print vote info
        votes.forEach(writer::println);
        writer.close();

        return fileName;

    }

    /**
     * Writer function to create the comma delimited file for the OPL elections
     *
     * @param elType     String the type of election
     * @param nSeats     int number of seats
     * @param numCand    int for the numbe of cadidates
     * @param nVotes     int number of votes
     * @param candidates List of cadidate information
     * @param votes      list of the voting tally
     * @return the filename of the csv file
     * @throws IOException
     */
    private String outputOPL(String elType, int nSeats, int numCand, int nVotes,
                             ArrayList candidates, ArrayList votes) throws IOException {
        String fileName = "manual_opl.csv";
        PrintWriter writer = new PrintWriter(fileName);

        writer.println(elType);
        writer.println(nSeats);
        writer.println(nVotes);
        writer.println(numCand);
        //add each candidate
        candidates.forEach(writer::println);
        //add each vote
        votes.forEach(writer::println);
        writer.close();

        return fileName;

    }
    @Override
    public void OpenDialog() {
        this.fileName = manualInput();
    }

    @Override
    public String GetFileName() {
        return (this.fileName != null)? this.fileName : "";
    }
    
}

