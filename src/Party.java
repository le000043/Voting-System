import java.util.ArrayList;


/**
 * <h1>Party</h1>
 * <p>Party Class is the representation of a Political Party
 * It has a name
 * Contains information on votes, Candidate objects, and number of seats. </p>
 *
 * @author Dan Belair
 * @version 1.0
 */

class Party {
    protected ArrayList<Candidate> candidateList;       //list of Candidate objects
    private int numberOfSeats;                          //Number of seats allocated
    private String name;                                //Name of the party
    private int partyVotes;                             // Votes received by the party
    private static Audit audit;

    /**
     * Constructor for the Party class
     * Takes one argument "name".
     *
     * @param name String representation of the party name
     *             Number of seats, and votes are initialized to 0.
     *             Original candidate list is empty.
     */
    public Party(String name) {
        this.name = name;
        this.candidateList = new ArrayList<>();
        this.numberOfSeats = 0;
        this.partyVotes = 0;
        audit = Audit.getInstance();
        audit.LOG("Party Created\n");
        audit.LOG(this.toString());

    }


    public void addPartyVote() {
        /**Increments Party Classes partyvotes by 1
         * Void function
         */
        audit.LOG("Increasing vote Count\t " + this.getName());
        this.partyVotes += 1;
    }


    public void addCandidate(Candidate candidate) {
        /**Adds Candidate object to the candidateList in a party
         *
         * @param candidate
         * void function
         */
        audit.LOG("Adding candidate\t" + candidate.getName() + " To party\t" + this.getName());
        candidateList.add(candidate);
    }

    //getters and setters

    public ArrayList<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(ArrayList<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setPartyVotes(int numberOfVote) {
        this.partyVotes = numberOfVote;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartyVotes() {
        return partyVotes;
    }


    @Override
    public String toString() {
        return "Party{" +
                "candidateList=" + candidateList +
                ", numberOfSeats=" + numberOfSeats +
                ", name='" + name + '\'' +
                ", partyVotes=" + partyVotes +
                '}';
    }

}
