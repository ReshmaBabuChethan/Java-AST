/**
 * @(#) ReplaceMethodNameAnalyzer.java
 */
package analysis.replace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import util.ParseUtil;
import visitor.rewrite.ReplaceMethodVisitor;

/**
 * @since J2SE-1.8
 */
public class ReplaceMethodNameAnalyzer {
   private ProgramElement curProgElem;
   private String newMethodName;

   public ReplaceMethodNameAnalyzer(ProgramElement curProgName, String newMethodName) {
      this.curProgElem = curProgName;
      this.newMethodName = newMethodName;

      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         try {
            analyzeJavaProject(project);
         } catch (MalformedTreeException | BadLocationException | CoreException e) {
            e.printStackTrace();
         }
      }
   }

   void analyzeJavaProject(IProject project) throws CoreException, JavaModelException, MalformedTreeException, BadLocationException {
      if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
         return;
      }
      IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages = javaProject.getPackageFragments();
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
               iPackage.getCompilationUnits().length >= 1 && //
               iPackage.getElementName().equals(curProgElem.getPkgName())) {
            replaceMethodName(iPackage);
         }
      }
   }

   void replaceMethodName(IPackageFragment iPackage) throws JavaModelException, MalformedTreeException, BadLocationException {
      for (ICompilationUnit iCUnit : iPackage.getCompilationUnits()) {
         String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCUnit.getElementName());
         if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
            continue;
         }
         ICompilationUnit workingCopy = iCUnit.getWorkingCopy(null);
         CompilationUnit cUnit = ParseUtil.parse(workingCopy);
         ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());
         ReplaceMethodVisitor v = new ReplaceMethodVisitor(curProgElem, newMethodName);
         v.setAST(cUnit.getAST());
         v.ASTRewrite(rewrite);
         cUnit.accept(v);
         TextEdit edits = null;
         edits = rewrite.rewriteAST(); // Compute the edits
         workingCopy.applyTextEdit(edits, null); // Apply the edits.
         workingCopy.commitWorkingCopy(false, null); // Save the changes.
      }
   }
}