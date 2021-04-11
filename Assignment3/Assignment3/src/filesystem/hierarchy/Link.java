package filesystem.hierarchy;

public class Link extends FileSystemObject {
    private FileSystemObject linkedObject;

   
    public Link(String name, FileSystemObject linkedObject) {
        this.name = name;
        this.size = 0;
        this.linkedObject = linkedObject;
    }

    // -- display() - display the link information to a string
    public String display(int level) {
        String displayString = "";

        displayString += printHelper.genSpaces(level);
        displayString += (name + "* -> " + linkedObject.getName() + "\n");

        return displayString;
    }
    
    /*** Accessors ***/
    
    public String getName() {
        return name;
    }
   
    public int getSize() {
        return size;
    }

    public FileSystemObject getLinkedObject() {
        return linkedObject;
    }
    
    
	// -- toString - convert link to a string
    public String toString() {
        return "link: " + name + "* -> " + linkedObject.getName() + "\n";
    }

}
