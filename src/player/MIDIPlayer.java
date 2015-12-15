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

import audiovisual.AudioVisual;
import audiovisual.SimpleAudioVisual;
import tools.ImageAndMusicTools;
import tools.MIDISequenceTools;
import tools.PNGMusic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MIDIPlayer {
    private final Sequencer sequencer = MidiSystem.getSequencer();
    private Sequence currSequence;
    private PNGMusic pngmusic;
    private boolean isPlaying;

    /**
     * Initializes the MIDIPlayer by loading the system's default
     * sequencer and synthesizer. Assumes that these exist, because
     * they probably do. Loads a SimpleAudioVisual object.
     * @throws Exception
     */
    public MIDIPlayer() throws Exception {
        currSequence = null;
        pngmusic = new PNGMusic(new SimpleAudioVisual());
        isPlaying = false;
    }
    
    /**
     * Loads the AudioVisual object that is passed in.
     * @param av
     * @throws Exception
     */
    public MIDIPlayer(AudioVisual av) throws Exception {
    	currSequence = null;
    	pngmusic = new PNGMusic(av);
    	isPlaying = false;
    }
    
    /**
     * Opens a midi file.
     * @param file
     * @throws Exception
     */
    public void open(File file) throws Exception {
    	if(ImageAndMusicTools.isValidImageFile(file)) {
    		pngmusic.loadImage(file);
    		currSequence = pngmusic.imageToMidi();
    	} else if(ImageAndMusicTools.isValidMidiFile(file)) {
            currSequence = MidiSystem.getSequence(file);
    	}
    	else throw new IllegalArgumentException("not a valid .mid or .png file");
    }

    /**
     * Converts a MIDI sequence to an image.
     * @return 			The image.
     * @throws Exception
     */
    public BufferedImage convertSequenceToPNG() throws Exception {
        BufferedImage image = pngmusic.midiToImage(currSequence);
        return image;
    }
    
    /**
     * Changes the instrument used in the sequence to that represented by instr_code.
     * @param instr_code
     * @throws Exception
     */
    public void changeInstrument(int instr_code) throws Exception {
    	MIDISequenceTools.setInstrument(currSequence.getTracks(), instr_code);
    }

    /**
     * Saves the current sequence as a Midi file.
     * @throws Exception
     */
    public void save(String name) throws Exception {
        if(!ImageAndMusicTools.isValidMidiFilename(name))
            throw new IllegalArgumentException(".mid extensions only");

        File file = new File(name);

        int copy = 2;
        while(file.exists()) {
            file = new File(generateConflictFilename(name, copy++));
        }

        MidiSystem.write(currSequence, 1, file);
    }
    
    /**
     * Appends a version number to a filename, in case multiple files
     * with the same root name exist. This is probably gratuitous, but it's better than
     * throwing exceptions when there are conflicts.
     * @param filename
     * @param copy
     * @return
     */
    private static String generateConflictFilename(String filename, int copy) {
    	String extn = filename.substring(filename.length()-4);
    	String restOfName = filename.substring(0, filename.length()-4);
    	restOfName += " (" + copy + ")";
    	return restOfName + extn;
    }

    /**
     * Starts playback of current song for the first time. (I.e.,
     * if the sequencer is currently closed. If the sequencer is not closed,
     * use resume() instead.)
     * @throws Exception
     */
    public void play() throws Exception {
        if(currSequence == null)
            throw new InvalidMidiDataException("no song is loaded =(");

        sequencer.open();
        sequencer.setSequence(currSequence);
        sequencer.start();
        isPlaying = true;
    }

    /**
     * Pauses playback. Resumes playback if already paused.
     * @throws Exception
     */
    public void pause() throws Exception {
    	if(!sequencer.isOpen())
    		throw new Exception("Sequencer is not open or does not exist!");
    	
    	if(!isPlaying) {
    		sequencer.start();
    		isPlaying = true;
    	} else {
	        sequencer.stop();
	        isPlaying = false;
    	}
    }

    /**
     * Resumes playback. Pauses playback if playing.
     * @throws Exception
     */
    public void resume() throws Exception {
    	if(!sequencer.isOpen())
    		throw new Exception("Sequencer is not open or does not exist!");
    	
        if(isPlaying) {
        	sequencer.stop();
        	isPlaying = false;
        } else {
        	sequencer.start();
        	isPlaying = true;
        }
    }

    /**
     * Stops playback.
     * @throws Exception
     */
    public void stop() throws Exception {
        sequencer.close();
        isPlaying = false;
    }
}
