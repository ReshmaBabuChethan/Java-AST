/**
 * @(#) ProgramAnalysisHandler.java
 */
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import model.ModelProvider;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class ProgramAnalysisHandler {
   @Inject
   private EPartService epartService;

   @Execute
   public void execute() {
      System.out.println("TableRefreshHandler!!");
      MPart findPart = epartService.findPart(MyTableViewer.ID);
      Object findPartObj = findPart.getObject();

      if (findPartObj instanceof MyTableViewer) {
         ModelProvider.INSTANCE.clearProgramElements();
         ProjectAnalyzer analyzer = new ProjectAnalyzer();
         analyzer.analyze();
         MyTableViewer v = (MyTableViewer) findPartObj;
         v.setInput(ModelProvider.INSTANCE.getProgramElements());
         v.refresh();
      }
   }
}