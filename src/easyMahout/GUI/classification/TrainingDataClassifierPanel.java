package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TrainingDataClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner passesSpinner;
	private JSpinner lambdaSpinner;
	private JSpinner rateSpinner;
	private JSpinner featuresSpinner;
	private JButton btnHelp;

	public TrainingDataClassifierPanel	() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Training data", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
		
		JLabel labelPasses = new JLabel("Number of passes to reexamine the input:");
		labelPasses.setBounds(15, 21, 300, 14);
		add(labelPasses);
		
		passesSpinner = new JSpinner();
		SpinnerModel passesModel = new SpinnerNumberModel(1,1,100,1);
		passesSpinner.setModel(passesModel);
		passesSpinner.setBounds(430, 19, 40, 20);
		add(passesSpinner);
		
		JLabel labelLambda = new JLabel("Lambda value:");
		labelLambda.setBounds(15, 50, 216, 14);
		add(labelLambda);
		
		lambdaSpinner = new JSpinner();
		SpinnerModel lambdaModel = new SpinnerNumberModel(0,0,1,0.1);
		lambdaSpinner.setModel(lambdaModel);
		lambdaSpinner.setBounds(430, 48, 40, 20);
		add(lambdaSpinner);
		
		JLabel labelRate = new JLabel("Rate value:");
		labelRate.setBounds(15, 80, 216, 14);
		add(labelRate);
		
		rateSpinner = new JSpinner();
		SpinnerModel rateModel = new SpinnerNumberModel(0,0,1,0.1);
		rateSpinner.setModel(rateModel);
		rateSpinner.setBounds(430, 78, 40, 20);
		add(rateSpinner);
		
		JLabel labelFeatures = new JLabel("Number of features of the vector used to build the model:");
		labelFeatures.setBounds(15, 110, 420, 14);
		add(labelFeatures);
		
		featuresSpinner = new JSpinner();
		SpinnerModel featuresModel = new SpinnerNumberModel(1,1,20,1);
		featuresSpinner.setModel(featuresModel);
		featuresSpinner.setBounds(430, 108, 40, 20);
		add(featuresSpinner);	
		
		//help
		
		btnHelp = new JButton(new ImageIcon(TrainingDataClassifierPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);
	}
	
	public int getNumFeatures(){
		return (int) featuresSpinner.getValue();
	}

	public int getPasses() {
		return (int) passesSpinner.getValue();
	}
}

