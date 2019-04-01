/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultStructureModel;
import resultMaker.ResultViewer;
import patternDetails.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amirEbrahimi.um@gmail.com
 */
public class Runner {
    
    
    public static void main(String[] args) {

        PatternsGroupInfo patternsGroupInfo = new PatternsGroupInfo();
        
        
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(Strategy.patternName);
        
        for (int i = 0; i < 3; i++) {
            InstanceInfo instance = new InstanceInfo();
            
            for (int j = 0; j < 3; j++) {
                Entry entry = new Entry();
                entry.setClassId("class" + j );
                entry.setRole(j+ " " + Strategy.role_Strategy);
                
                instance.addEntry(entry);
            }
            
            groupInfo.addInstance(instance);
        }
        
        patternsGroupInfo.addGroupInfo(groupInfo);
        
         
        groupInfo = new GroupInfo();
        groupInfo.setGroupName(Adapter.patternName);
        
        for (int i = 0; i < 3; i++) {
            InstanceInfo instance = new InstanceInfo();
            
            for (int j = 0; j < 3; j++) {
                Entry entry = new Entry();
                entry.setClassId("class" + j );
                entry.setRole(j + " " + Adapter.role_Adaptee);
                
                instance.addEntry(entry);
            }
            
            groupInfo.addInstance(instance);
        }
        
        patternsGroupInfo.addGroupInfo(groupInfo);       
        ResultViewer resultViewer = new ResultViewer(patternsGroupInfo, new JsonWriter());
        resultViewer.viewResults();
    }
}
