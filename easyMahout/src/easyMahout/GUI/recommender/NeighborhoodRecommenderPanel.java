package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class NeighborhoodRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxNeighborhood;

	private JTextField tfSize;

	private JTextField tfThreshold;

	private JTextField tfMinimun;

	private JLabel labelMinimun;

	private JLabel labelSize;

	private JLabel labelSamplingRate;

	private JTextField tfSamplingRate;

	private final static Logger log = Logger.getLogger(NeighborhoodRecommenderPanel.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NeighborhoodRecommenderPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectNeighborhood = new JLabel("Select neighborhood user function:");
		labelSelectNeighborhood.setBounds(21, 11, 216, 14);
		add(labelSelectNeighborhood);

		comboBoxNeighborhood = new JComboBox();
		comboBoxNeighborhood.setMaximumRowCount(16);
		comboBoxNeighborhood.setModel(new DefaultComboBoxModel(new String[] { Constants.Neighborhood.NEAREST,
				Constants.Neighborhood.THRESHOLD }));
		comboBoxNeighborhood.setBounds(38, 36, 199, 20);
		add(comboBoxNeighborhood);

		tfSize = new JTextField();
		tfSize.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSize.setBounds(177, 67, 62, 20);
		add(tfSize);
		tfSize.setText("10");
		tfSize.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Integer i = Integer.parseInt(text);
					if (i >= 1 && i <= 9999) {
						tfSize.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("ERROR: Size has to be an integer number in range [1..9999]", Constants.Log.ERROR);
						tfSize.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("ERROR: Size has to be an integer number in range [1..9999]", Constants.Log.ERROR);
					tfSize.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		tfThreshold = new JTextField();
		tfThreshold.setHorizontalAlignment(SwingConstants.RIGHT);
		tfThreshold.setBounds(177, 67, 62, 20);
		add(tfThreshold);
		tfThreshold.setVisible(false);
		tfThreshold.setText("0.7");
		tfThreshold.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double d = Double.parseDouble(text);
					if (d >= -1 && d <= 1) {
						tfThreshold.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("ERROR: Threshold has to be an real number in range [-1..1]", Constants.Log.ERROR);
						tfThreshold.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("ERROR: Threshold has to be an real number in range [-1..1]", Constants.Log.ERROR);
					tfThreshold.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		labelSize = new JLabel("Neighborhood size");
		labelSize.setBounds(38, 70, 137, 14);
		add(labelSize);

		tfMinimun = new JTextField();
		tfMinimun.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMinimun.setBounds(177, 98, 62, 20);
		add(tfMinimun);
		tfMinimun.setColumns(5);

		labelMinimun = new JLabel("Minimun similarity");
		labelMinimun.setBounds(38, 101, 115, 14);
		add(labelMinimun);

		labelSamplingRate = new JLabel("Sampling rate");
		labelSamplingRate.setBounds(38, 132, 98, 14);
		add(labelSamplingRate);

		tfSamplingRate = new JTextField();
		tfSamplingRate.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSamplingRate.setBounds(206, 129, 33, 20);
		add(tfSamplingRate);
		tfSamplingRate.setColumns(5);
		tfSamplingRate.setText("1.0");

		tfSamplingRate.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double d = Double.parseDouble(text);
					if (d > 0 && d <= 1.0) {
						tfSamplingRate.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("ERROR: Sampling Rate has to be a real number in range (0,1]", Constants.Log.ERROR);
						tfSamplingRate.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("ERROR: Sampling Rate has to be a real number in range (0,1]", Constants.Log.ERROR);
					tfSamplingRate.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		comboBoxNeighborhood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String neighborhood = (String) ((JComboBox) e.getSource()).getSelectedItem();
				if (neighborhood.equals(Constants.Neighborhood.NEAREST)) {
					labelMinimun.setEnabled(true);
					tfMinimun.setEnabled(true);
					tfSize.setVisible(true);
					labelSize.setText("Neighborhood size");
					tfThreshold.setVisible(false);
				} else {
					labelMinimun.setEnabled(false);
					tfMinimun.setEnabled(false);
					tfSize.setVisible(false);
					labelSize.setText("Threshold");
					tfThreshold.setVisible(true);
				}
			}
		});

	}

	public UserNeighborhood getNeighborhood(UserSimilarity similarity, DataModel model) {
		String selected = (String) comboBoxNeighborhood.getSelectedItem();
		if (selected.equals(Constants.Neighborhood.NEAREST)) {
			int size = Integer.parseInt(tfSize.getText());
			double minSim;
			if (StringUtils.isBlank(tfMinimun.getText())) {
				minSim = Double.NEGATIVE_INFINITY;
			} else {
				minSim = Double.parseDouble(tfMinimun.getText());
			}
			double sampling = Double.parseDouble(tfSamplingRate.getText());
			try {
				return new NearestNUserNeighborhood(size, minSim, similarity, model, sampling);
			} catch (TasteException e) {
				// TODO Auto-generated catch block
				log.error("error creating UserNeighborhood");
				e.printStackTrace();
				return null;
			}
		} else {
			double threshold = Double.parseDouble(tfThreshold.getText());
			double sampling = Double.parseDouble(tfSamplingRate.getText());
			return new ThresholdUserNeighborhood(threshold, similarity, model, sampling);
		}
	}


}
