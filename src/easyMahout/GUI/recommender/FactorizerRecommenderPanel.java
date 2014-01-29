package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.recommender.AllSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.PreferredItemsNeighborhoodCandidateItemsStrategy;
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

	@SuppressWarnings("rawtypes")
	private static JComboBox comboBoxFactorizer;

	private HelpTooltip helpTooltip;

	private FactorizerInputDialog inputDialog;

	private final static Logger log = Logger.getLogger(FactorizerRecommenderPanel.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FactorizerRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Rating Matrix Factorization ", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxFactorizer = new JComboBox();
		comboBoxFactorizer.setMaximumRowCount(16);
		comboBoxFactorizer.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommFactorizer.ALSWR_SHORT,
				Constants.RecommFactorizer.SVD, Constants.RecommFactorizer.SVD_PLUS_PLUS, Constants.RecommFactorizer.PARALLEL_SGD,
				Constants.RecommFactorizer.RATING_SGD }));
		comboBoxFactorizer.setBounds(38, 36, 249, 20);
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
		
//		CandidateItemsStrategy can = new AllSimilarItemsCandidateItemsStrategy(SimilarityRecommenderPanel.getItemSimilarity(DataModelRecommenderPanel.getDataModel()));
//		CandidateItemsStrategy can2 = new AllUnknownItemsCandidateItemsStrategy();
//		CandidateItemsStrategy can3 = new PreferredItemsNeighborhoodCandidateItemsStrategy();
		
		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_FACTORIZER);
		add(helpTooltip);

		JButton btnConfigure = new JButton("Configure");
		btnConfigure.setBounds(335, 35, 89, 23);
		add(btnConfigure);
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

				} else if (getSelectedFunction().equals(Constants.RecommFactorizer.SVD_PLUS_PLUS)) {

				} else if (getSelectedFunction().equals(Constants.RecommFactorizer.PARALLEL_SGD)) {

				} else if (getSelectedFunction().equals(Constants.RecommFactorizer.RATING_SGD)) {

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

}
