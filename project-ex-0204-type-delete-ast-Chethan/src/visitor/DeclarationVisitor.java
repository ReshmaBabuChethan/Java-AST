/**
 * @(#) DeclarationVisitor.java
 */
package visitor;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.ModelProvider;

/**
 * @since J2SE-1.8
 */
public class DeclarationVisitor extends ASTVisitor {
   private String pkgName;
   private String className;
   private String methodName;

   public DeclarationVisitor() {
   }

   @Override
   public boolean visit(PackageDeclaration pkgDecl) {
      pkgName = pkgDecl.getName().getFullyQualifiedName();
      return super.visit(pkgDecl);
   }

   /**
    * A type declaration is the union of a class declaration and an interface declaration.
    */
   @Override
   public boolean visit(TypeDeclaration typeDecl) {
      className = typeDecl.getName().getIdentifier();
      return super.visit(typeDecl);
   }

   @Override
   public boolean visit(MethodDeclaration methodDecl) {
      methodName = methodDecl.getName().getIdentifier();
      int parmSize = methodDecl.parameters().size();
      
      int methodModifiers = methodDecl.getModifiers();
      boolean isPublic = (methodModifiers & Modifier.PUBLIC)!= 0;
      //System.out.println(methodDecl.getName() + ":" + isPublic);
  
      Type returnType = methodDecl.getReturnType2();
      boolean isRetVoid = false;
      if (returnType.isPrimitiveType()) {
         PrimitiveType pt = (PrimitiveType) returnType;
         if (pt.getPrimitiveTypeCode().equals(PrimitiveType.VOID)) {
            isRetVoid = true;
         }
      }
      ModelProvider.INSTANCE.addProgramElements(pkgName, className, methodName, isRetVoid, parmSize);
      return super.visit(methodDecl);
   }
   
}