# Vote Count System
### Purpose:
To process the digital ballot. Digital ballots contains 1 and commas. The position of number 1 indicates which candiate a voter votes for.
For example:
1,, --> this ballot counts for the first candidate and there are 3 candidates

### Description:
This application takes in digital ballots and informs users which candidates are the winners
Constraint: the application must process 100,000 ballots under 5 minutes. If there is a tie, a random winner will be selected by fair coin flips.

## Running the system
To Run the vote count system 
1) Navigate to the project directory and open it in IntelliJ
2) Make sure that ElectionDriver is set to be the main class
3) Click the green play button 

-src should be marked as source folder
-outputFiles should be marked as output directory
-Testing should be marked as testing directory

Special Instructions:
 - For manual input, run the program with a -m flag
    this can be added under the IntelliJ Run --> edit config --> program arguments section
 - Audit file can be saved in a custom location
   - Audit files must have .txt as its extention
    

NOTES:

 - this run with java 11 or newer so make sure to run with a compatible version
 - The testing is done using Junit 4 (make sure this is imported)
 **Working with:**Griffin Higley, Erin Seichter and Dan Belair
