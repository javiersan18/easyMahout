package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.GUI.classification.SimilarityRecommenderPanel;
import easyMahout.utils.Constants;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxType;

	public TypeRecommenderPanel() {
		// super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectType = new JLabel("Select type of recommender:");
		labelSelectType.setBounds(21, 11, 216, 14);
		add(labelSelectType);

		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED,
				 }));
		comboBoxType.setBounds(38, 36, 141, 20);
		add(comboBoxType);

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RecommenderJPanel.getSimilarityPanel().setModelSimilarity(((JComboBox)e.getSource()).getSelectedItem());				
			}
		});

	}

	public String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}
}
