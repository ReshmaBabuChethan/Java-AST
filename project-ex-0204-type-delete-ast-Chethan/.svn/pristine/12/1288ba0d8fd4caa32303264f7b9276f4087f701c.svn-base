/**
 * @(#) ModelProvider.java
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since J2SE-1.8
 */
public enum ModelProvider {
   INSTANCE;
   private List<ProgramElement> progElements = new ArrayList<ProgramElement>();

   public void addProgramElements(String pkgName, String className, String methodName, boolean isRetVoid, int parmSize) {
      progElements.add(new ProgramElement(pkgName, className, methodName, isRetVoid, parmSize));
   }

   public List<ProgramElement> getProgramElements() {
      return progElements;
   }

   public void clearProgramElements() {
      progElements.clear();
   }
}
