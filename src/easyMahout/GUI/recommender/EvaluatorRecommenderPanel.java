package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class EvaluatorRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox comboBoxEvaluator;

	public EvaluatorRecommenderPanel() {		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Evaluator", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxEvaluator = new JComboBox();
		comboBoxEvaluator.setMaximumRowCount(16);
		comboBoxEvaluator.setModel(new DefaultComboBoxModel(new String[] { "Abstract Difference", "Average Absolute Difference",
				"Generic IRStats", "Generic Televant Items Data Splitter", "Order Based Recomender", "RMS Recomender" }));
		comboBoxEvaluator.setBounds(38, 36, 197, 20);
		add(comboBoxEvaluator);

	}
}
