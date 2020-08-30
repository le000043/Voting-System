import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * <h1>Election</h1>
 *
 * <p>
 * Election is an abstract class that holds all relevant information for an election.
 * This class is the parent class to OPL and CPL.
 * The Election class handles determining winners of an election and distributing seats to winners.
 * </p>
 *
 * @author Griffin Higley, Erin Seichter, Dat Le
 * @version 1.0
 */
abstract class Election {
    private static Audit audit;
    protected ArrayList<Ballot> ballotList;
    protected ElectionInfo electionInfo;
    protected ArrayList<Candidate> candidateList;
    protected ArrayList<Party> partyList;

    /**
     * Constructor for the Election Class. Takes in four parameters
     *
     * @param ballotList    List of ballot strings for this election
     * @param electionInfo  election meta data
     * @param candidateList List of candidates for this election
     * @param partyList     list of parties for this election
     */
    public Election(ArrayList<Ballot> ballotList, ElectionInfo electionInfo, ArrayList<Candidate> candidateList, ArrayList<Party> partyList) {
        this.ballotList = ballotList;
        this.electionInfo = electionInfo;
        this.candidateList = candidateList;
        this.partyList = partyList;
        audit = Audit.getInstance();
        initCoinFlip();
    }

    private void initCoinFlip() {
        /**
         * This function generates a random number 1 million times to prime the random number generator.
         * This is to attempt to make the number as random as possible to ensure accuracy of the coin flip in case of a tie event.
         * @return void
         **/
        audit.LOG("initCoinFlip");
        for (int i = 0; i < 1000000; i++) {
            Math.random();
        }
    }

    public abstract int Run() throws IOException;

    protected abstract void Count(Ballot ballot);

    protected static <Key, Value> ArrayList<Key> getKeyGivenValue(Map<Key, Value> map, Value value) {
        /**
         * Given value, get key from a map
         * @param map a map of key and value
         * @param value a variable type Value
         * @return key
         */
        audit.LOG("Election Running");
        // this function takes in a map and a valuen
        // and return the key associated with the value
        ArrayList<Key> tie_list = new ArrayList<>();
        for (Map.Entry<Key, Value> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                tie_list.add(entry.getKey());
            }
        }
        return tie_list;
    }

    protected static Party coinFlipParty(ArrayList<Party> tieParties) {
        /**
         * This function generates a random number to simulate a coin flip for tie events.
         * This version is for determining the winner of two tied Parties
         * @return Candidate: This value represents the winner of the coinflip
         **/
        audit.LOG("Coin Flip\n");
        if (tieParties.size() < 2) { //  must have at least 2 in order to compare
            throw new RuntimeException("CoinFlip argument requires an arrayList of size >= 2");
        }
        double randomNumber = Math.random(); //  provides a random number in the range [0,1)
        Party winner = (randomNumber >= 0.5) ? tieParties.get(0) : tieParties.get(1); //  return object 1 if number >= .5, otherwise object 2
        audit.LOG("Coin Flip winner\t" + winner.getName());
        return winner;
    }

    protected static Candidate coinFlipCandidate(ArrayList<Candidate> tieCandidates) {
        /**
         * This function generates a random number to simulate a coin flip for tie events.
         * This version is for determining the winner of two tied Candidates
         * @return Candidate: This value represents the winner of the coinflip
         **/
        audit.LOG("Coin Flip");
        if (tieCandidates.size() < 2) { //  provides a random number in the range [0,1)
            throw new RuntimeException("CoinFlip argument requires an arrayList of size >= 2");
        }
        double randomNumber = Math.random(); //  provides a random number in the range [0,1)
        Candidate winner = (randomNumber >= 0.5) ? tieCandidates.get(0) : tieCandidates.get(1); //  return object 1 if number >= .5, otherwise object 2
        audit.LOG("winner\t" + winner.toString());
        return winner;
    }


    protected ArrayList<Party> GetTiesAmongParties(Map<Party, Integer> map) {
        /**
         * @param map a map where key is party and value is integer
         * @return ArrayList<Party> list of all tie parties
         */
        audit.LOG("Getting Ties Among Parties");
        ArrayList<Integer> remainder_list = new ArrayList<Integer>(map.values());
        ArrayList<Party> dup_parties = new ArrayList<>();
        int length = remainder_list.size();
        for (int i = 0; i < length; i++) {
            //int currentRemainder = remainder_list.get(i);
            Set<Integer> set = new HashSet<Integer>();
            for (int r : remainder_list) {
                if (set.add(r) == false) {
                    return getKeyGivenValue(map, r);
                    // given key, get value Party object
                }
            }
        }
        audit.LOG("Ties found\t" + dup_parties);
        return dup_parties;
    }

    protected ArrayList<Candidate> GetTiesAmongCandidates(Map<Candidate, Integer> map) {
        /**
         * @param map a map where key is Candidate and value is integer
         * @return ArrayList<Party> list of all tie candidates
         */
        audit.LOG("Getting ties betwen Candidates\n");
        ArrayList<Integer> vote_list = new ArrayList<Integer>(map.values());
        ArrayList<Candidate> dup_candidates = new ArrayList<>();
        Set<Integer> set = new HashSet<Integer>();
        for (int v : vote_list) {
            if (set.add(v) == false) {
                return getKeyGivenValue(map, v); // given key, get value Party object
                // given key, get value Party object
            }
        }
        audit.LOG("Ties found\t" + dup_candidates);
        return dup_candidates;
    }


    protected void DistributeSeats() {
        /**
         * This distributes seats to parties
         * @return none
         */
        audit.LOG("Distributing Seats");
        double numSeats;
        double numBallots;
        if(this instanceof CPL){
            numSeats = ((CPL) this).electionInfor.numberOfSeats;
            numBallots = ((CPL) this).electionInfor.numberOfBallots;
        } else {
            numSeats = this.electionInfo.getNumberOfSeats();
            numBallots = this.electionInfo.getNumberOfBallots();
        }

        double quota = (numBallots / numSeats);
//        System.out.println("number of seats "+numSeats);
//        System.out.println("number of vote "+numBallots);
//        System.out.println("number of quota "+quota);


        int length = partyList.size();
        int totalSeatsLeftToDistribute = (int) numSeats;
        Map<Party, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Party currentParty = partyList.get(i);
            int vote = currentParty.getPartyVotes();
            int seats = (int) (vote / quota);
            double remainder = vote % quota;
            partyList.get(i).setNumberOfSeats(seats);
//            System.out.println("party "+partyList.get(i).getName()+"has votes of "+vote+" and has seats "+seats);
            // LOG: first allocation, partyList.get(i) stores the current party
            // that receives seats
            totalSeatsLeftToDistribute -= seats;
            map.put(currentParty, (int) remainder);
        }
        // done with first allocation
        // now get a list of <remainder,vote> sorted by key remainder
        if (totalSeatsLeftToDistribute > 0) { // some seat left
            // sort the map by values
            // the expression below to sort map by its value
            // ascending order !
            Map<Party, Integer> sortedMap = map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            // extract Parties where all are sorted
            List<Party> sortedPartyList = new ArrayList<Party>(sortedMap.keySet());
            ArrayList<Party> ties_partyList = GetTiesAmongParties(sortedMap);
            // while loop below distributes left over seats
            outterloop:
            while (totalSeatsLeftToDistribute != 0) {
                for (int i = length - 1; i >= 0; i--) { // loop through partyList
                    Party currentParty = sortedPartyList.get(i);
                    if (totalSeatsLeftToDistribute >= 1) {
                        // at least 1 seat left to give away
                        int newSeats = currentParty.getNumberOfSeats() + 1;
                        if (ties_partyList.contains(currentParty)) {
                            // time to run coinFlip when only 1 seat left and multiple ties
                            // (totalSeatsLeftToDistribute >= 1 || totalSeatsLeftToDistribute >= numSeats)
                            while (ties_partyList.size() >=1 && (totalSeatsLeftToDistribute >= 1)){
                                double randomNumber = Math.random();
                                int lengthOfTies_partyList = ties_partyList.size();
                                randomNumber = randomNumber * lengthOfTies_partyList; //0->length-1;
                                int index = (int) randomNumber;
                                currentParty = ties_partyList.get(index);
                                ties_partyList.remove(currentParty);
                                currentParty.setNumberOfSeats(newSeats);
                                totalSeatsLeftToDistribute -= 1;
                            }
                            // LOG: tie happen and current party is the chosen one
                            // override currentParty with the randomly chosen one
                        }
                        if (newSeats <= currentParty.getCandidateList().size()){
                            currentParty.setNumberOfSeats(newSeats);
                            totalSeatsLeftToDistribute -= 1;
                        }

                    } else {
                        // no more seat left
                        break outterloop;
                    }
                }
            }
           // System.out.println("=====END=====");
        }


    }

    public abstract ArrayList<Candidate> PickWinners();


    public ArrayList<Party> getPartyList() {
        return partyList;
    }

}


/**
 * <h1>CPL</h1>
 * <p>
 * The CPL contains the running and counting algorithm for a CPL type election.
 * This Class handles parsing the ballot and electionInfo files and extracting information
 * to set up the party list and count ballots. It calls the method in Party that increments vote count.
 * </p>
 *
 * @author Erin Seichter, Griffin Higley, Dat Le
 * @version 1.0
 */
class CPL extends Election {
    protected CplElectionInfor electionInfor;
    private static Audit audit;

    @Override
    public String toString() {
        return "CPL{" +
                "ballotList=" + ballotList +
                ", electionInfo=" + electionInfo +
                ", partyList=" + partyList +
                '}';
    }

    /**
     * Constructor for the Election Class. Takes in FIVE parameters
     *
     * @param ballotList    List of ballot strings for this election
     * @param electionInfo  election meta data
     * @param candidateList List of candidates for this election
     * @param partyList     list of parties for this election
     * @param electionInfor CPL election meta data
     */
    public CPL(ArrayList<Ballot> ballotList, ElectionInfo electionInfo, ArrayList<Candidate> candidateList,
               ArrayList<Party> partyList, CplElectionInfor electionInfor) {
        super(ballotList, electionInfo, candidateList, partyList);
        this.electionInfor = electionInfor;
        audit = Audit.getInstance();
    }

    public void runDistributeSeats() {
        DistributeSeats();
    }

    public int Run() throws IOException {
        /**
         * This function loops through all election ballots and runs the Count method on each ballot
         * @return int - 0 for success, 1 for error
         **/
        //  call Count() for each ballot
        audit.LOG("Election Running");
        this.ballotList.stream().forEach(ballot -> {
            Count(ballot);
        });
        audit.LOG("Election DistributeSeats");
        DistributeSeats();
        audit.LOG("Election PickWinners");
//        System.out.println("running this");
        PickWinners();
        audit.LOG("Election results Report");
        Results results = new Results(this);
        results.Report();
        audit.LOG("Election Media Report");
        Media media = new Media(this);
        media.Report();

        return 0;
    }


    protected void Count(Ballot ballot) {
        /**
         * distributes all votes
         */
        // partyList is complete
        // ballotList is ready
        // function increase vote of party that ballot is for
        audit.LOG("Counting Vote\t");
        int index = ballot.score();
        Party party = partyList.get(index);
        party.addPartyVote();
        audit.LOG("Votes goes to Party\t" + party.getName());
        // LOG: party gets votes
    }

    public ArrayList<Candidate> PickWinners() {
        /**
         * @return list of winners
         */
        // as order of candidateList is pre-determined, no ties among candidates in CPL
        ArrayList<Candidate> winners = new ArrayList<>();
        int length = partyList.size();
        for (int i = 0; i < length; i++) {
            int seats = partyList.get(i).getNumberOfSeats();
            //System.out.println("party: "+partyList.get(i).getName()+" has seats==="+seats);
            int iterator = 0;
            while (iterator < seats && iterator < partyList.get(i).getCandidateList().size()) {
                Candidate winner = (partyList.get(i).getCandidateList()).get(iterator);
                // LOG: get winners
                audit.LOG("Winner\t" + winner);
                winners.add(winner);
                iterator++;
            }
        }
        return winners;
    }

}

/**
 * <h1>OPL</h1>
 * <p>
 * The OPL contains the running and counting algorithm for a OPL type election.
 * This Class handles parsing the ballot and electionInfo files and extracting information
 * to set up the candidate list and count ballots. It calls the method in Candidate that increments vote count.
 * </p>
 *
 * @author Erin Seichter, Griffin Higley, Dat Le
 * @version 1.0
 */

class OPL extends Election {
    /**
     * Constructor for the OPL Class. Takes in four parameters
     *
     * @param ballotList    List of ballot strings for this election
     * @param electionInfo  election meta data
     * @param candidateList List of candidates for this election
     * @param partyList     list of parties for this election
     */
    private static Audit audit;

    public OPL(ArrayList<Ballot> ballotList, ElectionInfo electionInfo, ArrayList<Candidate> candidateList,
               ArrayList<Party> partyList) {
        super(ballotList, electionInfo, candidateList, partyList);
        audit = Audit.getInstance();
    }

    public int Run() throws IOException {
        /**
         * This function loops through all election ballots and runs the Count method on each ballot
         * @return int - 0 for success, 1 for error
         **/
        audit.LOG("ELECTION RUNNING");
        this.ballotList.stream().forEach(ballot -> {
            audit.LOG("Counting ballot\t" + ballot);
            Count(ballot);
        });
        audit.LOG("ELECTION DistributeSeats");
        DistributeSeats();
        audit.LOG("ELECTION DistributeSeats");
//        System.out.println("running that");
        PickWinners();
        audit.LOG("ELECTION Results Report");
        Results results = new Results(this);
        results.Report();
        audit.LOG("ELECTION Media Report");
        Media media = new Media(this);
        media.Report();
        audit.LOG("ELECTION OVER");
        return 0;

    }

    protected void Count(Ballot ballot) {
        /**
         * @param ballot a ballot
         */
        // each ballot counts for the specific candidate as well as for the party.
        // this functions will increase candidates'score and associated party vote
        // ballotList = [',,,,1',',1,,,',etc]
        // String currentBallot = ballotList.get(i);
        audit.LOG("COUNTING\t" + ballot);
        int index = ballot.score();
        Candidate currentCandidate = candidateList.get(index);
        currentCandidate.IncreaseVoteCount();
        // LOG: candidate gets vote
        currentCandidate.getParty().addPartyVote();
        // LOG: party gets vote
        // increase vote of each candidate's party
        audit.LOG("Vote goes to candidate\t" + currentCandidate);
    }

    public ArrayList<Candidate> PickWinners() {
        /**
         * @return list of winners
         */
//        System.out.println("===BEGIN===");
        audit.LOG("Picking winners\n");
        // in OPL, candidates appeared unordered inside candidate list of each party
        ArrayList<Candidate> winners = new ArrayList<>();
        // for loop below goes thru each party and sort its candidate list
        int partyListLength = partyList.size();
        for (int k = 0; k < partyListLength; k++) {
            Map<Candidate, Integer> candidateMap = new HashMap<>();
            // for loop below contructs hash map of Vote:Candidate pairs
            ArrayList<Candidate> currentPartyCandidateList = partyList.get(k).getCandidateList();
            int currentPartyCandidateListLength = currentPartyCandidateList.size();
            for (int j = 0; j < currentPartyCandidateListLength; j++) {
                Candidate currentCandidate = currentPartyCandidateList.get(j);
                candidateMap.put(currentCandidate, currentCandidate.getScore());
            }

            // the expression below supposes to sort candidateMap by values
            Map<Candidate, Integer> sortedMap = candidateMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            List<Candidate> sortedCurrentPartyCandidateList = new ArrayList<Candidate>(sortedMap.keySet());
            ArrayList<Candidate> ties_candidate_list = GetTiesAmongCandidates(sortedMap);
            int totalSeatsLeft = partyList.get(k).getNumberOfSeats();
//            System.out.println("Name of party: "+partyList.get(k).getName());
//            System.out.println("party total seat left: "+totalSeatsLeft);

            int iterator = sortedCurrentPartyCandidateList.size() - 1;
//            System.out.println("first cand: "+sortedCurrentPartyCandidateList.get(0).getName());
//            if (sortedCurrentPartyCandidateList.size()>1){
//                System.out.println("second cand: "+sortedCurrentPartyCandidateList.get(1).getName());
//            }
            while (totalSeatsLeft > 0 && iterator >= 0 && iterator < partyList.get(k).getCandidateList().size()) {
                Candidate currentCandidateOfTheParty = sortedCurrentPartyCandidateList.get(iterator);
                if (ties_candidate_list.contains(currentCandidateOfTheParty)) {
//                    System.out.println("TIE !");
                    // bunch of other candidates with the same votes. coin flipping time!
                    // LOG: tie happens, currentCandidateOfTheParty is the chosen candidate from tie
                    // override the currentCandidateOfTheParty with randomly chosen one
                    while (ties_candidate_list.size() >= 1 && totalSeatsLeft >= 1) {
                        double randomNumber = Math.random();
                        int lengthOfTies_candidateList = ties_candidate_list.size();
                        randomNumber = randomNumber * lengthOfTies_candidateList;
                        int index = (int) randomNumber;
                        currentCandidateOfTheParty = ties_candidate_list.get(index);
                        ties_candidate_list.remove(currentCandidateOfTheParty);
                        // totalSeatsLeft -= 1;
                    }
//                    System.out.println(currentCandidateOfTheParty.getName());
//                    System.out.println("Done breaking tie");
                }
                totalSeatsLeft--;
//                System.out.println(currentCandidateOfTheParty.getName());
                audit.LOG("Adding winner \t" + currentCandidateOfTheParty);
                winners.add(currentCandidateOfTheParty);
                sortedCurrentPartyCandidateList.remove(currentCandidateOfTheParty);
//                System.out.println("lucky candidate: "+currentCandidateOfTheParty.getName());
//                System.out.println("seats left: "+totalSeatsLeft);
                //LOG: get winners and currentCandidateOfTheParty
                iterator-=1;
            }

        }
//        System.out.println("===END===");
        return winners;
    }

    @Override
    public String toString() {
        return "OPL{" +
                "ballotList=" + ballotList +
                ", electionInfo=" + electionInfo +
                ", partyList=" + partyList +
                '}';
    }
}
