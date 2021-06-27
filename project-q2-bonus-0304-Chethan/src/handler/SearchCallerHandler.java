/**
 * @file HandlerSearchMethodCaller.java
 */
package handler;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.IMethod;

import analysis.ProjectAnalyzerDefUseSearchCaller;
import view.SimpleViewer;

/**
 * @since JavaSE-1.8
 */
public class SearchCallerHandler {
   SimpleViewer viewer = null;

   @Execute
   public void execute(EPartService service) {
      MPart part = service.findPart(SimpleViewer.VIEWID);
      if (part != null) {
         if (part.getObject() instanceof SimpleViewer) {
            viewer = (SimpleViewer) part.getObject();
            viewer.reset();

            try {
            	ProjectAnalyzerDefUseSearchCaller analyzer = new ProjectAnalyzerDefUseSearchCaller(viewer.getSearchText().getText());
               analyzer.analyze();
               List<Map<IMethod, IMethod[]>> calleeCallers = analyzer.getListCallers();
               viewer.display(calleeCallers);
            } catch (CoreException e) {
               e.printStackTrace();
            }
         }
      }
   }
}