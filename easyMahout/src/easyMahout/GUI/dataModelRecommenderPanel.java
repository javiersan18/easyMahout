package easyMahout.GUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

public class dataModelRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	DefaultComboBoxModel booleanModels, restModels;
	
	JComboBox comboBoxDatamodel;
	
	private final static Logger log = Logger.getLogger(dataModelRecommenderPanel.class);
	
	

	public dataModelRecommenderPanel() {
		super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel lblSelectTypeOf = new JLabel("Select type of Data Model:");
		lblSelectTypeOf.setBounds(10, 11, 216, 14);
		add(lblSelectTypeOf);

//		JComboBox comboBoxType = new JComboBox();
//		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based", "Clustering" }));
//		comboBoxType.setBounds(38, 86, 141, 20);
//		add(comboBoxType);

		comboBoxDatamodel = new JComboBox();
		booleanModels = new DefaultComboBoxModel(new String[] { "GenericBooleanPrefDataModel" });
		restModels = new DefaultComboBoxModel(new String[] { "FileDataModel", "GenericDataModel", "ExtendedDataModel",
				"CassandraDataModel", "HBaseDataModel", "KDDCupDataModel", "MongoDBDataModel", "PlusAnonymousConcurrentUserDataModel" });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(38, 86, 141, 20);
		add(comboBoxDatamodel);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(462, 357, 11, 14);
		add(lblId);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Boolean Preferences ");
		chckbxNewCheckBox.setToolTipText("(explenation.................................................................................)");
		chckbxNewCheckBox.setBounds(38, 50, 141, 29);
		add(chckbxNewCheckBox);
		chckbxNewCheckBox.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox)e.getSource()).isSelected()){
					comboBoxDatamodel.setModel(booleanModels);
					log.info("boolean");
				}
				else{
					comboBoxDatamodel.setModel(restModels);
					log.info("rest");
				}
				
			}
		});

	}
	
	
	
	
}
