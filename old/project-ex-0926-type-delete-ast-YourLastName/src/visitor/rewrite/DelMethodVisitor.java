/**
 * @(#) DelMethodVisitor.java
 */
package visitor.rewrite;

import java.lang.reflect.Modifier;

import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DelMethodVisitor extends ASTVisitor {
	private ProgramElement progElemToBeRemoved;
	private MethodDeclaration methodToBeRemoved;
	private ASTRewrite rewrite;

	@Inject
	private Shell shell;

	@Inject
	private ESelectionService selectionService;

	public DelMethodVisitor(ProgramElement curProgElem) {
		this.progElemToBeRemoved = curProgElem;
	}

	public void setASTRewrite(ASTRewrite rewrite) {
		this.rewrite = rewrite;
	}

	@Override
	public void endVisit(TypeDeclaration typeDecl) {
		ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
				TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		lrw.remove(methodToBeRemoved, null);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		String name = node.getName().getFullyQualifiedName();
		int methMods = node.getModifiers();
		boolean isPrivate = (methMods & Modifier.PRIVATE) != 0;
		if (name.equals(progElemToBeRemoved.getMethodName()) && isPrivate) {
			ProgramElement p = (ProgramElement) selectionService.getSelection();
			ModelProvider.INSTANCE.getProgramElements().remove(p);

			MessageDialog.openInformation(shell, "Title", "Deleted " + node.getName());

			this.methodToBeRemoved = node;
			return false;
		} else if (name.equals(progElemToBeRemoved.getMethodName()) && !isPrivate) {
			MessageDialog.openInformation(shell, "Warning",
					"Cannot delete the selected method " + node.getName() + " because it is not a private method!");
		}
		return true;
	}
}
