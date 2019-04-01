/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patterns;

import bsc.Graph;
import bsc.SqlConnection;
import bsc.Vertex;
import bsc.subPatternRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parastoo
 */
public class IteratorPattern extends Pattern{
    public static final String role_Iterator  = "Iterator";
    public static final String role_ConcreteIterator  = "ConIterator";
    public static final String role_Aggrigate  = "Aggregate";
    public static final String role_ConcreteAggrigate  = "ConAggregation";
    
//    public static final Edge relation_ConcreteAggrigate_Aggrigate_Inhritance = new Edge(role_ConcreteAggrigate, role_Aggrigate, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Iterator ;
    public List<Vertex> class_ConcreteIterator ;
    public Vertex class_Aggrigate ;
    public List<Vertex> class_ConcreteAggrigate ;

    
    public IteratorPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles ,systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteIterator == null){
            class_ConcreteIterator = new ArrayList<>(); 
        }
        if(class_ConcreteAggrigate == null){
            class_ConcreteAggrigate = new ArrayList<>(); 
        }
        switch(role){
            case role_Iterator:
                class_Iterator = className ;
                break;
            case role_ConcreteIterator:
                if(canAdd(class_ConcreteIterator, className))
                    class_ConcreteIterator.add(className);
                break;
            case role_Aggrigate:
                class_Aggrigate = className;
                break;
            case role_ConcreteAggrigate:
                 if(canAdd(class_ConcreteAggrigate, className))
                    class_ConcreteAggrigate.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        IteratorPattern otherIterator = (IteratorPattern) other ;
        boolean result = class_Iterator.classLabel.equals(otherIterator.class_Iterator.classLabel);
                    //&&   class_Aggrigate.equals(otherIterator.class_Aggrigate);
        
        if(result && !otherIterator.class_ConcreteIterator.isEmpty()){
            setRole(role_ConcreteIterator,otherIterator.class_ConcreteIterator.get(0));
        }
        if(result && !otherIterator.class_ConcreteAggrigate.isEmpty()){
            setRole(role_ConcreteAggrigate,otherIterator.class_ConcreteAggrigate.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        List<Integer> usedAggrigate = new ArrayList<>(); 
        List<Vertex> unusedAggrigate = new ArrayList<>(); 
        for(Vertex concreteIterator : class_ConcreteIterator){
            for (Vertex concreteAggrigate : class_ConcreteAggrigate) {
                String query = "select * from list_method "
                        + "where method_output_type = '"+concreteAggrigate+".Collection.Object'  and "
                        + "method_class_id  = (select class_id from list_class "
                        + "where class_name='"+concreteIterator.classLabel+"' and  package_Name = '"+concreteIterator.packageName+"')";
            
                try {
                    ResultSet resultSet = SqlConnection.execute(query);
                    if(resultSet != null && resultSet.next() != false){
                        resultSet.previous();
                        while (resultSet.next()) {
                            usedAggrigate.add(class_ConcreteAggrigate.indexOf(concreteAggrigate));
                            isTruePattern = true ;
                            break;
                        }
                    }
                    else{
                        removedVertex.add(concreteIterator);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteIterator.remove(remove);
        }
        for (Vertex conAggrigate :class_ConcreteAggrigate) {
            boolean used = false;
            for(int index : usedAggrigate) {
                if(class_ConcreteAggrigate.indexOf(used) == index){
                    used = true;
                    break;
                }
            }
            if(!used)
                unusedAggrigate.add(conAggrigate);
        }
        for(Vertex conAggrigate :unusedAggrigate){
            class_ConcreteAggrigate.remove(conAggrigate);
        }
    }
    
}
