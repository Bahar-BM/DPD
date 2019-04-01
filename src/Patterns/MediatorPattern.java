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
public class MediatorPattern extends Pattern{
    public static final String role_Colleague  = "Colleague";
    public static final String role_ConcreteColleague  = "ConColleague";
    public static final String role_Mediator  = "Mediator";
    public static final String role_ConcreteMediator  = "ConMediator";
    
//    public static final Edge relation_ConcreteMediator_Mediator_Inhritance = new Edge(role_ConcreteMediator, role_Mediator, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Colleague ;
    public List<Vertex> class_ConcreteColleague ;
    public Vertex class_Mediator ;
    public List<Vertex> class_ConcreteMediator ;

    
    public MediatorPattern(List<subPatternRole> subPatternRoles, Graph systemGraph) {
        super(subPatternRoles, systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteColleague == null){
            class_ConcreteColleague = new ArrayList<>(); 
        }
        if(class_ConcreteMediator == null){
            class_ConcreteMediator = new ArrayList<>(); 
        }
        switch(role){
            case role_Colleague:
                class_Colleague = className ;
                break;
            case role_ConcreteColleague:
                if(canAdd(class_ConcreteColleague, className))
                    class_ConcreteColleague.add(className);
                break;
            case role_Mediator:
                class_Mediator = className;
                break;
            case role_ConcreteMediator:
                 if(canAdd(class_ConcreteMediator, className))
                    class_ConcreteMediator.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        MediatorPattern otherMediator = (MediatorPattern) other ;
        boolean result = class_Colleague.classLabel.equals(otherMediator.class_Colleague.classLabel)
                    &&   class_Mediator.classLabel.equals(otherMediator.class_Mediator.classLabel);
        
        if(result && !otherMediator.class_ConcreteColleague.isEmpty()){
            setRole(role_ConcreteColleague,otherMediator.class_ConcreteColleague.get(0));
        }
        if(result && !otherMediator.class_ConcreteMediator.isEmpty()){
            setRole(role_ConcreteMediator,otherMediator.class_ConcreteMediator.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        boolean firstPartTrue = false;
        String query = "select * from list_method where method_class_id in"
                   + "(select class_id from list_class where class_name ='"+class_Colleague.classLabel
                   + "' and package_name = '"+class_Colleague.packageName
                   + "') and method_output_type = '"+class_Mediator.classLabel+"'";

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
            for(Vertex concreteMediator : class_ConcreteMediator){
                String query2 = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + concreteMediator.classLabel +"' and package_name like '"+ concreteMediator.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Mediator.classLabel +"' and package_name like '"+ class_Mediator.packageName + "')) "
                    + "and field_type like '"+ class_Colleague.classLabel+"'";
            

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
                        removedVertex.add(concreteMediator);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Vertex remove :removedVertex) {
                class_ConcreteMediator.remove(remove);
            }
        }
    }
    
}
