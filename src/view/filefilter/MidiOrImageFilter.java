package view.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import tools.ImageAndMusicTools;

/**
 * Filters out midi and image files.
 * @author abhishekchatterjee
 * Date: Dec 12, 2015
 * Time: 11:05:50 AM
 */
public class MidiOrImageFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory()) {
	        return true;
	    }

	    String extension = ImageAndMusicTools.getExtension(f);
	    if (extension != null) {
	        if (extension.equals("mid") ||
	            extension.equals("png") ||
	            extension.equals("jpg") ||
	            extension.equals("jpeg")||
	            extension.equals("gif")) {
	                return true;
	        } else {
	            return false;
	        }
	    }
	    
	    return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
