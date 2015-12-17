package view.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

/**
 * A frame intended to be used as the main frame of the application.
 * @author abhishekchatterjee
 * @date Dec 17, 2015
 * @time 1:20:40 PM
 */
public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Menu items
	private JMenuItem openMenuItem;		//File > Open
	private JMenuItem saveMenuItem;		//File > Save As
	private JMenuItem quitMenuItem;		//File > Quit
	private JMenuItem playerMenuItem;	//View > Music Player
	private JMenuItem recordMenuItem;	//View > Record Music

	public ApplicationFrame(int width, int height) {
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		this.setTitle("MIDI Player");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("ViewImages/music_icon.png"));
		this.setUpMenuBar();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Sets up a menu bar at the top of the frame.
	 */
	public void setUpMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save As"); 
        quitMenuItem = new JMenuItem("Quit");
        fileMenu.add(openMenuItem);     
        fileMenu.add(saveMenuItem);
        fileMenu.add(quitMenuItem);
        
        JMenu viewMenu = new JMenu("View");
        playerMenuItem = new JMenuItem("Music Player");
        recordMenuItem = new JMenuItem("Record Music");
        viewMenu.add(playerMenuItem);
        viewMenu.add(recordMenuItem);

        menubar.add(fileMenu);
        menubar.add(viewMenu);
        this.setJMenuBar(menubar);
	}
	
	public void addSaveActionListener(ActionListener actionListener) {
		this.saveMenuItem.addActionListener(actionListener);
	}
	
	public void addOpenActionListener(ActionListener actionListener) {
		this.openMenuItem.addActionListener(actionListener);
	}
	
	public void addQuitActionListener(ActionListener actionListener) {
		this.quitMenuItem.addActionListener(actionListener);
	}
	
	public void addPlayerActionListener(ActionListener actionListener) {
		this.playerMenuItem.addActionListener(actionListener);
	}
	
	public void addRecordActionListener(ActionListener actionListener) {
		this.recordMenuItem.addActionListener(actionListener);
	}

}
