/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patterns;

import bsc.Edge;
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
public class StatePattern extends Pattern{
    public static final String role_State  = "State";
    public static final String role_ConcreteState  = "ConState";
    public static final String role_Context  = "Context";
    
//    public static final Edge relation_ConcreteState_Component_Inhritance = new Edge(role_ConcreteState, role_State, "inheritance");
//    public static final Edge relation_Context_Component_Inheritance = new Edge(role_Context, role_State, "inheritance");
//    public static final Edge relation_Context_Component_Inheritance_Association = new Edge(role_Context, role_State, "association");
//    public static final Edge relation_Context1_Context_Association = new Edge(role_Context1, role_Context, "association");
//    public static final Edge relation_Context1_Component_Inheritance = new Edge(role_Context1, role_State, "inheritance");
    
    public Vertex class_State ;
    public List<Vertex> class_ConcreteState ;
    public Vertex class_Context ;
    public StatePattern(List<subPatternRole> subPatternRoles , Graph systemGraph ) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteState == null){
            class_ConcreteState = new ArrayList<>(); 
        }
        switch(role){
            case role_State:
                class_State = className ;
                break;
            case role_ConcreteState:
                if(canAdd(class_ConcreteState, className))
                    class_ConcreteState.add(className);
                break;
            case role_Context:
                class_Context = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        StatePattern otherState = (StatePattern) other ;
        boolean result = class_State.classLabel.equals(otherState.class_State.classLabel)
                &&       class_Context.classLabel.equals(otherState.class_Context.classLabel);
        if(result ){
            setRole(role_ConcreteState,otherState.class_ConcreteState.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        if(class_Context.classLabel.equals("Class58") && class_State.classLabel.equals("Class59")){
            System.out.println("this");
        }
        List<Vertex> removedVertex = new ArrayList<>();
        for (Vertex conStateA : class_ConcreteState) {
            boolean isTrueState = false;
            for (Vertex conStateB : class_ConcreteState) {
                String query = "select field_id from method_expression join list_field on " 
                    + "field_name = substring_index(expression_second , '(',1 )"
                    + "  where expression_type like 'creational' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + conStateA.classLabel +"' and package_name like '"+ conStateA.packageName
                    + "' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_State.classLabel +"' and package_name like '"+ class_State.packageName + "')) "
                    + "and SUBSTRING_INDEX(SUBSTRING_INDEX(field_type, '<', -1), '>', 1)  like '"+ conStateB.classLabel+"'";
            
                try {
                    ResultSet resultSet = SqlConnection.execute(query);
                    if(resultSet != null && resultSet.next() != false){
                        resultSet.previous();
                        while (resultSet.next()) {
                            isTruePattern =true;
                            isTrueState = true;
                            break;
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
            if(!isTrueState)
                removedVertex.add(conStateA);
        }
        for (Vertex remove :removedVertex) {
                class_ConcreteState.remove(remove);
        }
    }

}
