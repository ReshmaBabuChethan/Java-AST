/**
 * @(#) DelMethodVisitor.java
 */
package visitor.rewrite;

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
      if (name.equals(progElemToBeRemoved.getMethodName())) {

         MessageDialog.openInformation(shell, "Title", "" + node.getName());

         this.methodToBeRemoved = node;
         return false;
      }
      return true;
   }
}
