package visitorPattern.part;

import visitorPattern.visitor.CarPartVisitor;

public interface ICarElement {
	void accept(CarPartVisitor visitor);
}
