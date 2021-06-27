/**
 * @(#) ProgElementModelProvider.java
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since J2SE-1.8
 */
public enum ProgElementModelProvider {
   INSTANCE;

   private List<ProgramElement> progElements = new ArrayList<ProgramElement>();

   public List<ProgramElement> getProgElements() {
      return progElements;
   }

   public ProgramElement addProgramElement(ProgramElement pkg) {
      for (ProgramElement iElem : progElements) {
         if (iElem.getName().equals(pkg.getName())) {
            return iElem;
         }
      }
      this.progElements.add(pkg);
      return null;
   }

   public void clearProgramElements() {
      this.progElements.clear();
   }
}
