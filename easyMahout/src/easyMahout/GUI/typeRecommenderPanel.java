package easyMahout.GUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class typeRecommenderPanel extends JPanel {

	private JComboBox comboBoxType;

	public typeRecommenderPanel() {

		comboBoxType = new JComboBox();
		comboBoxType.setBounds(367, 294, 144, 25);
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] {"User-based", "Item-based", "Clustering" }));
		this.add(comboBoxType);
	}

}
