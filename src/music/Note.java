package music;

/**
 * An object representing a note. Javax.sound.midi does not explicitly define a note,
 * so an object representing a note might prove useful in packaging certain information
 * that we want, such as note length, pitch, etc. The AudioVisual group of functions
 * (as I've decided to call it), in any case, requires it.
 * A Note is immutable, as the universe intended.
 * @author abhishekchatterjee
 * Date: Dec 13, 2015
 * Time: 2:57:39 PM
 */
public class Note {
	public enum Length {
		WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH
	}
	
	public enum Dot {
		ZERO, ONE, TWO, THREE
	}
	
	private Length length;
	private Dot dot;
	public double duration;
	private int pitch;
	private String[] name;
	
	/**
	 * Initializes a note.
	 * @param length
	 * @param dot
	 * @param pitch	Pitch needs to correspond to the Midi standard.
	 */
	public Note(Length length, Dot dot, int pitch) {
		this.length = length;
		this.dot = dot;
		this.pitch = pitch;
		setDuration();
		setName();
	}
	
	public Length getLength() {
		return this.length;
	}
	
	public Dot getDot() {
		return this.dot;
	}
	
	public double getDuration() {
		return duration;
	}
	
	public int getPitch() {
		return this.pitch;
	}
	
	/**
	 * Returns the name(s) of this note. If the note has two possible values (i.e.,
	 * for black notes), then it returns both names.
	 * @return
	 */
	public String[] getName() {
		return name;
	}
	
	private void setDuration() {
		duration = 0.0;
		switch(length) {
			case WHOLE: duration += 1.0;
						break;
			case HALF: duration += 0.5;
					   break;
			case QUARTER: duration += 0.25;
						  break;
			case EIGHTH: duration += 0.125;
						 break;
			case SIXTEENTH: duration += 0.0625;
						    break;
			case THIRTY_SECOND: duration += 0.03125;
								break;
			case SIXTY_FOURTH: duration += 0.015625;
		}
		
		switch(dot) {
			case ZERO: break;
			case ONE: duration += 0.5*duration;
					  break;
			case TWO: duration += 0.25*duration;
					  break;
			case THREE: duration += 0.125*duration;
		}
	}
	
	/**
	 * Sets the name of a note (e.g. C1).
	 */
	private void setName() { 
		String rawName = "";
		int octave = (pitch-12)/12;
		int key = (pitch-21)%12;
		switch(key) {
			case 0: rawName += "A" + octave;
					break;
			case 1: rawName += "A" + octave + "#,B" + octave + "b";
					break;
			case 2: rawName += "B" + octave;
					break;
			case 3: rawName += "C" + octave;
					break;
			case 4: rawName += "C" + octave + "#,D" + octave + "b";
					break;
			case 5: rawName += "D" + octave;
					break;
			case 6: rawName += "D" + octave + "#,E" + octave + "b";
					break;
			case 7: rawName += "E" + octave;
					break;
			case 8: rawName += "F" + octave;
					break;
			case 9: rawName += "F" + octave + "#,G" + octave + "b";
					break;
			case 10: rawName += "G" + octave;
					break;
			case 11: rawName += "G" + octave + "#,A" + (octave+1) + "b";
		}
		
		name = rawName.split(",");
	}
	
}
