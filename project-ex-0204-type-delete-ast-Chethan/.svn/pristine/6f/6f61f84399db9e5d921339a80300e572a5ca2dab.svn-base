/**
 * @(#) ClassNameEditingSupport.java
 */
package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;

import analysis.replace.ReplaceClassNameAnalyzer;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ClassNameEditingSupport extends ProgElemEditingSupport {

   public ClassNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getClassName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ProgramElement p = (ProgramElement) element;
      new ReplaceClassNameAnalyzer(p, String.valueOf(value));
      p.setClassName((String.valueOf(value)));
      this.viewer.update(element, null);
      refreshViewer();
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }
}
