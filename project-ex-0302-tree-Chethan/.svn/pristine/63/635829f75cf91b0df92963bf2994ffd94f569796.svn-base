/**
 * @(#) MethodLabelProvider.java
 */
package model.provider;

import org.eclipse.jface.viewers.StyledString;

import model.progelement.MethodElement;

/**
 * @since J2SE-1.8
 */
public class LabelProviderStartPosition extends LabelProviderProgElem {

   @Override
   public StyledString getStyledText(Object element) {
      if (element instanceof MethodElement) {
         return new StyledString(((MethodElement) element).getStartPos());
      }
      return new StyledString(""); // super.getStyledText(element);
   }
}
