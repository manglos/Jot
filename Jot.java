import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.geom.*;
import java.util.*;
import java.util.Date;
import com.toedter.calendar.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.UIManager.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileReader;
import javax.swing.colorchooser.*;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.*;


public class Jot extends JFrame implements Serializable, ActionListener{
    
    static int noteNum;
    int y;
    //MouseListener mouseListener;
    JLabel feedbackLabel, dateLabel;
    String feedback = "";
    JCalendar calendar;
    Action boldAction, italicAction;
    Keymap parent, map;
    KeyStroke boldStroke, italicStroke;
    Note currentNote;
    //JXDatePicker calendar;
    ArrayList<Note> text  = new ArrayList<Note>();
    ArrayList<String> path = new ArrayList<String>();
    ImageIcon settingsW, settings, lensW, lens, back, forward, boldIcon, italicIcon, jotMagentaIcon, jotBlueIcon, jotRedIcon, jotPurpleIcon, jotYellowIcon, jotOrangeIcon, jotPinkIcon, jotGreenIcon;
    JPanel bottomPanel,cc, topPanel, startButtonPanel, notePanel, editButtonsPanel, addLinkSidePanel, addLinkMiddlePanel, filePanel, mainPanel, calendarPanel, calendarBottomPanel, settingsPanel, navPanel, addLinkPanel, welcomePanel, blankPanel, searchPanel, display;
    JButton bold, italics, saveButton, newButton, calButton, settingsButton, addLinkButton, makeLinkButton, startButton, searchButton, backButton, forwardButton, jotMagentaButton, jotBlueButton, jotRedButton, jotPurpleButton, jotYellowButton, jotOrangeButton, jotPinkButton, jotGreenButton;
    JRadioButton toFile, toWebsite, toNote;
    SimpleAttributeSet sas;
    static File jot = new File(System.getProperty("user.home")+System.getProperty("file.separator")+".jot");
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    //Rectangle2D.Float folderBox, noteBox;
	JotHeader header;
    //FolderPanel folderPanel;
    JLayeredPane layerPane;
    JLabel folderLabel;
    JTextField searchField, urlField, nameField, titleField;
    Color jotColor, jotMagenta, jotBlue, jotRed, jotPurple, jotYellow, jotOrange, jotPink, jotGreen;
    JList dateNotes, searchResults;
    Date currentDate;
    JScrollPane textScroll;
    JLabel settingsIcon, welcomeIcon;
    boolean[] isClicked;
    //JColorChooser cc;
    String last;
    TextPrompt tp, tp1, tp2, tp3;
    MyHTMLEditorKit kit;
    StyleSheet ss;
    
    public Jot(String name) {
        super(name);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400,650));
        setMinimumSize(new Dimension(300, 500));
        setResizable(true);        
        
        setColors();
        jotColor = new Color(0x748BE8);
        
        isClicked = new boolean[5];
        for(int i = 0; i<isClicked.length;i++)
			isClicked[i]=false;
			
		try{
			lensW = new ImageIcon(ImageIO.read(new File("lens-w.png")));
			lens = new ImageIcon(ImageIO.read(new File("lens.png")));
			settingsW = new ImageIcon(ImageIO.read(new File("settings-w.png")));
			settings = new ImageIcon(ImageIO.read(new File("settings.png")));
			back = new ImageIcon(ImageIO.read(new File("back.png")));
			forward = new ImageIcon(ImageIO.read(new File("forward.png")));
			jotMagentaIcon = new ImageIcon(ImageIO.read(new File("jotMagenta.png")));
			jotBlueIcon = new ImageIcon(ImageIO.read(new File("jotBlue.png")));
			jotGreenIcon = new ImageIcon(ImageIO.read(new File("jotGreen.png")));
			jotRedIcon = new ImageIcon(ImageIO.read(new File("jotRed.png")));
			jotYellowIcon = new ImageIcon(ImageIO.read(new File("jotYellow.png")));
			jotOrangeIcon = new ImageIcon(ImageIO.read(new File("jotOrange.png")));
			jotPinkIcon = new ImageIcon(ImageIO.read(new File("jotPink.png")));
			jotPurpleIcon = new ImageIcon(ImageIO.read(new File("jotPurple.png")));
			boldIcon = new ImageIcon(ImageIO.read(new File("bold.gif")));
			italicIcon = new ImageIcon(ImageIO.read(new File("italic.gif")));
			
		}catch(Exception e){}

		startButtonPanel = new JPanel();
		startButton = new JButton("Get Started");
		startButton.addActionListener(this);
		startButton.setToolTipText("Start making notes");
		//startButton.setPreferredSize(new Dimension(50,50));
		startButtonPanel.add(startButton);
		startButtonPanel.setBackground(jotColor);
		welcomeIcon = new JLabel();
        welcomeIcon.setIcon(new ImageIcon("jotstart.png"));
        welcomeIcon.setBackground(jotColor);
        welcomeIcon.setOpaque(true);
        welcomeIcon.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.add(welcomeIcon, BorderLayout.CENTER);  
        welcomePanel.add(startButtonPanel, BorderLayout.PAGE_END);

        settingsButton = new JButton("");
        settingsButton.setPreferredSize(new Dimension(35, 35));
        settingsButton.setBackground(jotColor);
        settingsButton.setIcon(settingsW);
        settingsButton.setToolTipText("Adjust settings for Jot");
        
        //settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        searchButton = new JButton("");
        searchButton.setPreferredSize(new Dimension(25, 25));
        searchButton.setBackground(jotColor);
        searchButton.setBorderPainted(false);
        searchButton.setIcon(lens);
        searchButton.setToolTipText("Search your notes");
        //settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        
        
        try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("GTK+".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
        
        sas = new SimpleAttributeSet();
        display = new JPanel(new CardLayout());
        
        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setPreferredSize(new Dimension(300, 300));
        calendarBottomPanel = new JPanel(new BorderLayout());
        calendar = new JCalendar();
        calendar.setFont(new Font("serif", Font.BOLD, 16));
        calendar.setPreferredSize(new Dimension(300, 300));
        calendar.setWeekOfYearVisible(false);
        calendar.addPropertyChangeListener(new DateChangeListener());
        calendar.setForeground(jotColor);
        calendar.setDecorationBackgroundColor(jotColor);
        calendar.setDecorationBackgroundVisible(true);
        calendar.setDecorationBordersVisible(true);
        
        jotMagentaButton = new JButton();
        jotBlueButton = new JButton();
        jotGreenButton = new JButton();
        jotRedButton = new JButton();
        jotYellowButton = new JButton();
        jotOrangeButton = new JButton();
        jotPinkButton = new JButton();
        jotPurpleButton = new JButton();
        
        jotMagentaButton.setIcon(jotMagentaIcon);
        jotBlueButton.setIcon(jotBlueIcon);
        jotGreenButton.setIcon(jotGreenIcon);
        jotRedButton.setIcon(jotRedIcon);
        jotYellowButton.setIcon(jotYellowIcon);
        jotOrangeButton.setIcon(jotOrangeIcon);
        jotPinkButton.setIcon(jotPinkIcon);
        jotPurpleButton.setIcon(jotPurpleIcon);
        
        jotMagentaButton.addActionListener(this);
        jotBlueButton.addActionListener(this);
        jotGreenButton.addActionListener(this);
        jotRedButton.addActionListener(this);
        jotYellowButton.addActionListener(this);
        jotOrangeButton.addActionListener(this);
        jotPinkButton.addActionListener(this);
        jotPurpleButton.addActionListener(this);
        
        cc = new JPanel(new FlowLayout());
        cc.setBackground(jotColor);
        cc.add(jotMagentaButton);
        cc.add(jotBlueButton);
        cc.add(jotRedButton);
        cc.add(jotGreenButton);
        cc.add(jotYellowButton);
        cc.add(jotOrangeButton);
        cc.add(jotPinkButton);
        cc.add(jotPurpleButton);
        
        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBackground(jotColor);
        settingsPanel.add(cc, BorderLayout.PAGE_START);
        
        toFile = new JRadioButton("Link to File", true);
        toFile.setToolTipText("Create a file link");
        toWebsite = new JRadioButton("Link to Website");
        toWebsite.setToolTipText("Create a web link");
        toNote = new JRadioButton("Link to Another Note");
        toNote.setToolTipText("Create link to another note");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(toFile);
        bg.add(toWebsite);
        bg.add(toNote);
        
        notePanel = new JPanel(new BorderLayout());
        
        addLinkSidePanel = new JPanel(new GridLayout(3,1));
        addLinkSidePanel.setBackground(jotColor);
        addLinkSidePanel.add(toFile);
        addLinkSidePanel.add(toWebsite);
        addLinkSidePanel.add(toNote);
        
        urlField = new JTextField(30);
        tp1 = new TextPrompt("URL of your link", urlField);
		tp1.changeAlpha(0.5f);
		tp1.changeStyle(Font.BOLD + Font.ITALIC);
        nameField = new JTextField(30);
        tp2 = new TextPrompt("Name of your link", urlField);
		tp2.changeAlpha(0.5f);
		tp2.changeStyle(Font.BOLD + Font.ITALIC);
        
        makeLinkButton = new JButton("Make Link");
        makeLinkButton.addActionListener(this);
        addLinkMiddlePanel = new JPanel(new GridLayout(2,1));
        addLinkMiddlePanel.setBackground(jotColor);
        addLinkMiddlePanel.add(urlField);
        addLinkMiddlePanel.add(nameField);
        addLinkMiddlePanel.add(makeLinkButton);

        
        addLinkPanel = new JPanel(new FlowLayout());
        addLinkPanel.setBackground(jotColor);
        addLinkPanel.add(addLinkSidePanel);
        addLinkPanel.add(addLinkMiddlePanel);
        
        
        MouseListener mouseListener = new MouseAdapter() {
		  public void mouseClicked(MouseEvent mouseEvent) {
			  CardLayout c = (CardLayout) display.getLayout();
			//JList theList = (JList) mouseEvent.getSource();
			if (mouseEvent.getClickCount() == 2) {
				String selectedItem = (String) dateNotes.getSelectedValue();
				BorderLayout layout = (BorderLayout)notePanel.getLayout();
				notePanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				Note n = getNote(selectedItem);
				textScroll = new JScrollPane(n);
				textScroll.setVisible(true);
				//notePanel.remove(BorderLayout.CENTER);
				if(selectedItem!=null)notePanel.add(textScroll, BorderLayout.CENTER);
				//if(selectedItem!=null)display.add(getNote(selectedItem), "TP");
				//System.out.println("clicked 2");
				c.show(display, "TP");
				isClicked[3]=false;
				calButton.setText("Calendar");
				path.add("TP");
				setButtons(0);
			}
		  }
		};
		MouseListener mouseListener2 = new MouseAdapter() {
		  public void mouseClicked(MouseEvent mouseEvent) {
			  CardLayout c = (CardLayout) display.getLayout();
			//JList theList = (JList) mouseEvent.getSource();
			if (mouseEvent.getClickCount() == 2) {
				String selectedItem = (String) searchResults.getSelectedValue();
				BorderLayout layout = (BorderLayout)notePanel.getLayout();
				notePanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				Note n = getNote(selectedItem);
				textScroll = new JScrollPane(n);
				textScroll.setVisible(true);
				//notePanel.remove(BorderLayout.CENTER);
				if(selectedItem!=null)notePanel.add(textScroll, BorderLayout.CENTER);
				c.show(display, "TP");
				isClicked[3]=false;
				calButton.setText("Calendar");
				path.add("TP");
				setButtons(0);
			}
		  }
		};
		
		map = JTextComponent.getKeymap(JTextComponent.DEFAULT_KEYMAP);
		boldStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK, false);
		italicStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK, false);
		map.addActionForKeyStroke(boldStroke, new StyledEditorKit.BoldAction());
		map.addActionForKeyStroke(italicStroke, new StyledEditorKit.ItalicAction());
		
		searchResults = new JList();
		searchResults.addMouseListener(mouseListener2);
		searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(jotColor);
        searchField = new JTextField(40);
        searchField.getDocument().addDocumentListener(documentListener);
        tp3 = new TextPrompt("Start typing to search your notes", searchField);
		tp3.changeAlpha(0.5f);
		tp3.changeStyle(Font.BOLD + Font.ITALIC);
        searchPanel.add(searchField, BorderLayout.PAGE_START);
        searchPanel.add(searchResults, BorderLayout.CENTER);
        
        currentDate = calendar.getDate();
        dateNotes = new JList();
        dateNotes.addMouseListener(mouseListener);
        
        dateLabel = new JLabel();
        
        calendarPanel.add(calendar, BorderLayout.PAGE_START);
        calendarPanel.add(dateNotes, BorderLayout.CENTER);
        //calendarPanel.add(dateNotes, BorderLayout.PAGE_END);
               
        
        
        bold = new JButton(boldIcon);
        boldAction = new StyledEditorKit.BoldAction();
        //boldAction.putValue(Action.SMALL_ICON, new ImageIcon(new URL(getCodeBase(), "bold.gif")));
        bold.setAction(boldAction);
        //bold.setPreferredSize(new Dimension(25, 25));
        bold.setBackground(jotColor);
        bold.setBorderPainted(false);
        bold.setIcon(boldIcon);
        bold.setText("");
        italics = new JButton(italicIcon);
        italicAction = new StyledEditorKit.ItalicAction();
        //boldAction.putValue(Action.SMALL_ICON, new ImageIcon(new URL(getCodeBase(), "bold.gif")));
        italics.setAction(italicAction);
        //italics.setPreferredSize(new Dimension(25, 25));
        italics.setBackground(jotColor);
        italics.setBorderPainted(false);
        italics.setIcon(italicIcon);
        italics.setText("");
        editButtonsPanel = new JPanel(new FlowLayout());
        editButtonsPanel.add(bold);
        editButtonsPanel.add(italics);
        editButtonsPanel.setPreferredSize(new Dimension(30, 30));
        
        notePanel.add(editButtonsPanel, BorderLayout.PAGE_END);
        
        saveButton = new JButton("Save");
        saveButton.setVisible(false);
        saveButton.setToolTipText("Save your Jot");
        calButton = new JButton("Calendar");
        calButton.setToolTipText("Go to the Calendar");
        feedbackLabel = new JLabel(feedback);
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addLinkButton = new JButton("Add Link");
        addLinkButton.setToolTipText("Create a new Link");
        newButton = new JButton("New Note");
        newButton.setVisible(false);
        newButton.setToolTipText("Create a new note");
        
        backButton = new JButton("");
        backButton.setPreferredSize(new Dimension(25, 25));
        backButton.setBackground(jotColor);
        backButton.setBorderPainted(false);
        backButton.setIcon(back);
        backButton.setToolTipText("Go Back");
        //backButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        forwardButton = new JButton("");
        forwardButton.setPreferredSize(new Dimension(25, 25));
        forwardButton.setBackground(jotColor);
        forwardButton.setBorderPainted(false);
        //forward.setPreferredSize(new Dimension(25,25));
        forwardButton.setIcon(forward);
        forwardButton.setToolTipText("Go Forward");
        //forwardButton.setOpaque(true);
        //forwardButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        //bold.addActionListener(this);
        //italics.addActionListener(this);
        saveButton.addActionListener(this);
        calButton.addActionListener(this);
        settingsButton.addActionListener(this);
        addLinkButton.addActionListener(this);
        searchButton.addActionListener(this);
        backButton.addActionListener(this);
        forwardButton.addActionListener(this);
        newButton.addActionListener(this);
        
        //JButton altSettingsButton = (JButton) settingsButton.clone();
        //calendarBottomPanel.add(altSettingsButton);
        
        navPanel = new JPanel();
        navPanel.setBackground(jotColor);
        navPanel.add(backButton);
        navPanel.add(forwardButton);
        
        
        topPanel = new JPanel(new BorderLayout());
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(jotColor);

        topPanel.add(navPanel, BorderLayout.LINE_START);
        topPanel.add(feedbackLabel, BorderLayout.CENTER);
        //topPanel.add(forwardButton, BorderLayout.LINE START);
        topPanel.add(searchButton, BorderLayout.LINE_END);
        
        //navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //searchButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        mainPanel = new JPanel();
        mainPanel.add(settingsButton);
        mainPanel.add(calButton);
        //mainPanel.add(bold);
        //mainPanel.add(italics);
        mainPanel.add(saveButton);
		mainPanel.add(newButton);	
        mainPanel.add(addLinkButton);
        //mainPanel.add(searchButton);
        mainPanel.setBackground(jotColor);
        
        /*&filePanel = new JPanel();
        filePanel.add(settingsButton);
        filePanel.add(calButton);
        filePanel.add(newButton);*/
        
        bottomPanel = new JPanel(new CardLayout());
        blankPanel = new JPanel();
        blankPanel.setBackground(jotColor);
        
        bottomPanel.add(blankPanel, "BP");
        bottomPanel.add(mainPanel, "MP");

		header = createFile();
		
		if(header!=null){
			text = header.getNotes();
			setColor(header.getColor());
			if(header.getKit()!=null){
				System.out.println("getting kit");
				kit = header.getKit();
				ss = header.getStyleSheet();
				//Note.ss.addStyleSheet(ss);
			}
		}	
		
        Note startup = new Note();
        textScroll = new JScrollPane(startup);
        //textScroll.setPreferredSize(new Dimension(300, 250));
        textScroll.setVisible(true);
        
        if(ss!=null)
			Note.ss.addStyleSheet(ss);
			
        startup.setText(fileToString("startup.html"));
        
        notePanel.add(textScroll, BorderLayout.CENTER);
        
        display.add(welcomePanel, "WP");
        display.add(notePanel, "TP");
		display.add(calendarPanel, "CP");
		display.add(settingsPanel, "SP");
		display.add(addLinkPanel, "AP");
		display.add(searchPanel, "XP");	

		setButtons(3);
        
        add(topPanel, BorderLayout.PAGE_START);
        add(display, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        
    }
    DocumentListener documentListener = new DocumentListener() {
		public void changedUpdate(DocumentEvent documentEvent) {
			ArrayList<String> results = search(searchField.getText());
			searchResults.setListData(results.toArray());
		}
		public void insertUpdate(DocumentEvent documentEvent) {
			ArrayList<String> results = search(searchField.getText());
			searchResults.setListData(results.toArray());
			
		}
		public void removeUpdate(DocumentEvent documentEvent) {
			ArrayList<String> results = search(searchField.getText());
			searchResults.setListData(results.toArray());
		}
		private void printIt(DocumentEvent documentEvent) {
			DocumentEvent.EventType type = documentEvent.getType();
			String typeString = null;
			if (type.equals(DocumentEvent.EventType.CHANGE)) {
				typeString = "Change";
			}  else if (type.equals(DocumentEvent.EventType.INSERT)) {
				typeString = "Insert";
			}  else if (type.equals(DocumentEvent.EventType.REMOVE)) {
				typeString = "Remove";
			}
			System.out.print("Type : " + typeString);
			Document source = documentEvent.getDocument();
			int length = source.getLength();
			System.out.println("Length: " + length);
		}
	};
    
    public void setColor(Color c) {
        jotColor = c;
        //settingsPanel.revalidate();
        mainPanel.setBackground(jotColor);
        settingsButton.setBackground(jotColor);
        settingsPanel.setBackground(jotColor);
        searchButton.setBackground(jotColor);
        searchPanel.setBackground(jotColor);
        calendar.setForeground(jotColor);
        calendar.setDecorationBackgroundColor(jotColor);
        calendar.setBackground(jotColor);
        topPanel.setBackground(jotColor);
        navPanel.setBackground(jotColor);
        backButton.setBackground(jotColor);
        forwardButton.setBackground(jotColor);
        addLinkPanel.setBackground(jotColor);
        addLinkSidePanel.setBackground(jotColor);
        addLinkMiddlePanel.setBackground(jotColor);
        welcomePanel.setBackground(jotColor);
        welcomeIcon.setBackground(jotColor);
        startButtonPanel.setBackground(jotColor);
        cc.setBackground(jotColor);
        cc.setForeground(jotColor);
        blankPanel.setBackground(jotColor);
    }
    
    void setDefaults(){
		Note imageNote = new Note("My Image Note", 2012, 11, 1);
		Note linkNote = new Note("My Link Note", 2012, 11, 4);
		Note holloweenNote = new Note("My Holloween Note", 2012, 10, 31);
		Note monkeyNote = new Note("Monkey", 2012, 11, 1);
		
		imageNote.setText(fileToString("image-note.html"));
		
		linkNote.addH2("Date: " + linkNote.month + "/" + linkNote.day + "/" + linkNote.year + "/");
		//linkNote.setText(fileToString("link-note.html"));
		linkNote.addText("A powerful feature of Jot is linking. Links, just like in webpages, allow the user to navigate to different resources, media, and pages at the click of a button. In the final version of Jot, links will be color coded according to the content they link to.");
		linkNote.addLine();
		linkNote.addH2("Here are some examples of links");
		
		linkNote.addLink("http://cs.oswego.edu/~alex/", "A great website");
		linkNote.addLine();
		linkNote.addFileLink("monalisa.jpg", "A picture of the Mona Lisa");
		linkNote.addLine();
		linkNote.addFileLink("oldtimeradio.m3u", "Old Time Radio Station");
		linkNote.addLine();
				
		monkeyNote.addH2("This is my note all about monkies.");
		monkeyNote.addLink("http://en.wikipedia.org/wiki/Monkey", "Monkey Wiki");
		monkeyNote.addLine();
		monkeyNote.addFileLink("monkey.jpg", "A Picture of a Monkey");
		monkeyNote.addLine();
		monkeyNote.addText("Monkeys are fantastic creatures who incidentally have the unfortunate innate desire to fling their own poo. <br><br>They have developed noses such that they can also wear funny glasses.");
		monkeyNote.addFileLink("karate-monkey.avi", "This monkey knows karate...");
		
		text.add(imageNote);
		text.add(linkNote);
		text.add(holloweenNote);
		text.add(monkeyNote);
	}
	
	void setColors(){
		jotMagenta = new Color(0x932c4a);
		jotBlue = new Color(0x748BE8);
		jotGreen = new Color(0x74E874);
		jotRed = new Color(0xE8747E);
		jotYellow = new Color(0xE6E874);
		jotOrange = new Color(0xE8BC74);
		jotPurple = new Color(0xC474E8);
		jotPink = new Color(0xE874C0);
	}
    
    private static void createAndShowGUI() {
        Jot frame = new Jot("Jot - Simply Powerful Notes");
        
		frame.setLocation(400, 70);
		
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
	
    Note getNote(String t){
				
		for(Note n : text){
			if(n.title.equals(t)){
				n.setKeymap(map);
				currentNote=n;
				textScroll = new JScrollPane(n);
				//textScroll.setPreferredSize(new Dimension(300, 250));
				textScroll.setVisible(true);
				return n;
			}
		}
		return null;
	}
	
	ArrayList<String> search(String s){
		ArrayList<String> results = new ArrayList<String>();
		
		for(Note n : text){
			if(n.getText().toLowerCase().indexOf(s.toLowerCase()) > -1)
				results.add(n.title);
		}
		
		return results;
	}
    
    void setDateNotes(int y, int m, int d){
		ArrayList<String> noteStrings = new ArrayList<String>();
		
		for(Note n : text)
			if((y==n.year) && (m==n.month) && (d==n.day))
				noteStrings.add(n.title);
				
		dateNotes.setListData(noteStrings.toArray());
	}
	
	public static String fileToString(String file){
		String contents = "";

		File f = null;
		try
		{
		  f = new File(file);

		  if (f.exists())
		  {
			  FileReader fr = null;
			  try
			  {
				  fr = new FileReader(f);
				  char[] template = new char[(int) f.length()];
				  fr.read(template);
				  contents = new String(template);
			  }
			  catch (Exception e)
			  {
				  e.printStackTrace();
			  }
			  finally
			  {
				  if (fr != null)
				  {
					  fr.close();
				  }
			  }
		  }
		}
		catch (Exception e)
		{
		  e.printStackTrace();
		}
		return contents;
		}
    
    public JotHeader createFile(){
        FileInputStream fis;
        ObjectInputStream ois;
        try{
            fis = new FileInputStream(jot);
            ois = new ObjectInputStream(fis);
            JotHeader h = (JotHeader)ois.readObject();
            System.out.println("Previous session loaded!");
            return h;
        }
        catch(Exception ex){
            System.out.println("No previous session found.");
            return null;
        }
    }
    
    class DateChangeListener implements PropertyChangeListener{
		
		public void propertyChange(PropertyChangeEvent e){
			Calendar c = calendar.getCalendar();
				
			String propertyName = e.getPropertyName();
			
			if(propertyName.equals("calendar")){
				setDateNotes(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
				feedbackLabel.setText(getMonthString(c.get(Calendar.MONTH)) + " " +c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR));
				calendarPanel.add(dateNotes, BorderLayout.CENTER);
				//calendarPanel.revalidate();
			}
		}
	
	}
	public String getMonthString(int m){
		switch(m){
			case 0:
				return "January";
			case 1:
				return "February";
			case 2:
				return "March";
			case 3:
				return "April";
			case 4:
				return "May";
			case 5:
				return "June";
			case 6:
				return "July";
			case 7:
				return "August";
			case 8:
				return "September";
			case 9:
				return "October";
			case 10:
				return "November";
			case 11:
				return "December";
		}
		
		return null;
	}
	void setButtons(int x){
		if(x==0){//edit mode
			backButton.setVisible(true);
			forwardButton.setVisible(true);
			searchButton.setVisible(true);
			//bold.setVisible(true);
			//italics.setVisible(true);
			addLinkButton.setVisible(true);
			newButton.setVisible(false);
			saveButton.setVisible(true);
		}
		if(x==1){//calendar mode
			backButton.setVisible(true);
			forwardButton.setVisible(true);
			searchButton.setVisible(true);
			//bold.setVisible(false);
			//italics.setVisible(false);
			addLinkButton.setVisible(false);
			newButton.setVisible(true);
		}
		if(x==2){//search mode
			backButton.setVisible(true);
			forwardButton.setVisible(true);
			searchButton.setVisible(true);
			//bold.setVisible(false);
			//italics.setVisible(false);
			addLinkButton.setVisible(false);
			newButton.setVisible(false);
			calButton.setVisible(true);
		}
		if(x==3){//welcome mode
			backButton.setVisible(false);
			forwardButton.setVisible(false);
			searchButton.setVisible(false);
		}
    }
    public void actionPerformed(ActionEvent e){
		
		System.out.println("y is " + y+ "\nPath size is " + path.size());
		if(e.getSource()==newButton){
			Calendar cal = calendar.getCalendar();
			CardLayout c = (CardLayout) display.getLayout();
			
			//Note n = new Note("A New Note");
			String heading = getMonthString(cal.get(Calendar.MONTH)) +" "+cal.get(Calendar.DAY_OF_MONTH)+", "+cal.get(Calendar.YEAR);
			//JOptionPane.
			titleField = new JTextField(20);
			tp = new TextPrompt("Enter note title here", titleField);
			tp.changeAlpha(0.5f);
			tp.changeStyle(Font.BOLD + Font.ITALIC);
			
			JOptionPane.showMessageDialog(null, titleField);
			
			Note n = new Note(titleField.getText(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
			kit= n.kit;
			//MyHTMLEditorKit.InsertHTMLTextAction html = new MyHTMLEditorKit.InsertHTMLTextAction("Wiki", "http://www.wikipedia.org", HTML.Tag.A, HTML.Tag.UL);
			ss = kit.getStyleSheet();
			ss.addRule("h1 {color : blue; font-weight: bold;text-decoration:underline;}");
			ss.addRule("h2 {color : green; font-style:italic;}");
			n.setKit(kit, ss);
			n.addH2(heading);
			n.addLine();
			textScroll = new JScrollPane(n);
			//textScroll.setPreferredSize(new Dimension(300, 250));
			textScroll.setVisible(true);
			text.add(n);
			//header.setNotes(text);
			System.out.println("Month: " + cal.get(Calendar.MONTH) + " Day: " + cal.get(Calendar.DAY_OF_MONTH) + " YEAR: " + cal.get(Calendar.YEAR));
			setDateNotes(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
			//dateNotes.revalidate();
		}
		if(e.getSource()==backButton){
			
			if(path.size()-y-1<=0)
				feedbackLabel.setText("Already at the Beginning");
			else{
				y++;
				CardLayout c = (CardLayout) display.getLayout();
				c.show(display, path.get(path.size()-y-1));
				
				//path.remove(path.size()-2);
			}
		}
		if(e.getSource()==forwardButton){
			
			if(y==0)
				feedbackLabel.setText("Already at the End");
			else{
				CardLayout c = (CardLayout) display.getLayout();
				c.show(display, path.get(path.size() - y-1));
				y--;
				//path.remove(path.size()-2);
			}
		}
        if(e.getSource()==startButton){
			CardLayout b = (CardLayout) bottomPanel.getLayout();
			CardLayout d = (CardLayout) display.getLayout();
			b.show(bottomPanel, "MP");
			d.show(display, "TP");
			path.add("WP");
			path.add("TP");
			setButtons(0);
		}
        /*if(e.getSource()==bold){
            String action = (String)e.getActionCommand();
            if(action.equals("Bold")){
                StyleConstants.setBold(sas, true);
                bold.setText("unBold");
                System.out.println(calendar.getDate().toString());
            }
            if(action.equals("unBold")){
                StyleConstants.setBold(sas, false);
                bold.setText("Bold");
            }
        }
        if(e.getSource()==italics){
            String action = (String)e.getActionCommand();
            if(action.equals("Italics")){
                StyleConstants.setItalic(sas, true);
                italics.setText("unItalic");
            }
            if(action.equals("unItalic")){
                StyleConstants.setItalic(sas, false);
                italics.setText("Italics");
            }
        }*/
        if(e.getSource()==saveButton){
			header = new JotHeader(jotColor, text, kit, ss);
			//header.setKit(kit);
            try{
                fos = new FileOutputStream(jot);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(header);
                oos.close();
                feedbackLabel.setText("Saved.");
            }
            catch(Exception ex){
                feedbackLabel.setText("Note could not be saved.");
                ex.printStackTrace();
            }
        }
        if(e.getSource()==calButton){
			CardLayout c = (CardLayout) display.getLayout();
			//CardLayout b = (CardLayout) bottomPanel.getLayout();
            String action = (String)e.getActionCommand();
            if(action.equals("Calendar")){
                c.show(display, "CP");
                setButtons(1);
                //b.show(bottomPanel, "CP");
                calButton.setText("Edit Mode");
                calButton.setToolTipText("Go to Edit Mode");
                path.add("CP");
            }
            if(action.equals("Edit Mode")){
                c.show(display, "TP");
                setButtons(0);
                //b.show(bottomPanel, "MP");
                calButton.setText("Calendar");
                calButton.setToolTipText("Go to Calendar");
                path.add("TP");
            }
        }
		if(e.getSource()==settingsButton){
			CardLayout c = (CardLayout) display.getLayout();
			if(isClicked[2]){
				c.show(display, "TP");
				path.add("TP");
				isClicked[2]=false;
				setButtons(0);
				settingsButton.setIcon(settingsW);
			}
			else{
				c.show(display, "SP");
				path.add("SP");
				setButtons(2);
				calButton.setText("Edit Mode");
				//JOptionPane.showMessageDialog(null, cc);
				isClicked[2]=true;
				
				settingsButton.setIcon(settings);
			}
		}
		if(e.getSource()==searchButton){
			CardLayout c = (CardLayout) display.getLayout();
			if(isClicked[3]){
				c.show(display, "TP");
				path.add("TP");
				isClicked[3]=false;
				setButtons(0);
				searchButton.setIcon(lens);
			}
			else{
				c.show(display, "XP");
				path.add("XP");
				isClicked[3]=true;
				setButtons(2);
				searchButton.setIcon(lensW);
			}
		}	
        if(e.getSource()==addLinkButton){
			CardLayout c = (CardLayout) display.getLayout();
			//JOptionPane.showMessageDialog(addLinkPanel);
			if(isClicked[4]){
				c.show(display, "TP");
				path.add("TP");
				isClicked[4]=false;
			}
			else{
				c.show(display, "AP");
				path.add("AP");
				isClicked[4]=true;
			}
		}
		if(e.getSource()==makeLinkButton){
			CardLayout c = (CardLayout) display.getLayout();
			if(toFile.isSelected()){
				currentNote.addFileLink(urlField.getText(), nameField.getText());
			}
			if(toWebsite.isSelected()){
				currentNote.addLink(urlField.getText(), nameField.getText());
			}
			c.show(display, "TP");
			path.add("TP");
			isClicked[4]=false;
		}
		if(e.getSource()==jotMagentaButton)
			setColor(jotMagenta);
		if(e.getSource()==jotBlueButton)
			setColor(jotBlue);
		if(e.getSource()==jotRedButton)
			setColor(jotRed);
		if(e.getSource()==jotGreenButton)
			setColor(jotGreen);
		if(e.getSource()==jotYellowButton)
			setColor(jotYellow);
		if(e.getSource()==jotOrangeButton)
			setColor(jotOrange);
		if(e.getSource()==jotPinkButton)
			setColor(jotPink);
		if(e.getSource()==jotPurpleButton)
			setColor(jotPurple);
		
    }
    
    
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
