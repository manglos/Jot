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

public class MyHTMLEditorKit extends HTMLEditorKit implements Serializable {
		boolean isNeedCursorChange=true;
        MyLinkController handler=new MyLinkController();
        public void install(JEditorPane c) {
            MouseListener[] oldMouseListeners=c.getMouseListeners();
            MouseMotionListener[] oldMouseMotionListeners=c.getMouseMotionListeners();
            super.install(c);
            //the following code removes link handler added by original
            //HTMLEditorKit
			
            
            for (MouseListener l: c.getMouseListeners()) {
                c.removeMouseListener(l);
            }
            for (MouseListener l: oldMouseListeners) {
                c.addMouseListener(l);
            }

            for (MouseMotionListener l: c.getMouseMotionListeners()) {
                c.removeMouseMotionListener(l);
            }
            for (MouseMotionListener l: oldMouseMotionListeners) {
                c.addMouseMotionListener(l);
            }
			c.addMouseListener(handler);
			c.addMouseMotionListener(handler);
            //add out link handler instead of removed one
            
        }
 
        public class MyLinkController extends LinkController implements Serializable{

            public void mouseClicked(MouseEvent e) {
                JEditorPane editor = (JEditorPane) e.getSource();
				//editor.setEditable(true);
				
                if (editor.isEditable() && SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getClickCount()==1) {
                        editor.setEditable(false);
                        super.mouseClicked(e);
                        editor.setEditable(true);
                    }
                }

            }
            public void mouseMoved(MouseEvent e) {
                JEditorPane editor = (JEditorPane) e.getSource();
 
                if (editor.isEditable()) {
                    //editor.setEditable(false);
                    super.mouseMoved(e);
                    isNeedCursorChange=true;
                    editor.setEditable(true);
                }
            }

        }
    }
