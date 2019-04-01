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
public class FactoryMethodPattern extends Pattern{
    public static final String role_Product  = "Product";
    public static final String role_ConcreteProduct  = "ConProduct";
    public static final String role_Creator  = "Creator";
    public static final String role_ConcreteCreator  = "ConCreator";
       
    public Vertex class_Product ;
    public List<Vertex> class_ConcreteProduct ;
    public Vertex class_Creator ;
    public List<Vertex> class_ConcreteCreator ;

    
    public FactoryMethodPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcreteProduct == null){
            class_ConcreteProduct = new ArrayList<>(); 
        }
        if(class_ConcreteCreator == null){
            class_ConcreteCreator = new ArrayList<>(); 
        }
        switch(role){
            case role_Product:
                class_Product = className ;
                break;
            case role_ConcreteProduct:
                if(canAdd(class_ConcreteProduct, className))
                    class_ConcreteProduct.add(className);
                break;
            case role_Creator:
                class_Creator = className;
                break;
            case role_ConcreteCreator:
                 if(canAdd(class_ConcreteCreator, className))
                    class_ConcreteCreator.add(className);
                break;
        }
    }
    @Override
    public boolean equals(Object other) {
        FactoryMethodPattern otherFactoryMethod = (FactoryMethodPattern) other ;
        boolean result = class_Product.classLabel.equals(otherFactoryMethod.class_Product.classLabel)
                    &&   class_Creator.classLabel.equals(otherFactoryMethod.class_Creator.classLabel);
        
        if(result && !otherFactoryMethod.class_ConcreteProduct.isEmpty()){
            setRole(role_ConcreteProduct,otherFactoryMethod.class_ConcreteProduct.get(0));
        }
        if(result && !otherFactoryMethod.class_ConcreteCreator.isEmpty()){
            setRole(role_ConcreteCreator,otherFactoryMethod.class_ConcreteCreator.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        List<Integer> usedProduct = new ArrayList<>(); 
        List<Vertex> unusedProduct = new ArrayList<>(); 
                    
        for(Vertex concretecreator : class_ConcreteCreator){
            String query = "SELECT * from list_method join list_class on method_class_id = class_id "
                    + "where class_name like '"+concretecreator.classLabel+"' and package_name like '"
                    + concretecreator.packageName+"' "
                    + "and method_output_type like '"+class_Product.classLabel+"' and method_name in"
                    + "(select method_name from list_method join list_class on method_class_id = class_id "
                    + "where class_name like '"+class_Creator.classLabel
                    + "' and package_name like '"+class_Creator.packageName+"')";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        isTruePattern = true;
                        break;
                    }
                }
                else{
                    boolean isTrueconcreteCreator = false;
                    //in query ro khodam ezafe kardam ke jae product type khroji conproduct ro ham check mikone!
                    for(Vertex concreteProduct : class_ConcreteProduct){
                        String query2 = "SELECT * from list_method join list_class on method_class_id = class_id "
                                + "where class_name like '"+concretecreator.classLabel+"' and package_name like '"
                                + concretecreator.packageName+"' "
                                + "and method_output_type like '"+concreteProduct.classLabel+"' and method_name in"
                                + "(select method_name from list_method join list_class on method_class_id = class_id "
                                + "where class_name like '"+class_Creator.classLabel
                                + "' and package_name like '"+class_Creator.packageName+"')";
                        try {
                            ResultSet resultSet2 = SqlConnection.execute(query2);
                            if(resultSet2.next() != false){
                                resultSet2.previous();
                                while (resultSet2.next()) {
                                    usedProduct.add(class_ConcreteProduct.indexOf(concreteProduct));
                                    isTruePattern = true ;
                                    isTrueconcreteCreator = true;
                                    break;
                                }
                            }
                            else{
                                
                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                    if(!isTrueconcreteCreator)
                        removedVertex.add(concretecreator);
                                
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcreteCreator.remove(remove);
        }
        for (Vertex conProduct :class_ConcreteProduct) {
            boolean used = false;
            for(int index : usedProduct) {
                if(class_ConcreteProduct.indexOf(conProduct) == index){
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
