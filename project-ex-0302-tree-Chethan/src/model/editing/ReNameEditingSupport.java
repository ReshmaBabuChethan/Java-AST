package model.editing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ViewerCell;

import analysis.RenameClassAnalyzer;
import analysis.RenameMethodAnalyzer;
import model.progelement.MethodElement;
import model.progelement.ProgramElement;
import model.progelement.TypeElement;
import view.Viewer;

public class ReNameEditingSupport extends ProgElemEditingSupport {
   private Viewer myViewer;

   public ReNameEditingSupport(Viewer myViewer) {
      super(myViewer.getViewer());
      this.myViewer = myViewer;
   }

   @Override
   protected Object getValue(Object element) {
      if (element instanceof MethodElement) {
         MethodElement m = (MethodElement) element;
         return m.getMethodName();
      }
      if (element instanceof ProgramElement) {
         ProgramElement p = (ProgramElement) element;
         return p.getName();
      }
      return element;
   }

   @Override
   protected void setValue(Object element, Object value) {
      if (element instanceof MethodElement) {
         MethodElement methodElem = (MethodElement) element;
         String newName = String.valueOf(value).trim();
         if (methodElem.getMethodName().equalsIgnoreCase(newName)) {
            return;
         }
         try {
            RenameMethodAnalyzer renameAnalyzer = new RenameMethodAnalyzer(methodElem, newName);
            renameAnalyzer.analyze();
         } catch (CoreException e) {
            e.printStackTrace();
         }
         methodElem.setMethodName(newName);
         this.treeViewer.update(element, null);
         this.myViewer.updateView();
      } else if (element instanceof TypeElement) {
         TypeElement typeElem = (TypeElement) element;
         String newName = String.valueOf(value).trim();
         if (typeElem.getName().equalsIgnoreCase(newName)) {
            return;
         }
         try {
            RenameClassAnalyzer renameAnalyzer = new RenameClassAnalyzer(typeElem, newName);
            renameAnalyzer.analyze();
         } catch (CoreException e1) {
            e1.printStackTrace();
         }
         typeElem.setName(newName);
         this.treeViewer.update(element, null);
         this.myViewer.updateView();
      }
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }
}

// private void updateModel() {
// this.treeViewer.getTree().deselectAll(); // resolved issue: stack overflow errors.
// ProjectAnalyzer analyzer = new ProjectAnalyzer();
// ModelProviderProgElem.INSTANCE.clearProgramElements();
// try {
// analyzer.analyze();
// } catch (CoreException e) {
// e.printStackTrace();
// }
// }