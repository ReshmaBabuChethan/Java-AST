 
package handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import java.io.*;
import java.util.*;

public class ShowHighestNumberHandler {
	@Execute
	public void execute(Shell shell) {
		List<Integer> list = new ArrayList<>();
		String filePath = "F:\\DINGA\\BJM\\SBD2\\Spr21\\8790\\Week2\\numbers.csv";
		File file = new File(filePath);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Integer convertdInt=Integer.valueOf(line); //converting to integer
				list.add(convertdInt);
			}
		}catch ( FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
		
		//sorting the list into descending order
		Collections.sort(list,Collections.reverseOrder());
		
		//result array declaration
		int[] result= new int[5];
		int j=0;
		result[j]=list.get(0); //assign with top most element
		for(int i=1;i<list.size(); i++)
        {
			
			if(j<4)
			{
				int max=list.get(i);
					if (max != result[j]) {
						j++;
					    result[j]=max;
				     }
			 }
             
        }
		
		//declare a string variable
		String finalResult="Top five numbers are: "+ Integer.toString(result[0]);
		
		// loop through result by concatenating
		for(int i=1;i<result.length; i++) {
			finalResult += ", " +Integer.toString(result[i]);
		}
		finalResult += ".";
		
		MessageDialog.openInformation(shell, "Title", finalResult);
	}
		
}