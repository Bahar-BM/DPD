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
public class ObserverPattern extends Pattern{
    public static final String role_Observer  = "Observer";
    public static final String role_ConcreteObserver  = "ConObserver";
    public static final String role_Subject  = "Subject";
    public static final String role_ConcreteSubject  = "ConSubject";
    
//    public static final Edge relation_ConcreteSubject_Subject_Inhritance = new Edge(role_ConcreteSubject, role_Subject, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Observer ;
    public List<Vertex> class_ConcreteObserver ;
    public Vertex class_Subject ;
    public List<Vertex> class_ConcreteSubject ;

    
    public ObserverPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteObserver == null){
            class_ConcreteObserver = new ArrayList<>(); 
        }
        if(class_ConcreteSubject == null){
            class_ConcreteSubject = new ArrayList<>(); 
        }
        switch(role){
            case role_Observer:
                class_Observer = className ;
                break;
            case role_ConcreteObserver:
                if(canAdd(class_ConcreteObserver, className))
                    class_ConcreteObserver.add(className);
                break;
            case role_Subject:
                class_Subject = className;
                break;
            case role_ConcreteSubject:
                 if(canAdd(class_ConcreteSubject, className))
                    class_ConcreteSubject.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        ObserverPattern otherObserver = (ObserverPattern) other ;
        boolean result = class_Observer.classLabel.equals(otherObserver.class_Observer.classLabel)
                    &&   class_Subject.classLabel.equals(otherObserver.class_Subject.classLabel);
        
        if(result && !otherObserver.class_ConcreteObserver.isEmpty()){
            setRole(role_ConcreteObserver,otherObserver.class_ConcreteObserver.get(0));
        }
        if(result && !otherObserver.class_ConcreteSubject.isEmpty()){
            setRole(role_ConcreteSubject,otherObserver.class_ConcreteSubject.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteClass : class_ConcreteObserver){
            String query = "select field_id from method_expression join list_field on " 
                        + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in " 
                        + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id "
                        + "where class_name like '"+ class_Subject.classLabel+"' and package_name " 
                        + "like '"+ class_Subject.packageName+"' ) " 
                        + "and field_type like '"+class_Observer.classLabel+"' and " 
                        + "expression_second in " 
                        + "(select m2.method_name from list_method m2 join list_class c2 on " 
                        + "m2.method_class_id = c2.class_id and  " 
                        + "c2.class_name like +'"+ concreteClass.classLabel +"' and " 
                        + "c2.package_name like '"+concreteClass.packageName+"')";
            
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
            class_ConcreteObserver.remove(remove);
        }
        
    }
}
