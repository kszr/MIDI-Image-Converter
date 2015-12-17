package view.panel;

import javax.swing.JPanel;

/**
 * A panel from which the user can select an instrument from a list of instruments
 * to change the playback instrument.
 * @author Abhishek Chatterjee
 * @date 14-Dec-2015
 * @time 6:59:44 pm
 */
public class InstrumentBankPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String[] instrumentList;
	
	public InstrumentBankPanel(String[] instrumentList) {
		this.instrumentList = instrumentList;
	}
}
