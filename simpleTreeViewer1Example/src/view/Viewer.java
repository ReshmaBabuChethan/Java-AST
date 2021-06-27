/**
 * @(#) Viewer.java
 */
package view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import model.ModelProvider;
import model.Person;
import view.provider.ViewPersonContentProvider;
import view.provider.ViewPersonLabelProvider;

/**
 * @since J2SE-1.8
 */
public class Viewer {
   private TreeViewer viewer;

   @PostConstruct
   public void postConstruct(Composite parent) {
      viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
      viewer.setContentProvider(new ViewPersonContentProvider());
      viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewPersonLabelProvider()));
      List<Person> data = ModelProvider.INSTANCE.getPersons();
      Person[] array = data.toArray(new Person[data.size()]);
      viewer.setInput(array);
      addListener();
   }

   private void addListener() {
      Tree tree = (Tree) viewer.getControl();
      tree.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            TreeItem item = (TreeItem) e.item;
            if (item != null && item.getItemCount() > 0) {
               item.setExpanded(!item.getExpanded());
               viewer.refresh();
            }
         }
      });
      viewer.addDoubleClickListener(new IDoubleClickListener() {
         @Override
         public void doubleClick(DoubleClickEvent event) {
            TreeViewer viewer = (TreeViewer) event.getViewer();
            IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
            Object selectedNode = thisSelection.getFirstElement();
            viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
         }
      });
      tree = (Tree) viewer.getControl();
      Listener listener = new Listener() {
         @Override
         public void handleEvent(Event event) {
            TreeItem treeItem = (TreeItem) event.item;
            final TreeColumn[] treeColumns = treeItem.getParent().getColumns();
            viewer.getTree().getDisplay().asyncExec(new Runnable() {
               @Override
               public void run() {
                  for (TreeColumn treeColumn : treeColumns)
                     treeColumn.pack();
               }
            });
         }
      };
      tree.addListener(SWT.Expand, listener);
   }

   @Focus
   public void setFocus() {
      viewer.getControl().setFocus();
   }
}