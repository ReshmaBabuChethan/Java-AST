/**
 * @(#) ReturnTypeLabelProvider.java
 */
package model.labelprovider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReturnTypeLabelProvider extends ColumnLabelProvider {
   @Override
   public String getText(Object element) {
      ProgramElement p = (ProgramElement) element;
      return p.isReturnVoid() ? "Yes" : "No";
   }
}
