package view.buttons;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 3:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlaybackButton extends JButton {
    /**
     * Creates a custom playback button, and sets the image icon
     * whose path is passed to it as the icon.
     * @param imagefilename
     */
    public PlaybackButton(String imagefilename) {
        super();
        Dimension size = new Dimension(50, 50);
        setSize(size);

        ImageIcon stopIcon = new ImageIcon(imagefilename);
        setIcon(stopIcon);
        setVisible(true);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
