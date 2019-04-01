import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;
import org.omg.DynamicAny._DynStructStub;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.json.Json;

public class generalFunction {
	static String packageName;
	static public ArrayList<String> listFilesForFolder(final File folder) {
	ArrayList<String> file_path= new ArrayList<String>();
		try {
		for (final File fileEntry : folder.listFiles()) {
			
			
	        if (fileEntry.isDirectory()) {
	        	 packageName= fileEntry.getName(); 
	        	file_path.addAll(listFilesForFolder(fileEntry));
	        } else {
	        	String name=fileEntry.getName().toLowerCase();
	        	int dot = name.lastIndexOf('.');
	        	String extension = (dot == -1) ? "" : name.substring(dot+1);
	        	if(extension.equals("java")  ) 
	        		{
	        		String Query1="SELECT count(*) as cnt FROM list_class where class_name='"+name.substring(0,dot).toLowerCase()+"' and package_name='"+packageName+"'";
	        		System.out.println(Query1);
	        		String args[]= new String[7];
	        		MysqlCon con=MysqlCon.getInstance(args);
	        		ResultSet result=con.selectQuery(Query1);
					if(result.next())
					{
						if(Integer.parseInt(result.getString("cnt")) == 0 )
							file_path.add(fileEntry.getAbsolutePath());
					}
	        		
	        		
	        		}
	        	
	        }
	    }
		 }catch(Exception e){ System.out.println(e);}
		
		return file_path;
	}
	static public String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}	
	static public void readAndSaveInDB(String Dir)
	{
		try
		{
		final File folder = new File(Dir);
    	ArrayList<String> list_path=new ArrayList<String>();
    	list_path=generalFunction.listFilesForFolder(folder);
    	Iterator<String> itr= list_path.iterator();
    	while(itr.hasNext())
    	{ 
    		String file_path=itr.next(); 
    		
    		//3) read any file content for walk
    		String Directory=readFile(file_path);
    		////////////////////////////
    		
    		 File f = new File(file_path);
    		 generalFunction.packageName= f.getParentFile().getName(); 
    		 
    		//////////////////////////////////
    		    ANTLRInputStream input = new ANTLRInputStream(Directory);
        	javaLexer lexer = new javaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            javaParser parser = new javaParser(tokens);
            javaParser.CompilationUnitContext tree=parser.compilationUnit();
            javaTestListener Extractor = new javaTestListener();
            ParseTreeWalker.DEFAULT.walk(Extractor,tree);
    	}
	 }catch(Exception e){ System.out.println(e);}
	}
	static public void saveLastIdTable()
	{
		String[] args = new String[7];
		MysqlCon con=MysqlCon.getInstance(args);
		String Query="update last_id_jdk8 set max_class_id=(select max(class_id) as mid from list_class)";
		con.insertQuery(Query);
		Query="update last_id_jdk8 set max_method_id=(select max(method_id) as mid from list_method)";
		con.insertQuery(Query);
		Query="update last_id_jdk8 set max_field_id=(select max(field_id) as mid from list_field)";
		con.insertQuery(Query);
		Query="update last_id_jdk8 set max_expression_id=(select max(expression_id) as mid from method_expression)";
		con.insertQuery(Query);
	}

	static public ArrayList[] convertResultToString(ResultSet rs)
	{
		ArrayList[] list_of_string=new ArrayList[0];
		try{ 
			int number_of_row=0;
			if(rs.last()){
				number_of_row = rs.getRow(); 
				   rs.beforeFirst();
				}
			list_of_string=new ArrayList[number_of_row]; 
			ResultSetMetaData rsmd = rs.getMetaData();
	         int number_of_column = rsmd.getColumnCount();
	         int j=-1;
	         if(number_of_row > 0)
			     {
	        	  while(rs.next())
					{   
						j++;
						list_of_string[j]=new ArrayList<String>();
	        		  for(int i=1;i<=number_of_column;i++)
						{
	        			  list_of_string[j].add(rs.getString(i));
	        			}
			     }
			     }
	         }catch(Exception e){ System.out.println(e);}
	 		return list_of_string;		
	} 	
			
	//ArrayList<String> list_of_string=new ArrayList<String>();
		//ArrayList<Object> final_result=new ArrayList();
		//try{ 
		//	ResultSetMetaData rsmd = rs.getMetaData();
		//	 System.out.println("ResultSetMetaData");
	     //   int numOfCols = rsmd.getColumnCount();
	        
	     //   System.out.println("numOfCols"+numOfCols);
		//	if(!rs.equals("") && rs!=null)
		//	{
		//		list_of_string.removeAll(list_of_string);
		//		while(rs.next())
		//		{   
		//			for(int i=1;i<=numOfCols;i++)
		//			{
		//				System.out.println("22222:"+i+"="+rsmd.getColumnName(i));
		//				list_of_string.add(rs.getString(rsmd.getColumnName(i)));
		//				System.out.println("333333");
		//			}
		//			final_result.add(list_of_string) ;   
		//		} 
		//	}
	//	}catch(Exception e){ System.out.println(e);}
	//	return final_result;				  
	//}
	static public void printStringArray(ArrayList<String> res)
	{
		for(int i=0;i<res.size();i++)
			System.out.println(res.get(i));
	}
}
