
/**
 * <h1>Ballot</h1>
 *
 * <p>
 * Ballot is a class that holds all relevant information for a single ballot
 * * This Class handles returning the index of the winner of that ballot vote
 * </p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
public class Ballot {
    private String raw;

    /**
     * Constructor for the Ballot Class. Takes in three parameters
     *
     * @param raw comma delimited string representing the individual ballot
     */
    public Ballot(String raw) {
        this.raw = raw;
    }

    public int score() {
        /**
         * This function gets the index of '1' in a comma delimited string
         * The index of the 1 is the same index as the winner of the vote (party or candidate)
         * @return void
         **/
        return (raw.indexOf("1"));
    }

    @Override
    public String toString() {
        return "Ballot{" +
                "raw='" + raw + '\'' +
                '}';
    }
}
