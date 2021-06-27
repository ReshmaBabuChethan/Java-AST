/**
 * @(#) MsgUtil.java
 */
package util;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @since J2SE-1.8
 */
public class MsgUtil {
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   static Shell shell;

   public static void openInfo(String title, String msg) {
      MessageDialog.openInformation(shell, title, msg);
   }

   public static void openWarning(String title, String msg) {
      MessageDialog.openWarning(shell, title, msg);
   }

   public static boolean openQuestion(String title, String msg) {
      return MessageDialog.openQuestion(shell, title, msg);
   }

}
