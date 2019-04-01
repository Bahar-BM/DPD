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
public class StrategyPattern extends Pattern{
    public static final String role_Strategy  = "Strategy";
    public static final String role_ConcreteStrategy  = "ConStrategy";
    public static final String role_Context  = "Context";
    
//    public static final Edge relation_ConcreteStrategy_Component_Inhritance = new Edge(role_ConcreteStrategy, role_Strategy, "inheritance");
//    public static final Edge relation_Context_Component_Inheritance = new Edge(role_Context, role_Strategy, "inheritance");
//    public static final Edge relation_Context_Component_Inheritance_Association = new Edge(role_Context, role_Strategy, "association");
//    public static final Edge relation_Context1_Context_Association = new Edge(role_Context1, role_Context, "association");
//    public static final Edge relation_Context1_Component_Inheritance = new Edge(role_Context1, role_Strategy, "inheritance");
    
    public Vertex class_Strategy ;
    public List<Vertex> class_ConcreteStrategy ;
    public Vertex class_Context ;
    public StrategyPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles ,  systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteStrategy == null){
            class_ConcreteStrategy = new ArrayList<>(); 
        }
        switch(role){
            case role_Strategy:
                class_Strategy = className ;
                break;
            case role_ConcreteStrategy:
                if(canAdd(class_ConcreteStrategy, className))
                    class_ConcreteStrategy.add(className);
                break;
            case role_Context:
                class_Context = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        StrategyPattern otherStrategy = (StrategyPattern) other ;
        boolean result = class_Strategy.classLabel.equals(otherStrategy.class_Strategy.classLabel)
                &&       class_Context.classLabel.equals(otherStrategy.class_Context.classLabel);
        if(result ){
            setRole(role_ConcreteStrategy,otherStrategy.class_ConcreteStrategy.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteClass : class_ConcreteStrategy){
            String query = "select field_id from method_expression join list_field on " 
                        + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in " 
                        + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id "
                        + "where class_name like '"+ class_Context.classLabel+"' and package_name " 
                        + "like '"+ class_Context.packageName+"' ) " 
                        + "and field_type like '"+class_Strategy.classLabel+"' and " 
                        + "expression_second in " 
                        + "(select m2.method_name from list_method m2 join list_class c2 on " 
                        + "m2.method_class_id = c2.class_id and  " 
                        + "c2.class_name like +'"+ concreteClass.classLabel +"' and " 
                        + "c2.package_name like '"+ concreteClass.packageName+"')";
            
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
            class_ConcreteStrategy.remove(remove);
        }
        if(isTruePattern){
            if(class_ConcreteStrategy.size()<2){
                isTruePattern =false;
            }
        }
        
    }

}
