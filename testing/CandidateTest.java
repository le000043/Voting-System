import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandidateTest {
    String partyName = "R";
    Party party = new Party(partyName);
    String name = "Test";

    @Test //Test ID: VCS_8
    public void increaseVoteCount() {
        Candidate candidate = new Candidate(name, partyName);
        candidate.assignParty(party);
        assertEquals(0, candidate.getScore());
        candidate.IncreaseVoteCount();
        assertEquals(1, candidate.getScore());
    }

    @Test //Test ID: VCS_9
    public void getName() {
        Candidate candidate = new Candidate(name, partyName);
        candidate.assignParty(party);
        assertEquals(name, candidate.getName());
    }

    @Test //Test ID: VCS_10
    public void getScore() {
        Candidate candidate = new Candidate(name, partyName);
        candidate.assignParty(party);
        assertEquals(0, candidate.getScore());
    }

    @Test //Test ID: VCS_11
    public void getParty() {
        Candidate candidate = new Candidate(name, partyName);
        candidate.assignParty(party);
        assertEquals(party, candidate.getParty());
    }

    @Override
    public String toString() {
        return "CandidateTest{" +
                "partyName='" + partyName + '\'' +
                ", party=" + party.getName() +
                ", name='" + name + '\'' +
                '}';
    }
}