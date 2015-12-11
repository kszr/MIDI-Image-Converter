package audiovisual;

/**
 * Implement this interface to define a mapping between a color and a note.
 * @author abhishekchatterjee
 * Date: Dec 11, 2015
 * Time: 2:36:08 PM
 * @param <K>
 * @param <T>
 */
public abstract interface AudioVisual<K, T> {
	public K getColor(T note);
	public T getNote(K color);
}
