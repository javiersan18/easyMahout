package easyMahout.GUI;

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

import org.apache.log4j.Logger;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.cassandra.CassandraDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import easyMahout.recommender.ExtendedDataModel;
import MahoutInAction.Recommender.Recommender22;

public class CopyOfrecommenderJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelRecommender;

	private JLabel labelType, labelDatamodel, labelSimilarity, labelDataSource, labelEvaluator, labelNeighborhood;

	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxType, comboBoxDatamodel, comboBoxNeighborhood, comboBoxSimilarity;

	private JButton btnRun, btnImport;

	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel booleanModels, restModels;

	private JTextField textPath, usersNeigborhood;

	private JCheckBox chckbxBooleanPreference;

	private final static Logger log = Logger.getLogger(CopyOfrecommenderJPanel.class);

	// Recommender atributes
	private Recommender recomm;

	@SuppressWarnings("unused")
	private DataModel dataModel;

	private Similarity similarityMetric;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CopyOfrecommenderJPanel() {
		panelRecommender = new JPanel();
		panelRecommender.setLayout(null);

		labelType = new JLabel("Type");
		labelType.setBounds(12, 12, 27, 16);
		panelRecommender.add(labelType);

		comboBoxType = new JComboBox();
		comboBoxType.setBounds(125, 8, 144, 25);
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based", "Clustering" }));
		panelRecommender.add(comboBoxType);

		btnRun = new JButton("Run!");
		btnRun.setBounds(599, 387, 98, 26);
		panelRecommender.add(btnRun);

		labelDatamodel = new JLabel("Data Model");
		labelDatamodel.setBounds(460, 13, 63, 14);
		panelRecommender.add(labelDatamodel);

		comboBoxDatamodel = new JComboBox();
		booleanModels = new DefaultComboBoxModel(new String[] { "GenericBooleanPrefDataModel" });
		restModels = new DefaultComboBoxModel(new String[] { "FileDataModel", "GenericDataModel", "ExtendedDataModel",
				"CassandraDataModel", "HBaseDataModel", "KDDCupDataModel", "MongoDBDataModel", "PlusAnonymousConcurrentUserDataModel" });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(541, 8, 156, 25);
		panelRecommender.add(comboBoxDatamodel);

		// Similarity Metric (Label and comboBox)
		labelSimilarity = new JLabel("Similarity Metric");
		labelSimilarity.setBounds(12, 87, 92, 16);
		panelRecommender.add(labelSimilarity);

		comboBoxSimilarity = new JComboBox();
		comboBoxSimilarity.setModel(new DefaultComboBoxModel(new String[] { "Abstract", "Average", "Caching", "CityBlock", "Euclidean",
				"Generic", "Likelihood", "Pearson Correlation", "Spearman Correlation", "Tanimoto Coefficient", "Uncenter Cosine" }));
		comboBoxSimilarity.setBounds(125, 83, 144, 25);
		panelRecommender.add(comboBoxSimilarity);

		labelDataSource = new JLabel("Data Source");
		labelDataSource.setBounds(12, 49, 70, 16);
		panelRecommender.add(labelDataSource);

		textPath = new JTextField();
		textPath.setBounds(125, 45, 462, 25);
		panelRecommender.add(textPath);
		textPath.setColumns(10);

		labelEvaluator = new JLabel("Evaluator");
		labelEvaluator.setBounds(12, 167, 46, 14);
		panelRecommender.add(labelEvaluator);

		labelNeighborhood = new JLabel("Neighborhood");
		labelNeighborhood.setBounds(10, 128, 72, 14);
		panelRecommender.add(labelNeighborhood);

		comboBoxNeighborhood = new JComboBox();
		comboBoxNeighborhood.setBounds(125, 125, 144, 20);
		comboBoxNeighborhood.setModel(new DefaultComboBoxModel(new String[] { "Nearest N", "Threshold" }));
		panelRecommender.add(comboBoxNeighborhood);

		usersNeigborhood = new JTextField();
		usersNeigborhood.setBounds(345, 125, 86, 20);
		panelRecommender.add(usersNeigborhood);
		usersNeigborhood.setColumns(10);
		usersNeigborhood.setText("100");

		JLabel lblUsers = new JLabel("Users");
		lblUsers.setBounds(293, 128, 46, 14);
		panelRecommender.add(lblUsers);

		btnImport = new JButton("Import...");

		btnImport.setBounds(599, 44, 98, 26);
		panelRecommender.add(btnImport);

		chckbxBooleanPreference = new JCheckBox("Boolean Preference");
		chckbxBooleanPreference.setBounds(293, 8, 138, 24);
		panelRecommender.add(chckbxBooleanPreference);

		// checkBooleanPreferenceCheckbox(comboBox_type, comboBox_datamodel,
		// booleanModels, restModels, chckbxBooleanPreference);

		// -------------------------------------------------------------------------------
		// ----------- Action
		// Listeners---------------------------------------------------
		// -------------------------------------------------------------------------------

		// ComboBox

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checkBooleanPreferenceCheckbox(comboBox_type,
				// comboBox_datamodel, booleanModels, restModels,
				// chckbxBooleanPreference);

				checkBooleanPreferenceCheckbox();

			}
		});

		comboBoxDatamodel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		// Buttons

		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Recommender22.main(null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(CopyOfrecommenderJPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File data = selectedFile.getSelectedFile();
					String absPath = data.getAbsolutePath();
					int selected = comboBoxDatamodel.getSelectedIndex();

					try {
						// TODO: distintos tipos de modelos...
						switch (selected) {
							case 0:
								dataModel = new FileDataModel(new File(absPath));
								break;
							case 1:
								dataModel = new GenericDataModel(GenericDataModel.toDataMap(new FileDataModel(new File(absPath))));
								break;
							case 2:
								dataModel = new ExtendedDataModel(new File(absPath), ",");
								break;
							case 3:
								// dataModel = new CassandraDataModel(new
								// File(absPath));
								break;
							case 4:
								dataModel = new FileDataModel(new File(absPath));
								break;
							case 5:
								dataModel = new FileDataModel(new File(absPath));
								break;
							case 6:
								dataModel = new FileDataModel(new File(absPath));
								break;
							case 7:
								dataModel = new FileDataModel(new File(absPath));
								break;
							default:
								dataModel = new FileDataModel(new File(absPath));
								break;
						}

						// dataModel = new FileDataModel(new File(absPath));
						textPath.setText(absPath);
						mainGUI.writeResult("Data Model successfully created from file", "info");
					} catch (IllegalArgumentException e1) {
						mainGUI.writeResult("Error reading data file: " + e1.getMessage(), "error");
						log.error("Error reading data file", e1);
					} catch (Exception e1) {
						mainGUI.writeResult("Error reading data file", "error");
						log.error("Error reading data file", e1);
					}

				} else if (i == JFileChooser.ERROR_OPTION) {
					mainGUI.writeResult("Error openig the file", "error");
					log.error("Error opening data file");
				}
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void checkBooleanPreferenceCheckbox() {
		int selectedType = comboBoxType.getSelectedIndex();
		if (selectedType == 0 || selectedType == 1) {
			// user or item based
			chckbxBooleanPreference.setEnabled(true);
			comboBoxDatamodel.setModel(restModels);
		} else {
			// clustering
			chckbxBooleanPreference.setEnabled(false);
			comboBoxDatamodel.setModel(booleanModels);
		}
	}

	public JPanel getPanel() {
		return panelRecommender;
	}

	public void setPanel(JPanel panel_recommender) {
		this.panelRecommender = panel_recommender;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboBox_type() {
		return comboBoxType;
	}

	public void setComboBox_type(@SuppressWarnings("rawtypes") JComboBox comboBox_type) {
		this.comboBoxType = comboBox_type;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboBox_datamodel() {
		return comboBoxDatamodel;
	}

	public void setComboBox_datamodel(@SuppressWarnings("rawtypes") JComboBox comboBox_datamodel) {
		this.comboBoxDatamodel = comboBox_datamodel;
	}

	public JButton getBtnRun() {
		return btnRun;
	}

	public void setBtnRun(JButton btnRun) {
		this.btnRun = btnRun;
	}

	public JButton getBtnImport() {
		return btnImport;
	}

	public void setBtnImport(JButton btnImport) {
		this.btnImport = btnImport;
	}

	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel getBooleanModels() {
		return booleanModels;
	}

	public void setBooleanModels(@SuppressWarnings("rawtypes") DefaultComboBoxModel booleanModels) {
		this.booleanModels = booleanModels;
	}

	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel getRestModels() {
		return restModels;
	}

	public void setRestModels(@SuppressWarnings("rawtypes") DefaultComboBoxModel restModels) {
		this.restModels = restModels;
	}

	public JTextField getTextPath() {
		return textPath;
	}

	public void setTextPath(JTextField textPath) {
		this.textPath = textPath;
	}

	public JCheckBox getChckbxBooleanPreference() {
		return chckbxBooleanPreference;
	}

	public void setChckbxBooleanPreference(JCheckBox chckbxBooleanPreference) {
		this.chckbxBooleanPreference = chckbxBooleanPreference;
	}

}
