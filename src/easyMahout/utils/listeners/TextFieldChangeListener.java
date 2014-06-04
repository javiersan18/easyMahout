package easyMahout.utils.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class TextFieldChangeListener implements DocumentListener {

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
		MainRecommenderPanel.setConfigurationModified(true);
	}

}
