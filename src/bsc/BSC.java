
package bsc;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class BSC {

   
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        
//        String systemFileDirectory = "C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\xml";
        String systemFileDirectory = args[1];
//        String subPatternFileDirectory = "C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\pattern structure\\subPatternfile.txt";
//        String PatternFileDirectory = "C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\pattern structure\\FSMs.txt";
//        String structuralResultDirectory = "C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\StructuralResults\\Structuralresults.txt";
//        String behavioralResultDirectory = "C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\BehavioralResults\\Behavioralresults.txt";
        String behavioralResultDirectory = "./BehavioralResults/Behavioralresults.txt";
        String subPatternFileDirectory = "./pattern structure/subPatternfile.txt";
        String PatternFileDirectory = "./pattern structure/FSMs.txt";
        String structuralResultDirectory = "./StructuralResults/Structuralresults.txt";
        String unmergedResultDirectory = args[2];


        SqlConnection.setArgs(args);
        CandidateSubPattern csp = new CandidateSubPattern();
        HashMap<String, List<SubGraph>> candidateSubPatternHMap = csp.findCandidateSubPatterns(systemFileDirectory ,subPatternFileDirectory);
        CandidatePattern cp  = new CandidatePattern();
        cp.findStructuralPattern(PatternFileDirectory ,behavioralResultDirectory, structuralResultDirectory , unmergedResultDirectory , candidateSubPatternHMap , csp.systemGraph);



    }
    
}
