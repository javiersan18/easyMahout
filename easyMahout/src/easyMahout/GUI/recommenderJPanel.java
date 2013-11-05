package easyMahout.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import easyMahout.recommender.ExtendedDataModel;
import easyMahout.utils.DynamicTree;
import MahoutInAction.Recommender.Recommender22;

import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Color;

public class recommenderJPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private static JPanel panelRecommender;

	private JLabel labelType, labelDatamodel, labelSimilarity, labelDataSource, labelEvaluator, labelNeighborhood;

	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxType, comboBoxDatamodel, comboBoxNeighborhood, comboBoxSimilarity;

	private JButton btnRun, btnImport;

	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel booleanModels, restModels;

	private JTextField textPath, usersNeigborhood;

	private JCheckBox chckbxBooleanPreference;
	
	private JPanel configPanel;

	

	private final static Logger log = Logger.getLogger(recommenderJPanel.class);

	// Recommender atributes
	private Recommender recomm;

	@SuppressWarnings("unused")
	private DataModel dataModel;

	private Similarity similarityMetric;
	
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public recommenderJPanel() {
		panelRecommender = new JPanel();
		panelRecommender.setLayout(null);
		
		labelType = new JLabel("Type");
		labelType.setBounds(138, 489, 27, 16);
		panelRecommender.add(labelType);
//
//		comboBoxType = new JComboBox();
//		comboBoxType.setBounds(367, 294, 144, 25);
//		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based", "Clustering" }));
//		panelRecommender.add(comboBoxType);
//
//		btnRun = new JButton("Run!");
//		btnRun.setBounds(599, 387, 98, 26);
//		panelRecommender.add(btnRun);
//
//		labelDatamodel = new JLabel("Data Model");
//		labelDatamodel.setBounds(460, 13, 63, 14);
//		panelRecommender.add(labelDatamodel);
//
//		comboBoxDatamodel = new JComboBox();
//		booleanModels = new DefaultComboBoxModel(new String[] { "GenericBooleanPrefDataModel" });
//		restModels = new DefaultComboBoxModel(new String[] { "FileDataModel", "GenericDataModel", "ExtendedDataModel",
//				"CassandraDataModel", "HBaseDataModel", "KDDCupDataModel", "MongoDBDataModel", "PlusAnonymousConcurrentUserDataModel" });
//		comboBoxDatamodel.setModel(restModels);
//		comboBoxDatamodel.setBounds(541, 8, 156, 25);
//		panelRecommender.add(comboBoxDatamodel);
//
//		// Similarity Metric (Label and comboBox)
//		labelSimilarity = new JLabel("Similarity Metric");
//		labelSimilarity.setBounds(266, 369, 92, 16);
//		panelRecommender.add(labelSimilarity);
//
//		comboBoxSimilarity = new JComboBox();
//		comboBoxSimilarity.setModel(new DefaultComboBoxModel(new String[] { "Abstract", "Average", "Caching", "CityBlock", "Euclidean",
//				"Generic", "Likelihood", "Pearson Correlation", "Spearman Correlation", "Tanimoto Coefficient", "Uncenter Cosine" }));
//		comboBoxSimilarity.setBounds(367, 369, 144, 25);
//		panelRecommender.add(comboBoxSimilarity);
//
//		labelDataSource = new JLabel("Data Source");
//		labelDataSource.setBounds(266, 331, 70, 16);
//		panelRecommender.add(labelDataSource);
//
//		textPath = new JTextField();
//		textPath.setBounds(279, 45, 308, 25);
//		panelRecommender.add(textPath);
//		textPath.setColumns(10);
//
//		labelEvaluator = new JLabel("Evaluator");
//		labelEvaluator.setBounds(266, 449, 46, 14);
//		panelRecommender.add(labelEvaluator);
//
//		labelNeighborhood = new JLabel("Neighborhood");
//		labelNeighborhood.setBounds(264, 410, 72, 14);
//		panelRecommender.add(labelNeighborhood);
//
//		comboBoxNeighborhood = new JComboBox();
//		comboBoxNeighborhood.setBounds(367, 411, 144, 20);
//		comboBoxNeighborhood.setModel(new DefaultComboBoxModel(new String[] { "Nearest N", "Threshold" }));
//		panelRecommender.add(comboBoxNeighborhood);
//
//		usersNeigborhood = new JTextField();
//		usersNeigborhood.setBounds(345, 125, 86, 20);
//		panelRecommender.add(usersNeigborhood);
//		usersNeigborhood.setColumns(10);
//		usersNeigborhood.setText("100");
//
//		JLabel lblUsers = new JLabel("Users");
//		lblUsers.setBounds(293, 128, 46, 14);
//		panelRecommender.add(lblUsers);
//
//		btnImport = new JButton("Import...");
//
//		btnImport.setBounds(599, 44, 98, 26);
//		panelRecommender.add(btnImport);
//
//		chckbxBooleanPreference = new JCheckBox("Boolean Preference");
//		chckbxBooleanPreference.setBounds(293, 8, 138, 24);
//		panelRecommender.add(chckbxBooleanPreference);

		// Hashtable<String, Object> hierarchy = new Hashtable();
		// Hashtable<String, Object> hierarchy2 = new Hashtable();
		// hierarchy.put("Recommender", hierarchy2);
		// hierarchy2.put("Type", new Hashtable());
		// hierarchy2.put("Data Model", new Hashtable());
		// hierarchy2.put("Similarity", new Hashtable());
		// hierarchy2.put("Neighborhood", new Hashtable());
		// hierarchy2.put("Evaluator", new Hashtable());

		// JTree tree = new JTree(hierarchy);
		// tree.expandRow(0);
		DynamicTree treePanel = new DynamicTree("Recommender");
		populateTree(treePanel);
		treePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		treePanel.setBounds(10, 12, 226, 412);
		treePanel.expandRow(0);
		panelRecommender.add(treePanel);
		
		//panel = new typeRecommenderPanel();
		//panel.setBounds(246, 12, 473, 412);
		//panelRecommender.add(panel);
		
		
		
		// JTree tree = new JTree();
		// tree.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		// tree.setBounds(10, 12, 226, 412);
		// panelRecommender.add(tree);


		
		
		
		

		// -------------------------------------------------------------------------------
		// ----------- Action
		// Listeners---------------------------------------------------
		// -------------------------------------------------------------------------------

		// ComboBox

//		comboBoxType.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// checkBooleanPreferenceCheckbox(comboBox_type,
//				// comboBox_datamodel, booleanModels, restModels,
//				// chckbxBooleanPreference);
//
//				checkBooleanPreferenceCheckbox();
//
//			}
//		});
//
//		comboBoxDatamodel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
//
//		// Buttons
//
//		btnRun.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					Recommender22.main(null);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//		});
//
//		btnImport.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser selectedFile = new JFileChooser();
//				int i = selectedFile.showOpenDialog(recommenderJPanel.this);
//				if (i == JFileChooser.APPROVE_OPTION) {
//					File data = selectedFile.getSelectedFile();
//					String absPath = data.getAbsolutePath();
//					int selected = comboBoxDatamodel.getSelectedIndex();
//
//					try {
//						// TODO: distintos tipos de modelos...
//						switch (selected) {
//							case 0:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//							case 1:
//								dataModel = new GenericDataModel(GenericDataModel.toDataMap(new FileDataModel(new File(absPath))));
//								break;
//							case 2:
//								dataModel = new ExtendedDataModel(new File(absPath), ",");
//								break;
//							case 3:
//								// dataModel = new CassandraDataModel(new
//								// File(absPath));
//								break;
//							case 4:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//							case 5:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//							case 6:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//							case 7:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//							default:
//								dataModel = new FileDataModel(new File(absPath));
//								break;
//						}
//
//						// dataModel = new FileDataModel(new File(absPath));
//						textPath.setText(absPath);
//						mainGUI.writeResult("Data Model successfully created from file", "info");
//					} catch (IllegalArgumentException e1) {
//						mainGUI.writeResult("Error reading data file: " + e1.getMessage(), "error");
//						log.error("Error reading data file", e1);
//					} catch (Exception e1) {
//						mainGUI.writeResult("Error reading data file", "error");
//						log.error("Error reading data file", e1);
//					}
//
//				} else if (i == JFileChooser.ERROR_OPTION) {
//					mainGUI.writeResult("Error openig the file", "error");
//					log.error("Error opening data file");
//				}
//			}
//		});
//
}
//
//	@SuppressWarnings("unchecked")
//	private void checkBooleanPreferenceCheckbox() {
//		int selectedType = comboBoxType.getSelectedIndex();
//		if (selectedType == 0 || selectedType == 1) {
//			// user or item based
//			chckbxBooleanPreference.setEnabled(true);
//			comboBoxDatamodel.setModel(restModels);
//		} else {
//			// clustering
//			chckbxBooleanPreference.setEnabled(false);
//			comboBoxDatamodel.setModel(booleanModels);
//		}
//	}

	public static void addComponent(JComponent comp){
		panelRecommender.add(comp);
	}
		
	public void populateTree(DynamicTree treePanel) {

		String cat1 = new String("Type");
		String cat2 = new String("Data Model");
		String cat3 = new String("Similarity");
		String cat4 = new String("Neighborhood");
		String cat5 = new String("Evaluator");

		DefaultMutableTreeNode pRoot, pType, pModel, pSim, pNeigh, pEval;

		pType = treePanel.addObject(null, cat1);
		pModel = treePanel.addObject(null, cat2);
		pSim = treePanel.addObject(null, cat3);
		pNeigh = treePanel.addObject(null, cat4);
		pEval = treePanel.addObject(null, cat5);
		

		// treePanel.addObject(p1, c1Name);
		// treePanel.addObject(p1, c2Name);

		// treePanel.addObject(p2, c1Name);
		// treePanel.addObject(p2, c2Name);
	}

	public JPanel getPanel() {
		return panelRecommender;
	}
	
	public static void setConfigPanel(JPanel configPanel) {
		configPanel = configPanel;
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
