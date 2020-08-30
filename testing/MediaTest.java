

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MediaTest {

    @Test //Test ID: VCS_20
    public void report() throws IOException {
        File testFile = new File("./testing/test_media.txt");
        Ballot ballot = new Ballot("1,,");
        ArrayList<Ballot> ballots = new ArrayList<>();
        ballots.add(ballot);

        ElectionInfo electionInfo = new ElectionInfo(1, 3, 1);

        Candidate c1 = new Candidate("john", "R");
        Candidate c2 = new Candidate("Zoe", "D");
        Candidate c3 = new Candidate("Deb", "I");
        ArrayList<Candidate> candidates = new ArrayList<>();
        candidates.add(c1);
        candidates.add(c2);
        candidates.add(c3);

        Party r = new Party("R");
        Party d = new Party("D");
        Party i = new Party("I");
        ArrayList<Party> parties = new ArrayList<>();
        parties.add(r);
        parties.add(d);
        parties.add(i);

        r.addCandidate(c1);
        d.addCandidate(c1);
        i.addCandidate(c3);

        c1.assignParty(r);
        c2.assignParty(d);
        c3.assignParty(i);

        OPL opl = new OPL(ballots, electionInfo, candidates, parties);

        c1.IncreaseVoteCount();

        Media media = new Media(opl);
        media.Report();
    }
}
