package view.panels;

import view.buttons.*;


import view.MIDIPlayerView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/14/13
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private TextButton openButton;
    private TextButton saveButton;
    private TextButton pngMusicButton;
    private JButton backButton;
    private JButton forwardButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JTextField filename_field;
    private JTextField status_field;
    private final JFileChooser fileChooser = new JFileChooser();
    
    /**
     * Filters out midi and png files for now.
     * @author abhishekchatterjee
     * Date: Dec 12, 2015
     * Time: 11:05:50 AM
     */
    public static class MidiOrImageFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			if (f.isDirectory()) {
		        return true;
		    }

		    String extension = getExtension(f);
		    if (extension != null) {
		        if (extension.equals("mid") ||
		            extension.equals("png")) {
		                return true;
		        } else {
		            return false;
		        }
		    }
		    
		    return false;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		
	    private static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
    	
    }
    
    /**
     * Instantiates the PlayerPanel by setting up all the necessary
     * components.
     */
    public PlayerPanel() {
        super();
        setUpMainPanel();
    }

    /**
     * Sets up the panel that is visible by default when launching the application
     * by adding components like the text field for filenames and playback buttons.
     */
    private void setUpMainPanel() {
        setLayout(new GridLayout(3, 1));
        setSize(new Dimension(MIDIPlayerView.FRAME_WIDTH, MIDIPlayerView.FRAME_HEIGHT));
        setUpTextField();
        setUpStatusField();
        setUpPlaybackButtons();
    }

    /**
     * Sets up the panel containing the text field for filenames.
     */
    private void setUpTextField() {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new FlowLayout());
        textFieldPanel.setSize(new Dimension(500, 70));
        //createTextField(textFieldPanel);
        setUpTextFieldButtons(textFieldPanel);
        textFieldPanel.setVisible(true);
        add(textFieldPanel);
    }

    /**
     * ACtually creates the text field.
     * @param textFieldPanel
     */
//    private void createTextField(JPanel textFieldPanel) {
//        filename_field = new JTextField(10);
//        textFieldPanel.add(filename_field);
//    }

    /**
     * Sets up the panel for the status field.
     */
    private void setUpStatusField() {
        JPanel statusFieldPanel = new JPanel();
        statusFieldPanel.setLayout(new FlowLayout());
        statusFieldPanel.setSize(new Dimension(500, 70));
        createStatusField(statusFieldPanel);
        statusFieldPanel.setVisible(true);
        add(statusFieldPanel);
    }

    /**
     * Sets up the status field.
     * @param statusFieldPanel
     */
    private void createStatusField(JPanel statusFieldPanel) {
        status_field = new JTextField(30);
        status_field.setEditable(false);
        status_field.setBackground(UIManager.getColor("Panel.background"));
        status_field.setText("No file is loaded");
        statusFieldPanel.add(status_field);
    }

    /**
     * Sets up buttons for the text field.
     * @param textFieldPanel
     */
    private void setUpTextFieldButtons(JPanel textFieldPanel) {
        openButton = new TextButton("Open", new Dimension(35, 35));
        textFieldPanel.add(openButton);
        saveButton = new TextButton("Save", new Dimension(35, 35));
        textFieldPanel.add(saveButton);
        pngMusicButton = new TextButton("Convert to PNG", new Dimension(60, 35));
        textFieldPanel.add(pngMusicButton);
    }

    /**
     * Returns the text that is in the text field.
     * @return
     */
    public String getTextFieldText() {
        return filename_field.getText();
    }

    public void setStatusFieldText(String status) {
        status_field.setText(status);
    }

    /**
     * Sets up the control buttons used in the midi player.
     */
    private void setUpPlaybackButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setSize(new Dimension(500, 70));

        backButton = new PlaybackButton("ViewImages/rewindtostart.png");
        buttonsPanel.add(backButton);

        playButton = new PlaybackButton("ViewImages/play.png");
        buttonsPanel.add(playButton);
        pauseButton = new PlaybackButton("ViewImages/pause.png");
        buttonsPanel.add(pauseButton);
        stopButton = new PlaybackButton("ViewImages/stop.png");
        buttonsPanel.add(stopButton);

        forwardButton = new PlaybackButton("ViewImages/skiptoend.png");
        buttonsPanel.add(forwardButton);

        buttonsPanel.setVisible(true);
        add(buttonsPanel);
    }
    
    /**
     * Opens the file chooser.
     */
    public void openFileChooser() {
        fileChooser.addChoosableFileFilter(new MidiOrImageFilter());
    	fileChooser.setAcceptAllFileFilterUsed(false);
    	fileChooser.showOpenDialog(this);
    }
    
    /**
     * Opens the file chooser in save mode or whatever.
     */
    public void openFileSaver() {
    	fileChooser.addChoosableFileFilter(new MidiOrImageFilter());
    	fileChooser.setAcceptAllFileFilterUsed(false);
    	fileChooser.showSaveDialog(this);
    }
    
    /**
     * Returns the file selected in the file chooser.
     * @return
     */
    public File getSelectedFile() {
    	return fileChooser.getSelectedFile();
    }

    /**
     * Adds the ActionListener through which the controller interacts with
     * the open button.
     * @param openAction
     */
    public void addOpenActionListener(ActionListener openAction) {
        openButton.addActionListener(openAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts with
     * the open button.
     * @param saveAction
     */
    public void addSaveActionListener(ActionListener saveAction) {
        saveButton.addActionListener(saveAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts
     * with the pngMusic button.
     * @param pngMusicAction
     */
    public void addPNGMusicActionListener(ActionListener pngMusicAction) {
        pngMusicButton.addActionListener(pngMusicAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts with
     * the play button.
     * @param playAction
     */
    public void addPlayActionListener(ActionListener playAction) {
        playButton.addActionListener(playAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts
     * with the pause button.
     * @param pauseAction
     */
    public void addPauseActionListener(ActionListener pauseAction) {
        pauseButton.addActionListener(pauseAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts
     * with the stop button.
     * @param stopAction
     */
    public void addStopActionListener(ActionListener stopAction) {
        stopButton.addActionListener(stopAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts with
     * the back button.
     * @param backAction
     */
    public void addBackActionListener(ActionListener backAction) {
        backButton.addActionListener(backAction);
    }

    /**
     * Adds the ActionListener through which the controller interacts with
     * the forward button.
     * @param forwardAction
     */
    public void addForwardActionListener(ActionListener forwardAction) {
        forwardButton.addActionListener(forwardAction);
    }
}
