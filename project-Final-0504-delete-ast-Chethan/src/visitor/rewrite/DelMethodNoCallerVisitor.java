/**
 * @(#) DelMethodVisitor.java
 */
package visitor.rewrite;



import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.MethodReferenceMatch;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ProgramElement;
//import visitor.rewrite.ASTVisitorSearchMethodCallers.MethodCallerSearchRequestor;

/**
 * @since J2SE-1.8
 */
public class DelMethodNoCallerVisitor extends ASTVisitor {
   private ProgramElement progElemToBeRemoved;
   private MethodDeclaration methodToBeRemoved;
   private ASTRewrite rewrite;
   boolean paramChecker = false;
//Added
   private IPackageFragment[] packages;
   private Map<IMethod, IMethod[]> dataCallers = null;
   //private List<Map<IMethod, IMethod[]>> listCallers;
   
   @Inject
   private Shell shell;

   public DelMethodNoCallerVisitor(ProgramElement curProgElem,IPackageFragment[] p) {
      this.progElemToBeRemoved = curProgElem;
      this.paramChecker = false;
     //added
      this.packages = p;
      dataCallers = new HashMap<>();
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
   
   @Override
   public void endVisit(TypeDeclaration typeDecl) {
	   if(paramChecker)
	   {
		   ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		   lrw.remove(methodToBeRemoved, null);
	   }
	 
   }


   public boolean paramChecker() {
		return paramChecker;
   }
   
   public boolean visit(MethodDeclaration node) {
	      String name = node.getName().getFullyQualifiedName();
	      
	      //Added
	      IJavaElement methodDecl = node.resolveBinding().getJavaElement();
	         if (methodDecl instanceof IMethod) {
	            IMethod callee = (IMethod) methodDecl;
	            List<IMethod> callers = getCallerMethods(callee, packages);
	            dataCallers.put(callee, callers.toArray(new IMethod[0]));
           
	      if (name.equals(progElemToBeRemoved.getMethodName())) {   	
	       	 System.out.println("method name:"+progElemToBeRemoved.getMethodName());
	    	 // String name2="m1";
	    	  //if(!name2.equals(progElemToBeRemoved.getMethodName())){
	       //	System.out.println("empty:"+this.getDataCallers().isEmpty());
	    	//System.out.println("empty2:"+this.dataCallers.isEmpty());
	    	//  if (this.getDataCallers().isEmpty()) {
	       	System.out.println("empty2:"+callers.isEmpty());
	       	if (callers.isEmpty()) {
	    	   MessageDialog.openInformation(shell, "Title", "" + node.getName());
	    	      this.methodToBeRemoved = node;
	    	      paramChecker=true;   	   	 
	              return false;
	      }
	    	  else {
	    		  System.out.println("hello hai");
	    		  
	    		  //listCallers = new ArrayList<>();
	    		 // listCallers.add(dataCallers);
	    		  //String finalResult=callers.get(0).getElementName();
	    		  //adding part of final
	    		/*  try {
	    		  System.out.println("this is flag:"+callers.get(0).getFlags());
	    		  System.out.println("this is param:"+callers.get(i).getNumberOfParameters());
	    		  System.out.println("this is return type:"+callers.get(i).getReturnType());
	    		  }
	    		   catch (JavaModelException e) {
	    	            e.printStackTrace();
	    	         }
	    		  */
	    		  String finalResult="";
	    		  String disvar="";
	    		  for(int i=0; i<callers.size(); i++) {	   
	    			  if (i==0) {
	    				  finalResult=callers.get(0).getElementName();  
	    			  }
	    			  else {
	    			  finalResult += ", " +callers.get(i).getElementName();
	    			  }
	    			  /*if (i==callers.size()-1) {
	    				  finalResult += ", and"+callers.get(i).getElementName()+".";
	    			  }*/
	    			  try {
	    	    		  System.out.println("this is flag:"+callers.get(i).getFlags());
	    	    		  System.out.println("this is param:"+callers.get(i).getNumberOfParameters());
	    	    		  System.out.println("this is return type:"+callers.get(i).getReturnType());
	    	    		   disvar+= callers.get(i).getElementName()+" has the ";
	    	    				  if(callers.get(i).getFlags()==1) {
	    	    					  disvar+="public modifier"; }
	    	    				  else {
	    	    					  disvar+="private modifier";  }
	    	    				  if(callers.get(i).getReturnType().equalsIgnoreCase("V")) {
	    	    					  disvar+=", the void-return type"; }
	    	    				  else {
	    	    					  disvar+=", the int-return type";}
	    	    				
	    	    					  disvar+=", and "+callers.get(i).getNumberOfParameters()+" method parameter(s). ";  	    				 
	    	    				  }
	    	    		  
	    	    		   catch (JavaModelException e) {
	    	    	            e.printStackTrace();}
	    			}
	    			finalResult += ".";
	    			
	    		      	  MessageDialog.openInformation(shell, "Title", 
	    				 "Can not delete the selected method.\n"+"This " + progElemToBeRemoved.getMethodName()+ "  method has method callers: "+finalResult+" "+disvar);
	    	  paramChecker=false;
	    	  
	    	  }
	       	}
	      } 
	      return true;
	      
	   
   }

	     
	         
  List<IMethod> getCallerMethods(IMethod method, IPackageFragment[] packages) {
    SearchPattern pattern = SearchPattern.createPattern(method, IJavaSearchConstants.REFERENCES);
	             MethodCallerSearchRequestor requestor = new MethodCallerSearchRequestor();
	             SearchEngine searchEngine = new SearchEngine();
	             try {
	                IJavaSearchScope searchScope = SearchEngine.createJavaSearchScope(packages);
	                searchEngine.search(pattern, //
	                      new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, //
	                      searchScope, requestor, null);
	                return requestor.getCallerMethods();
	             } catch (CoreException e) {
	                e.printStackTrace();
	             }
	             return null;
	          }

	          class MethodCallerSearchRequestor extends SearchRequestor {
	             private List<IMethod> callerMethods = null;

	             public MethodCallerSearchRequestor() {
	                super();
	                this.callerMethods = new ArrayList<IMethod>();
	             }

	             public List<IMethod> getCallerMethods() {
	                return this.callerMethods;
	                
	             }

	             @Override
	             public void acceptSearchMatch(SearchMatch match) throws CoreException {
	                if (match instanceof MethodReferenceMatch) {
	                   MethodReferenceMatch methodMatch = (MethodReferenceMatch) match;
	                   if (methodMatch.getElement() instanceof IMethod) {
	                      IMethod method = (IMethod) methodMatch.getElement();
	                      //adding here
	                      
	                      //int methodModifiers = methodMatch.getModifiers();
	                      //node met=(node) methodMatch;
	               	  // if((methodModifiers & Modifier.PUBLIC)!= 0)

	                      this.callerMethods.add(method);
	                   }
	                }
	             }
	          }

	          public Map<IMethod, IMethod[]> getDataCallers() {
	             return dataCallers;
	          }
   
}


