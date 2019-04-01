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
public class TemplatePattern extends Pattern{
    public static final String role_AbstractClass  = "AbstractClass";
    public static final String role_ConcreteClass  = "ConClass";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Product, role_Director, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_AbstractClass ;
    public List<Vertex> class_ConcreteClass ;
    

    
    public TemplatePattern(List<subPatternRole> subPatternRoles , Graph systemGraph ) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteClass == null){
            class_ConcreteClass = new ArrayList<>(); 
        }
        
        switch(role){
            case role_AbstractClass:
                class_AbstractClass = className ;
                break;
            case role_ConcreteClass:
                if(canAdd(class_ConcreteClass, className))
                    class_ConcreteClass.add(className);
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        TemplatePattern otherTemplate = (TemplatePattern) other ;
        boolean result = class_AbstractClass.classLabel.equals(otherTemplate.class_AbstractClass.classLabel);
        if(result){
            setRole(role_ConcreteClass,otherTemplate.class_ConcreteClass.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteClass : class_ConcreteClass){
            String query = "select * from method_expression e join list_class c on e.expression_class_id = c.class_id " 
                    + "where e.expression_type like 'invocation' and e.expression_first = 'self' "
                    + "and c.class_name like '"+class_AbstractClass.classLabel+"'  and c.package_name like '"
                    +class_AbstractClass.packageName+"' " 
                    + "and e.expression_second not like 'super' and  "
                    + "substring( e.expression_second ,1,length(e.expression_second)-2) in " 
                    + "(select m.method_name from list_method m join list_class c2 on m.method_class_id = c2.class_id and " 
                    + " c2.class_name like '"+concreteClass.classLabel +"' and " 
                    + "c2.package_name like '"+concreteClass.packageName+"')";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        isTruePattern =true;
                        break;
                    }
                }
                else{
                    removedVertex.add(concreteClass);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteClass.remove(remove);
        }
        
    }
}
