package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import easyMahout.utils.Constants;

public class AlgorithmClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JComboBox algorithmComboBox;
	
	private DefaultComboBoxModel algorithmModel;
	
	private String algorithm;

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
		algorithmModel = new DefaultComboBoxModel(new String[] {"Please select an algorithm", Constants.ClassificatorAlg.NAIVEBAYES,
				Constants.ClassificatorAlg.COMPNAIVEBAYES, Constants.ClassificatorAlg.SGD, 
				Constants.ClassificatorAlg.SVM, Constants.ClassificatorAlg.RAMDOMFOREST});
		algorithmComboBox.setModel(algorithmModel);
		algorithmComboBox.setBounds(160, 9, 170, 20);
		
		algorithmComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				algorithm = (String) algorithmComboBox.getSelectedItem();
				
				if(!algorithm.equals("Please select an algorithm")){				
					if(algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES) ||
							algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES)){
						bayesEnabled();
						SGDDisabled();
					}
					if(algorithm.equals(Constants.ClassificatorAlg.SGD)){
						SGDEnabled();
						bayesDisabled();
					}
				}
			}
        });

		
		add(algorithmComboBox);

	}

	public static String getSelectedType() {
		// TODO Auto-generated method stub
		return (String) algorithmComboBox.getSelectedItem();
	}
	

	private void bayesEnabled(){
	}
	
	private void bayesDisabled() {
		
	}

	private void SGDEnabled() {
		
	}

	private void SGDDisabled() {
		
	}
}
