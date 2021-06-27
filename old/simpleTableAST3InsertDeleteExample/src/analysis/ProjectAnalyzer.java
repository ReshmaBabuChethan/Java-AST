/**
 * @(#) ProjectAnalyzer.java
 */
package analysis;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import visitor.DeclarationVisitor;

/**
 * @since J2SE-1.8
 */
public class ProjectAnalyzer {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   private IProject[] projects;

   public ProjectAnalyzer() {
      // Get all projects in the workspace.
      projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
   }

   public void analyze() {
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
      if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) {
         return;
      }
      IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages = javaProject.getPackageFragments();
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            analyzeCompilationUnit(iPackage);
         }
      }
   }

   private void analyzeCompilationUnit(IPackageFragment iPackage) throws JavaModelException {
      for (ICompilationUnit iUnit : iPackage.getCompilationUnits()) {
         CompilationUnit compilationUnit = parse(iUnit);
         DeclarationVisitor declVisitor = new DeclarationVisitor();
         compilationUnit.accept(declVisitor);
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