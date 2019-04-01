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
public class VisitorPattern extends Pattern{
    public static final String role_Visitor  = "Visitor";
    public static final String role_ConcreteVisitor  = "ConVisitor";
    public static final String role_Element  = "Element";
    public static final String role_ConcreteElement  = "ConElement";
    public static final String role_ObjectStructure  = "ObjectStructure";
    
  
    public Vertex class_Visitor ;
    public List<Vertex> class_ConcreteVisitor ;
    public Vertex class_Element ;
    public List<Vertex> class_ConcreteElement ;
    public Vertex class_ObjectStructure ;
    
    
    public VisitorPattern(List<subPatternRole> subPatternRoles ,  Graph systemGraph) {
        super(subPatternRoles,  systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteVisitor == null){
            class_ConcreteVisitor = new ArrayList<>(); 
        }
        if(class_ConcreteElement == null){
            class_ConcreteElement = new ArrayList<>(); 
        }
        switch(role){
            case role_Visitor:
                class_Visitor = className ;
                break;
            case role_ConcreteVisitor:
                if(canAdd(class_ConcreteVisitor, className))
                    class_ConcreteVisitor.add(className);
                break;
            case role_Element:
                class_Element = className;
                break;
            case role_ConcreteElement:
                 if(canAdd(class_ConcreteElement, className))
                    class_ConcreteElement.add(className);
                break;
            case role_ObjectStructure:
                class_ObjectStructure = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        VisitorPattern otherVisitor = (VisitorPattern) other ;
        boolean result = class_Visitor.classLabel.equals(otherVisitor.class_Visitor.classLabel)
                    &&   class_Element.classLabel.equals(otherVisitor.class_Element.classLabel)
                    &&   class_ObjectStructure.classLabel.equals(otherVisitor.class_ObjectStructure.classLabel);
        
        if(result ){
            setRole(role_ConcreteVisitor,otherVisitor.class_ConcreteVisitor.get(0));
        }
        if(result){
            setRole(role_ConcreteElement,otherVisitor.class_ConcreteElement.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteElement : class_ConcreteElement){
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + concreteElement.classLabel +"' and package_name like '"+ concreteElement.packageName+"' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Element.classLabel +"' and package_name like '"+ class_Element.packageName + "')) "
                    + "and field_type like '"+ class_Visitor.classLabel+"'";
            
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
                    removedVertex.add(concreteElement);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteElement.remove(remove);
        }
    }
}
