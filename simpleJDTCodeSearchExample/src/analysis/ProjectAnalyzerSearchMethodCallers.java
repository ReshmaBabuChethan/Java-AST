/**
 * @file ProjectAnalyzerSearchMethodCallers.java
 */
package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import util.UtilAST;
import visitor.ASTVisitorSearchMethodCallers;

/**
 * @since JavaSE-1.8
 */
public class ProjectAnalyzerSearchMethodCallers {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   IProject[] projects;
   private List<Map<IMethod, IMethod[]>> listCallers;
   private String callee;

   public ProjectAnalyzerSearchMethodCallers() {
      listCallers = new ArrayList<>();
   }

   public ProjectAnalyzerSearchMethodCallers(String callee) {
      this();
      this.callee = callee;
   }

   public void analyze() throws CoreException {
      // =============================================================
      // 1st step: Project
      // =============================================================
      projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check if we have a Java project.
            continue;
         }
         analyzePackages(JavaCore.create(project).getPackageFragments());
      }
   }

   protected void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
      // =============================================================
      // 2nd step: Packages
      // =============================================================
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            analyzeCompilationUnit(iPackage.getCompilationUnits(), packages);
         }
      }
   }

   private void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits, IPackageFragment[] packages) throws JavaModelException {
      // =============================================================
      // 3rd step: ICompilationUnits
      // =============================================================
      ASTVisitorSearchMethodCallers visitor = null;
      for (ICompilationUnit iUnit : iCompilationUnits) {
         CompilationUnit compilationUnit = UtilAST.parse(iUnit);
         visitor = new ASTVisitorSearchMethodCallers(this.callee, packages);
         compilationUnit.accept(visitor);

         Map<IMethod, IMethod[]> dataCallers = visitor.getDataCallers();
         listCallers.add(dataCallers);
      }
   }

   public List<Map<IMethod, IMethod[]>> getListCallers() {
      return listCallers;
   }
}