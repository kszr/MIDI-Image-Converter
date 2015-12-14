package audiovisual;

import music.Note;

/**
 * A simple implementation of the AudioVisual interface that performs an approximately
 * symmetrical mapping between the notes on a piano and color intensity. 
 * In particular, a note is assumed to be a value between 21 and 108, representing
 * the 88 keys of a piano in the MIDI standard, and intensity is a number between 0 and 255,
 * corresponding to R, G, or B. With 85 different pitches as possible output, this function
 * spans much of the 88-note range of a piano, though it is nevertheless not a bijection.
 * @author abhishekchatterjee
 * Date: Dec 11, 2015
 * Time: 2:34:02 PM
 */
public class SimpleAudioVisual implements AudioVisual {

	@Override
	public Integer getColor(Note note) {
		if(note.getPitch() < 21 || note.getPitch() > 108)
			throw new IllegalArgumentException("pitch is an integer between 21 and 108");
		return (note.getPitch()-21)*3;
	}
 
	@Override
	public Note getNote(Integer color) {
		if(color < 0 || color > 255)
			throw new IllegalArgumentException("color is an integer between 0 and 255");
		return new Note(Note.Length.SIXTEENTH, Note.Dot.ZERO, (21 + color/3));
	}
}
