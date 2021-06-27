package view;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import model.ProgramElement;

public class AddProgElemDialog_Old extends TitleAreaDialog {
   private Text textPkg;
   private Text textClass;
   private Text textMethod;
   private Text textIsReturnVoid;
   private Text textParameterSize;
   private ProgramElement progElem;

   public ProgramElement getProgElem() {
      return progElem;
   }

   public AddProgElemDialog_Old(Shell parentShell) {
      super(parentShell);
   }

   @Override
   protected Control createContents(Composite parent) {
      Control contents = super.createContents(parent);
      setTitle("Add a new Program Element");
      setMessage("Please enter the data of the new program element", IMessageProvider.INFORMATION);
      return contents;
   }

   @Override
   protected Control createDialogArea(Composite parent) {
      GridLayout layout = new GridLayout();
      layout.numColumns = 2;
      parent.setLayout(layout);
      textPkg = createDataField(parent, "Package");
      textClass = createDataField(parent, "Class");
      textMethod = createDataField(parent, "Method");
      textIsReturnVoid = createDataField(parent, "IsReturnVoid");
      textParameterSize = createDataField(parent, "Parameter Size");
      textIsReturnVoid.setText("void");
      textIsReturnVoid.setEditable(false);
      textParameterSize.setText("0");
      textParameterSize.setEditable(false);
      return parent;
   }

   private Text createDataField(Composite parent, String str) {
      Label l = new Label(parent, SWT.NONE);
      l.setText(str);
      return new Text(parent, SWT.BORDER);
   }

   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      ((GridLayout) parent.getLayout()).numColumns++;
      Button button = new Button(parent, SWT.PUSH);
      button.setText("OK");
      button.setFont(JFaceResources.getDialogFont());
      button.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            if (textPkg.getText().trim().length() != 0 && textClass.getText().trim().length() != 0 && textMethod.getText().trim().length() != 0) {
               progElem = new ProgramElement(textPkg.getText(), textClass.getText(), textMethod.getText(), true, 0);
               close();
            } else {
               setErrorMessage("Please enter all data");
            }
         }
      });
   }
}
