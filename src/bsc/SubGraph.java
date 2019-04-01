
package bsc;

import com.sun.javafx.geom.Matrix3f;
import java.util.ArrayList;
import java.util.List;


public class SubGraph extends Graph{
    public SubGraph(List<Vertex> vertexes , Graph parent , int[][] matrix , String label){
        super(parent);
        this.vertexes = vertexes;
        this.matrix = matrix;
        this.graphLabel = label;
        int index = 0;
        for(Vertex v : vertexes){
            hashName.put(v.classLabel, index);
            index++;
        }
        makeEdges();
    }

    private void makeEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < this.vertexes.size(); i++) {
            for (int j = 0; j < this.vertexes.size(); j++) {
                if(matrix[i][j] != 1){
                    Edge e = new Edge(vertexes.get(i).classLabel,vertexes.get(j).classLabel, matrix[i][j]);
                    edges.add(e);
                }
            }
 
        }
        this.edges = edges;
    }
    
    public boolean equals (SubGraph other){
        if(this.equalsVertex(other) && this.equalsMatrix(other) ){
            return true;
        }
        else
            return false;
    }
    
    public boolean equalsList (List<SubGraph> other){
            boolean result = false;
            for(SubGraph sg : other) {
                result = this.equalsVertex(sg);
                if(result)
                    break;
            }
            return result;
        
    }
    
    public boolean equalsVertex (SubGraph other){
        boolean result = true;
        for (int i = 0; i < other.vertexes.size() && i < this.vertexes.size(); i++) {
            if(!other.vertexes.get(i).classLabel.equals(this.vertexes.get(i).classLabel))
            {
                result = false;
                break;
            }
        }
        return result;
    }
    
    public boolean equalsMatrix (SubGraph other){
        boolean result = true;
        for (int i = 0; i < other.vertexes.size() && i < this.vertexes.size(); i++) {
            for (int j = 0;  j < other.vertexes.size() && j < this.vertexes.size(); j++) {
                if(! (this.matrix[i][j] == other.matrix[i][j]))
                {
                    result = false;
                    break;
                }
            }
            if(!result)
                break;
            
        }
        return result;
    }
    
}
