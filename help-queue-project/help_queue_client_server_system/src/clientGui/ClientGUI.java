package clientGui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class ClientGUI {

public static void main(String[] args) {
	new ClientGUI();
}
	public ClientGUI()
	{
		JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Help Request");
		guiFrame.setSize(300,300);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);



		//Create the second JPanel. Add a JLabel and JList and
		//make use the JPanel is not visible.
		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);
		JLabel help = new JLabel(getError());
	
		listPanel.add(help);
		
		
		
		JButton helpBut = new JButton( "Request Help");
		helpBut.setForeground(Color.GREEN);
		JButton cancelBut = new JButton( "Cancel Request");
		cancelBut.setForeground(Color.RED);
		cancelBut.setVisible(false);


		
		helpBut.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent event){

			
			listPanel.setVisible(!listPanel.isVisible());
			helpBut.setVisible(false);
			cancelBut.setVisible(true);

			}
		});
		
		
		cancelBut.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent event){
					listPanel.setVisible(!listPanel.isVisible());
					helpBut.setVisible(true);
					cancelBut.setVisible(false);
				}
			});
				
	
	
	guiFrame.add(listPanel, BorderLayout.CENTER);
	guiFrame.add(helpBut,BorderLayout.SOUTH);
	guiFrame.add(cancelBut,BorderLayout.NORTH);
	//make sure the JFrame is visible
	guiFrame.setVisible(true);
	}
	
	public static String getError() {
		String error = "Help Requested!";
		return error;
	}
}