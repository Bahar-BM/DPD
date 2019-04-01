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
public class FacadePattern extends Pattern {
    public static final String role_Facade  = "Facade";
    public static final String role_ConcreteFacade  = "ConFacade";
    public static final String role_SubSystem  = "Subsystem";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Receiver, role_SubSystem, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Facade ;
    public Vertex class_ConcreteFacade ;
    public List<Vertex> class_SubSystem  ;

    
    public FacadePattern(List<subPatternRole> subPatternRoles, Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_SubSystem == null){
            class_SubSystem = new ArrayList<>(); 
        }
        
        switch(role){
            case role_Facade:
                class_Facade = className ;
                break;
            case role_ConcreteFacade:
                class_ConcreteFacade =className;
                break;
            case role_SubSystem:
                if(canAdd(class_SubSystem, className))
                    class_SubSystem.add(className);
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        FacadePattern otherFacade = (FacadePattern) other ;
        boolean result = class_Facade.classLabel.equals(otherFacade.class_Facade.classLabel)
                    &&   class_ConcreteFacade.classLabel.equals(otherFacade.class_ConcreteFacade.classLabel);
        
        if(result){
            setRole(role_SubSystem,otherFacade.class_SubSystem.get(0));
        }
        
        return result;
    }

    @Override
    public void checkBehave() {
        
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteSubSystem : class_SubSystem){
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_ConcreteFacade.classLabel +"' and package_name like '"+ class_ConcreteFacade.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Facade.classLabel +"' and package_name like '"+ class_Facade.packageName + "')) "
                    + "and field_type like '"+ concreteSubSystem.classLabel+"'";
            
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
                    removedVertex.add(concreteSubSystem);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_SubSystem.remove(remove);
        }
    }
}
