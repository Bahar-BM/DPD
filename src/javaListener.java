// Generated from java.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link javaParser}.
 */
public interface javaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link javaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(javaParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(javaParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(javaParser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(javaParser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(javaParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(javaParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(javaParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(javaParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(javaParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(javaParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceModifier(javaParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceModifier(javaParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(javaParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(javaParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(javaParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(javaParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(javaParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(javaParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(javaParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(javaParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void enterTypeBound(javaParser.TypeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void exitTypeBound(javaParser.TypeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(javaParser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(javaParser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstants(javaParser.EnumConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstants(javaParser.EnumConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstant(javaParser.EnumConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstant(javaParser.EnumConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterEnumBodyDeclarations(javaParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitEnumBodyDeclarations(javaParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(javaParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(javaParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(javaParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(javaParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(javaParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(javaParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(javaParser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(javaParser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(javaParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(javaParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(javaParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(javaParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(javaParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(javaParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericMethodDeclaration(javaParser.GenericMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericMethodDeclaration(javaParser.GenericMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(javaParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(javaParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericConstructorDeclaration(javaParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericConstructorDeclaration(javaParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(javaParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(javaParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBodyDeclaration(javaParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBodyDeclaration(javaParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberDeclaration(javaParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberDeclaration(javaParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstDeclaration(javaParser.ConstDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstDeclaration(javaParser.ConstDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConstantDeclarator(javaParser.ConstantDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConstantDeclarator(javaParser.ConstantDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(javaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(javaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericInterfaceMethodDeclaration(javaParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericInterfaceMethodDeclaration(javaParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(javaParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(javaParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(javaParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(javaParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(javaParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(javaParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(javaParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(javaParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(javaParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(javaParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enumConstantName}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstantName(javaParser.EnumConstantNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enumConstantName}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstantName(javaParser.EnumConstantNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(javaParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(javaParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(javaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(javaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(javaParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(javaParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(javaParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(javaParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(javaParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(javaParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(javaParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(javaParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(javaParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(javaParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(javaParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(javaParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(javaParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(javaParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(javaParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(javaParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(javaParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(javaParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void enterConstructorBody(javaParser.ConstructorBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void exitConstructorBody(javaParser.ConstructorBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(javaParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(javaParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(javaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(javaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(javaParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(javaParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationName}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationName(javaParser.AnnotationNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationName}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationName(javaParser.AnnotationNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairs(javaParser.ElementValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairs(javaParser.ElementValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(javaParser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(javaParser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(javaParser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(javaParser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(javaParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(javaParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeDeclaration(javaParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeDeclaration(javaParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeBody(javaParser.AnnotationTypeBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeBody(javaParser.AnnotationTypeBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementDeclaration(javaParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementDeclaration(javaParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementRest(javaParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementRest(javaParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodOrConstantRest(javaParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodOrConstantRest(javaParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodRest(javaParser.AnnotationMethodRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodRest(javaParser.AnnotationMethodRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationConstantRest(javaParser.AnnotationConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationConstantRest(javaParser.AnnotationConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(javaParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(javaParser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(javaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(javaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(javaParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(javaParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclarationStatement(javaParser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclarationStatement(javaParser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(javaParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(javaParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(javaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(javaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(javaParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(javaParser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#catchType}.
	 * @param ctx the parse tree
	 */
	void enterCatchType(javaParser.CatchTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#catchType}.
	 * @param ctx the parse tree
	 */
	void exitCatchType(javaParser.CatchTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(javaParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(javaParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void enterResourceSpecification(javaParser.ResourceSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void exitResourceSpecification(javaParser.ResourceSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#resources}.
	 * @param ctx the parse tree
	 */
	void enterResources(javaParser.ResourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#resources}.
	 * @param ctx the parse tree
	 */
	void exitResources(javaParser.ResourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(javaParser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(javaParser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(javaParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(javaParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(javaParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(javaParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(javaParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(javaParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(javaParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(javaParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForControl(javaParser.EnhancedForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForControl(javaParser.EnhancedForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(javaParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(javaParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(javaParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(javaParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(javaParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(javaParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(javaParser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(javaParser.StatementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(javaParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(javaParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(javaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(javaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(javaParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(javaParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(javaParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(javaParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(javaParser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(javaParser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void enterInnerCreator(javaParser.InnerCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void exitInnerCreator(javaParser.InnerCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreatorRest(javaParser.ArrayCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreatorRest(javaParser.ArrayCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(javaParser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(javaParser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocation(javaParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocation(javaParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArguments(javaParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArguments(javaParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentsOrDiamond(javaParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentsOrDiamond(javaParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArgumentsOrDiamond(javaParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArgumentsOrDiamond(javaParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(javaParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(javaParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocationSuffix(javaParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocationSuffix(javaParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link javaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(javaParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link javaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(javaParser.ArgumentsContext ctx);
}