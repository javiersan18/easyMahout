package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.orbutil.graph.NodeData;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.Constants.Log;
import easyMahout.utils.help.ClassifierTips;

public class AlgorithmClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox algorithmComboBox;

	private DefaultComboBoxModel algorithmModel;

	private String algorithm;

	private JButton btnHelp;
	
	private HelpTooltip helpTooltip;

	@SuppressWarnings("unchecked")
	public AlgorithmClassifierPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Training Algorithm", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelAlgorithm = new JLabel("Select a training algorithm:");
		labelAlgorithm.setBounds(38, 45, 216, 20);
		add(labelAlgorithm);

		algorithmComboBox = new JComboBox<Object>();
		algorithmModel = new DefaultComboBoxModel<Object>(new String[] {
				Constants.ClassificatorAlg.NAIVEBAYES,
				Constants.ClassificatorAlg.COMPNAIVEBAYES});
				//Constants.ClassificatorAlg.SGD });
		algorithmComboBox.setModel(algorithmModel);
		algorithmComboBox.setBackground(Color.WHITE);
		algorithmComboBox.setBounds(250, 45, 220, 20);

		algorithmComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				algorithm = (String) algorithmComboBox.getSelectedItem();

				if (algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES)
						|| algorithm
								.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES)) {
					bayesEnabled();
					SGDDisabled();
				}
				if (algorithm.equals(Constants.ClassificatorAlg.SGD)) {
					MainGUI.writeResult("SGD doesn't works, still in development", Log.ERROR);
					SGDEnabled();
					bayesDisabled();
				}

			}
		});

		add(algorithmComboBox);
		
//help
		
		btnHelp = new JButton(new ImageIcon(AlgorithmClassifierPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClassifierTips.CLASSIFIER_ALGORITHM);
		add(helpTooltip);
	}

	public static String getSelectedType() {
		return (String) algorithmComboBox.getSelectedItem();
	}

	private void bayesEnabled() {
		MainClassifierPanel.getNodeTestData().setEnabled(true);
	}

	private void bayesDisabled() {
		MainClassifierPanel.getNodeTestData().setEnabled(false);
	}

	private void SGDEnabled() {
		MainClassifierPanel.getNodeDataDefs().setEnabled(true);
		MainClassifierPanel.getNodeTrainingData().setEnabled(true);
	}

	private void SGDDisabled() {
		MainClassifierPanel.getNodeDataDefs().setEnabled(false);
		MainClassifierPanel.getNodeTrainingData().setEnabled(false);
	}
}
