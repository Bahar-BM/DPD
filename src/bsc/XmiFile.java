
package bsc;

import cdg.model.CdAssociation;
import cdg.model.CdAttribute;
import cdg.model.CdClass;
import cdg.model.CdDependency;
import cdg.model.CdGeneralization;
import cdg.model.CdOperation;
import cdg.model.CdParameter;
import cdg.model.ClassDiagram;
import diagramParser.Json2Object;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Node;

public class XmiFile {
    
    
    public ClassDiagram  classDiagram ;
    public List<CdClass> classNodes  = new ArrayList<>();
    public List<Node> interfaceNodes = new ArrayList<>(); 
    public List<CdAssociation> AssociationNodes = new ArrayList<>();
    public List<CdGeneralization> InheritanceNodes = new ArrayList<>(); 
    public List<CdDependency> DependencyNodes = new ArrayList<>(); 
    

    public ClassDiagram readFile(String directory) throws DocumentException{
        
        Json2Object jo = new Json2Object();
        classDiagram = jo.convert2Object(directory);
        return classDiagram;
        
        
        
    }
    public void findClassInterface(ClassDiagram classDiagram) throws DocumentException{
        classNodes.addAll( classDiagram.getUmlClasses());
    }
    public int countClass()throws DocumentException{
        
        List<CdClass> totalClass = new ArrayList<>();
        totalClass.addAll(classNodes);

        return totalClass.size();
    }
    
    public List<Vertex> makeVertexes()throws DocumentException{
        
        List<Vertex> vertexes = new ArrayList<>();
        Vertex v = null; 
        for(CdClass item : classNodes){
            v = makeVertex(item  );
            vertexes.add(v);
        }
        return vertexes;
    }

    private Vertex makeVertex(CdClass cdclass  )throws DocumentException {
        
        
        Vertex result = new Vertex();
        result.classLabel = cdclass.getName();
        result.packageName = "targetCode";
        Method m = null; 
        List<CdOperation> operations = cdclass.getOperations();
        List<CdAttribute> classAttribute = cdclass.getAttributes();
        for(CdAttribute ct : classAttribute)
            result.classVariables.add(ct.getName());
        
        for(CdOperation op :operations)
            result.methods.add(makeMethod(op));
        
        return result;    
    }

    private Method makeMethod(CdOperation operation)throws DocumentException {
        Method result = new Method();
        result.methodLabel = operation.getName();
        List<CdParameter> inputType = operation.getParameters();
        for(CdParameter n : inputType)
            result.inputType.add(n.getType().getTypeName()); 
        result.outType = operation.getReturnType().getTypeName();
        return result;
    }
    
    public void findRelationship(ClassDiagram classDiagram) throws DocumentException{
        AssociationNodes.addAll(classDiagram.getAssociationRelations());
        InheritanceNodes.addAll(classDiagram.getGeneralizationRelations());
        DependencyNodes .addAll(classDiagram.getDependencyRelations());
        
    }
    
    public List<Edge> makeEdges() {
        List<Edge> edges = new ArrayList<>();
        Edge e ; 
        for(CdAssociation item : AssociationNodes){
            e = makeEdge(item ,"association");
            if(e.canAdd(edges))
            {   edges.add(e);
                System.out.println(e.source +":"+e.target +":Association");
            }
        }
        for(CdGeneralization item : InheritanceNodes){
            e = makeEdge(item ,"inheritance");
            if(e.canAdd(edges))
            {   edges.add(e);
                System.out.println(e.source +":"+e.target +":Inheritance");
            }
            
        }
        for(CdDependency item : DependencyNodes){
            e = makeEdge(item ,"dependency");
            if(e.canAdd(edges))
            {   edges.add(e);
                System.out.println(e.source +":"+e.target +":dependency");
            }
            
        }
        
        return edges;
    }
    
    public List<Edge> makeEdges(Vertex vertex) {
        List<Edge> edges = new ArrayList<>();
        Edge e ; 
        for (String type : vertex.classVariables) {
            if(type.equals(vertex.classLabel)){
                e = new Edge(vertex.classLabel,vertex.classLabel ,"association");
                if(e.canAdd(edges))
                {   
                    edges.add(e);
                }
            }
        }
        
        return edges;
    }

    private Edge makeEdge(CdAssociation association, String relation) {
        
        String source = association.getLeftClass();
        String target = association.getRightClass();
        Edge result = new Edge(source, target, relation); 
        
        return result;  
        
    }
    private Edge makeEdge(CdDependency dependency, String relation) {
        
        String source = dependency.getClientClass();
        String target = dependency.getSupplierClass();
        Edge result = new Edge(source, target, relation); 
        
        return result;  
        
    }
    private Edge makeEdge(CdGeneralization generalization, String relation) {
        
        String source = generalization.getSubClass();
        String target = generalization.getSuperClass();
        Edge result = new Edge(source, target, relation); 
        
        return result;  
        
    }

    

    
//    public Document readFile(String directory) throws DocumentException{
//        
//        input = new File(directory);
//        reader = new SAXReader();
//        return reader.read(input);
//        
//        
//    }
//    public void findClassInterface(Document document) throws DocumentException{
//        classNodes.addAll( document.selectNodes("//UML:Package/UML:Namespace.ownedElement/UML:Class"));
//        interfaceNodes.addAll( document.selectNodes("//UML:Package/UML:Namespace.ownedElement/UML:Interface"));
//    }
//    public int countClass()throws DocumentException{
//        
//        List<Node> totalClass = new ArrayList<>();
//        totalClass.addAll(classNodes);
//        totalClass.addAll(interfaceNodes);
//
//        return totalClass.size();
//    }
//    
//    public List<Vertex> makeVertexes()throws DocumentException{
//        
//        List<Vertex> vertexes = new ArrayList<>();
//        Vertex v = null; 
//        for(Node item : classNodes){
//            v = makeVertex(item ,"Class" );
//            vertexes.add(v);
//        }
//        for(Node item : interfaceNodes){
//            v = makeVertex(item ,"Interface");
//            vertexes.add(v);
//        }
//        return vertexes;
//    }
//
//    private Vertex makeVertex(Node node ,String classType )throws DocumentException {
//        
//        
//        Vertex result = new Vertex();
//        result.classLabel = node.valueOf("@name");
//        
//        result.xmiId = node.valueOf("@xmi.id");
//        
//        result.packageName = node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='package_name']").valueOf("@value");
//        result.classId = node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='ea_localid']").valueOf("@value");
//        
//        if(result.classId.equals("77")){
//            System.out.println("this");
//        }
//        Method m = null; 
//        List<Node> operations = node.selectNodes("UML:Classifier.feature/UML:Operation");
//        List<Node> classVariables = node.selectNodes("UML:Classifier.feature/UML:Attribute");
//        for(Node cv : classVariables){
//            if(findClassVarType(cv) != null)
//                result.classVariables.add(findClassVarType(cv));
//        }
//        for(Node op :operations){
//            result.methods.add(makeMethod(op));
//        }
//        return result;    
//    }
//
//    private Method makeMethod(Node node)throws DocumentException {
//        Method result = new Method();
//        result.methodLabel = node.valueOf("@name");
//        if(node.selectSingleNode("UML:BehavioralFeature.parameter/UML:Parameter/UML:ModelElement.taggedValue/UML:TaggedValue[@tag='type']")!=null)
//        {    List<Node> inoutput = node.selectNodes("UML:BehavioralFeature.parameter/UML:Parameter/UML:Parameter.type/UML:Classifier");
//            for(Node n : inoutput)
//                result.inOutType.add(n.valueOf("@xmi.idref"));  
//        
//        }
//        return result;
//    }
//    
//    public void findRelationship(Document document) throws DocumentException{
//        AssociationNodes .addAll(document.selectNodes("//UML:Package/UML:Namespace.ownedElement/UML:Association"));
//        InheritanceNodes = new ArrayList<>();
//        List<Node> generalization = document.selectNodes("//UML:Package/UML:Namespace.ownedElement/UML:Generalization");
//        List<Node> inheritance = document.selectNodes("//UML:Package/UML:Namespace.ownedElement/UML:Dependency");
//        if(!inheritance.isEmpty())
//            InheritanceNodes.addAll(inheritance);
//        if(!generalization.isEmpty())
//            InheritanceNodes.addAll(generalization);  
//    }
//    
//    public List<Edge> makeEdges() {
//        List<Edge> edges = new ArrayList<>();
//        Edge e ; 
//        for(Node item : AssociationNodes){
//            e = makeEdge(item ,"association");
//            if(e.canAdd(edges))
//            {   edges.add(e);
//                System.out.println(e.source +":"+e.target +":Association");
//            }
//        }
//        for(Node item : InheritanceNodes){
//            e = makeEdge(item ,"inheritance");
//            if(e.canAdd(edges))
//            {   edges.add(e);
//                System.out.println(e.source +":"+e.target +":Inheritance");
//            }
//            
//        }
//        
//        return edges;
//    }
//    
//    public List<Edge> makeEdges(Vertex vertex) {
//        List<Edge> edges = new ArrayList<>();
//        Edge e ; 
//        for (String type : vertex.classVariables) {
//            if(type.equals(vertex.classLabel)){
//                e = new Edge(vertex.classId,vertex.classId ,"association");
//                if(e.canAdd(edges))
//                {   
//                    edges.add(e);
//                }
//            }
//            
//        }
//        
//        return edges;
//    }
//
//    private Edge makeEdge(Node node, String relation) {
//        
//        String source = node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='ea_sourceID']").valueOf("@value");
//        String target = node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='ea_targetID']").valueOf("@value");
//        Edge result = new Edge(source, target, relation); 
//        
//        return result;  
//        
//    }
//
//    private String findClassVarType(Node node) {
//       
//        if( node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='type']") != null)
//        {
//            Node classVar = node.selectSingleNode("UML:ModelElement.taggedValue/UML:TaggedValue[@tag='type']");
//            String type = classVar.valueOf("@value");
//            try{
//                if(type.substring(type.indexOf("<") + 1, type.indexOf(">")) !=  null){
//                    type = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
//                }
//                else if(type.substring(type.indexOf("[") + 1, type.indexOf("]")) !=  null){
//                    type = type.substring(type.indexOf("[") + 1, type.indexOf("]"));
//                }
//            }
//            catch(Exception ex){
//                
//            }
//            return type;
//        }
//        
//        return "" ;
//                
//        
//    }

    
    
    
}
