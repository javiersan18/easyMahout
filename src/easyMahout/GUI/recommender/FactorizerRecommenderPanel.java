package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import easyMahout.GUI.AboutUsPopupDialogBox;
import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.inputDialogs.SVDFactorizerInputDialog;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class FactorizerRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private static JComboBox comboBoxFactorizer;

	private HelpTooltip helpTooltip;
	
	private SVDFactorizerInputDialog inputDialog;

	private final static Logger log = Logger.getLogger(FactorizerRecommenderPanel.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FactorizerRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Rating Matrix Factorization ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
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

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_NEIGHBORHOOD);
		add(helpTooltip);
		
		JButton btnConfigure = new JButton("Configure");
		btnConfigure.setBounds(335, 35, 89, 23);
		add(btnConfigure);
		btnConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputDialog==null){
					inputDialog = new SVDFactorizerInputDialog();
				}
				else inputDialog.setVisible(true);
			}
		});

//		comboBoxFactorizer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String neighborhood = (String) ((JComboBox) e.getSource()).getSelectedItem();
//				if (neighborhood.equals(Constants.Neighborhood.NEAREST)) {
//					lblNoIterations.setEnabled(true);
//					tfNoIterations.setEnabled(true);
//					tfNoFeatures.setVisible(true);
//					lblNoFeatures.setText("Neighborhood size");
//					tfThreshold.setVisible(false);
//				} else {
//					lblNoIterations.setEnabled(false);
//					tfNoIterations.setEnabled(false);
//					tfNoFeatures.setVisible(false);
//					lblNoFeatures.setText("Threshold");
//					tfThreshold.setVisible(true);
//				}
//			}
//		});

	}

//	public static UserNeighborhood getNeighborhood(UserSimilarity similarity, DataModel model) {
//		String selected = (String) comboBoxFactorizer.getSelectedItem();
//		if (selected.equals(Constants.Neighborhood.NEAREST)) {
//			int size = Integer.parseInt(tfNoFeatures.getText());
//			double minSim;
//			if (StringUtils.isBlank(tfNoIterations.getText())) {
//				minSim = Double.NEGATIVE_INFINITY;
//			} else {
//				minSim = Double.parseDouble(tfNoIterations.getText());
//			}
//			double sampling = Double.parseDouble(tfSamplingRate.getText());
//			try {
//				return new NearestNUserNeighborhood(size, minSim, similarity, model, sampling);
//			} catch (TasteException e) {
//				// TODO Auto-generated catch block
//				log.error("error creating UserNeighborhood");
//				e.printStackTrace();
//				return null;
//			}
//		} else {
//			double threshold = Double.parseDouble(tfThreshold.getText());
//			double sampling = Double.parseDouble(tfSamplingRate.getText());
//			return new ThresholdUserNeighborhood(threshold, similarity, model, sampling);
//		}
//	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static String getSelectedFuction() {
		return (String) comboBoxFactorizer.getSelectedItem();
	}

//	public static String getNeighborhoodSize() {
//		if (StringUtils.isNotBlank(tfNoFeatures.getText())) {
//			return tfNoFeatures.getText();
//		} else {
//			return " ";
//		}
//	}
//
//	public static String getMinSimilarities() {
//		if (StringUtils.isNotBlank(tfNoIterations.getText())) {
//			return tfNoIterations.getText();
//		} else {
//			return " ";
//		}
//	}

//	public static String getThreshold() {
//		if (StringUtils.isNotBlank(tfThreshold.getText())) {
//			return tfThreshold.getText();
//		} else {
//			return " ";
//		}
//	}
//
//	public static String getSampling() {
//		if (StringUtils.isNotBlank(tfSamplingRate.getText())) {
//			return tfSamplingRate.getText();
//		} else {
//			return " ";
//		}
//	}

	public static void setSelectedFuction(String function) {
		comboBoxFactorizer.setSelectedItem(function);
	}

//	public static void setSize(String size) {
//		tfNoFeatures.setText(size);
//	}
//
//	public static void setMinSimilarities(String minsim) {
//		tfNoIterations.setText(minsim);
//	}
//
//	public static void setThreshold(String threshold) {
//		tfNoFeatures.setText(threshold);
//	}
}
