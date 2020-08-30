import java.util.logging.Logger;

/**
 * <h1>Candidate</h1>
 *
 * <p>
 * Candidate is a class that holds all relevant information for a single candidate
 * * This Class handles incrementing the vote count on this individual candidate
 * </p>
 *
 * @author Erin Seichter
 * @version 1.0
 */
class Candidate {
    private String name;                 //Name of the Candidate
    private int score;                   //how many votes the Candidate has
    private String partyString;          //Name of the affiliated party
    private Party party;
    private static Audit audit;

    public Candidate(String name, String party) {
        /** Overloaded Constructor for Candidate
         * Takes two parameters. Initializes score to zero
         * @param name  String representation of the Candidate
         * @param party  String representing Politcal Party
         */
        this.name = name;
        this.score = 0;
        this.partyString = party;
        audit = Audit.getInstance();
        audit.LOG("Candidate Created\t" + this.getName());
    }


    public Candidate(String name, int score, String party) {
        /**Overloaded Constructor for the Candidate Class
         * Takes three parameters
         * @param name String representation of the Candidate
         * @param score Int representing votes or rank
         * @param party String representing Politcal Party
         */
        this.name = name;
        this.score = score;
        this.partyString = party;
        audit = Audit.getInstance();
        audit.LOG("Candidate Created\t" + this.getName());
    }


    public void IncreaseVoteCount() {
        /**
         * This function increments vote count/score of the candidate by one
         * @return void
         **/
        audit.LOG("Increasing vote count for Candidate\t" + this.getName());
        this.score++;
    }

    public void assignParty(Party party) {
        /**
         * This function assigns a party to a candidate
         * @return void
         */
        audit.LOG("Assigning Candidate\t" + this.getName() + " To Party\t" + party.getName());
        this.party = party;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        /**
         * This function returns the Candidate's score
         * @return int - score of candidate
         **/
        return this.score;
    }

    public String getPartyString() {
        /**
         * This function returns the Candidate's party as a string
         * @return String - party of candidate
         **/
        return partyString;
    }

    public void setPartyString(String partyString) {
        this.partyString = partyString;
    }

    public Party getParty() {
        return party;
    }


    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", score=" + score +
                ", partyString='" + partyString;
    }

}
