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
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClassifierTips;

public class TestDataClassifierPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private JButton btnHelp;

	private HelpTooltip helpTooltip;

	private JComboBox<Object> SelectionComboBox;

	private DefaultComboBoxModel<Object> SelectionModel;

	private JComboBox<Object> TypeRandomComboBox;

	private DefaultComboBoxModel<Object> TypeRandomModel;

	private JComboBox<Object> TypeDefinedComboBox;

	private DefaultComboBoxModel<Object> TypeDefinedModel;

	private static JSpinner percentSpinner;
	
	private static boolean random = true;
	
	private static boolean percent = true;
	
	private static boolean location = false;

	private static JSpinner sizeSpinner;

	private final static Logger log = Logger.getLogger(DataModelClassifierPanel.class);
	
	public TestDataClassifierPanel	() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Test data", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

	
		JLabel labelSelection = new JLabel("Choose one method of selection:");
		labelSelection.setBounds(15, 21, 380, 14);
		add(labelSelection);
		
		SelectionComboBox = new JComboBox<Object>();
		SelectionModel = new DefaultComboBoxModel<Object>(new String[] {
				"Random",
				"Defined" });
		SelectionComboBox.setModel(SelectionModel);
		SelectionComboBox.setBounds(350, 19, 100, 20);
		SelectionComboBox.setBackground(Color.WHITE);
		add(SelectionComboBox);
		
		SelectionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SelectionComboBox.getSelectedItem().equals("Random")){
					random = true;
					location = false;
					TypeRandomComboBox.setVisible(true);
					TypeDefinedComboBox.setVisible(false);
				} else {
					random = false;
					location = false;
					TypeRandomComboBox.setVisible(false);
					TypeDefinedComboBox.setSelectedIndex(0);
					TypeDefinedComboBox.setVisible(true);
				}
			}
		});
		
		JLabel labelType = new JLabel("Choose one type of selection:");
		labelType.setBounds(15, 51, 250, 14);
		add(labelType);
		
		TypeRandomComboBox = new JComboBox<Object>();
		TypeRandomModel = new DefaultComboBoxModel<Object>(new String[] {
				"Percent",
				"Size" });
		TypeRandomComboBox.setModel(TypeRandomModel);
		TypeRandomComboBox.setBounds(260, 49, 190, 20);
		TypeRandomComboBox.setBackground(Color.WHITE);
		add(TypeRandomComboBox);
		
		TypeRandomComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TypeRandomComboBox.getSelectedItem().equals("Percent")){
					percent = true;
					percentSpinner.setEnabled(true);
					sizeSpinner.setEnabled(false);
				} else {
					percent = false;
					percentSpinner.setEnabled(false);
					sizeSpinner.setEnabled(true);
				}
			}
		});
		
		TypeDefinedComboBox = new JComboBox<Object>();
		TypeDefinedModel = new DefaultComboBoxModel<Object>(new String[] {
				"Percent by category",
				"Size by category",
				"From a percentage"});
		TypeDefinedComboBox.setModel(TypeDefinedModel);
		TypeDefinedComboBox.setBounds(260, 49, 190, 20);
		TypeDefinedComboBox.setBackground(Color.WHITE);
		add(TypeDefinedComboBox);
		
		TypeDefinedComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TypeDefinedComboBox.getSelectedItem().equals("Percent by category")){
					percent = true;
					location = false;
					percentSpinner.setEnabled(true);
					sizeSpinner.setEnabled(false);
				} else if(TypeDefinedComboBox.getSelectedItem().equals("Size by category")) {
					percent = false;
					location = false;
					percentSpinner.setEnabled(false);
					sizeSpinner.setEnabled(true);
				} else {
					percent = false;
					location = true;
					percentSpinner.setEnabled(true);
					sizeSpinner.setEnabled(false);
				}
			}
		});
		
		JLabel labelPercent = new JLabel("Select the percent of input data to be used as test:");
		labelPercent.setBounds(15, 80, 380, 14);
		add(labelPercent);
		
		percentSpinner = new JSpinner();
		SpinnerModel percentModel = new SpinnerNumberModel(25,1,100,1);
		percentSpinner.setModel(percentModel);
		percentSpinner.setBounds(390, 78, 60, 20);
		add(percentSpinner);
		
		JLabel labelSize = new JLabel("Select the size of input data to be used as test:");
		labelSize.setBounds(15, 110, 380, 14);
		add(labelSize);
		
		sizeSpinner = new JSpinner();
		SpinnerModel sizeModel = new SpinnerNumberModel(10,10,10000,10);
		sizeSpinner.setModel(sizeModel);
		sizeSpinner.setBounds(390, 108, 60, 20);
		sizeSpinner.setEnabled(false);
		add(sizeSpinner);
		
		
//Help
		
		btnHelp = new JButton(new ImageIcon(TestDataClassifierPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClassifierTips.CLASSIFIER_TESTDATA);
		add(helpTooltip);
		
	}

	public static String getPct() {
		return (String)  Integer.toString((Integer)percentSpinner.getValue());
	}
	
	public static boolean getRandom(){
		return random;
	}

	public static String getSizeSelection() {
		return (String)  Integer.toString((Integer)sizeSpinner.getValue());
	}

	public static boolean isPct() {
		return percent;
	}
	
	public static boolean getLocationSplit() {
		return location;
	}

}
