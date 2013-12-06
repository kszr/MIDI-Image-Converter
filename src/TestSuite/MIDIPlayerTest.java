package TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/7/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */

import Player.MIDIPlayer;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import java.io.FileNotFoundException;

public class MIDIPlayerTest {
    @Test
    public void testConstructor() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidFilename1() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        player.open(".");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidFilename2() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        player.open("BestTextFileInTheWorldZOMG.txt");
    }

    @Test(expected=FileNotFoundException.class)
    public void testNonexistentFilename() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        player.open("badname.mid");
    }

    @Test(expected=InvalidMidiDataException.class)
    public void testIllegalStart() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        player.play();
    }

    @Test
    public void testSaveFile() throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        player.open("MidiFiles/dm_mzzpa.mid");
        player.save("MidiFiles/liszt_etude_4.mid");
    }

}
