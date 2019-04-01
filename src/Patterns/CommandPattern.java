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
public class CommandPattern extends Pattern{
    public static final String role_Command  = "Command";
    public static final String role_ConcreteCommand  = "ConCommand";
    public static final String role_Invoker  = "Invoker";
    public static final String role_Receiver  = "Receiver";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Receiver, role_Invoker, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Command ;
    public List<Vertex> class_ConcreteCommand ;
    public Vertex class_Invoker ;
    public Vertex class_Receiver ;

    
    public CommandPattern(List<subPatternRole> subPatternRoles , Graph systemGraph ) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteCommand == null){
            class_ConcreteCommand = new ArrayList<>(); 
        }
        
        switch(role){
            case role_Command:
                class_Command = className ;
                break;
            case role_ConcreteCommand:
                if(canAdd(class_ConcreteCommand, className))
                    class_ConcreteCommand.add(className);
                break;
            case role_Invoker:
                class_Invoker = className;
                break;
            case role_Receiver:
                class_Receiver = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        CommandPattern otherCommand = (CommandPattern) other ;
        boolean result = class_Command.classLabel.equals(otherCommand.class_Command.classLabel)
                    &&   class_Invoker.classLabel.equals(otherCommand.class_Invoker.classLabel)
                    &&   class_Receiver.classLabel.equals(otherCommand.class_Receiver.classLabel);
        
        if(result){
            setRole(role_ConcreteCommand,otherCommand.class_ConcreteCommand.get(0));
        }
        
        return result;
    }

    @Override
    public void checkBehave() {
        boolean firstPartIsTrue =false;
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteCommand : class_ConcreteCommand){
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + concreteCommand.classLabel +"' and package_name like '"+ concreteCommand.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Command.classLabel +"' and package_name like '"+ class_Command.packageName + "')) "
                    + "and field_type like '"+ class_Receiver.classLabel+"'";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        firstPartIsTrue = true;
                        break;
                    }
                }
                else{
                    removedVertex.add(concreteCommand);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteCommand.remove(remove);
        }
        removedVertex = new ArrayList<>();
        for(Vertex concreteCommand : class_ConcreteCommand){
             String query = "select field_id from method_expression join list_field on " 
                        + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in " 
                        + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id "
                        + "where class_name like '"+ class_Invoker.classLabel+"' and package_name " 
                        + "like '"+ class_Invoker.packageName+"' ) " 
                        + "and field_type like '"+class_Command.classLabel+"' and " 
                        + "expression_second in " 
                        + "(select m2.method_name from list_method m2 join list_class c2 on " 
                        + "m2.method_class_id = c2.class_id and  " 
                        + "c2.class_name like +'"+ concreteCommand.classLabel +"' and " 
                        + "c2.package_name like '"+ concreteCommand.packageName+"')";
            
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
                    removedVertex.add(concreteCommand);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteCommand.remove(remove);
        }
    }
}
