import java.io.*;

class EraserThread implements Runnable {
   private boolean stop;

   /**
    * Begin masking...display asterisks (*)
    */
   public void run () {
      stop = true;
      while (stop) {
        System.out.print("\010*");

	 try {
	    Thread.currentThread().sleep(2000);
         } catch(InterruptedException ie) {
            ie.printStackTrace();
         }
      }
   }

   /**
    * Instruct the thread to stop masking
    */
   public void stopMasking() {
      this.stop = false;
   }
}
