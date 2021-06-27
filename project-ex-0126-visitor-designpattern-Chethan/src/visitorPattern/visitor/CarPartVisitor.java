package visitorPattern.visitor;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;

public abstract class CarPartVisitor {
	public abstract void visit(Wheel wheel);

	public abstract void visit(Engine engine);

	public abstract void visit(Body body);

	public abstract void visit(Brake body);
}
