//Modified for startPos
package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import analysis.ProjectAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import java.util.List;


public class Viewer {
	private TableViewer viewer;
	private static final Image CHECKED = getImage("checked.gif");
	private static final Image UNCHECKED = getImage("unchecked.gif");
	private PrintWriter printWriter;
	String[] titles = { "Package Name", "Class Name", "Method Name", "Is Return Void", "Parameter Size",
	"Starting Position of Method" };

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		createViewer(parent);
		createContextMenu(parent);
	}

	private void createContextMenu(Composite parent) {
		Menu contextMenu = new Menu(viewer.getTable());
		viewer.getTable().setMenu(contextMenu);
		createMenuItem(contextMenu);
	}

	private void createMenuItem(Menu contextMenu) {
      final MenuItem menuItemAnalzr = new MenuItem(contextMenu, SWT.PUSH);
      menuItemAnalzr.setText("Analyze Program");
      menuItemAnalzr.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            ModelProvider.INSTANCE.clearProgramElements();
            try {
               ProjectAnalyzer analyzer = new ProjectAnalyzer();
               analyzer.analyze();
            } catch (Exception e2) {
               e2.printStackTrace();
           }
            viewer.setInput(ModelProvider.INSTANCE.getProgramElements());
         }
      });
      
      //Export 
      final MenuItem menuItemExprt = new MenuItem(contextMenu, SWT.PUSH);
      menuItemExprt.setText("Export");
      menuItemExprt.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
        	 List<ProgramElement> programElements= ModelProvider.INSTANCE.getProgramElements();
        	    	 
        	 final String filePath = System.getProperty("user.dir");
        	 File fileObj = new File(filePath + File.separator + "outputdata.csv");
     		  try {
     			if (fileObj.exists())
     				fileObj.delete(); // to delete the file if it already exists

     			fileObj.createNewFile();
     			System.out.println("File " + fileObj + " in " + filePath);
     			FileWriter fileWriter = new FileWriter(fileObj);
     			printWriter = new PrintWriter(fileWriter);
     		  } catch (IOException s) {
     			s.printStackTrace();
     		}    	
     	
     	     //String Header= "Package Name, Class Name, Method Name, Is Return Void, Parameter Size,Starting Position of Method";
     		 String Header=titles[0]+","+titles[1]+","+titles[2]+","+titles[3]+","+titles[4]+","+titles[5];
     		printWriter.print(Header+ System.lineSeparator());
 			 
        	for(ProgramElement iprogramElement : programElements) {
        		String PkgName= iprogramElement.getPkgName();
        		String ClassName = iprogramElement.getClassName();
        		String MthdName = iprogramElement.getMethodName();
        		boolean ReturnVd= iprogramElement.isReturnVoid();
        		//Image img=getimage(iprogramElement.isReturnVoid());
        		int ParamSize= iprogramElement.getParameterSize();
        		int StartPos= iprogramElement.getStartPosMethod();
        		String str= PkgName+","+ClassName+","+MthdName+","+ReturnVd+","+ParamSize+","+StartPos;
        		printWriter.print(str + System.lineSeparator()); 
        	   }
        		printWriter.close();
             }
        });
  
    }

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createProgElemColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(ModelProvider.INSTANCE.getProgramElements());
		// make the selection available to other views
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				Object firstElement = selection.getFirstElement();
				System.out.println("Do something with it: " + firstElement);
			}
		});

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	private void createProgElemColumns(final Composite parent, final TableViewer viewer) {
		//String[] titles = { "Package Name", "Class Name", "Method Name", "Is Return Void", "Parameter Size",
		//		"Starting Position of Method" };
		int[] bounds = { 100, 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ProgramElement p = (ProgramElement) element;
				return p.getPkgName();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ProgramElement p = (ProgramElement) element;
				return p.getClassName();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ProgramElement p = (ProgramElement) element;
				return p.getMethodName();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				if (((ProgramElement) element).isReturnVoid()) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ProgramElement p = (ProgramElement) element;
				return String.valueOf(p.getParameterSize());
			}
		});

		// starting position method
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ProgramElement p = (ProgramElement) element;
				return String.valueOf(p.getStartPosMethod());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(Viewer.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}
}
