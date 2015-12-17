package tools.phonograph;

import java.util.Iterator;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import tools.MIDISequenceTools;
import music.Note;

/**
 * An interator for notes in a track. "Needle" is an allusion to phonograph needles.
 * @author Abhishek Chatterjee
 * @date 15-Dec-2015
 * @time 4:16:57 pm
 */
public class Needle implements Iterator<Note> {
	private Track track;
	private int eventIndex;
	private int nextIndex;
	
	public Needle(Track track) {
		this.track = track;
		this.eventIndex = -1;
		this.nextIndex = -2;
	}
	
	@Override
	public boolean hasNext() {
		int newEventIndex = this.eventIndex+1;
		try {
			while((MIDISequenceTools.getMessageType(this.track, newEventIndex) & 0xFF) != ShortMessage.NOTE_ON)
				newEventIndex++;
			this.nextIndex = newEventIndex;
			return true;
		} catch(ArrayIndexOutOfBoundsException exception) {
			return false;
		}
	}

	@Override
	public Note next() {
		if(this.nextIndex < (this.eventIndex+1))
			this.hasNext();
		
		Note nextNote = MIDISequenceTools.getNoteFromTrack(this.track, this.nextIndex);
		this.eventIndex = this.nextIndex;
		return nextNote;
	}

	@Override
	public void remove() {
		//Can't remove notes from a track.
	}

}
