package easyMahout.GUI.classification;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClassifierTips;
import easyMahout.utils.help.ClusterTips;
import easyMahout.utils.help.RecommenderTips;

import javax.swing.SwingConstants;

public class ConfigureClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblGuide;
	private HelpTooltip helpTooltip;

	public ConfigureClassifierPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Configuration Info", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		lblGuide = new JLabel(ClassifierTips.CLASSIF_CONFIG);
		lblGuide.setVerticalAlignment(SwingConstants.TOP);
		lblGuide.setBounds(38, 36, 405, 334);
		add(lblGuide);
		
		
		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClassifierTips.CLASSIF_CONFIG);
		add(helpTooltip);
		}
	
		public static final String CLASSIFIER = "docs/tooltips/classifier/classifier.html";		
		
		public static final String CLASSIFIER_TYPE = "<html>"
				+ "<h4>Classifier Types</h4>"
				+ "<br><p><u><b>Based on different algorithms:</b></u> The system generates classifiers using information</p>"
				+ "<p> for analyse it and extract conclusions about one target.</p><p> The algoritms avalaible in easyMahout " +
				" are Naive Bayes and Complementary Naive Bayes.</p>"
				+ "</html>";

		public static final String CLASSIFIER_DATAMODEL = "docs/tooltips/classifier/datamodel.html";

		public static final String CLASSIFIER_SIMILARITY = "docs/tooltips/classifier/algorithm.html";

		public static final String CLASSIFIER_NEIGHBORHOOD = "docs/tooltips/clustering/testdata.html";

		public static final String CLASSIFIER_EVALUATOR = "docs/tooltips/clustering/testmodel.html";

		public static final String CLASSIFIER_CONFIG = "<html>"
				+ "<h4>Steps to build a classifier</h4>"
				+ "<br><p><u><b>1.</b></u> The system generates classifiers using an algorithm ??? Naive Bayes, Complementary Naive Bayes)</p>"
				+ "<br><p><u><b>2.</b></u> With the input data the algorithm creates a model.</p><p>This model can be used for test new data </p>"
				+ "<p><u><b>4.</b></u></p>" + "</html>";

		public static final String CLASSIFIER_TYPE_DIST = "<html>"
				+ "<h4>Classifier Types Distributed</h4>"
				+ "<br><p><u><b>Based on different algorithms:</b></u> The system generates classifiers using information</p>"
				+ "<p> for analyse it and extract conclusions about one target.</p><p> The algoritms avalaible in easyMahout " +
				" are Naive Bayes and Complementary Naive Bayes.</p>"
				+ "</html>";

		public static final String CLASSIFIER_DATAMODEL_DIST = "docs/tooltips/classifier/datamodel_dist.html";

		public static final String CLASSIFIER_CONFIG_DIST = "<html>"
				+ "<h4>Steps to build a DISTRIBUTED classifier</h4>"
				+ "<br><p><u><b>1.</b></u> The system generates classifiers using an algorithm ??? Naive Bayes, Complementary Naive Bayes)</p>"
				+ "<br><p><u><b>2.</b></u> With the input data the algorithm creates a model.</p><p>This model can be used for test new data </p>"
				+ "<p><u><b>4.</b></u></p>" + "</html>";

		public static final String CLASSIFIER_JOB = "docs/tooltips/classifier/job_dist.html";

	public void setDistributed(boolean distributed) {
		if (distributed) {
			lblGuide.setText(ClassifierTips.CLASSIF_CONFIG_DIST);
		} else {
			lblGuide.setText(ClassifierTips.CLASSIF_CONFIG);
		}
	}
	
	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
}

