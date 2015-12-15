package audiovisual;

import music.Note;

/**
 * An AudioVisual that maps notes to colors in the same way as SimpleAudioVisual, but
 * incorporates dots in the reverse conversion to generate (hopefully) interesting
 * syncopation.
 * @author abhishekchatterjee
 * @date Dec 14, 2015
 * @time 4:30:36 PM
 */
public class DotAudioVisual implements AudioVisual {

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
		
		Note.Length length;
		Note.Dot dot;
		
		if(color < 25) {
			length = Note.Length.HALF;
			dot = Note.Dot.ZERO;
		} else if(color < 50) {
			length = Note.Length.QUARTER;
			dot = Note.Dot.ZERO;
		} else if(color < 100) {
			length = Note.Length.EIGHTH;
			dot = Note.Dot.ZERO;
		} else if(color < 150) {
			length = Note.Length.SIXTEENTH;
			dot = Note.Dot.ZERO;
		} else if(color < 200) {
			length = Note.Length.EIGHTH;
			dot = Note.Dot.ONE;
		} else {
			length = Note.Length.QUARTER;
			dot = Note.Dot.ONE;
		}
		
		return new Note(length, dot, (21 + color/3));
	}

}
