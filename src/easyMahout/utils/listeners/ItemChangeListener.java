package easyMahout.utils.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class ItemChangeListener implements ItemListener {	

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() instanceof JComboBox) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				MainRecommenderPanel.setConfigurationModified(true);
			}
		} else if (event.getSource() instanceof JCheckBox) {
			if (event.getStateChange() == ItemEvent.SELECTED || event.getStateChange() == ItemEvent.DESELECTED) {
				MainRecommenderPanel.setConfigurationModified(true);
			}
		} else if (event.getSource() instanceof JRadioButton) {
			if (event.getStateChange() == ItemEvent.SELECTED || event.getStateChange() == ItemEvent.DESELECTED) {
				MainRecommenderPanel.setConfigurationModified(true);
			}
		}
	}
}