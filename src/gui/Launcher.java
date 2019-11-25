package gui;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/
public class Launcher implements java.io.Serializable {
  protected Launcher() {
    throw new UnsupportedOperationException();
  }
  /**
  * the main.
  * @param args the arguments
  */
    public static void main(String[] args) {
        Gui.main(args);
    }
}
