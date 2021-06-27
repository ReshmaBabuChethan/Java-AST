/**
 * @(#) ReplaceClassVisitor.java
 */
package visitor.rewrite;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReplaceClassVisitor extends ASTVisitor {
   private ProgramElement curProgElem;
   private String newClassName;
   private ICompilationUnit iUnit;
   private ASTRewrite rewrite;
   private CompilationUnit cUnit;

   public ReplaceClassVisitor(ProgramElement curProgElem, String newClassName) {
      this.curProgElem = curProgElem;
      this.newClassName = newClassName;
   }

   @Override
   public boolean visit(TypeDeclaration node) {
      if (node.getName().getIdentifier().equals(curProgElem.getClassName()) == false) {
         return true;
      }
      // Description of the change
      SimpleName oldName = node.getName();
      SimpleName newName = cUnit.getAST().newSimpleName(newClassName);

      try {
         // Update type java element accordingly
         IType oldType = iUnit.getType(oldName.getFullyQualifiedName());
         oldType.rename(newClassName, true, null);
      } catch (JavaModelException e) {
         e.printStackTrace();
      }
      rewrite.replace(oldName, newName, null);
      return super.visit(node);
   }

   public void setICompilationUnit(ICompilationUnit iUnit) {
      this.iUnit = iUnit;
   }

   public void setRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }

   public void setCompilationUnit(CompilationUnit cUnit) {
      this.cUnit = cUnit;
   }
}
