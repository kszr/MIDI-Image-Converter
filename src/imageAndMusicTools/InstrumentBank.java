package imageAndMusicTools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;

/**
 * A class that lets you search for an instrument by name and retrieve its code 
 * in the soundbank.
 * 
 * Some implementation details from here: http://jsresources.sourceforge.net/examples/DisplaySoundbank.java.html
 * @author abhishekchatterjee
 *
 */
public class InstrumentBank {
    private Map<String, Integer> instrumentMap = new HashMap<String, Integer>();
    private Soundbank soundbank;

    public InstrumentBank() throws MidiUnavailableException {
    	soundbank = MidiSystem.getSynthesizer().getDefaultSoundbank();
    	loadInstrumentMap();
    }
    
    public InstrumentBank(Soundbank soundbank) throws MidiUnavailableException {
    	this.soundbank = soundbank == null ? MidiSystem.getSynthesizer().getDefaultSoundbank() : soundbank;
    	loadInstrumentMap();
    }
    
    private void loadInstrumentMap() {
    	Instrument[] allInstruments = soundbank.getInstruments();
    	for(Instrument instrument : allInstruments) {
    		String name = instrument.getName().toLowerCase();
    		int program = instrument.getPatch().getProgram();
    		instrumentMap.put(name, program);
    	}
    }
    
    /**
     * Returns the name of the instrument associated with this program (an integer between 0 and 127),
     * or null, if no such instrument exists.
     * @param program
     * @return
     */
    public String getName(int program) {
    	for(String name : instrumentMap.keySet()) {
    		if(instrumentMap.get(name.toLowerCase()) == program)
    			return name;
    	}
    	return null;
    }
    
    /**
     * Returns the program (an integer between 0 and 127) associated with an instrument name.
     */
    public Integer getProgram(String name) {
    	return instrumentMap.get(name);
    }
}
