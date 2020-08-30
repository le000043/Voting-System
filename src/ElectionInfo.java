import java.util.ArrayList;

/**
 * <h1>ElectionInfo</h1>
 *
 * <p>
 * ElectionInfo class contains meta level information for the election
 * Includes the number of seats, candidates and ballots
 * Aggregated data. It has no functions outside of getters and setters.
 * </p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
public class ElectionInfo {
    protected int numberOfSeats;
    protected int numberOfCandidates;
    protected int numberOfBallots;

    public ElectionInfo() {

    }

    /**
     * Constructor for the ElectionInfo Class. Takes in three parameters
     *
     * @param numberOfSeats      Number of total seats up for election
     * @param numberOfCandidates Number of Candidates competing
     * @param numberOfBallots    Number of Ballots cast in the election
     */

    public ElectionInfo(int numberOfSeats, int numberOfCandidates, int numberOfBallots) {
        this.numberOfSeats = numberOfSeats;
        this.numberOfCandidates = numberOfCandidates;
        this.numberOfBallots = numberOfBallots;
    }

    //getters and setters
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfCandidates() {
        return numberOfCandidates;
    }

    public void setNumberOfCandidates(int numberOfCandidates) {
        this.numberOfCandidates = numberOfCandidates;
    }

    public int getNumberOfBallots() {
        return numberOfBallots;
    }

    public void setNumberOfBallots(int numberOfBallots) {
        this.numberOfBallots = numberOfBallots;
    }

    @Override
    public String toString() {
        return "ElectionInfo{" +
                "numberOfSeats=" + numberOfSeats +
                ", numberOfCandidates=" + numberOfCandidates +
                ", numberOfBallots=" + numberOfBallots +
                '}';
    }
}


/**
 * <h1>CPLElectionInfor</h1>
 *
 * <p>
 * CPLElectionInfor class contains meta level information for the CPL election
 * Inherits from ElectionInfo
 * Includes the number of seats, candidates and ballots
 * Aggregated data. It has no functions outside of getters and setters.
 * </p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
class CplElectionInfor extends ElectionInfo {
    private int numberOfParties;
    private ArrayList<String> partyNames;

    public CplElectionInfor() {
    }

    /**
     * Constructor for the CplElectionInfor Class. Takes in five parameters
     *
     * @param numberOfSeats      Number of total seats up for election
     * @param numberOfCandidates Number of Candidates competing
     * @param numberOfBallots    Number of Ballots cast in the election
     * @param numberOfParties    Number of Parties cast in the election
     * @param partyNames         Names of the parties in the election
     */
    public CplElectionInfor(int numberOfSeats, int numberOfCandidates, int numberOfBallots, int numberOfParties, ArrayList<String> partyNames) {
        super(numberOfSeats, numberOfCandidates, numberOfBallots);
        this.numberOfParties = numberOfParties;
        this.partyNames = partyNames;
    }

    public int getNumberOfParties() {
        return numberOfParties;
    }

    public void setNumberOfParties(int numberOfParties) {
        this.numberOfParties = numberOfParties;
    }

    @Override
    public String toString() {
        return "CplElectionInfor{" +
                "numberOfSeats=" + numberOfSeats +
                ", numberOfCandidates=" + numberOfCandidates +
                ", numberOfBallots=" + numberOfBallots +
                ", numberOfParties=" + numberOfParties +
                ", partyNames=" + partyNames +
                '}';
    }
}