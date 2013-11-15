import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class LinkPanel extends JPanel implements ActionListener{
	
	JPanel sidePanel, middlePanel;
	JRadioButton toFile, toWebsite, toNote;
	//JButton addButton;
	JTextField urlField, nameField;
	ButtonGroup typeButtons;
	String type;
	
	LinkPanel(Color c){
		this.setLayout(new FlowLayout());
		this.setBackground(c);
		
        toFile = new JRadioButton("Link to File");
        toWebsite = new JRadioButton("Link to Website");
        toNote = new JRadioButton("Link to Another Note");
                
        urlField = new JTextField("URL of your link", 30);
        nameField = new JTextField("Then Name your link", 30);
        
        //addButton = new JButton("Add My Link!");
        
        toFile.addActionListener(this);
        toWebsite.addActionListener(this);
        toNote.addActionListener(this);
        urlField.addActionListener(this);
        nameField.addActionListener(this);
        //addButton.addActionListener(this);
        
        typeButtons = new ButtonGroup();
        typeButtons.add(toFile);
        typeButtons.add(toWebsite);
        typeButtons.add(toNote);
		
		sidePanel = new JPanel(new GridLayout(3,1));
        sidePanel.setBackground(c);
        sidePanel.add(toFile);
        sidePanel.add(toWebsite);
        sidePanel.add(toNote);
        
        urlField = new JTextField("URL of your link", 30);
        nameField = new JTextField("Then Name your link", 30);
        
        middlePanel = new JPanel(new GridLayout(2,1));
        middlePanel.setBackground(c);
        middlePanel.add(urlField);
        middlePanel.add(nameField);
        
        add(sidePanel);
        add(middlePanel);
		
	}
	
	public void setColor(Color c){
		this.setBackground(c);
		sidePanel.setBackground(c);
		middlePanel.setBackground(c);
	}
	public String getURL(){
		if(urlField!=null)
			return urlField.getText();
		
		return null;
	}
	public String getName(){
		if(nameField!=null)
			return nameField.getText();
		
		return null;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==toFile)
			type = "file";
		if(e.getSource()==toWebsite)
			type = "site";
		if(e.getSource()==toNote)
			type = "note";		
	}
	
}
