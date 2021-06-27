/**
 * @(#) ReplaceMethodVisitor.java
 */
package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReplaceMethodVisitor extends ASTVisitor {
   private ProgramElement curProgElem;
   private String newMethodName;

   private ASTRewrite rewrite;
   private AST astCUnit;

   private MethodDeclaration methodToBeRemoved;
   private TypeDeclaration typeDecl;

   public ReplaceMethodVisitor(AST astCUnit, ASTRewrite rewrite) {
      this.astCUnit = astCUnit;
      this.rewrite = rewrite;
   }

   public ReplaceMethodVisitor(ProgramElement curProgElem, String newMethodName) {
      this.curProgElem = curProgElem;
      this.newMethodName = newMethodName;
   }

   @Override
   public boolean visit(TypeDeclaration node) {
      typeDecl = node;
      return super.visit(node);
   }

   public boolean visit(MethodDeclaration node) {
      if (checkMethod(node)) {
         this.methodToBeRemoved = node;
         addNewMethod(node);
      }
      return true;
   }

   @Override
   public void endVisit(TypeDeclaration typeDecl) {
      ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
      lrw.remove(methodToBeRemoved, null);
   }

   private boolean checkMethod(MethodDeclaration md) {
      boolean check1 = this.curProgElem.getMethodName().equals(md.getName().getFullyQualifiedName());
      boolean check2 = this.curProgElem.getParameterSize().equals(md.parameters().size());
      if (check1 && check2) {
         return true;
      }
      return false;
   }

   @SuppressWarnings("unchecked")
   void addNewMethod(MethodDeclaration node) {
      Type curRetType = node.getReturnType2();
      List<?> curBodyStmts = node.getBody().statements();
      List<?> curModifiers = node.modifiers();
      List<?> curParameters = node.parameters();

      MethodDeclaration newMethodDecl = typeDecl.getAST().newMethodDeclaration();
      // * modifier
      newMethodDecl.setName(astCUnit.newSimpleName(this.newMethodName));
      for (Object m : curModifiers) {
         if (m instanceof Modifier) {
            Modifier mod = (Modifier) m;
            Modifier newMod = astCUnit.newModifier(mod.getKeyword());
            newMethodDecl.modifiers().add(newMod);
         }
      }
      // * return type
      if (curRetType.isPrimitiveType()) {
         PrimitiveType pt = (PrimitiveType) curRetType;
         newMethodDecl.setReturnType2(astCUnit.newPrimitiveType(pt.getPrimitiveTypeCode()));
      }
      // * parameters
      for (Object o : curParameters) {
         if (o instanceof SingleVariableDeclaration) {
            SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
            SingleVariableDeclaration newSVD = astCUnit.newSingleVariableDeclaration();
            Type type = svd.getType();
            if (type.isPrimitiveType()) {
               PrimitiveType pt = (PrimitiveType) type;
               newSVD.setType(astCUnit.newPrimitiveType(pt.getPrimitiveTypeCode()));
            }
            newSVD.setName(astCUnit.newSimpleName(svd.getName().getIdentifier()));
            newMethodDecl.parameters().add(newSVD);
         }
      }
      // * body
      Block block = astCUnit.newBlock();
      ListRewrite listRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
      for (Object stmt : curBodyStmts) {
         listRewrite.insertLast((ASTNode) stmt, null);
      }
      newMethodDecl.setBody(block);

      ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
      lrw.insertAfter(newMethodDecl, methodToBeRemoved, null);
   }

   public void setAST(AST ast) {
      this.astCUnit = ast;
   }

   public void ASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
}
