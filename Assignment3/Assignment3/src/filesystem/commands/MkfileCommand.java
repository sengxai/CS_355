/*
 * MkfileCommand - command to make file in current directory
 * 
 * Created - Paul J. Wagner, 4/10/2013, format: mkfile name size contents
 * Modified - Paul J. Wagner, 4/24/2014 - changed to return output as string
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.File;

public class MkfileCommand extends AbstractCommand {
	// data
	// -- brings fs down from AbstractCommand
	
	// methods
	// constructors
	public MkfileCommand() {
		// nothing at this time
	}
	
	public MkfileCommand(FileSystem fs) {
		this.fs = fs;
	}

	// other methods
	public String execute(String [] params) {
		String outputString = "mkfile> " + params[1] + " added";
		Directory currentDir = fs.getCurrentWorkingDirectory();
		//System.out.println("address of currentDir is: " + currentDir);
		currentDir.addChild(new File(params[1], Integer.parseInt(params[2]), params[3]));
		return outputString;
	}

}	// end - class MkfileCommand
