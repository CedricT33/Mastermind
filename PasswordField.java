import java.io.*;

public class PasswordField {

   /**
    *@return The password as entered by the user
    */
   public static String readPassword () {
      EraserThread et = new EraserThread();
      Thread mask = new Thread(et);
      mask.start();

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String password = "";

      try {
         password = in.readLine();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
      // stop masking
      et.stopMasking();
      // return the password entered by the user
      return password;
   }
}
