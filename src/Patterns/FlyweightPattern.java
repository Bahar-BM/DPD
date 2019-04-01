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
public class FlyweightPattern extends Pattern{
    public static final String role_Flyweight  = "Flyweight";
    public static final String role_ConcreteFlyweight  = "ConFlyweight";
    public static final String role_FlyweightFactory  = "FlyweightFactory";
    
//    public static final Edge relation_ConcreteFlyweight_Component_Inhritance = new Edge(role_ConcreteFlyweight, role_Flyweight, "inheritance");
//    public static final Edge relation_FlyweightFactory_Component_Inheritance = new Edge(role_FlyweightFactory, role_Flyweight, "inheritance");
//    public static final Edge relation_FlyweightFactory_Component_Inheritance_Association = new Edge(role_FlyweightFactory, role_Flyweight, "association");
//    public static final Edge relation_FlyweightFactory1_FlyweightFactory_Association = new Edge(role_FlyweightFactory1, role_FlyweightFactory, "association");
//    public static final Edge relation_FlyweightFactory1_Component_Inheritance = new Edge(role_FlyweightFactory1, role_Flyweight, "inheritance");
    
    public Vertex class_Flyweight ;
    public List<Vertex> class_ConcreteFlyweight ;
    public Vertex class_FlyweightFactory ;
    
    public FlyweightPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteFlyweight == null){
            class_ConcreteFlyweight = new ArrayList<>(); 
        }
        switch(role){
            case role_Flyweight:
                class_Flyweight = className ;
                break;
            case role_ConcreteFlyweight:
                if(canAdd(class_ConcreteFlyweight, className))
                    class_ConcreteFlyweight.add(className);
                break;
            case role_FlyweightFactory:
                class_FlyweightFactory = className;
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        FlyweightPattern otherFlyweight = (FlyweightPattern) other ;
        boolean result = class_Flyweight.classLabel.equals(otherFlyweight.class_Flyweight.classLabel)
                    &&   class_FlyweightFactory.classLabel.equals(otherFlyweight.class_FlyweightFactory.classLabel);
        
        if(result){
            setRole(role_ConcreteFlyweight,otherFlyweight.class_ConcreteFlyweight.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        boolean firstPartTrue = false;
        String query = "select * from list_method where method_class_id in"
                   + "(select class_id from list_class where class_name ='"+class_FlyweightFactory.classLabel
                   + "' and package_name = '"+class_FlyweightFactory.packageName
                   + "') and method_output_type = '"+class_Flyweight.classLabel+"'";

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
            for(Vertex concreteFlyweight : class_ConcreteFlyweight){
                String query2 = "select field_id from method_expression join list_field on " 
                    + "field_name = substring_index(expression_second , '(',1 )"
                    + "  where expression_type like 'creational' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_FlyweightFactory.classLabel +"' and package_name like '"+ class_FlyweightFactory.packageName
                    + "') "
                    + "and SUBSTRING_INDEX(SUBSTRING_INDEX(field_type, '<', -1), '>', 1)  like '"+ concreteFlyweight.classLabel+"'";
            

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
                        removedVertex.add(concreteFlyweight);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Vertex remove :removedVertex) {
                class_ConcreteFlyweight.remove(remove);
            }
        }
    }
    
}
