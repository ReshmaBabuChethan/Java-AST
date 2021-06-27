/*package visitor.rewrite;

import java.lang.reflect.Modifier;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
/*public class DelMethodVisitor extends ASTVisitor {
   private ProgramElement progElemToBeRemoved;
   private MethodDeclaration methodToBeRemoved;
   private ASTRewrite rewrite;

   @Inject
   private Shell shell;

   public DelMethodVisitor(ProgramElement curProgElem) {
      this.progElemToBeRemoved = curProgElem;
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }

   @Override
   public void endVisit(TypeDeclaration typeDecl) {
      ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
      lrw.remove(methodToBeRemoved, null);
   }

   public boolean visit(MethodDeclaration node) {
      String name = node.getName().getFullyQualifiedName();
   	  int methodModifiers = node.getModifiers();
	  boolean isPrivate = (methodModifiers & Modifier.PRIVATE)!= 0;	 
      if (name.equals(progElemToBeRemoved.getMethodName())) {   	
   	   	 if(isPrivate == false) {
   	   		 MessageDialog.openInformation(shell, "Title", "Can not delete the selected method " + node.getName()+ " because it is not a private method.");

   	   	 }
   	   	 else {
   	   		 MessageDialog.openInformation(shell, "Title", "" + node.getName());
   	   		 this.methodToBeRemoved = node;
   	   	 }
         return false;
      }
      return true;
   }
   
}*/


/**
 * @(#) DelMethodVisitor.java
 */
package visitor.rewrite;

import java.lang.reflect.Modifier;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DelMethodVisitor extends ASTVisitor {
   private ProgramElement progElemToBeRemoved;
   private MethodDeclaration methodToBeRemoved;
   private ASTRewrite rewrite;
   boolean privateChecker = false;

   @Inject
   private Shell shell;

   public DelMethodVisitor(ProgramElement curProgElem) {
      this.progElemToBeRemoved = curProgElem;
      this.privateChecker = true;
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
   
   @Override
   public void endVisit(TypeDeclaration typeDecl) {
	   if(privateChecker)
	   {
		   ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		   lrw.remove(methodToBeRemoved, null);
	   }
	 
   }

   public boolean visit(MethodDeclaration node) {
      String name = node.getName().getFullyQualifiedName();

      if (name.equals(progElemToBeRemoved.getMethodName())) {   	
       	  int methodModifiers = node.getModifiers();
    	   if((methodModifiers & Modifier.PRIVATE)!= 0) {
    		  MessageDialog.openInformation(shell, "Title", "" + node.getName());
    	  this.methodToBeRemoved = node;
   	   	 
         return false;
      }
    	  else {
    	  MessageDialog.openInformation(shell, "Title", 
    				 "Can not delete the selected method " + progElemToBeRemoved.getMethodName()+ " because it is not a private method");
    	  privateChecker=false;
    	  
    	  }
      }
      return true;
      
   }
   
   //passing checker for validation
   public boolean privateChecker() {
		return privateChecker;
   }


   
}


