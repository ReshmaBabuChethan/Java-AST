/**
 * @(#) ViewPersonContentProvider.java
 */
package view.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;

import model.Person;
import view.Viewer;

/**
 * @since J2SE-1.8
 */
public class ViewPersonContentProvider implements ITreeContentProvider {
   public void inputChanged(Viewer v, Object oldInput, Object newInput) {
   }

   @Override
   public void dispose() {
   }

   @Override
   public Object[] getElements(Object inputElement) {
      return (Person[]) inputElement;
   }

   @Override
   public Object[] getChildren(Object parentElement) {
      Person p = (Person) parentElement;
      return p.list();
   }

   @Override
   public Object getParent(Object element) {
      Person p = (Person) element;
      return p.getParent();
   }

   @Override
   public boolean hasChildren(Object element) {
      Person p = (Person) element;
      return p.hasChildren();
   }
}
