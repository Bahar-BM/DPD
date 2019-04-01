
package bsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import patternDetails.*; 
import resultStructureModel.Entry;
import resultStructureModel.GroupInfo;
import resultStructureModel.InstanceInfo;
import resultStructureModel.JsonWriter;
import resultStructureModel.PatternsGroupInfo;
import resultMaker.ResultViewer;
import Patterns.*;
public class BSCFile {

    
    public File input;
    public BufferedReader reader;
    public List<String> subPatterns;
    
    public static List<String> readFile (String directory) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(directory));
        List<String> results = new ArrayList<>();
        try {
            String line = reader.readLine();
            while (line != null){
                results.add(line);
                line = reader.readLine();
            }
            return results;
        } finally {
            reader.close();
        }
    }

    public static void writeFile (String directory ,HashMap<String,List<Object>> patterns){
        

        PatternsGroupInfo patternsGroupInfo = new PatternsGroupInfo();
        GroupInfo groupInfo = new GroupInfo();
        try{
            for (String patternLabel : patterns.keySet()) {
                System.out.println(patternLabel + " number:" + patterns.get(patternLabel).size());
            }
            for (String patternLabel : patterns.keySet()) {
                
                switch(patternLabel){    
                    case "Adapter":   
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Adapter.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = AdapterPattern.class.getFields();
                            for (Field field : fields) {                 
                                Entry entry = new Entry();                 
                                switch(field.getName())
                                {
                                    case "class_Target":
                                        entry.setClassId(((AdapterPattern)pattern).class_Target.classLabel);
                                        entry.setRole( " " + Adapter.role_Target);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_Adapter":
                                        entry.setClassId(((AdapterPattern)pattern).class_Adapter.classLabel);
                                        entry.setRole( " " + Adapter.role_Adapter);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_Adaptee":
                                        entry.setClassId(((AdapterPattern)pattern).class_Adaptee.classLabel);
                                        entry.setRole( " " + Adapter.role_Adaptee);
                                        instance.addEntry(entry);
                                        break;
                                        
                                }
                            }             
                            groupInfo.addInstance(instance);         
                        }   
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;  //optional  
                    case "Composite": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Composite.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = CompositePattern.class.getFields();
                            for (Field field : fields) {                 
                                Entry entry = new Entry();                 
                                switch(field.getName())
                                {
                                    case "class_Componenet":
                                        if(((CompositePattern)pattern).class_Componenet != null){
                                            entry.setClassId(((CompositePattern)pattern).class_Componenet.classLabel);
                                            entry.setRole( " " + Composite.role_Component);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                    case "class_Composite":
                                        if(((CompositePattern)pattern).class_Composite != null){
                                            entry.setClassId(((CompositePattern)pattern).class_Composite.classLabel);
                                            entry.setRole( " " + Composite.role_Composite);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                    case "class_ConcreteComponenet":
                                        for(Vertex name : ((CompositePattern)pattern).class_ConcreteComponenet){
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Composite.role_Leaf);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                }
                                                                  
                               
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;  //optional  
                    case "AbstractFactory":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(AbstractFactory.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = AbstractFactoryPattern.class.getFields();
                            for (Field field : fields) {                  
                                Entry entry = new Entry();                 
                                
                                switch(field.getName())
                                {
                                    case "class_AbstractFactory":
                                        entry.setClassId(((AbstractFactoryPattern)pattern).class_AbstractFactory.classLabel);
                                        entry.setRole( " " + AbstractFactory.role_AbstractFactroy);
                                        instance.addEntry(entry);  
                                        break;
                                    case "class_ConcreteFactory":
                                        for(Vertex name : ((AbstractFactoryPattern)pattern).class_ConcreteFactory)
                                        {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + AbstractFactory.role_ConcreteFactory);
                                            instance.addEntry(entry);  
                                        }
                                        break;
                                        //bayad concrete product ham dashte bashe
                                    case "class_ConcreteProduct":
                                        for(Vertex name : ((AbstractFactoryPattern)pattern).class_ConcreteProduct)
                                        {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + AbstractFactory.role_concreteProduct);
                                            instance.addEntry(entry);  
                                        }
                                        break;
//                                    
                                    case "class_AbstarctProduct":
                                        entry.setClassId(((AbstractFactoryPattern)pattern).class_AbstarctProduct.classLabel);
                                        entry.setRole( " " + AbstractFactory.role_Product);
                                        instance.addEntry(entry);  
                                        break;
                                }
                                                                  
                                
                            }   
                            
                            groupInfo.addInstance(instance);         
                        }    
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Decorator": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Decorator.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = DecoratorPattern.class.getFields();
                            for (Field field : fields) {               
                                Entry entry = new Entry();                 
                                 
                                switch(field.getName())
                                {
                                    case "class_Componenet":
                                        entry.setClassId(((DecoratorPattern)pattern).class_Componenet.classLabel);
                                        entry.setRole( " " + Decorator.role_Component);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_ConcreteComponenet":
                                        for(Vertex name : ((DecoratorPattern)pattern).class_ConcreteComponenet)
                                        {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Decorator.role_ConcreteComponent);
                                            instance.addEntry(entry);  
                                        }
                                        break;
                                    case "class_Decorator":
                                        entry.setClassId(((DecoratorPattern)pattern).class_Decorator.classLabel);
                                        entry.setRole( " " + Decorator.role_Decorator);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_ConcreteDecorator":
                                        for(Vertex name : ((DecoratorPattern)pattern).class_ConcreteDecorator)
                                        {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Decorator.role_ConcreteDecorator);
                                            instance.addEntry(entry);  
                                        }
                                        break;    
                                }
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "FactoryMethod":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(FactoryMethod.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = FactoryMethodPattern.class.getFields();
                            for (Field field : fields) { 
                                Entry entry = new Entry();                 
                                switch(field.getName())
                                {
                                    case "class_Creator":
                                        entry.setClassId(((FactoryMethodPattern)pattern).class_Creator.classLabel);
                                        entry.setRole( " " + FactoryMethod.role_Creator);
                                        instance.addEntry(entry); 
                                        break;
                                    case "class_ConcreteCreator":
                                        for (Vertex name : ((FactoryMethodPattern)pattern).class_ConcreteCreator) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + FactoryMethod.role_ConcreteCreator);
                                            instance.addEntry(entry); 
                                        }
                                        break;
                                    case "class_ConcreteProduct":
                                        for (Vertex name : ((FactoryMethodPattern)pattern).class_ConcreteProduct) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + FactoryMethod.role_ConcreteCreator);
                                            instance.addEntry(entry); 
                                        }
                                        break;
                                        //role product nadarim
                                    case "class_Product":
                                        entry.setClassId(((FactoryMethodPattern)pattern).class_Product.classLabel);
                                        entry.setRole( " " + FactoryMethod.role_Product);
                                        instance.addEntry(entry); 
                                        break;
                                        
//                                    
                                }
                                                                  
                                 
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Observer":
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Observer.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = ObserverPattern.class.getFields();
                            for (Field field : fields) { 
                                Entry entry = new Entry();      
                                switch(field.getName())
                                {
                                    case "class_Observer":
                                        entry.setClassId(((ObserverPattern)pattern).class_Observer.classLabel);
                                        entry.setRole( " " + Observer.role_Observer);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_ConcreteObserver":
                                        for (Vertex name : ((ObserverPattern)pattern).class_ConcreteObserver) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Observer.role_ConcreteObserver);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                    case "class_ConcreteSubject":
                                        for (Vertex name : ((ObserverPattern)pattern).class_ConcreteSubject) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Observer.role_ConcreteSubject);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                    case "class_Subject":
                                        entry.setClassId(((ObserverPattern)pattern).class_Subject.classLabel);
                                        entry.setRole( " " + Observer.role_Subject);
                                        instance.addEntry(entry);
                                        break;
                                    
                                }
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Singleton": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Singleton.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = SingletonPattern.class.getFields();
                            for (Field field : fields) { 
                                Entry entry = new Entry();     
                                switch(field.getName())
                                {
                                    case "class_Singleton":
                                        entry.setClassId(((SingletonPattern)pattern).class_Singleton.classLabel);
                                        entry.setRole( " " + Singleton.role_Singleton);
                                        instance.addEntry(entry); 
                                        break;
                                    
                                }
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "State": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(State.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = StatePattern.class.getFields();
                            for (Field field : fields) { 
                                Entry entry = new Entry();
                                switch(field.getName())
                                {
                                    case "class_State":
                                        entry.setClassId(((StatePattern)pattern).class_State.classLabel);
                                        entry.setRole( " " + State.role_State);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_ConcreteState":
                                        for (Vertex name : ((StatePattern)pattern).class_ConcreteState) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + State.role_ConcreteState);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                        
                                    case "class_Context":
                                        entry.setClassId(((StatePattern)pattern).class_Context.classLabel);
                                        entry.setRole( " " + State.role_Context);
                                        instance.addEntry(entry);
                                        break;
                                }
                                                                  
                                  
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Strategy": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Strategy.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = StrategyPattern.class.getFields();
                            for (Field field : fields) { 
                                Entry entry = new Entry();
                                switch(field.getName())
                                {
                                    case "class_Strategy":
                                        entry.setClassId(((StrategyPattern)pattern).class_Strategy.classLabel);
                                        entry.setRole( " " + Strategy.role_Strategy);
                                        instance.addEntry(entry);
                                        break;
                                    case "class_ConcreteStrategy":
                                        for (Vertex name : ((StrategyPattern)pattern).class_ConcreteStrategy) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Strategy.role_ConcreteStrategy);
                                            instance.addEntry(entry);
                                        }
                                        break;
                                        
                                    case "class_Context":
                                        entry.setClassId(((StrategyPattern)pattern).class_Context.classLabel);
                                        entry.setRole( " " + State.role_Context);
                                        instance.addEntry(entry);
                                        break;
                                }
                                                                  
                                
                            } 
                            groupInfo.addInstance(instance);  
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "TemplateMethod":
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(TemplateMethod.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = TemplatePattern.class.getFields();
                            for (Field field : fields) {            
                                Entry entry = new Entry(); 
                                switch(field.getName())
                                {
                                    case "class_AbstractClass":
                                        entry.setClassId(((TemplatePattern)pattern).class_AbstractClass.classLabel);
                                        entry.setRole( " " + TemplateMethod.role_AbstractClass);
                                        instance.addEntry(entry); 
                                        break;
                                    case "class_ConcreteClass":
                                        for (Vertex name : ((TemplatePattern)pattern).class_ConcreteClass) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + TemplateMethod.role_ConcreteClass);
                                            instance.addEntry(entry); 
                                        }
                                        break;
                                    
                                    
                                }                    
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Visitor":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Visitor.patternName);
                        for (Object pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            Field[] fields = VisitorPattern.class.getFields();
                            for (Field field : fields) {                 
                                Entry entry = new Entry();     
                                switch(field.getName())
                                {
                                    case "class_ObjectStructure":
                                        entry.setClassId(((VisitorPattern)pattern).class_ObjectStructure.classLabel);
                                        entry.setRole( " " + Visitor.role_ObjectStructure);
                                        instance.addEntry(entry); 
                                        break;
                                    case "class_Element":
                                        entry.setClassId(((VisitorPattern)pattern).class_Element.classLabel);
                                        entry.setRole( " " + Visitor.role_Element);
                                        instance.addEntry(entry); 
                                        break;
                                    case "class_ConcreteElement":
                                        for (Vertex name : ((VisitorPattern)pattern).class_ConcreteElement) {
                                            entry = new Entry();
                                            entry.setClassId(name.classLabel);
                                            entry.setRole( " " + Visitor.role_ConcreteElement);
                                            instance.addEntry(entry); 
                                        }
                                        break;
                                    case "class_Visitor":
                                        entry.setClassId(((VisitorPattern)pattern).class_Visitor.classLabel);
                                        entry.setRole( " " + Visitor.role_Visitor);
                                        instance.addEntry(entry); 
                                        break;
                                }
                                     
                            }             
                            groupInfo.addInstance(instance);         
                        } 
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    default:     
                    break;
                }    
            }
            ResultViewer resultViewer = new ResultViewer(patternsGroupInfo, new JsonWriter());
            //resultViewer.viewResults();
            String results = resultViewer.getResults();
            try{
                PrintWriter writer = new PrintWriter(directory, "UTF-8");
                writer.println(results);
                writer.close();
            } catch (IOException e) {
               // do something
            }
        }
        catch(Exception ex){
            System.out.println("fu");
        }
    }
  
    public static void writeGsonFile(String resultGsonDirectory,HashMap<String,List<Graph>> patterns) {
        PatternsGroupInfo patternsGroupInfo = new PatternsGroupInfo();
        GroupInfo groupInfo = new GroupInfo();
        try{
            for (String patternLabel : patterns.keySet()) {
                System.out.println(patternLabel + " number:" + patterns.get(patternLabel).size());
            }
            for (String patternLabel : patterns.keySet()) {
                
                switch(patternLabel){    
                    case "Adapter":   
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Adapter.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "Target":
                                        entry.setRole( " " + Adapter.role_Target);
                                        break;
                                    case "Adapter":
                                        entry.setRole( " " + Adapter.role_Adapter);
                                        break;
                                    case "Adaptee":
                                        entry.setRole( " " + Adapter.role_Adaptee);
                                        break;
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }   
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;  //optional  
                    case "Composite": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Composite.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "Comp":
                                        entry.setRole( " " + Composite.role_Component);
                                        break;
                                    case "Composite":
                                        entry.setRole( " " + Composite.role_Composite);
                                        break;
                                    case "ConComp":
                                        entry.setRole( " " + Composite.role_Leaf);
                                        break;
                                    //in ham composite1 to code man hast
//                                    case "Composite1":
//                                        entry.setRole( " " + Composite.role_Leaf);
//                                        break;                                        
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;  //optional  
                    case "AbstractFactory":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(AbstractFactory.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(roleLabel );  
                                switch(roleLabel)
                                {
                                    case "AbstractFactory":
                                        entry.setRole( " " + AbstractFactory.role_AbstractFactroy);
                                        break;
                                    case "ConFactory":
                                        entry.setRole( " " + AbstractFactory.role_ConcreteFactory);
                                        break;
                                        //bayad concrete product ham dashte bashe
//                                    case "ConProduct":
//                                        entry.setRole(pattern.roleVertexHMap.get(pattern.roleVertexHMap.get(roleLabel))+ " " + AbstractFactory.role_);
//                                        break;
//                                    
                                    case "AbstractProduct":
                                        entry.setRole( " " + AbstractFactory.role_Product);
                                        break;
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }    
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Decorator": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Decorator.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "Comp":
                                        entry.setRole( " " + Decorator.role_Component);
                                        break;
                                    case "ConComp":
                                        entry.setRole( " " + Decorator.role_ConcreteComponent);
                                        break;
                                    case "Decorator":
                                        entry.setRole( " " + Decorator.role_Decorator);
                                        break;
                                    case "ConDecorator":
                                        entry.setRole( " " + Decorator.role_ConcreteDecorator);
                                        break;    
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "FactoryMethod":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(FactoryMethod.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "Creator":
                                        entry.setRole( " " + FactoryMethod.role_Creator);
                                        break;
                                    case "ConCreator":
                                        entry.setRole( " " + FactoryMethod.role_ConcreteCreator);
                                        break;
                                    case "ConProduct":
                                        entry.setRole( " " + FactoryMethod.role_ConcreteProduct);
                                        break;
                                        //role product nadarim
//                                    case "Product":
//                                        entry.setRole( " " + FactoryMethod.role_ConcreteProduct);
                                        //ino ham nadare
//                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Observer":
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Observer.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId("class" + j );  
                                switch(roleLabel)
                                {
                                    case "Observer":
                                        entry.setRole( " " + Observer.role_Observer);
                                        break;
                                    case "ConObserver":
                                        entry.setRole( " " + Observer.role_ConcreteObserver);
                                        break;
                                    case "ConSubject":
                                        entry.setRole( " " + Observer.role_ConcreteSubject);
                                        break;
                                    case "Subject":
                                        entry.setRole( " " + Observer.role_Subject);
                                        break;
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Singleton": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Singleton.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId("class" + j );  
                                switch(roleLabel)
                                {
                                    case "Singleton":
                                        entry.setRole( " " + Singleton.role_Singleton);
                                        break;
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "State": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(State.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "State":
                                        entry.setRole( " " + State.role_State);
                                        break;
                                    case "ConState":
                                        entry.setRole( " " + State.role_State);
                                        break;
                                    case "Context":
                                        entry.setRole( " " + State.role_Context);
                                        break;
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Strategy": 
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Strategy.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "Strategy":
                                        entry.setRole( " " + Strategy.role_Strategy);
                                        break;
                                    case "ConStrategy":
                                        entry.setRole( " " + Strategy.role_ConcreteStrategy);
                                        break;
                                    case "Context":
                                        entry.setRole( " " + Strategy.role_Context);
                                        break;
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }  
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "TemplateMethod":
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(TemplateMethod.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId(pattern.roleVertexHMap.get(roleLabel) );  
                                switch(roleLabel)
                                {
                                    case "AbstractClass":
                                        entry.setRole( " " + TemplateMethod.role_AbstractClass);
                                        break;
                                    case "ConClassA":
                                        entry.setRole( " " + TemplateMethod.role_ConcreteClass);
                                        break;
                                    case "ConClassB":
                                        entry.setRole( " " + TemplateMethod.role_ConcreteClass);
                                        break;
                                    
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Visitor":  
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Visitor.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {                 
                                Entry entry = new Entry();                 
                                entry.setClassId("class" + j );  
                                switch(roleLabel)
                                {
                                    case "ObjectStructure":
                                        entry.setRole( " " + Visitor.role_ObjectStructure);
                                        break;
                                    case "Element":
                                        entry.setRole( " " + Visitor.role_Element);
                                        break;
                                    case "ConElement":
                                        entry.setRole( " " + Visitor.role_ConcreteElement);
                                        break;
                                    case "Visitor":
                                        entry.setRole( " " + Visitor.role_Visitor);
                                        break;
                                }
                                                                  
                                instance.addEntry(entry);  
                                j++;
                            }             
                            groupInfo.addInstance(instance);         
                        } 
                        patternsGroupInfo.addGroupInfo(groupInfo); 
                        break;
                    case "Builder":
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupName(Builder.patternName);
                        for (Graph pattern :  patterns.get(patternLabel)) {
                            InstanceInfo instance = new InstanceInfo();
                            int j = 0;
                            for (String roleLabel : pattern.roleVertexHMap.keySet()) {
                                Entry entry = new Entry();
                                entry.setClassId("class" + j );
                                switch(roleLabel)
                                {
                                    case "Builder":
                                        entry.setRole( " " + Builder.role_Builder);
                                        break;
                                    case "Product":
                                        entry.setRole( " " + Builder.role_Product);
                                        break;
                                    case "Director":
                                        entry.setRole( " " + Builder.role_Director);
                                        break;
                                }

                                instance.addEntry(entry);
                                j++;
                            }
                            groupInfo.addInstance(instance);
                        }
                        patternsGroupInfo.addGroupInfo(groupInfo);
                        break;
                    default:     
                    break;
                }    
            }
            ResultViewer resultViewer = new ResultViewer(patternsGroupInfo, new JsonWriter());
            //resultViewer.viewResults();
            String results = resultViewer.getResults();
            try{
                PrintWriter writer = new PrintWriter(resultGsonDirectory, "UTF-8");
                writer.println(results);
                writer.close();
            } catch (IOException e) {
               // do something
            }
        }
        catch(Exception ex){
            
        }
    }
}
