package audiovisual;

import music.Note;

/**
 * Implement this interface to define a mapping between a color value and a note.
 * @author abhishekchatterjee
 * Date: Dec 11, 2015
 * Time: 2:36:08 PM
 */
public abstract interface AudioVisual {
	public Integer getColor(Note note);
	public Note getNote(Integer color);
}
