/**
 * @(#) ReturnTypeEditingSupport.java
 */
package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReturnTypeEditingSupport extends ProgElemEditingSupport {

   public ReturnTypeEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected CellEditor getCellEditor(Object element) {
      String[] isvoid = new String[2];
      isvoid[0] = "Yes";
      isvoid[1] = "No";
      return new ComboBoxCellEditor(viewer.getTable(), isvoid);
   }

   @Override
   protected Object getValue(Object element) {
      if (((ProgramElement) element).isReturnVoid()) {
         return 0;
      }
      return 1;
   }

   @Override
   protected void setValue(Object element, Object value) {
      if (((Integer) value) == 0) {
         ((ProgramElement) element).setReturnVoid(true);
      } else {
         ((ProgramElement) element).setReturnVoid(false);
      }
      this.viewer.update(element, null);
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }
}
