

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CPLTest {
    @Test //Test ID: VCS_22
    public void getPartyListTest() {
        Party party1 = new Party("Republican");
        Party party2 = new Party("Democratic");
        Party party3 = new Party("G");
        ArrayList<Party> partyList = new ArrayList<>();
        ArrayList<String> partyNames = new ArrayList<>();
        partyNames.add("Republican");
        partyNames.add("Democratic");
        partyList.add(party1);
        partyList.add(party2);
        ArrayList<Candidate> candidateList = new ArrayList<>();
        ArrayList<Ballot> ballotList = new ArrayList<>();
        ElectionInfo electionInfo = new ElectionInfo(1, 2, 1);
        CplElectionInfor electionInfor = new CplElectionInfor(1, 2, 1, 2, partyNames);
        CPL newCPL = new CPL(ballotList, electionInfo, candidateList, partyList, electionInfor);
        ArrayList<Party> actual = newCPL.getPartyList();
        int length = partyList.size();
        for (int i = 0; i < length; i++) {
            assertEquals(true, partyList.contains(actual.get(i)));
        }
        assertEquals(false, actual.contains(party3));
    }

    @Test //Test ID: VCS_5
    public void coinFlipTest() {
        Ballot ballot1 = new Ballot("1,,");
        Ballot ballot2 = new Ballot(",1,");
        Ballot ballot3 = new Ballot(",,1");
        Party party1 = new Party("Republican");
        Party party2 = new Party("Democratic");
        Party party3 = new Party("Green");
        ArrayList<Party> partyList = new ArrayList<>();
        Candidate c1 = new Candidate("Dom", "Republican");
        Candidate c2 = new Candidate("Dan", "Democratic");
        Candidate c3 = new Candidate("Diggle", "Green");

        ArrayList<Candidate> candidateList = new ArrayList<>();
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);

        party1.addCandidate(c1);
        party2.addCandidate(c2);
        party3.addCandidate(c3);

        ArrayList<String> partyNames = new ArrayList<>();
        ElectionInfo electionInfo = new ElectionInfo(2, 3, 3);
        CplElectionInfor electionInfor = new CplElectionInfor(2, 3, 3, 3, partyNames);

        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        ballotList.add(ballot2);
        ballotList.add(ballot3);

        partyNames.add("Republican");
        partyNames.add("Democratic");
        partyNames.add("G");

        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();
        ArrayList<Integer> third = new ArrayList<>();
        partyList.add(party2);
        partyList.add(party1);
        partyList.add(party3);


        CPL newCPL = new CPL(ballotList,electionInfo,candidateList,partyList,electionInfor);
        for (int i=0;i<10000;i++) {
            newCPL.runDistributeSeats();
            ArrayList<Candidate> cList = newCPL.PickWinners();
            if (cList.get(0).getName().equals("Dom") || cList.get(1).getName().equals("Dom")) {
                first.add(1);
            }
        }
        for (int i=0;i<10000;i++) {
            newCPL.runDistributeSeats();
            ArrayList<Candidate> cList = newCPL.PickWinners();
            if (cList.get(0).getName().equals("Dan") || cList.get(1).getName().equals("Dan")) {
                second.add(1);
            }
        }
        for (int i=0;i<10000;i++) {
            newCPL.runDistributeSeats();
            ArrayList<Candidate> cList = newCPL.PickWinners();
            if (cList.get(0).getName().equals("Diggle")|| cList.get(1).getName().equals("Diggle")) {
                third.add(1);
            }
        }

        double firstWinRate = (first.size())/10000.0;
        double secondWinRate =(second.size())/10000.0;
        double thirdWinRate =(third.size())/10000.0;


        assertEquals(true, firstWinRate >= 0.3);
        assertEquals(true, secondWinRate >= 0.3);
        assertEquals(true, thirdWinRate >= 0.3);


    }

    @Test //Test ID: VCS_6
    public void DistributeSeatsTest() {
        Party party1 = new Party("Republican");
        Party party2 = new Party("Democratic");
        Party party3 = new Party("Reform");
        Party party4 = new Party("Green");
        Party party5 = new Party("Moll");
        ArrayList<String> partyNames = new ArrayList<>();
        partyNames.add("Republican");
        partyNames.add("Democratic");
        partyNames.add("Reform");
        partyNames.add("Green");
        partyNames.add("Moll");

        Ballot ballot1 = new Ballot("1,,,,");
        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        Candidate c1 = new Candidate("Dom01", "Democrat");
        Candidate c2 = new Candidate("Dom02", "Republican");
        Candidate c3 = new Candidate("Dom03", "Reform");
        Candidate c4 = new Candidate("Dom04", "Reform");
        Candidate c5 = new Candidate("Dom05", "Green");
        Candidate c6 = new Candidate("Dom06", "Moll");


        ArrayList<Candidate> candidateList = new ArrayList<>();
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);
        candidateList.add(c4);
        candidateList.add(c5);
        candidateList.add(c6);


        party1.addCandidate(c1);
        party1.addCandidate(c1);
        party1.addCandidate(c1);
        party1.addCandidate(c1);
        party1.addCandidate(c1);

        party2.addCandidate(c2);
        party2.addCandidate(c2);
        party2.addCandidate(c2);
        party2.addCandidate(c2);
        party2.addCandidate(c2);

        party3.addCandidate(c3);
        party3.addCandidate(c3);
        party3.addCandidate(c3);
        party3.addCandidate(c3);
        party3.addCandidate(c3);

        party4.addCandidate(c4);
        party4.addCandidate(c4);
        party4.addCandidate(c4);
        party4.addCandidate(c4);
        party4.addCandidate(c4);

        party5.addCandidate(c5);


        ArrayList<Party> partyList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);
        partyList.add(party3);
        partyList.add(party4);
        partyList.add(party5);
        ElectionInfo electionInfo = new ElectionInfo(10, 6, 100000);
        CplElectionInfor electionInfor = new CplElectionInfor(10, 6, 100000, 5, partyNames);
        CPL newCPL = new CPL(ballotList, electionInfo, candidateList, partyList, electionInfor);
        party1.setPartyVotes(38000);
        party2.setPartyVotes(23000);
        party3.setPartyVotes(21000);
        party4.setPartyVotes(12000);
        party5.setPartyVotes(6000);
        newCPL.runDistributeSeats();
        assertEquals(2, party2.getNumberOfSeats());
        assertEquals(2, party3.getNumberOfSeats());
        assertEquals(1, party4.getNumberOfSeats());
        assertEquals(1, party5.getNumberOfSeats());
        assertEquals(4, party1.getNumberOfSeats());
    }

    @Test //Test ID: VCS_7
    public void run() throws IOException {
        File file = new File("./testing/cpl_electionInfo_file.csv");
        String fileName = "./testing/cpl_electionInfo_file.csv";
        String electionType = "CPL";
        // party1: 6 ballots->3 seats
        //party2: 12 ballots-> 6 seats
        // party3: 2 ballots-> 1 seats
        // quota 20 votes: 10 seats = 2 votes/seat
        Ballot ballot1 = new Ballot("1,,");
        Ballot ballot2 = new Ballot(",1,");
        Ballot ballot3 = new Ballot(",1,");
        Ballot ballot4 = new Ballot("1,,");
        Ballot ballot5 = new Ballot(",1,");
        Ballot ballot6 = new Ballot(",1,");
        Ballot ballot7 = new Ballot("1,,");
        Ballot ballot8 = new Ballot(",1,");
        Ballot ballot9 = new Ballot(",1,");
        Ballot ballot10 = new Ballot("1,,");
        Ballot ballot11 = new Ballot(",1,");
        Ballot ballot12 = new Ballot(",1,");
        Ballot ballot13 = new Ballot("1,,");
        Ballot ballot14 = new Ballot(",1,");
        Ballot ballot15 = new Ballot(",1,");
        Ballot ballot16 = new Ballot("1,,");
        Ballot ballot17 = new Ballot(",1,");
        Ballot ballot18 = new Ballot(",1,");
        Ballot ballot19 = new Ballot(",,1");
        Ballot ballot20 = new Ballot(",,1");

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
        ballotList.add(ballot12);
        ballotList.add(ballot13);
        ballotList.add(ballot14);
        ballotList.add(ballot15);
        ballotList.add(ballot16);
        ballotList.add(ballot17);
        ballotList.add(ballot18);
        ballotList.add(ballot19);
        ballotList.add(ballot20);


        Party party1 = new Party("Democrat");
        Party party2 = new Party("Republic");
        Party party3 = new Party("Green");
        Candidate c1 = new Candidate("Dom", "Democrat");
        Candidate c2 = new Candidate("Dem.Dom", "Democrat");
        Candidate c3 = new Candidate("unknown_dude", "Democrat");
        Candidate c4 = new Candidate("noChance", "Democrat");
        Candidate c5 = new Candidate("Daniel", "Republic");
        Candidate c6 = new Candidate("Rep.Dude", "Republic");
        Candidate c7 = new Candidate("Rep.Lady", "Republic");
        Candidate c8 = new Candidate("John", "Green");
        Candidate c9 = new Candidate("no_chance", "Green");
        ArrayList<Party> partyList = new ArrayList<>();
        ArrayList<Candidate> candidateList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);
        partyList.add(party3);
        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c4);
        candidateList.add(c5);
        candidateList.add(c6);
        candidateList.add(c7);
        candidateList.add(c8);
        candidateList.add(c9);
        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party1.addCandidate(c3);
        party1.addCandidate(c4);
        party2.addCandidate(c5);
        party2.addCandidate(c6);
        party2.addCandidate(c7);
        party3.addCandidate(c8);
        party3.addCandidate(c9);

        ArrayList<String> partyNames = new ArrayList<>();
        partyNames.add("Democrat");
        partyNames.add("Republic");
        partyNames.add("Green");
        ArrayList<Candidate> ExpectedWinners = new ArrayList<>();
        ExpectedWinners.add(c1);
        ExpectedWinners.add(c2);
        ExpectedWinners.add(c3);
        ExpectedWinners.add(c5);
        ExpectedWinners.add(c6);
        ExpectedWinners.add(c7);
        ExpectedWinners.add(c8); // c4 and c9 not included

        ElectionInfo electionInfo = new ElectionInfo(10, 9, 20);
        CplElectionInfor electionInfor = new CplElectionInfor(10, 9, 20, 3, partyNames);
        CPL newCPL = new CPL(ballotList, electionInfo, candidateList, partyList, electionInfor);
        newCPL.Run();
        assertEquals(6, party1.getPartyVotes());
        assertEquals(12, party2.getPartyVotes());
        assertEquals(2, party3.getPartyVotes());

        assertEquals(3, party1.getNumberOfSeats());
        assertEquals(6, party2.getNumberOfSeats());
        assertEquals(1, party3.getNumberOfSeats());
        ArrayList<Candidate> ActualWinners = newCPL.PickWinners();

        assertEquals(7,ActualWinners.size());

        assertEquals(false, ActualWinners.contains(c4));
        assertEquals(false, ActualWinners.contains(c9));

        assertEquals(true, ActualWinners.contains(c1));
        assertEquals(true, ActualWinners.contains(c2));
        assertEquals(true, ActualWinners.contains(c3));
        assertEquals(true, ActualWinners.contains(c5));
        assertEquals(true, ActualWinners.contains(c6));
        assertEquals(true, ActualWinners.contains(c7));
        assertEquals(true, ActualWinners.contains(c8));



    }

    @Test //Test ID:
    public void run02() throws IOException {
        File file = new File("./testing/cpl_electionInfo_file.csv");
        String fileName = "./testing/cpl_electionInfo_file.csv";
        String electionType = "CPL";

        Ballot ballot1 = new Ballot("1,");
        Ballot ballot2 = new Ballot(",1");
        Ballot ballot3 = new Ballot("1,");
        Ballot ballot4 = new Ballot("1,");
        Ballot ballot5 = new Ballot("1,");
        Ballot ballot6 = new Ballot("1,");



        ArrayList<Ballot> ballotList = new ArrayList<>();
        ballotList.add(ballot1);
        ballotList.add(ballot2);
        ballotList.add(ballot3);
        ballotList.add(ballot4);
        ballotList.add(ballot5);
        ballotList.add(ballot6);



        Party party1 = new Party("a");
        Party party2 = new Party("b");

        Candidate c1 = new Candidate("s", "a");
        Candidate c2 = new Candidate("d", "a");
        Candidate c3 = new Candidate("f", "b");

        ArrayList<Party> partyList = new ArrayList<>();
        ArrayList<Candidate> candidateList = new ArrayList<>();
        partyList.add(party1);
        partyList.add(party2);

        candidateList.add(c1);
        candidateList.add(c2);
        candidateList.add(c3);

        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party2.addCandidate(c3);

        ArrayList<String> partyNames = new ArrayList<>();
        partyNames.add("a");
        partyNames.add("b");
        ArrayList<Candidate> ExpectedWinners = new ArrayList<>();
        ExpectedWinners.add(c1);
        ExpectedWinners.add(c2);
        ExpectedWinners.add(c3);

        ElectionInfo electionInfo = new ElectionInfo(2, 3, 6);
        CplElectionInfor electionInfor = new CplElectionInfor(2, 3, 6, 2, partyNames);
        CPL newCPL = new CPL(ballotList, electionInfo, candidateList, partyList, electionInfor);
        newCPL.Run();
        assertEquals(5, party1.getPartyVotes());
        assertEquals(1, party2.getPartyVotes());

        assertEquals(2, party1.getNumberOfSeats());
        assertEquals(0, party2.getNumberOfSeats());
        ArrayList<Candidate> ActualWinners = newCPL.PickWinners();
        assertEquals(2,ActualWinners.size());



    }
}