import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class patternQuery {
	/*
	 * //Abbreviation:
	 * cl_col_mang_met=>class_by_collection_and_management_method
	 * cl_met_lo_inv_sname=>class_by_method_by_loop_and_invocarion_and_check_same_name
	 */
	
	public void Singleton()
	{
		///ArrayList[] final_result=new ArrayList[1];
		try{
			//استخراج مصداق ها
			//########################example1
			String Query1="select group_concat(method_class_id) as mcid from list_method where method_type=1 and (method_visibility='private' or method_visibility='protected') ";
			//list_of_first_process
			String first_class_id1="";
			String first_class_id2="";
			ResultSet result=null;
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			result=con.selectQuery(Query1);
			if(result.next()) first_class_id1=result.getString("mcid");
			
		    if(first_class_id1!="")
		    {
				Query1="select group_concat(method_class_id) as mcid from list_method left join list_class on list_method.method_class_id=list_class.class_id where method_class_id in ("+first_class_id1+") and method_type=0 and method_visibility='public'  and method_is_static=true and (method_output_type=class_name or method_output_type in (class_parents) or method_output_type in (class_parents_interface) )";
				con=MysqlCon.getInstance(args);
				result=con.selectQuery(Query1);
				if(result.next()) first_class_id1=result.getString("mcid");	
		    }
		
		//########################example2
		String Query1_2="select group_concat(method_class_id) as mcid from list_method  where method_type=1 and method_visibility='public' and method_is_static=true ";
		con=MysqlCon.getInstance(args);
		result=con.selectQuery(Query1_2);
		if(result.next())  first_class_id2=result.getString("mcid");
		
		//############################merge output of first process of example1 & example2
		if(first_class_id1!=null && !first_class_id1.equals("") && first_class_id2!=null &&  !first_class_id2.equals("")) first_class_id1=first_class_id1+","+first_class_id2;
		else if(first_class_id2!=null &&  !first_class_id2.equals("")) first_class_id1=first_class_id2;
		if(first_class_id1!=null && !first_class_id1.equals(""))
		{ 
			//تا اینجا کلاس هایی که دارای سازنده با مشخصات خاص و متد پابلیک استاتیک است شناسایی شده است
		    //  کلاس هایی که امضای متد مقاله 15 را دارند+متد پابلیک استاتیک
	    	//حال به دنبال این میگردیم که در کدام یک از این کلاس ها، متغیر پرایویت استاتیک وجود دارد
			//???
			//delete =>and  field_visibility='private'
			Query1="select group_concat(field_class_id) as fcid from list_field left join list_class on list_field.field_class_id=list_class.class_id where  field_class_id in ("+first_class_id1+")   and field_group=0 and field_is_static=true   and  ( field_type=class_name or  	field_type in (class_parents) or  	field_type in (class_parents_interface) )";
			con=MysqlCon.getInstance(args);
			result=con.selectQuery(Query1);
			if(result.next())  first_class_id1=result.getString("fcid");
			System.out.println("singleton: "+first_class_id1);
		}
		else
			System.out.println("dont find any singleton");
			
		
		//return final_result;
		}catch(Exception e){ System.out.println(e);}
	}
	
	
	
}
