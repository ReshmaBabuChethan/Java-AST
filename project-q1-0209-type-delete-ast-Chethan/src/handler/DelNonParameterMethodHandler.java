

/**
 * @(#) DelMethodHandler.java
 */
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import analysis.DelNonParamMethodAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class DelNonParameterMethodHandler {

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
              //Check its parameter and ok to delete
           DelNonParamMethodAnalyzer delPublicAnalyzer = new DelNonParamMethodAnalyzer(p);
  			if(delPublicAnalyzer.DeleteSuccess()) {
  		           ModelProvider.INSTANCE.getProgramElements().remove(p);
  		            MyTableViewer v = (MyTableViewer) findPartObj;
  		            v.refresh();
         }
    	  }
      }
   }
}