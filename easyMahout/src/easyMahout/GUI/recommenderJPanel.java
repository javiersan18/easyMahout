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
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import MahoutInAction.Recommender.Recommender22;

public class recommenderJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelRecommender;

	private JLabel labelType, labelDatamodel, labelSimilarity, labelDataSource;

	private JComboBox comboBox_type, comboBox_datamodel;

	private JButton btnRun, btnImport;

	private DefaultComboBoxModel booleanModels, restModels;

	private JTextField textPath;

	private JCheckBox chckbxBooleanPreference;

	private final static Logger log = Logger.getLogger(recommenderJPanel.class);

	// Recommender atributes
	private Recommender recomm;

	@SuppressWarnings("unused")
	private DataModel dataModel;

	private Similarity similarityMetric;

	public recommenderJPanel() {
		panelRecommender = new JPanel();
		panelRecommender.setLayout(null);

		labelType = new JLabel("Type");
		labelType.setBounds(12, 12, 27, 16);
		panelRecommender.add(labelType);

		comboBox_type = new JComboBox();
		comboBox_type.setBounds(125, 8, 144, 25);
		comboBox_type.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based", "Clustering" }));
		panelRecommender.add(comboBox_type);

		btnRun = new JButton("Run!");
		btnRun.setBounds(599, 387, 98, 26);
		panelRecommender.add(btnRun);

		labelDatamodel = new JLabel("Data Model");
		labelDatamodel.setBounds(460, 13, 63, 14);
		panelRecommender.add(labelDatamodel);

		comboBox_datamodel = new JComboBox();
		booleanModels = new DefaultComboBoxModel(new String[] { "GenericBooleanPrefDataModel" });
		restModels = new DefaultComboBoxModel(new String[] { "FileDataModel", "GenericDataModel", "ExtendedDataModel",
				"CassandraDataModel", "HBaseDataModel", "KDDCupDataModel", "MongoDBDataModel", "PlusAnonymousConcurrentUserDataModel" });
		comboBox_datamodel.setModel(restModels);
		comboBox_datamodel.setBounds(541, 8, 156, 25);
		panelRecommender.add(comboBox_datamodel);

		// Similarity Metric (Label and comboBox)
		labelSimilarity = new JLabel("Similarity Metric");
		labelSimilarity.setBounds(12, 87, 92, 16);
		panelRecommender.add(labelSimilarity);

		JComboBox comboBox_similarity = new JComboBox();
		comboBox_similarity.setModel(new DefaultComboBoxModel(new String[] { "Abstract", "Average", "Caching", "CityBlock", "Euclidean",
				"Generic", "Likelihood", "Pearson Correlation", "Spearman Correlation", "Tanimoto Coefficient", "Uncenter Cosine" }));
		comboBox_similarity.setBounds(125, 83, 144, 25);
		panelRecommender.add(comboBox_similarity);

		labelDataSource = new JLabel("Data Source");
		labelDataSource.setBounds(12, 49, 70, 16);
		panelRecommender.add(labelDataSource);

		textPath = new JTextField();
		textPath.setBounds(125, 45, 462, 25);
		panelRecommender.add(textPath);
		textPath.setColumns(10);

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

		comboBox_type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checkBooleanPreferenceCheckbox(comboBox_type,
				// comboBox_datamodel, booleanModels, restModels,
				// chckbxBooleanPreference);

				checkBooleanPreferenceCheckbox();

			}
		});

		comboBox_datamodel.addActionListener(new ActionListener() {
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
				int i = selectedFile.showOpenDialog(recommenderJPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File data = selectedFile.getSelectedFile();
					String absPath = data.getAbsolutePath();
					int selected = comboBox_datamodel.getSelectedIndex();
					if (selected == 0) {
						
						
						
						try {
							dataModel = new FileDataModel(new File(absPath));
							textPath.setText(absPath);
							mainGUI.writeResult("Data Model successfully created from file", "info");
						} catch (IllegalArgumentException e1) {
							mainGUI.writeResult("Error reading data file: " + e1.getMessage(), "error");
							log.error("Error reading data file", e1);
						} catch (Exception e1) {
							mainGUI.writeResult("Error reading data file", "error");
							log.error("Error reading data file", e1);
						}

					} else if (selected != 1) {
						mainGUI.writeResult("Error openig the file", "error");
						log.error("Error opening data file");
					}

				}
			}
		});

	}

	private void checkBooleanPreferenceCheckbox() {
		int selectedType = comboBox_type.getSelectedIndex();
		if (selectedType == 0 || selectedType == 1) {
			// user or item based
			chckbxBooleanPreference.setEnabled(true);
			comboBox_datamodel.setModel(restModels);
		} else {
			// clustering
			chckbxBooleanPreference.setEnabled(false);
			comboBox_datamodel.setModel(booleanModels);
		}
	}

	public JPanel getPanel() {
		return panelRecommender;
	}

	public void setPanel(JPanel panel_recommender) {
		this.panelRecommender = panel_recommender;
	}

	public JComboBox getComboBox_type() {
		return comboBox_type;
	}

	public void setComboBox_type(JComboBox comboBox_type) {
		this.comboBox_type = comboBox_type;
	}

	public JComboBox getComboBox_datamodel() {
		return comboBox_datamodel;
	}

	public void setComboBox_datamodel(JComboBox comboBox_datamodel) {
		this.comboBox_datamodel = comboBox_datamodel;
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

	public DefaultComboBoxModel getBooleanModels() {
		return booleanModels;
	}

	public void setBooleanModels(DefaultComboBoxModel booleanModels) {
		this.booleanModels = booleanModels;
	}

	public DefaultComboBoxModel getRestModels() {
		return restModels;
	}

	public void setRestModels(DefaultComboBoxModel restModels) {
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
