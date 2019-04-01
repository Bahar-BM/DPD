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
public class AbstractFactoryPattern extends Pattern{

    public static final String role_AbstractFactory  = "AbstractFactory";
    public static final String role_ConcreteFactory  = "ConFactory";
    public static final String role_AbstarctProduct  = "AbstractProduct";
    public static final String role_ConcreteProduct  = "ConProduct";
    
//    public static final Edge relation_ConcreteProduct_AbstarctProduct_Inhritance = new Edge(role_ConcreteProduct, role_AbstarctProduct, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance = new Edge(role_Composite, role_Componenet, "inheritance");
//    public static final Edge relation_Composite_Component_Inheritance_Association = new Edge(role_Composite, role_Componenet, "association");
//    public static final Edge relation_Composite1_Composite_Association = new Edge(role_Composite1, role_Composite, "association");
//    public static final Edge relation_Composite1_Component_Inheritance = new Edge(role_Composite1, role_Componenet, "inheritance");   
    
    public Vertex class_AbstractFactory ;
    public List<Vertex> class_ConcreteFactory ;
    public Vertex class_AbstarctProduct ;
    public List<Vertex> class_ConcreteProduct ;

    
    public AbstractFactoryPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteFactory == null){
            class_ConcreteFactory = new ArrayList<>(); 
        }
        if(class_ConcreteProduct == null){
            class_ConcreteProduct = new ArrayList<>(); 
        }
        switch(role){
            case role_AbstractFactory:
                class_AbstractFactory = className ;
                break;
            case role_ConcreteFactory:
                if(canAdd(class_ConcreteFactory, className))
                    class_ConcreteFactory.add(className);
                break;
            case role_AbstarctProduct:
                class_AbstarctProduct = className;
                break;
            case role_ConcreteProduct:
                 if(canAdd(class_ConcreteProduct, className))
                    class_ConcreteProduct.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        AbstractFactoryPattern otherAbstractFactory = (AbstractFactoryPattern) other ;
        boolean result = class_AbstractFactory.classLabel.equals(otherAbstractFactory.class_AbstractFactory.classLabel)
                    &&   class_AbstarctProduct.classLabel.equals(otherAbstractFactory.class_AbstarctProduct.classLabel);
        
        if(result && !otherAbstractFactory.class_ConcreteFactory.isEmpty()){
            setRole(role_ConcreteFactory,otherAbstractFactory.class_ConcreteFactory.get(0));
        }
        if(result && !otherAbstractFactory.class_ConcreteProduct.isEmpty()){
            setRole(role_ConcreteProduct,otherAbstractFactory.class_ConcreteProduct.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concreteFactory : class_ConcreteFactory){
            String query = "SELECT * from list_method join list_class on method_class_id = class_id "
                    + "where class_name like '"+concreteFactory.classLabel+"' and package_name like '"
                    + concreteFactory.packageName+"' "
                    + "and method_output_type like '"+class_AbstarctProduct.classLabel+"' and method_name in"
                    + "(select method_name from list_method join list_class on method_class_id = class_id "
                    + "where class_name like '"+class_AbstractFactory.classLabel
                    + "' and package_name like '"+class_AbstractFactory.packageName+"')";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        break;
                    }
                }
                else{
                    removedVertex.add(concreteFactory);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteFactory.remove(remove);
        }
        removedVertex = new ArrayList<>();
        List<Integer> usedProduct = new ArrayList<>(); 
        List<Vertex> unusedProduct = new ArrayList<>(); 
        for(Vertex concreteFactory : class_ConcreteFactory){
            for (Vertex concreteProduct : class_ConcreteProduct) {
                String query = "select * from method_expression join list_field "
                    + "on expression_first = field_name join list_method "
                    + "on expression_second = method_name "
                    + "where expression_type = 'creational' and SUBSTRING_INDEX(expression_second,'.',1) like "
                    + "'"+concreteFactory+"' and expression_method_id "
                    + "in(select method_id from list_method join list_class on method_class_id = class_id "
                    + "where class_name like '"+concreteProduct.classLabel+"' and package_name like '"+concreteProduct.packageName+"')";
            
                try {
                    ResultSet resultSet = SqlConnection.execute(query);
                    if(resultSet != null && resultSet.next() != false){
                        resultSet.previous();
                        while (resultSet.next()) {
                            usedProduct.add(class_ConcreteProduct.indexOf(concreteFactory));
                            isTruePattern = true ;
                            break;
                        }
                    }
                    else{
                        removedVertex.add(concreteFactory);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteFactory.remove(remove);
        }
        for (Vertex conProduct :class_ConcreteProduct) {
            boolean used = false;
            for(int index : usedProduct) {
                if(class_ConcreteProduct.indexOf(used) == index){
                    used = true;
                    break;
                }
            }
            if(!used)
                unusedProduct.add(conProduct);
        }
        for(Vertex conProduct :unusedProduct){
            class_ConcreteProduct.remove(conProduct);
        }
    }
    
}
