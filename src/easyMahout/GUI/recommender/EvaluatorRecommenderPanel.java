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
import javax.swing.border.TitledBorder;

import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;

public class EvaluatorRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxEvaluator;

	private HelpTooltip helpTooltip;

	public EvaluatorRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Evaluator", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxEvaluator = new JComboBox();
		comboBoxEvaluator.setMaximumRowCount(16);
		comboBoxEvaluator.setModel(new DefaultComboBoxModel(new String[] { Constants.Evaluator.ABSTRACT_DIFFERENCE,
				Constants.Evaluator.AVERAGE_ABSOLUTE_DIFFERENCE, Constants.Evaluator.GENERIC_IRSTATS,
				Constants.Evaluator.GENERIC_RELEVANT_ITEMS_DATA_SPLITTER, Constants.Evaluator.ORDER_BASED_RECOMMENDER,
				Constants.Evaluator.RMS_RECOMMENDER }));
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

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
}
