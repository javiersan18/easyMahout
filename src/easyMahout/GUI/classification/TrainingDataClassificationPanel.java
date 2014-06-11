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
import javax.swing.border.TitledBorder;

import easyMahout.utils.Constants;

public class TrainingDataClassificationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner passesSpinner;
	private JSpinner lambdaSpinner;
	private JSpinner rateSpinner;
	private JSpinner featuresSpinner;
	
	//private JComboBox examplesTrainingComboBox;
	
	//private DefaultComboBoxModel numExModel;

	public TrainingDataClassificationPanel	() {
		super();setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Training data", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
		
		/*JLabel labelNumEx = new JLabel("Number of training examples:");
		labelNumEx.setBounds(21, 11, 216, 14);
		add(labelNumEx);
		
		examplesTrainingComboBox = new JComboBox();
		numExModel = new DefaultComboBoxModel(new String[] {" Unknown", " N < 10K", " 10K < N <100K",
				" 100K < N < 1M", " 1M < N < 10M", " 10M < N < 100M", " 100M < N"});
		examplesTrainingComboBox.setModel(numExModel);
		examplesTrainingComboBox.setBounds(190, 9, 100, 20);
		add(examplesTrainingComboBox);*/
		
		JLabel labelPasses = new JLabel("Number of passes to reexamine the input:");
		labelPasses.setBounds(21, 21, 216, 14);
		add(labelPasses);
		
		passesSpinner = new JSpinner();
		SpinnerModel passesModel = new SpinnerNumberModel(1,1,100,1);
		passesSpinner.setModel(passesModel);
		passesSpinner.setBounds(340, 19, 40, 20);
		add(passesSpinner);
		
		JLabel labelLambda = new JLabel("Lambda value:");
		labelLambda.setBounds(21, 50, 216, 14);
		add(labelLambda);
		
		lambdaSpinner = new JSpinner();
		SpinnerModel lambdaModel = new SpinnerNumberModel(0,0,1,0.1);
		lambdaSpinner.setModel(lambdaModel);
		lambdaSpinner.setBounds(340, 48, 40, 20);
		add(lambdaSpinner);
		
		JLabel labelRate = new JLabel("Rate value:");
		labelRate.setBounds(21, 80, 216, 14);
		add(labelRate);
		
		rateSpinner = new JSpinner();
		SpinnerModel rateModel = new SpinnerNumberModel(0,0,1,0.1);
		rateSpinner.setModel(rateModel);
		rateSpinner.setBounds(340, 78, 40, 20);
		add(rateSpinner);
		
		JLabel labelFeatures = new JLabel("Number of features of the vector used to build the model:");
		labelFeatures.setBounds(21, 110, 290, 14);
		add(labelFeatures);
		
		featuresSpinner = new JSpinner();
		SpinnerModel featuresModel = new SpinnerNumberModel(1,1,20,1);
		featuresSpinner.setModel(featuresModel);
		featuresSpinner.setBounds(340, 108, 40, 20);
		add(featuresSpinner);		
	}
	
	public int getNumFeatures(){
		return (int) featuresSpinner.getValue();
	}

	public int getPasses() {
		return (int) passesSpinner.getValue();
	}
}

