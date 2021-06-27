/**
 * @(#) ParameterEditingSupport.java
 */
package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ParameterEditingSupport extends ProgElemEditingSupport {

   public ParameterEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return "" + ((ProgramElement) element).getParameterSize();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ProgramElement p = (ProgramElement) element;
      p.setParameterSize(Integer.valueOf(value.toString()));
      this.viewer.update(element, null);
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }
}
