package visitorPattern.visitor;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;

public class MyRenameVisitor extends CarPartVisitor {
	public void visit(Wheel part) {
		String newName = "New " + part.getName(); // Suppose a new name is notified.
		// System.out.println("[DBG] Changing the name property to : " + newName);
		part.setName(newName);
		System.out.println("[DBG] Changing the name property to : " + newName);
	}

	public void visit(Engine part) {
		String newName = "New " + part.getName(); // Suppose a new name is notified.
		System.out.println("[DBG] Changing the name property to : " + newName);
		part.setName(newName);
	}

	public void visit(Body part) {
		String newName = "New " + part.getName(); // Suppose a new name is notified.
		System.out.println("[DBG] Changing the name property to : " + newName);
		part.setName(newName);
	}

	public void visit(Brake part) {
		String newName = "New " + part.getName(); // Suppose a new name is notified.
		System.out.println("[DBG] Changing the name property to : " + newName);
		part.setName(newName);
	}
}