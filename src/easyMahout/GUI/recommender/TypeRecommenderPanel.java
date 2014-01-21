package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;

import javax.swing.JRadioButton;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox comboBoxType;

	private DefaultComboBoxModel distributedModel;

	private DefaultComboBoxModel nonDistributedModel;

	private final JButton btnHelp;

	private JRadioButton rdbtnALSWR;

	private JRadioButton rdbtnSVD;

	private HelpTooltip helpTooltip;

	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(TypeRecommenderPanel.class);

	public TypeRecommenderPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Non distributed recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));

		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		distributedModel = new DefaultComboBoxModel(new String[] { Constants.RecommType.ITEMSIMILARITY,
				Constants.RecommType.ITEMBASED_DISTRIBUTED, Constants.RecommType.FACTORIZED_RECOMMENDER });
		nonDistributedModel = new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED,
				Constants.RecommType.FACTORIZED_RECOMMENDER });

		comboBoxType = new JComboBox();
		comboBoxType.setModel(nonDistributedModel);
		comboBoxType.setBounds(38, 36, 205, 20);
		add(comboBoxType);

		btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_TYPE);

		ButtonGroup factorizedButtonGroup = new ButtonGroup();

		rdbtnALSWR = new JRadioButton(Constants.RecommFactorizer.ALSWR);
		rdbtnALSWR.setVisible(false);
		rdbtnALSWR.setBounds(37, 73, 325, 23);
		//add(rdbtnALSWR);
		factorizedButtonGroup.add(rdbtnALSWR);

		rdbtnSVD = new JRadioButton(Constants.RecommFactorizer.SVD);
		rdbtnSVD.setVisible(false);
		rdbtnSVD.setBounds(37, 99, 325, 23);
		//add(rdbtnSVD);
		factorizedButtonGroup.add(rdbtnSVD);

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) ((JComboBox) e.getSource()).getSelectedItem();
				MainRecommenderPanel.getSimilarityPanel().setModelSimilarity(type);
				if (type.equals(Constants.RecommType.ITEMBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(false);
				} else if (type.equals(Constants.RecommType.USERBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(true);
				} else if (type.equals(Constants.RecommType.FACTORIZED_RECOMMENDER)) {
					// TODO desactivar casi todo, activar menu de factorizacion					
					setFactoricerOptions(true);
				} else if (type.equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {
					setFactoricerOptions(false);
				} else if (type.equals(Constants.RecommType.ITEMSIMILARITY)) {
					setFactoricerOptions(false);
				}
			}
		});
		comboBoxType.addItemListener(new ItemChangeListener());

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}

	public static void setSelectedType(String type) {
		comboBoxType.setSelectedItem(type);
	}

	private void setFactoricerOptions(boolean flag) {
		if (MainGUI.isDistributed()) {
			rdbtnALSWR.setVisible(flag);
			rdbtnSVD.setVisible(flag);
		} else {
			rdbtnALSWR.setVisible(false);
			rdbtnSVD.setVisible(false);
		}
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Distributed recommender", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			comboBoxType.setModel(distributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_TYPE_DIST);
		} else {
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Non distributed recommender", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			comboBoxType.setModel(nonDistributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_TYPE);
		}
	}
}
