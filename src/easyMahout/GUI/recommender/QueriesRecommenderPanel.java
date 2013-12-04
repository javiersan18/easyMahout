package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;
import javax.swing.JTable;

public class QueriesRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblQueries;
	private JTable table;

	public QueriesRecommenderPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		lblQueries = new JLabel("Add queries:");
		lblQueries.setBounds(21, 11, 216, 14);
		add(lblQueries);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		table.setBounds(56, 263, 294, -209);
		add(table);

	}
}
