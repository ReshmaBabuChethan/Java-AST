/**
 * @file SimpleViewer.java
 */
package view;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import analysis.ProjectAnalyzerDefUsePublic;
import analysis.ProjectAnalyzerDefUseNonParam;
import analysis.ProjectAnalyzerDefUseVoidRet;
import data.DefUseModel;
import util.SWTResourceManager;

/**
 * @since JavaSE-1.8
 */
public class SimpleViewer {

   private StyledText styledText;
   public String checker=null;

   public SimpleViewer() {
   }

   @PostConstruct
   public void createControls(Composite parent) {
      Composite composite = new Composite(parent, SWT.NONE);
      composite.setLayout(new FillLayout(SWT.HORIZONTAL));
      styledText = new StyledText(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      styledText.setFont(SWTResourceManager.getFont("Fixedsys", 12, SWT.NORMAL));
      //added
      createContextMenu(parent);
   }
   
   //modified
   private void createContextMenu(Composite parent) {
		Menu popupMenu = new Menu(styledText);
		styledText.setMenu(popupMenu);
		
		//Public methods
		final MenuItem menuItemPublic = new MenuItem(popupMenu, SWT.PUSH);
		menuItemPublic.setText("Def Use Analysis for Public Methods");
		menuItemPublic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				analyzeDefUsePublic();
			}
		});
				
	//Non param methods
				final MenuItem menuItemNonParam = new MenuItem(popupMenu, SWT.PUSH);
				menuItemNonParam.setText("Def Use Analysis for Non-Parameter Methods");
				menuItemNonParam.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						analyzeDefUseNonParam();
					}
				});
				
		//Void methods
				final MenuItem menuItemVoidRet = new MenuItem(popupMenu, SWT.PUSH);
				menuItemVoidRet.setText("Def Use Analysis for Void Return Methods");
				menuItemVoidRet.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						analyzeDefUseVoidReturn();
					}
				});
	}

   
	public void analyzeDefUsePublic() {
		 		//System.out.println("setting public flag from simpleviewer");
		ProjectAnalyzerDefUsePublic analyzer = new ProjectAnalyzerDefUsePublic();
				try {
						analyzer.analyze();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();
		displayDefUseView(analysisDataList);
			}
	
	public void analyzeDefUseNonParam() {
		ProjectAnalyzerDefUseNonParam analyzer = new ProjectAnalyzerDefUseNonParam();
				try {
						analyzer.analyze();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();
		displayDefUseView(analysisDataList);
	}
	
	public void analyzeDefUseVoidReturn() {
		ProjectAnalyzerDefUseVoidRet analyzer = new ProjectAnalyzerDefUseVoidRet();
				try {
						analyzer.analyze();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();
		displayDefUseView(analysisDataList);
	}

	public void displayDefUseView(List<Map<IVariableBinding, DefUseModel>> list) {
		this.reset();
		int counter = 1;
		for (Map<IVariableBinding, DefUseModel> iMap : list) {
			for (Entry<IVariableBinding, DefUseModel> entry : iMap.entrySet()) {
				IVariableBinding iBinding = entry.getKey();
				DefUseModel iVariableAnal = entry.getValue();
				CompilationUnit cUnit = iVariableAnal.getCompilationUnit();
				VariableDeclarationStatement varDeclStmt = iVariableAnal.getVarDeclStmt();
				VariableDeclarationFragment varDecl = iVariableAnal.getVarDeclFrgt();

				this.appendText("[" + (counter++) + "] ABOUT VARIABLES '" + varDecl.getName() + "'\n");
				String method = "[METHOD] " + iBinding.getDeclaringMethod() + "\n";
				this.appendText(method);
				String stmt = "\t[DECLARE STMT] " + strTrim(varDeclStmt) + "\t [" + getLineNum(cUnit, varDeclStmt)+ "]\n";
				this.appendText(stmt);
				String var = "\t[DECLARE VAR] " + varDecl.getName() + "\t [" + getLineNum(cUnit, varDecl) + "]\n";
				this.appendText(var);

				List<SimpleName> usedVars = iVariableAnal.getUsedVars();
				for (SimpleName iSimpleName : usedVars) {

					ASTNode parentNode = iSimpleName.getParent();
					if (parentNode != null && parentNode instanceof Assignment) {
						String assign = "\t\t[ASSIGN VAR] " + strTrim(parentNode) + "\t ["
								+ getLineNum(cUnit, iSimpleName) + "]\n";
						this.appendText(assign);
					} else {
						String use = "\t\t[USE VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName)
								+ "]\n";
						this.appendText(use);
					}
				}
			}
		}
	}
	//till here

   public StyledText getStyledText() {
      return styledText;
   }

   public void appendText(String s) {
      this.styledText.append(s);
   }

   public void reset() {
      this.styledText.setText("");
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
      this.styledText.setFocus();
   }
   
   //modified 
   String strTrim(Object o) {
		return o.toString().trim();
	}

	int getLineNum(CompilationUnit compilationUnit, ASTNode node) {
		return compilationUnit.getLineNumber(node.getStartPosition());
	}
//till here
}
