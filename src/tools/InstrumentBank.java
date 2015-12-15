package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * A class that lets you search for an instrument by name and retrieve its program number.
 * Some implementation details from here: http://jsresources.sourceforge.net/examples/DisplaySoundbank.java.html,
 * but this is really general stuff, and there's probably no need to cite a source for it...
 * @author abhishekchatterjee
 * Date: 12/10/2015
 */
public class InstrumentBank {
    private BiMap<String, Integer> instrumentMap = HashBiMap.create();
    private Soundbank soundbank;

    /**
     * Loads the default Soudnbank of the system into the instrument bank.
     * @throws MidiUnavailableException
     */
    public InstrumentBank() throws MidiUnavailableException {
    	soundbank = MidiSystem.getSynthesizer().getDefaultSoundbank();
    	loadInstrumentMap();
    }
    
    /**
     * A constructor that allows the user to pass in an existing Soundbank to load into the
     * instrument bank. It defaults to the system default, if null is passed.
     * @param soundbank
     * @throws MidiUnavailableException
     */
    public InstrumentBank(Soundbank soundbank) throws MidiUnavailableException {
    	this.soundbank = soundbank == null ? MidiSystem.getSynthesizer().getDefaultSoundbank() : soundbank;
    	loadInstrumentMap();
    }
    
    /**
     * For some reason the same program is allowed to have different instrument names.
     * For now I am discarding duplicates, since they are just different names for the same
     * instrument program.
     */
    private void loadInstrumentMap() {
    	Instrument[] allInstruments = soundbank.getInstruments();
    	for(Instrument instrument : allInstruments) {
    		String name = instrument.getName().trim();
    		int program = instrument.getPatch().getProgram();
    		try {
    			instrumentMap.put(name, program);
    		} catch (IllegalArgumentException e) {
    			if(!e.toString().contains("value already present"))
    				throw new IllegalArgumentException(e);
    		}
    	}
    }
    
    /**
     * Returns the name of the instrument associated with this program (an integer between 0 and 127),
     * or null, if no such instrument exists.
     * @param program	The program (a number between 0 and 127).
     * @return			The name of the instrument associated with this program number.
     * @throws IllegalArgumentException if the program is not between 0 and 127.
     */
    public String getName(int program) throws IllegalArgumentException {
    	if(program < 0 || program > 127)
    		throw new IllegalArgumentException("program should be between 0 and 127");
    	return instrumentMap.inverse().get(program);
    }
    
    /**
     * Returns the program (an integer between 0 and 127) associated with an instrument name.
     * Use the getAllNames() method if unsure what names to pass to this method.
     * @param name	The name of the instrument whose program is desired. (Case-insensitive)
     * @return		The program associated with this instrument name.
     */
    public Integer getProgram(String name) {
    	return instrumentMap.get(name);
    }
    
    /**
     * Returns a list of all the instrument names in the InstrumentBank. Use this method if
     * unsure what instrument names are present in the instrument bank. The list is ordered by
     * increasing program number.
     * @return	A list of all the instrument names in the soundbank.
     */
    public String[] getAllNames() {
    	Set<Integer> valueSet = new TreeSet<Integer>(instrumentMap.values());
    	List<String> allNames = new ArrayList<String>();
    	for(int value : valueSet) {
    		allNames.add(instrumentMap.inverse().get(value));
    	}
    	return allNames.toArray(new String[allNames.size()]);
    }
}
