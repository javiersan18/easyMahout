package easyMahout.GUI.classification;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;

public class AlgorithmClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox algorithmComboBox;
	
	private DefaultComboBoxModel algorithmModel;

	public AlgorithmClassifierPanel() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
		
		JLabel labelAlgorithm = new JLabel("Select a training algorithm:");
		labelAlgorithm.setBounds(21, 11, 216, 14);
		add(labelAlgorithm);
		
		algorithmComboBox = new JComboBox();
		algorithmModel = new DefaultComboBoxModel(new String[] {Constants.ClassificatorAlg.SGD, 
				Constants.ClassificatorAlg.SVM, Constants.ClassificatorAlg.NAIVEBAYES,
				Constants.ClassificatorAlg.COMPNAIVEBAYES, Constants.ClassificatorAlg.RAMDOMFOREST});
		algorithmComboBox.setModel(algorithmModel);
		algorithmComboBox.setBounds(160, 9, 170, 20);
		add(algorithmComboBox);

	}

	public String getSelectedType() {
		// TODO Auto-generated method stub
		return (String) algorithmComboBox.getSelectedItem();
	}
}
