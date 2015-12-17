package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import view.frame.ApplicationFrame;
import view.frame.ImageFrame;
import view.panel.PlayerPanel;
import view.panel.RecordingPanel;

public class MIDIPlayerView {
    private final PlayerPanel playerPanel = new PlayerPanel();
    private final RecordingPanel recordingPanel = new RecordingPanel();
    
    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGHT = 250;
    
    private String instrument = null;
    private final ApplicationFrame applicationWindow = new ApplicationFrame(FRAME_WIDTH, FRAME_HEIGHT);
    private ImageFrame imageFrame = null;

    /**
     * Instantiates the JFrame with buttons and other objects.
     */
    public MIDIPlayerView() {
        setUpMenuBar();
        setUpPlayerPanel();
        setUpRecordingPanel();
        applicationWindow.setVisible(true);
    }

    /**
     * Sets up the panel for the MIDI player that is also the default panel
     * that is loaded when the application starts.
     */
    private void setUpPlayerPanel() {
        playerPanel.setVisible(true);
        applicationWindow.getContentPane().add(playerPanel);
    }

    /**
     * Sets up the panel on which users get to record music.
     */
    private void setUpRecordingPanel() {
        recordingPanel.setVisible(true);
    }


    /**
     * Creates a menubar with the aid of which the user can switch between the
     * main player and the user recorded music part of the application.
     */
    public void setUpMenuBar() {
    	this.applicationWindow.setUpMenuBar();
    	this.addPlayerActionListener();
    	this.addRecordActionListener();
    	this.addQuitActionListener();
    }

    public void displayMessageBox(String displayText, int type) {
        JOptionPane.showMessageDialog(null, displayText, null, type);
    }

    /**
     * Opens a new window displaying the result of the midi to image conversion.
     * @param image
     */
    public void displayImage(Image image) {
        imageFrame = new ImageFrame(image);
        imageFrame.addSaveActionListener();
        imageFrame.setLocationRelativeTo(applicationWindow);
        imageFrame.setVisible(true);
    }
    
    /**
     * Displays a window in which the user can select an instrument by name.
     * @param instrumentNames
     */
    public void displayInstrumentBank(String[] instrumentNames) {
    	instrument = (String) JOptionPane.showInputDialog(
    				applicationWindow,
    				"Select an instrument",
    				"Instrument Bank",
    				JOptionPane.PLAIN_MESSAGE,
    				null, 
    				instrumentNames,
    				null
    			);
    }
    
    /**
     * Returns the name of the instrument selected from the instrumentBank.
     * (RIP, MVC...)
     * @return
     */
    public String getInstrument() {
    	return instrument;
    }
    
    /**
     * Opens the player panel's file chooser.
     */
    public void openFileChooser() {
    	playerPanel.openFileChooser();
    }
    
    /**
     * Opens the player panel's file chooser.
     */
    public void openFileSaver() {
    	playerPanel.openFileSaver();
    }
    
    /**
     * Returns the file selected in the player panel's file chooser.
     * @return
     */
    public File getSelectedFile() {
    	return playerPanel.getSelectedFile();
    }

    /**
     * Gets the text that is in playerPanel's text field.
     * @return
     */
    public String getTextFieldText() {
        return playerPanel.getTextFieldText();
    }

    public void setStatusFieldText(String status) {
        playerPanel.setStatusFieldText(status);
    }

    /**
     * Adds an ActionListener to playerPanel's open button.
     * @param openActionListener
     */
    public void addFileOpenListener(ActionListener openActionListener) {
        playerPanel.addOpenActionListener(openActionListener);
        applicationWindow.addOpenActionListener(openActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's save button.
     * @param saveActionListener
     */
    public void addPlayerFileSaveListener(ActionListener saveActionListener) {
        playerPanel.addSaveActionListener(saveActionListener);
        applicationWindow.addSaveActionListener(saveActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's pngMusic button.
     * @param pngMusicAction
     */
    public void addPlayerPNGMusicListener(ActionListener pngMusicAction) {
        playerPanel.addPNGMusicActionListener(pngMusicAction);
    }

    /**
     * Adds an ActionListener to playerPanel's play button.
     * @param playActionListener
     */
    public void addPlayerPlayListener(ActionListener playActionListener) {
        playerPanel.addPlayActionListener(playActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's pause button.
     * @param pauseActionListener
     */
    public void addPlayerPauseListener(ActionListener pauseActionListener) {
        playerPanel.addPauseActionListener(pauseActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param stopActionListener
     */
    public void addPlayerStopListener(ActionListener stopActionListener) {
        playerPanel.addStopActionListener(stopActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param backActionListener
     */
    public void addPlayerBackListener(ActionListener backActionListener) {
        playerPanel.addBackActionListener(backActionListener);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param forwardActionListener
     */
    public void addPlayerForwardListener(ActionListener forwardActionListener) {
        playerPanel.addForwardActionListener(forwardActionListener);
    }
    
    /**
     * Adds an ActionListener to playerPanel's "change instrument" button.
     * @param instrumentActionListener
     */
    public void addInstrumentListener(ActionListener instrumentActionListener) {
    	playerPanel.addInstrumentActionListener(instrumentActionListener);
    }
    
    /**
     * Adds an ActionListener to View > Music Player in the menu bar.
     */
    private void addPlayerActionListener() {
    	this.applicationWindow.addPlayerActionListener(new ActionListener() {
            /**
             * Switches to the player panel.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                recordingPanel.setVisible(false);
                playerPanel.setVisible(true);
                applicationWindow.getContentPane().remove(recordingPanel);
                applicationWindow.getContentPane().add(playerPanel);
            }
        });
    }
    
    /**
     * Adds an ActionListener to View > Record Music in the menu bar.
     */
    private void addRecordActionListener() {
    	this.applicationWindow.addRecordActionListener(new ActionListener() {
            /**
             * Switches to the recording panel.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                playerPanel.setVisible(false);
                recordingPanel.setVisible(true);
                applicationWindow.getContentPane().remove(playerPanel);
                applicationWindow.getContentPane().add(recordingPanel);
            }
        });
    }
    
    /**
     * Adds an ActionListener to File > Quit in the menu bar.
     */
    private void addQuitActionListener() {
    	this.applicationWindow.addQuitActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
    		
    	});
    }

}
