package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

import javax.swing.JCheckBox;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimilarityRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel userSimilarity;

	private DefaultComboBoxModel itemSimilarity;

	private DefaultComboBoxModel distributedModel;

	private static JComboBox comboBoxSimilarity;

	private static JCheckBox chckbxWeighted;

	private HelpTooltip helpTooltip;

	private final static Logger log = Logger.getLogger(SimilarityRecommenderPanel.class);

	private static JTextField tfMaxSimilarities;

	private static JTextField tfMaxPreferences;

	private static JTextField tfMinPreferences;

	private static JTextField tfThreshold;

	private JLabel lblMaxsimilaritiesperitem;

	private JLabel lblMaxPreferencesPer;

	private JLabel lblMinPreferencesPer;

	private JLabel lblThreshold;

	public SimilarityRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Similarity metric", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		userSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.SPEARMAN,
				Constants.Similarity.TANIMOTO, Constants.Similarity.COSINE });

		itemSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.TANIMOTO,
				Constants.Similarity.COSINE });

		distributedModel = new DefaultComboBoxModel(new String[] { Constants.SimilarityDistributed.EUCLIDEAN,
				Constants.SimilarityDistributed.PEARSON, Constants.SimilarityDistributed.COOCURRENCE,
				Constants.SimilarityDistributed.LOGARITHMIC, Constants.SimilarityDistributed.TANIMOTO,
				Constants.SimilarityDistributed.COSINE, Constants.SimilarityDistributed.ZERO_COSINE });

		comboBoxSimilarity = new JComboBox();
		comboBoxSimilarity.setMaximumRowCount(16);

		MainRecommenderPanel.getTypePanel();
		String type = TypeRecommenderPanel.getSelectedType();
		this.setModelSimilarity(type);

		comboBoxSimilarity.setBounds(38, 36, 197, 20);
		add(comboBoxSimilarity);
		comboBoxSimilarity.addItemListener(new ItemChangeListener());

		chckbxWeighted = new JCheckBox("Weighted");
		chckbxWeighted.setBounds(256, 35, 97, 23);
		add(chckbxWeighted);
		chckbxWeighted.addItemListener(new ItemChangeListener());

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_SIMILARITY);
		add(helpTooltip);

		lblMaxsimilaritiesperitem = new JLabel("Max similarities per Item");
		lblMaxsimilaritiesperitem.setBounds(38, 80, 135, 14);
		add(lblMaxsimilaritiesperitem);
		lblMaxsimilaritiesperitem.setVisible(false);

		tfMaxSimilarities = new JTextField();
		tfMaxSimilarities.setBounds(183, 77, 86, 20);
		add(tfMaxSimilarities);
		tfMaxSimilarities.setColumns(10);
		tfMaxSimilarities.setVisible(false);
		tfMaxSimilarities.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblMaxPreferencesPer = new JLabel("Max preferences per user");
		lblMaxPreferencesPer.setBounds(38, 115, 135, 14);
		add(lblMaxPreferencesPer);
		lblMaxPreferencesPer.setVisible(false);

		tfMaxPreferences = new JTextField();
		tfMaxPreferences.setBounds(183, 112, 86, 20);
		add(tfMaxPreferences);
		tfMaxPreferences.setColumns(10);
		tfMaxPreferences.setVisible(false);
		tfMaxPreferences.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblMinPreferencesPer = new JLabel("Min preferences per user");
		lblMinPreferencesPer.setBounds(38, 150, 135, 14);
		add(lblMinPreferencesPer);
		lblMinPreferencesPer.setVisible(false);

		tfMinPreferences = new JTextField();
		tfMinPreferences.setBounds(183, 147, 86, 20);
		add(tfMinPreferences);
		tfMinPreferences.setColumns(10);
		tfMinPreferences.setVisible(false);
		tfMinPreferences.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblThreshold = new JLabel("Threshold");
		lblThreshold.setBounds(38, 185, 128, 14);
		add(lblThreshold);
		lblThreshold.setVisible(false);

		tfThreshold = new JTextField();
		tfThreshold.setBounds(183, 182, 86, 20);
		add(tfThreshold);
		tfThreshold.setColumns(10);
		tfThreshold.setVisible(false);
		tfThreshold.getDocument().addDocumentListener(new TextFieldChangeListener());

		comboBoxSimilarity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String similarity = (String) ((JComboBox) e.getSource()).getSelectedItem();
				if (similarity.equals(Constants.Similarity.EUCLIDEAN) || similarity.equals(Constants.Similarity.PEARSON)
						|| similarity.equals(Constants.Similarity.COSINE)) {
					chckbxWeighted.setEnabled(true);
				} else {
					chckbxWeighted.setEnabled(false);
				}
			}
		});

	}

	public String getSelectedSimilarity() {
		return (String) comboBoxSimilarity.getSelectedItem();
	}

	public boolean isWeighted() {
		return chckbxWeighted.isSelected();
	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public void setModelSimilarity(String type) {
		if (type.equals(Constants.RecommType.ITEMBASED)) {
			comboBoxSimilarity.setModel(itemSimilarity);
		} else if (type.equals(Constants.RecommType.USERBASED)) {
			comboBoxSimilarity.setModel(userSimilarity);
		}
	}

	public static UserSimilarity getUserSimilarity(DataModel dataModel) {
		try {
			if (chckbxWeighted.isSelected()) {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel, Weighting.WEIGHTED);
					default:
						return null;
				}
			} else {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel);
					case Constants.Similarity.CITYBLOCK:
						return new CityBlockSimilarity(dataModel);
					case Constants.Similarity.LOGARITHMIC:
						return new LogLikelihoodSimilarity(dataModel);
					case Constants.Similarity.SPEARMAN:
						return new SpearmanCorrelationSimilarity(dataModel);
					case Constants.Similarity.TANIMOTO:
						return new TanimotoCoefficientSimilarity(dataModel);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel);
					default:
						return null;
				}
			}
		} catch (TasteException e) {
			// TODO: Taste exception error
			log.error("error creating user similarity");
			return null;
		}
	}

	public static ItemSimilarity getItemSimilarity(DataModel dataModel) {
		try {
			if (chckbxWeighted.isSelected()) {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel, Weighting.WEIGHTED);
					default:
						return null;
				}
			} else {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel);
					case Constants.Similarity.CITYBLOCK:
						return new CityBlockSimilarity(dataModel);
					case Constants.Similarity.LOGARITHMIC:
						return new LogLikelihoodSimilarity(dataModel);
					case Constants.Similarity.TANIMOTO:
						return new TanimotoCoefficientSimilarity(dataModel);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel);
					default:
						return null;
				}
			}
		} catch (TasteException e) {
			// TODO: Taste exception error
			log.error("error creating item similarity");
			return null;
		}
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			chckbxWeighted.setVisible(false);
			tfMaxPreferences.setVisible(true);
			tfMaxSimilarities.setVisible(true);
			tfMinPreferences.setVisible(true);
			tfThreshold.setVisible(true);
			lblMaxPreferencesPer.setVisible(true);
			lblMinPreferencesPer.setVisible(true);
			lblMaxsimilaritiesperitem.setVisible(true);
			lblThreshold.setVisible(true);
			comboBoxSimilarity.setModel(distributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_SIMILARITY_DIST);
		} else {
			chckbxWeighted.setVisible(true);
			tfMaxPreferences.setVisible(false);
			tfMaxSimilarities.setVisible(false);
			tfMinPreferences.setVisible(false);
			tfThreshold.setVisible(false);
			lblMaxPreferencesPer.setVisible(false);
			lblMinPreferencesPer.setVisible(false);
			lblMaxsimilaritiesperitem.setVisible(false);
			lblThreshold.setVisible(false);
			MainRecommenderPanel.getTypePanel();
			String type = TypeRecommenderPanel.getSelectedType();
			this.setModelSimilarity(type);
			helpTooltip.setText(RecommenderTips.RECOMM_SIMILARITY);
		}
	}

	public static String getDistributedSimilarity() {
		if (MainGUI.isDistributed()) {
			String similarity = (String) comboBoxSimilarity.getSelectedItem();
			switch (similarity) {
				case Constants.SimilarityDistributed.PEARSON:
					return Constants.SimilarityDistributedParameter.PEARSON;
				case Constants.SimilarityDistributed.EUCLIDEAN:
					return Constants.SimilarityDistributedParameter.EUCLIDEAN;
				case Constants.SimilarityDistributed.COOCURRENCE:
					return Constants.SimilarityDistributedParameter.COOCURRENCE;
				case Constants.SimilarityDistributed.LOGARITHMIC:
					return Constants.SimilarityDistributedParameter.LOGARITHMIC;
				case Constants.SimilarityDistributed.TANIMOTO:
					return Constants.SimilarityDistributedParameter.TANIMOTO;
				case Constants.SimilarityDistributed.COSINE:
					return Constants.SimilarityDistributedParameter.COSINE;
				case Constants.SimilarityDistributed.ZERO_COSINE:
					return Constants.SimilarityDistributedParameter.ZERO_COSINE;
				default:
					return null;
			}
		} else {
			// TODO ERROR
			return null;
		}
	}

	public static String getMaxSimilarities() {		
		if (StringUtils.isNotBlank(tfMaxSimilarities.getText())) {
			return tfMaxSimilarities.getText();
		} else {
			return " ";
		}
	}

	public static String getMaxPreferences() {		
		if (StringUtils.isNotBlank(tfMaxPreferences.getText())) {
			return tfMaxPreferences.getText();
		} else {
			return " ";
		}
	}

	public static String getMinPreferences() {		
		if (StringUtils.isNotBlank(tfMinPreferences.getText())) {
			return tfMinPreferences.getText();
		} else {
			return " ";
		}
	}

	public static String getThreshold() {		
		if (StringUtils.isNotBlank(tfThreshold.getText())) {
			return tfThreshold.getText();
		} else {
			return " ";
		}
	}
	
	public static String getSelectedMetric(){
		return (String) comboBoxSimilarity.getSelectedItem();
	}
	
	public static String getWeighted(){
		return Boolean.toString(chckbxWeighted.isSelected());
	}

	public static void setSelectedMetric(String metric) {
		comboBoxSimilarity.setSelectedItem(metric);		
	}
	
	public static void setWeighted(boolean selected) {
		chckbxWeighted.setSelected(selected);
	}
	
	public static void setMaxSimilarities(String maxsim) {
		tfMaxSimilarities.setText(maxsim);
	}
	
	public static void setMaxPreferences(String maxprefs) {
		tfMaxPreferences.setText(maxprefs);
	}

	public static void setMinPreferences(String minprefs) {
		tfMinPreferences.setText(minprefs);
	}

	public static void setThreshold(String threshold) {
		tfThreshold.setText(threshold);
	}
	

}
