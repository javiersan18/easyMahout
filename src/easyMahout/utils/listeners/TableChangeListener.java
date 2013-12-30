package easyMahout.utils.listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import easyMahout.GUI.recommender.MainRecommenderPanel;

public class TableChangeListener implements TableModelListener {

	private final static Logger log = Logger.getLogger(TableChangeListener.class);

	public void tableChanged(TableModelEvent e) {
		log.info("table change");
		MainRecommenderPanel.setConfigurationModified(true);
	}

}
