/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patterns;

import bsc.Graph;
import bsc.Vertex;
import bsc.subPatternRole;
import java.util.List;

/**
 *
 * @author Parastoo
 */
public abstract class  Pattern {
    
    public boolean isTruePattern = false;
   
    public abstract void setRole(String role ,Vertex className);
    public void setRoles(List<subPatternRole> subPatternRoles ,Graph systemGraph){
        subPatternRoles.stream().forEach((spr) -> {
            spr.roleVertexHMap.keySet().stream().forEach((key) -> {
                String classLabel = (String)spr.roleVertexHMap.get(key);
                Vertex className = systemGraph.idVertexHMap.get(classLabel);
                setRole(key ,className);
            });
        });
    }
    public abstract boolean equals(Object other);
    public  Pattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        setRoles(subPatternRoles , systemGraph);
    }
    public boolean canAdd(List<Vertex> list , Vertex str ){
        for (Vertex s : list) {
            if(s.classLabel.equals(str.classLabel))
                return false;
            
        }
        return true;
    }
    public abstract void checkBehave();
    
}
