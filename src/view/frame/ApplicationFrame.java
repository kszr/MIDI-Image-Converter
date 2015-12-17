package view.frame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ApplicationFrame extends JFrame {
	public ApplicationFrame(int width, int height) {
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		this.setTitle("MIDI Player");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("ViewImages/music_icon.png"));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

}
