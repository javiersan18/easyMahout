package easyMahout.utils.listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class TableChangeListener implements TableModelListener {

	public void tableChanged(TableModelEvent e) {
		MainRecommenderPanel.setConfigurationModified(true);
	}

}
