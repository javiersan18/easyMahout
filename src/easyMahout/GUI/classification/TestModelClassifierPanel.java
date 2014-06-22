package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jidesoft.swing.FolderChooser;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.builder.ClassifierBuilder;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClassifierTips;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class TestModelClassifierPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

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

	private static JTextField textModelPath;

	private JButton btnSelectModel;

	private JLabel lblLabelIndexSource;

	private static JTextField textLabelIndexPath;

	private JButton btnSelectLabelIndex;

	private final static Logger log = Logger.getLogger(DataModelClassifierPanel.class);
	
	public TestModelClassifierPanel	() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Test model", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

//Input		
		
		lblInputDataSource = new JLabel("Input data source:");
		lblInputDataSource.setBounds(38, 40, 150, 14);
		add(lblInputDataSource);

		textInputPath = new JTextField();
		
		//Entrada predefinida
		
		textInputPath.setText("/home/daniel/Desktop/Data");
		
		textInputPath.setBounds(38, 60, 401, 20);
		add(textInputPath);
		textInputPath.setColumns(10);
		textInputPath.getDocument().addDocumentListener(new TextFieldChangeListener());
		textInputPath.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					
					if (!text.equals("")) {
						textInputPath.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is empty");
						MainGUI.writeResult("You have to choose an input!", Constants.Log.ERROR);
						textInputPath.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is empty");
					MainGUI.writeResult("You have to choose an input!", Constants.Log.ERROR);
					textInputPath.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		btnSelectInput=new JButton("Select File...");
		
		btnSelectInput.setBounds(182, 85, 150, 23);
		add(btnSelectInput);

//Model Input
		
		lblModelDataSource = new JLabel("Model path:");
		lblModelDataSource.setBounds(38, 120, 107, 14);
		add(lblModelDataSource);

		textModelPath = new JTextField();
		
		//Entrada predefinida
		
		textModelPath.setText("/home/daniel/Desktop/Classifier/model");
		
		textModelPath.setBounds(38, 140, 401, 20);
		add(textModelPath);
		textModelPath.setColumns(10);
		textModelPath.getDocument().addDocumentListener(new TextFieldChangeListener());
		textModelPath.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					
					if (!text.equals("")) {
						textModelPath.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is empty");
						MainGUI.writeResult("You have to choose an model input!", Constants.Log.ERROR);
						textModelPath.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is empty");
					MainGUI.writeResult("You have to choose an model input!", Constants.Log.ERROR);
					textModelPath.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		btnSelectModel=new JButton("Select File...");
		
		btnSelectModel.setBounds(182, 165, 150, 23);
		add(btnSelectModel);
		
		
//Label Index
		
		lblLabelIndexSource = new JLabel("Label Index path:");
		lblLabelIndexSource.setBounds(38, 195, 150, 14);
		add(lblLabelIndexSource);

		textLabelIndexPath = new JTextField();
				
		//Entrada predefinida
				
		textLabelIndexPath.setText("/home/daniel/Desktop/Classifier/labelIndex");
				
		textLabelIndexPath.setBounds(38, 215, 401, 20);
		add(textLabelIndexPath);
		textLabelIndexPath.setColumns(10);
		textLabelIndexPath.getDocument().addDocumentListener(new TextFieldChangeListener());
		textLabelIndexPath.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
							
					if (!text.equals("")) {
						textLabelIndexPath.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is empty");
						MainGUI.writeResult("You have to choose a label index input!", Constants.Log.ERROR);
						textLabelIndexPath.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is empty");
					MainGUI.writeResult("You have to choose label index input!", Constants.Log.ERROR);
					textLabelIndexPath.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		btnSelectLabelIndex=new JButton("Select File...");
				
		btnSelectLabelIndex.setBounds(182, 240, 150, 23);
		add(btnSelectLabelIndex);
				
//Output 

		lblOutputDataSource = new JLabel("Output data source :");
		lblOutputDataSource.setBounds(38, 270, 157, 14);
		add(lblOutputDataSource);

		textOutputPath = new JTextField();
		
		//Salida predefinida
		
		textOutputPath.setText("/home/daniel/Desktop/Classifier");
		
		textOutputPath.setColumns(10);
		textOutputPath.setBounds(38, 290, 401, 20);
		add(textOutputPath);
		textOutputPath.getDocument().addDocumentListener(new TextFieldChangeListener());
		textOutputPath.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					
					if (!text.equals("")) {
						textOutputPath.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is empty");
						MainGUI.writeResult("You have to choose an output!", Constants.Log.ERROR);
						textOutputPath.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is empty");
					MainGUI.writeResult("You have to choose an output!", Constants.Log.ERROR);
					textOutputPath.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		
		btnSelectOutput = new JButton("Select File...");
		btnSelectOutput.setBounds(182, 315, 150, 23);
		add(btnSelectOutput);
		
		btnCreate=new JButton("Test Classifier");
		btnCreate.setBounds(280, 365, 150, 23);
		add(btnCreate);

		btnSelectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(TestModelClassifierPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textInputPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
						log.error("Error searching input directory");
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(TestModelClassifierPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textInputPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error opening the file.", Constants.Log.ERROR);
						log.error("Error opening data file");
					}
				}
			}
		});
		
		btnSelectModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(TestModelClassifierPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textModelPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
						log.error("Error searching input directory");
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(TestModelClassifierPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textModelPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the file.", Constants.Log.ERROR);
						log.error("Error searching output data file");
					}
				}
			}
		});
		
		btnSelectLabelIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(TestModelClassifierPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textLabelIndexPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
						log.error("Error searching input directory");
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(TestModelClassifierPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textLabelIndexPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the file.", Constants.Log.ERROR);
						log.error("Error searching output data file");
					}
				}
			}
		});

		btnSelectOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(TestModelClassifierPanel.this);
					if (returnVal == FolderChooser.APPROVE_OPTION) {
						File data = chooser.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textOutputPath.setText(absPath);
					} else if (returnVal == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the input directory.", Constants.Log.ERROR);
						log.error("Error searching input directory");
					}
				} else {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(TestModelClassifierPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textOutputPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error searching the file.", Constants.Log.ERROR);
						log.error("Error searching output data file");
					}
				}
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//mouse loading				
				btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				 
				MainGUI.clean();
				
				String filePath = textInputPath.getText();//input
				String output = textOutputPath.getText();
				tfDelimiter.setBackground(Color.WHITE);
				
				if (!filePath.isEmpty() && !output.isEmpty()){
					try {
						MainGUI.writeResult("Starting to test the classifier", Constants.Log.INFO);
						ClassifierBuilder.testClassifier();
					} catch (Exception e1) {
						e1.printStackTrace();
						//MainGUI.writeResult("Not able to build the classifier, GENERIC", Constants.Log.ERROR);
					}
				}
				else MainGUI.writeResult("You have to specify both input, model, label index and output source file!", Constants.Log.ERROR);
				
				//mouse finished loading
				btnCreate.setCursor(Cursor.getDefaultCursor());				
			}
		});	
		
//help		
		
		btnHelp = new JButton(new ImageIcon(TestModelClassifierPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClassifierTips.CLASSIFIER_TESTMODEL);
		add(helpTooltip);

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static String getInputPath() {
		if (StringUtils.isNotBlank(textInputPath.getText())) {
			return textInputPath.getText();
		} else {
			return "  ";
		}
	}
	
	public static String getModelPath() {
		if (StringUtils.isNotBlank(textModelPath.getText())) {
			return textModelPath.getText();
		} else {
			return "  ";
		}
	}
	
	public static String getLabelIndexPath() {
		if (StringUtils.isNotBlank(textLabelIndexPath.getText())) {
			return textLabelIndexPath.getText();
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

}
