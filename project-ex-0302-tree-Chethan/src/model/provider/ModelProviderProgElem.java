package model.provider;

import java.util.ArrayList;
import java.util.List;

import model.progelement.ProgramElement;

public enum ModelProviderProgElem {
	INSTANCE;

	private List<ProgramElement> progElements = new ArrayList<ProgramElement>();

	public List<ProgramElement> getProgElements() {
		return progElements;
	}

	public ProgramElement addProgramElement(ProgramElement pkg) {
		for (ProgramElement iElem : progElements) {
			if (iElem.getName().equals(pkg.getName())) {
				return iElem;
			}
		}
		this.progElements.add(pkg);
		return null;
	}

	public void clearProgramElements() {
		this.progElements.clear();
	}
}
