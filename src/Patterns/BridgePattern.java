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
public class BridgePattern extends Pattern{
    public static final String role_Implementor  = "Implementor";
    public static final String role_ConcreteImplementor  = "ConImplementor";
    public static final String role_Abstraction  = "Abstraction";
    public static final String role_RefinedAbstraction  = "RefinedAbstraction";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_RefinedAbstraction, role_Abstraction, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Implementor ;
    public List<Vertex> class_ConcreteImplementor ;
    public Vertex class_Abstraction ;
    public List<Vertex> class_RefinedAbstraction ;

    
    public BridgePattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteImplementor == null){
            class_ConcreteImplementor = new ArrayList<>(); 
        }
        if(class_RefinedAbstraction == null){
            class_RefinedAbstraction = new ArrayList<>(); 
        }
        switch(role){
            case role_Implementor:
                class_Implementor = className ;
                break;
            case role_ConcreteImplementor:
                if(canAdd(class_ConcreteImplementor, className))
                    class_ConcreteImplementor.add(className);
                break;
            case role_Abstraction:
                class_Abstraction = className;
                break;
            case role_RefinedAbstraction:
                 if(canAdd(class_RefinedAbstraction, className))
                    class_RefinedAbstraction.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        BridgePattern otherBridge = (BridgePattern) other ;
        boolean result = class_Implementor.classLabel.equals(otherBridge.class_Implementor.classLabel)
                    &&   class_Abstraction.classLabel.equals(otherBridge.class_Abstraction.classLabel);
        
        if(result && !otherBridge.class_ConcreteImplementor.isEmpty()){
            setRole(role_ConcreteImplementor,otherBridge.class_ConcreteImplementor.get(0));
        }
        if(result && !otherBridge.class_RefinedAbstraction.isEmpty()){
            setRole(role_RefinedAbstraction,otherBridge.class_RefinedAbstraction.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        List<Integer> usedConImplementor = new ArrayList<>(); 
        List<Vertex> unusedConImplementor = new ArrayList<>(); 
                    
        for(Vertex concreteRefined : class_RefinedAbstraction){
            boolean isTrueconcreteRefined = false;
            for (Vertex concreteImplementor : class_ConcreteImplementor) {
                
                String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + concreteRefined.classLabel +"' and package_name like '"+ concreteRefined.packageName
                    + "' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Abstraction.classLabel +"' and package_name like '"+ class_Abstraction.packageName + "')) "
                    + "and field_type like '"+ class_Implementor.classLabel+"' and expression_second in "
                    + "(select method_name from list_method m3 join list_class c3 on m3.method_class_id = c3.class_id "
                    + "where class_name = '"+concreteImplementor.classLabel+"' and package_name = '"+concreteImplementor.packageName+"'";
                
                    try {
                    ResultSet resultSet = SqlConnection.execute(query);
                    if(resultSet != null && resultSet.next() != false){
                        resultSet.previous();
                        while (resultSet.next()) {
                            usedConImplementor.add(class_ConcreteImplementor.indexOf(concreteImplementor));
                            isTruePattern = true;
                            isTrueconcreteRefined = true;
                            break;
                        }
                    }
                    else{
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
            if(!isTrueconcreteRefined)
                    removedVertex.add(concreteRefined);
        }
        for (Vertex remove :removedVertex) {
            class_RefinedAbstraction.remove(remove);
        }
        for (Vertex conImplementor :class_ConcreteImplementor) {
            boolean used = false;
            for(int index : usedConImplementor) {
                if(class_ConcreteImplementor.indexOf(conImplementor) == index){
                    used = true;
                    break;
                }
            }
            if(!used)
                unusedConImplementor.add(conImplementor);
        }
        for(Vertex conImplementor :unusedConImplementor){
            class_ConcreteImplementor.remove(conImplementor);
        }
        
    }
    
}
