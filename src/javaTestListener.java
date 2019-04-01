import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

//import org.antlr.v4.runtime.Token;
public class javaTestListener extends javaBaseListener {
	int class_id=0;
	int nested_class_id=0;
	int method_id=0;
	int nested_method_id=0;
	int field_id=0;
	//String field_position="";
	boolean is_constructor=false;
	/////String class_name;
	String  visibility="public";
	boolean is_final=false;
	boolean is_abstract=false;
	boolean is_static=false;
	String  field_visibility="public";
	boolean field_is_static=false;
	boolean field_is_final=false;
	ArrayList<String> method_field=new ArrayList(); // list field related to a method
	
	@Override public void enterCompilationUnit(javaParser.CompilationUnitContext ctx)
	{ 
		System.out.println("enterCompilationUnit"+ctx.getText());
	}
	@Override public void exitCompilationUnit(javaParser.CompilationUnitContext ctx) { }
	///////////////////////CLASS AND INTERFACE
	@Override public void enterClassOrInterfaceModifier(javaParser.ClassOrInterfaceModifierContext ctx) 
	{
		String ClassOrInterfaceModifier=ctx.getText().toLowerCase();	
		switch(ClassOrInterfaceModifier)
		{
		case "public":
		case "private": 
		case "protected": this.visibility=ClassOrInterfaceModifier; break;
		case "abstract": this.is_abstract=true;  break;
		case "static": this.is_static=true;  break;
		case "final": this.is_final=true;  break;
		default: System.out.println("NOTEEEEEEEEEEEEEEEEEEE:enterClassOrInterfaceModifier:"+ClassOrInterfaceModifier);
		}	
		System.out.println("enterClassOrInterfaceModifier:"+this.is_static);
	}
	@Override public void exitClassOrInterfaceModifier(javaParser.ClassOrInterfaceModifierContext ctx) {}
	//--------------------------------
	@Override public void enterMemberDeclaration(javaParser.MemberDeclarationContext ctx) 
	{ 
		System.out.println("enterMemberDeclaration"+ctx.getText());
	}
	@Override public void exitMemberDeclaration(javaParser.MemberDeclarationContext ctx) { }
	//-------------------------------------
	@Override public void enterClassOrInterfaceType(javaParser.ClassOrInterfaceTypeContext ctx)
	{
		System.out.println("enterClassOrInterfaceType"+ctx.getText());
	}
	@Override public void exitClassOrInterfaceType(javaParser.ClassOrInterfaceTypeContext ctx) { }
	//----------------------------------
	//////////////////////INTERFACE
	@Override public void enterInterfaceDeclaration(javaParser.InterfaceDeclarationContext ctx) 
	{ 
		System.out.println("enterInterfaceDeclaration"+ctx.getText());
		//save 1) name, 2) visibility, 3) is_abstract=0, 4) is_static=0, 5) parent
		try{
				int class_type=1; //6 interface 
				String class_name=ctx.Identifier().getText().toLowerCase(); //1
				String class_visibility=this.visibility.toLowerCase(); //2
				boolean class_is_abstract=this.is_abstract;//3
				boolean class_is_static=this.is_static;//4
				boolean class_is_final=this.is_final;//4
				String class_parent="";
				String class_parent_interface="";
				for(int i=0;i<ctx.getChildCount();i++)
				{
					 if(ctx.getChild(i).toString().equals((String) "extends"))
					{
						for (int j=i+1;j<ctx.getChildCount()-1; j++)
						   class_parent_interface+=ctx.getChild(j).getText().toLowerCase(); //5
					} 
				}
				String[] args = new String[7];
				MysqlCon con=MysqlCon.getInstance(args);
				
				 
				boolean insert_res=con.insertQuery("insert into list_class(class_name,class_type,class_is_abstract,class_visibility,class_is_static,class_is_final,class_parents,class_parents_interface,package_name) "+ "values('"+class_name+"','"+class_type+"',"+class_is_abstract+",'"+class_visibility+"',"+class_is_static+","+class_is_final+",'"+class_parent+"','"+ class_parent_interface+"','"+ generalFunction.packageName+"')");
				ResultSet result=con.selectQuery("select max(class_id) as mcid from list_class");
				// generalFunction.printStringArray(result);
				if(result.next()) this.class_id=Integer.parseInt(result.getString("mcid"));
				this.setDefaultValueAtrribute();
		}catch(Exception e){ System.out.println(e);}
				
	}
	@Override public void exitInterfaceDeclaration(javaParser.InterfaceDeclarationContext ctx)
	{
		this.class_id=0;
		String ClassName=ctx.Identifier().getText();
		System.out.println("exitInterfaceDeclaration: '"+ClassName+"' ");
	}
	//--------------------------------
	@Override public void enterInterfaceBody(javaParser.InterfaceBodyContext ctx)
	{ 
		System.out.println("enterInterfaceBody"+ctx.getText());
	}
	@Override public void exitInterfaceBody(javaParser.InterfaceBodyContext ctx) { }
	//--------------------------------
	@Override public void enterInterfaceBodyDeclaration(javaParser.InterfaceBodyDeclarationContext ctx) 
	{
		System.out.println("enterInterfaceBodyDeclaration"+ctx.getText());
	}
	@Override public void exitInterfaceBodyDeclaration(javaParser.InterfaceBodyDeclarationContext ctx) { }
//---------------------------------
	@Override public void enterInterfaceMemberDeclaration(javaParser.InterfaceMemberDeclarationContext ctx) 
	{
		System.out.println("enterInterfaceMemberDeclaration"+ctx.getText());
	}
	@Override public void exitInterfaceMemberDeclaration(javaParser.InterfaceMemberDeclarationContext ctx) {}
	//---------------------------------
	@Override public void enterInterfaceMethodDeclaration(javaParser.InterfaceMethodDeclarationContext ctx) 
	{
		System.out.println("enterInterfaceMethodDeclaration"+ctx.getText());
		//save 1) name, 2) visibility, 3) is_abstract=0, 4) is_static, 5) output 6)input
		try{
		//invocation,...
				String method_name=ctx.Identifier().getText().toLowerCase(); //1
				String method_visibility=this.visibility.toLowerCase(); //2
				boolean method_is_abstract=this.is_abstract;//3
				boolean method_is_static=this.is_static;//4
				boolean method_is_final=this.is_final;//4
				String method_output_type=ctx.getChild(0).getText().toLowerCase(); //5
				int method_type=0;
				if(this.method_id==0)
					   method_type=0;
					 else //this is a nested class
						method_type=this.method_id+10;
				String[] args = new String[7];
				MysqlCon con=MysqlCon.getInstance(args);
				boolean insert_res=con.insertQuery("insert into list_method"
						+ "(method_class_id,method_name,method_visibility,method_is_abstract,method_is_static,method_is_final,method_output_type,method_type) "
						+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+method_name+"','"+method_visibility+"',"+method_is_abstract+","+method_is_static+","+method_is_final+",'"+method_output_type+"',"+method_type+")");
				ResultSet result=con.selectQuery("select max(method_id) as mmid from list_method");
				//generalFunction.printStringArray(result);
				if(result.next()) 
				{
					if(this.method_id==0)
						   this.method_id=Integer.parseInt(result.getString("mmid")); 
						 else //this is a nested class
							 this.nested_method_id=Integer.parseInt(result.getString("mmid")); 
					//this.method_id=Integer.parseInt(result.getString("mmid"));
				}
				this.setDefaultValueAtrribute();
	}catch(Exception e){ System.out.println(e);}
	}
	@Override public void exitInterfaceMethodDeclaration(javaParser.InterfaceMethodDeclarationContext ctx) { }
   //-----------------------------------------
	@Override public void enterGenericInterfaceMethodDeclaration(javaParser.GenericInterfaceMethodDeclarationContext ctx) 
	{
		System.out.println("enterGenericInterfaceMethodDeclaration"+ctx.getText());
	}
	@Override public void exitGenericInterfaceMethodDeclaration(javaParser.GenericInterfaceMethodDeclarationContext ctx) { }
	//------------------------------------------
	//////////////////////CLASS	
	@Override public void enterClassDeclaration(javaParser.ClassDeclarationContext ctx) { 
		
		//save 1) name, 2) visibility, 3) is_abstract, 4) is_static, 5) parent
		try
		{
		int class_type=0; //6 class
		if(this.class_id==0)
			   class_type=0;
			 else //this is a nested class
				class_type=this.class_id+10;
		 
		String class_name=ctx.Identifier().getText().toLowerCase(); //1
		String class_visibility=this.visibility.toLowerCase(); //2
		boolean class_is_abstract=this.is_abstract;//3
		boolean class_is_static=this.is_static;//4
		boolean class_is_final=this.is_final;//4
		String class_parent="";
		String class_parent_interface="";
		for(int i=0;i<ctx.getChildCount();i++)
		{
			if(ctx.getChild(i).toString().equals((String) "extends"))
				{
					class_parent=ctx.getChild(i+1).getText().toLowerCase(); //5
				}
			else if(ctx.getChild(i).toString().equals((String) "implements"))
			{
				for (int j=i+1;j<ctx.getChildCount()-1; j++)
				   class_parent_interface+=ctx.getChild(j).getText().toLowerCase(); //5
			} 
		}

		String[] args = new String[7];
		MysqlCon con=MysqlCon.getInstance(args);
		boolean insert_res=con.insertQuery("insert into list_class(class_name,class_type,class_is_abstract,class_visibility,class_is_static,class_is_final,class_parents,class_parents_interface,package_name) "+ "values('"+class_name+"','"+class_type+"',"+class_is_abstract+",'"+class_visibility+"',"+class_is_static+","+class_is_final+",'"+class_parent+"','"+ class_parent_interface+"','"+generalFunction.packageName+"')");
		ResultSet result=con.selectQuery("select max(class_id) as mcid from list_class");
			if(result.next())
				{
				if(this.class_id==0)
					   this.class_id=Integer.parseInt(result.getString("mcid")); 
					 else //this is a nested class
						 this.nested_class_id=Integer.parseInt(result.getString("mcid")); 
				}
			 this.setDefaultValueAtrribute();
		}catch(Exception e){ System.out.println(e);}
	}
	@Override public void exitClassDeclaration(javaParser.ClassDeclarationContext ctx)
	{
		if(this.nested_class_id > 0 ) this.nested_class_id=0;
		else this.class_id=0;
		String ClassName=ctx.Identifier().getText();
		System.out.println("exitClassDeclaration: '"+ClassName+"' ");
	}
	//--------------------------------
	@Override public void enterConstructorDeclaration(javaParser.ConstructorDeclarationContext ctx) 
	{
		System.out.println("enterConstructorDeclaration"+ctx.Identifier().getText());
		//save 1) name, 2) visibility, 3) is_abstract, 4) is_static, 5) output 6)input
		try
		{	
		//invocation,invpcatoin object
				String method_name=ctx.Identifier().getText().toLowerCase(); //1
				String method_visibility=this.visibility.toLowerCase(); //2
				boolean method_is_abstract=this.is_abstract;//3
				boolean method_is_static=this.is_static;//4
				boolean method_is_final=this.is_final;//4
				String method_output_type=ctx.Identifier().getText().toLowerCase(); //5///////////////////////////////////////////////////

				String[] args = new String[7];
				MysqlCon con=MysqlCon.getInstance(args);
				boolean insert_res=con.insertQuery("insert into list_method"
						+ "(method_class_id,method_name,method_visibility,method_is_abstract,method_is_static,method_is_final,method_output_type,method_type) "
						+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+method_name+"','"+method_visibility+"',"+method_is_abstract+","+method_is_static+","+method_is_final+",'"+method_output_type+"',1)");
				this.setDefaultValueAtrribute();
				ResultSet result=con.selectQuery("select max(method_id) as mcid from list_method");
				if(result.next()) 
				{
					if(this.method_id==0)
						   this.method_id=Integer.parseInt(result.getString("mcid")); 
						 else //this is a nested class
							 this.nested_method_id=Integer.parseInt(result.getString("mcid")); 
					//this.method_id=Integer.parseInt(result.getString("mcid"));
				}
				this.is_constructor=true;
	}catch(Exception e){ System.out.println(e);}
		
	}
	@Override public void exitConstructorDeclaration(javaParser.ConstructorDeclarationContext ctx)
	{
		//this.method_id=0;
		if(this.nested_method_id > 0)
			   this.nested_method_id=0; 
			 else //this is a nested class
				 this.method_id=0; 
		this.is_constructor=false;
	}	
	//-------------------------------
	@Override public void enterConstructorBody(javaParser.ConstructorBodyContext ctx)
	{
		System.out.println("enterConstructorBody"+ctx.getText());
	}
	@Override public void exitConstructorBody(javaParser.ConstructorBodyContext ctx) { }
	//-------------------------------
	@Override public void enterGenericConstructorDeclaration(javaParser.GenericConstructorDeclarationContext ctx) 
	{
		System.out.println("enterGenericConstructorDeclaration"+ctx.getText());
	}
	@Override public void exitGenericConstructorDeclaration(javaParser.GenericConstructorDeclarationContext ctx) { }
	//-------------------------------
	@Override public void enterClassBody(javaParser.ClassBodyContext ctx)
	{ 
		System.out.println("enterClassBody"+ctx.getText());
	}
	@Override public void exitClassBody(javaParser.ClassBodyContext ctx) { }
	//------------------------
	@Override public void enterClassBodyDeclaration(javaParser.ClassBodyDeclarationContext ctx)
	{ 
		System.out.println("enterClassBodyDeclaration"+ctx.getText());
	}
	@Override public void exitClassBodyDeclaration(javaParser.ClassBodyDeclarationContext ctx) { }
	
///////////////////////########METHOD#########///////////////////////////	
	@Override public void enterMethodDeclaration(javaParser.MethodDeclarationContext ctx)
	{ 
		//save 1) name, 2) visibility, 3) is_abstract, 4) is_static, 5) output 6)input
		//invocation,invpcatoin object
	try
	{
		String method_name=ctx.Identifier().getText().toLowerCase(); //1
		String method_visibility=this.visibility.toLowerCase(); //2
		boolean method_is_abstract=this.is_abstract;//3
		boolean method_is_static=this.is_static;//4
		boolean method_is_final=this.is_final;//4
		String method_output_type=ctx.getChild(0).getText().toLowerCase(); //5
		int method_type=0;
		if(this.method_id==0)
			   method_type=0;
			 else //this is a nested class
				method_type=this.method_id+10;
		
		//String input=ctx.getChild(n).getText(); //6
		String[] args = new String[7];
		MysqlCon con=MysqlCon.getInstance(args);
		boolean insert_res=con.insertQuery("insert into list_method"
				+ "(method_class_id,method_name,method_visibility,method_is_abstract,method_is_static,method_is_final,method_output_type,method_type) "
				+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+method_name+"','"+method_visibility+"',"+method_is_abstract+","+method_is_static+","+method_is_final+",'"+method_output_type+"',"+method_type+")");
		ResultSet result=con.selectQuery("select max(method_id) as mcid from list_method");
		
		//if(result.next()) this.method_id=Integer.parseInt(result.getString("mcid"));
		if(result.next())
		{
			if(this.method_id==0)
				   this.method_id=Integer.parseInt(result.getString("mcid")); 
				 else //this is a nested class
					 this.nested_method_id=Integer.parseInt(result.getString("mcid")); 
		}
		
		
		this.setDefaultValueAtrribute();
	}catch(Exception e){ System.out.println(e);}
	}
	@Override public void exitMethodDeclaration(javaParser.MethodDeclarationContext ctx) 
	{
		 //this.method_id=0;
		 if(this.nested_method_id > 0 ) this.nested_method_id=0;
			else 
			{
				this.method_id=0;
				 String method_name=ctx.Identifier().getText(); //1
				 System.out.println("exitMethodDeclarationnnnnnnnnn");
				 generalFunction.printStringArray(this.method_field);
				 this.method_field.clear();
				 System.out.println("exitMethodDeclaration: '"+method_name+"' ");
			}
	}
   //----------------------------------
	@Override public void enterGenericMethodDeclaration(javaParser.GenericMethodDeclarationContext ctx) 
	{
		System.out.println("enterGenericMethodDeclaration"+ctx.getText());
	}
	@Override public void exitGenericMethodDeclaration(javaParser.GenericMethodDeclarationContext ctx) { }
	//---------------------------------
	@Override public void enterMethodBody(javaParser.MethodBodyContext ctx)
	{
		System.out.println("enterMethodBody"+ctx.getText());
	}
	@Override public void exitMethodBody(javaParser.MethodBodyContext ctx) { }
	//---------------------------------	
    @Override public void enterExplicitGenericInvocation(javaParser.ExplicitGenericInvocationContext ctx) 
	{
    	System.out.println("enterExplicitGenericInvocation"+ctx.getText());
	}	
	@Override public void exitExplicitGenericInvocation(javaParser.ExplicitGenericInvocationContext ctx)
	{
		//String MethodName=ctx.getText();
	}	
    //-----------------------------	
    @Override public void enterExpression(javaParser.ExpressionContext ctx) 
	{
		int child_count=ctx.getChildCount();
		System.out.println("enterExpression"+child_count);
		System.out.println(ctx.getChild(0).getText());
		//check creational
		if(child_count==2 && ctx.getChild(0).toString().equals("new") )
		{
			String expression_type=(String) "creational";
			String created_class=ctx.getChild(1).getText().toLowerCase();
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','new','"+created_class+"')");	
		}
		//check call
		if(
				(child_count==3 && ctx.getChild(1).toString().equals("(") && ctx.getChild(2).toString().equals(")") )
				||
				(child_count==4 && ctx.getChild(1).toString().equals("(") && ctx.getChild(3).toString().equals(")"))
		)
		{ // show() or show(1,2) or this.show or b.show;
			String callee_name="";// caller type get by after queries (step 2)
			String calee_fun="";
			int calee_count=ctx.getChild(0).getChildCount();
			switch(calee_count)
			{
			case 1:
			{
				 callee_name="self";// caller type get by after queries (step 2)
				 calee_fun=ctx.getChild(0).getChild(0).getText().toLowerCase();
				
			} break;
			case 3: 
			{
				if(ctx.getChild(0).getChild(1).getText().equals("."))
				{
					callee_name=ctx.getChild(0).getChild(0).getText().toLowerCase();// caller type get by after queries (step 2)
					calee_fun=ctx.getChild(0).getChild(2).getText().toLowerCase();
				}
				
			}
			break;
			}
			if(callee_name!="" && calee_fun!="")
			{
				String[] args = new String[7];
				MysqlCon con=MysqlCon.getInstance(args);
			String expression_type=(String) "invocation";
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','"+callee_name+"','"+calee_fun+"')");	
			}
		}
		//check invocation
		/*if(child_count==3 && ctx.getChild(1).toString().equals(".") )
		{
			String calee_fun=ctx.getChild(2).getText();
			String callee_name=ctx.getChild(0).getText();// caller type get by after queries (step 2)
			MysqlCon con=MysqlCon.getInstance();
			String expression_type=(String) "invocation";
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+this.method_id+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','"+callee_name+"','"+calee_fun+"')");	
		}	*/	
	}
	@Override public void exitExpression(javaParser.ExpressionContext ctx) { }
    //-----------------------	
	@Override public void enterParExpression(javaParser.ParExpressionContext ctx) 
	{
		System.out.println("enterParExpression"+ctx.getText());
	}
	@Override public void exitParExpression(javaParser.ParExpressionContext ctx) { }
//----------------------------
	@Override public void enterExpressionList(javaParser.ExpressionListContext ctx) 
	{
		System.out.println("enterExpressionList"+ctx.getText());
	}
	@Override public void exitExpressionList(javaParser.ExpressionListContext ctx) { }
	//-------------------------
	@Override public void enterConstantExpression(javaParser.ConstantExpressionContext ctx) 
	{
		System.out.println("enterConstantExpression"+ctx.getText());
	}
	@Override public void exitConstantExpression(javaParser.ConstantExpressionContext ctx) { }
	//--------------------------
    @Override public void enterStatementExpression(javaParser.StatementExpressionContext ctx) 
	{
		System.out.println("enterStatementExpression"+ctx.getText());
		//r1=r3,f=5,b.fun2()
	}
	@Override public void exitStatementExpression(javaParser.StatementExpressionContext ctx) { }
	//-------------------------
	@Override public void enterBlock(javaParser.BlockContext ctx)
	{
		System.out.println("enterBlock"+ctx.getText());
	}
	@Override public void exitBlock(javaParser.BlockContext ctx) { }
	//------------------------
	@Override public void enterBlockStatement(javaParser.BlockStatementContext ctx)
	{ 
		System.out.println("enterBlockStatement"+ctx.getText());
		//detect for:
		System.out.println("countttttttttttttttttttttttttt"+ctx.getChildCount());
		//////////////
		//for(inti=0;i<10;i++){r1=r1*2;}   ,  r1=r1*2;	,  while(r3>10){r3--;}
		//switch(r1){case1:s=1;break;case2:s=2;break;default:s=3;}
		//break;
		//if(r1=r2){r1=r3;}elser1=r2;
		/////////int m;
		/////////A num;
		//f=5;
		////////Bb=newB(s);
		//b.fun2();
		//return1
	}
	@Override public void exitBlockStatement(javaParser.BlockStatementContext ctx) { }
	//-----------------------
	@Override public void enterStatement(javaParser.StatementContext ctx) 
	{ 
		System.out.println("enterStatement"+ctx.getText());
		System.out.println("counteeeeeeeeeeeeeeeeeeee"+ctx.getChildCount());
		//detect for:
		if(ctx.getChildCount()==5 &&  (ctx.getChild(0).getText().toLowerCase().equals((String)"for") || ctx.getChild(0).getText().toLowerCase().equals((String)"foreach")))
		{
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			String expression_type=(String) "loop";
			String for_control=con.skipInjection(ctx.getChild(2).getText().toLowerCase());
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','for','"+for_control+"')");	
	
		}
		//detect while
		else if(ctx.getChildCount()==3 && ctx.getChild(0).getText().toLowerCase().equals((String)"while"))
		{
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			String expression_type=(String) "loop";
			String while_control=con.skipInjection(ctx.getChild(1).getText());
			
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','while','"+while_control+"')");	
	
		}
		//detect if
		else if(ctx.getChildCount()>=3 && ctx.getChild(0).getText().toLowerCase().equals((String)"if"))
		{
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			String expression_type=(String) "conditional";
			String if_control=con.skipInjection(ctx.getChild(1).getText().toLowerCase());
		
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','if','"+if_control+"')");	
	
		}
		else if(ctx.getChildCount()>=5 && ctx.getChild(0).getText().toLowerCase().equals((String)"assert"))
		{
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			String expression_type=(String) "conditional";
			String if_control=con.skipInjection(ctx.getChild(1).getText().toLowerCase());
			
			boolean insert_res=con.insertQuery("insert into method_expression"
					+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
					+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','assert','"+if_control+"')");	
	
		}
		//detect switch
				else if(ctx.getChildCount()>=5 && ctx.getChild(0).getText().toLowerCase().equals((String)"switch"))
				{
					String expression_type=(String) "conditional";
					String[] args = new String[7];
					MysqlCon con=MysqlCon.getInstance(args);
					String switch_control=con.skipInjection(ctx.getChild(1).getText().toLowerCase());
					
					boolean insert_res=con.insertQuery("insert into method_expression"
							+ "(expression_method_id,expression_class_id,expression_type,expression_first,expression_second) "
							+ "values("+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+","+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",'"+expression_type+"','switch','"+switch_control+"')");	
			
				}
			///detect return
				else if(ctx.getChildCount()>=3 && ctx.getChild(0).getText().toLowerCase().equals((String)"return") && this.is_constructor)
				{
					try
					{
					String output=ctx.getChild(1).getText().toLowerCase(); //name of variable
					String Query1="select field_type from list_field where  field_class_id="+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+" and field_method_id="+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+" and field_name='"+output+"' ";
					String[] args = new String[7];
					MysqlCon con=MysqlCon.getInstance(args);
					ResultSet result=con.selectQuery(Query1);
					if(result.next())  output=con.skipInjection(result.getString("field_type"));
					else output="";
					//////////////////
					boolean update_res=con.insertQuery("update  list_method set method_output_type='"+output+"' where method_id="+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id));	
					}catch(Exception e){ System.out.println(e);}
				}
		///////
		//for(inti=0;i<10;i++){r1=r1*2;}  , {r1=r1*2;},r1=r1*2;  , while(r3>10){r3--;}
		//switch(r1){case1:s=1;break;case2:s=2;break;default:s=3;}
		//break;
		//if(r1=r2){r1=r3;}elser1=r2;
		//f=5;
		//b.fun2();
		//return1
	}
	@Override public void exitStatement(javaParser.StatementContext ctx) { }
	//-----------------------
	@Override public void enterSwitchBlockStatementGroup(javaParser.SwitchBlockStatementGroupContext ctx) 
	{ 
		System.out.println("enterSwitchBlockStatementGroup"+ctx.getText());
		//case1:s=1;break;  
		//case2:s=2;break;
		//default:s=3;
	}
	@Override public void exitSwitchBlockStatementGroup(javaParser.SwitchBlockStatementGroupContext ctx) { }
	//--------------------------
	@Override public void enterSwitchLabel(javaParser.SwitchLabelContext ctx) 
	{
		System.out.println("enterSwitchLabel"+ctx.getText());
	}
	@Override public void exitSwitchLabel(javaParser.SwitchLabelContext ctx) { }
	//------------------------------
	@Override public void enterForControl(javaParser.ForControlContext ctx)
	{
		System.out.println("enterForControl"+ctx.getText());
	}
	@Override public void exitForControl(javaParser.ForControlContext ctx) { }
	//--------------------------
	@Override public void enterForInit(javaParser.ForInitContext ctx)
	{
		System.out.println("enterForInit"+ctx.getText());
		//inti=0
		//=>enterExpression:i<10 
		//=>enterForUpdate:  i++ 
	}
	@Override public void exitForInit(javaParser.ForInitContext ctx) { }
	//--------------------------------
	@Override public void enterForUpdate(javaParser.ForUpdateContext ctx)
	{ 
		System.out.println("enterForUpdate"+ctx.getText());
		//i++
	}
	@Override public void exitForUpdate(javaParser.ForUpdateContext ctx) { }
	//------------------------
	@Override public void enterEnhancedForControl(javaParser.EnhancedForControlContext ctx)
	{ 
		System.out.println("enterEnhancedForControl"+ctx.getText());
	}
	@Override public void exitEnhancedForControl(javaParser.EnhancedForControlContext ctx) { }
	//-------------------------------
	@Override public void enterFinallyBlock(javaParser.FinallyBlockContext ctx) 
	{
		System.out.println("enterFinallyBlock"+ctx.getText());
	}
	@Override public void exitFinallyBlock(javaParser.FinallyBlockContext ctx) { }
	//---------------------------------------
	@Override public void enterResourceSpecification(javaParser.ResourceSpecificationContext ctx) 
	{
		System.out.println("enterResourceSpecification"+ctx.getText());
	}
	@Override public void exitResourceSpecification(javaParser.ResourceSpecificationContext ctx) { }
	//-----------------------------------
	@Override public void enterResources(javaParser.ResourcesContext ctx) 
	{
		System.out.println("enterResources"+ctx.getText());
	}
	@Override public void exitResources(javaParser.ResourcesContext ctx) { }
	//--------------------------
	@Override public void enterResource(javaParser.ResourceContext ctx) 
	{
		System.out.println("enterResource"+ctx.getText());
	}
	@Override public void exitResource(javaParser.ResourceContext ctx) { }
	//---------------------------------------
	@Override public void enterCreator(javaParser.CreatorContext ctx) 
	{
		System.out.println("enterCreator"+ctx.getText());
	}
	@Override public void exitCreator(javaParser.CreatorContext ctx) { }
	//----------------------------------------
	@Override public void enterCreatedName(javaParser.CreatedNameContext ctx) 
	{
		System.out.println("enterCreatedName"+ctx.getText());
	}
	@Override public void exitCreatedName(javaParser.CreatedNameContext ctx) { }
	//------------------------------------------
	@Override public void enterInnerCreator(javaParser.InnerCreatorContext ctx) 
	{
		System.out.println("enterInnerCreator"+ctx.getText());
	}
	@Override public void exitInnerCreator(javaParser.InnerCreatorContext ctx) { }
	//-----------------------------------
	@Override public void enterArrayCreatorRest(javaParser.ArrayCreatorRestContext ctx) 
	{
		System.out.println("enterArrayCreatorRest"+ctx.getText());
	}
	@Override public void exitArrayCreatorRest(javaParser.ArrayCreatorRestContext ctx) { }
	//-----------------------------------
	@Override public void enterClassCreatorRest(javaParser.ClassCreatorRestContext ctx)
	{
		System.out.println("enterClassCreatorRest"+ctx.getText());
	}
	@Override public void exitClassCreatorRest(javaParser.ClassCreatorRestContext ctx) { }
	//-----------------------------------
///////////////////////////VARIABLE	
	@Override public void enterTypeDeclaration(javaParser.TypeDeclarationContext ctx)
	{ 
		System.out.println("enterTypeDeclaration"+ctx.getText());
	}
	@Override public void exitTypeDeclaration(javaParser.TypeDeclarationContext ctx) { }
//--------------------------------------------
	@Override public void enterFieldDeclaration(javaParser.FieldDeclarationContext ctx) 
	{
		//for field in class
	try
	{
		System.out.println("enterFieldDeclaration"+ctx.getText());
		System.out.println("iiiiiiiiii"+ctx.getChild(1).getText());
		 
		String type_field=ctx.typeType().getText().toLowerCase();
		String name_field=ctx.getChild(1).getText().toLowerCase();
		String[] args = new String[7];
		MysqlCon con=MysqlCon.getInstance(args);
		boolean insert_res=con.insertQuery("insert into list_field"
				+ "(field_class_id,field_method_id,field_visibility,field_is_static,field_is_final,field_name,field_type,field_value,field_group) "
				+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+",0,'"+this.field_visibility+"',"+this.field_is_static+","+this.field_is_final+",'"+name_field+"','"+type_field+"','',0)");	
		ResultSet result=con.selectQuery("select max(field_id) as mcid from list_field");
		if(result.next()) this.field_id=Integer.parseInt(result.getString("mcid"));
		this.setDefaultFieldValue();
		this.setDefaultValueAtrribute();
	}catch(Exception e){ System.out.println(e);}
	}
	@Override public void exitFieldDeclaration(javaParser.FieldDeclarationContext ctx) { }
	//--------------------------------------
	@Override public void enterVariableDeclarators(javaParser.VariableDeclaratorsContext ctx) 
	{
		System.out.println("enterVariableDeclarator(s)"+ctx.getText());
	}
	@Override public void exitVariableDeclarators(javaParser.VariableDeclaratorsContext ctx) { }
	//--------------------------------------
	@Override public void enterVariableDeclarator(javaParser.VariableDeclaratorContext ctx) 
	{
		System.out.println("enterVariableDeclarator"+ctx.getText());
	}
	@Override public void exitVariableDeclarator(javaParser.VariableDeclaratorContext ctx) { }
//--------------------------------------------
	@Override public void enterVariableDeclaratorId(javaParser.VariableDeclaratorIdContext ctx) 
	{
		System.out.println("enterVariableDeclaratorId"+ctx.getText());
		//enterLocalVariableDeclaration
		// filed_id 
		if(this.field_id > 0 )
		{
			String field_name=ctx.getText().toLowerCase();
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			boolean update_res=con.insertQuery("update list_field set "
					+ "field_name='"+field_name+"' where field_id="+this.field_id);	
			this.field_id=0;
		}
		
		
	}
    @Override public void exitVariableDeclaratorId(javaParser.VariableDeclaratorIdContext ctx) { }
	//-------------------------------
    @Override public void enterVariableInitializer(javaParser.VariableInitializerContext ctx) 
    {
    	System.out.println("enterVariableInitializer"+ctx.getText());
    }
	@Override public void exitVariableInitializer(javaParser.VariableInitializerContext ctx) { }
//------------------------------------------
	@Override public void enterArrayInitializer(javaParser.ArrayInitializerContext ctx)
	{
		System.out.println("enterArrayInitializer"+ctx.getText());
	}
	@Override public void exitArrayInitializer(javaParser.ArrayInitializerContext ctx) { }
	//-------------------------------------
	@Override public void enterPrimitiveType(javaParser.PrimitiveTypeContext ctx)
	{
		System.out.println("enterPrimitiveType"+ctx.getText());
	}
	@Override public void exitPrimitiveType(javaParser.PrimitiveTypeContext ctx) { }
	//----------------------------------
	@Override public void enterTypeArguments(javaParser.TypeArgumentsContext ctx)
	{
		System.out.println("enterTypeArguments"+ctx.getText());
	}
	@Override public void exitTypeArguments(javaParser.TypeArgumentsContext ctx) { }
//----------------------------
	@Override public void enterTypeArgument(javaParser.TypeArgumentContext ctx)
	{
		System.out.println("enterTypeArgument"+ctx.getText());
	}
	@Override public void exitTypeArgument(javaParser.TypeArgumentContext ctx) { }
	//--------------------------------------
	@Override public void enterQualifiedNameList(javaParser.QualifiedNameListContext ctx)
	{
		System.out.println("enterQualifiedNameList"+ctx.getText());
	}
	@Override public void exitQualifiedNameList(javaParser.QualifiedNameListContext ctx) { }
	//--------------------------------------------
	@Override public void enterFormalParameters(javaParser.FormalParametersContext ctx)
	{
		System.out.println("enterFormalParameters"+ctx.getText());
		
	}
	@Override public void exitFormalParameters(javaParser.FormalParametersContext ctx) { }
	//--------------------------------------------
	@Override public void enterFormalParameterList(javaParser.FormalParameterListContext ctx)
	{
		System.out.println("enterFormalParameterList"+ctx.getText());
	}
	@Override public void exitFormalParameterList(javaParser.FormalParameterListContext ctx) { }
	//------------------------------------
	@Override public void enterFormalParameter(javaParser.FormalParameterContext ctx)
        { 
		//for input methods
		try
		{
			System.out.println("enterFormalParameter"+ctx.getText());
			String type_field=ctx.typeType().getText().toLowerCase();
			String name_field=ctx.getChild(1).getText().toLowerCase();
			this.method_field.add(name_field);
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			boolean insert_res=con.insertQuery("insert into list_field"
					+ "(field_class_id,field_method_id,field_visibility,field_is_static,field_is_final,field_name,field_type,field_value,field_group) "
					+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+","+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+",'private',false,false,'"+name_field+"','"+type_field+"','',2)");	
			//this.setDefaultFieldValue();
			ResultSet result=con.selectQuery("select max(field_id) as mcid from list_field");
			if(result.next()) this.field_id=Integer.parseInt(result.getString("mcid"));
			//this.field_position="method";
        }catch(Exception e){ System.out.println(e);}
		}
	@Override public void exitFormalParameter(javaParser.FormalParameterContext ctx) { }
	//--------------------------------------
	@Override public void enterLastFormalParameter(javaParser.LastFormalParameterContext ctx)
	{
		System.out.println("enterLastFormalParameter"+ctx.getText());
	}
	@Override public void exitLastFormalParameter(javaParser.LastFormalParameterContext ctx) { }
	//------------------------------
	@Override public void enterElementValuePairs(javaParser.ElementValuePairsContext ctx) 
	{
		System.out.println("enterElementValuePairs"+ctx.getText());
	}
	@Override public void exitElementValuePairs(javaParser.ElementValuePairsContext ctx) { }
	//-----------------------------------
	@Override public void enterElementValuePair(javaParser.ElementValuePairContext ctx) 
	{
		System.out.println("enterElementValuePair"+ctx.getText());
	}
	@Override public void exitElementValuePair(javaParser.ElementValuePairContext ctx) { }
	//------------------------------------
	@Override public void enterElementValue(javaParser.ElementValueContext ctx) 
	{
		System.out.println("enterElementValue"+ctx.getText());
	}
	@Override public void exitElementValue(javaParser.ElementValueContext ctx) { }
	//---------------------------------
	@Override public void enterElementValueArrayInitializer(javaParser.ElementValueArrayInitializerContext ctx) 
	{ 
		System.out.println("enterElementValueArrayInitializer"+ctx.getText());
	}
	@Override public void exitElementValueArrayInitializer(javaParser.ElementValueArrayInitializerContext ctx) { }
	//-------------------------------------------------------
	@Override public void enterDefaultValue(javaParser.DefaultValueContext ctx)
	{
		System.out.println("enterDefaultValue"+ctx.getText());
    }
	@Override public void exitDefaultValue(javaParser.DefaultValueContext ctx) { }
	//--------------------------
	@Override public void enterLocalVariableDeclarationStatement(javaParser.LocalVariableDeclarationStatementContext ctx)
	{ 
		System.out.println("enterLocalVariableDeclarationStatement"+ctx.getText());
	}
	@Override public void exitLocalVariableDeclarationStatement(javaParser.LocalVariableDeclarationStatementContext ctx) { }
	//---------------------------------------
	@Override public void enterLocalVariableDeclaration(javaParser.LocalVariableDeclarationContext ctx) 
	{
		System.out.println("enterLocalVariableDeclaration"+ctx.variableDeclarators().getText());
		//this.enterVariableDeclaratorId(ctx.variableDeclarators());
		try
		{
		String type_field=ctx.typeType().getText().toLowerCase();
		String name_field=ctx.getChild(1).getText().toLowerCase();
		String value_field="";
		String[] alist;
		alist=name_field.split("=");
		if(alist.length > 0) name_field=alist[0];
		if(alist.length > 1) value_field=alist[1];
		this.method_field.add(name_field);
		String[] args = new String[7];
		MysqlCon con=MysqlCon.getInstance(args);
		boolean insert_res=con.insertQuery("insert into list_field"
				+ "(field_class_id,field_method_id,field_visibility,field_is_static,field_is_final,field_name,field_type,field_value,field_group) "
				+ "values("+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+","+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+",'private',false,false,'"+name_field+"','"+type_field+"','"+value_field+"',1)");	
		ResultSet result=con.selectQuery("select max(field_id) as mcid from list_field");
		if(result.next()) this.field_id=Integer.parseInt(result.getString("mcid"));
		}catch(Exception e){ System.out.println(e);}
	}
	@Override public void exitLocalVariableDeclaration(javaParser.LocalVariableDeclarationContext ctx) { }
	//------------------------------------
//////////////////////////////////////OTHER
	@Override public void enterModifier(javaParser.ModifierContext ctx)
	{ 
		System.out.println("enterModifier"+ctx.getText());
		//save modifier of fileds
		String field_modifier=ctx.getText().toLowerCase();		
		switch(field_modifier)
		{
		case "public":
		case "private": 
		case "protected": this.field_visibility=field_modifier; break;
		case "static": this.field_is_static=true;  break;
		case "final": this.field_is_final=true;  break;
		default: System.out.println("NOTEEEEEEEEEEEEEEEEEEE:enterModifier:"+field_modifier);
		}	
		
	}
	@Override public void exitModifier(javaParser.ModifierContext ctx) { }
	//-----------------------------------
	@Override public void enterVariableModifier(javaParser.VariableModifierContext ctx) 
	{
		System.out.println("enterVariableModifier"+ctx.getText());
	}
	@Override public void exitVariableModifier(javaParser.VariableModifierContext ctx) { }
	//--------------------------------
	@Override public void enterTypeParameters(javaParser.TypeParametersContext ctx) 
	{ 
		System.out.println("enterTypeParameters"+ctx.getText());
	}
	@Override public void exitTypeParameters(javaParser.TypeParametersContext ctx) { }
	//---------------------------------
	@Override public void enterTypeParameter(javaParser.TypeParameterContext ctx)
	{ 
		System.out.println("enterTypeParameter"+ctx.getText());
	}
	@Override public void exitTypeParameter(javaParser.TypeParameterContext ctx) { }
	//------------------------------------------
	@Override public void enterTypeBound(javaParser.TypeBoundContext ctx) 
	{ 
		System.out.println("enterTypeBound"+ctx.getText());
	}
	@Override public void exitTypeBound(javaParser.TypeBoundContext ctx) { }
	//---------------------------------------
	@Override public void enterEnumDeclaration(javaParser.EnumDeclarationContext ctx) 
	{
		System.out.println("enterEnumDeclaration"+ctx.getText());
	}
	@Override public void exitEnumDeclaration(javaParser.EnumDeclarationContext ctx) { }
	//---------------------------------------
	@Override public void enterEnumConstants(javaParser.EnumConstantsContext ctx)
	{ 
		System.out.println("enterEnumConstant(s)"+ctx.getText());
	}
	@Override public void exitEnumConstants(javaParser.EnumConstantsContext ctx) { }
	//-------------------------------------
	@Override public void enterEnumConstant(javaParser.EnumConstantContext ctx) 
	{
		System.out.println("enterEnumConstant"+ctx.getText());
	}
	@Override public void exitEnumConstant(javaParser.EnumConstantContext ctx) { }
	//-------------------------------------
	@Override public void enterEnumBodyDeclarations(javaParser.EnumBodyDeclarationsContext ctx) 
	{
		System.out.println("enterEnumBodyDeclarations"+ctx.getText());
	}
	@Override public void exitEnumBodyDeclarations(javaParser.EnumBodyDeclarationsContext ctx) { }
	//---------------------------------------
	@Override public void enterEnumConstantName(javaParser.EnumConstantNameContext ctx)
	{
		System.out.println("enterEnumConstantName"+ctx.getText());
	}
	@Override public void exitEnumConstantName(javaParser.EnumConstantNameContext ctx) { }
	//-----------------------------------
	@Override public void enterTypeType(javaParser.TypeTypeContext ctx)
	{
		System.out.println("ententerTypeTypeerEnumBodyDeclarations"+ctx.getText());
	}
	@Override public void exitTypeType(javaParser.TypeTypeContext ctx) { }
	//----------------------------------------
	@Override public void enterTypeList(javaParser.TypeListContext ctx)
	{ 
		System.out.println("enterTypeList"+ctx.getText());
	}
	@Override public void exitTypeList(javaParser.TypeListContext ctx) { }
	//-----------------------------------------
	@Override public void enterConstDeclaration(javaParser.ConstDeclarationContext ctx) 
	{
		System.out.println("enterConstDeclaration"+ctx.getText());
	}
	@Override public void exitConstDeclaration(javaParser.ConstDeclarationContext ctx) { }
	//----------------------------------------------
	@Override public void enterConstantDeclarator(javaParser.ConstantDeclaratorContext ctx) 
	{
		System.out.println("enterConstantDeclarator"+ctx.getText());
	}
	@Override public void exitConstantDeclarator(javaParser.ConstantDeclaratorContext ctx) { }
	//------------------------------------------
	@Override public void enterLiteral(javaParser.LiteralContext ctx)
	{
		System.out.println("enterLiteral"+ctx.getText());
	}
	@Override public void exitLiteral(javaParser.LiteralContext ctx) { }
	//-------------------------------------
	@Override public void enterQualifiedName(javaParser.QualifiedNameContext ctx)
	{ 
		System.out.println("enterQualifiedName"+ctx.getText());
	}
	@Override public void exitQualifiedName(javaParser.QualifiedNameContext ctx) { }
	//--------------------------------------
	@Override public void enterCatchClause(javaParser.CatchClauseContext ctx)
	{
		System.out.println("enterCatchClause"+ctx.getText());
	}
	@Override public void exitCatchClause(javaParser.CatchClauseContext ctx) { }
	//------------------------------------
	@Override public void enterCatchType(javaParser.CatchTypeContext ctx)
	{
		System.out.println("enterCatchType"+ctx.getText());
	}
	@Override public void exitCatchType(javaParser.CatchTypeContext ctx) { }
	//-------------------------------------
	@Override public void enterPrimary(javaParser.PrimaryContext ctx) 
	{
		System.out.println("enterPrimary"+ctx.getText());
	}
	@Override public void exitPrimary(javaParser.PrimaryContext ctx) { }
	//-----------------------------------
	@Override public void enterArguments(javaParser.ArgumentsContext ctx)
	{ 
		System.out.println("enterArguments"+ctx.getText());
	}
	@Override public void exitArguments(javaParser.ArgumentsContext ctx) { }
//------------------------------
//---------------------------------
	@Override public void enterNonWildcardTypeArguments(javaParser.NonWildcardTypeArgumentsContext ctx)
	{
		System.out.println("enterNonWildcardTypeArguments"+ctx.getText());
	}
	@Override public void exitNonWildcardTypeArguments(javaParser.NonWildcardTypeArgumentsContext ctx) { }
	//------------------------------------
	@Override public void enterTypeArgumentsOrDiamond(javaParser.TypeArgumentsOrDiamondContext ctx) 
	{
		System.out.println("enterTypeArgumentsOrDiamond"+ctx.getText());
	}
	@Override public void exitTypeArgumentsOrDiamond(javaParser.TypeArgumentsOrDiamondContext ctx) { }
	//-----------------------------------
	@Override public void enterNonWildcardTypeArgumentsOrDiamond(javaParser.NonWildcardTypeArgumentsOrDiamondContext ctx)
	{
		System.out.println("enterNonWildcardTypeArgumentsOrDiamond"+ctx.getText());
	}
	@Override public void exitNonWildcardTypeArgumentsOrDiamond(javaParser.NonWildcardTypeArgumentsOrDiamondContext ctx) { }
	//----------------------------------
	@Override public void enterSuperSuffix(javaParser.SuperSuffixContext ctx) 
	{
		System.out.println("enterSuperSuffix"+ctx.getText());
	}
	@Override public void exitSuperSuffix(javaParser.SuperSuffixContext ctx) { }
	//----------------------------------
	@Override public void enterExplicitGenericInvocationSuffix(javaParser.ExplicitGenericInvocationSuffixContext ctx) 
	{
		System.out.println("enterExplicitGenericInvocationSuffix"+ctx.getText());
	}
	@Override public void exitExplicitGenericInvocationSuffix(javaParser.ExplicitGenericInvocationSuffixContext ctx) { }
//-----------------------------------
	@Override public void enterEveryRule(ParserRuleContext ctx) 
	{
		System.out.println("enterEveryRule"+ctx.getText());
	}
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	//-------------------------------
	@Override public void visitTerminal(TerminalNode node) 
	{
		System.out.println("visitTerminal"+node.getText());
		if(node.getText().equals((String) "="))
		{
			String field_name=node.getParent().getChild(0).getText().toLowerCase();
			String field_value=node.getParent().getChild(2).getText().toLowerCase();
			//value??????????
			String[] args = new String[7];
			MysqlCon con=MysqlCon.getInstance(args);
			String where="";
			//if(this.field_position.equals((String) "class"))
			//{
			//	 where="  field_class_id="+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+" and field_method_id=0";
			//}
			//else if(this.field_position.equals((String) "method"))
			//{
			
			if(this.method_field.contains(field_name)) //check if this field is in the method (or in class)
				where="  field_class_id="+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+" and field_method_id="+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+"";
			else 
				where="  field_class_id="+(this.nested_class_id > 0 ? this.nested_class_id : this.class_id)+" and field_method_id=0";
			//}
			if(where!="")
			{
				boolean update_res=con.insertQuery("update list_field set field_value='"+field_value+"' where "+where+" and field_name='"+field_name+"' ");	
				//this.field_position="";
			}
			System.out.println("wh"+(this.nested_method_id > 0 ? this.nested_method_id : this.method_id)+"");
			System.out.println("whereeeeeeeeeee"+"update list_field set field_value='"+field_value+"' where "+where+" and field_name='"+field_name+"' ");
			
		}
	}
	@Override public void visitErrorNode(ErrorNode node) 
	{ 
		System.out.println("visitErrorNode"+node.getText());
	}
//-------------------------------
////////////////////////////////////////////Other function
	public void setDefaultValueAtrribute()
	{
		this.is_abstract=false;
		this.is_final=false;
		this.is_static=false;
		this.visibility="public";
		this.is_constructor=false;
	}	
	public void setDefaultFieldValue()
	{
		this.field_is_static=false;
		this.field_is_final=false;
		this.field_visibility="public";
	}

}
