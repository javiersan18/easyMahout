package easyMahout.GUI.recommender;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import easyMahout.GUI.MainGUI;
import easyMahout.recommender.ExtendedDataModel;
import easyMahout.utils.Constants;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class DataModelRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel booleanModels, restModels;

	private JComboBox comboBoxDatamodel;

	private final static Logger log = Logger.getLogger(DataModelRecommenderPanel.class);

	private JTextField textPath, tfDelimiter;

	private JLabel lblDelimiter, lblDataSource;

	private JButton btnSelect;

	private JCheckBox chckbxBooleanPreferences;

	private DataModel dataModel;

	public DataModelRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Data Model", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxDatamodel = new JComboBox();
		booleanModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.GENERIC_BOOLEAN });
		restModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.FILE, Constants.DataModel.GENERIC, Constants.DataModel.EXTENDED,
				Constants.DataModel.CASSANDRA, Constants.DataModel.HBASE, Constants.DataModel.KDDCUP, Constants.DataModel.MONGOL_DB,
				Constants.DataModel.PLUS_ANONYMOUS });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(38, 68, 216, 20);
		add(comboBoxDatamodel);

		chckbxBooleanPreferences = new JCheckBox("Boolean Preferences ");
		chckbxBooleanPreferences.setBounds(38, 27, 199, 23);
		add(chckbxBooleanPreferences);

		lblDataSource = new JLabel("Data source:");
		lblDataSource.setBounds(38, 105, 89, 14);
		add(lblDataSource);

		textPath = new JTextField();
		textPath.setBounds(38, 131, 401, 20);
		add(textPath);
		textPath.setColumns(10);

		btnSelect = new JButton("Select File...");
		btnSelect.setBounds(130, 165, 107, 23);
		add(btnSelect);

		lblDelimiter = new JLabel("Delimiter");
		lblDelimiter.setBounds(274, 71, 68, 14);
		add(lblDelimiter);
		lblDelimiter.setEnabled(false);

		tfDelimiter = new JTextField();
		tfDelimiter.setHorizontalAlignment(SwingConstants.CENTER);
		tfDelimiter.setBounds(329, 68, 46, 20);
		add(tfDelimiter);
		tfDelimiter.setColumns(10);
		tfDelimiter.setText(",");
		tfDelimiter.setEnabled(false);

		JButton btnCreate = new JButton("Create Model");
		btnCreate.setBounds(241, 165, 107, 23);
		add(btnCreate);

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(DataModelRecommenderPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File data = selectedFile.getSelectedFile();
					String absPath = data.getAbsolutePath();
					textPath.setText(absPath);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error openig the file", Constants.Log.ERROR);
					log.error("Error opening data file");
				}
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboBoxDatamodel.getSelectedIndex();
				String filePath = textPath.getText();
				try {
					// TODO: distintos tipos de modelos...
					switch (selected) {
					case 0:
						setDataModel(new FileDataModel(new File(filePath)));
						MainGUI.writeResult("Data Model successfully created from file", Constants.Log.INFO);
						break;
					case 1:
						setDataModel(new GenericDataModel(GenericDataModel.toDataMap(new FileDataModel(new File(filePath)))));
						MainGUI.writeResult("Data Model successfully created from file", Constants.Log.INFO);
						break;
					case 2:
						String delimiter = tfDelimiter.getText();
						if (StringUtils.isBlank(delimiter)) {
							tfDelimiter.setBackground(new Color(240, 128, 128));
							log.error("Delimiter for ExtendedDataModel is empty.");
							MainGUI.writeResult("Delimiter for Extended Data Model is empty.", Constants.Log.ERROR);
						} else {
							tfDelimiter.setBackground(Color.WHITE);
							setDataModel(new ExtendedDataModel(new File(filePath), tfDelimiter.getText()));
							MainGUI.writeResult("Data Model successfully created from file", Constants.Log.INFO);
						}
						break;
					case 3:
						// dataModel = new CassandraDataModel(new
						// File(absPath));
						break;
					case 4:
						setDataModel(new FileDataModel(new File(filePath)));
						break;
					case 5:
						setDataModel(new FileDataModel(new File(filePath)));
						break;
					case 6:
						setDataModel(new FileDataModel(new File(filePath)));
						break;
					case 7:
						setDataModel(new FileDataModel(new File(filePath)));
						break;
					default:
						setDataModel(new FileDataModel(new File(filePath)));
						break;
					}
					// TODO: revisar errores de excepciones mostrados en
					// consola
				} catch (IllegalArgumentException e1) {
					MainGUI.writeResult("Error reading data file: " + e1.getMessage(), Constants.Log.ERROR);
					log.error("Error reading data file", e1);
				} catch (Exception e1) {
					MainGUI.writeResult(e1.getMessage(), Constants.Log.ERROR);
					log.error("Error reading data file", e1);
				}

			}
		});

		chckbxBooleanPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					comboBoxDatamodel.setModel(booleanModels);
					lblDelimiter.setEnabled(false);
					tfDelimiter.setEnabled(false);
					log.info("boolean");
				} else {
					comboBoxDatamodel.setModel(restModels);
					log.info("rest");
				}

			}
		});

		comboBoxDatamodel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String model = (String) ((JComboBox) e.getSource()).getSelectedItem();
				if (model.equals(Constants.DataModel.EXTENDED)) {
					lblDelimiter.setEnabled(true);
					tfDelimiter.setEnabled(true);
				} else {
					lblDelimiter.setEnabled(false);
					tfDelimiter.setEnabled(false);
				}

			}
		});

		// btnSelect.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// JFileChooser selectedFile = new JFileChooser();
		// int i = selectedFile.showOpenDialog(DataModelRecommenderPanel.this);
		// if (i == JFileChooser.APPROVE_OPTION) {
		// File data = selectedFile.getSelectedFile();
		// String absPath = data.getAbsolutePath();
		// int selected = comboBoxDatamodel.getSelectedIndex();
		//
		// try {
		// // TODO: distintos tipos de modelos...
		// switch (selected) {
		// case 0:
		// setDataModel(new FileDataModel(new File(absPath)));
		// textPath.setText(absPath);
		// MainGUI.writeResult("Data Model successfully created from file",
		// Constants.Log.INFO);
		// break;
		// case 1:
		// setDataModel(new GenericDataModel(GenericDataModel.toDataMap(new
		// FileDataModel(new File(absPath)))));
		// textPath.setText(absPath);
		// MainGUI.writeResult("Data Model successfully created from file",
		// Constants.Log.INFO);
		// break;
		// case 2:
		// String delimiter = tfDelimiter.getText();
		// if (StringUtils.isBlank(delimiter)) {
		// tfDelimiter.setBackground(new Color(240, 128, 128));
		// log.error("Delimiter for ExtendedDataModel is empty.");
		// MainGUI.writeResult("Delimiter for Extended Data Model is empty.",
		// Constants.Log.ERROR);
		// } else {
		// tfDelimiter.setBackground(Color.WHITE);
		// setDataModel(new ExtendedDataModel(new File(absPath),
		// tfDelimiter.getText()));
		// textPath.setText(absPath);
		// MainGUI.writeResult("Data Model successfully created from file",
		// Constants.Log.INFO);
		// }
		// break;
		// case 3:
		// // dataModel = new CassandraDataModel(new
		// // File(absPath));
		// break;
		// case 4:
		// setDataModel(new FileDataModel(new File(absPath)));
		// break;
		// case 5:
		// setDataModel(new FileDataModel(new File(absPath)));
		// break;
		// case 6:
		// setDataModel(new FileDataModel(new File(absPath)));
		// break;
		// case 7:
		// setDataModel(new FileDataModel(new File(absPath)));
		// break;
		// default:
		// setDataModel(new FileDataModel(new File(absPath)));
		// break;
		// }
		// // TODO: revisar errores de excepciones mostrados en
		// // consola
		// } catch (IllegalArgumentException e1) {
		// MainGUI.writeResult("Error reading data file: " + e1.getMessage(),
		// Constants.Log.ERROR);
		// log.error("Error reading data file", e1);
		// } catch (Exception e1) {
		// MainGUI.writeResult(e1.getMessage(), Constants.Log.ERROR);
		// log.error("Error reading data file", e1);
		// }
		//
		// } else if (i == JFileChooser.ERROR_OPTION) {
		// MainGUI.writeResult("Error openig the file", Constants.Log.ERROR);
		// log.error("Error opening data file");
		// }
		// }
		// });

	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}
}
