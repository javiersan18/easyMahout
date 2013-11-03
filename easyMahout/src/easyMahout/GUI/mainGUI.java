package easyMahout.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import MahoutInAction.Recommender.Recommender22;
import easyMahout.enumTypes.RecommenderType;
import easyMahout.enumTypes.DataModelType;

import javax.swing.JTextField;

import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import javax.swing.JCheckBox;

public class mainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame frmEasymahout;
	private JTextField textField;

	// Recommender Attributes
	private Recommender recomm;
	private DataModel model;

	// private DefaultComboBoxModel<String> userSimilarityBox = new
	// DefaultComboBoxModel<String>(new String[] {"Generic DataModel",
	// "Extended DataModel"});

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGUI window = new mainGUI();
					window.frmEasymahout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmEasymahout = new JFrame();
		frmEasymahout.setTitle("easyMahout 0.1");

		frmEasymahout.setMinimumSize(new Dimension(750, 650));
		frmEasymahout.setBounds(100, 100, 95, 279);

		frmEasymahout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEasymahout.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 742, 464);
		frmEasymahout.getContentPane().add(tabbedPane);

		createRecommenderTab(tabbedPane);

		createClassificationTab(tabbedPane);

		createLog();

		createMenuBar();

	}

	private void createLog() {
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(0, 462, 742, 131);
		frmEasymahout.getContentPane().add(formattedTextField);

		JTextPane log = new JTextPane();

		log.setBounds(12, 462, 712, 118);
		frmEasymahout.getContentPane().add(log);
	}

	private void createClassificationTab(JTabbedPane tabbedPane) {
		JPanel panel_classification = new JPanel();
		tabbedPane.addTab("Classification", null, panel_classification, null);
	}

	private void createRecommenderTab(JTabbedPane tabbedPane) {
		JPanel panel_recommender = new JPanel();
		tabbedPane.addTab("Recommendation", null, panel_recommender, null);
		panel_recommender.setLayout(null);

		JLabel lblType = new JLabel("Type");
		lblType.setBounds(12, 12, 27, 16);
		panel_recommender.add(lblType);

		final JComboBox<String> comboBox_type = new JComboBox<String>();
		comboBox_type.setBounds(125, 8, 144, 25);
		comboBox_type.setModel(new DefaultComboBoxModel<String>(new String[] { "User-based",
				"Item-based", "Clustering" }));
		panel_recommender.add(comboBox_type);

		JButton btnRun = new JButton("Run!");
		btnRun.setBounds(599, 387, 98, 26);
		panel_recommender.add(btnRun);

		JLabel lblDatamodel = new JLabel("Data Model");
		lblDatamodel.setBounds(460, 13, 63, 14);
		panel_recommender.add(lblDatamodel);

		final JComboBox<String> comboBox_datamodel = new JComboBox<String>();
		final DefaultComboBoxModel<String> booleanModels = new DefaultComboBoxModel<String>(
				new String[] { "GenericBooleanPrefDataModel" });
		final DefaultComboBoxModel<String> restModels = new DefaultComboBoxModel<String>(new String[] {
				"FileDataModel", "GenericDataModel", "ExtendedDataModel", "CassandraDataModel",
				"HBaseDataModel", "KDDCupDataModel", "MongoDBDataModel",
				"PlusAnonymousConcurrentUserDataModel" });
		comboBox_datamodel.setModel(restModels);
		comboBox_datamodel.setBounds(541, 8, 156, 25);
		panel_recommender.add(comboBox_datamodel);

		// Similarity Metric (Label and comboBox)
		JLabel lblNewLabel = new JLabel("Similarity Metric");
		lblNewLabel.setBounds(12, 87, 92, 16);
		panel_recommender.add(lblNewLabel);

		JComboBox<String> comboBox_similarity = new JComboBox<String>();
		comboBox_similarity.setModel(new DefaultComboBoxModel<String>(new String[] { "Abstract",
				"Average", "Caching", "CityBlock", "Euclidean", "Generic", "Likelihood",
				"Pearson Correlation", "Spearman Correlation", "Tanimoto Coefficient",
				"Uncenter Cosine" }));
		comboBox_similarity.setBounds(125, 83, 144, 25);
		panel_recommender.add(comboBox_similarity);

		JLabel lblNewLabel_1 = new JLabel("Data Source");
		lblNewLabel_1.setBounds(12, 49, 70, 16);
		panel_recommender.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(125, 45, 462, 25);
		panel_recommender.add(textField);
		textField.setColumns(10);

		JButton btnImport = new JButton("Import...");

		btnImport.setBounds(599, 44, 98, 26);
		panel_recommender.add(btnImport);

		final JCheckBox chckbxBooleanPreference = new JCheckBox("Boolean Preference");
		chckbxBooleanPreference.setBounds(293, 8, 138, 24);
		panel_recommender.add(chckbxBooleanPreference);

		checkBooleanPreferenceCheckbox(comboBox_type, comboBox_datamodel, booleanModels, restModels, chckbxBooleanPreference);

		// -------------------------------------------------------------------------------
		// ----------- Action
		// Listeners---------------------------------------------------
		// -------------------------------------------------------------------------------

		// ComboBox

		comboBox_type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checkBooleanPreferenceCheckbox(comboBox_type, comboBox_datamodel, booleanModels, restModels, chckbxBooleanPreference);

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
				int i = selectedFile.showOpenDialog(mainGUI.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File data = selectedFile.getSelectedFile();
					String absPath = data.getAbsolutePath();
					textField.setText(absPath);
					int selected = comboBox_datamodel.getSelectedIndex();
					if (selected == 0) {

					} else if (selected == 1) {

					} else {
						// error
					}

				}
			}
		});
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frmEasymahout.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}

	private void checkBooleanPreferenceCheckbox(JComboBox<String> comboBox_type,
			JComboBox<String> comboBox_model, DefaultComboBoxModel<String> booleanModels,
			DefaultComboBoxModel<String> restModels,
			JCheckBox chckbxBooleanPreference) {
		int selectedType = comboBox_type.getSelectedIndex();
		if (selectedType == 0 || selectedType == 1) {
			// user or item based
			chckbxBooleanPreference.setEnabled(true);
			comboBox_model.setModel(restModels);
		} else {
			// clustering
			chckbxBooleanPreference.setEnabled(false);
			comboBox_model.setModel(booleanModels);
		}
	}
}
