/*
 * FileSystemView - view class to display file system interaction through shell
 * 
 * Created - Paul J. Wagner, 4/24/2014
 */
package filesystem.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import filesystem.general.Shell;

/**
 * @author WAGNERPJ
 */
public class ShellView extends JFrame {
	// - data
	private static final long serialVersionUID = 1L; 	// serial id 
	private Shell shell = null;							// the shell environment
	JTextField commandInputText = null;					// command input text field

	JTextArea consoleDisplayArea = null;				// console output display area
	JTextArea fileSystemDisplayArea = null;				// file system display area

	// - methods
	// -- constructors
	// --- file system constructor
	public ShellView(Shell shell) {
		super();
		this.shell = shell;
		initialize();
	}	// end - constructor
	
	// -- other methods
	// --- initialize - set up the graphical display
	private void initialize() {

		JPanel fileSystemApplicationContentPane = new JPanel();		// overall content pane
		JLabel title = new JLabel();							// title for pane

		Button runButton = new Button("Execute");	// button to execute a single command
					
		try {
			// set up the application frame
			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setSize(640, 580);
			setTitle("File System Project");
	
			// construct the title label
			title.setFont(new java.awt.Font("Arial", 1, 36));
			title.setText("Shell / File System");
			title.setBounds(13, 21, 600, 31);
			title.setForeground(java.awt.Color.blue);
			title.setHorizontalAlignment(SwingConstants.CENTER);

			// construct the input text field
			commandInputText = new JTextField(50);
			commandInputText.setBounds(30, 80, 180, 30);
			
			// construct the console display text area
			consoleDisplayArea = new JTextArea(10, 35);
			consoleDisplayArea.setBounds(30, 200, 260, 280);			
			
			// construct the file system display text area
			fileSystemDisplayArea = new JTextArea(10, 35);
			fileSystemDisplayArea.setBounds(320, 75, 260, 280);
			fileSystemDisplayArea.setText(shell.getFileSystem().genDisplay());

			// construct the button	
			runButton.setBounds(30, 130, 100, 35);
			runButton.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent e){						
					// set which tests are to be executed based on check boxes
					String inputString = getInputText();	
					// execute those tests
					String resultString = shell.executeCurrentCommandString(inputString);
					// display it
					consoleDisplayArea.setText(resultString);
					fileSystemDisplayArea.setText(shell.getFileSystem().genDisplay());
				} } );

			// --- construct the highest level content-pane
			fileSystemApplicationContentPane.setLayout(null);
			fileSystemApplicationContentPane.add(title);
			fileSystemApplicationContentPane.add(commandInputText);
			fileSystemApplicationContentPane.add(consoleDisplayArea);
			fileSystemApplicationContentPane.add(fileSystemDisplayArea);
			fileSystemApplicationContentPane.add(runButton);
				
			// --- finally, set the content pane
			setContentPane(fileSystemApplicationContentPane);

		} catch (java.lang.Throwable ivjExc) {
			System.err.println("Exception occurred in initialize() of File System Application");
			ivjExc.printStackTrace(System.out);
		}
	}	// end - method initialize
	
	
	/**
	 * getInputText - get the input text from a text field
	 * @return String
	 */
	public String getInputText() {
		String result = commandInputText.getText();
		return result;
	}	// end - method getInputText

	/**
	 * main - starts the application.
	 * @param args an array of command-line arguments
	 */
	public static void main(java.lang.String[] args) {
		
		try {
			// set up the models and controllers
			//FileSystem theFileSystem = new FileSystem();
			Shell theShell = new Shell();
			ShellView aShellView = new ShellView(theShell);
	
			// set up window closure
			aShellView.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosed(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
	
			// enable window visibility
			aShellView.setVisible(true);

		} catch (Throwable exception) {
			System.err.println("Exception occurred in main() of ShellView");
			exception.printStackTrace(System.out);
		}
	}	// end - method main

}	// end - class FileSystemView
