/**
 * @(#) DeclarationVisitor.java
 */
package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.MethodElement;
import model.ProgElementModelProvider;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DeclarationVisitor extends ASTVisitor {

   private String pkgName;
   private String className;
   private String methodName;
   private ProgramElement pkgElem;
   private ProgramElement classElem;
   private MethodElement methodElem;

   public DeclarationVisitor() {
   }

   @Override
   public boolean visit(PackageDeclaration pkgDecl) {
      this.pkgName = pkgDecl.getName().getFullyQualifiedName();
      this.pkgElem = new ProgramElement(pkgName);
      ProgramElement prePkg = ProgElementModelProvider.INSTANCE.addProgramElement(this.pkgElem);
      if (prePkg != null) {
         this.pkgElem = prePkg;
      }
      return super.visit(pkgDecl);
   }

   @Override
   public boolean visit(TypeDeclaration typeDecl) {
      this.className = typeDecl.getName().getIdentifier();
      this.classElem = new ProgramElement(className, this.pkgElem);
      this.pkgElem.add(classElem);
      return super.visit(typeDecl);
   }

   @Override
   public boolean visit(MethodDeclaration methodDecl) {
      this.methodName = methodDecl.getName().getIdentifier();
      String className = methodDecl.resolveBinding().getDeclaringClass().getName();
      String pkgName = methodDecl.resolveBinding().getDeclaringClass().getPackage().getName();
      //Declaring the starting position here
      //Integer position= methodDecl.getStartPosition();
      //String startPos = position.toString();
      String startPos = ((Integer)methodDecl.getStartPosition()).toString();
    

      this.methodElem = new MethodElement(methodName, this.classElem);
      this.methodElem.setParameters(methodDecl.parameters());
      this.methodElem.setClassName(className);
      this.methodElem.setPkgName(pkgName);
      this.methodElem.setStartPos(startPos);//adding to the MethodElement
      this.classElem.add(methodElem);
      //this.methodElem

      return super.visit(methodDecl);
   }
}
