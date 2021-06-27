/**
 * @(#) AddProgElemAnalyzer.java
 */
package analysis;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class AddProgElemAnalyzer {
   private ProgramElement newProgElem;

   public AddProgElemAnalyzer(ProgramElement newProgName) {
      this.newProgElem = newProgName;

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
      IFolder folder = project.getFolder("src");
      IPackageFragmentRoot srcFolder = javaProject.getPackageFragmentRoot(folder);
      IPackageFragment fragment = srcFolder.createPackageFragment(this.newProgElem.getPkgName(), true, null);
      createNewCUnit(fragment);
   }

   @SuppressWarnings({ "unchecked" })
   void createNewCUnit(IPackageFragment iPackage) throws JavaModelException, MalformedTreeException, BadLocationException {
      AST ast = AST.newAST(AST.JLS15, false);
      CompilationUnit cUnit = ast.newCompilationUnit();
      // package
      PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
      packageDeclaration.setName(ast.newSimpleName(this.newProgElem.getPkgName()));
      cUnit.setPackage(packageDeclaration);
      // type
      TypeDeclaration typeDecl = ast.newTypeDeclaration();
      typeDecl.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
      typeDecl.setName(ast.newSimpleName(this.newProgElem.getClassName()));
      // method
      MethodDeclaration methodDecl = ast.newMethodDeclaration();
      methodDecl.setConstructor(false);
      methodDecl.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
      methodDecl.setName(ast.newSimpleName(this.newProgElem.getMethodName()));
      methodDecl.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
      Block newBlock = ast.newBlock();
      methodDecl.setBody(newBlock);
      typeDecl.bodyDeclarations().add(methodDecl);
      cUnit.types().add(typeDecl);
      // create
      iPackage.createCompilationUnit(this.newProgElem.getClassName() + ".java", cUnit.toString(), true, null);
   }
}