/*
 * AbstractCommand - abstract class for commands
 */
package filesystem.commands;

import filesystem.general.FileSystem;

public abstract class AbstractCommand {
	// data
	protected FileSystem fs;			// FileSystem reference so command can work with file system
	
	// methods
	// -- execute() - abstract void command to be overridden by specific concrete classes
	public abstract String execute(String [] params);

}	// end - abstract class AbstractCommand
