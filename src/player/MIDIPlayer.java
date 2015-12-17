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
import tools.InstrumentBank;
import tools.MIDISequenceTools;
import tools.PNGMusic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MIDIPlayer {
    private final Sequencer sequencer = MidiSystem.getSequencer();
    private final InstrumentBank instrumentBank = new InstrumentBank();
    private Sequence currSequence;
    private PNGMusic pngmusic;

    /**
     * Initializes the MIDIPlayer by loading the system's default
     * sequencer and synthesizer. Assumes that these exist, because
     * they probably do. Loads a SimpleAudioVisual object.
     * @throws Exception
     */
    public MIDIPlayer() throws Exception {
        currSequence = null;
        pngmusic = new PNGMusic(new SimpleAudioVisual());
    }
    
    /**
     * Loads the AudioVisual object that is passed in.
     * @param av
     * @throws Exception
     */
    public MIDIPlayer(AudioVisual av) throws Exception {
    	currSequence = null;
    	pngmusic = new PNGMusic(av);
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
    public Image convertSequenceToPNG() throws Exception {
    	if(currSequence == null)
    		throw new NullPointerException("No music to convert!");
    	
        Image image = pngmusic.midiToImage(currSequence);
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
     * Changes the instrument used in the sequence to that represented by instr_code.
     * @param instr_code
     * @throws Exception
     */
    public void changeInstrument(String name) throws Exception {
    	Integer code = instrumentBank.getProgram(name);
    	
    	if(currSequence == null)
    		throw new NullPointerException("No music loaded!");
    	
    	if(code == null)
    		throw new Exception("Not a valid instrument!");
    	long tick = sequencer.getTickPosition();
    	MIDISequenceTools.setInstrument(currSequence.getTracks(), code);
    	
    	stop();
    	play(tick);
    }
    
    /**
     * Gets a list of all instruments in the loaded SoundBank.
     * @return
     */
    public String[] getInstrumentList() {
    	return instrumentBank.getAllNames();
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
    }
    
    /**
     * Begins playback from the given tick.
     * @param tick
     * @throws Exception
     */
    public void play(long tick) throws Exception {
    	if(currSequence == null)
            throw new InvalidMidiDataException("no song is loaded =(");
    	
    	sequencer.open();
        sequencer.setSequence(currSequence);
        sequencer.setTickPosition(tick);
        sequencer.start();
    }

    /**
     * Pauses playback. Resumes playback if already paused.
     * @throws Exception
     */
    public void pause() throws Exception {
    	if(!sequencer.isOpen())
    		throw new Exception("Sequencer is not open or does not exist!");
    	
    	if(!sequencer.isRunning())
    		sequencer.start();
    	else
	        sequencer.stop();
    }

    /**
     * Resumes playback. Pauses playback if playing.
     * @throws Exception
     */
    public void resume() throws Exception {
    	if(!sequencer.isOpen())
    		throw new Exception("Sequencer is not open or does not exist!");

    	if(sequencer.isRunning())
        	sequencer.stop();
    	else {
    		if(sequencer.getTickPosition() == sequencer.getTickLength())
    			sequencer.setTickPosition(0);
        	sequencer.start();
    	}
    }

    /**
     * Stops playback.
     * @throws Exception
     */
    public void stop() throws Exception {
        sequencer.close();
    }
    
    /**
     * Sets the position of the sequencer to the specified tick. If tick is
     * greater than the number of ticks in the sequence, it sets it to the last tick
     * instead. If tick is less than 0, it sets it to the first tick.
     * @param tick
     */
    public void setTickPosition(long tick) {
    	long length = sequencer.getTickLength();
    	if(tick < 0)
    		tick = 0;
    	if(tick > length)
    		tick = length;
    	sequencer.setTickPosition(tick);
    }

}
