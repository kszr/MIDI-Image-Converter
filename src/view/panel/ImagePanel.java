package view.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private Image img;

    public ImagePanel(Image img) {
        super();
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    public Image getImage() {
    	return img;
    }
}
