import javax.swing.*;

/**
 * Created by shengliu on 9/3/17.
 */
public class MiniCalculatorDriver {

   public static void main(String[] args){
   MiniCalculator aCalculator = null;

      try {
         aCalculator = new MiniCalculator();

      } catch (Exception e) {
         JOptionPane.showMessageDialog(null,e);
         e.printStackTrace();
      }
   }
}
