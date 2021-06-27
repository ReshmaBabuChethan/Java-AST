/**
 * @(#) ViewContentProvider.java
 */
package view.provider;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;

import view.Viewer;

/**
 * @since J2SE-1.8
 */
public class ViewContentProvider implements ITreeContentProvider {
   public void inputChanged(Viewer v, Object oldInput, Object newInput) {
   }

   @Override
   public void dispose() {
   }

   @Override
   public Object[] getElements(Object inputElement) {
      return (File[]) inputElement;
   }

   @Override
   public Object[] getChildren(Object parentElement) {
      File file = (File) parentElement;
      return file.listFiles();
   }

   @Override
   public Object getParent(Object element) {
      File file = (File) element;
      return file.getParentFile();
   }

   @Override
   public boolean hasChildren(Object element) {
      File file = (File) element;
      if (file.isDirectory()) {
         return true;
      }
      return false;
   }
}
