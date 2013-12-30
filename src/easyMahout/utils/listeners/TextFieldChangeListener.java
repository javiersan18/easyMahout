package easyMahout.utils.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class TextFieldChangeListener implements DocumentListener {

	private final static Logger log = Logger.getLogger(TextFieldChangeListener.class);

	public void changedUpdate(DocumentEvent e) {
		modified();
	}

	public void removeUpdate(DocumentEvent e) {
		modified();
	}

	public void insertUpdate(DocumentEvent e) {
		modified();
	}

	public void modified() {
		log.info("textfield change");
		MainRecommenderPanel.setConfigurationModified(true);
	}

}
