
package bsc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Parastoo
 */
public class StructuralFeatureModel {
    public String name ;
    public List<subPatternCombination> combinations ;
    
    public boolean andCondition ;
    
    public StructuralFeatureModel(){
        combinations = new ArrayList<subPatternCombination>();
    }
    public void makeSFM(String sfmString) {
        String[] splitedByDash = sfmString.split("-");
        this.name = splitedByDash[0];
        
        for (int i = 0; i < Integer.parseInt(splitedByDash[1]); i++) {
            subPatternCombination spc = new subPatternCombination();
            String[] splitedBySpace = splitedByDash[i+2].split("\\s+");
            String[] splitedByAnd ;
            String[] splitedRelationByAnd ;
            if(Integer.parseInt(splitedByDash[splitedByDash.length -1]) == 1)
            {
                splitedByAnd = splitedBySpace[0].split("!&&");
                splitedRelationByAnd = splitedBySpace[1].split("!&&");
                andCondition = false;
            }
            else{
                splitedByAnd = splitedBySpace[0].split("&&");
                splitedRelationByAnd = splitedBySpace[1].split("&&");
                andCondition = true;
            }
            for (int j = 0; j < splitedByAnd.length; j++) {
                String[] roles = splitedRelationByAnd[j].substring(1,splitedRelationByAnd[j].length()-1).split(",");
                subPatternRole spr = new subPatternRole(splitedByAnd[j], roles);
                spc.subPatternRoles.add(spr);
            }
            if(splitedBySpace.length >= 3){
                for (int j = 2; j < splitedBySpace.length; j++) {
                    String[] splitedByUnderLine = splitedBySpace[j].split("_");
                    JointClass jc = new JointClass();
                    String[] indexOfIncludedClasses= splitedByUnderLine[0].substring(1, splitedByUnderLine[0].length()-1).split(",");
                    jc.indexOfIncludedClasses = new String[indexOfIncludedClasses.length];
                    jc.indexOfIncludedClasses = indexOfIncludedClasses;
                    String[] rolesOfJointClasses = splitedByUnderLine[1].substring(1, splitedByUnderLine[1].length()-1).split(",");
                    jc.rolesOfJointClasses = new String[indexOfIncludedClasses.length];
                    jc.rolesOfJointClasses = rolesOfJointClasses;
                    spc.jointClasses.add(jc);
                }


            }
            combinations.add(spc);
        }
    }
    
}
