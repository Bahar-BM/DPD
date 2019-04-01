package resultStructureModel;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amirEbrahimi.um@gmail.com
 */
public class PatternsGroupInfo 
{

    public ArrayList<GroupInfo> groupsInfo;

    public PatternsGroupInfo()
    {
        init();
    }
    
    private void init()
    {
        groupsInfo = new ArrayList<>();
    }
    
    public void addGroupInfo(GroupInfo newGroup)
    {  
        for(GroupInfo savedGroup : groupsInfo)
        {
            if(newGroup.getGroupName().equals(savedGroup.getGroupName()))
            {
                for(InstanceInfo instance : newGroup.getInstances())
                {
                    savedGroup.addInstance(instance);
                }
                return;
            }
        } 
        groupsInfo.add(newGroup);
    }

    public ArrayList<GroupInfo> getGroupsInfo() {
        return groupsInfo;
    }
    
    
}
