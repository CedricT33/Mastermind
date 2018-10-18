class MyClass EcritureLente {

  public static void ecritureLente(String mot){
    for (int i = 0; i < mot.length(); i++){
      System.out.print(mot.charAt(i));
      try {
        Thread.sleep(20);
            } catch(InterruptedException ie) {
               ie.printStackTrace();
            }
    }
  }
}
