package easyMahout.GUI.classification;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class EvaluatorRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox comboBoxEvaluator;

	public EvaluatorRecommenderPanel() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectEvaluator = new JLabel("Select evaluator:");
		labelSelectEvaluator.setBounds(10, 11, 216, 14);
		add(labelSelectEvaluator);

		comboBoxEvaluator = new JComboBox();
		comboBoxEvaluator.setMaximumRowCount(16);
		comboBoxEvaluator.setModel(new DefaultComboBoxModel(new String[] { "Abstract Difference", "Average Absolute Difference",
				"Generic IRStats", "Generic Televant Items Data Splitter", "Order Based Recomender", "RMS Recomender" }));
		comboBoxEvaluator.setBounds(10, 36, 197, 20);
		add(comboBoxEvaluator);

	}
}
