/**
 * @(#) ParseUtil.java
 */
package util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * @since J2SE-1.8
 */
public class ParseUtil {
   /*    
    * Reads a ICompilationUnit and creates the AST DOM for manipulating the Java 
    * source file. Constant for indicating the AST API that handles JLS8. This API 
    * is capable of handling all constructs in the Java language as described in the 
    * Java Language Specification, Java SE 8 Edition (JLS8) as specified by JSR337. 
    * JLS8 is a superset of all earlier versions of the Java language, and the JLS8 
    * API can be used to manipulate programs written in all versions of the Java 
    * language up to and including Java SE 8 (aka JDK 1.8).
    */
   public static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS11);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   public static String getClassNameFromJavaFile(String javafileName) {
      int indexOfDot = javafileName.lastIndexOf('.');
      if (indexOfDot < 0) {
         return null;
      }
      return javafileName.substring(0, indexOfDot);
   }
}
