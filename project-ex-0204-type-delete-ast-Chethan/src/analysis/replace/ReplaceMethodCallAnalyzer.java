/**
 * @(#) ReplaceMethodCallAnalyzer.java
 */
package analysis.replace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import model.ProgramElement;
import visitor.rewrite.ReplaceMethodCallVisitor;

/**
 * @since J2SE-1.8
 */
public class ReplaceMethodCallAnalyzer {
   private ProgramElement curProgElem;
   private String newMethodName;

   public ReplaceMethodCallAnalyzer(ProgramElement curClassName, String newMethodName) {
      // Get all projects in the workspace.
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         try {
            analyzeJavaProject(project);
         } catch (CoreException e) {
            e.printStackTrace();
         }
      }
   }

   private void analyzeJavaProject(IProject project) throws CoreException, JavaModelException {
      // Check if we have a Java project.
      if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
         return;
      }
      IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages = javaProject.getPackageFragments();
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            replaceMethodCall(iPackage);
         }
      }
   }

   private void replaceMethodCall(IPackageFragment iPackage) throws JavaModelException {
      for (ICompilationUnit iCunit : iPackage.getCompilationUnits()) {
         ICompilationUnit workingCopy = iCunit.getWorkingCopy(null);
         CompilationUnit cUnit = parse(workingCopy);
         AST ast = cUnit.getAST();
         ASTRewrite rewrite = ASTRewrite.create(ast);

         ReplaceMethodCallVisitor repMethodVisitor = new ReplaceMethodCallVisitor(curProgElem, newMethodName);
         repMethodVisitor.setCompilationUnit(cUnit);
         repMethodVisitor.setASTRewrite(rewrite);

         cUnit.accept(repMethodVisitor);

         // Apply the edits.
         workingCopy.applyTextEdit(rewrite.rewriteAST(), new NullProgressMonitor());
         // Save the changes.
         workingCopy.commitWorkingCopy(false, new NullProgressMonitor());
      }
   }

   private static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS15);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }
}