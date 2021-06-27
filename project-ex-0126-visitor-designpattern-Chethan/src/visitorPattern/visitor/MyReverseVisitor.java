package visitorPattern.visitor;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;

public class MyReverseVisitor extends CarPartVisitor {
	public String reverseString(String originalStr) {
		// split the string into array of words
		String[] orgStr = originalStr.split(" ");
		String reverseStr = "";
		// outer loop based on number of words in a string
		for (int i = 0; i < orgStr.length; i++) {
			String word = orgStr[i];
			String reverseWord = "";
			// inner loop for word reversal
			for (int j = word.length() - 1; j >= 0; j--) {
				reverseWord = reverseWord + word.charAt(j);
			}
			reverseStr = reverseStr + reverseWord + " ";
		}
		return reverseStr.trim();
	}

	public void visit(Wheel part) {
		String reversedName = reverseString(part.getName());
		part.setName(reversedName);

		String reversedModel = reverseString(part.getModelNumberWheel());
		part.setModelNumberWheel(reversedModel);

		String reversedYear = reverseString(part.getModelYearWheel());
		part.setModelYearWheel(reversedYear);

	}

	public void visit(Engine part) {
		String reversedName = reverseString(part.getName());
		part.setName(reversedName);

		String reversedModel = reverseString(part.getModelNumberEngine());
		part.setModelNumberEngine(reversedModel);

		String reversedYear = reverseString(part.getModelYearEngine());
		part.setModelYearEngine(reversedYear);
	}

	public void visit(Body part) {
		String reversedName = reverseString(part.getName());
		part.setName(reversedName);

		String reversedModel = reverseString(part.getModelNumberBody());
		part.setModelNumberBody(reversedModel);

		String reversedYear = reverseString(part.getModelYearBody());
		part.setModelYearBody(reversedYear);
	}

	public void visit(Brake part) {
		// calling function to reverse a string
		String reversedName = reverseString(part.getName());
		// re-initializing the name with reversed string
		part.setName(reversedName);

		String reversedModel = reverseString(part.getModelNumberBrake());
		part.setModelNumberBrake(reversedModel);

		String reversedYear = reverseString(part.getModelYearBrake());
		part.setModelYearBrake(reversedYear);
	}
}