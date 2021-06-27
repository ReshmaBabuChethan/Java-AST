package visitorPattern;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;
import visitorPattern.visitor.CarPartVisitor;

public class Car {
	Wheel wheel = new Wheel("Wheel Part");
	Engine engine = new Engine("Engine Part");
	Body body = new Body("Body Part");
	Brake brake = new Brake("Brake Part");

	public void accept(CarPartVisitor visitor) {
		wheel.accept(visitor);
		engine.accept(visitor);
		body.accept(visitor);
		brake.accept(visitor);
	}
}