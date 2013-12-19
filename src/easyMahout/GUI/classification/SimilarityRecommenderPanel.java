package easyMahout.GUI.classification;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;


import easyMahout.utils.Constants;

public class SimilarityRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel userSimilarity, itemSimilarity, restSimilarity;

	private JComboBox comboBoxSimilarity;

	public SimilarityRecommenderPanel() {
		// super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectSimilarity = new JLabel("Select similarity metric:");
		labelSelectSimilarity.setBounds(21, 11, 216, 14);
		add(labelSelectSimilarity);

		userSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.SPEARMAN,
				Constants.Similarity.TANIMOTO, Constants.Similarity.COSINE });

		itemSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.TANIMOTO,
				Constants.Similarity.COSINE });

		//restSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.AVERAGING });		

		comboBoxSimilarity = new JComboBox();
		comboBoxSimilarity.setMaximumRowCount(16);
		
//		String type = RecommenderJPanel.getTypePanel().getSelectedType();
//		if (type.equals(Constants.RecommType.ITEMBASED)) {
//			comboBoxSimilarity.setModel(itemSimilarity);
//		} else if (type.equals(Constants.RecommType.USERBASED)) {
//			comboBoxSimilarity.setModel(userSimilarity);
//		} else {
//			comboBoxSimilarity.setModel(restSimilarity);
//		}		
//		
//		comboBoxSimilarity.setBounds(38, 36, 197, 20);
//		add(comboBoxSimilarity);

	}

	public String getSelectedSimilarity() {
		return (String) comboBoxSimilarity.getSelectedItem();
	}
	
	public void setModelSimilarity(Object type) {
		if (type.equals(Constants.RecommType.ITEMBASED)) {
			comboBoxSimilarity.setModel(itemSimilarity);
		} else if (type.equals(Constants.RecommType.USERBASED)) {
			comboBoxSimilarity.setModel(userSimilarity);
		} else {
			comboBoxSimilarity.setModel(restSimilarity);
		}
	}





	
	
}
