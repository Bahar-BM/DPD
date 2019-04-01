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
public class GroupInfo 
{
    public String groupName;
    public ArrayList<InstanceInfo> instances;

    public GroupInfo(String groupName)
    {
        this.groupName = groupName;
        init();
    }

    public GroupInfo()
    {
        init();
    }
    
    
    private void init()
    {
        instances = new ArrayList<>();
    }
    
    public void addInstance(InstanceInfo instance)
    {
        instances.add(instance);
    }
    
    public ArrayList<InstanceInfo> getInstances()
    {
        return this.instances;
    }
    
    public void setGroupName(String groupName)
    {
        if(groupName.isEmpty())
        {
            throw new IllegalArgumentException("empty group name is not admissible!");
        }
        
        this.groupName = groupName;
    }
    
    public String getGroupName()
    {
        return this.groupName;
    }
    
    public int getNumberOfInstances()
    {
        return this.instances.size();
    }
}

