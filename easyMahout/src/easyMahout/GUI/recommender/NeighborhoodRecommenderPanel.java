package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class NeighborhoodRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox comboBoxNeighborhood;

	public NeighborhoodRecommenderPanel() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectNeighborhood = new JLabel("Select neighborhood user function:");
		labelSelectNeighborhood.setBounds(10, 11, 216, 14);
		add(labelSelectNeighborhood);

		comboBoxNeighborhood = new JComboBox();
		comboBoxNeighborhood.setMaximumRowCount(16);
		comboBoxNeighborhood.setModel(new DefaultComboBoxModel(new String[] { "Abstract", "Caching", "Nearest N", "Threshold" }));
		comboBoxNeighborhood.setBounds(10, 36, 197, 20);
		add(comboBoxNeighborhood);

	}
}
