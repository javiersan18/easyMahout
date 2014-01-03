package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AbstractDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.TextFieldChangeListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EvaluatorRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox comboBoxEvaluator;

	private HelpTooltip helpTooltip;

	private JLabel lblTrainingPercentage;

	private JTextField tfEvaluation;

	private JTextField tfTraining;

	private JLabel lblEvaluationPercentage;

	private final static Logger log = Logger.getLogger(EvaluatorRecommenderPanel.class);

	private JButton btnEvaluate;

	protected boolean irsStats;

	private static DefaultComboBoxModel booleanEvaluator;

	private static DefaultComboBoxModel preferencesEvaluator;

	private static boolean booleanPreferences;

	public EvaluatorRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Evaluator", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		preferencesEvaluator = new DefaultComboBoxModel(new String[] { Constants.Evaluator.AVERAGE_ABSOLUTE_DIFFERENCE,
				Constants.Evaluator.RMS_RECOMMENDER, Constants.Evaluator.GENERIC_IRSTATS });

		booleanEvaluator = new DefaultComboBoxModel(new String[] { Constants.Evaluator.GENERIC_IRSTATS });

		booleanPreferences = false;

		comboBoxEvaluator = new JComboBox();
		comboBoxEvaluator.setMaximumRowCount(16);
		comboBoxEvaluator.setModel(preferencesEvaluator);
		comboBoxEvaluator.setBounds(38, 36, 197, 20);
		add(comboBoxEvaluator);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_EVALUATOR);
		add(helpTooltip);

		lblTrainingPercentage = new JLabel("Training percentage");
		lblTrainingPercentage.setBounds(38, 70, 137, 14);
		add(lblTrainingPercentage);

		tfEvaluation = new JTextField();
		tfEvaluation.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEvaluation.setColumns(5);
		tfEvaluation.setBounds(177, 98, 62, 20);
		tfEvaluation.setText("1.0");
		add(tfEvaluation);
		tfEvaluation.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double d = Double.parseDouble(text);
					if (d >= 0.0 && d <= 1.0) {
						tfEvaluation.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("Evaluation percentage has to be an real number in range [0,0 .. 1,0]", Constants.Log.ERROR);
						tfEvaluation.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("Evaluation percentage has to be an real number in range [0,0 .. 1,0]", Constants.Log.ERROR);
					tfEvaluation.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfEvaluation.getDocument().addDocumentListener(new TextFieldChangeListener());

		tfTraining = new JTextField();
		tfTraining.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTraining.setBounds(177, 67, 62, 20);
		tfTraining.setText("0.7");
		add(tfTraining);
		tfTraining.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double d = Double.parseDouble(text);
					if (d >= 0.0 && d <= 1.0) {
						tfTraining.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("Training percentage has to be an real number in range [0,0 .. 1,0]", Constants.Log.ERROR);
						tfTraining.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("Training percentage has to be an real number in range [0,0 .. 1,0]", Constants.Log.ERROR);
					tfTraining.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfTraining.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblEvaluationPercentage = new JLabel("Evaluation percentage");
		lblEvaluationPercentage.setBounds(38, 101, 115, 14);
		add(lblEvaluationPercentage);

		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setAlignmentX(0.5f);
		btnEvaluate.setBounds(371, 364, 89, 23);
		add(btnEvaluate);
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// construir builder
				DataModel model = dataModelPanel.getDataModel();
				if (model != null) {
					RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
						@Override
						public Recommender buildRecommender(DataModel model)
						throws TasteException {
							UserSimilarity similarity = SimilarityRecommenderPanel.getUserSimilarity(model);
							UserNeighborhood neighborhood = NeighborhoodRecommenderPanel.getNeighborhood(similarity, model);
							return new GenericUserBasedRecommender(model, neighborhood, similarity);
						}
					};
				} else {
					log.error("Trying to run a recommender without datamodel loaded");
					MainGUI.writeResult("Trying to run a recommender without a dataModel loaded", Constants.Log.ERROR);
					return null;
				}

				GenericRecommenderIRStatsEvaluator evaluatorIRStats = null;
				RecommenderEvaluator evaluator = null;

				if (booleanPreferences) {
					evaluatorIRStats = new GenericRecommenderIRStatsEvaluator();
					irsStats = true;
				} else {

					String evaluatorType = (String) comboBoxEvaluator.getSelectedItem();
					switch (evaluatorType) {

						case Constants.Evaluator.AVERAGE_ABSOLUTE_DIFFERENCE:
							evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
							irsStats = false;
							break;
						case Constants.Evaluator.RMS_RECOMMENDER:
							evaluator = new RMSRecommenderEvaluator();
							irsStats = false;
							break;
						case Constants.Evaluator.GENERIC_IRSTATS:
							evaluatorIRStats = new GenericRecommenderIRStatsEvaluator();
							irsStats = true;
							break;
						default:
							evaluator = null;
					}
				}

				if (irsStats) {
					evaluatorIRStats.evaluate(recommenderBuilder,
							null,
							DataModelRecommenderPanel.getDataModel(),
							null,
							at,
							GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
							evaluationPercentage);
				} else {

				}

			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static void setBooleanPreferences(boolean booleanPrefs) {
		booleanPreferences = booleanPrefs;
		if (booleanPrefs) {
			comboBoxEvaluator.setModel(booleanEvaluator);
		} else {
			comboBoxEvaluator.setModel(preferencesEvaluator);
		}
	}
}
