package audiovisual;

/**
 * A simple implementation of the AudioVisual interface that performs an approximately
 * symmetrical mapping between the notes on a piano and color intensity. 
 * In particular,  note is assumed to be a value between 21 and 108, representing
 * the 88 keys of a piano in the MIDI standard, and intensity is a number between 0 and 255,
 * corresponding to R, G, or B. With 85 different pitches as possible output, this function
 * spans much of the 88-note range of a piano, though it is nevertheless not a bijection.
 * @author abhishekchatterjee
 * Date: Dec 11, 2015
 * Time: 2:34:02 PM
 */
public class SimpleAudioVisual implements AudioVisual<Integer, Integer> {

	@Override
	public Integer getColor(Integer note) {
		return (note-21)*3;
	}
 
	@Override
	public Integer getNote(Integer color) {
		return 21 + color/3;
	}

}
