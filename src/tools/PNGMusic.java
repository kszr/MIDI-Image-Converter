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
    
    private ImageAndMusicTools imageAndMusicTools;

    /**
     * Instantiates the Player.PNGMusic object by creating three tracks,
     * because it seems a good idea at the moment to have different tracks
     * for R, G, and B rather than attempting the pointless task of assigning
     * a unique value to each unique set of RGB values. (255 x 255 x 255 values for
     * RGB, 88 keys on a piano.)
     * @throws Exception
     */
    public PNGMusic() throws Exception {
        //initializeSequence();
        imageAndMusicTools = new ImageAndMusicTools();
    }
    
    public PNGMusic(AudioVisual map) throws Exception {
        //initializeSequence();
        imageAndMusicTools = new ImageAndMusicTools(map);
    }

    /**
     * Sets up tracks, etc. in the sequence that is being created.
     * @throws Exception
     */
    private void initializeSequence() throws Exception {
        sequence = new Sequence(MIDISequenceTools.DIVISION_TYPE, MIDISequenceTools.TICKS_PER_BEAT);
        tracks = new Track[3];

        for(int index=0; index<tracks.length; index++) {
            tracks[index] = sequence.createTrack();
        }

        MIDISequenceTools.setUpSystem(tracks);
        MIDISequenceTools.setTempo(tracks);

        String[] tracknames = new String[3];
        tracknames[0] = "Piano RH";
        tracknames[1] = "Nose";
        tracknames[2] = "Piano LH";

        MIDISequenceTools.setTrackNames(tracks, tracknames);
        MIDISequenceTools.setOmni(tracks);
        MIDISequenceTools.setPoly(tracks);
        MIDISequenceTools.setInstrument(tracks, 0);
    }

    /**
     * Loads an image from a file.
     * @param file
     * @throws Exception
     */
    public void loadImage(File file) throws Exception {
        if(!file.exists())
            throw new FileNotFoundException("File does not exist");

        initializeSequence();
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
        long[] ticks = new long[3];
        ticks[0] = ticks[1] = ticks[2] = 1;

        for(int row=0; row<height; row++) {
            for(int column=0; column<width; column++) {
                int color = imageData.getRGB(column, row);
                int[] argb = ImageAndMusicTools.getARGB(color);
                Note[] notes = new Note[3];
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

        long tick = Math.max(ticks[0], Math.max(ticks[1], ticks[2]));
        for(Track track : tracks) {
            MIDISequenceTools.setEndOfTrack(track, tick + 19);
        }
        return sequence;
    }

    /**
     * Uses the MIDI data in the loaded sequence to generate a square image.
     * @return
     * @throws Exception
     * @TODO	Need to better deal with uncertainties in note lengths and track counts.
     */
    public BufferedImage midiToImage(Sequence sequence) throws Exception {
    	initializeSequence();
        int side = (int) Math.sqrt(sequence.getTickLength()/sequence.getResolution() * 4);
        BufferedImage newImage = new BufferedImage(side, side, BufferedImage.TYPE_3BYTE_BGR);
        
        System.err.println("INFO: Image side length = " + side);
        for(int i=0; i<sequence.getTracks().length; i++)
        	System.err.println("INFO: Track #" + i + " size = " + sequence.getTracks()[i].size());
        
        int[] eventIndex = new int[3];
        eventIndex[0] = eventIndex[1] = eventIndex[2] = 0;
        for(int row=0; row<side; row++) {
            for(int column=0; column<side; column++) {
                int[] rgb = {-1, -1, -1};
                Note[] notes = new Note[3];
                Track[] allTracks = sequence.getTracks();
                int k = 3;
                Track[] kLongest = getKLongestTracks(allTracks, k);

            	int size = Math.min(3, kLongest.length);
            	for(int index=0; index<size; index++) {
                    while((MIDISequenceTools.getMessageType(sequence.getTracks()[index], eventIndex[index]) & 0xFF) != ShortMessage.NOTE_ON) {
                        eventIndex[index]++;
                    }
            		notes[index] = MIDISequenceTools.getNoteFromTrack(kLongest[index], eventIndex[index]);
            		
            		if(notes[index].getPitch() < 21 || notes[index].getPitch() > 108) {
            			int pitch = Math.abs(21 - notes[index].getPitch()) < Math.abs(108 - notes[index].getPitch()) ? 21 : 108;
            			Note newNote = new Note(notes[index].getLength(), notes[index].getDot(), pitch);
            			notes[index] = newNote;
            		}
            		rgb[index] = imageAndMusicTools.pitchToColor(notes[index]);
            	}
            	
            	/**
            	 * If getKLongestTracks() failed to return k tracks...
            	 */
            	int j = k - kLongest.length;
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
