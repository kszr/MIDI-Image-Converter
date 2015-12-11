package imageAndMusicTools;

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
 * A class that lets you search for an instrument by name and retrieve its code 
 * in the soundbank.
 * 
 * Some implementation details from here: http://jsresources.sourceforge.net/examples/DisplaySoundbank.java.html
 * @author abhishekchatterjee
 * Date: 12/10/2015
 */
public class InstrumentBank {
    private BiMap<String, Integer> instrumentMap = HashBiMap.create();
    private Soundbank soundbank;

    public InstrumentBank() throws MidiUnavailableException {
    	soundbank = MidiSystem.getSynthesizer().getDefaultSoundbank();
    	loadInstrumentMap();
    }
    
    public InstrumentBank(Soundbank soundbank) throws MidiUnavailableException {
    	this.soundbank = soundbank == null ? MidiSystem.getSynthesizer().getDefaultSoundbank() : soundbank;
    	loadInstrumentMap();
    }
    
    /**
     * For some reason the same program is allowed to have different instrument names.
     * For now I am discarding duplicates.
     */
    private void loadInstrumentMap() {
    	Instrument[] allInstruments = soundbank.getInstruments();
    	for(Instrument instrument : allInstruments) {
    		String name = instrument.getName().toLowerCase().trim();
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
     * @param program
     * @return
     */
    public String getName(int program) {
    	return instrumentMap.inverse().get(program);
    }
    
    /**
     * Returns the program (an integer between 0 and 127) associated with an instrument name.
     */
    public Integer getProgram(String name) {
    	return instrumentMap.get(name);
    }
    
    /**
     * Returns a list of all the instrument names in the InstrumentBank.
     * @return
     */
    public String[] getAllNames() {
    	Set<Integer> valueSet = new TreeSet<Integer>(instrumentMap.values());
    	List<String> allNames = new ArrayList<String>();
    	for(int value : valueSet) {
    		allNames.add(instrumentMap.inverse().get(value));
    	}
    	return allNames.toArray(new String[allNames.size()]);
    }
    
    public static void main(String[] args) throws MidiUnavailableException {
    	String[] list = new InstrumentBank().getAllNames();
    	for(String name : list)
    		System.out.println(name);
    }
}
