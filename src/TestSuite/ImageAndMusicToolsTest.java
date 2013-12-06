package TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/8/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */

import Player.ImageAndMusicTools;
import org.junit.Assert;
import org.junit.Test;

public class ImageAndMusicToolsTest {
    @Test
    public void testGetARGB() {
        int argb = 0xAABBCCDD;
        int[] argb_exploded = ImageAndMusicTools.getARGB(argb);

        Assert.assertEquals(0xAA, argb_exploded[0]);
        Assert.assertEquals(0xBB, argb_exploded[1]);
        Assert.assertEquals(0xCC, argb_exploded[2]);
        Assert.assertEquals(0xDD, argb_exploded[3]);
    }

    @Test
    public void testColorToPitch() {
        int[] intensity = new int[256];

        for(int index=0; index<256; index++) {
            intensity[index]=index;
        }

        for(int index=0; index<256; index++) {
            int pitch = ImageAndMusicTools.colorToPitch(intensity[index]);
            Assert.assertTrue(pitch >= 21 && pitch <= 106);
        }
    }

    @Test
    public void testPitchToColor() {
        int[] pitches = new int[86];

        for(int index=0; index<86; index++) {
            pitches[index] = 21 + index;
        }

        for(int pitch : pitches) {
            int intensity = ImageAndMusicTools.pitchToColor(pitch);
            Assert.assertTrue(intensity >= 0 && intensity <= 255);
        }
    }
}
