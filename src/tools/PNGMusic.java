package tools;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/7/13
 * Time: 9:30 PM
 * References:
 * - Setting up sequencer and meta events: http://www.automatic-pilot.com/midifile.html
 * - MIDI messages: http://www.midi.org/techspecs/midimessages.php
 * - General Midi Standard: https://en.wikipedia.org/wiki/General_MIDI#Program_change_events
 * - Setting tempi: http://www.recordingblogs.com/sa/Wiki/tabid/88/Default.aspx?topic=MIDI+Set+Tempo+meta+message
 * - System exclusive messages: http://www.recordingblogs.com/sa/tabid/88/Default.aspx?topic=MIDI+System+Exclusive+message
 * - MIDI note names and pitches: http://www.phys.unsw.edu.au/jw/notes.html
 */

//import com.intellij.util.ui.UIUtil;

import javax.imageio.ImageIO;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import tools.phonograph.Needle;
import audiovisual.AudioVisual;
import music.Note;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * A class that handles conversion between Images and Music.
 * Despite the name, JPEG is allowed.
 * This class will be expanded to be more sophisticated.
 * (Originally written in 2013, despite the date.)
 * @author abhishekchatterjee
 * Date: Dec 11, 2015
 * Time: 8:30:10 AM
 */
public class PNGMusic {
    private final int SCALED_SIZE = 100;
    private final int DISPLAY_SIZE = 250;
    private final int TICKS_PER_WHOLE_NOTE = 1920;

    private BufferedImage imageData;
    private Sequence sequence;
    private Track[] tracks;
    private int numtracks;
    
    private ImageAndMusicTools imageAndMusicTools;

    /**
     * Instantiates the Player.PNGMusic object by creating three tracks,
     * because it seems a good idea at the moment to have different tracks
     * for R, G, and B rather than attempting the pointless task of assigning
     * a unique value to each unique set of RGB values. (255 x 255 x 255 values for
     * RGB, 88 keys on a piano.) Number of tracks is 3 by default.
     * @throws Exception
     */
    public PNGMusic() throws Exception {
        //initializeSequence();
        imageAndMusicTools = new ImageAndMusicTools();
        this.numtracks = 3;
    }
    
    /**
     * Loads the given AudioVisual object.
     * @param map
     * @throws Exception
     */
    public PNGMusic(AudioVisual map) throws Exception {
        //initializeSequence();
        imageAndMusicTools = new ImageAndMusicTools(map);
        this.numtracks = 3;
    }
    
    /**
     * Sets the number of tracks to numtracks and reinitializes the sequence.
     * @param numtracks
     * @throws Exception 
     */
    public void setNumTracks(int numtracks) throws Exception {
    	this.numtracks = numtracks;
    	this.initializeSequence();
    }
    
    /**
     * Returns the number of tracks in the current sequence.
     * @return
     */
    public int getNumTracks() {
    	return this.numtracks;
    }

    /**
     *Creates a new sequence and sets up tracks, etc. in it.
     * @throws Exception
     */
    private void initializeSequence() throws Exception {
        sequence = new Sequence(MIDISequenceTools.DIVISION_TYPE, MIDISequenceTools.TICKS_PER_BEAT);
        
        this.initializeTracks();

        String[] tracknames = new String[numtracks];
        this.setTrackNames(tracknames);
        
        MIDISequenceTools.setUpSystem(tracks);
        MIDISequenceTools.setTempo(tracks);
        MIDISequenceTools.setTrackNames(tracks, tracknames);
        MIDISequenceTools.setOmni(tracks);
        MIDISequenceTools.setPoly(tracks);
        MIDISequenceTools.setInstrument(tracks, 0);
    }
    
    /**
     * Creates numtracks tracks and puts them into the sequence.
     */
    private void initializeTracks() {
        tracks = new Track[numtracks];
        for(int index=0; index<tracks.length; index++) {
            tracks[index] = sequence.createTrack();
        }
    }
    
    /**
     * Creates names for tracks. The names for the case when numtracks=3
     * are what they are for LEGACY REASONS.
     */
    private void setTrackNames(String[] tracknames) {
    	if(numtracks == 3) {
            tracknames[0] = "Piano RH";
            tracknames[1] = "Nose";
            tracknames[2] = "Piano LH";
    	} else {
    		for(int i=0; i<numtracks; i++)
    			tracknames[i] = "Track " + i;
    	}
    }

    /**
     * Loads an image from a file.
     * @param file
     * @throws Exception
     */
    public void loadImage(File file) throws Exception {
        if(!file.exists())
            throw new FileNotFoundException("File does not exist");

        //Sets the number of tracks to 3, which also (re)initializes the sequence.
        this.setNumTracks(3);
        Image image = ImageIO.read(file);
        image = image.getScaledInstance(SCALED_SIZE, SCALED_SIZE, Image.SCALE_SMOOTH);
        imageData = new BufferedImage(SCALED_SIZE, SCALED_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        imageData.getGraphics().drawImage(image, 0, 0 , null);
    }

    /**
     * Converts the loaded image into a MIDI sequence.
     * @throws Exception
     */
    public Sequence imageToMidi() throws Exception {
        int width = imageData.getWidth();
        int height = imageData.getHeight();

        //Initialize ticks in each track to 1.
        long[] ticks = new long[numtracks];
        for(int i=0; i<numtracks; i++)
        	ticks[i] = 1;

        for(int row=0; row<height; row++) {
            for(int column=0; column<width; column++) {
                int color = imageData.getRGB(column, row);
                int[] argb = ImageAndMusicTools.getARGB(color);
                Note[] notes = new Note[numtracks];
                for(int i=0; i<tracks.length; i++)
                	notes[i] = imageAndMusicTools.colorToNote(argb[i+1]);

                /**
                 * Turn the note at notes[index] on in tracks[index] at the specified tick,
                 * and then turn it off after a number of ticks corresponding to its
                 * length have passed.
                 */
                for(int index=0; index<tracks.length; index++) {
                    MIDISequenceTools.setNoteOn(tracks[index], notes[index], 0x60, ticks[index]);
                    ticks[index] += notes[index].getDuration()*TICKS_PER_WHOLE_NOTE;
                    MIDISequenceTools.setNoteOff(tracks[index], notes[index], 0x40, ticks[index]);
                }
            }
        }

        //Use the length of the longest track in ticks to set the end of the sequence.
        long endtick = getMax(ticks);
        for(Track track : tracks) {
            MIDISequenceTools.setEndOfTrack(track, endtick + 19);
        }
        return sequence;
    }
    
    /**
     * A lame helper method that gets the max value from an array of longs.
     * @param array
     * @return
     */
    private long getMax(long[] array) {
    	long max = Long.MIN_VALUE;
    	for(int i=0; i<array.length; i++)
    		if(array[i] > max)
    			max = array[i];
    	return max;
    }

    /**
     * Uses the MIDI data in the loaded sequence to generate a square image.
     * @return
     * @throws Exception
     * @TODO	Need to better deal with uncertainties in note lengths and track counts.
     */
    public BufferedImage midiToImage(Sequence sequence) throws Exception {
    	initializeSequence();
        int side = (int) Math.sqrt(sequence.getTickLength()/sequence.getResolution()*4);
        BufferedImage newImage = new BufferedImage(side, side, BufferedImage.TYPE_3BYTE_BGR);
        
        System.err.println("INFO: Image side length = " + side);
        for(int i=0; i<sequence.getTracks().length; i++)
        	System.err.println("INFO: Track #" + i + " size = " + sequence.getTracks()[i].size());
        
        int[] rgb = new int[numtracks];
        Track[] allTracks = sequence.getTracks();
        Track[] kLongest = getKLongestTracks(allTracks, numtracks);
        
        int realnumtracks = Math.min(numtracks, kLongest.length);
        Needle[] needles = new Needle[realnumtracks];
        for(int i=0; i<realnumtracks; i++)
        	needles[i] = new Needle(kLongest[i]);
        
        for(int row=0; row<side; row++) {
            for(int column=0; column<side; column++) {
            	for(int index=0; index<realnumtracks; index++) {
                    Note nextNote;
                    if(needles[index].hasNext())
                    	nextNote = needles[index].next();
                    else
                    	continue;
                		
            		if(nextNote.getPitch() < 21 || nextNote.getPitch() > 108) {
            			int pitch = Math.abs(21 - nextNote.getPitch()) < Math.abs(108 - nextNote.getPitch()) ? 21 : 108;
            			nextNote = new Note(nextNote.getLength(), nextNote.getDot(), pitch);
            		}
            		rgb[index] = imageAndMusicTools.pitchToColor(nextNote);
            	}
            	
            	/**
            	 * If getKLongestTracks() failed to return numtracks tracks...
            	 */
            	int j = numtracks - kLongest.length;
            	for(int index=0; index<j; index++) {
            		rgb[index] = 255; //Just white... =/
            	}
            	
                int color = (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
                newImage.setRGB(column, row, color);
            }
        }
        Image image = newImage;
        image = image.getScaledInstance(DISPLAY_SIZE, DISPLAY_SIZE, Image.SCALE_SMOOTH);
        newImage = new BufferedImage(DISPLAY_SIZE, DISPLAY_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        newImage.getGraphics().drawImage(image, 0, 0 , null);
        return newImage;
    }
    
    /**
     * Returns
     * @param track
     * @param currTick
     * @param eventIndex
     * @return
     */
    private Note getNextNote(Track track, long currTick, int eventIndex) {
    	return null;
    }
    
    /**
     * Uses the partial selection sort algorithm to obtain the k longest tracks in the track list. Or
     * tracks.length - whichever is smaller.
     * the difference.
     * @param tracks
     * @return
     */
    private static Track[] getKLongestTracks(Track[] tracks, int k) {
    	k = Math.min(k, tracks.length);
    	Track[] kLongest = new Track[k];
    	for(int i=0; i<k; i++) {
    		int maxindex = i;
    		Track maxvalue = tracks[i];
    		for(int j=i+1; j<tracks.length; j++) {
    			if(tracks[j].size() > maxvalue.size()) {
    				maxindex = j;
    				maxvalue = tracks[j];
    			}
    		}
    		Track temp = tracks[i];
    		tracks[i] = tracks[maxindex];
    		tracks[maxindex] = temp;
    	}
    	kLongest = Arrays.copyOfRange(tracks, 0, k);
    	return kLongest;
    }
    
}
