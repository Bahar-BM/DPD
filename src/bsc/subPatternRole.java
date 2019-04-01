
package bsc;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Parastoo
 */
public class subPatternRole {
    public String subPatternName ;
    public HashMap<String,String> roleVertexHMap ;
    public String[] roles ;
    public subPatternRole (String subPatternName, String[] roles){
        this.subPatternName = subPatternName ;
        roleVertexHMap = new HashMap<>();
        this.roles = roles;
    }
    public void setRoles(SubGraph sg ){
        int index = 0;
        for(Vertex v : sg.vertexes){
            roleVertexHMap.put(roles[index], v.classLabel);
            index++;
        }
    }
}
