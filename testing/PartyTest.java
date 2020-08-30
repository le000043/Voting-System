import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;


public class PartyTest {
    String partyName = "Test";
    Candidate jimBob = new Candidate("bob", partyName);

    @Test //Test ID: VCS_12
    public void addVote() {
        Party party = new Party(partyName);
        assertEquals(0, party.getPartyVotes());
        party.addPartyVote();
        assertEquals(1, party.getPartyVotes());
    }

    @Test //Test ID: VCS_13
    public void addCand() {
        Party party = new Party(partyName);
        party.addCandidate(jimBob);
        assertEquals(1, party.candidateList.size());
    }

    @Test //Test ID: VCS_14
    public void getSeats() {
        Party party = new Party(partyName);
        assertEquals(0, party.getNumberOfSeats());
    }

    @Test //Test ID: VCS_15
    public void setSeats() {
        Party party = new Party(partyName);
        party.setNumberOfSeats(3);
        assertEquals(3, party.getNumberOfSeats());
    }

    @Test //Test ID: VCS_16
    public void setPartyVotes() {
        Party party = new Party(partyName);
        party.addPartyVote();
        assertEquals(1, party.getPartyVotes());
    }

    @Test //Test ID: VCS_17
    public void getName() {
        Party party = new Party(partyName);
        assertEquals(true, party.getName().equals(partyName));

    }

    @Test //Test ID: VCS_18
    public void getVotes() {
        Party party = new Party(partyName);
        assertEquals(0, party.getPartyVotes());
        party.addPartyVote();
        assertEquals(1, party.getPartyVotes());
    }

}

