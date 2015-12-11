package player;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/7/13
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import tools.PNGMusic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class MIDIPlayer {
    private final Sequencer _sequencer = MidiSystem.getSequencer();
    private Sequence _currSequence;
    private PNGMusic _pngmusic;

    /**
     * Initializes the Player.MIDIPlayer by loading the system's default
     * sequencer and synthesizer. Assumes that these exist, because
     * they probably do.
     * @throws Exception
     */
    public MIDIPlayer() throws Exception {
        _currSequence = null;
        _pngmusic = new PNGMusic();
    }

    /**
     * Opens a MIDI file with the given name, or loads a MIDI sequence
     * created from a PNG file.
     * @param filename
     * @throws Exception
     */
    public void open(String filename) throws Exception {
        if(!checkValidFilename(filename)) {
            _pngmusic.loadImage(filename);
            _currSequence = _pngmusic.imageToMidi();
        }
        else {
            File song = new File(filename);

            if(!song.exists())
            throw new FileNotFoundException();

            _currSequence = MidiSystem.getSequence(song);
        }
    }

    public BufferedImage convertSequenceToPNG() throws Exception {
        BufferedImage image = _pngmusic.midiToImage(_currSequence);
        return image;
    }

    /**
     * Saves the current sequence as a Midi file.
     * @throws Exception
     */
    public void save(String name) throws Exception {
        if(!checkValidFilename(name))
            throw new IllegalArgumentException("Invalid filename");

        File file = new File(name);

        if(file.exists())
            throw new Exception();
        
        try {
        	MidiSystem.write(_currSequence, 1, file);
        } catch (FileNotFoundException e) {
        	File dir = new File("OutputMidiFiles");
        	dir.mkdir();
        	MidiSystem.write(_currSequence, 1, file);
        }
    }

    /**
     * Starts playback of current song.
     * @throws Exception
     */
    public void play() throws Exception {
        if(_currSequence == null)
            throw new InvalidMidiDataException("=(");

        _sequencer.open();
        _sequencer.setSequence(_currSequence);
        _sequencer.start();
    }

    /**
     * Pauses playback.
     * @throws Exception
     */
    public void pause() throws Exception {
        _sequencer.stop();
    }

    /**
     * Resumes playback.
     * @throws Exception
     */
    public void resume() throws Exception {
        _sequencer.start();
    }

    /**
     * Stops playback.
     * @throws Exception
     */
    public void stop() throws Exception {
        _sequencer.close();
    }

    /**
     * Returns whether the filename is valid, herein defined as a
     * name with extension ".mid".
     * @param filename
     * @return
     */
    private boolean checkValidFilename(String filename) {
        return filename.length()>4 && filename.substring(filename.length()-3).equalsIgnoreCase("mid");
    }

}
