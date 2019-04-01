import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
  
class MysqlCon{ 
	 private static MysqlCon ms;
	 public static Connection  msc;

	 private MysqlCon(){}
	 public static synchronized MysqlCon getInstance(String[] args)
	 {
		 try{  
			 Class.forName("com.mysql.jdbc.Driver");  
			  if (ms == null)
			  {
				  ms=new MysqlCon();
//				  msc= DriverManager.getConnection("jdbc:mysql://localhost:3306/codestore","root","137428sAm");
				  msc= DriverManager.getConnection("jdbc:mysql://"+args[3]+":"+args[4]+ "/codestore",args[5],args[6]);
				  //dpd_db_test
			  }
			  }catch(Exception e){ System.out.println(e);}
		return ms;  
	 }
	static public ResultSet selectQuery(String query)
	 {
		ResultSet rs=null;
		try{  
			 Class.forName("com.mysql.jdbc.Driver");
		     Statement stmt=msc.createStatement(); 
		     rs=stmt.executeQuery(query); 
		      //////////////result=generalFunction.convertResultToString(rs);
		     ///////generalFunction.printStringArray(result);
		 }catch(Exception e){ System.out.println(e);}
	return rs;
	}
	static public boolean insertQuery(String query)
	 {
		 boolean rs=true;
		 try{  
			 Class.forName("com.mysql.jdbc.Driver");
		     Statement stmt=msc.createStatement() ;
		     rs=stmt.execute(query); 
		    
		 }catch(Exception e){ System.out.println(e);}
		return rs;
	 
	 }
	static public boolean truncate()
	{ //empty DB
		 boolean rs=true;
		 try{  
			 Class.forName("com.mysql.jdbc.Driver");
		     Statement stmt=msc.createStatement() ;
		    /**/
		     String max_class_id="0";
		     String max_method_id="0";
		     String max_field_id="0";
		     String max_expression_id="0";
		     String Query1="SELECT * FROM last_id_jdk8 ";
			 ResultSet result=selectQuery(Query1);
	         if(result.next())
				{ 
	        	  max_class_id=result.getString("max_class_id");
			      max_method_id=result.getString("max_method_id");;
			      max_field_id=result.getString("max_field_id");;
			      max_expression_id=result.getString("max_expression_id");;
				}
		     rs=stmt.execute("delete from list_class where class_id > "+max_class_id);
		     rs=stmt.execute("delete from list_method where method_id > "+max_method_id); 
		     rs=stmt.execute("delete from list_field where field_id > "+max_field_id); 
		     rs=stmt.execute("delete from method_expression where expression_id > "+max_expression_id); 
		    
		 }catch(Exception e){ System.out.println(e);}
		return rs;
	}
	static public String getParent(String class_name)
	{
		try{  
			 String class_parent="";	
		     String Query="select class_parents from list_class where  class_name='"+class_name+"'";
		     ResultSet result=selectQuery(Query);
		     if(result.next()) 
		     {
		    	 class_parent=result.getString("class_parents"); 
		    	 if(!class_parent.equals("") && class_parent!=null )
				 { 
					String result_parent= getParent(class_parent); 
					return class_parent=class_parent+(result_parent.equals("") ? "" : ","+result_parent); 
				 }
		     }
		}catch(Exception e){ System.out.println("error 2:"); System.out.println(e);}
		 return "";	 
	}
	static public String getParentInterface(String class_name)
	{
		try{  
			class_name=class_name.replace(",", "','"); 
			class_name="'"+class_name+"'";
			String class_parents_interface="";	
		     String Query="select class_parents_interface,class_parents from list_class where (class_parents_interface!='' or class_parents!='' ) and class_name in ("+class_name+")";
		     ResultSet result=selectQuery(Query);
		     String all_class_parents="";
		     while(result.next()) 
		     {
		    	 class_parents_interface=result.getString("class_parents_interface"); 
		    	 String class_parents=result.getString("class_parents"); 
			    	
		    	 if((!class_parents_interface.equals("") && class_parents_interface!=null) || (!class_parents.equals("") && class_parents!=null) )
				 {  
					 if((!class_parents_interface.equals("") && class_parents_interface!=null) && (!class_parents.equals("") && class_parents!=null) ) class_parents=class_parents_interface+","+class_parents;
					 else if((!class_parents_interface.equals("") && class_parents_interface!=null) ) class_parents=class_parents_interface;
					 if(all_class_parents.equals("")) all_class_parents+=","+class_parents; else all_class_parents+=class_parents;
					
				 }
		     }
		     if(!all_class_parents.equals(""))
		     {
		    	 String result_parent= getParentInterface(all_class_parents); 
					return class_parents_interface=class_parents_interface+(result_parent.equals("") ? "" : ","+result_parent); 
			 } 
		}catch(Exception e){ System.out.println("error 2:"); System.out.println(e);}
		 return "";	 
	}
	static public void insertTransitiveRelationship()
	{
		 
		try{
			//#######################################insert nested class
			String Query="select class_id,class_type,class_parents,class_parents_interface from list_class where class_type>1";
			ResultSet result=selectQuery(Query);
			while(result.next())
				 {
				   String nested_class_id=result.getString("class_id");
				   int nested_base_class_id=Integer.parseInt(result.getString("class_type"))-10;
				    String nested_class_parent=result.getString("class_parents");
				   String nested_class_interface=result.getString("class_parents_interface");
				   //select parent of nested class & insert to its base class
				    Query="update list_class set class_parents=concat(class_parents,'"+(!nested_class_parent.equals("") & nested_class_parent!=null ? ","+nested_class_parent : "" )+"'),class_parents_interface=concat(class_parents_interface,'"+(!nested_class_interface.equals("") & nested_class_interface!=null ? ","+nested_class_interface : "" )+"') where class_id="+nested_base_class_id;
					insertQuery(Query);
					
				   //select method of nested class & insert to its base class
					Query="insert into place_for_copy_nested_list_method select * from list_method where method_class_id="+nested_class_id;
					insertQuery(Query);
					Query="update place_for_copy_nested_list_method set method_class_id="+nested_base_class_id;
					insertQuery(Query);
					Query="insert into list_method ( `method_class_id`, `method_name`, `method_visibility`, `method_is_abstract`, `method_is_static`, `method_is_final`, `method_output_type`, `method_type`) select  `method_class_id`, `method_name`, `method_visibility`, `method_is_abstract`, `method_is_static`, `method_is_final`, `method_output_type`, `method_type` from place_for_copy_nested_list_method";
					insertQuery(Query);
					Query="TRUNCATE table place_for_copy_nested_list_method ";
					insertQuery(Query);
					
				   //select field of nested class & insert to its base class
					Query="insert into place_for_copy_nested_list_field select * from list_field where field_class_id="+nested_class_id;
					insertQuery(Query);
					Query="update place_for_copy_nested_list_field set field_class_id="+nested_base_class_id;
					insertQuery(Query);
					Query="insert into list_field (`field_class_id`, `field_method_id`, `field_visibility`, `field_is_static`, `field_is_final`, `field_name`, `field_type`, `field_value`, `field_group`) select `field_class_id`, `field_method_id`, `field_visibility`, `field_is_static`, `field_is_final`, `field_name`, `field_type`, `field_value`, `field_group` from place_for_copy_nested_list_field";
					insertQuery(Query);
					Query="TRUNCATE table place_for_copy_nested_list_field ";
					insertQuery(Query);
				   
					//select expression of nested class & insert to its base class 
					Query="insert into place_for_copy_nested_method_expression select * from method_expression where expression_class_id="+nested_class_id;
					insertQuery(Query);
					Query="update place_for_copy_nested_method_expression set expression_class_id="+nested_base_class_id;
					insertQuery(Query);
					Query="insert into method_expression ( `expression_class_id`, `expression_method_id`, `expression_type`, `expression_first`, `expression_second`) select  `expression_class_id`, `expression_method_id`, `expression_type`, `expression_first`, `expression_second` from place_for_copy_nested_method_expression";
					insertQuery(Query);
					Query="TRUNCATE table place_for_copy_nested_method_expression ";
					insertQuery(Query);
				 }
			
			 System.out.println("insert nested class");
            
			//#######################################insert nested method
				 Query="select method_id,method_type from list_method where method_type>1";
				 result=selectQuery(Query);
				while(result.next())
					 {
					   String nested_method_id=result.getString("method_id");
					   int nested_base_method_id=Integer.parseInt(result.getString("method_type"))-10;
					   //select parent of nested class & insert to its base class
//					    Query="update list_method set class_parents=concat(class_parents,'"+(!nested_class_parent.equals("") & nested_class_parent!=null ? ","+nested_class_parent : "" )+"'),class_parents_interface=concat(class_parents_interface,'"+(!nested_class_interface.equals("") & nested_class_interface!=null ? ","+nested_class_interface : "" )+"') where class_id="+nested_base_class_id;
//						insertQuery(Query);
						
					   
					   //select field of nested method & insert to its base method
						Query="insert into place_for_copy_nested_list_field select * from list_field where field_method_id="+nested_method_id;
						insertQuery(Query);
						Query="update place_for_copy_nested_list_field set field_method_id="+nested_base_method_id;
						insertQuery(Query);
						Query="insert into list_field (`field_class_id`, `field_method_id`, `field_visibility`, `field_is_static`, `field_is_final`, `field_name`, `field_type`, `field_value`, `field_group`) select `field_class_id`, `field_method_id`, `field_visibility`, `field_is_static`, `field_is_final`, `field_name`, `field_type`, `field_value`, `field_group` from place_for_copy_nested_list_field";
						insertQuery(Query);
						Query="TRUNCATE table place_for_copy_nested_list_field ";
						insertQuery(Query);
					   
						//select expression of nested class & insert to its base class 
						Query="insert into place_for_copy_nested_method_expression select * from method_expression where expression_method_id="+nested_method_id;
						insertQuery(Query);
						Query="update place_for_copy_nested_method_expression set expression_method_id="+nested_base_method_id;
						insertQuery(Query);
						Query="insert into method_expression ( `expression_class_id`, `expression_method_id`, `expression_type`, `expression_first`, `expression_second`) select  `expression_class_id`, `expression_method_id`, `expression_type`, `expression_first`, `expression_second` from place_for_copy_nested_method_expression";
						insertQuery(Query);
						Query="TRUNCATE table place_for_copy_nested_method_expression ";
						insertQuery(Query);
					 }
				
				 System.out.println("insert nested method");
	            
		//#####################################insert parent to children
		 Query="select class_id,class_parents,class_name from list_class where  class_type='0' or class_type='1'";
		 result=selectQuery(Query);
		 while(result.next())
		 {
			 String class_id=result.getString("class_id");
			 String class_parent=result.getString("class_parents");
			 String class_name=result.getString("class_name");
			 
			 if(!class_parent.equals("") && class_parent!=null)
			 {  
				String result_parent=getParent(class_parent);
				 class_parent=class_parent+(result_parent.equals("") ? "" : ","+result_parent);
			 }
			 if(!class_parent.equals(result.getString("class_parents")))
			 {
				 String Query2="update list_class set class_parents='"+class_parent+"' where class_id="+class_id;
				 insertQuery(Query2);
			 }
			 
			
			//#######################################insert children of each class
			 if(!class_parent.equals("") && class_parent!=null)
			 {   
				 class_parent=class_parent.replace(",", "','");
				 class_parent="'"+class_parent+"'";
				 String Query2="update list_class set class_children =IF (class_children='' , '"+class_name+"' , concat(class_children,',"+class_name+"')) where class_name in ("+class_parent+")";
				 System.out.println("children of each class:"+Query2);
				 insertQuery(Query2);
			 }
			 
		 }
		 System.out.println("insert parent to children & insert children of each class");
		
		//#######################################insert interface parent to children
		 Query="select class_id,class_parents_interface,class_name,class_parents from list_class where class_type='0' or class_type='1'";
		 result=selectQuery(Query);
		 while(result.next())
		 {
			 String class_id=result.getString("class_id");
			 String class_parents_interface=result.getString("class_parents_interface");
			 String class_parents=result.getString("class_parents");
			 String class_name=result.getString("class_name");
			 if((!class_parents_interface.equals("") && class_parents_interface!=null) || (!class_parents.equals("") && class_parents!=null) )
			 {  
				 if((!class_parents_interface.equals("") && class_parents_interface!=null) && (!class_parents.equals("") && class_parents!=null) ) class_parents=class_parents_interface+","+class_parents;
				 else if((!class_parents_interface.equals("") && class_parents_interface!=null) ) class_parents=class_parents_interface;
				
				 String result_parent=getParentInterface(class_parents);
				 class_parents_interface=class_parents_interface+(result_parent.equals("") ? "" : ","+result_parent);
			 }
			 if(!class_parents_interface.equals(result.getString("class_parents_interface")))
			 {
				 
				 String Query2="update list_class set class_parents_interface='"+class_parents_interface+"' where class_id="+class_id;
				 insertQuery(Query2);
				 System.out.println("class_parents_interface:"+Query2);
			 }
			 
			//#######################################insert children of each class
			 if(!class_parents_interface.equals("") && class_parents_interface!=null)
			 {  
				 class_parents_interface=class_parents_interface.replace(",", "','");
				 class_parents_interface="'"+class_parents_interface+"'";
				 String Query2="update list_class set class_children =IF (class_children='' , '"+class_name+"' , concat(class_children,',"+class_name+"')) where class_name in ("+class_parents_interface+")";
				 insertQuery(Query2);
				 System.out.println("interface children:"+Query2);
			 }
		 }
		 System.out.println("insert parent_interface to children & insert children of each _interface");
		 
	    //#######################################################################
		//Transitive association?
		 }catch(Exception e){ System.out.println(e);}
		
	}
static public String skipInjection(String str)
{
  return str.replace("\n", "\\n").replace("'", "\'");	
}
	//public static void main(String args[]){  
//try{  
//Class.forName("com.mysql.jdbc.Driver");  
//$this->con=DriverManager.getConnection(  
//"jdbc:mysql://localhost:3306/test","root","");  
//here sonoo is database name, root is username and password  
//Statement stmt=con.createStatement();  
//ResultSet rs=stmt.executeQuery("select * from first");  
//while(rs.next())  
//System.out.println(rs.getInt(1)+"  "+rs.getString(2));
//con.close();  
//}catch(Exception e){ System.out.println(e);}

 
  
  
 

}  
