package view.buttons;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 3:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextButton extends JButton {
    /**
     * Creates a custom text button that displays the given text.
     * @param displaytext
     */
    public TextButton(String displaytext, Dimension size) {
        super();
        setText(displaytext);
        setSize(size);
        setVisible(true);
    }
}
