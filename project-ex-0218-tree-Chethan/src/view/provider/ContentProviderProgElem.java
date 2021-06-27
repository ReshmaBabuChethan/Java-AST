/**
 * @(#) ProgElemContentProvider.java
 */
package view.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;

import model.ProgramElement;
import view.Viewer;

/**
 * @since J2SE-1.8
 */
public class ContentProviderProgElem implements ITreeContentProvider {
   public void inputChanged(Viewer v, Object oldInput, Object newInput) {
   }

   @Override
   public void dispose() {
   }

   @Override
   public Object[] getElements(Object inputElement) {
      return (ProgramElement[]) inputElement;
   }

   @Override
   public Object[] getChildren(Object parentElement) {
      ProgramElement p = (ProgramElement) parentElement;
      return p.list();
   }

   @Override
   public Object getParent(Object element) {
      ProgramElement p = (ProgramElement) element;
      return p.getParent();
   }

   @Override
   public boolean hasChildren(Object element) {
      ProgramElement p = (ProgramElement) element;
      return p.hasChildren();
   }
}
