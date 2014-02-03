package easyMahout.utils.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class ItemChangeListener implements ItemListener {

	private final static Logger log = Logger.getLogger(ItemChangeListener.class);

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() instanceof JComboBox) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				log.info("combobox change");
				MainRecommenderPanel.setConfigurationModified(true);
			}
		} else if (event.getSource() instanceof JCheckBox) {
			if (event.getStateChange() == ItemEvent.SELECTED || event.getStateChange() == ItemEvent.DESELECTED) {
				log.info("checkbox change");
				MainRecommenderPanel.setConfigurationModified(true);
			}
		} else if (event.getSource() instanceof JRadioButton) {
			if (event.getStateChange() == ItemEvent.SELECTED || event.getStateChange() == ItemEvent.DESELECTED) {
				log.info("JRadioButton change");
				MainRecommenderPanel.setConfigurationModified(true);
			}
		}
	}
}