import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shengliu on 9/3/17.
 */
public class MiniCalculator extends JFrame implements ActionListener, KeyListener{
private final String DEFAULT_TITLE = "MiniCalculator V1.0";
private final Dimension DEFAULT_SIZE = new Dimension(400,400);
private JPanel     buttonPanel  = null;
private JTextField displayField = null;
private JTextArea     displayLabel = null;
private double previousDisplayFieldValue = 0.0;
private char   previousOperationChar = (char)-999;

   MiniCalculator() throws Exception {

      try {
         createCalculator();
      } catch (Exception e) {
         throw new Exception(e);
      }
   }                                    //Default

   MiniCalculator(String title, Dimension size){}

   private JPanel generateButtonPanel() throws Exception {
   final int BUTTON_ROWS        =   4;
   final int BUTTON_COLUMS      =   4;
   final String[] BUTTON_TEXT   =   {"7",   "8",   "9",   "/",
                                     "4",   "5",   "6",   "*",
                                     "1",   "2",   "3",   "-",
                                     "0",   "Clear",   "=",   "+"};
   JPanel buttonPanel = null;
   JButton aButton = null;


      try{
         buttonPanel = new JPanel();
         buttonPanel.addKeyListener(this);
         buttonPanel.setLayout(new GridLayout(BUTTON_ROWS,BUTTON_COLUMS));

         for(int i = 0; i < BUTTON_TEXT.length; i++){
            aButton = new JButton(BUTTON_TEXT[i]);
            aButton.addActionListener(this);
            aButton.addKeyListener(this);
            buttonPanel.add(aButton);
         }

         setButtonPanel(buttonPanel);

      }catch (Exception e){
         throw new Exception(e);
      }

      return buttonPanel;
   }

   private JPanel getButtonPanel(){

      return buttonPanel;
   }

   private void setDisplayLabel(JTextArea label){

      displayLabel = label;
   }

   private JTextArea getDisplayLabel(){

      return displayLabel;
   }

   private JPanel generateTextFieldPanel(){
   JTextField aTextField = null;
   JTextArea aLabel     = null;
   JPanel     textFieldPanel = null;
   JScrollPane aScroll = null;

      aTextField = new JTextField(15);
      aTextField.setText("0");
      aTextField.setPreferredSize(new Dimension(250,100));
      aTextField.addActionListener(this);
      setDisplayField(aTextField);

      aLabel     = new JTextArea("Here is the activity console: \n");
      aLabel.setPreferredSize(new Dimension(250,1000));
      aLabel.setEditable(false);
      setDisplayLabel(aLabel);

      aScroll = new JScrollPane(aLabel);
      aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      textFieldPanel = new JPanel();

      textFieldPanel.setLayout(new BoxLayout(textFieldPanel,BoxLayout.PAGE_AXIS));
      textFieldPanel.add(aTextField);
      textFieldPanel.add(aScroll);
      textFieldPanel.setPreferredSize(new Dimension(250,300));
      textFieldPanel.addKeyListener(this);


      return textFieldPanel;
   }

   private void resetDisplayArea(){

      getDisplayLabel().setText("Here is the activity console: \n");
   }

   private void setDisplayField(JTextField textField){

      displayField = textField;
   }

   private void setPreviousDisplayFieldValue(double value){

      previousDisplayFieldValue = value;
   }

   private void setButtonPanel(JPanel buttonPanelLocal){

      buttonPanel = buttonPanelLocal;
   }

   private double getPreviousDisplayFieldValue(){

      return previousDisplayFieldValue;
   }

   private void setPreviousOperationChar(char value){

      previousOperationChar = value;
   }

   private char getPreviousOperationChar(){

      return previousOperationChar;
   }

   private JTextField getDisplayField(){

      return displayField;
   }

   private boolean displayFieldIsEmpty(){

      return (getDisplayField().getText().length() < 1);
   }

   private void createCalculator() throws Exception {

      try {
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setSize(getDEFAULT_SIZE());
         setTitle(getDEFAULT_TITLE());
         setLayout(new GridLayout(2,1));
         setFocusable(true);
         add(generateTextFieldPanel());
         add(generateButtonPanel());
         addWindowListener(new MIniCalculatorWindowListener());
         addKeyListener(this);
         setVisible(true);
      } catch (Exception e){
         throw new Exception(e);
      }

   }

   private Dimension getDEFAULT_SIZE(){

      return DEFAULT_SIZE;
   }

   private String getDEFAULT_TITLE(){

      return DEFAULT_TITLE;
   }

   private void calculateResult(double previousNum, double current, char operation) throws Exception {
   double result = 0.0;

      if((operation == '+')){
         result = (previousNum) + (current);
      }
      else if((operation == '-')){
         result = (previousNum) - (current);
      }
      else if((operation == '*')){
         result = (previousNum) * (current);
      }
      else if((operation == '/')){
         result = (previousNum) / (current);
      }
      else{
         throw new Exception("Unknown button initiated: " + operation);
      }

      getDisplayField().setText(Double.toString(result));
      //getDisplayLabel().setText(getDisplayLabel().getText() + "Operation:  " + previousNum + operation + current + " = " + result + '\n' );
      getDisplayLabel().append("Operation:  " + previousNum + operation + current + " = " + result + '\n');

   }

   private void createCalculator(String title, Dimension size){

   }

   @Override
   public void actionPerformed(ActionEvent e) {
   char key = (char) -999;
      //Determine which button is clicked

      try {
         key = ((JButton)e.getSource()).getText().charAt(0);
         if (((key == '+') || (key == '-') || (key == '*') || (key == '/')) && !(displayFieldIsEmpty())) {

            setPreviousDisplayFieldValue(Double.parseDouble(getDisplayField().getText()));
            getDisplayField().setText("");
            setPreviousOperationChar(((JButton) e.getSource()).getText().charAt(0));
         } else if (((key == 'C')||(key == 'c')) && !(displayFieldIsEmpty())) {
            setPreviousDisplayFieldValue(0.0);
            getDisplayField().setText("0");
            setPreviousOperationChar((char) -999);
            resetDisplayArea();
         } else if ((key == '=') && !(displayFieldIsEmpty())) {
            calculateResult(getPreviousDisplayFieldValue(), Double.parseDouble(getDisplayField().getText()), getPreviousOperationChar());

         } else {
            getDisplayField().setText(getDisplayField().getText().replace("0","") + Character.toString(((JButton)e.getSource()).getText().charAt(0)));
         }


      }catch (Exception ex){
         ex.printStackTrace();
      }
   }


   @Override
   public void keyTyped(KeyEvent e) {



      if(((e.getKeyChar() >= '0') && (e.getKeyChar()<='9'))||((e.getKeyChar() == '/')||(e.getKeyChar() == '*')||(e.getKeyChar() == '-')||
          (e.getKeyChar() == '+') ||((e.getKeyChar() == '='))||((e.getKeyChar() == 'C')||(e.getKeyChar() == 'c')))){

         for(int i = 0; i < getButtonPanel().getComponentCount(); i++){
            if(((JButton)getButtonPanel().getComponent(i)).getText().charAt(0) == (e.getKeyChar())){
               ((JButton)getButtonPanel().getComponent(i)).doClick();
            }

         }
      }
      else{
         System.out.println("Typed unsupported key! Typed: " + e.getKeyChar());
      }
   }
   @Override
   public void keyPressed(KeyEvent e) {
   }

   @Override
   public void keyReleased(KeyEvent e) {

   }
}
