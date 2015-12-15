package view;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/9/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */

import view.panels.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

public class MIDIPlayerView {
    private final JFrame applicationWindow = new JFrame("Midi Player");
    private final PlayerPanel playerPanel = new PlayerPanel();
    private final RecordingPanel recordingPanel = new RecordingPanel();
    
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    
    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGHT = 250;
    
    private String instrument = null;

    /**
     * Instantiates the JFrame with buttons and other objects.
     */
    public MIDIPlayerView() {
        applicationWindow.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        applicationWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("ViewImages/music_icon.png"));
        setUpMenuBar();
        setUpPlayerPanel();
        setUpRecordingPanel();
        applicationWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        applicationWindow.setLocationRelativeTo(null);
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
        JMenuBar menubar = new JMenuBar();
        JMenu switchView = new JMenu("View");

        JMenuItem musicPlayer = new JMenuItem("Music Player");
        musicPlayer.addActionListener(new ActionListener() {
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

        JMenuItem recordMusic = new JMenuItem("Record Music");
        recordMusic.addActionListener(new ActionListener() {
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

        switchView.add(musicPlayer);
        switchView.add(recordMusic);

        JMenu filemenu = new JMenu("File");
        
        openMenuItem = new JMenuItem("Open");
        filemenu.add(openMenuItem);
        
        saveMenuItem = new JMenuItem("Save As");
        filemenu.add(saveMenuItem);
        
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() {
            /**
             * Quits the program.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        filemenu.add(quit);

        menubar.add(filemenu);
        menubar.add(switchView);
        applicationWindow.setJMenuBar(menubar);
    }

    public void displayMessageBox(String displayText, int type) {
        JOptionPane.showMessageDialog(null, displayText, null, type);
    }

    /**
     * Opens a new window displaying the result of the midi to image conversion.
     * @param image
     */
    public void displayImage(BufferedImage image) {
        JFrame imageframe = new JFrame();
        imageframe.setSize(new Dimension(image.getWidth(), image.getHeight()+30));
        ImagePanel imgpanel = new ImagePanel(image);
        imgpanel.setVisible(true);
        imageframe.getContentPane().add(imgpanel);
        imageframe.setLocationRelativeTo(applicationWindow);
        imageframe.setVisible(true);
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
     * @param openAction
     */
    public void addFileOpenListener(ActionListener openAction) {
        playerPanel.addOpenActionListener(openAction);
        openMenuItem.addActionListener(openAction);
    }

    /**
     * Adds an ActionListener to playerPanel's save button.
     * @param saveAction
     */
    public void addPlayerFileSaveListener(ActionListener saveAction) {
        playerPanel.addSaveActionListener(saveAction);
        saveMenuItem.addActionListener(saveAction);
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
     * @param playAction
     */
    public void addPlayerPlayListener(ActionListener playAction) {
        playerPanel.addPlayActionListener(playAction);
    }

    /**
     * Adds an ActionListener to playerPanel's pause button.
     * @param pauseAction
     */
    public void addPlayerPauseListener(ActionListener pauseAction) {
        playerPanel.addPauseActionListener(pauseAction);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param stopAction
     */
    public void addPlayerStopListener(ActionListener stopAction) {
        playerPanel.addStopActionListener(stopAction);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param backAction
     */
    public void addPlayerBackListener(ActionListener backAction) {
        playerPanel.addBackActionListener(backAction);
    }

    /**
     * Adds an ActionListener to playerPanel's stop button.
     * @param forwardAction
     */
    public void addPlayerForwardListener(ActionListener forwardAction) {
        playerPanel.addForwardActionListener(forwardAction);
    }
    
    /**
     * Adds an ActionListener to playerPanel's "change instrument" button.
     * @param instrumentAction
     */
    public void addInstrumentListener(ActionListener instrumentAction) {
    	playerPanel.addInstrumentActionListener(instrumentAction);
    }
}
