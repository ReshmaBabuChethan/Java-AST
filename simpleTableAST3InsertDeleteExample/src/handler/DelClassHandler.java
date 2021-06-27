/**
 * @(#) DelClassHandler.java
 */
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import analysis.DelClassAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class DelClassHandler {
   @Inject
   private ESelectionService selectionService;
   @Inject
   private EPartService epartService;

   @Execute
   public void execute() {
      System.out.println("DelProgElemHandler!!");

      MPart findPart = epartService.findPart(MyTableViewer.ID);
      Object findPartObj = findPart.getObject();
      if (findPartObj instanceof MyTableViewer) {
         MyTableViewer v = (MyTableViewer) findPartObj;
         if (remove(selectionService.getSelection())) {
            v.refresh();
         }
      }
   }

   private boolean remove(Object sel) {
      if (sel instanceof ProgramElement) {
         ProgramElement p = (ProgramElement) sel;
         ModelProvider.INSTANCE.getProgramElements().remove(p);
         new DelClassAnalyzer(p);
         return true;
      }
      return false;
   }
}