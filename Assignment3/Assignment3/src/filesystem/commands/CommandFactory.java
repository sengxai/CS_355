/*
 * class CommandFactory - abstract factory class to hold table of commands
 * 
 * Created - Paul J. Wagner, 5/2/2013
 * Modified - Paul J. Wagner, 4/24/2014, updated comments
 */
package filesystem.commands;

import java.util.Hashtable;

import filesystem.general.FileSystem;

/**
 * CommandFactory - a class that encapsulates mapping a command from a view
 *                  to a sub-controller class that is invoked by the controller
 *                  Based on code originally generated by Tom Moore at UW-Eau Claire,
 *                  Original source: Alur, et. al., Core J2EE Patterns
 * @author wagnerpj
 */
public abstract class CommandFactory {
	/**
	 * A table that contains a set of name/value pairs corresponding
	 * to a command string/command class mapping.
	 */
	  private static Hashtable<String, AbstractCommand> commandTable = null;
	
	/**
	 * This is a parameterized factory method
	 * The point of this factory method is to encapsulate all the logic involved in
	 * picking a proper concrete controller given a command.
	 *
	 * @param  String -- name of command to be mapped.
	 */
	 public static AbstractCommand getCommand(String op) {
		AbstractCommand theCommand = null;
		
		op = op.trim();
		
		theCommand = (AbstractCommand) commandTable.get(op);
		if(op == null || theCommand == null) {
			theCommand = (AbstractCommand) commandTable.get("error");
		}
	
		return theCommand;
	 }
	 
	/**
	 * This function maps a command passed as a parameter to the controller
	 * to a Command that controls the work to be performed.
	 * Add entries below to add additional functionality.
	 * An alternative implementation would make use of a property class in
	 * which the Command class instances are created in the property class.
	 */
	public static void initCommandTable(FileSystem fs) {
	
		if (commandTable == null) {
			
			commandTable = new Hashtable<String, AbstractCommand>();
		
			commandTable.put("error", new ErrorCommand(fs) );
			commandTable.put("pwd", new PwdCommand(fs) );
			commandTable.put("ls", new LsCommand(fs) );
			commandTable.put("mkfile", new MkfileCommand(fs));
			commandTable.put("mkdir", new MkdirCommand(fs));
			commandTable.put("cd", new CdCommand(fs));
			commandTable.put("rm", new RmCommand(fs));
			commandTable.put("rmdir", new RmDirCommand(fs));
			commandTable.put("cat", new CatCommand(fs));
			commandTable.put("du", new DuCommand(fs));
			commandTable.put("mklink", new MklinkCommand(fs));
			commandTable.put("rmlink", new RmlinkCommand(fs));
			
			
		}
	}

}	// end - class CommandFactory
