/**
 * @(#) MethodNameEditingSupport.java
 */
package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;

import analysis.replace.ReplaceMethodNameAnalyzer;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class MethodNameEditingSupport extends ProgElemEditingSupport {

   public MethodNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getMethodName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ProgramElement p = (ProgramElement) element;
      new ReplaceMethodNameAnalyzer(p, String.valueOf(value));
      p.setMethodName((String.valueOf(value)));
      this.viewer.update(element, null);
      refreshViewer();
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }
}
