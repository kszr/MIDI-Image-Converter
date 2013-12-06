package Player;

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

import com.intellij.util.ui.UIUtil;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class PNGMusic {
    private final int SCALE_SIZE = 100;
    private final int DISPLAY_SIZE = 250;

    private BufferedImage _imageData;
    private Sequence _sequence;
    Track[] tracks;

    /**
     * Instantiates the Player.PNGMusic object by creating three tracks,
     * because it seems a good idea at the moment to have different tracks
     * for R, G, and B rather than attempting the pointless task of assigning
     * a unique value to each unique set of RGB values. (255 x 255 x 255 values for
     * RGB, 88 keys on a piano.)
     * @throws Exception
     */
    public PNGMusic() throws Exception {
        _sequence = new Sequence(MIDISequenceTools.DIVISION_TYPE, MIDISequenceTools.TICKS_PER_BEAT);
        tracks = new Track[3];

        for(int index=0; index<tracks.length; index++) {
            tracks[index] = _sequence.createTrack();
        }

        MIDISequenceTools.setUpSystem(tracks);
        MIDISequenceTools.setTempo(tracks);

        String[] tracknames = new String[3];
        tracknames[0] = "Piano RH";
        tracknames[1] = "Nose";
        tracknames[2] = "Piano LH";

        MIDISequenceTools.setTrackNames(tracks, tracknames);
        MIDISequenceTools.setOmniOn(tracks);
        MIDISequenceTools.setPoly(tracks);
        MIDISequenceTools.setInstrument(tracks, MIDISequenceTools.PIANO);
    }

    /**
     * Loads an image from a file.
     * @param filename
     * @throws Exception
     */
    public void loadImage(String filename) throws Exception {
        if(!checkValidFilename(filename))
            throw new IllegalArgumentException();

        File file = new File(filename);

        if(!file.exists())
            throw new FileNotFoundException("File does not exist");

        Image image = ImageIO.read(file);
        image = image.getScaledInstance(SCALE_SIZE, SCALE_SIZE, Image.SCALE_SMOOTH);
        _imageData = UIUtil.createImage(SCALE_SIZE, SCALE_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        _imageData.getGraphics().drawImage(image, 0, 0 , null);
    }

    private boolean checkValidFilename(String filename) {
        return filename.length()>4 && filename.substring(filename.length()-3).equalsIgnoreCase("png");
    }

    /**
     * Converts the loaded image into a MIDI sequence.
     * @throws Exception
     */
    public Sequence imageToMidi() throws Exception {
        int width = _imageData.getWidth();
        int height = _imageData.getHeight();

        long ticks=1;

        for(int row=0; row<height; row++) {
            for(int column=0; column<width; column++) {
                int color = _imageData.getRGB(column, row);

                int[] argb = ImageAndMusicTools.getARGB(color);

                for(int index=0; index<tracks.length; index++) {
                    MIDISequenceTools.setNoteOn(tracks[index], ImageAndMusicTools.colorToPitch(argb[index + 1]), 0x60, ticks);
                }

                for(int index=0; index<tracks.length; index++) {
                    MIDISequenceTools.setNoteOff(tracks[index], ImageAndMusicTools.colorToPitch(argb[index + 1]), 0x40, ticks + 120);
                }

                ticks += 121;
            }
        }

        for(Track track : tracks) {
            MIDISequenceTools.setEndOfTrack(track, ticks + 19);
        }

        return _sequence;
    }

    /**
     * Uses the MIDI data in the loaded sequence to generate a square image.
     * @return
     * @throws Exception
     */
    public BufferedImage midiToImage(Sequence sequence) throws Exception {
        int side = (int) Math.sqrt(sequence.getTickLength()/sequence.getResolution() * 4);

        BufferedImage newImage = UIUtil.createImage(side, side, BufferedImage.TYPE_3BYTE_BGR);
        System.out.println(side);

        int eventIndex = 0;
        for(int row=0; row<side; row++) {
            for(int column=0; column<side; column++) {
                while((MIDISequenceTools.getMessageType(sequence.getTracks()[1], eventIndex) & 0xFF) != ShortMessage.NOTE_ON) {
                    eventIndex++;
                }

                int[] rgb = new int[3];
                int[] notes = new int[3];

                try {
                    for(int index=0; index<3; index++) {
                        notes[index] = MIDISequenceTools.getNoteFromTrack(sequence.getTracks()[index], eventIndex);
                        rgb[index] = ImageAndMusicTools.pitchToColor(notes[index]);
                    }
                }
                catch(Exception e) {
                    for(int index=0; index<3; index++) {
                        notes[index] = MIDISequenceTools.getNoteFromTrack(sequence.getTracks()[index+1], eventIndex);
                        rgb[index] = ImageAndMusicTools.pitchToColor(notes[index]);
                    }
                }

                int color = (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];

                newImage.setRGB(column, row, color);

                eventIndex ++;
            }
        }

        Image image = newImage;
        image = image.getScaledInstance(DISPLAY_SIZE, DISPLAY_SIZE, Image.SCALE_SMOOTH);
        newImage = UIUtil.createImage(DISPLAY_SIZE, DISPLAY_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        newImage.getGraphics().drawImage(image, 0, 0 , null);
        return newImage;
    }

    /**
     * Saves the sequence generated from the image as a MIDI file.
     * @param filename
     * @throws Exception
     */
    public void save(String filename) throws Exception {
        File file = new File(filename);
        MidiSystem.write(_sequence, 1, file);
    }

}