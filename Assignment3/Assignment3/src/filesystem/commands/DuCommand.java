/*
 * DuCommand - command class for du to add up file sizes in current directory
 */
package filesystem.commands;

import filesystem.general.FileSystem;

public class DuCommand extends AbstractCommand {
    // data

    // methods
    // constructors
    public DuCommand() {
        // nothing at this time
    }

    public DuCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
        String outputString = "";

        outputString = "du> size: " + fs.getCurrentWorkingDirectory().getSize();
        return outputString;
    }

}    // end - class DuCommand
