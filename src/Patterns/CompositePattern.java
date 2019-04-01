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
public class CompositePattern extends Pattern{

    public static final String role_Componenet  = "Comp";
    public static final String role_ConcreteComponenet  = "ConComp";
    public static final String role_Composite  = "Composite";
    public static final String role_Composite1 = "Composite1";
    
    public static final Edge relation_ConcreteComponenet_Component_Inhritance = new Edge(role_ConcreteComponenet, role_Componenet, "inheritance");
    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
    
    public Vertex class_Componenet ;
    public List<Vertex> class_ConcreteComponenet ;
    public Vertex class_Composite ;
    public Vertex class_Composite1 ;
    
    public CompositePattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteComponenet == null){
            class_ConcreteComponenet = new ArrayList<>(); 
        }
        switch(role){
            case role_Componenet:
                class_Componenet = className ;
                break;
            case role_ConcreteComponenet:
                if(canAdd(class_ConcreteComponenet, className))
                    class_ConcreteComponenet.add(className);
                break;
            case role_Composite:
                class_Composite = className;
                break;
            case role_Composite1:
                class_Composite1 = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        CompositePattern otherComposite = (CompositePattern) other ;
        boolean result = class_Componenet.classLabel.equals(otherComposite.class_Componenet.classLabel);
        if(otherComposite.class_Composite != null){
            result = result 
                    && class_Composite.classLabel.equals(otherComposite.class_Composite.classLabel);
        }
        if(otherComposite.class_Composite1 != null){
            result = result 
                    && class_Composite1.classLabel.equals(otherComposite.class_Composite1.classLabel);
        }
        if(result && !otherComposite.class_ConcreteComponenet.isEmpty()){
            setRole(role_ConcreteComponenet,otherComposite.class_ConcreteComponenet.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        
        if(class_Composite != null){
            
            if(class_Composite.classLabel.equals("Class29")){
                System.out.println("this");
            }
            
            String query = "select field_id from method_expression join list_field on " 
                    + "field_name = substring_index(expression_first , '.',1 )"
                    + "  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_Composite.classLabel +"' and package_name like '"+ class_Composite.packageName
                    + "' and method_name in "
                    + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                    + "where class_name like '"+ class_Componenet.classLabel +"' and package_name like '"+ class_Componenet.packageName + "')) "
                    + "and SUBSTRING_INDEX(SUBSTRING_INDEX(field_type, '<', -1), '>', 1)  like '"+ class_Componenet.classLabel+"'";
            
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

    
    
}
