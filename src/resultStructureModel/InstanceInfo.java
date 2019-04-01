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
public class InstanceInfo 
{
    
    public ArrayList<Entry> entriesInfo;

    public InstanceInfo()
    {
        init();
    }
    
    private void init()
    {
        entriesInfo = new ArrayList<>();
    }
    
    public void addEntry(Entry entry)
    {
        entriesInfo.add(entry);
    }
    
    public void removeEntry(Entry entry)
    {
        entriesInfo.remove(entry);
    }

    public ArrayList<Entry> getEntriesInfo() {
        return entriesInfo;
    }
    
    
}
