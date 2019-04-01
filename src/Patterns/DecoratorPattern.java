
package Patterns;

import static Patterns.BridgePattern.role_ConcreteImplementor;
import static Patterns.BridgePattern.role_RefinedAbstraction;
import java.util.List;
import bsc.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parastoo
 */
public class DecoratorPattern extends Pattern{

    public static final String role_Componenet  = "Comp";
    public static final String role_ConcreteComponenet  = "ConComp";
    public static final String role_Decorator  = "Decorator";
    public static final String role_ConcreteDecorator = "ConDecorator";
    
//    public static final Edge relation_ConcreteComponenet_Component_Inhritance = new Edge(role_ConcreteComponenet, role_Componenet, "inheritance");
//    public static final Edge relation_Decorator_Component_Inheritance = new Edge(role_Decorator, role_Componenet, "inheritance");
//    public static final Edge relation_Decorator_Component_Association = new Edge(role_Decorator, role_Componenet, "association");
//    public static final Edge relation_ConcreteDecorator_Decorator_Inheritance = new Edge(role_ConcreteDecorator, role_Decorator, "inheritance");
    
    public  Vertex class_Componenet ;
    public  List<Vertex> class_ConcreteComponenet ;
    public  Vertex class_Decorator ;
    public  List<Vertex> class_ConcreteDecorator;

    public DecoratorPattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }
    
    
    
    @Override
    public boolean equals(Object other){
        DecoratorPattern otherDecorator = (DecoratorPattern) other;
        boolean result = class_Componenet.classLabel.equals(otherDecorator.class_Componenet.classLabel)
                      && class_Decorator.classLabel.equals(otherDecorator.class_Decorator.classLabel);
        if(result && !otherDecorator.class_ConcreteComponenet.isEmpty()){
            setRole(role_ConcreteComponenet,otherDecorator.class_ConcreteComponenet.get(0));
        }
        if(result && !otherDecorator.class_ConcreteDecorator.isEmpty()){
            setRole(role_ConcreteDecorator,otherDecorator.class_ConcreteDecorator.get(0));
        }
        return result;
    }
    
    @Override
    public void setRole(String role ,Vertex className)
    {
        if(class_ConcreteComponenet == null){
            class_ConcreteComponenet = new ArrayList<>();
        }
        if(class_ConcreteDecorator == null){
            class_ConcreteDecorator = new ArrayList<>();
        }
        switch(role){
            case role_Componenet:
                class_Componenet = className ;
                break;
            case role_ConcreteComponenet:
                if(canAdd(class_ConcreteDecorator, className))
                    class_ConcreteComponenet.add(className);
                break;
            case role_Decorator:
                class_Decorator = className;
                break;
            case role_ConcreteDecorator:
                if(canAdd(class_ConcreteDecorator, className))
                    class_ConcreteDecorator.add(className);
                break;
        }
    }

    @Override
    public void checkBehave() {
    
        String query = "select field_id from method_expression join list_field on " 
                    + "field_name = substring_index(expression_first , '.',1 )"
                    + "  where expression_type like 'invocation' and expression_method_id in "
                    + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                    + "where class_name like '" + class_Decorator.classLabel +"' and package_name like '"+ class_Decorator.packageName
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
        
            List<Vertex> removedVertex = new ArrayList<>();
            for(Vertex concreteDecorator : class_ConcreteDecorator){
                String query2 = "select field_id from method_expression join list_field on " 
                        + "field_name = expression_first  where expression_type like 'invocation' and expression_method_id in "
                        + "(select m.method_id from list_method m  join list_class c on m.method_class_id = c.class_id  "
                        + "where class_name like '" + concreteDecorator.classLabel +"' and package_name like '"+ concreteDecorator.packageName+"' and method_name in "
                        + "(select m2.method_name from list_method m2 join list_class c2 on  m2.method_class_id = c2.class_id  "
                        + "where c2.class_name like '"+ class_Decorator.classLabel +"' and c2.package_name like '"+ class_Decorator.packageName + "'"
                        + "and m2.method_name from list_method m3 join list_class c3 on m3.method_class_id = c3.class_id where c3.class_name like '"
                        + class_Componenet.classLabel+"' and c3.package_name  like '"+ class_Componenet.packageName
                        + "'))) "
                        + "and field_type like '"+ class_Componenet.classLabel+"'";

                try {
                    ResultSet resultSet = SqlConnection.execute(query);
                    if(resultSet != null && resultSet.next() != false){
                        while (resultSet.next()) {
                            isTruePattern =true;
                            break;
                        }
                    }
                    else{
                        removedVertex.add(concreteDecorator);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Vertex remove :removedVertex) {
                class_ConcreteDecorator.remove(remove);
            }
        
    }

}
   
