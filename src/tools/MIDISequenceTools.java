package tools;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import music.Note;

/**
 * This class handles various operations on Midi sequences.
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/15/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class MIDISequenceTools {
    //General Constants:
    public static final int TICKS_PER_BEAT=480;
    public static final float DIVISION_TYPE=Sequence.PPQ; //Pulses (ticks) per quarter note

    //Short Message Constants:
    public static final int TRACK_NAME_MSG =0x03;
    public static final int OMNI_ON=0x7D;
    public static final int POLY_ON=0x7F;

    //Constants for MetaMessages:
    public static final int SET_TEMPO=0x51;
    public static final int END_OF_TRACK=0x2F;

    //Universal system exclusive messages:
    public static final int SYSEX_REAL_TIME=0x7E;
    public static final int SYSEX_NON_REAL_TIME=0x7F;
    public static final int SYSEX_END=0xF7;

    /**
     * Prepares the system to receive MIDI data.
     * @param tracks
     * @throws Exception
     */
    public static void setUpSystem(Track[] tracks) throws Exception {
        byte[] msg = {(byte) SysexMessage.SYSTEM_EXCLUSIVE, SYSEX_REAL_TIME, SYSEX_NON_REAL_TIME, 0x09, 0x01,(byte) SYSEX_END};
        SysexMessage sysex = new SysexMessage();
        sysex.setMessage(msg, msg.length);
        MidiEvent event = new MidiEvent(sysex,(long)0);

        for(Track track : tracks) {
            track.add(event);
        }
    }

    /**
     * Sets the tempo of each track to 120 bpm.
     * @param tracks
     * @throws Exception
     */
    public static void setTempo(Track[] tracks) throws Exception {
        MetaMessage meta = new MetaMessage();
        byte[] bt = {0x07, (byte) 0xA1, 0x20};  //0x07A120 Supposedly corresponds to 120 bpm
        meta.setMessage(SET_TEMPO, bt, bt.length);
        MidiEvent event = new MidiEvent(meta,(long)0);

        for(Track track : tracks) {
            track.add(event);
        }
    }

    /**
     * Assigns a name to each of the three tracks. This is meta information that a
     * sequencer may or may not make use of, e.g. to display staff names in a notation
     * program.
     * @param tracks
     * @param tracknames
     * @throws Exception
     */
    public static void setTrackNames(Track[] tracks, String[] tracknames) throws Exception {
        if(tracks.length != tracknames.length)
            throw new IllegalArgumentException("Array lengths do not match");

        for(int index=0; index<tracks.length; index++) {
            MetaMessage meta = new MetaMessage();
            meta.setMessage(TRACK_NAME_MSG, tracknames[index].getBytes(), tracknames[index].length());
            MidiEvent event = new MidiEvent(meta,(long)0);
            tracks[index].add(event);
        }
    }

    /**
     * Sets Omni mode to on. This means that the MIDI instrument
     * will respond to any incoming MIDI data, according to this source:
     * http://www.sweetwater.com/insync/omni-mode/
     * @param tracks
     * @throws Exception
     */
    public static void setOmniOn(Track[] tracks) throws Exception {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.CONTROL_CHANGE, OMNI_ON, 0);
        MidiEvent event = new MidiEvent(msg,(long)0);

        for(Track track : tracks) {
            track.add(event);
        }
    }

    /**
     * Sets Poly (as opposed to Mono) mode to on.
     * @param tracks
     * @throws Exception
     */
    public static void setPoly(Track[] tracks) throws Exception {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.CONTROL_CHANGE, POLY_ON, 0);
        MidiEvent event = new MidiEvent(msg,(long)0);

        for(Track track : tracks) {
            track.add(event);
        }
    }

    /**
     * Sets the track's instrument to that represented by code.
     * @param tracks
     * @param instr_code
     * @throws Exception
     */
    public static void setInstrument(Track[] tracks, int instr_code) throws Exception {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.PROGRAM_CHANGE, instr_code, 0);
        MidiEvent event = new MidiEvent(msg,(long)0);

        for(Track track : tracks) {
            track.add(event);
        }
    }

    /**
     * Sets a note on message on a given track which tells the sequencer to start
     * playing a given note on a given tick and with a given velocity.
     * @param track
     * @param note
     * @param notevel
     * @param tick
     * @throws Exception
     */
    public static void setNoteOn(Track track, Note note, int notevel, long tick) throws Exception {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.NOTE_ON, note.getPitch(), notevel);
        MidiEvent event = new MidiEvent(msg, tick);
        track.add(event);
    }

    /**
     * Sets a note off message on a given track which tells the sequencer to stop
     * playing a note that is being played on this track on a certain tick and with
     * a given velocity.
     * @param track
     * @param note
     * @param notevel
     * @param tick
     * @throws Exception
     */
    public static void setNoteOff(Track track, Note note, int notevel, long tick) throws Exception {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.NOTE_OFF, note.getPitch(), notevel);
        MidiEvent event = new MidiEvent(msg, tick);
        track.add(event);
    }

    /**
     * Sets an end of track message that indicates that the track will finish playing
     * on the given tick.
     * @param track
     * @param tick
     * @throws Exception
     */
    public static void setEndOfTrack(Track track, long tick) throws Exception {
        MetaMessage meta = new MetaMessage();
        meta.setMessage(END_OF_TRACK, null ,0);
        MidiEvent event = new MidiEvent(meta, tick);
        track.add(event);
    }

    /**
     * Returns the "status" byte of a particular message in a given track.
     * @param track
     * @param eventIndex
     * @return
     */
    public static int getMessageType(Track track, int eventIndex) {
        MidiEvent currEvent = track.get(eventIndex);
        MidiMessage message = currEvent.getMessage();
        byte[] messageArray = message.getMessage();
        return messageArray[0];
    }

    /**
     * Retrieves a note from a midi event in the sequence. Assumes that the
     * message at eventIndex in this track has been verified to be a NOTE ON message,
     * though this method would in theory be valid for NOTE OFF messages as well.
     * @param track
     * @param eventIndex
     */
    public static int getNoteFromTrack(Track track, int eventIndex) {
        MidiEvent currEvent = track.get(eventIndex);
        MidiMessage message = currEvent.getMessage();
        byte[] messageArray = message.getMessage();
        int note = (int) messageArray[1];
        return note;
    }
}
