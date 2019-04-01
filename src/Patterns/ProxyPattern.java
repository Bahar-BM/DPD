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
public class ProxyPattern extends Pattern{
    public static final String role_Subject  = "Subject";
    public static final String role_Proxy  = "Proxy";
    public static final String role_RealSubject  = "RealSubject";
    
//    public static final Edge relation_ConcreteProduct_AbstarctProduct_Inhritance = new Edge(role_ConcreteProduct, role_AbstarctProduct, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Subject ;
    public Vertex class_Proxy ;
    public Vertex class_RealSubject ;

    
    public ProxyPattern(List<subPatternRole> subPatternRoles, Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
       
        switch(role){
            case role_Subject:
                class_Subject = className ;
                break;
            case role_RealSubject:
                class_RealSubject = className ; 
                break;
            case role_Proxy:
                class_Proxy = className;
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        ProxyPattern otherProxy = (ProxyPattern) other ;
        boolean result = class_Subject.classLabel.equals(otherProxy.class_Subject.classLabel)
                    &&   class_RealSubject.classLabel.equals(otherProxy.class_RealSubject.classLabel)
                    &&   class_Proxy.classLabel.equals(otherProxy.class_Proxy);
        
        return result;
    }

    @Override
    public void checkBehave() {
        String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_Proxy.classLabel +"' and package_name like '"+ class_Proxy.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Subject.classLabel +"' and package_name like '"+ class_Subject.packageName + "')) "
                    + "and field_type like '"+ class_RealSubject.classLabel+"'";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        isTruePattern =true;
                        break;
                    }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
