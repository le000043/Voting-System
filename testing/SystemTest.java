import org.junit.Test;

import java.io.IOException;

public class SystemTest {
    @Test //Test ID: VCS_24
    public void main() {
        ElectionDriverTest electionDriverTest = new ElectionDriverTest();
        try {
            electionDriverTest.main();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CandidateTest candidateTest = new CandidateTest();
        candidateTest.getName();
        candidateTest.getParty();
        candidateTest.getScore();
        candidateTest.increaseVoteCount();

        PartyTest partyTest = new PartyTest();
        partyTest.addCand();
        partyTest.addVote();
        partyTest.getName();
        partyTest.getSeats();
        partyTest.getVotes();
        partyTest.setPartyVotes();
        partyTest.setSeats();

        CPLTest cplTest = new CPLTest();
        cplTest.coinFlipTest();
//        cplTest.DistributeVoteTest();
        cplTest.getPartyListTest();
        try {
            cplTest.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OPLTest oplTest = new OPLTest();
        oplTest.coinFlipOPL();
        oplTest.PickWinner();
        try {
            oplTest.RunGeneral();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultsTest resultsTest = new ResultsTest();
        try {
            resultsTest.report();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaTest mediaTest = new MediaTest();
        try {
            mediaTest.report();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AuditTest auditTest = new AuditTest();
        try {
            auditTest.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BallotTest ballotTest = new BallotTest();
        try {
            ballotTest.score();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
