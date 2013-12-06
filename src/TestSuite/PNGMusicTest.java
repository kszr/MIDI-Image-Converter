package TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/8/13
 * Time: 5:10 AM
 * To change this template use File | Settings | File Templates.
 */

import Player.PNGMusic;
import org.junit.Test;

import java.io.FileNotFoundException;

public class PNGMusicTest {
    @Test
    public void testConstructor() throws Exception {
        PNGMusic instance = new PNGMusic();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidFilename() throws Exception {
        PNGMusic instance = new PNGMusic();
        instance.loadImage("alice_in_wonderland.txt");
    }

    @Test(expected=FileNotFoundException.class)
    public void testNonexistentFile() throws Exception {
        PNGMusic instance = new PNGMusic();
        instance.loadImage("Images/alice_in_wonderland_frontispiece.png");
    }
}
