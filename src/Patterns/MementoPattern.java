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
public class MementoPattern extends Pattern{
    public static final String role_Memento  = "Memento";
    public static final String role_MementoImp  = "MementoImp";
    public static final String role_Cakertaker  = "Cakertaker";
    public static final String role_Originator  = "Originator";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Originator, role_Caretaker, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Memento ;
    public List<Vertex> class_MementoImp ;
    public Vertex class_Cakertaker ;
    public Vertex class_Originator ;

    
    public MementoPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_MementoImp == null){
            class_MementoImp = new ArrayList<>(); 
        }
        
        switch(role){
            case role_Memento:
                class_Memento = className ;
                break;
            case role_MementoImp:
                if(canAdd(class_MementoImp, className))
                    class_MementoImp.add(className);
                break;
            case role_Cakertaker:
                class_Cakertaker = className;
                break;
            case role_Originator:
                class_Originator = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        MementoPattern otherMemento = (MementoPattern) other ;
        boolean result = class_Memento.classLabel.equals(otherMemento.class_Memento.classLabel)
                    &&   class_Cakertaker.classLabel.equals(otherMemento.class_Cakertaker.classLabel)
                    &&   class_Originator.classLabel.equals(otherMemento.class_Originator.classLabel);
        
        if(result){
            setRole(role_MementoImp,otherMemento.class_MementoImp.get(0));
        }
        
        return result;
    }

    @Override
    public void checkBehave() {
        boolean firstPartTrue = false;
        String query = "select * from list_method where method_class_id in"
                   + "(select class_id from list_class where class_name ='"+class_Cakertaker.classLabel
                   + "' and package_name = '"+class_Cakertaker.packageName
                   + "') and method_output_type = '"+class_Memento.classLabel+"'";

            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        firstPartTrue = true;
                        break; 
                   }
                }

            } catch (SQLException ex) {
               Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        List<Vertex> removedVertex = new ArrayList<>();
        if(firstPartTrue){
            for(Vertex concreteMemento : class_MementoImp){
                String query2 = "select field_id from method_expression join list_field on " 
                    + "field_name = substring_index(expression_second , '(',1 )"
                    + "  where expression_type like 'creational' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_Originator.classLabel +"' and package_name like '"+ concreteMemento.packageName
                    + "') "
                    + "and SUBSTRING_INDEX(SUBSTRING_INDEX(field_type, '<', -1), '>', 1)  like '"+ concreteMemento.classLabel+"'";
            

                try {
                    ResultSet resultSet = SqlConnection.execute(query2);
                    if(resultSet.next() != false){
                        resultSet.previous();
                        while (resultSet.next()) {
                            isTruePattern =true;
                            break;
                        }
                    }
                    else{
                        removedVertex.add(concreteMemento);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Vertex remove :removedVertex) {
                class_MementoImp.remove(remove);
            }
        }
    }
}
