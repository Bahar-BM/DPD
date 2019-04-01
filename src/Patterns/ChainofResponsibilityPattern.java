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
public class ChainofResponsibilityPattern extends Pattern{
    public static final String role_Handler  = "Handler";
    public static final String role_ConcreteHandler  = "ConHandler";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Product, role_Director, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
    public Vertex class_Handler ;
    public List<Vertex> class_ConcreteHandler ;
    

    
    public ChainofResponsibilityPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteHandler == null){
            class_ConcreteHandler = new ArrayList<>(); 
        }
        
        switch(role){
            case role_Handler:
                class_Handler = className ;
                break;
            case role_ConcreteHandler:
                if(canAdd(class_ConcreteHandler, className))
                    class_ConcreteHandler.add(className);
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        ChainofResponsibilityPattern otherChainOfResponsibility = (ChainofResponsibilityPattern) other ;
        boolean result = class_Handler.classLabel.equals(otherChainOfResponsibility.class_Handler.classLabel);
        if(result){
            setRole(role_ConcreteHandler,otherChainOfResponsibility.class_ConcreteHandler.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteHandler: class_ConcreteHandler){
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + concreteHandler.classLabel +"' and package_name like '"+ concreteHandler.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Handler.classLabel +"' and package_name like '"+ class_Handler.packageName + "')) "
                    + "and field_type like '"+ class_Handler.classLabel+"'";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet != null && resultSet.next()) {
                        isTruePattern =true;
                        break;
                    }
                }
                else{
                    removedVertex.add(concreteHandler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteHandler.remove(remove);
        }
    }
}
