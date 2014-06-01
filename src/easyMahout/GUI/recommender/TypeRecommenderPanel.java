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

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JComboBox<String> comboBoxType;

	private DefaultComboBoxModel<String> distributedModel;

	private DefaultComboBoxModel<String> nonDistributedModel;

	private final JButton btnHelp;

	private HelpTooltip helpTooltip;

	//private final static Logger log = Logger.getLogger(TypeRecommenderPanel.class);

	public TypeRecommenderPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Non distributed recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));

		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		distributedModel = new DefaultComboBoxModel<String>(new String[] { Constants.RecommType.ITEMBASED_DISTRIBUTED, Constants.RecommType.FACTORIZED_RECOMMENDER });
		nonDistributedModel = new DefaultComboBoxModel<String>(new String[] { Constants.RecommType.USERBASED,
				Constants.RecommType.ITEMBASED, Constants.RecommType.FACTORIZED_RECOMMENDER });

		comboBoxType = new JComboBox<String>();
		comboBoxType.setModel(nonDistributedModel);
		comboBoxType.setBounds(38, 45, 205, 20);
		add(comboBoxType);
		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				String type = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				MainRecommenderPanel.getSimilarityPanel().setModelSimilarity(type);
				if (type.equals(Constants.RecommType.ITEMBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(false);
					MainRecommenderPanel.setEnableFactorization(false);
					MainRecommenderPanel.setEnableEvaluator(true);
					MainRecommenderPanel.setEnableSimilarity(true);
				} else if (type.equals(Constants.RecommType.USERBASED)) {
					MainRecommenderPanel.setEnableNeighborhood(true);
					MainRecommenderPanel.setEnableFactorization(false);
					MainRecommenderPanel.setEnableEvaluator(true);
					MainRecommenderPanel.setEnableSimilarity(true);
				} else if (type.equals(Constants.RecommType.FACTORIZED_RECOMMENDER)) {
					MainRecommenderPanel.setEnableFactorization(true);
					MainRecommenderPanel.setEnableNeighborhood(false);
					MainRecommenderPanel.setEnableEvaluator(false);
					if (MainGUI.isDistributed()) {
						MainRecommenderPanel.setEnableSimilarity(false);
					} else {
						MainRecommenderPanel.setEnableSimilarity(true);
					}
				} else if (type.equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {
					MainRecommenderPanel.setEnableFactorization(false);
					MainRecommenderPanel.setEnableSimilarity(true);
				}
			}
		});
		comboBoxType.addItemListener(new ItemChangeListener());

		btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_TYPE);

		// Radio Buttons for choose the different steps before run recommender
		ButtonGroup factorizedButtonGroup = new ButtonGroup();

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
	
	public static void returnToNonDistributed(){
		comboBoxType.setSelectedItem(Constants.RecommType.USERBASED);
		MainRecommenderPanel.setEnableNeighborhood(true);
		MainRecommenderPanel.setEnableFactorization(false);
		MainRecommenderPanel.setEnableEvaluator(true);
		MainRecommenderPanel.setEnableSimilarity(true);
	}
	

	public void setDistributed(boolean distributed) {
		if (distributed) {
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Distributed recommender", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			comboBoxType.setModel(distributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_TYPE_DIST);
			MainRecommenderPanel.setEnableFactorization(false);
			MainRecommenderPanel.setEnableSimilarity(true);
		} else {
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Non distributed recommender", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			comboBoxType.setModel(nonDistributedModel);
			helpTooltip.setText(RecommenderTips.RECOMM_TYPE);
		}
	}

}
