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
public class PrototypePattern extends Pattern{
    public static final String role_Prototype  = "Prototype";
    public static final String role_ConcretePrototype  = "ConPrototype";
    public static final String role_Client  = "Client";
    
//    public static final Edge relation_ConcretePrototype_Component_Inhritance = new Edge(role_ConcretePrototype, role_Prototype, "inheritance");
//    public static final Edge relation_Client_Component_Inheritance = new Edge(role_Client, role_Prototype, "inheritance");
//    public static final Edge relation_Client_Component_Inheritance_Association = new Edge(role_Client, role_Prototype, "association");
//    public static final Edge relation_Client1_Client_Association = new Edge(role_Client1, role_Client, "association");
//    public static final Edge relation_Client1_Component_Inheritance = new Edge(role_Client1, role_Prototype, "inheritance");
    
    public Vertex class_Prototype ;
    public List<Vertex> class_ConcretePrototype ;
    public Vertex class_Client ;
    
    public PrototypePattern(List<subPatternRole> subPatternRoles , Graph systemGraph) {
        super(subPatternRoles , systemGraph);
    }

    @Override
    public void setRole(String role, Vertex className) {
        if(class_ConcretePrototype == null){
            class_ConcretePrototype = new ArrayList<>(); 
        }
        switch(role){
            case role_Prototype:
                class_Prototype = className ;
                break;
            case role_ConcretePrototype:
                if(canAdd(class_ConcretePrototype, className))
                    class_ConcretePrototype.add(className);
                break;
            case role_Client:
                class_Client = className;
                break;
            
        }
    }
    @Override
    public boolean equals(Object other) {
        PrototypePattern otherPrototype = (PrototypePattern) other ;
        boolean result = class_Prototype.classLabel.equals(otherPrototype.class_Prototype.classLabel)
                    &&   class_Client.classLabel.equals(otherPrototype.class_Client.classLabel);
        
        if(result){
            setRole(role_ConcretePrototype,otherPrototype.class_ConcretePrototype.get(0));
        }
        return result;
    }

    @Override
    public void checkBehave() {
        List<Vertex> removedVertex = new ArrayList<>();
        for(Vertex concretePrototype: class_ConcretePrototype){
            String query = "select class_parents_interface  "
                    + "from list_class where class_name = '"+concretePrototype.classLabel+"'"
                    + "  and package_name = '"+concretePrototype.classLabel+"'";
            
            try {
                ResultSet resultSet = SqlConnection.execute(query);
                if(resultSet != null && resultSet.next() != false){
                    resultSet.previous();
                    while (resultSet.next()) {
                        String class_parents_interface = resultSet.getString("class_parents_interface");
                        String[] interfaces = class_parents_interface.split(",");
                        for(String str :interfaces){
                            if(str.equals(class_Prototype.classLabel)){
                                isTruePattern = true;
                                break;
                            }
                        }
                    }
                }
                else{
                    removedVertex.add(concretePrototype);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SingletonPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Vertex remove :removedVertex) {
            class_ConcretePrototype.remove(remove);
        }
    
    }
}
