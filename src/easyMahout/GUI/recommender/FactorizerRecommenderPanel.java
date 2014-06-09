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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.mahout.cf.taste.impl.recommender.AllSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.PreferredItemsNeighborhoodCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;

import easyMahout.GUI.recommender.inputDialogs.ALSWRFactorizerInputDialog;
import easyMahout.GUI.recommender.inputDialogs.FactorizerInputDialog;
import easyMahout.GUI.recommender.inputDialogs.SVDFactorizerInputDialog;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;

public class FactorizerRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox<String> comboBoxFactorizer;

	private DefaultComboBoxModel<String> distributedModel;

	private DefaultComboBoxModel<String> nonDistributedModel;

	private HelpTooltip helpTooltip;

	private static FactorizerInputDialog inputDialog;

	private static JComboBox<String> comboBoxCandidate;

	private JLabel lblCandidateItemStrategy;

	private static JCheckBox chckbxEvaluateFactorizer;

	//private final static Logger log = Logger.getLogger(FactorizerRecommenderPanel.class);

	public FactorizerRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Rating Matrix Factorization ", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		distributedModel = new DefaultComboBoxModel<String>(new String[] { Constants.RecommFactorizer.ALSWR_SHORT, Constants.RecommFactorizer.SVD });

		nonDistributedModel = new DefaultComboBoxModel<String>(new String[] { Constants.RecommFactorizer.ALSWR_SHORT, Constants.RecommFactorizer.SVD,
				Constants.RecommFactorizer.SVD_PLUS_PLUS, Constants.RecommFactorizer.PARALLEL_SGD, Constants.RecommFactorizer.RATING_SGD });

		comboBoxFactorizer = new JComboBox<String>();
		comboBoxFactorizer.setMaximumRowCount(16);
		comboBoxFactorizer.setModel(nonDistributedModel);
		comboBoxFactorizer.setBounds(38, 70, 249, 20);
		add(comboBoxFactorizer);
		comboBoxFactorizer.addItemListener(new ItemChangeListener());

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_FACTORIZER);
		add(helpTooltip);

		JButton btnConfigure = new JButton("Configure");
		btnConfigure.setBounds(327, 69, 100, 20);
		add(btnConfigure);

		JLabel lblFactorizer = new JLabel("Factorizer");
		lblFactorizer.setBounds(38, 40, 249, 14);
		add(lblFactorizer);

		lblCandidateItemStrategy = new JLabel("Candidate Item Strategy");
		lblCandidateItemStrategy.setBounds(38, 110, 249, 14);
		add(lblCandidateItemStrategy);

		comboBoxCandidate = new JComboBox<String>();
		comboBoxCandidate.setBounds(38, 140, 164, 20);
		comboBoxCandidate.setModel(new DefaultComboBoxModel<String>(new String[] { Constants.RecommCandidate.PREFERRED_ITEMS,
				Constants.RecommCandidate.ALL_SIMILAR_ITEMS, Constants.RecommCandidate.ALL_UKNOWN_ITEMS, Constants.RecommCandidate.SAMPLING_ITEMS }));
		add(comboBoxCandidate);

		chckbxEvaluateFactorizer = new JCheckBox("Evaluate Factorizer");
		chckbxEvaluateFactorizer.setBounds(38, 106, 249, 23);
		add(chckbxEvaluateFactorizer);
		chckbxEvaluateFactorizer.setVisible(false);

		btnConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getSelectedFunction().equals(Constants.RecommFactorizer.ALSWR_SHORT)) {

					if (inputDialog == null || !(inputDialog instanceof ALSWRFactorizerInputDialog)) {
						inputDialog = new ALSWRFactorizerInputDialog();
					} else
						inputDialog.setVisible(true);

				} else if (getSelectedFunction().equals(Constants.RecommFactorizer.SVD)) {

					if (inputDialog == null || !(inputDialog instanceof SVDFactorizerInputDialog)) {
						inputDialog = new SVDFactorizerInputDialog();
					} else
						inputDialog.setVisible(true);
				}
			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static void setSelectedFunction(String function) {
		comboBoxFactorizer.setSelectedItem(function);
	}

	public static String getSelectedFunction() {
		return (String) comboBoxFactorizer.getSelectedItem();
	}

	public static String getSelectedCandidate() {
		return (String) comboBoxCandidate.getSelectedItem();
	}

	public static Factorizer getFactorizer() {
		if (getSelectedFunction().equals(Constants.RecommFactorizer.ALSWR_SHORT)) {
			return ALSWRFactorizerInputDialog.getFactorizer();
		} else if (getSelectedFunction().equals(Constants.RecommFactorizer.SVD)) {
			return SVDFactorizerInputDialog.getFactorizer();
		} else if (getSelectedFunction().equals(Constants.RecommFactorizer.SVD_PLUS_PLUS)) {
			return null;
		} else if (getSelectedFunction().equals(Constants.RecommFactorizer.PARALLEL_SGD)) {
			return null;
		} else if (getSelectedFunction().equals(Constants.RecommFactorizer.RATING_SGD)) {
			return null;
		} else {
			return null;
		}
	}

	public static CandidateItemsStrategy getCandidate() {
		if (getSelectedCandidate().equals(Constants.RecommCandidate.PREFERRED_ITEMS)) {
			return new PreferredItemsNeighborhoodCandidateItemsStrategy();
		} else if (getSelectedCandidate().equals(Constants.RecommCandidate.ALL_SIMILAR_ITEMS)) {
			return new AllSimilarItemsCandidateItemsStrategy(SimilarityRecommenderPanel.getItemSimilarity(DataModelRecommenderPanel.getDataModel()));
		} else if (getSelectedCandidate().equals(Constants.RecommCandidate.ALL_UKNOWN_ITEMS)) {
			return new AllUnknownItemsCandidateItemsStrategy();
		} else if (getSelectedCandidate().equals(Constants.RecommCandidate.SAMPLING_ITEMS)) {
			return null;
		} else {
			return null;
		}
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			comboBoxFactorizer.setModel(distributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_FACTORIZER_DIST);
		} else {
			comboBoxFactorizer.setModel(nonDistributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_FACTORIZER);
		}
		lblCandidateItemStrategy.setVisible(!distributed);
		comboBoxCandidate.setVisible(!distributed);
		chckbxEvaluateFactorizer.setVisible(distributed);
	}

	public static FactorizerInputDialog getFactorizerInput() {
		return inputDialog;
	}

	public static boolean getEvaluateFactorizer() {
		return chckbxEvaluateFactorizer.isSelected();
	}
}
