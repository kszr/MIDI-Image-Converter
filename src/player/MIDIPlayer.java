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
import java.util.Scanner;

public class MIDIPlayer {
    private final Sequencer _sequencer = MidiSystem.getSequencer();
    private Sequence _currSequence;
    private PNGMusic _pngmusic;

    /**
     * Initializes the MIDIPlayer by loading the system's default
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
        if(PNGMusic.isValidPNGFilename(filename)) {
            _pngmusic.loadImage(filename);
            _currSequence = _pngmusic.imageToMidi();
        } else if (isValidMidiFilename(filename)){
            File song = new File(filename);

            if(!song.exists())
            throw new FileNotFoundException();

            _currSequence = MidiSystem.getSequence(song);
        } 
        else throw new IllegalArgumentException("not a valid .mid or .png file");
    }

    /**
     * Converts a MIDI sequence to an image.
     * @return 			The image.
     * @throws Exception
     */
    public BufferedImage convertSequenceToPNG() throws Exception {
        BufferedImage image = _pngmusic.midiToImage(_currSequence);
        return image;
    }

    /**
     * Saves the current sequence as a Midi file.
     * @throws Exception
     */
    public void save(String name) throws Exception {
        if(!isValidMidiFilename(name))
            throw new IllegalArgumentException("Invalid filename");

        File file = new File(name);

        int copy = 2;
        while(file.exists()) {
            file = new File(generateConflictFilename(name, copy++));
        }
        
        try {
        	MidiSystem.write(_currSequence, 1, file);
        } catch (FileNotFoundException e) {
        	File dir = new File("OutputMidiFiles");
        	dir.mkdir();
        	MidiSystem.write(_currSequence, 1, file);
        }
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
     * Returns true if the filename belongs to a Midi file (i.e., with extension ".mid").
     * @param filename
     * @return
     */
    private static boolean isValidMidiFilename(String filename) {
        return filename.length()>4 && filename.substring(filename.length()-4).equalsIgnoreCase(".mid");
    }

//    public static void main(String[] args) throws Exception {
//    	MIDIPlayer player = new MIDIPlayer();
//    	player.open("InputMidiFiles/ricercar-a-6-harp.mid");
//    	player.play();
//    	Scanner scanner = new Scanner(System.in);
//    	String command;
//    	while(true) {
//	        command = scanner.next();
//	        if(command.equals("pause"))
//	            player.pause();
//	        else if(command.equals("resume"))
//	            player.resume();
//	        else if(command.equals("stop"))
//	            player.stop();
//	        else if(command.equals("start"))
//	            player.play();
//	        else if(command.equals("quit"))
//	        	break;
//    	}
//    	scanner.close();
//    }
}
