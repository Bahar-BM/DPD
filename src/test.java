//import com.sun.org.apache.bcel.internal.generic.NEW;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;
import java.util.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.json.Json;
public class test {
    public static void main(String[] args) throws Exception {
    	String[] args2 = new String[7];
    	args2[0]= "C:\\Users\\Parastoo\\Documents\\course\\karshenasi\\proje karshenasi\\change input\\Target Codes (1)\\Target Codes\\300 Class\\GeneratedCode\\src";
    	args2[1]="C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\xml";
    	args2[2]="C:\\Users\\Parastoo\\Documents\\BSC3\\BSC\\BehavioralResults\\Behavioralresults.txt";
    	args2[3]="localhost";
    	args2[4]="3306";
    	args2[5]="root";
    	args2[6]="137428sAm";


    	MysqlCon con=MysqlCon.getInstance(args2);
		con.truncate();
    	//2) read file in a directory & insert in DB
		      //read jdk8 library and insert in DB:
    	   //##generalFunction.readAndSaveInDB("C:/Users/abas/Desktop/DPD source/source code of jdk8/");
    	   //## generalFunction.saveLastIdTable();
		     //read local source and insert in DB:

		//generalFunction.readAndSaveInDB("C:\\Users\\Parastoo\\Documents\\course\\karshenasi\\proje karshenasi\\change input\\Target Codes (1)\\Target Codes\\300 Class\\GeneratedCode\\src");
		generalFunction.readAndSaveInDB(args2[0]);
		/*final File folder = new File("C:/Users/abas/Desktop/test DPD/");
    	ArrayList<String> list_path=new ArrayList<String>();
    	list_path=generalFunction.listFilesForFolder(folder);
    	Iterator<String> itr= list_path.iterator();
    	while(itr.hasNext())
    	{ 
    		String file_path=itr.next(); System.out.println(file_path);
    		//3) read any file content for walk
    		String Directory=generalFunction.readFile(file_path);
    		ANTLRInputStream input = new ANTLRInputStream(Directory);
        	javaLexer lexer = new javaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            javaParser parser = new javaParser(tokens);
            javaParser.CompilationUnitContext tree=parser.compilationUnit();
            javaTestListener Extractor = new javaTestListener();
            ParseTreeWalker.DEFAULT.walk(Extractor,tree);
    	}
    	*/
    	//3) insert Transitive Relationship
    	
    	con.insertTransitiveRelationship();
    	
    	/*
    	//4) search pattern
    	//###############################search singleton
    	patternQuery singleton_query=new patternQuery();
    	singleton_query.Singleton();
    	//################################search composite
    	patternQuery composite_query=new patternQuery();
    	composite_query.Composite();
    	*/
    	//patternQuery composite_query=new patternQuery();
    	//composite_query.Composite2();
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

    	
    	

    	//#################################for test
    	//String Directory = "public static abstract class a extends c implements d,e{ Vector al=new Vector(); int[] list3 = {1,2,3,4,5,6,8,9}; int[] list4 = new int[10]; Vector al2=new Vector(3); int[] list ; List<graphic> mChildGraphics = new ArrayList<graphic>(); public a(int g) { f=g; PCLASS2 cl=new CLASS2(); return cl; fun4(10); } public int f; public int bb=10; private static int fun3(){  super(); int vname=111;   return 1;}public void show(int r1, int r2, int r3){int s; for(int i=0;i<10;i++) {r1=r1*2; } while(r3 > 10 ){ r3--; } switch(r1){ case 1: s=1; break; case 2: s=2; break; default: s=3;} if(r1=r2){ r1=r3;} else r1=r2; int m; f=5;A num;B b=new B(s);b.fun2();}}";
    	//String Directory = "public static interface Sports {private void setHomeTeam(String name);public void setVisitingTeam(String name);}public interface Football extends Sports {public void homeTeamScored(int points);private void visitingTeamScored(int points);public void endOfQuarter(int quarter);}";
    	//String Directory = "class a{}";
     /*   ANTLRInputStream input = new ANTLRInputStream(Directory);
    	javaLexer lexer = new javaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        javaParser parser = new javaParser(tokens);
        javaParser.CompilationUnitContext tree=parser.compilationUnit();
        javaTestListener Extractor = new javaTestListener();
        ParseTreeWalker.DEFAULT.walk(Extractor,tree);*/
    	//##################################

		bsc.BSC.main(args2);
    }
}