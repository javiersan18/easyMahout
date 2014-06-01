package easyMahout.GUI.recommender;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import easyMahout.utils.help.RecommenderTips;

import javax.swing.SwingConstants;

public class ConfigureRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblGuide;

	public ConfigureRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Configuration Info", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		lblGuide = new JLabel(RecommenderTips.RECOMM_CONFIG);
		lblGuide.setVerticalAlignment(SwingConstants.TOP);
		lblGuide.setBounds(38, 36, 405, 334);
		add(lblGuide);
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			lblGuide.setText(RecommenderTips.RECOMM_CONFIG_DIST);
		} else {
			lblGuide.setText(RecommenderTips.RECOMM_CONFIG);
		}
	}

}
