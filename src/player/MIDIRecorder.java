package player;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import tools.InstrumentBank;
import tools.MIDISequenceTools;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/15/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MIDIRecorder {
    private final int NUM_TRACKS = 1;

    private final Sequencer sequencer = MidiSystem.getSequencer();
    private Sequence sequence;
    private Track[] tracks;
    private boolean isRecording = false;

    public MIDIRecorder() throws Exception {
        sequence = new Sequence(MIDISequenceTools.DIVISION_TYPE, MIDISequenceTools.TICKS_PER_BEAT);
        tracks = new Track[NUM_TRACKS];
        tracks[0] = sequence.createTrack();
        sequencer.recordEnable(tracks[0], -1);
        sequencer.setSequence(sequence);

        MIDISequenceTools.setUpSystem(tracks);
        MIDISequenceTools.setTempo(tracks);

        String[] tracknames = new String[NUM_TRACKS];
        tracknames[0] = "Piano RH";
        
        MIDISequenceTools.setTrackNames(tracks, tracknames);
        MIDISequenceTools.setOmniOn(tracks);
        MIDISequenceTools.setPoly(tracks);
        MIDISequenceTools.setInstrument(tracks, new InstrumentBank().getProgram("piano 1"));
    }

    /**
     * Starts recording.
     */
    public void startRecording() throws Exception {
        sequencer.open();
        sequencer.startRecording();
        isRecording = true;
    }

    /**
     * Stops recording.
     */
    public void stopRecording() throws Exception {
        sequencer.close();
        sequencer.stopRecording();
        isRecording = false;
    }

    /**
     * Returns the sequence that has been recorded to.
     * @return
     */
    public Sequence getSequence() throws Exception {
        if(isRecording) {
            throw new Exception("DON'T YOU DARE REQEUST A SEQUENCE WHILE RECORDING.");
        }

        return sequence;
    }
}
