package tools;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/8/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageAndMusicTools {
	
	/**
	 * This class is not instantiable.
	 */
	private ImageAndMusicTools() {
		
	}
	
    /**
     * Given an integer representation of color in the BufferedImage.TYPE_INT_ARGB
     * color model, returns an integer array with the values of alpha, R, G, and B.
     * @param color
     * @return
     */
    public static int[] getARGB(int color) {
        int[] argb_exploded = new int[4];

        argb_exploded[0] = (color >> 24) & 0xFF;
        argb_exploded[1] = (color >> 16) & 0xFF;
        argb_exploded[2] = (color >>8 ) & 0xFF;
        argb_exploded[3] = color & 0xFF;

        return argb_exploded;
    }
    
    /**
     * Returns true if the filename belongs to a Midi file (i.e., with extension ".mid").
     * @param filename
     * @return
     */
    public static boolean isValidMidiFilename(String filename) {
    	String extn = getExtension(filename);
        return (extn != null) && extn.equals("mid");
    }

    /**
     * Returns true if the filename belongs to an image file, which can have the following extensions:
     * - png
     * - jpg
     * - jpeg
     * - gif
     * @param filename
     * @return
     */
	public static boolean isValidImageFilename(String filename) {
		String extn = getExtension(filename);
		return (extn != null) && (extn.equals("png") ||
								  extn.equals("jpg") ||
								  extn.equals("jpeg")||
								  extn.equals("gif"));
	}
	
	public static boolean isValidImageFile(File file) {
		return file.exists() && isValidImageFilename(file.getName());
	}

	public static boolean isValidMidiFile(File file) {
		return file.exists() && isValidMidiFilename(file.getName());
	}
	
    
    /**
     * Gets the extension from a filename.
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {
        String ext = null;
        int i = filename.lastIndexOf('.');

        if (i > 0 &&  i < filename.length() - 1) {
            ext = filename.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    /**
     * Gets the extension from a file.
     * @param file
     * @return
     */
    public static String getExtension(File file) {
    	return file != null ? getExtension(file.getName()) : null;
    }
    
    
    /**
     * Appends a version number to a filename, in case multiple files
     * with the same root name exist. This is probably gratuitous, but it's better than
     * throwing exceptions when there are conflicts.
     * @param filename
     * @param copy
     * @return
     */
    public static String generateConflictFilename(String filename, int copy) {
    	String extn = ImageAndMusicTools.getExtension(filename);
    	String restOfName = filename.substring(0, filename.lastIndexOf("." + extn));
    	restOfName += " (" + copy + ")";
    	return restOfName + "." + extn;
    }
    
}
