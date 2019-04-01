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
public class BuilderPattern  extends Pattern{
    public static final String role_Builder  = "Builder";
    public static final String role_ConcreteBuilder  = "ConBuilder";
    public static final String role_Director  = "Director";
    public static final String role_Product  = "Product";
    
//    public static final Edge relation_RefinedAbstraction_Abstraction_Inhritance = new Edge(role_Product, role_Director, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");
//    
    public Vertex class_Builder ;
    public List<Vertex> class_ConcreteBuilder ;
    public Vertex class_Director ;
    public Vertex class_Product ;

    
    public BuilderPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteBuilder == null){
            class_ConcreteBuilder = new ArrayList<>(); 
        }
        
        switch(role){
            case role_Builder:
                class_Builder = className ;
                break;
            case role_ConcreteBuilder:
                if(canAdd(class_ConcreteBuilder, className))
                    class_ConcreteBuilder.add(className);
                break;
            case role_Director:
                class_Director = className;
                break;
            case role_Product:
                class_Product = className;
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        BuilderPattern otherBuilder = (BuilderPattern) other ;
        boolean result = class_Builder.classLabel.equals(otherBuilder.class_Builder.classLabel)
                    &&   class_Director.classLabel.equals(otherBuilder.class_Director.classLabel)
                    &&   class_Product.classLabel.equals(otherBuilder.class_Product.classLabel);
        
        if(result){
            setRole(role_ConcreteBuilder,otherBuilder.class_ConcreteBuilder.get(0));
        }
        
        return result;
    }

    @Override
    public void checkBehave() {
        boolean firstPartIsTrue =false;
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteBuilder : class_ConcreteBuilder){
            String query = "select * from list_method join list_class on method_class_id = class_id "
                    + "where class_name = '"+concreteBuilder.classLabel+"' and package_name ='"
                    + concreteBuilder.packageName+"' and method_output_type = '"+class_Product.classLabel
                    + "' and method_name in (select method_name from list_method join list_class "
                    + "on method_class_id = class_id where class_name = '"+class_Builder.packageName+"' and package_name ='"
                    + class_Builder.packageName+"')";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        firstPartIsTrue = true;
                        break;
                    }
                }
                else{
                    removedVertex.add(concreteBuilder);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteBuilder.remove(remove);
        }
        removedVertex = new ArrayList<>();
        for(Vertex concreteBuilder : class_ConcreteBuilder){
            String query = "select field_id from method_expression join list_field on " 
                        + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in " 
                        + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id "
                        + "where class_name like '"+ class_Director.classLabel+"' and package_name " 
                        + "like '"+ class_Director.packageName+"' ) " 
                        + "and field_type like '"+class_Builder.classLabel+"' and " 
                        + "expression_second in " 
                        + "(select m2.method_name from list_method m2 join list_class c2 on " 
                        + "m2.method_class_id = c2.class_id and  " 
                        + "c2.class_name like +'"+ concreteBuilder.classLabel +"' and " 
                        + "c2.package_name like '"+ concreteBuilder.packageName+"')";
            
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
                    removedVertex.add(concreteBuilder);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteBuilder.remove(remove);
        }
    }
}
