/**
 * @(#) ProgElemEditingSupport.java
 */
package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;

import analysis.ProjectAnalyzer;
import model.ModelProvider;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ProgElemEditingSupport extends EditingSupport {
   protected TableViewer viewer;
   protected TextCellEditor editor;

   public ProgElemEditingSupport(TableViewer viewer) {
      super(viewer);
      this.viewer = viewer;
      this.editor = new TextCellEditor(viewer.getTable());
   }

   @Override
   protected CellEditor getCellEditor(Object element) {
      return this.editor;
   }

   @Override
   protected boolean canEdit(Object element) {
      return true;
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getPkgName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ((ProgramElement) element).setPkgName((String.valueOf(value)));
      this.viewer.update(element, null);
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }

   void refreshViewer() {
      ModelProvider.INSTANCE.clearProgramElements();
      ProjectAnalyzer analyzer = new ProjectAnalyzer();
      analyzer.analyze();
      this.viewer.setInput(ModelProvider.INSTANCE.getProgramElements());
      this.viewer.refresh();
      // String commandId = "simpletableast2editableexample.command.refresh";
      // Command command = commandService.getCommand(commandId);
      // if (command.isDefined()) {
      // // handlerService.activateHandler(commandId, new Handler2());
      // ParameterizedCommand createCommand = commandService.createCommand(commandId, null);
      // if (handlerService.canExecute(createCommand)) {
      // handlerService.executeHandler(createCommand);
      // }
      // }
   }
}
