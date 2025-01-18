import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.time.*;  
import java.awt.event.*;
import java.time.format.*;

public class Events extends JPanel{
  
    private static final long serialVersionUID = 1L;
	
	
	
	public Events(LocalDate date, Database database, JPanel mainPanel){
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		ArrayList<Event> events = database.getEvents(dateFormatter.format(date));
		
		setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));
        setBackground(Color.WHITE);
		
		int rows=4;
		if(events.size()>4){
			rows=events.size();
		}
		
		JPanel list = new JPanel(new GridLayout(rows,1,10,10));
		list.setBackground(Color.WHITE);
		
		JScrollPane sp = new JScrollPane(list);
		
		for(int i=0;i<events.size();i++){
			
			final int j=i;
			
			JPanel event = new JPanel(new GridLayout(2,1));
			Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			Border matteBorder = BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#00d1e8"));
			//event.setBorder(BorderFactory.createEmptyBorder(BorderFactory.createEmptyBorder(10,10,10,10),BorderFactory.createMatteBorder(0,10,0,0,Color.decode("#00d1e8"))));
			event.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));
			event.setBackground(Color.decode("#f0f0f0"));
			event.setCursor(new Cursor(Cursor.HAND_CURSOR));
			event.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseReleased(MouseEvent e){}
			
			@Override
			public void mousePressed(MouseEvent e){
				
			}
			
			@Override
			public void mouseExited(MouseEvent e){}
			
			@Override
			public void mouseEntered(MouseEvent e){
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e){
				new EventEditor(events.get(j),database,mainPanel );
			}
			
			
			});
			
			JLabel title = new JLabel(events.get(i).getTitle());
			title.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
			title.setFont(new Font("Helvetica",Font.BOLD,18));
			title.setForeground(Color.BLACK);
			event.add(title);
			
			JLabel time = new JLabel(events.get(i).getDateTimeToString());
			//JLabel time = new JLabel();
			time.setBorder(BorderFactory.createEmptyBorder(5,15,4,15));
			time.setFont(new Font("Helvetica",Font.BOLD,14));
			time.setForeground(Color.DARK_GRAY);
			event.add(time);
		
			list.add(event);
		}
		
		add(sp, BorderLayout.CENTER);
		
		JButton newEvent = new JButton("New");
		newEvent.setFont(new Font("Helvetica",Font.PLAIN,20));
		newEvent.setBackground(Color.decode("#D3D3D3"));
		newEvent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		add(newEvent, BorderLayout.SOUTH);
		newEvent.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseReleased(MouseEvent e){}
			
			@Override
			public void mousePressed(MouseEvent e){
				
			}
			
			@Override
			public void mouseExited(MouseEvent e){}
			
			@Override
			public void mouseEntered(MouseEvent e){
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e){
				new EventEditor(new Event(date),database,mainPanel);
			}
			
			
		});
	}
  
}