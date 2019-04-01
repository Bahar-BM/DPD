
package bsc;

import java.util.ArrayList;
import java.util.List;


public class Vertex {
   public String classLabel; 
   
   public String xmiId;
   public  String packageName;
   public List<Method> methods = new ArrayList<>();
   public int inputWeight ;
   public int outputWeight ;
   public String role ;
   public List<String> classVariables = new ArrayList<>() ;
   
   public boolean inOutEquals (Vertex v){
        if((v.inputWeight % this.inputWeight == 0) && (v.outputWeight % this.outputWeight == 0))
            return true;
        else
            return false;
   }
}
