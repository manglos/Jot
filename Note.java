import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.*;
 
public class Note extends JTextPane{
 
    public String myHTML="";
    public String title;
    //public id;
    public int year, month, day;
    MyHTMLEditorKit kit;
    static StyleSheet ss;
    Document doc;
    ImageIcon bgImage;
	//TexturePaint texture;
    //KeyStroke boldStroke, italicStroke;
          
    static final String[] browsers = { "google-chrome", "firefox", "opera",
      "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla" };
 
    boolean isNeedCursorChange=true;
    
    
    
    public Note() {
		super();
		setOpaque(false);
        myHTML = "<html><link rel=\"stylesheet\" type=\"text/css\" href=\"mystyle.css\" media=\"screen\" />\n" +
            "<body>\n";
        
        if(kit==null)    
			kit=new MyHTMLEditorKit();
			
        ss = kit.getStyleSheet();
		/*ss.addRule("h1 {color : green; font-weight: bold;text-decoration:underline;}");
		ss.addRule("h2 {font-style:italic;}");*/
        doc = kit.createDefaultDocument();
        setEditorKit(kit);
        setDocument(doc);
 
        setText(myHTML);
        addHyperlinkListener(new HTMLListener());
		bgImage = new ImageIcon("watermark.png");
		
		/*Keymap map = JTextComponent.addKeymap("FontStyleMap", getKeymap());
		boldStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK, false);
		italicStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK, false);
		map.addActionForKeyStroke(boldStroke, new StyledEditorKit.BoldAction());
		map.addActionForKeyStroke(italicStroke, new StyledEditorKit.ItalicAction());
		setKeymap(map);*/
        //setContentType("text/html");
    }
    public Note(String t) {
		super();
		setOpaque(false);
		title=t;
        myHTML = "<html><title>" + title + "</title><link rel=\"stylesheet\" href=\"mystyle.css\">\n" +
            "<body>\n" + "<h1>" + t + "</h1>";
            
        if(kit==null)    
			kit=new MyHTMLEditorKit();
        ss = kit.getStyleSheet();
		/*ss.addRule("h1 {color : green; font-weight: bold;text-decoration:underline;}");
		ss.addRule("h2 {font-style:italic;}");*/
        doc = kit.createDefaultDocument();
        setEditorKit(kit);
        setDocument(doc);
 
        setText(myHTML);
        addHyperlinkListener(new HTMLListener());
        bgImage = new ImageIcon("watermark.png");
        /*Keymap map = JTextComponent.addKeymap("FontStyleMap", getKeymap());
		boldStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK, false);
		italicStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK, false);
		map.addActionForKeyStroke(boldStroke, new StyledEditorKit.BoldAction());
		map.addActionForKeyStroke(italicStroke, new StyledEditorKit.ItalicAction());
		setKeymap(map);*/
        //setContentType("text/html");
    }
    public Note(String t, int y, int m, int d) {
		super();
		setOpaque(false);
		title=t;
        myHTML = "<html><head><title>" + title + "</title><link rel=\"stylesheet\" href=\"mystyle.css\">\n" +
            "<body>\n" + "<h1>" + title + "</h1>";
        
        year=y;
        month=m;
        day=d;
            
        if(kit==null)    
			kit=new MyHTMLEditorKit();
        ss = kit.getStyleSheet();
        /*ss.addRule("h1 {color : green; font-weight: bold;text-decoration:underline;}");
        ss.addRule("h2 {font-style:italic;}");*/
        doc = kit.createDefaultDocument();
        setEditorKit(kit);
        setDocument(doc);
 
        setText(myHTML);
        addHyperlinkListener(new HTMLListener());
        bgImage = new ImageIcon("watermark.png");
		/*Keymap map = JTextComponent.addKeymap("FontStyleMap", getKeymap());
		boldStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK, false);
		italicStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK, false);
		map.addActionForKeyStroke(boldStroke, new StyledEditorKit.BoldAction());
		map.addActionForKeyStroke(italicStroke, new StyledEditorKit.ItalicAction());
		setKeymap(map);*/
        setContentType("text/html");
    }
    public void setKit(MyHTMLEditorKit k, StyleSheet s){
		kit = k;
		
        doc = kit.createDefaultDocument();
        setEditorKit(kit);
        setDocument(doc);
    }
    
    public void setCursor(Cursor cursor) {
        if (isNeedCursorChange) {
			super.setCursor(cursor);
		}
    }
    
    public void addLink(String fn, String ln){
		//setDocument(doc);
		//setEditorKit(kit);
		try{kit.insertHTML((HTMLDocument)doc, doc.getLength(), "<a href=\"" + fn + "\">" + ln + "</a>", 0, 0, HTML.Tag.A);}
		catch(Exception e){}
		/*HTML += "<a href=\"" + fn + "\">" +ln + "</a>";
		setText(HTML);*/
	}
	
	public void addFileLink(String fn, String ln){
		myHTML += "<a href=\"" + this.getClass().getClassLoader().getResource(fn).toString() + "\">" +ln + "</a>";
		setText(myHTML);
	}
	
	public void addImage(String fn){
		System.out.println("<img src=\"" + this.getClass().getClassLoader().getResource(fn).toString() + "\"/>");
		myHTML += "<img src=\"" + this.getClass().getClassLoader().getResource(fn).toString() + "\" width=\"190\" height=\"281\" />";//"<img src=\"file:///home/uman/Java/CSC420/test/images/" + fn + "\" />";
		setText(myHTML);
	}
	
	public void addLine(){
		myHTML += "<br>";
		setText(myHTML);
	}
	
	public void addH1(String h){
		myHTML += "<h1>" + h + "</h1>";
		setText(myHTML);
	}
	
	public void addH2(String h){
		myHTML += "<h2>" + h + "</h2>";
		setText(myHTML);
	}
	
	public void addText(String h){
		myHTML += "<p>" + h + "</p>";
		setText(myHTML);
	}
	
	public void end(){
		myHTML += "<br></body></HTML>";
		setText(myHTML);
	}
	
	
    //public static void main(String[] args) throws Exception {
        //new URLEditorPane();
    //}
	@Override
	protected void paintComponent(Graphics g) {
		// set background green - but can draw image here too
		//g.setColor(Color.GREEN);
		//g.fillRect(0, 0, getWidth(), getHeight());
		int x = 25;
		int y = getHeight()/3;
		
		g.drawImage(bgImage.getImage(), x, y, null);

		// uncomment the following to draw an image
		// Image img = ...;
		// g.drawImage(img, 0, 0, this);


		super.paintComponent(g);
	}
    private class HTMLListener implements HyperlinkListener, Serializable{
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
				Desktop dt = Desktop.getDesktop();
				//System.out.println(e.getURL().getFile());
				dt.open(new File(e.getURL().getFile()));
				
            } catch (Exception e1) {
				try{
                String browser = null;
				for (String b : browsers)
					if (browser == null && Runtime.getRuntime().exec(new String[]
                        {"which", b}).getInputStream().read() != -1)
						Runtime.getRuntime().exec(new String[] {browser = b, e.getURL().toString()});
				}catch(IOException e2){}
				//Runtime.getRuntime().exec(e.getURL().toString());
            }
        }
      }
    }
    
 
    
}
