import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.text.html.StyleSheet;

public class JotHeader implements Serializable{
	protected Color myColor;
	protected ArrayList<Note> myNotes;
	protected String lastPanel = null;
	protected Note lastNote = null;
	MyHTMLEditorKit kit;
	StyleSheet ss;

	JotHeader(Color c, ArrayList<Note> n, MyHTMLEditorKit k, StyleSheet s){
		myColor = c;
		myNotes = n;
		kit = k;
		ss = s;
	}
	
	Note getLastNote(){
		return lastNote;
	}
	
	ArrayList<Note> getNotes(){
		return myNotes;
	}
	
	void setNotes(ArrayList<Note> n){
		myNotes = n;
	}
	
	void setKit(MyHTMLEditorKit k){
		kit = k;
	}
	
	MyHTMLEditorKit getKit(){
		return kit;
	}
	
	StyleSheet getStyleSheet(){
		return ss;
	}
	
	Color getColor(){
		return myColor;
	}
	void setColor(Color c){
		myColor = c;
	}
	
	String getLastPanel(){
		return lastPanel;
	}
}
