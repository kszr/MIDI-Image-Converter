package view.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import tools.ImageAndMusicTools;
import view.filefilter.ImageFileFilter;
import view.panel.ImagePanel;

/**
 * A frame used to display images.
 * @author abhishekchatterjee
 * @date Dec 17, 2015
 * @time 1:21:25 PM
 */
public class ImageFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private JFileChooser fileChooser = new JFileChooser();
	private JMenuItem saveMenuItem;
	
	public ImageFrame(Image img) {
		setTitle("Image");
		setLayout(new BorderLayout());
		imagePanel = new ImagePanel(img);
		imagePanel.setSize(new Dimension(((BufferedImage) img).getWidth(), ((BufferedImage) img).getHeight()));
        getContentPane().add(imagePanel, BorderLayout.CENTER);
        setResizable(false);
        setUpMenuBar();
        pack();
        imagePanel.setVisible(true);
	}
	
	public void setUpMenuBar() {
		JMenuBar fileMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		saveMenuItem = new JMenuItem("Save As");
		fileMenu.add(saveMenuItem);
		fileMenuBar.add(fileMenu);
		setJMenuBar(fileMenuBar);
	}
	
	public void addSaveActionListener() {
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openSaveFileChooser();
				File file = fileChooser.getSelectedFile();
				if(file != null) {
				    try {
				        int copy = 2;
				        String name = file.getAbsolutePath();
				        if(!ImageAndMusicTools.getExtension(name).equals("png"))
				        	throw new Exception();
				        while(file.exists()) {
				            file = new File(ImageAndMusicTools.generateConflictFilename(name, copy++));
				        }
						ImageIO.write((BufferedImage) imagePanel.getImage(), "png", file);
						JOptionPane.showMessageDialog(null, "Saved " + file.getName(), null, JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Failed to save file", null, JOptionPane.WARNING_MESSAGE);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Invalid file type!", null, JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			
		});
	}
	
	public void openSaveFileChooser() {
        fileChooser.addChoosableFileFilter(new ImageFileFilter());
    	fileChooser.setAcceptAllFileFilterUsed(false);
    	fileChooser.showSaveDialog(this);
	}
	
	public File getSelectedFile() {
		return fileChooser.getSelectedFile();
	}
	
}
