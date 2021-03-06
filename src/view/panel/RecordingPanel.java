package view.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import view.MIDIPlayerView;
import view.button.PlaybackButton;
import view.button.TextButton;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/14/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecordingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
     * Instantiates the RecordingPanel by setting up all the necessary
     * components.
     */
    public RecordingPanel() {
        super();
        setUpRecordingPanel();
    }

    /**
     * Adds the necessary components, such as that which the user interacts
     * with to record music, and buttons for recording and saving.
     */
    private void setUpRecordingPanel() {
        setLayout(new FlowLayout());
        setSize(new Dimension(MIDIPlayerView.FRAME_WIDTH, MIDIPlayerView.FRAME_HEIGHT));
        setUpUserInput();
        setUpButtons();
    }

    /**
     * Sets up the keyboard input. OH GOD, THIS WILL NEVER WORK.
     */
    private void setUpUserInput() {
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new FlowLayout());
        keyboardPanel.setSize(new Dimension(210, 110));

        JPanel onScreenKeyboard = new JPanel();
        onScreenKeyboard.setSize(new Dimension(210, 108));
        GridLayout layout = new GridLayout(1, 7);
        layout.setHgap(0);
        layout.setVgap(0);
        onScreenKeyboard.setLayout(layout);
       /*
        for(int i=0; i<7; i++) {
            String keyname = Character.toString((char) ('A' + i));
            PianoKey whitekey = new PianoKey(keyname, PianoKey.color.WHITE);
            onScreenKeyboard.add(whitekey);
        }   */

        onScreenKeyboard.setVisible(true);
        //keyboardPanel.add(onScreenKeyboard);
        //keyboardPanel.setVisible(true);
        add(onScreenKeyboard);
    }

    /**
     * Sets up buttons for interacting with the program and for recording.
     */
    private void setUpButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setSize(new Dimension(500, 70));

        buttonsPanel.add(new PlaybackButton("ViewImages/record.png"));
        createTextField(buttonsPanel);
        buttonsPanel.add(new TextButton("Save", new Dimension(35, 35)));

        buttonsPanel.setVisible(true);
        add(buttonsPanel);
    }

    /**
     * Creates the text field in which the filename for the music is specified.
     * @param textFieldPanel
     */
    private void createTextField(JPanel textFieldPanel) {
        JTextField filename_field = new JTextField(10);
        textFieldPanel.add(filename_field);
    }
}
