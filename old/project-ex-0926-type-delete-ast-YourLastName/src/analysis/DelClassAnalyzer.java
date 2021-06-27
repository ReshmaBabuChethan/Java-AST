/**
 * @(#) DelClassAnalyzer.java
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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;

import model.ProgramElement;
import util.ParseUtil;

/**
 * @since J2SE-1.8
 */
public class DelClassAnalyzer {
   private ProgramElement curProgElem;

   public DelClassAnalyzer(ProgramElement newProgName) {
      this.curProgElem = newProgName;

      // Get all projects in the workspace.
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
      // Check if we have a Java project.
      if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
         return;
      }
      IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages = javaProject.getPackageFragments();

      for (IPackageFragment iPackage : packages) {
         // Package fragments include all packages in the classpath.
         // We will only look at the package from the source folder,
         // indicating this root only contains source files.
         // K_BINARY would include also included JARS, e.g. rt.jar.
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
               iPackage.getCompilationUnits().length >= 1 && //
               iPackage.getElementName().equals(curProgElem.getPkgName())) {
            deleteClass(iPackage);
         }
      }
   }

   void deleteClass(IPackageFragment iPackage) throws JavaModelException, MalformedTreeException, BadLocationException {
      for (ICompilationUnit iCUnit : iPackage.getCompilationUnits()) {
         String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCUnit.getElementName());
         if (nameICUnit.equals(this.curProgElem.getClassName())) {
            iCUnit.delete(true, null);
            return;
         }
      }
   }
}