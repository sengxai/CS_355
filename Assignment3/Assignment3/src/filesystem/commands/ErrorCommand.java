/*
 * ErrorCommand - command class for default error situations (no legitimate command given) 
 *
 * Created - Paul J. Wagner, 4/10/2013
 * Modified - Paul J. Wagner, 4/24/2014 - changed output to return instead of display
 */
package filesystem.commands;

import filesystem.general.FileSystem;

public class ErrorCommand extends AbstractCommand {
	// data
	
	// methods
	// constructors
	public ErrorCommand() {
		// nothing at this time
	}
	
	public ErrorCommand(FileSystem fs) {
		this.fs = fs;
	}

	// other methods
	public String execute(String [] params) {
		return ("Error - invalid command");
	}
}
