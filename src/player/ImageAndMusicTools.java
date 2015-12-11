package player;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/8/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageAndMusicTools {
    /**
     * Given an integer representation of color in the BufferedImage.TYPE_INT_ARGB
     * color model, returns an integer array with the values of alpha, R, G, and B.
     * @param color
     * @return
     */
    public static int[] getARGB(int color) {
        int[] argb_exploded = new int[4];

        argb_exploded[0] = (color >> 24) & 0x000000FF;
        argb_exploded[1] = (color >> 16) & 0x000000FF;
        argb_exploded[2] = (color >>8 ) & 0x000000FF;
        argb_exploded[3] = color & 0x000000FF;

        return argb_exploded;
    }

    /**
     * Returns a simple color-to-pitch function, where 21 is the lowest note
     * on a piano in the MIDI standard, and intensity is the 0-255 value
     * corresponding to R, G, or B. With 85 different pitches as possible output,
     * this function spans much of the 88-note range of the piano.
     * @param intensity
     * @return
     */
    public static int colorToPitch(int intensity) {
        return 21 + intensity/3;
    }

    /**
     * Approximately the inverse of the function used in colorToPitch().
     * A mathematical inverse is not possible owing to the fact that there can be no bijection
     * between intensities and musical notes.
     * @return
     */
    public static int pitchToColor(int note) {
        return (note-21)*3;
    }
}
