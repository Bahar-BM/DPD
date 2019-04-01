
package bsc;


public class Dependency {
    
    public void findDependency(Graph graph){
        
        for(Vertex v : graph.vertexes){
            
            checkVertexMethods(v,graph);
        }
    }

    private void checkVertexMethods(Vertex vertex, Graph graph) {
        for(Method m :vertex.methods){
            checkMethodInoutType(m,vertex,graph);
        }
    }

    private void checkMethodInoutType(Method method, Vertex vertex, Graph graph) {
        for(String type: method.inputType){
            if(!type.equals(vertex.xmiId)){
                for(Vertex v : graph.vertexes){
                    
                    if(v.xmiId.equals(type) && v.packageName.equals(vertex.packageName)){
                        Edge e = new Edge(vertex.classLabel, v.classLabel , "dependency");
                        if(e.canAdd(graph.edges)){
                            graph.edges.add(e);
                            System.out.println(vertex.classLabel +":"+v.classLabel +":dependency");
                        }
                    }
                }
            }
        }
    }
}
