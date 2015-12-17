package view.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import view.panel.ImagePanel;

public class ImageFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	
	public ImageFrame(Image img) {
		this.setTitle("Image");
		this.setLayout(new BorderLayout());
		this.imagePanel = new ImagePanel(img);
		this.imagePanel.setSize(new Dimension(((BufferedImage) img).getWidth(), ((BufferedImage) img).getHeight()));
        this.getContentPane().add(imagePanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.imagePanel.setVisible(true);
	}
	
}
