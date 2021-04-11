/*
 * LsCommand - command class for ls (list current directory)
 * 
 * Created - Paul J. Wagner, 4/10/2013
 * Modified - Paul J. Wagner, 4/24/2014 - changed output to return instead of display
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.FileSystemObject;

public class LsCommand extends AbstractCommand {
	// data
	// -- brings down fs from AbstractCommand
	
	// methods
	// constructors
	public LsCommand() {
		// nothing at this time
	}
	
	public LsCommand(FileSystem fs) {
		this.fs = fs;
	}

	// other methods
	public String execute(String [] params) {
		String outputString = "";
		Directory currentDir = fs.getCurrentWorkingDirectory();
		
		outputString += "ls> ";
		//outputString += currentDir.toString();		// TODO: drop this line?
		for (FileSystemObject fso : currentDir.getChildren()) {  
			outputString += fso.toString();
		}
		return outputString;
	}
}	// end - class LsCommand
