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
public class DelPublicMethodVisitor extends ASTVisitor {
   private ProgramElement progElemToBeRemoved;
   private MethodDeclaration methodToBeRemoved;
   private ASTRewrite rewrite;
   boolean publicChecker = false;

   @Inject
   private Shell shell;

   public DelPublicMethodVisitor(ProgramElement curProgElem) {
      this.progElemToBeRemoved = curProgElem;
      this.publicChecker = true;
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
   
   @Override
   public void endVisit(TypeDeclaration typeDecl) {
	   if(publicChecker)
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
    	   if((methodModifiers & Modifier.PUBLIC)!= 0) {
    		  MessageDialog.openInformation(shell, "Title", "" + node.getName());
    	  this.methodToBeRemoved = node;
   	   	 
         return false;
      }
    	  else {
    	  MessageDialog.openInformation(shell, "Title", 
    				 "Can not delete the selected method " + progElemToBeRemoved.getMethodName()+ " because it is not a public method");
    	  publicChecker=false;
    	  
    	  }
      }
      return true;
      
   }
   
   //passing checker for validation
   public boolean publicChecker() {
		return publicChecker;
   }

   
}


