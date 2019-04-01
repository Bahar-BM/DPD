
package bsc;

import java.util.List;


public class Edge {
    public String source ; 
    public String target ;
    public int weight ;
    public String relation ;
    public final int association =2;
    public final int inheritance =3;
    public final int aggrigation =5;
    public final int dependency  =7;
    public Edge(String source, String target, String relation){
        this.source = source ;
        this.target = target ;
        this.relation = relation ; 
        if(relation == "association")
            this.weight = association;
        if(relation == "aggrigation")
            this.weight = association;
        if(relation == "inheritance")
            this.weight = inheritance;
        if(relation == "dependency")
            this.weight = dependency;
        
    }
    public Edge(String source, String target, int weight){
        this.source = source ;
        this.target = target ;
        this.weight = weight ;
        switch(weight){    
            case 2:    
                this.relation = "association";
                break;  //optional  
            case 3:    
                this.relation = "inheritance";
                break;  //optional  
            case 5:    
                this.relation = "association";
                break;
            case 7:
                this.relation = "dependency";
                break;  
        }    
        
        
    }
    public boolean canAdd( List<Edge> edges){
        for(Edge item : edges){
            if(this.equals(item))
                return false;
        }
        return true;
    }
    public boolean equals(Edge item){
        if(this.source.equals(item.source) && this.target.equals(item.target) && this.weight == item.weight)
        {
            return true;
        }
        else
            return false;
    }
    
}
