package audiovisual;

import music.Note;

/**
 * A more sophisticated AudioVisual function thing. The pitch-to-color mapping is the same as that
 * in SimpleAudioVisual, but this AudioVisual varies the note length a little bit based on color.
 * @author Abhishek Chatterjee
 * @date 13-Dec-2015
 * @time 4:42:44 pm
 */
public class LengthAudioVisual implements AudioVisual {

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
		Note.Name name;
		if(color <= 100)
			name = Note.Name.EIGHTH;
		else if(color <= 200)
			name = Note.Name.SIXTEENTH;
		else
			name = Note.Name.QUARTER;
		return new Note(name, Note.Dot.ZERO, (21 + color/3));
	}

}
