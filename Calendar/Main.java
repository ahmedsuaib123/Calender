import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.time.*;  

public class Main{
  public static void main(String[] args) {
    
	
	
	JFrame frame = new JFrame("Calendar");
	frame.setSize(900,500);
	frame.setLocationRelativeTo(null);
	frame.getContentPane().setBackground(Color.WHITE);
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	
	JPanel mainPanel=new JPanel(new GridLayout(1,2));
	
	LocalDate date = LocalDate.now();
	Database database = new Database();
	
	mainPanel.add(new Calendar(date.getYear(),date.getMonthValue(),date,mainPanel,database));
	mainPanel.add(new Events(date,database,mainPanel));
	
	frame.getContentPane().add(mainPanel);
	frame.setVisible(true);
  }
}
