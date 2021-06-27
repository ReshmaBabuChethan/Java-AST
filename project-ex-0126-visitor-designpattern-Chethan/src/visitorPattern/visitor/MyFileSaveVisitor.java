package visitorPattern.visitor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import visitorPattern.part.Body;
import visitorPattern.part.Brake;
import visitorPattern.part.Engine;
import visitorPattern.part.Wheel;

public class MyFileSaveVisitor extends CarPartVisitor {

	private String filePath = System.getProperty("user.dir");
	private List<String> contents;
	PrintWriter printWriter;

	public MyFileSaveVisitor() {
		contents = new ArrayList<String>();
		File fileObj = new File(filePath + File.separator + "outputdata.csv");
		try {
			if (fileObj.exists())
				fileObj.delete(); // to delete the file if it already exists

			fileObj.createNewFile();
			System.out.println("File " + fileObj + " in " + filePath);
			FileWriter fileWriter = new FileWriter(fileObj);
			printWriter = new PrintWriter(fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(List<String> str) {
		for (String writeStr : str)
			printWriter.print(writeStr + System.lineSeparator());
		printWriter.close();
	}

	@Override
	public void visit(Wheel part) {
		String wheelWrite = part.getName() + "," + part.getModelNumberWheel() + "," + part.getModelYearWheel();
		System.out.println("String is " + wheelWrite);
		contents.add(wheelWrite);
	}

	@Override
	public void visit(Engine part) {
		String engineWrite = part.getName() + "," + part.getModelNumberEngine() + "," + part.getModelYearEngine();
		System.out.println("String is " + engineWrite);
		contents.add(engineWrite);
	}

	@Override
	public void visit(Body part) {
		String bodyWrite = part.getName() + "," + part.getModelNumberBody() + "," + part.getModelYearBody();
		System.out.println("String is " + bodyWrite);
		contents.add(bodyWrite);
	}

	@Override
	public void visit(Brake part) {
		// concatenating all the attributes into single line
		String brakeWrite = part.getName() + "," + part.getModelNumberBrake() + "," + part.getModelYearBrake();
		System.out.println("String is " + brakeWrite);
		// Adding each line to contents list
		contents.add(brakeWrite);
		// calling function to write into a csv file
		writeToFile(contents);
	}

}