package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import easyMahout.utils.Constants;

public class DataDefinitionsClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField textTarget;

	private JComboBox categoriesComboBox;

	private DefaultComboBoxModel categoriesModel;

	private JSpinner categoriesSpinner;

	private JSpinner numPredictorsSpinner;

	private int numPredictors;
	
	private JLabel[] labelPredictor;

	private JTextField[] textPredictor;

	private JComboBox[] typesComboBox;
	
	private DefaultComboBoxModel typesModel;
	
	private int prevNumPredictors;

	private final int maxPredictors = 10;
	
	@SuppressWarnings("unchecked")
	public DataDefinitionsClassifierPanel() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
		
		JLabel labelTarget = new JLabel("Name of the target variable:");
		labelTarget.setBounds(21, 11, 216, 14);
		add(labelTarget);
		
		textTarget = new JTextField();
		textTarget.setHorizontalAlignment(SwingConstants.CENTER);
		textTarget.setBounds(240, 9, 100, 20);
		add(textTarget);
		textTarget.setColumns(1);
		textTarget.setText("");
		textTarget.setEnabled(true);
		
		JLabel labelCategories = new JLabel("Number of categories of the target variable:");
		labelCategories.setBounds(21, 40, 216, 14);
		add(labelCategories);
		
		categoriesSpinner = new JSpinner();
		SpinnerModel categoriesModel = new SpinnerNumberModel(1,1,100,1);
		categoriesSpinner.setModel(categoriesModel);
		categoriesSpinner.setBounds(240, 38, 40, 20);
		add(categoriesSpinner);
		
		//NUMPREDICTORS
		
		JLabel labelNumPredictors = new JLabel("Number of predictors variables:");
		labelNumPredictors.setBounds(21, 70, 216, 14);
		add(labelNumPredictors);
		
		numPredictorsSpinner = new JSpinner();
		SpinnerModel numPredictorsmodel = new SpinnerNumberModel(1,1,maxPredictors ,1);
		numPredictorsSpinner.setModel(numPredictorsmodel);
		numPredictorsSpinner.setBounds(240, 68, 40, 20);
		add(numPredictorsSpinner);
		
		//PREDICTORS
		
		numPredictors = 1;
		
		labelPredictor = new JLabel[maxPredictors];
		textPredictor = new JTextField[maxPredictors];
		typesComboBox = new JComboBox[maxPredictors];
		
		typesModel = new DefaultComboBoxModel(new String[] {Constants.ClassificatorPredictorTypes.NUMERIC, 
				Constants.ClassificatorPredictorTypes.WORD, Constants.ClassificatorPredictorTypes.TEXT});
		
		for(int i = 0; i < maxPredictors; i++){
			
			int posY = 100 + (30*i); 
			
			labelPredictor[i] = new JLabel("Name of the predictor variable '" + i + "':");
			labelPredictor[i].setBounds(21, posY, 216, 14);
			labelPredictor[i].setEnabled(true);
			labelPredictor[i].setVisible(false);
			add(labelPredictor[i]);
			
			textPredictor[i] = new JTextField();
			textPredictor[i].setHorizontalAlignment(SwingConstants.CENTER);
			textPredictor[i].setBounds(240, posY-2, 100, 20);		
			textPredictor[i].setColumns(1);
			textPredictor[i].setText("");
			textPredictor[i].setEnabled(true);
			textPredictor[i].setVisible(false);
			add(textPredictor[i]);		
			
			typesComboBox[i] = new JComboBox();
			typesComboBox[i].setModel(typesModel);
			typesComboBox[i].setBounds(360, posY-2, 80, 20);
			typesComboBox[i].setEnabled(true);
			typesComboBox[i].setVisible(false);
			add(typesComboBox[i]);
		}
		
		labelPredictor[0].setVisible(true);
		textPredictor[0].setVisible(true);
		typesComboBox[0].setVisible(true);
		
		numPredictorsSpinner.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) {
				// handle click
				prevNumPredictors = numPredictors;
				numPredictors = (int) numPredictorsSpinner.getValue();
					
				if(prevNumPredictors > numPredictors){
						
					labelPredictor[numPredictors].setVisible(false);
					textPredictor[numPredictors].setVisible(false);
					typesComboBox[numPredictors].setVisible(false);
												
				} else {
						
					labelPredictor[numPredictors-1].setVisible(true);
					textPredictor[numPredictors-1].setVisible(true);
					typesComboBox[numPredictors-1].setVisible(true);
						
				}
			}
		});
	}
	
	public String getTextTarget() {
		return textTarget.getText();
	}
	
	public int getTargetCategories(){
		return (int) categoriesComboBox.getSelectedItem();
	}
}
