package easyMahout.GUI.classification;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;

public class TrainingDataClassificationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox examplesTrainingComboBox;
	
	private DefaultComboBoxModel numExModel;
	
	private JSpinner numCharSpinner;
	
	private SpinnerModel numCharModel;

	public TrainingDataClassificationPanel	() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
		
		JLabel labelNumEx = new JLabel("Number of training examples:");
		labelNumEx.setBounds(21, 11, 216, 14);
		add(labelNumEx);
		
		examplesTrainingComboBox = new JComboBox();
		numExModel = new DefaultComboBoxModel(new String[] {" Unknown", " N < 10K", " 10K < N <100K",
				" 100K < N < 1M", " 1M < N < 10M", " 10M < N < 100M", " 100M < N"});
		examplesTrainingComboBox.setModel(numExModel);
		examplesTrainingComboBox.setBounds(190, 9, 100, 20);
		add(examplesTrainingComboBox);
		
		JLabel labelNumChar = new JLabel("Number of characteristics by example:");
		labelNumChar.setBounds(21, 41, 216, 14);
		add(labelNumChar);
		
		numCharSpinner = new JSpinner();
		numCharModel = new SpinnerNumberModel(2, 2, 20, 1);
		numCharSpinner.setModel(numCharModel);
		numCharSpinner.setBounds(240, 39, 50, 20);
		add(numCharSpinner);
	}
	
}

