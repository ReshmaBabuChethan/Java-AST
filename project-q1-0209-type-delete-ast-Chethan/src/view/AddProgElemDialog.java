/**
 * @(#) AddProgElemDialog.java
 */
package view;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class AddProgElemDialog extends TitleAreaDialog {
   Text methodTxt, classTxt, pkgTxt;
   Combo comboReturnType, comboParm;
   ProgramElement progElem;

   public AddProgElemDialog(Shell parentShell) {
      super(parentShell);
   }

   @Override
   protected Control createContents(Composite parent) {
      Control contents = super.createContents(parent);
      setTitle("Add a New Program Element\n\n  New package, class, and method names");
      return contents;
   }

   @Override
   protected Control createDialogArea(Composite parent) {
      Composite area = (Composite) super.createDialogArea(parent);
      Composite container = new Composite(area, SWT.NONE);
      container.setLayoutData(new GridData(GridData.FILL_BOTH));

      Label lbPkg = new Label(container, SWT.NONE);
      lbPkg.setBounds(10, 10, 59, 14);
      lbPkg.setText("Package");

      Label lbClass = new Label(container, SWT.NONE);
      lbClass.setBounds(10, 35, 59, 14);
      lbClass.setText("Class");

      Label lbMethod = new Label(container, SWT.NONE);
      lbMethod.setBounds(10, 60, 59, 14);
      lbMethod.setText("Method");

      Label lbParms = new Label(container, SWT.NONE);
      lbParms.setBounds(10, 85, 69, 14);
      lbParms.setText("Parameters");

      Label lbReturnType = new Label(container, SWT.NONE);
      lbReturnType.setBounds(10, 110, 69, 14);
      lbReturnType.setText("Return Type");

      pkgTxt = new Text(container, SWT.BORDER);
      pkgTxt.setBounds(101, 10, 339, 19);
      pkgTxt.setText("pkg3");

      classTxt = new Text(container, SWT.BORDER);
      classTxt.setBounds(101, 35, 339, 19);
      classTxt.setText("ClassC");

      methodTxt = new Text(container, SWT.BORDER);
      methodTxt.setBounds(101, 60, 339, 19);
      methodTxt.setText("m1");

      comboReturnType = new Combo(container, SWT.NONE);
      comboReturnType.setBounds(101, 110, 133, 22);
      comboReturnType.setText("void"); // return

      comboParm = new Combo(container, SWT.NONE);
      comboParm.setBounds(101, 85, 150, 22);
      comboParm.setText("void"); // p

      return parent;
   }

   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      ((GridLayout) parent.getLayout()).numColumns++;
      Button button = new Button(parent, SWT.PUSH);
      button.setText("OK");
      button.setFont(JFaceResources.getDialogFont());
      button.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            String valPkg = pkgTxt.getText();
            String valClass = classTxt.getText();
            String valMethod = methodTxt.getText();

            if (valPkg.trim().isEmpty() || valClass.trim().isEmpty() || valMethod.trim().isEmpty()) {
               return;
            }
            progElem = new ProgramElement(valPkg, valClass, valMethod, true, 0);
            close();
         }
      });
   }

   public ProgramElement getProgElem() {
      return progElem;
   }

   @Override
   protected Point getInitialSize() {
      return new Point(450, 330);
   }

}
