import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <h1>Election Builder</h1>
 *
 * <p>
 * ElectionBuilder is a static class that parses the input file and extracts the information for use in the system.
 * </p>
 *
 * @author Griffin Higley
 * @version 1.0
 */
public class ElectionBuilder {
    private static Audit audit;
    private static HashMap<String, ArrayList<Candidate>> partyStringToCandidateHashMap = new HashMap();

    private static ArrayList<Party> generatePartyList(ArrayList<Candidate> candidateList) {
        /**
         * This function generates the party list for the election and adds candidates to the party.
         *
         * @return ArrayList<Party> - the list of parties
         **/
        audit = Audit.getInstance();
        audit.LOG("Building generatePartyList\n");
        HashMap<String, Party> partyHashMap = new HashMap();
        candidateList.stream().forEach(candidate -> {
            if (partyHashMap.containsKey(candidate.getPartyString())) {
                candidate.assignParty(partyHashMap.get(candidate.getPartyString()));
                partyHashMap.get(candidate.getPartyString()).addCandidate(candidate);
            } else {
                Party party = new Party(candidate.getPartyString());
                party.addCandidate(candidate);
                candidate.assignParty(party);
                partyHashMap.put(candidate.getPartyString(), party);
            }
        });
        return new ArrayList<Party>(partyHashMap.values());
    }

    private static ArrayList<Candidate> generateCandidateList(ArrayList<String> candidateStringList) {
        /**
         * This function generates the candidate list for the election and initializes each candidate.
         *
         * @return ArrayList<Candidate> - the list of candidates
         **/
        audit = Audit.getInstance();
        audit.LOG("Building generateCandidateList\n");
        ArrayList<Candidate> candidates = new ArrayList<>();
        candidateStringList.stream().forEach(parseString -> {
            String candidateString = parseString.substring(1, parseString.length() - 1);
            String[] candidateParts = candidateString.split(",");
            Candidate candidate;
            if (candidateParts.length > 3) {
                candidate = new Candidate(candidateParts[0], Integer.parseInt(candidateParts[2]), candidateParts[1]);
            } else {
                candidate = new Candidate(candidateParts[0], candidateParts[1]);
            }
            candidates.add(candidate);
        });
        return candidates;
    }

    private static ArrayList<Ballot> generateBallotList(ArrayList<String> ballotStringList) {
        /**
         * This function generates the ballot list for the election, splitting them up so they can be easily read one ballot at a time.
         *
         * @return ArrayList<Ballot> - the list of ballots
         **/
        audit = Audit.getInstance();
        audit.LOG("Building generateBallotList\n");
        ArrayList<Ballot> ballots = new ArrayList<>();
        ballotStringList.stream().forEach(ballotString -> {
            ballots.add(new Ballot(ballotString));
        });
        return ballots;
    }

    private static ElectionInfo generateElectionInfo(ArrayList<String> electionInfoList) {
        /**
         * This function collects the election data (# seats, # ballots, # candidates) and puts it in an ElectionInfo Object
         *
         * @return ElectionInfo - the election data
         **/
        audit = Audit.getInstance();
        audit.LOG("Building generateElectionInfo\n");
        return new ElectionInfo(Integer.parseInt(electionInfoList.get(0)),
                Integer.parseInt(electionInfoList.get(2)),
                Integer.parseInt(electionInfoList.get(1)));
    }

    private static CplElectionInfor generateElectionInfo(ArrayList<String> electionInfoList, String partyStringList) {
        /**
         * This function collects the election data for a CPL election (# seats, # ballots, # candidates, party names) and puts it in an ElectionInfo Object
         *
         * @return CplElectionInfo - the CPL election data
         **/
        ArrayList<String> partyNames = ElectionBuilder.preparePartyNames(partyStringList);
        audit = Audit.getInstance();
        audit.LOG("Building generateElectionInfo\n");
        return new CplElectionInfor(Integer.parseInt(electionInfoList.get(1)),
                Integer.parseInt(electionInfoList.get(3)),
                Integer.parseInt(electionInfoList.get(2)),
                partyNames.size(),
                partyNames
        );
    }

    private static ArrayList<String> preparePartyNames(String parties) {
        /**
         * This function parses all the CPL party names and places them in a list for easy reading later
         *
         * @return ArrayList<String> - The list of party names
         **/
        audit = Audit.getInstance();
        audit.LOG("Building preparePartyNames\n");
        String[] partyNames = parties.substring(1, parties.length() - 1).split(",");
        ArrayList<String> partyNamesList = new ArrayList<>();
        for (String name : partyNames) {
            partyNamesList.add(name);
        }
        return partyNamesList;
    }

    public static Election build(String electionType, ArrayList<String> electionInformation,
                                 ArrayList<String> candidateList, ArrayList<String> ballotList) {
        audit = Audit.getInstance();
        audit.LOG("Building Election\n");
        /**
         * This function calls all the generator functions and builds the system for an OPL type election
         *
         * @return Election - the election object that will run the system and count ballots to determine the winners
         **/

        ArrayList<Ballot> ballots = ElectionBuilder.generateBallotList(ballotList);
        ElectionInfo electionInfo = ElectionBuilder.generateElectionInfo(electionInformation);
        ArrayList<Candidate> candidates = ElectionBuilder.generateCandidateList(candidateList);
        ArrayList<Party> parties = ElectionBuilder.generatePartyList(candidates);
        OPL opl = new OPL(ballots, electionInfo, candidates, parties);
        return opl;
    }

    public static Election build(String electionType, ArrayList<String> electionInformation,
                                 ArrayList<String> candidateList, ArrayList<String> ballotList, String CPLPartyList) {
        /**
         * This function calls all the generator functions and builds the system for a CPL type election
         *
         * @return Election - the election object that will run the system and count ballots to determine the winners
         **/
        audit = Audit.getInstance();
        audit.LOG("Building Election\n");
        ArrayList<Ballot> ballots = ElectionBuilder.generateBallotList(ballotList);
        CplElectionInfor CplElectionInfo = ElectionBuilder.generateElectionInfo(electionInformation, CPLPartyList);
        ElectionInfo electionInfo = ElectionBuilder.generateElectionInfo(electionInformation);
        ArrayList<Candidate> candidates = ElectionBuilder.generateCandidateList(candidateList);
        ArrayList<Party> parties = ElectionBuilder.generatePartyList(candidates);

        CPL cpl = new CPL(ballots, electionInfo, candidates, parties, CplElectionInfo);
        return cpl;
    }
}
