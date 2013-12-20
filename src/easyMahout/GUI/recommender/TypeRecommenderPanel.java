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

import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxType;

	private HelpTooltip helpTooltip;

	public TypeRecommenderPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Type of recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED }));
		comboBoxType.setBounds(38, 36, 141, 20);
		add(comboBoxType);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_TYPE);
		add(helpTooltip);

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) ((JComboBox) e.getSource()).getSelectedItem();
				MainRecommenderPanel.getSimilarityPanel().setModelSimilarity(type);
				if (type.equals(Constants.RecommType.ITEMBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(false);
				} else if (type.equals(Constants.RecommType.USERBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(true);
				}
			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}
}
