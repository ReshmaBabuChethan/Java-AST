/**
 * @(#) ReplaceMethodCallVisitor.java
 */
package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReplaceMethodCallVisitor extends ASTVisitor {
   /*
   private static final String	PKGNAME_LOG			= "pkg";
   private static final String	CLASSNAME_LOG		= "Log";
   private static final String	METHODNAME_LOG		= "log";
   private static final String	CLASSNAME_PRINT	= "PrintStream";
   private static final String	METHODNAME_PRINT	= "println";
   */
   private ASTRewrite rewrite;
   private AST astCUnit;
   private ProgramElement curProgElem;
   private String newMethodName;

   public ReplaceMethodCallVisitor(ProgramElement curProgElem, String newMethodName) {
      this.curProgElem = curProgElem;
      this.newMethodName = newMethodName;
   }

   /*
   public boolean visit(TypeDeclaration node) {
   	String name = node.getName().getFullyQualifiedName();
   	if (name.equals(CLASSNAME_LOG)) {
   		this.updated = false;
   		return false;
   	} else {
   		return super.visit(node);
   	}
   }
   */

   @Override
   public boolean visit(ExpressionStatement node) {
      Expression expr = node.getExpression();
      if (expr instanceof MethodInvocation) {
         MethodInvocation miExpr = (MethodInvocation) expr;
         String identifier = miExpr.getName().getIdentifier();
         String declaringClass = miExpr.resolveMethodBinding().getDeclaringClass().getName();

         if (declaringClass.equals(this.curProgElem.getClassName()) && //
               identifier.endsWith(this.curProgElem.getMethodName())) {
            ExpressionStatement stmt = createNewExpStmt(miExpr);
            this.rewrite.replace(node, stmt, null);
         }
      }
      return super.visit(node);
   }

   ExpressionStatement createNewExpStmt(MethodInvocation miExpr) {
      MethodInvocation newMethodInvocation = this.astCUnit.newMethodInvocation();
      QualifiedName newQualifiedName = this.astCUnit.newQualifiedName( //
            this.astCUnit.newSimpleName(this.curProgElem.getPkgName()), //
            this.astCUnit.newSimpleName(this.curProgElem.getClassName()));
      newMethodInvocation.setExpression(newQualifiedName);
      newMethodInvocation.setName(this.astCUnit.newSimpleName(this.newMethodName));
      addArguments(miExpr, newMethodInvocation);
      return this.astCUnit.newExpressionStatement(newMethodInvocation);
   }

   @SuppressWarnings("unchecked")
   void addArguments(MethodInvocation miExpr, MethodInvocation newMethodInvocation) {
      List<Object> args = miExpr.arguments();
      for (Object o : args) {
         if (o instanceof SimpleName) {
            SimpleName n = (SimpleName) o;
            SimpleName arg = this.astCUnit.newSimpleName(n.getIdentifier());
            newMethodInvocation.arguments().add(arg);
         } else if (o instanceof InfixExpression) {
            InfixExpression iExpr = (InfixExpression) o;
            Operator op = iExpr.getOperator();
            Expression leftOp = iExpr.getLeftOperand();
            Expression rightOp = iExpr.getRightOperand();

            InfixExpression newInfixExpr = this.astCUnit.newInfixExpression();
            newInfixExpr.setOperator(op);
            if (rightOp instanceof SimpleName) {
               setSimpleName(rightOp, newInfixExpr, false);
            }
            if (rightOp instanceof StringLiteral) {
               setStringLiteral(rightOp, newInfixExpr, false);
            }
            if (leftOp instanceof SimpleName) {
               setSimpleName(leftOp, newInfixExpr, true);
            }
            if (leftOp instanceof StringLiteral) {
               setStringLiteral(leftOp, newInfixExpr, true);
            }
            newMethodInvocation.arguments().add(newInfixExpr);
         }
      }
   }

   void setSimpleName(Object operand, InfixExpression newInfixExpr, boolean isLeft) {
      SimpleName n = (SimpleName) operand;
      SimpleName newName = this.astCUnit.newSimpleName(n.getIdentifier());
      if (isLeft) {
         newInfixExpr.setLeftOperand(newName);
      } else {
         newInfixExpr.setRightOperand(newName);
      }
   }

   void setStringLiteral(Object operand, InfixExpression newInfixExpr, boolean isLeft) {
      StringLiteral lit = (StringLiteral) operand;
      StringLiteral newLit = this.astCUnit.newStringLiteral();
      newLit.setLiteralValue(lit.getLiteralValue());
      if (isLeft) {
         newInfixExpr.setLeftOperand(newLit);
      } else {
         newInfixExpr.setRightOperand(newLit);
      }
   }

   // public boolean isUpdated() {
   // return updated;
   // }

   public void setCompilationUnit(CompilationUnit cUnit) {
      this.astCUnit = cUnit.getAST();
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
}
