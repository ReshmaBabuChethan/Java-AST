 
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;
import analysis.DelPrivateMethodAnalyzer;

public class DelPrivateMethodHandler {
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

	         if (selectionService.getSelection() instanceof ProgramElement) {
	            ProgramElement p = (ProgramElement) selectionService.getSelection();
	            //ModelProvider.INSTANCE.getProgramElements().remove(p);
	            //Check whether its private and ok to delete
             DelPrivateMethodAnalyzer delPrivateAnalyzer = new DelPrivateMethodAnalyzer(p);
				if(delPrivateAnalyzer.DeleteSuccess()) {
			           ModelProvider.INSTANCE.getProgramElements().remove(p);
			            MyTableViewer v = (MyTableViewer) findPartObj;
			            v.refresh();
				}

	         }
	      }
	   }
}