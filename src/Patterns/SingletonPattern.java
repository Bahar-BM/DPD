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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parastoo
 */
public class SingletonPattern extends Pattern{
    public static final String role_Singleton  = "Singleton";
    
//    public static final Edge relation_ConcreteProduct_AbstarctProduct_Inhritance = new Edge(role_ConcreteProduct, role_AbstarctProduct, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Singleton ;

    
    public SingletonPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
       
        switch(role){
            case role_Singleton:
                class_Singleton = className ;
                break;
            
            
        }
    }
    @Override
    public boolean equals(Object other) {
        SingletonPattern otherSingleton = (SingletonPattern) other ;
        boolean result = class_Singleton.classLabel.equals(otherSingleton.class_Singleton.classLabel);
        
        return result;
    }
    @Override
    public void checkBehave(){
        String query = "select method_visibility,method_is_static "
                + "from list_method "
                + "where method_class_id in(SELECT class_id from list_class where class_name ="
                +"'"+class_Singleton.classLabel+"' and package_name = "+ "'"+class_Singleton.packageName+"'"
                +") "
                + "and method_type =1";
        
        try {
            ResultSet resultSet = SqlConnection.execute(query);
            while (resultSet != null && resultSet.next()) {
                int isStatic = resultSet.getInt("method_is_static");   
                String visibility = resultSet.getString("method_visibility");
                if( (isStatic == 1 && visibility.equals("public"))
                    ||  (visibility.equals("private"))
                    ||  (visibility.equals("protected"))
                ){
                    isTruePattern = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
