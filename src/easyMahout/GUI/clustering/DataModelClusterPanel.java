package easyMahout.GUI.clustering;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ProgressMonitor;
import javax.swing.border.LineBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;
import org.apache.mahout.common.distance.WeightedEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.WeightedManhattanDistanceMeasure;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import com.jidesoft.swing.FolderChooser;
import com.jidesoft.utils.SwingWorker;

import easyMahout.GUI.MainGUI;

import easyMahout.GUI.clustering.builder.ClusterBuilder;
import easyMahout.GUI.clustering.builder.CreateSequenceFile;
import easyMahout.GUI.clustering.builder.ReadSequenceFile;
import easyMahout.GUI.recommender.EvaluatorRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.GUI.recommender.builder.RecommenderBuilder;
import easyMahout.recommender.ExtendedDataModel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.MyThread;
import easyMahout.utils.help.ClusterTips;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;
import easyMahout.utils.listeners.TextFieldChangeListener;

import java.awt.ComponentOrientation;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;



public class DataModelClusterPanel extends JPanel {

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

	private static JButton btnCreate;

	private final JButton btnHelp;

	private static JCheckBox chckbxBooleanPreferences;

	private static DataModel dataModel;
	
	private static String outputFormatted;

	private HelpTooltip helpTooltip;

	private final static Logger log = Logger.getLogger(DataModelClusterPanel.class);

	public DataModelClusterPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Data Model", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxDatamodel = new JComboBox();
		comboBoxDatamodel.addItemListener(new ItemChangeListener());
		booleanModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.GENERIC_BOOLEAN });
		restModels = new DefaultComboBoxModel(new String[] { Constants.DataModel.FILE, Constants.DataModel.GENERIC,
				Constants.DataModel.EXTENDED, Constants.DataModel.CASSANDRA, Constants.DataModel.HBASE, Constants.DataModel.KDDCUP,
				Constants.DataModel.MONGOL_DB, Constants.DataModel.PLUS_ANONYMOUS });
		comboBoxDatamodel.setModel(restModels);
		comboBoxDatamodel.setBounds(38, 68, 216, 20);
		add(comboBoxDatamodel);

		chckbxBooleanPreferences = new JCheckBox("Boolean Preferences ");
		chckbxBooleanPreferences.setBounds(38, 27, 199, 23);
		add(chckbxBooleanPreferences);
		chckbxBooleanPreferences.addItemListener(new ItemChangeListener());
		chckbxBooleanPreferences.setEnabled(false);

		lblInputDataSource = new JLabel("Input data source:");
		lblInputDataSource.setBounds(38, 105, 107, 14);
		add(lblInputDataSource);

		textInputPath = new JTextField();
		textInputPath.setBounds(38, 130, 401, 20);
		add(textInputPath);
		textInputPath.setColumns(10);
		textInputPath.setEditable(false);
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
		
		btnSelectInput.setBounds(182, 165, 107, 23);
		add(btnSelectInput);

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
		tfDelimiter.getDocument().addDocumentListener(new TextFieldChangeListener());

		btnCreate=new JButton("Run Clustering");
		btnCreate.setBounds(341, 365, 107, 23);
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

		lblOutputDataSource = new JLabel("Output data source :");
		lblOutputDataSource.setBounds(38, 206, 157, 14);
		add(lblOutputDataSource);

		textOutputPath = new JTextField();
		textOutputPath.setColumns(10);
		textOutputPath.setBounds(38, 230, 401, 20);
		add(textOutputPath);
		textOutputPath.setEditable(false);
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
		btnSelectOutput.setBounds(182, 265, 107, 23);
		add(btnSelectOutput);

		btnSelectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(DataModelClusterPanel.this);
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
					int i = selectedFile.showOpenDialog(DataModelClusterPanel.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File data = selectedFile.getSelectedFile();
						String absPath = data.getAbsolutePath();
						textInputPath.setText(absPath);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error openig the file.", Constants.Log.ERROR);
						log.error("Error opening data file");
					}
				}
			}
		});

		btnSelectOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainGUI.isDistributed()) {
					FolderChooser chooser = new FolderChooser();
					int returnVal = chooser.showOpenDialog(DataModelClusterPanel.this);
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
					int i = selectedFile.showOpenDialog(DataModelClusterPanel.this);
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
				String delimiter = tfDelimiter.getText(); //delimiter
				outputFormatted = output+".csv";
				tfDelimiter.setBackground(Color.WHITE);
				if (!filePath.isEmpty() && !output.isEmpty()){
				if (ClusterBuilder.isSequential()) {
					CreateSequenceFile.convert(filePath, output, delimiter);
					ReadSequenceFile.readSequenceFile(output, outputFormatted);
					try {
						ClusterBuilder.buildCluster();
						
						//dibuja();
					} catch (ClassNotFoundException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (InterruptedException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (IOException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					}

				}
				else {
					try {
						ClusterBuilder.buildCluster();
					} catch (ClassNotFoundException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (InterruptedException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (IOException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					}
				}
				
			}
				else MainGUI.writeResult("You have to specify both input and output source file!", Constants.Log.ERROR);
				
			//mouse finished loading
				btnCreate.setCursor(Cursor.getDefaultCursor());
				
			}
			});
		

		

		chckbxBooleanPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					comboBoxDatamodel.setModel(booleanModels);
					lblDelimiter.setEnabled(false);
					tfDelimiter.setEnabled(false);
					log.info("boolean");
					EvaluatorRecommenderPanel.setBooleanPreferences(true);
				} else {
					comboBoxDatamodel.setModel(restModels);
					EvaluatorRecommenderPanel.setBooleanPreferences(false);
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

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		DataModelClusterPanel.dataModel = dataModel;
	}

	public void setDistributed(boolean distributed) {

		if (distributed) {
			helpTooltip.setText(ClusterTips.CLUSTER_DATAMODEL_DIST);
			lblOutputDataSource.setText("Output directory:");
			lblInputDataSource.setText("Input directory:");
			btnSelectOutput.setText("Select Folder...");
			btnSelectInput.setText("Select Folder...");
			
		} else {
			helpTooltip.setText(ClusterTips.CLUSTER_DATAMODEL);
			lblOutputDataSource.setText("Output data file (Optional):");
			lblInputDataSource.setText("Input data source:");
			btnSelectOutput.setText("Select File...");
			btnSelectInput.setText("Select File...");
		}

		comboBoxDatamodel.setEnabled(!distributed);
		tfDelimiter.setEnabled(!distributed);
		
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

	public static String getOutputFormatted() {
		return outputFormatted;
	}

	public static void setOutputFormatted(String outputFormatted) {
		DataModelClusterPanel.outputFormatted = outputFormatted;
	}
	public static void push(){
		btnCreate.doClick();
	}
	public static void dibuja(){
	/*	double[] x = {0.5,0.9,7};
		double[] y = {30,9,8};
		double[] z= {2,4,8};*/
		double [][] matriz = null;
		HashMap<IntWritable,WeightedVectorWritable> dots= ClusterBuilder.getPuntos();
		// create your PlotPanel (you can use it as a JPanel)
		Plot3DPanel plot = new Plot3DPanel();
		Set<IntWritable> points = dots.keySet();
		for (int i=0;i<points.size();i++){
		// add a line plot to the PlotPanel
			
		plot.addGridPlot("", Color.black, matriz);	
		//plot.addLinePlot("my plot", x, y,z);
		
		}
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Clusters");
		frame.setSize(500, 500);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
}
