package handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import model.Person;
import model.PersonModelProvider;
import util.UtilFile;
import view.MyTableViewer;

public class AddPersonHandler {
	@Inject
	private EPartService epartService;
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;
	
	@Execute
	public void execute() {
		
		PersonModelProvider personObject = PersonModelProvider.INSTANCE;
		List<Person> newAddPersons = new ArrayList<Person>();
		
		
		List<String> contents = UtilFile.readFile("F:\\DINGA\\BJM\\SBD2\\Spr21\\8790\\Eclipse_project\\workspaceCSCI8790\\workspaceCSCI8790-Chethan\\project-ex-0202-table-Chethan\\input_add.csv");
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);
		
		for (List<String> iList : tableContents) {
			newAddPersons.add(new Person(iList.get(0), iList.get(1), iList.get(2), iList.get(3)));
		}
		if (!newAddPersons.isEmpty()) {
			personObject.addPersons(newAddPersons);
			MPart findPart = epartService.findPart(MyTableViewer.ID);
			Object findPartObj = findPart.getObject();

			if (findPartObj instanceof MyTableViewer) {
				MyTableViewer v = (MyTableViewer) findPartObj;
				v.refresh();
			}
		}
	}

}