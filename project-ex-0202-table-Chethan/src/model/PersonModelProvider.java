

package model;

import java.util.ArrayList;
import java.util.List;

import util.UtilFile;

public enum PersonModelProvider {
	// INSTANCE;
	INSTANCE(getFilePath()); // Call a constructor with a parameter. 

	private List<Person> persons;

        // Load data sets. 
	private PersonModelProvider() {
	}

        // Load the data sets from a file dynamically. 
	private PersonModelProvider(String inputdata) {
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		persons = new ArrayList<Person>();
		for (List<String> iList : tableContents) {                        
			persons.add(new Person(iList.get(0),iList.get(1),iList.get(2),iList.get(3)));
		}
	}

	private static String getFilePath() {
		return "F:\\DINGA\\BJM\\SBD2\\Spr21\\8790\\Eclipse_project\\workspaceCSCI8790\\workspaceCSCI8790-Chethan\\project-ex-0202-table-Chethan\\input_init.csv";
	}

	public List<Person> getPersons() {
		return persons;
	}
	public void addPersons(List<Person> newAddPersons) {
		persons.addAll(newAddPersons);
	}

}