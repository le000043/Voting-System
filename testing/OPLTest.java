

import java.io.*;
import java.net.URL;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OPLTest {
    @Test //Test ID: VCS_2
    public void PickWinner() {
        Candidate c1 = new Candidate("Dom1", "Democrat");
        c1.IncreaseVoteCount();
        c1.IncreaseVoteCount();

        Candidate c2 = new Candidate("Dom2", "Democrat");
        c2.IncreaseVoteCount();
        c2.IncreaseVoteCount();

        Candidate c3 = new Candidate("Dom3", "Democrat");
        c3.IncreaseVoteCount();

        Candidate c4 = new Candidate("Dom4", "Republican");
        c4.IncreaseVoteCount();
        c4.IncreaseVoteCount();

        Candidate c5 = new Candidate("Dom5", "Republican");
        c5.IncreaseVoteCount();
        c5.IncreaseVoteCount();

        Candidate c6 = new Candidate("Dom6", "Republican");
        c6.IncreaseVoteCount();

        Party party1 = new Party("Democrat");
        Party party2 = new Party("Republican");
        c1.assignParty(party1);
        c2.assignParty(party1);
        c3.assignParty(party1);
        c4.assignParty(party2);
        c5.assignParty(party2);
        c6.assignParty(party2);
        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party1.addCandidate(c3);
        party2.addCandidate(c4);
        party2.addCandidate(c5);
        party2.addCandidate(c6);
        ArrayList<Party> partyList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);

        Ballot ballot1 = new Ballot(",1,,,,,");

        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        ArrayList<Candidate> candidateList = new ArrayList<>();
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);
        candidateList.add(c4);
        candidateList.add(c5);
        candidateList.add(c6);

        ElectionInfo electionInfo = new ElectionInfo(4, 6, 1);
        OPL newOPL = new OPL(ballotList, electionInfo, candidateList, partyList);
        party1.setNumberOfSeats(2);
        party2.setNumberOfSeats(2);
        ArrayList<Candidate> ExpectedWinners = new ArrayList<>();
        ExpectedWinners.add(c1);
        ExpectedWinners.add(c2);
        ExpectedWinners.add(c4);
        ExpectedWinners.add(c5); //c3, c6 lost

        ArrayList<Candidate> ActualWinners = newOPL.PickWinners();
        int length = ActualWinners.size();
        for (int i = 0; i < length; i++) {

            assertEquals(true, ExpectedWinners.contains(ActualWinners.get(i)));
        }
        assertEquals(false, ExpectedWinners.contains(c3));
        assertEquals(false, ExpectedWinners.contains(c6));

    }
    @Test public void run01() throws IOException {
        Ballot ballot1 = new Ballot("1,,,,");
        Ballot ballot2 = new Ballot(",1,,,");
        Ballot ballot3 = new Ballot(",,1,,");
        Ballot ballot4 = new Ballot(",,,1,");
        Ballot ballot5 = new Ballot(",,,,1");
        Ballot ballot6 = new Ballot("1,,,,");
        Ballot ballot7 = new Ballot(",1,,,");
        Ballot ballot8 = new Ballot("1,,,,");
        Ballot ballot9 = new Ballot(",,,1,");
        Ballot ballot10 = new Ballot(",1,,,");
        Ballot ballot11 = new Ballot(",,,1,");
        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        ballotList.add(ballot2);
        ballotList.add(ballot3);
        ballotList.add(ballot4);
        ballotList.add(ballot5);
        ballotList.add(ballot6);
        ballotList.add(ballot7);
        ballotList.add(ballot8);
        ballotList.add(ballot9);
        ballotList.add(ballot10);
        ballotList.add(ballot11);

        Party party1 = new Party("d");
        Party party2 = new Party("r");
        Party party3 = new Party("g");

        Candidate c1 = new Candidate("albert", "d");
        c1.assignParty(party1);
        party1.addCandidate(c1);

        Candidate c2 = new Candidate("bob", "r");
        c2.assignParty(party2);
        party2.addCandidate(c2);

        Candidate c3 = new Candidate("calvin", "r");
        c3.assignParty(party2);
        party2.addCandidate(c3);

        Candidate c4 = new Candidate("david", "d");
        c4.assignParty(party1);
        party1.addCandidate(c4);

        Candidate c5 = new Candidate("edgar", "g");
        c5.assignParty(party3);
        party3.addCandidate(c5);

        ArrayList<Candidate> candidateList = new ArrayList<>();
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);
        candidateList.add(c4);
        candidateList.add(c5);

        ArrayList<Party> partyList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);
        partyList.add(party3);


        ElectionInfo electionInfo = new ElectionInfo(3, 5, 11);
        OPL newOPL = new OPL(ballotList, electionInfo, candidateList, partyList);
        newOPL.Run();
        ArrayList<Candidate> ActualWinners = newOPL.PickWinners();
        int length = ActualWinners.size();
        assertEquals(3,length);





    }
    @Test //Test ID: VCS_3
    public void coinFlipOPL() {
        Party party1 = new Party("Democrat");
        Candidate c1 = new Candidate("Dom", "Democrat");
        Ballot ballot1 = new Ballot(",1,,,,,");

        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        c1.assignParty(party1);

        Candidate c2 = new Candidate("Dem.Dom", "Democrat");
        c2.assignParty(party1);

        ArrayList<Candidate> candidateList = new ArrayList<>();
        candidateList.add(c1);
        candidateList.add(c2);
        ArrayList<Party> partyList = new ArrayList<>();
        partyList.add(party1);
        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party1.setNumberOfSeats(1);
        ElectionInfo electionInfo = new ElectionInfo(1, 2, 1);
        OPL newOPL = new OPL(ballotList, electionInfo, candidateList, partyList);
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            party1.setNumberOfSeats(1);
            ArrayList<Candidate> oList = newOPL.PickWinners();
            if (oList.get(0).getName().equals("Dom")) {
                first.add(1);
            } else {
                second.add(1);
            }
        }
        double firstWinRate = (first.size()) / 100.0;
        double secondWinRate = (second.size()) / 100.0;

        assertEquals(true, firstWinRate >= 0.38);
        assertEquals(true, secondWinRate >= 0.38);
    }

    @Test //Test ID: VCS_4
    public void RunGeneral() throws IOException {
        File file = new File("./testing/cpl_electionInfo_file.csv");
        String fileName = "./testing/cpl_electionInfo_file.csv";
        String electionType = "CPL";
        // party1: 6 ballots->3 seats
        //party2: 2 ballots->1  seats
        // party3: 2 ballots-> 1 seats
        // quota 10 votes: 5 seats = 2 votes/seat
        //d1:2 d2:3 d3:1
        //r1:1,r2:1 win:r2
        // g1:0, g2:1 win g2
        // win:d1,2,3, r2, g2
        Ballot ballot1 = new Ballot(",1,,,,,");
        Ballot ballot2 = new Ballot("1,,,,,,");
        Ballot ballot3 = new Ballot(",1,,,,,");
        Ballot ballot4 = new Ballot(",1,,,,,");
        Ballot ballot5 = new Ballot(",,1,,,,");
        Ballot ballot6 = new Ballot("1,,,,,,");

        Ballot ballot7 = new Ballot(",,,1,,,");
        Ballot ballot8 = new Ballot(",,,1,,,");

        Ballot ballot9 = new Ballot(",,,,,,1");
        Ballot ballot10 = new Ballot(",,,,,,1");


        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        ballotList.add(ballot2);
        ballotList.add(ballot3);
        ballotList.add(ballot4);
        ballotList.add(ballot5);
        ballotList.add(ballot6);
        ballotList.add(ballot7);
        ballotList.add(ballot8);
        ballotList.add(ballot9);
        ballotList.add(ballot10);


        Party party1 = new Party("Democrat");
        Party party2 = new Party("Republic");
        Party party3 = new Party("Green");
        Candidate c1 = new Candidate("Dom", "Democrat");
        c1.assignParty(party1);
        Candidate c2 = new Candidate("Dem.Dom", "Democrat");
        c2.assignParty(party1);
        Candidate c3 = new Candidate("unknown_dude", "Democrat");
        c3.assignParty(party1);
        Candidate c4 = new Candidate("Daniel", "Republic");
        c4.assignParty(party2);
        Candidate c5 = new Candidate("Rep.Dude", "Republic");
        c5.assignParty(party2);
        Candidate c6 = new Candidate("John", "Green");
        c6.assignParty(party3);
        Candidate c7 = new Candidate("no_chance", "Green");
        c7.assignParty(party3);
        ArrayList<Party> partyList = new ArrayList<>();
        ArrayList<Candidate> candidateList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);
        partyList.add(party3);
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);
        candidateList.add(c4);
        candidateList.add(c5);
        candidateList.add(c6);
        candidateList.add(c7);
        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party1.addCandidate(c3);
        party2.addCandidate(c4);
        party2.addCandidate(c5);
        party3.addCandidate(c6);
        party3.addCandidate(c7);


        ArrayList<String> partyNames = new ArrayList<>();
        partyNames.add("Democrat");
        partyNames.add("Republic");
        partyNames.add("Green");
        ArrayList<Candidate> ExpectedWinners = new ArrayList<>();
        ExpectedWinners.add(c1);
        ExpectedWinners.add(c2);
        ExpectedWinners.add(c3);
        ExpectedWinners.add(c4);
        ExpectedWinners.add(c7); // c6 c5 lost

        ElectionInfo electionInfo = new ElectionInfo(5, 7, 10);
        //CplElectionInfor electionInfor = new CplElectionInfor(5,7,10,3,partyNames);
        OPL newOPL = new OPL(ballotList, electionInfo, candidateList, partyList);
        newOPL.Run();
        assertEquals(6, party1.getPartyVotes());
        assertEquals(2, party2.getPartyVotes());
        assertEquals(2, party3.getPartyVotes());
        assertEquals(3, party1.getNumberOfSeats());
        assertEquals(1, party2.getNumberOfSeats());
        assertEquals(1, party3.getNumberOfSeats());
        ArrayList<Candidate> ActualWinners = newOPL.PickWinners();
        assertEquals(party1.getNumberOfSeats(),3);
        assertEquals(party2.getNumberOfSeats(),1);
        assertEquals(party3.getNumberOfSeats(),1);
        int length = ActualWinners.size();
        for (int i = 0; i < length; i++) {

            assertEquals(true, ExpectedWinners.contains(ActualWinners.get(i)));
        }
        assertEquals(false, ActualWinners.contains(c5));
        assertEquals(false, ActualWinners.contains(c6));

        assertEquals(true, ActualWinners.contains(c1));
        assertEquals(true, ActualWinners.contains(c2));
        assertEquals(true, ActualWinners.contains(c3));
        assertEquals(true, ActualWinners.contains(c4));
        assertEquals(true, ActualWinners.contains(c7));



    }
}