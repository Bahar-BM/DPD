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
public class InterpreterPattern extends Pattern {
    public static final String role_AbstractExpression  = "AbstractExpression";
    public static final String role_TerminalExpression  = "TerminalExpression";
    public static final String role_Context  = "Context";
    public static final String role_NonterminalExpression  = "NonterminalExpression";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_NonterminalExpression, role_Content, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_AbstractExpression ;
    public List<Vertex> class_TerminalExpression ;
    public Vertex class_Content ;
    public Vertex class_NonterminalExpression ;

    
    public InterpreterPattern(List<subPatternRole> subPatternRoles, Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_TerminalExpression == null){
            class_TerminalExpression = new ArrayList<>(); 
        }
        
        switch(role){
            case role_AbstractExpression:
                class_AbstractExpression = className ;
                break;
            case role_TerminalExpression:
                if(canAdd(class_TerminalExpression, className))
                    class_TerminalExpression.add(className);
                break;
            case role_Context:
                class_Content = className;
                break;
            case role_NonterminalExpression:
                class_NonterminalExpression = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        InterpreterPattern otherInterpreter = (InterpreterPattern) other ;
        boolean result = class_AbstractExpression.classLabel.equals(otherInterpreter.class_AbstractExpression.classLabel)
                    &&   class_Content.classLabel.equals(otherInterpreter.class_Content.classLabel)
                    &&   class_NonterminalExpression.classLabel.equals(otherInterpreter.class_NonterminalExpression.classLabel);
        
        if(result){
            setRole(role_TerminalExpression,otherInterpreter.class_TerminalExpression.get(0));
        }
        
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteTerminal : class_TerminalExpression){
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_NonterminalExpression.classLabel +"' and package_name like '"
                    + class_NonterminalExpression.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ concreteTerminal.classLabel +"' and package_name like '"+ concreteTerminal.packageName +"'"
                    + "and method_name in " 
                    +"(select m3.method_name from list_method m3 join list_class c3 on  m3.method_class_id = c3.class_id  "
                    + "where class_name like '"+ class_AbstractExpression.classLabel +"' and package_name like '"+ class_AbstractExpression.packageName +"'"
                    + "))) "
                    + "and field_type like '"+ class_Content.classLabel+"'";
            
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
                    removedVertex.add(concreteTerminal);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_TerminalExpression.remove(remove);
        }
    }
}
