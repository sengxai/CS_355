/*
 * MkdirCommand - command to make directory in current directory
 * 
 * Created - Paul J. Wagner, 4/27/2014 - format: mkdir name size
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;

public class MkdirCommand extends AbstractCommand {
	// data
	// -- brings fs down from AbstractCommand
	
	// methods
	// constructors
	public MkdirCommand() {
		// nothing at this time
	}
	
	public MkdirCommand(FileSystem fs) {
		this.fs = fs;
	}

	// other methods
	public String execute(String [] params) {
		String outputString = "mkdir> " + params[1] + " added";
		Directory currentDir = fs.getCurrentWorkingDirectory();
		//System.out.println("mkdir execute() - just got currentDir: " + currentDir.getName());
		currentDir.addChild(new Directory(params[1]));
		return outputString;
	}

}	// end - class MkdirCommand
