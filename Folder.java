import java.io.Serializable;
import java.util.*;


public class Folder implements Serializable{
    ArrayList<Note> notes;
    String name = "";
    Folder(){
		notes = new ArrayList<Note>();
	}
	Folder(String n){
		name = n;
		notes = new ArrayList<Note>();
	}
	String getName(){
		return name;
	}
    void addNote(Note n){
        notes.add(n);
    }
    
    
}
