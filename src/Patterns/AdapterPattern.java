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
public class AdapterPattern extends Pattern{
    public static final String role_Target  = "Target";
    public static final String role_Adapter  = "Adapter";
    public static final String role_Adaptee  = "Adaptee";
    
//    public static final Edge relation_ConcreteProduct_AbstarctProduct_Inhritance = new Edge(role_ConcreteProduct, role_AbstarctProduct, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Target ;
    public Vertex class_Adaptee ;
    public Vertex class_Adapter ;

    
    public AdapterPattern(List<subPatternRole> subPatternRoles ,Graph systemGraph) {
        super(subPatternRoles ,systemGraph );
    }

    @Override
    public void setRole(String role, Vertex className) {
       
        switch(role){
            case role_Target:
                class_Target = className ;
                break;
            case role_Adaptee:
                class_Adaptee = className ; 
                break;
            case role_Adapter:
                class_Adapter = className;
                break;
            
        }
        
       
    }
    @Override
    public boolean equals(Object other) {
        AdapterPattern otherAdapter = (AdapterPattern) other ;
        boolean result = class_Target.classLabel.equals(otherAdapter.class_Target.classLabel)
                    &&   class_Adaptee.classLabel.equals(otherAdapter.class_Adaptee.classLabel)
                    &&   class_Adapter.classLabel.equals(otherAdapter.class_Adapter.classLabel);
        
        return result;
    }
    
    @Override
    public void checkBehave(){
         if(class_Target.classLabel.equals("Class9") ){
            System.out.println("this");
        }
        String query = "select field_id from method_expression join list_field on " 
                + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                + "where class_name like '" + class_Adapter.classLabel +"' and package_name like '"+ class_Adapter.packageName+"' and method_name in "
                + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                + "where class_name like '"+ class_Target.classLabel +"' and package_name like '"+ class_Target.packageName + "')) "
                + "and field_type like '"+ class_Adaptee.classLabel+"'";
        
        try {
            ResultSet resultSet = SqlConnection.execute(query);
            while (resultSet != null && resultSet.next()) {
                isTruePattern =true;
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
