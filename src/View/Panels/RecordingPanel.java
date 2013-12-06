package View.Panels;

import View.Buttons.PlaybackButton;
import View.Buttons.TextButton;
import View.MIDIPlayerView;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/14/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecordingPanel extends JPanel {
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
        setLayout(new GridLayout(3, 1));
        setSize(new Dimension(MIDIPlayerView.FRAME_WIDTH, MIDIPlayerView.FRAME_HEIGHT));
        setUpUserInput();
        setUpButtons();
    }

    /**
     * Sets up the <<<<<INSERTMODEOFINPUTHERE>>>>>
     */
    private void setUpUserInput() {
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new FlowLayout());
        keyboardPanel.setSize(new Dimension(210, 110));

        JPanel onScreenKeyboard = new JPanel();
        onScreenKeyboard.setSize(new Dimension(210, 110));
        onScreenKeyboard.setLayout(new FlowLayout());

        for(int i=0; i<7; i++) {
        JPanel whitekey = new JPanel();
        whitekey.setSize(new Dimension(30, 110));
        whitekey.setBorder(new BorderUIResource.LineBorderUIResource(Color.black, 1));
        whitekey.setOpaque(true);
        whitekey.setVisible(true);
        onScreenKeyboard.add(whitekey);
        }



        onScreenKeyboard.setVisible(true);
        keyboardPanel.add(onScreenKeyboard);
        keyboardPanel.setVisible(true);
        add(keyboardPanel);
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
