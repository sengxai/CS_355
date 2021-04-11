/*
 * class Parser - to parse commands to separate command from arguments
 * 
 * Created - Paul J. Wagner, 4/10/2013
 * Modified - Paul J. Wagner, 4/24/2014 - updated comments
 */
package filesystem.general;

public class Parser {
	// data
	
	// methods
	// constructors
	// -- default constructor
	public Parser() {
	}
	
	// other methods
	// -- parseCommand(originalCommand) - split main command from arguments
	public String [] parseCommand(String originalCommand) {
		String [] parts = null;				// result parts
		String contents = null;				// contents of mkfile command
		int doubleQuotePos = -1;			// position of first double quote in mkfile command
		
		doubleQuotePos = originalCommand.indexOf("\"");
		if (doubleQuotePos != -1) {			// found a double quote in a mkfile command
			contents = originalCommand.substring(doubleQuotePos + 1, originalCommand.length() - 1);
			parts = originalCommand.split(" ");
			parts[3] = contents;
		} else {							// all other commands without double quote
			parts = originalCommand.split(" ");
		}
		return parts;
	}	// end - method parseCommand

}	// end - class Parser
