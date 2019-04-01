
package bsc;


import Patterns.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Parastoo
 */
public class CandidatePattern {

    private static boolean hasSameVertex(List<SubGraph> e) {
        boolean hasSameVertex = false;
        if(e.size() ==1)
            hasSameVertex = true;
        for (int i = 0; i < e.size(); i++) {
            for (int j = i+1; j < e.size(); j++) {
                for (int k = 0; k < e.get(i).vertexes.size(); k++) {
                    for (int l = 0; l < e.get(j).vertexes.size(); l++) {
                        if(e.get(i).vertexes.get(k).classLabel.equals(e.get(i).vertexes.get(l).classLabel))
                        {
                            hasSameVertex = true;
                            break;
                        }
                        
                    }
                }
            }
        }
        return hasSameVertex;
    }
    
    HashMap<String,List<Object>> structuralPatternResults = new HashMap<>();
    HashMap<String,List<Object>> behavioralPatternResults = new HashMap<>();
    public HashMap<String,List<Object>> findStructuralPattern(String sfmDirectory , String behavioralResultDirectory, String structuralResultDirectory ,
            String unmergedResultDirectory ,HashMap<String, List<SubGraph>> candidateSubPatternHMap , Graph systemGarph) 
            throws IOException, Exception, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        HashMap<String,List<Graph>> structuralGraphResults = new HashMap<>();
        List<String> sfmStrings = BSCFile.readFile(sfmDirectory);
        List<StructuralFeatureModel> sfms = new ArrayList<>();
        for(String sfmString : sfmStrings){
            
            StructuralFeatureModel sfm = new StructuralFeatureModel();
            sfm.makeSFM(sfmString);
            sfms.add(sfm);
        }
        
        for (StructuralFeatureModel sfm : sfms) {
            
            List<Graph> sfmResults = new  ArrayList<>();
            
            
            for(subPatternCombination spc : sfm.combinations){
                List<List<SubGraph>> selectedSubPattern = new ArrayList<>();
                
                
                List<List<SubGraph>> subsets = null;
                for(subPatternRole spr : spc.subPatternRoles){
                    List<SubGraph> candidateSP = candidateSubPatternHMap.get(spr.subPatternName);
                    if(!candidateSP.isEmpty()){
                        selectedSubPattern.add(candidateSP);
                    }
                }
                if(selectedSubPattern.size() == spc.subPatternRoles.size() || (!sfm.andCondition && selectedSubPattern.size() >= 1))
                    subsets = getSubsets(selectedSubPattern);
                if(subsets != null){
                    if(!sfm.andCondition){
                        for(SubGraph sg :selectedSubPattern.get(0)){
                            if( selectedSubPattern.size() ==1  || !sg.equalsList(selectedSubPattern.get(1)))
                            {
                                System.out.println("new pattern discoverd"+sfm.name);
                                spc.setRoles( new ArrayList<SubGraph>() {{add(sg);}}) ;
                                List<subPatternRole> role = new ArrayList<>();
                                role.add(spc.subPatternRoles.get(0));
                                Class<?> cl = Class.forName("Patterns."+sfm.name+"Pattern");
                                Constructor<?> cons = cl.getConstructor(List.class, Graph.class);
                                Object tempPattern = cons.newInstance(spc.subPatternRoles , systemGarph);
                                addPatternToResults(sfm.name ,tempPattern);
                                sfmResults.add(new Graph(sfm.name , new ArrayList<SubGraph>() {{add(sg);}} , role));
                            }
                        }
                    }
                    else{
                        int index = 0;
                        for(List<SubGraph> subset : subsets){
                            if(!subset.isEmpty() && subset.size() == spc.subPatternRoles.size()){
                                spc.setRoles(subset);
                                
                                if(spc.checkJointClasses(subset)){
                                    index++;
                                    if(index == 275){
                                        System.out.println("heere");
                                    }
                                    System.out.println("new pattern discoverd"+"index:"+index+sfm.name);
                                    if(sfm.name.equals("Facade") && index == 576 ){
                                        System.out.println("here");
                                    }
                                    Graph temp =new Graph(sfm.name , subset , spc.subPatternRoles);
                                    if(temp.roleVertexHMap.size() == temp.vertexRoleHMap.size())
                                    {
                                        Class<?> cl = Class.forName("Patterns."+sfm.name+"Pattern");
                                        Constructor<?> cons = cl.getConstructor(List.class ,Graph.class );
                                        Object tempPattern = cons.newInstance(spc.subPatternRoles , systemGarph);
                                        addPatternToResults(sfm.name ,tempPattern);
                                    }
                                    sfmResults.add(temp);
                                    
                                }
                            }
                        }
                    }
                }
            }
        structuralGraphResults.put(sfm.name, sfmResults);
        }
        BSCFile.writeGsonFile(unmergedResultDirectory, structuralGraphResults);
        BSCFile.writeFile(structuralResultDirectory, structuralPatternResults);
        for (String  key :structuralPatternResults.keySet()) {
            List<Object> added = new ArrayList<>();
            if(key.equals("Template")){
                System.out.println("this");
            }
            int index=1;
            SqlConnection.getInstance();
            for(Object object :  structuralPatternResults.get(key)){
                index ++;
                if(index ==6  && key.equals("State")){
                    System.out.println("this");
                }
                System.out.println(index);
                Pattern pattern = (Pattern) object; 
                pattern.checkBehave();
                if(pattern.isTruePattern)
                   added.add(object);
            }
            behavioralPatternResults.put(key, added);
        }
        BSCFile.writeFile(behavioralResultDirectory, behavioralPatternResults);
        for (String  key :behavioralPatternResults.keySet()) {
            System.out.println(key+" :"+behavioralPatternResults.get(key).size());
        }
        return structuralPatternResults;
    }
    
    public static List<List<SubGraph>> getSubsets(List<List<SubGraph>> arrays) throws Exception {
        List<List<SubGraph>> output = new ArrayList<>();
        int counter =0;
        ArrayList<Integer> arraySizes = new ArrayList<>();  // size of each array;
        ArrayList<Integer> counting = new ArrayList<>();

        long sizeOfOutput = 1;
        for (List<SubGraph> cur : arrays) {
            int size = cur.size();
            if(size == 0) throw new Exception("there is an empty list");
            sizeOfOutput *= size;
            arraySizes.add(size);
            counting.add(0);
        }

        int cursor = 0;
        for (int i = 0; i < sizeOfOutput; i++) {
            if(counter >10000000){
                break;
            }
            List<SubGraph> e = new ArrayList<>();
            boolean equals = false;
            for (int j = 0; j < arrays.size(); j++) {
                SubGraph item = arrays.get(j).get(counting.get(j));
                for(SubGraph temp : e){
                    if(temp.vertexes.size() == item.vertexes.size() && temp.equals(item))
                    {
                        equals = true ; 
                        break;
                    }
            }
            if(!equals){
                e.add(arrays.get(j).get(counting.get(j)));
            }
        }
            
            if(hasSameVertex(e)){
                output.add(e);
            counter++;}

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
    private void addPatternToResults(String patternCategory, Object tempPattern) {
        if(structuralPatternResults.get(patternCategory) != null){
            boolean equals = false;
            List<Object> patterns = structuralPatternResults.get(patternCategory);
            for (Object item : patterns) {
                equals = item.equals(tempPattern);  
                if(equals)
                    break;
            }
            if(!equals)
                patterns.add(tempPattern);
        }
        else{
            List<Object> patterns = new ArrayList<>();
            patterns.add(tempPattern);
            structuralPatternResults.put(patternCategory,patterns);
        }
    }
}
