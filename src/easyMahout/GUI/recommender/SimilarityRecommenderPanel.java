package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
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

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class SimilarityRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DefaultComboBoxModel<String> userSimilarity;

	private DefaultComboBoxModel<String> itemSimilarity;

	private DefaultComboBoxModel<String> distributedModel;

	private static JComboBox<String> comboBoxSimilarity;

	private static JCheckBox chckbxWeighted;

	private HelpTooltip helpTooltip;

	//private final static Logger log = Logger.getLogger(SimilarityRecommenderPanel.class);

	private static JTextField tfMaxSimilarities;

	private static JTextField tfMaxPreferences;

	private static JTextField tfMinPreferences;

	private static JTextField tfThreshold;

	private JLabel lblMaxsimilaritiesperitem;

	private JLabel lblMaxPreferencesPer;

	private JLabel lblMinPreferencesPer;

	private JLabel lblThreshold;
	private static JTextField tfMaxPrefsInItemSimilarity;
	private JLabel lblMaxPrefsItemSimilarity;

	public SimilarityRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Similarity metric", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		userSimilarity = new DefaultComboBoxModel<String>(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.SPEARMAN,
				Constants.Similarity.TANIMOTO, Constants.Similarity.COSINE });

		itemSimilarity = new DefaultComboBoxModel<String>(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.TANIMOTO,
				Constants.Similarity.COSINE });

		distributedModel = new DefaultComboBoxModel<String>(new String[] { Constants.SimilarityDistributed.EUCLIDEAN,
				Constants.SimilarityDistributed.PEARSON, Constants.SimilarityDistributed.COOCURRENCE,
				Constants.SimilarityDistributed.LOGARITHMIC, Constants.SimilarityDistributed.TANIMOTO,
				Constants.SimilarityDistributed.COSINE, Constants.SimilarityDistributed.ZERO_COSINE });

		comboBoxSimilarity = new JComboBox<String>();
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

		lblMaxsimilaritiesperitem = new JLabel("Max similarities per item");
		lblMaxsimilaritiesperitem.setBounds(38, 80, 208, 14);
		add(lblMaxsimilaritiesperitem);
		lblMaxsimilaritiesperitem.setVisible(false);

		tfMaxSimilarities = new JTextField();
		tfMaxSimilarities.setBounds(310, 80, 86, 20);
		tfMaxSimilarities.setText("100");
		add(tfMaxSimilarities);
		tfMaxSimilarities.setColumns(10);
		tfMaxSimilarities.setVisible(false);
		tfMaxSimilarities.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblMaxPreferencesPer = new JLabel("Max preferences per user");
		lblMaxPreferencesPer.setBounds(38, 115, 197, 14);
		add(lblMaxPreferencesPer);
		lblMaxPreferencesPer.setVisible(false);

		tfMaxPreferences = new JTextField();
		tfMaxPreferences.setBounds(310, 115, 86, 20);
		tfMaxPreferences.setText("500");
		add(tfMaxPreferences);
		tfMaxPreferences.setColumns(10);
		tfMaxPreferences.setVisible(false);
		tfMaxPreferences.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblMinPreferencesPer = new JLabel("Min preferences per user");
		lblMinPreferencesPer.setBounds(38, 150, 197, 14);
		add(lblMinPreferencesPer);
		lblMinPreferencesPer.setVisible(false);

		tfMinPreferences = new JTextField();
		tfMinPreferences.setBounds(310, 150, 86, 20);
		tfMinPreferences.setText("1");
		add(tfMinPreferences);
		tfMinPreferences.setColumns(10);
		tfMinPreferences.setVisible(false);
		tfMinPreferences.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblThreshold = new JLabel("Threshold");
		lblThreshold.setBounds(38, 220, 197, 14);
		add(lblThreshold);
		lblThreshold.setVisible(false);
		

		tfThreshold = new JTextField();
		tfThreshold.setBounds(310, 220, 86, 20);
		add(tfThreshold);
		tfThreshold.setColumns(10);
		tfThreshold.setVisible(false);
		tfThreshold.getDocument().addDocumentListener(new TextFieldChangeListener());
		
		lblMaxPrefsItemSimilarity = new JLabel("Max prefs. per user in Item Similarity");
		lblMaxPrefsItemSimilarity.setBounds(38, 185, 310, 14);
		add(lblMaxPrefsItemSimilarity);
		lblMaxPrefsItemSimilarity.setVisible(false);
		
		tfMaxPrefsInItemSimilarity = new JTextField();
		tfMaxPrefsInItemSimilarity.setBounds(310, 185, 86, 20);
		tfMaxPrefsInItemSimilarity.setText("1000");
		add(tfMaxPrefsInItemSimilarity);
		tfMaxPrefsInItemSimilarity.setColumns(10);
		tfMaxPrefsInItemSimilarity.setVisible(false);
		tfMaxPrefsInItemSimilarity.getDocument().addDocumentListener(new TextFieldChangeListener());

		comboBoxSimilarity.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String similarity = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
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
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
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
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
			return null;
		}
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			chckbxWeighted.setVisible(false);
			tfMaxPreferences.setVisible(true);
			tfMaxSimilarities.setVisible(true);
			tfMinPreferences.setVisible(true);
			tfMaxPrefsInItemSimilarity.setVisible(true);
			tfThreshold.setVisible(true);
			lblMaxPreferencesPer.setVisible(true);
			lblMinPreferencesPer.setVisible(true);
			lblMaxsimilaritiesperitem.setVisible(true);
			lblMaxPrefsItemSimilarity.setVisible(true);
			lblThreshold.setVisible(true);
			comboBoxSimilarity.setModel(distributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_SIMILARITY_DIST);
		} else {
			chckbxWeighted.setVisible(true);
			tfMaxPreferences.setVisible(false);
			tfMaxSimilarities.setVisible(false);
			tfMinPreferences.setVisible(false);
			tfMaxPrefsInItemSimilarity.setVisible(false);
			tfThreshold.setVisible(false);
			lblMaxPreferencesPer.setVisible(false);
			lblMinPreferencesPer.setVisible(false);
			lblMaxsimilaritiesperitem.setVisible(false);
			lblMaxPrefsItemSimilarity.setVisible(false);
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
	
	public static String getMaxPrefsInItemSimilarity() {		
		if (StringUtils.isNotBlank(tfMaxPrefsInItemSimilarity.getText())) {
			return tfMaxPrefsInItemSimilarity.getText();
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
