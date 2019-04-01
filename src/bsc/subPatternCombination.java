
package bsc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Parastoo
 */
class subPatternCombination {
    public List<subPatternRole> subPatternRoles ;
    public List<JointClass> jointClasses ;
    public subPatternCombination(){
        subPatternRoles = new ArrayList<>();
        jointClasses = new ArrayList<>();
    }
    
    public void setRoles(List<SubGraph> subPatterns){
        int index = 0 ;
        for (subPatternRole spr : subPatternRoles) {
            spr.setRoles(subPatterns.get(index));
            if(subPatterns.size() ==1)
                break;
            index++;
        }
    }
    public boolean checkJointClasses (List<SubGraph> subPatterns ){
       
        boolean result = true;
        for(JointClass jc : jointClasses){
            List<subPatternRole> includedsubPatternRoles = new ArrayList<>();
            for(String index : jc.indexOfIncludedClasses){
                includedsubPatternRoles.add(subPatternRoles.get(Integer.parseInt(index)-1));
                
            }
            
            for(String jcRole : jc.rolesOfJointClasses){
                List<String> sameroles = new ArrayList<>() ; 
                for(subPatternRole spr : includedsubPatternRoles){
                    sameroles.add(spr.roleVertexHMap.get(jcRole));
                }
                result = sameroles.isEmpty() || sameroles.stream().allMatch(sameroles.get(0)::equals);
                if(!result)
                    break;
            }
            if(!result)
                break;
        }
        return result;
    }
   
}
