package visitorPattern.visitor;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;

public class MyAccessVisitor extends CarPartVisitor {
	public void visit(Wheel part) {
		System.out.println("[DBG] Accessing the name property: " + part.getName());
	}

	public void visit(Engine part) {
		System.out.println("[DBG] Accessing the name property: " + part.getName());
	}

	public void visit(Body part) {
		System.out.println("[DBG] Accessing the name property: " + part.getName());
	}

	public void visit(Brake part) {
		System.out.println("[DBG] Accessing the name property: " + part.getName());
	}
}