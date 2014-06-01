package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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

import com.jidesoft.swing.FolderChooser;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.ExtendedDataModel;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class DataModelRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel booleanModels, restModels;

	private static JComboBox comboBoxDatamodel;

	private static JTextField textInputPath;

	private static JTextField tfDelimiter;

	private static JTextField textOutputPath;

	private JLabel lblDelimiter;

	private JLabel lblInputDataSource;

	private JLabel lblOutputDataSource;

	private JButton btnSelectInput;

	private JButton btnSelectOutput;

	private JButton btnCreate;

	private final JButton btnHelp;

	private static JCheckBox chckbxBooleanPreferences;

	private static DataModel dataModel;

	private static JCheckBox chckbxRemoveIfExist;

	private HelpTooltip helpTooltip;

	private final static Logger log = Logger.getLogger(DataModelRecommenderPanel.class);

	public DataModelRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Data Model", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxDatamodel = new JComboBox();
		comboBoxDatamodel.addItemListener(new ItemChangeListener());
		booleanModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.GENERIC_BOOLEAN });
		restModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.GENERIC, Constants.DataModel.EXTENDED });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(38, 68, 216, 20);
		add(comboBoxDatamodel);

		chckbxBooleanPreferences = new JCheckBox("Boolean Preferences ");
		chckbxBooleanPreferences.setBounds(38, 27, 199, 23);
		add(chckbxBooleanPreferences);
		chckbxBooleanPreferences.addItemListener(new ItemChangeListener());

		lblInputDataSource = new JLabel("Input data source:");
		lblInputDataSource.setBounds(38, 105, 157, 14);
		add(lblInputDataSource);

		textInputPath = new JTextField();
		textInputPath.setBounds(38, 130, 401, 20);
		add(textInputPath);
		textInputPath.setColumns(10);
		textInputPath.getDocument().addDocumentListener(new TextFieldChangeListener());

		btnSelectInput = new JButton("Select File...");
		btnSelectInput.setBounds(130, 165, 107, 23);
		add(btnSelectInput);

		lblDelimiter = new JLabel("Delimiter");
		lblDelimiter.setBounds(274, 71, 68, 14);
		add(lblDelimiter);
		lblDelimiter.setEnabled(false);

		tfDelimiter = new JTextField();
		tfDelimiter.setHorizontalAlignment(SwingConstants.CENTER);
		tfDelimiter.setBounds(352, 68, 46, 20);
		add(tfDelimiter);
		tfDelimiter.setColumns(10);
		tfDelimiter.setText(",");
		tfDelimiter.setEnabled(false);
		tfDelimiter.getDocument().addDocumentListener(new TextFieldChangeListener());

		btnCreate = new JButton("Create Model");
		btnCreate.setBounds(241, 165, 134, 23);
		add(btnCreate);

		btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_DATAMODEL);
		add(helpTooltip);

		lblOutputDataSource = new JLabel("Output data source (Optional):");
		lblOutputDataSource.setBounds(38, 206, 216, 14);
		add(lblOutputDataSource);

		textOutputPath = new JTextField();
		textOutputPath.setColumns(10);
		textOutputPath.setBounds(38, 230, 401, 20);
		add(textOutputPath);
		textOutputPath.getDocument().addDocumentListener(new TextFieldChangeListener());

		btnSelectOutput = new JButton("Select File...");
		btnSelectOutput.setBounds(130, 267, 107, 23);
		add(btnSelectOutput);

		chckbxRemoveIfExist = new JCheckBox("Remove if exist");
		chckbxRemoveIfExist.setBounds(241, 267, 157, 23);
		add(chckbxRemoveIfExist);
		chckbxRemoveIfExist.setEnabled(false);

		btnSelectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(DataModelRecommenderPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textInputPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(DataModelRecommenderPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textInputPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error openig the file.", Constants.Log.ERROR);
					}
				}
			}
		});

		btnSelectOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(DataModelRecommenderPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textOutputPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(DataModelRecommenderPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textOutputPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the file.", Constants.Log.ERROR);
					}
				}
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboBoxDatamodel.getSelectedIndex();
				String filePath = textInputPath.getText();
				try {
					// TODO: distintos tipos de modelos...
					switch (selected) {
						case 0:
							setDataModel(new GenericDataModel(GenericDataModel.toDataMap(new FileDataModel(new File(filePath)))));
							MainGUI.writeResult("Data Model successfully created from file.", Constants.Log.INFO);
							break;
						case 1:
							String delimiter = tfDelimiter.getText();
							if (StringUtils.isBlank(delimiter)) {
								tfDelimiter.setBackground(new Color(240, 128, 128));
								MainGUI.writeResult("Delimiter for Extended Data Model is empty.", Constants.Log.ERROR);
							} else {
								tfDelimiter.setBackground(Color.WHITE);
								setDataModel(new ExtendedDataModel(new File(filePath), tfDelimiter.getText()));
								MainGUI.writeResult("Data Model successfully created from file.", Constants.Log.INFO);
							}
							break;
						default:
							setDataModel(new FileDataModel(new File(filePath)));
							break;
					}
					// TODO: revisar errores de excepciones mostrados en
					// consola
				} catch (IllegalArgumentException e1) {
					MainGUI.writeResult("Error reading data file: " + e1.getMessage() + ".", Constants.Log.ERROR);
				} catch (Exception e1) {
					MainGUI.writeResult("File " + e1.getMessage() + " not found.", Constants.Log.ERROR);
				}
			}
		});

		chckbxBooleanPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					comboBoxDatamodel.setModel(booleanModels);
					lblDelimiter.setEnabled(false);
					tfDelimiter.setEnabled(false);
					EvaluatorRecommenderPanel.setBooleanPreferences(true);
				} else {
					comboBoxDatamodel.setModel(restModels);
					EvaluatorRecommenderPanel.setBooleanPreferences(false);
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

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		DataModelRecommenderPanel.dataModel = dataModel;
	}

	public void setDistributed(boolean distributed) {
		if (distributed) {
			helpTooltip.setText(RecommenderTips.RECOMM_DATAMODEL_DIST);
			lblOutputDataSource.setText("Output directory:");
			lblInputDataSource.setText("Input directory:");
			btnSelectOutput.setText("Select Folder...");
			btnSelectInput.setText("Select Folder...");
		} else {
			helpTooltip.setText(RecommenderTips.RECOMM_DATAMODEL);
			lblOutputDataSource.setText("Output data file (Optional):");
			lblInputDataSource.setText("Input data source:");
			btnSelectOutput.setText("Select File...");
			btnSelectInput.setText("Select File...");
		}

		comboBoxDatamodel.setEnabled(!distributed);
		btnCreate.setEnabled(!distributed);
		chckbxRemoveIfExist.setEnabled(distributed);
	}

	public static String getInputPath() {
		if (StringUtils.isNotBlank(textInputPath.getText())) {
			return textInputPath.getText();
		} else {
			return "  ";
		}
	}

	public static String getOutputPath() {
		if (StringUtils.isNotBlank(textOutputPath.getText())) {
			return textOutputPath.getText();
		} else {
			return "  ";
		}
	}

	public static String getBooleanPrefs() {
		return String.valueOf(chckbxBooleanPreferences.isSelected());
	}

	public static String getSelectedDataModel() {
		return (String) comboBoxDatamodel.getSelectedItem();
	}

	public static String getDelimiter() {
		if (StringUtils.isNotBlank(tfDelimiter.getText())) {
			return tfDelimiter.getText();
		} else {
			return "  ";
		}
	}

	public static void setBooleanPrefs(boolean selected) {
		chckbxBooleanPreferences.setSelected(selected);
	}

	public static boolean removeUfExist() {
		return chckbxRemoveIfExist.isSelected();
	}

	public static void setRemoveUfExist(boolean selected) {
		chckbxRemoveIfExist.setSelected(selected);
	}

	public static void setSelectedModel(String model) {
		comboBoxDatamodel.setSelectedItem(model);
	}

	public static void setDelimiter(String delimiter) {
		tfDelimiter.setText(delimiter);
	}

	public static void setInputPath(String inputPath) {
		textInputPath.setText(inputPath);
	}

	public static void setOutputPath(String outputPath) {
		textOutputPath.setText(outputPath);
	}
}
