package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import easyMahout.GUI.MainGUI;
import easyMahout.recommender.ExtendedDataModel;
import easyMahout.utils.Constants;

public class DataModelRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel booleanModels, restModels;

	private JComboBox comboBoxDatamodel;

	private final static Logger log = Logger.getLogger(DataModelRecommenderPanel.class);

	private JTextField textPath;

	private DataModel dataModel;

	public DataModelRecommenderPanel() {
		//super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectModel = new JLabel("Select type of Data Model:");
		labelSelectModel.setBounds(21, 11, 216, 14);
		add(labelSelectModel);

		comboBoxDatamodel = new JComboBox();
		booleanModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.GENERIC_BOOLEAN });
		restModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.FILE, Constants.DataModel.GENERIC,
				Constants.DataModel.EXTENDED, Constants.DataModel.CASSANDRA, Constants.DataModel.HBASE, Constants.DataModel.KDDCUP,
				Constants.DataModel.MONGOL_DB, Constants.DataModel.PLUS_ANONYMOUS });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(38, 68, 216, 20);
		add(comboBoxDatamodel);

		JCheckBox chckbxBooleanPreferences = new JCheckBox("Boolean Preferences ");
		chckbxBooleanPreferences.setBounds(38, 38, 199, 23);
		add(chckbxBooleanPreferences);

		JLabel labelDataSource = new JLabel("Data source:");
		labelDataSource.setBounds(21, 106, 89, 14);
		add(labelDataSource);

		textPath = new JTextField();
		textPath.setBounds(38, 131, 313, 20);
		add(textPath);
		textPath.setColumns(10);

		JButton btnImport = new JButton("Import...");
		btnImport.setBounds(361, 130, 89, 23);
		add(btnImport);

		chckbxBooleanPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					comboBoxDatamodel.setModel(booleanModels);
					log.info("boolean");
				} else {
					comboBoxDatamodel.setModel(restModels);
					log.info("rest");
				}

			}
		});

		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(DataModelRecommenderPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File data = selectedFile.getSelectedFile();
					String absPath = data.getAbsolutePath();
					int selected = comboBoxDatamodel.getSelectedIndex();

					try {
						// TODO: distintos tipos de modelos...
						switch (selected) {
							case 0:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
							case 1:
								setDataModel(new GenericDataModel(GenericDataModel.toDataMap(new FileDataModel(new File(absPath)))));
								break;
							case 2:
								setDataModel(new ExtendedDataModel(new File(absPath), ","));
								break;
							case 3:
								// dataModel = new CassandraDataModel(new
								// File(absPath));
								break;
							case 4:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
							case 5:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
							case 6:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
							case 7:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
							default:
								setDataModel(new FileDataModel(new File(absPath)));
								break;
						}
						textPath.setText(absPath);
						MainGUI.writeResult("Data Model successfully created from file", Constants.Log.INFO);
						// TODO: revisar errores de excepciones mostrados en
						// consola
					} catch (IllegalArgumentException e1) {
						MainGUI.writeResult("Error reading data file: " + e1.getMessage(), Constants.Log.ERROR);
						log.error("Error reading data file", e1);
					} catch (Exception e1) {
						MainGUI.writeResult("Error reading data file", Constants.Log.ERROR);
						log.error("Error reading data file", e1);
					}

				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error openig the file", Constants.Log.ERROR);
					log.error("Error opening data file");
				}
			}
		});

	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}
}
