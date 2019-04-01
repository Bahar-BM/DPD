
package bsc;

import cdg.model.ClassDiagram;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.DocumentException;


public class Graph {
    public List<Vertex> vertexes = new ArrayList<>() ; 
    public HashMap<String,Integer> hashName = new HashMap<>();
    public List<Edge> edges = new ArrayList<>();
    public String graphLabel ;
    public int[][] matrix ;
    public Graph parent ; 
    public HashMap <String,String> roleVertexHMap ; 
    public HashMap <String,String> vertexRoleHMap ; 
    public HashMap <String,Vertex> idVertexHMap = new HashMap<>(); 
    
    protected Graph(Graph parent) {
        this.parent = parent ; 
    }
    public Graph() {
        this.parent = null;
    }
    public Graph(String label, List<SubGraph> subGraphs, List<subPatternRole> subPatternRoles) {
        roleVertexHMap = new HashMap<>() ; 
        vertexRoleHMap = new HashMap<>() ; 
        this.graphLabel = label;
        for (SubGraph sg : subGraphs) {
            this.vertexes.addAll(sg.vertexes);
            this.edges.addAll(sg.edges);
            
            removeRepetedVertex();
        }
        
        for (subPatternRole spr : subPatternRoles) {
            Iterator it = spr.roleVertexHMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                this.roleVertexHMap.put((String)pair.getKey() ,(String)pair.getValue());
                this.vertexRoleHMap.put((String)pair.getValue() ,(String)pair.getKey());
            }
        }
    }
    
    public void makeGCDR_S(String directory){

        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        try {
            XmiFile xf = new XmiFile(); 
            for (File file : listOfFiles) {
                if (file.isFile()) {

                    ClassDiagram classDiagram = xf.readFile(directory+"\\"+file.getName());
                    xf.findClassInterface(classDiagram);
                    xf.findRelationship(classDiagram);
                }
                        
            }
            
            vertexes.addAll(xf.makeVertexes());
            edges.addAll(xf.makeEdges());
            int index = 0;
            for(Vertex v : vertexes){
                
                hashName.put(v.classLabel, index);
                idVertexHMap.put(v.classLabel , v);
                index++;
            }
            
        } 
        catch (DocumentException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    public void makeGCDR_SubPattern(String subPattern){
        vertexes = new ArrayList<>();
        edges = new ArrayList<>();
        String[] splited = subPattern.split("\\s+");
        graphLabel = splited[0];
        String[] classes = splited[1].split("-");
        for (int i = 0; i < classes.length; i++) {
            Vertex v = new Vertex();
            v.classLabel = classes[i];
            vertexes.add(v);
        }
        
        int index = 0 ;
        for(Vertex v : vertexes){
            hashName.put(v.classLabel, index);
            index++;
        }
        
        String[] relations = splited[2].split("-");
        for (int i = 0; i < relations.length; i++) {
            
            String relationshipNodes = relations[i].substring(2,7);
            String[] relationClasses = relationshipNodes.split(",");
            String relationType = relations[i].substring(10,relations[i].length()-1);
            String edgeRelation = "" ;
            
            if(relationType.equals("inherit"))
                edgeRelation ="inheritance";
            else if(relationType.equals("ass"))
                edgeRelation ="association";
            else if(relationType.equals("agg"))
                edgeRelation ="aggrigation";
            else if(relationType.equals("dep"))
                edgeRelation ="dependency";
            
            Edge e = new Edge(relationClasses[0],relationClasses[1], edgeRelation);
            if(e.canAdd(edges))
                edges.add(e);
            
        }
       
    }
    public void makeMatrix(){
        matrix = new int [vertexes.size()][vertexes.size()];
        for (int[] row: matrix)
            Arrays.fill(row, 1);
        
        for(Edge e : edges){
            try{
                int i = hashName.get(e.source);
                int j = hashName.get(e.target);
                
                matrix[i][j] = matrix[i][j] * e.weight ; 
            }
            catch(Exception ex){
                System.out.println("the class doesn't exist");
            }
        }
        
        setVertexInOutWeight();
    }
    private void setVertexInOutWeight() {
        int index = 0;
        for(Vertex v : vertexes){
            int out = 1;
            for (int i = 0; i < vertexes.size(); i++) {
                out = out * matrix [index][i];
            }
            v.outputWeight = out ;
            int in = 1;
            for (int i = 0; i < vertexes.size(); i++) {
                in = in * matrix [i][index];
            }
            v.inputWeight = in ;
            index++;
        }
        
        for(Vertex v : vertexes){
            System.out.println("in:" + v.inputWeight +"out:"+v.outputWeight+"name:"+v.classLabel);
        }
    }
    private void removeRepetedVertex() {
        for (int i = 0; i < this.vertexes.size(); i++) {
            for (int j = i+1; j < this.vertexes.size(); j++) {
                if(this.vertexes.get(i).classLabel.equals(this.vertexes.get(j).classLabel))
                    this.vertexes.remove(j);
            }
        }
    }
}
