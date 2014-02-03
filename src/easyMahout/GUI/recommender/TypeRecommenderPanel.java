package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jidesoft.swing.FolderChooser;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox<String> comboBoxType;

	private DefaultComboBoxModel<String> distributedModel;

	private DefaultComboBoxModel<String> nonDistributedModel;

	private final JButton btnHelp;

	private HelpTooltip helpTooltip;

	private JRadioButton rdbtnConfFactAndRecomm;

	private JRadioButton rdbtnConfFact;

	private JRadioButton rdbtnConfRecomm;

	private static JTextField tfUserFeaturesMatrix;

	private static JTextField tfItemFeaturesMatrix;

	private final static Logger log = Logger.getLogger(TypeRecommenderPanel.class);
	private JLabel lblUserFeaturesMatrix;
	private JLabel lblItemFeaturesMatrix;
	private JButton btnSelectUserFeaturesMatrix;
	private JButton btnSelectItemFeaturesMatrix;

	public TypeRecommenderPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Non distributed recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));

		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		distributedModel = new DefaultComboBoxModel<String>(new String[] { // Constants.RecommType.ITEMSIMILARITY,
				Constants.RecommType.ITEMBASED_DISTRIBUTED, Constants.RecommType.FACTORIZED_RECOMMENDER });
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
					// TODO desactivar casi todo, activar menu de factorizacion
					// setFactoricerOptions(true);
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
				// else if (type.equals(Constants.RecommType.ITEMSIMILARITY)) {
				// //setFactoricerOptions(false);
				// }
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

		rdbtnConfFactAndRecomm = new JRadioButton("Configure Factorizer and Recommender");
		rdbtnConfFactAndRecomm.setBounds(38, 80, 404, 23);
		add(rdbtnConfFactAndRecomm);
		factorizedButtonGroup.add(rdbtnConfFactAndRecomm);

		rdbtnConfFact = new JRadioButton("Configure only the Factorizer");
		rdbtnConfFact.setBounds(38, 110, 404, 23);
		add(rdbtnConfFact);
		factorizedButtonGroup.add(rdbtnConfFact);

		rdbtnConfRecomm = new JRadioButton("Configure only the Recommender");
		rdbtnConfRecomm.setBounds(38, 140, 404, 23);
		add(rdbtnConfRecomm);
		factorizedButtonGroup.add(rdbtnConfRecomm);
		rdbtnConfRecomm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selected = ((JRadioButton) e.getSource()).isSelected();
				if(selected){
					log.debug("radio button only recomm selected");
					feature();
				}else{
					log.debug("radio button only recomm deselected");
				}
				
			}
		});

		// U and M matrix folder chooser
		lblUserFeaturesMatrix = new JLabel("User Features Matrix (U)");
		lblUserFeaturesMatrix.setBounds(38, 180, 157, 14);
		add(lblUserFeaturesMatrix);

		tfUserFeaturesMatrix = new JTextField();
		tfUserFeaturesMatrix.setColumns(10);
		tfUserFeaturesMatrix.setBounds(38, 210, 298, 20);
		add(tfUserFeaturesMatrix);

		btnSelectUserFeaturesMatrix = new JButton("Select Folder...");
		btnSelectUserFeaturesMatrix.setBounds(346, 206, 107, 23);
		add(btnSelectUserFeaturesMatrix);
		btnSelectUserFeaturesMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FolderChooser chooser = new FolderChooser();
				int returnVal = chooser.showOpenDialog(TypeRecommenderPanel.this);
				if (returnVal == FolderChooser.APPROVE_OPTION) {
					File data = chooser.getSelectedFile();
					String absPath = data.getAbsolutePath();
					tfUserFeaturesMatrix.setText(absPath);
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error searching the userFeatures directory.", Constants.Log.ERROR);
					log.error("Error searching userFeatures directory");
				}
			}
		});

		lblItemFeaturesMatrix = new JLabel("Item Features Matrix (M)");
		lblItemFeaturesMatrix.setBounds(38, 250, 157, 14);
		add(lblItemFeaturesMatrix);

		tfItemFeaturesMatrix = new JTextField();
		tfItemFeaturesMatrix.setColumns(10);
		tfItemFeaturesMatrix.setBounds(38, 280, 298, 20);
		add(tfItemFeaturesMatrix);

		btnSelectItemFeaturesMatrix = new JButton("Select Folder...");
		btnSelectItemFeaturesMatrix.setBounds(346, 279, 107, 23);
		add(btnSelectItemFeaturesMatrix);
		btnSelectItemFeaturesMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FolderChooser chooser = new FolderChooser();
				int returnVal = chooser.showOpenDialog(TypeRecommenderPanel.this);
				if (returnVal == FolderChooser.APPROVE_OPTION) {
					File data = chooser.getSelectedFile();
					String absPath = data.getAbsolutePath();
					tfItemFeaturesMatrix.setText(absPath);
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error searching the itemFeatures directory.", Constants.Log.ERROR);
					log.error("Error searching itemFeatures directory");
				}
			}
		});

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

	public static String getUserFeaturesPath() {
		if (StringUtils.isNotBlank(tfUserFeaturesMatrix.getText())) {
			return tfUserFeaturesMatrix.getText();
		} else {
			return "  ";
		}
	}

	public static String getInputFeaturesPath() {
		if (StringUtils.isNotBlank(tfItemFeaturesMatrix.getText())) {
			return tfItemFeaturesMatrix.getText();
		} else {
			return "  ";
		}
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

	private void feature() {
		lblItemFeaturesMatrix.setVisible(true);
		lblUserFeaturesMatrix.setVisible(true);
		tfItemFeaturesMatrix.setVisible(true);
		tfUserFeaturesMatrix.setVisible(true);
		btnSelectItemFeaturesMatrix.setVisible(true);
		btnSelectUserFeaturesMatrix.setVisible(true);
	}
}
