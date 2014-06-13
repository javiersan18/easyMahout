package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.orbutil.graph.NodeData;

import easyMahout.utils.Constants;

public class AlgorithmClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JComboBox algorithmComboBox;

	private DefaultComboBoxModel algorithmModel;

	private String algorithm;

	@SuppressWarnings("unchecked")
	public AlgorithmClassifierPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Training Algorithm", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelAlgorithm = new JLabel("Select a training algorithm");
		labelAlgorithm.setBounds(38, 45, 216, 20);
		add(labelAlgorithm);

		algorithmComboBox = new JComboBox<Object>();
		algorithmModel = new DefaultComboBoxModel<Object>(new String[] {
				Constants.ClassificatorAlg.NAIVEBAYES,
				Constants.ClassificatorAlg.COMPNAIVEBAYES });
		algorithmComboBox.setModel(algorithmModel);
		algorithmComboBox.setBounds(250, 45, 190, 20);

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
					SGDEnabled();
					bayesDisabled();
				}

			}
		});

		add(algorithmComboBox);
	}

	public static String getSelectedType() {
		return (String) algorithmComboBox.getSelectedItem();
	}

	private void bayesEnabled() {
	}

	private void bayesDisabled() {
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
