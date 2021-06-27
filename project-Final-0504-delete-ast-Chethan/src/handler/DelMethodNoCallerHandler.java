 
/*package handler;

import org.eclipse.e4.core.di.annotations.Execute;

public class DelMethodNoCallerHandler {
	@Execute
	public void execute() {
		
	}
		
}*/

 
/*package handler;

import org.eclipse.e4.core.di.annotations.Execute;

public class DelMethodNoCallerHandler {
	@Execute
	public void execute() {
		
	}
		
}*/




/**
 * @(#) DelMethodHandler.java
 */
package handler;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jdt.core.IMethod;

//import analysis.DelMethodNoCallerAnalyzer;
import analysis.DelMethodNoCallerAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class DelMethodNoCallerHandler {

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
               DelMethodNoCallerAnalyzer delAnalyzer = new DelMethodNoCallerAnalyzer(p);
             // System.out.println("From handler1");
              
              //Added
             // delAnalyzer.analyze();
             // List<Map<IMethod, IMethod[]>> calleeCallers = delAnalyzer.getListCallers();
          //  if (calleeCallers.isEmpty()){
              
  			if(delAnalyzer.DeleteSuccess()) {
            	
  		           ModelProvider.INSTANCE.getProgramElements().remove(p);
  		            MyTableViewer v = (MyTableViewer) findPartObj;
  		            v.refresh();
  		          System.out.println("From handler2");}
         }
    	  }
      }
   }
