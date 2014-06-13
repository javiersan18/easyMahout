package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.model.DataModel;

import com.jidesoft.swing.FolderChooser;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.builder.ClassifierBuilder;
import easyMahout.GUI.recommender.EvaluatorRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class TestDataClassifierPanel extends JPanel{

	private static JTextField textInputPath;

	private static JTextField tfDelimiter;

	private static JTextField textOutputPath;

	private JLabel lblInputDataSource;

	private JLabel lblOutputDataSource;

	private JButton btnSelectInput;

	private JButton btnSelectOutput;

	private static JButton btnCreate;

	private JButton btnHelp;

	private HelpTooltip helpTooltip;

	private JLabel lblModelDataSource;

	private JTextField textModelPath;

	private JButton btnSelectModel;

	private JLabel lblLabelIndexSource;

	private JTextField textLabelIndexPath;

	private JButton btnSelectLabelIndex;

	private JComboBox<Object> SelectionComboBox;

	private DefaultComboBoxModel<Object> SelectionModel;

	private JSpinner percentSpinner;

	private final static Logger log = Logger.getLogger(DataModelClassifierPanel.class);
	
	public TestDataClassifierPanel	() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Test data", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

	
		JLabel labelPasses = new JLabel("Choose one type of selection for the test data:");
		labelPasses.setBounds(15, 21, 380, 14);
		add(labelPasses);
		
		SelectionComboBox = new JComboBox<Object>();
		SelectionModel = new DefaultComboBoxModel<Object>(new String[] {
				"Percent",
				"Size" });
		SelectionComboBox.setModel(SelectionModel);
		SelectionComboBox.setBounds(150, 51, 190, 20);
		SelectionComboBox.setBackground(Color.WHITE);
		add(SelectionComboBox);
		
		SelectionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SelectionComboBox.getSelectedItem().equals("Percent")){
					percentSpinner.setEnabled(true);
				} else {
					percentSpinner.setEnabled(false);
				}
			}
		});
		
		JLabel labelPercent = new JLabel("Select the percent of input data to be used as test:");
		labelPercent.setBounds(15, 90, 380, 14);
		add(labelPercent);
		
		percentSpinner = new JSpinner();
		SpinnerModel percentModel = new SpinnerNumberModel(0.1,0.01,1,0.01);
		percentSpinner.setModel(percentModel);
		percentSpinner.setBounds(400, 88, 50, 20);
		add(percentSpinner);		
		
	}

}
