/**
 * @file HandlerSearchMethodCaller.java
 */
package handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.IMethod;

import analysis.ProjectAnalyzerDefUse;
import analysis.ProjectAnalyzerSearchMethodCallers;
import data.DefUseModel;
import view.SimpleViewer;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;

/**
 * @since JavaSE-1.8
 */
public class HandlerSearchMethodCaller {
   SimpleViewer viewer = null;

   @Execute
   public void execute(EPartService service) {
      MPart part = service.findPart(SimpleViewer.VIEWID);
      if (part != null) {
         if (part.getObject() instanceof SimpleViewer) {
            viewer = (SimpleViewer) part.getObject();
            viewer.reset();

            try {
               ProjectAnalyzerSearchMethodCallers analyzer = new ProjectAnalyzerSearchMethodCallers(viewer.getSearchText().getText());
               analyzer.analyze();
               
               //ProjectAnalyzerDefUse analyzer2 = new ProjectAnalyzerDefUse(viewer.getSearchText().getText());
               //analyzer2.analyze();
               
               List<Map<IMethod, IMethod[]>> calleeCallers = analyzer.getListCallers();
               List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();
               
               viewer.display(calleeCallers,analysisDataList);
               
               
               

               //displayDefUsedView(viewer, analysisDataList);
            } catch (CoreException e) {
               e.printStackTrace();
            }
         }
      }
   }
   
   public static void displayDefUsedView(String name, StyledText viewer, List<Map<IVariableBinding, DefUseModel>> analysisDataList) {
	     int counter = 1;
	      System.out.println("debug...2.."+counter);
	      for (Map<IVariableBinding, DefUseModel> iMap : analysisDataList) {
	         for (Entry<IVariableBinding, DefUseModel> entry : iMap.entrySet()) {
	            IVariableBinding iBinding = entry.getKey();
	            DefUseModel iVariableAnal = entry.getValue();
	            CompilationUnit cUnit = iVariableAnal.getCompilationUnit();
	            VariableDeclarationStatement varDeclStmt = iVariableAnal.getVarDeclStmt();
	            VariableDeclarationFragment varDecl = iVariableAnal.getVarDeclFrgt();
	            System.out.println("iBinding.getDeclaringMethod()....."+iBinding.getDeclaringMethod().getName());
	            System.out.println("name.."+name);
	            if(name.equals(iBinding.getDeclaringMethod().getName())) {
System.out.println("ffff");
	            	 viewer.append("[" + (counter++) + "] ABOUT VARIABLES '" + varDecl.getName() + "'\n");
	 	            String method = "[METHOD] " + iBinding.getDeclaringMethod() + "\n";
	 	            viewer.append(method);
	 	            String stmt = "\t[DECLARE STMT] " + strTrim(varDeclStmt) + "\t [" + getLineNum(cUnit, varDeclStmt) + "]\n";
	 	            viewer.append(stmt);
	 	            String var = "\t[DECLARE VAR] " + varDecl.getName() + "\t [" + getLineNum(cUnit, varDecl) + "]\n";
	 	            viewer.append(var);

	 	            List<SimpleName> usedVars = iVariableAnal.getUsedVars();
	 	            for (SimpleName iSimpleName : usedVars) {

	 	               ASTNode parentNode = iSimpleName.getParent();
	 	               if (parentNode != null && parentNode instanceof Assignment) {
	 	                  String assign = "\t\t[ASSIGN VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
	 	                  viewer.append(assign);
	 	               } else {
	 	                  String use = "\t\t[USE VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
	 	                  viewer.append(use);
	 	               }
	 	            }
	            }
	           
	         }
	         System.out.println("debug...3.."+counter);
	      }
	   }
   
   public static String strTrim(Object o) {
	      return o.toString().trim();
	   }

	   public static int getLineNum(CompilationUnit compilationUnit, ASTNode node) {
	      return compilationUnit.getLineNumber(node.getStartPosition());
	   }
}