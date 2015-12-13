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
	public enum Name {
		WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH
	}
	
	public enum Dot {
		ZERO, ONE, TWO, THREE
	}
	
	private Name name;
	private Dot dot;
	public double duration;
	private int pitch;
	
	/**
	 * Initializes a note.
	 * @param name
	 * @param dotx
	 * @param pitch	Pitch needs to correspond to the Midi standard.
	 */
	public Note(Name name, Dot dotx, int pitch) {
		this.name = name;
		this.dot = dotx;
		this.pitch = pitch;
		setDuration();
	}
	
	public Name getName() {
		return this.name;
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
	
	private void setDuration() {
		duration = 0.0;
		switch(name) {
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
}
