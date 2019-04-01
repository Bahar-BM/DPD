
package bsc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CandidateSubPattern {
    
    HashMap<String, List<SubGraph>> candidateSubPatternHMap = new HashMap<>();
    Graph systemGraph ;
    public HashMap<String, List<SubGraph>> findCandidateSubPatterns( String systemFileDirectory ,String subPatternFileDirectory) throws FileNotFoundException, IOException{
        systemGraph = new Graph();
        String directory = systemFileDirectory;
        systemGraph.graphLabel = "system";
        systemGraph.makeGCDR_S(directory);
        systemGraph.makeMatrix();
        
        String subPatternsDirectory = subPatternFileDirectory;
        List<String> subPatterns = BSCFile.readFile(subPatternsDirectory);
        List<Graph> subPatternGraphs = new ArrayList<>();

        
        for(String subPattern : subPatterns){
            Graph sp = new Graph();
            sp.makeGCDR_SubPattern(subPattern);
            sp.makeMatrix();
            subPatternGraphs.add(sp);
        }
        
        //algorithm
        for(Graph subPatternGraph : subPatternGraphs){
            
            boolean withoutCandidate = false;
            List<SubGraph> candidateSubGraphs = new ArrayList<>();
            List<Vertex> k = subPatternGraph.vertexes;
            List<List<Vertex>> cvs = new ArrayList<>();
            
            for(Vertex v_sub : k ){
                List<Vertex> cvsci = new ArrayList<>();
                for(Vertex v_System : systemGraph.vertexes){
                    
                    if(v_sub.inOutEquals(v_System))
                        cvsci.add(v_System);
                }
                if(cvsci.isEmpty())
                {
                    withoutCandidate = true;
                    break;
                }
                cvs.add(cvsci);
            }
            if(!withoutCandidate){
                
                System.out.println("-----------------newsubPattern-------------------------");

                System.out.println("number of classes:"+cvs.size());
                for(List<Vertex> mi : cvs){

                    for (Vertex item : mi) {
                        System.out.println(item.classLabel);
                    }
                    System.out.println("---------");
                }

                try {
                    List<List<Vertex>> subsets = CandidateSubPattern.getSubsets(cvs);
                    for (List<Vertex> subset : subsets) {
                        
                        boolean notCompatiblePattern = false;
                        for (int i = 0; i < subset.size(); i++) {
                            for (int j = i; j < subset.size(); j++) {
                                Vertex mi = subset.get(i);
                                Vertex mj = subset.get(j);
                                int miIndex = systemGraph.hashName.get(mi.classLabel);
                                int mjIndex = systemGraph.hashName.get(mj.classLabel);
                                if(systemGraph.matrix[miIndex][mjIndex] % subPatternGraph.matrix[i][j] != 0 ||
                                    systemGraph.matrix[mjIndex][miIndex] % subPatternGraph.matrix[j][i] != 0){
                                    notCompatiblePattern = true;
                                    break;
                                }
                                
                                
                            }
                            if(notCompatiblePattern)
                                break;
                        }
                        if(!notCompatiblePattern){
                            candidateSubGraphs.add(new SubGraph(subset,systemGraph,subPatternGraph.matrix,subPatternGraph.graphLabel));
                        }
                    }

                } catch (Exception ex) {
                    System.out.println("error:"+ex.getMessage());

                }
            }
            candidateSubPatternHMap.put(subPatternGraph.graphLabel ,candidateSubGraphs );
            
        }
        System.out.println("finish");
        for (String key :candidateSubPatternHMap.keySet()) {
            for(SubGraph sg : candidateSubPatternHMap.get(key)){
                System.out.println(key+":");
                for(Vertex v : sg.vertexes){
                    System.out.println("vertex :"+v.classLabel);
                }
            }
        }
        return candidateSubPatternHMap ;
    }
    
    public static List<List<Vertex>> getSubsets(List<List<Vertex>> arrays) throws Exception {
        List<List<Vertex>> output = new ArrayList<>();

        ArrayList<Integer> arraySizes = new ArrayList<>();  // size of each array;
        ArrayList<Integer> counting = new ArrayList<>();

        long sizeOfOutput = 1;
        for (List<Vertex> cur : arrays) {
            int size = cur.size();
            if(size == 0) throw new Exception("there is an empty list");
            sizeOfOutput *= size;
            arraySizes.add(size);
            counting.add(0);
        }

        int cursor = 0;
        for (int i = 0; i < sizeOfOutput; i++) {
            boolean sameVertex = false;
            List<Vertex> e = new ArrayList<>();
            for (int j = 0; j < arrays.size(); j++) {
                for (int k = 0; k < e.size(); k++) {
                    if(e.get(k).classLabel.equals(arrays.get(j).get(counting.get(j)).classLabel))
                    {   sameVertex = true;
                        break;
                    }
                    
                }
                if(!sameVertex)
                    e.add(arrays.get(j).get(counting.get(j)));
            }
            if (!sameVertex) {
                output.add(e);
            }
            

            boolean resetCursor = false;
            if (i == sizeOfOutput-1) break;

            do {
                int c = counting.get(cursor);

                if (c + 1 < arraySizes.get(cursor)) {
                    counting.set(cursor, c + 1);
                    break;
                }else{
                    counting.set(cursor, 0);
                    resetCursor = true;
                    cursor++;
                }
            }while(true);

            if(resetCursor) cursor = 0;
        }

        return output;
    } 
}
