package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CartPartVisitor;

public interface ICarElement {
	void accept(CartPartVisitor visitor);
}
