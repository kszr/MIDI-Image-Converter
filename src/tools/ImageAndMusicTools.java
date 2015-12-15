package tools;

import java.io.File;

import audiovisual.AudioVisual;
import audiovisual.SimpleAudioVisual;
import music.Note;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/8/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageAndMusicTools {
	private AudioVisual lightSoundMapper;
	
	public ImageAndMusicTools() {
		lightSoundMapper = new SimpleAudioVisual();
	}
	
	/**
	 * A constructor that loads the given AudioVisual mapper.
	 * @param map
	 */
	public ImageAndMusicTools(AudioVisual mapper) {
		this.lightSoundMapper = mapper;
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
     * Returns a
     * @param color
     * @return
     */
    public Note colorToNote(int color) {
        return lightSoundMapper.getNote(color);
    }

    /**
     * Approximately the inverse of the function used in colorToPitch().
     * @return
     */
    public Integer pitchToColor(Note note) {
        return lightSoundMapper.getColor(note);
    }
    
    /**
     * Returns true if the filename belongs to a Midi file (i.e., with extension ".mid").
     * @param filename
     * @return
     */
    public static boolean isValidMidiFilename(String filename) {
        return filename.length()>4 && filename.substring(filename.length()-4).equalsIgnoreCase(".mid");
    }

    /**
     * Returns true if the filename belongs to a PNG file (i.e., with extension ".png").
     * @param filename
     * @return
     */
	public static boolean isValidImageFilename(String filename) {
		return filename.length()>4 && (filename.substring(filename.length()-4).equalsIgnoreCase(".png") ||
				filename.substring(filename.length()-4).equalsIgnoreCase(".jpg"));
	}
	
	public static boolean isValidImageFile(File file) {
		return file.exists() && isValidImageFilename(file.getName());
	}

	public static boolean isValidMidiFile(File file) {
		return file.exists() && isValidMidiFilename(file.getName());
	}
}
