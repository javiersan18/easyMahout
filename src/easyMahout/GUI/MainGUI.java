package easyMahout.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import easyMahout.GUI.classification.MainClassifierPanel;
import easyMahout.GUI.clustering.MainClusterPanel;
import easyMahout.GUI.clustering.builder.ClusterBuilder;
import easyMahout.GUI.recommender.MainRecommenderPanel;
import easyMahout.recommender.RecommenderXMLPreferences;
import easyMahout.utils.Constants;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static MainRecommenderPanel recommenderTab;

	private JTabbedPane tabbedPane;

	private MainClusterPanel clusterTab;

	private MainClassifierPanel classificationTab;

	private JScrollPane logScrollPane;

	private static JTextPane logTextPane;

	private static StringBuilder textBuilder;

	private static boolean distributed;

	private static MainGUI main;

	private static JMenuItem mntmSave;

	private final static Logger log = Logger.getLogger(MainGUI.class);

	private static JRadioButtonMenuItem nonDistributedMenuItem;

	private static JRadioButtonMenuItem distributedMenuItem;
	
	private static boolean canopy;
	
	private long time;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System(Windows, Mac, linux) Look and fell
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); 
			
		} catch (Exception e) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.setVisible(true);					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		super();
		setResizable(false);
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/easyMahout/log4j.properties");

		distributed = false;
		main = this;

		this.setTitle("easyMahout " + Constants.EasyMahout.VERSION);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/easyMahout/GUI/images/mahoutIcon45.png")));
		
		this.setBounds(100, 100, 740, 690);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		textBuilder = new StringBuilder();

		createMenuBar();

		logScrollPane = new JScrollPane();
		logScrollPane.setBounds(2, 463, 730, 176);
		this.getContentPane().add(logScrollPane);

		// Result log textField
		setLogTextPane(new JTextPane());
		logScrollPane.setViewportView(getLogTextPane());
		getLogTextPane().setBackground(Color.WHITE);
		getLogTextPane().setBounds(42, 501, 735, 131);
		getLogTextPane().setEditable(false);
		getLogTextPane().setContentType("text/html");

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(2, 0, 732, 463);
		this.getContentPane().add(tabbedPane);

		recommenderTab = new MainRecommenderPanel();
		tabbedPane.addTab("Recommendation", null, recommenderTab, null);

		classificationTab = new MainClassifierPanel();
		tabbedPane.addTab("Classification", null, classificationTab, null);

		clusterTab = new MainClusterPanel();
		tabbedPane.addTab("Clustering", null, clusterTab, null);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onClose();
			}
		});

		// RecommenderXMLPreferences.saveXMLFile();
		// RecommenderXMLPreferences.loadXMLFile();

	}

	public static void writeResult(String text, String type) {
		// TODO: poner la hora a los logs, scroll o popup para ver los
		// resultados comodamente.
		if (type.equalsIgnoreCase(Constants.Log.ERROR)) {
			textBuilder.append("<font color=red>ERROR: ").append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.WARNING)) {
			textBuilder.append("<font color=yellow>WARNING: ").append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.RESULT)) {
			textBuilder.append("<font color=black>RESULT: ").append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.INFO)) {
			textBuilder.append("<font color=green>INFO: ").append(text).append("</font><br>");
		}

		getLogTextPane().setText(textBuilder.toString());

	}

	private void onClose() {
		// Hacer clase padre comun de las clases main, para llamar genericamente
		// al panel actual
		if (recommenderTab.isControlModified() && recommenderTab.isConfigurationModified()) {
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"The actual configuration is not saved, would yo like to save it?",
					"Recommender preferences",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				// modified configuration
				if (StringUtils.isBlank(recommenderTab.getActiveConfigutation())) {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(MainGUI.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File prefs = selectedFile.getSelectedFile();
						String absPath = prefs.getAbsolutePath();
						RecommenderXMLPreferences.saveXMLFile(absPath);
						MainGUI.writeResult("Preferences file saved as: " + prefs.getName(), Constants.Log.INFO);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error saving the file", Constants.Log.ERROR);
						log.error("Error saving preferences file");
					}
					MainRecommenderPanel.setConfigurationModified(false);
					// crear fichero (jfilechooser)
					// salvar config en fichero
					// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
				} else {
					// salvar config en fichero
					RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
					MainRecommenderPanel.setConfigurationModified(false);
				}
			} else if (dialogResult == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		} else {
			
			System.exit(0);
		}
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mnItemExit = new JMenuItem("Exit");
		mnItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		JMenuItem mntmLoad = new JMenuItem("Load...");
		mnFile.add(mntmLoad);
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				// TODO hacer directorio default saves
				int i = selectedFile.showOpenDialog(MainGUI.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File prefs = selectedFile.getSelectedFile();
					String absPath = prefs.getAbsolutePath();
					RecommenderXMLPreferences.loadXMLFile(absPath);
					// MainGUI.setMainTitle(absPath);
					mntmSave.setEnabled(true);
					MainGUI.writeResult("Preferences file loaded: " + prefs.getName(), Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error loading the file", Constants.Log.ERROR);
					log.error("Error loading preferences file");
				}
			}
		});

		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.setEnabled(false);
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
				MainGUI.writeResult("Preferences file saved", Constants.Log.INFO);
			}
		});

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(MainGUI.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File prefs = selectedFile.getSelectedFile();
					String absPath = prefs.getAbsolutePath();
					RecommenderXMLPreferences.saveXMLFile(absPath);
					// MainGUI.setMainTitle(absPath);
					mntmSave.setEnabled(true);
					MainGUI.writeResult("Preferences file saved as: " + prefs.getName(), Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error saving the file", Constants.Log.ERROR);
					log.error("Error saving preferences file");
				}
			}
		});
		
		JSeparator separator_3 = new JSeparator();
		mnFile.add(separator_3);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mnFile.add(mntmPreferences);

		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		mnFile.add(mnItemExit);

		JMenu mnProperties = new JMenu("Properties");
		menuBar.add(mnProperties);

		ButtonGroup distributedGroup = new ButtonGroup();

		nonDistributedMenuItem = new JRadioButtonMenuItem("Non Distributed", true);
		mnProperties.add(nonDistributedMenuItem);
		distributedGroup.add(nonDistributedMenuItem);
		nonDistributedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recommenderTab.isControlModified() && recommenderTab.isConfigurationModified()) {
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"The actual configuration is not saved, would yo like to save it?",
							"Non distributed recommender preferences",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						// modified configuration
						if (StringUtils.isBlank(recommenderTab.getActiveConfigutation())) {
							JFileChooser selectedFile = new JFileChooser();
							int i = selectedFile.showOpenDialog(MainGUI.this);
							if (i == JFileChooser.APPROVE_OPTION) {
								File prefs = selectedFile.getSelectedFile();
								String absPath = prefs.getAbsolutePath();
								RecommenderXMLPreferences.saveXMLFile(absPath);
								MainGUI.writeResult("Preferences file saved as: " + prefs.getName(), Constants.Log.INFO);
							} else if (i == JFileChooser.ERROR_OPTION) {
								MainGUI.writeResult("Error saving the file", Constants.Log.ERROR);
								log.error("Error saving preferences file");
							}
							// crear fichero (jfilechooser)
							// salvar config en fichero
							// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
							setDistributed();
							MainRecommenderPanel.setConfigurationModified(false);
						} else {
							// salvar config en fichero
							// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
							setDistributed();
							MainRecommenderPanel.setConfigurationModified(false);
						}
					} else if (dialogResult == JOptionPane.NO_OPTION) {
						setDistributed();
						MainRecommenderPanel.setConfigurationModified(false);
					}
				} else {
					setDistributed();
					MainRecommenderPanel.setConfigurationModified(false);
				}
			}

			private void setDistributed() {
				// Change preferences
				distributed = false;
				// recommenderTab = new MainRecommenderPanel();
				recommenderTab.setDistributed(distributed);
				clusterTab.setDistributed(distributed);
				// stop hadoop
				ClusterBuilder.setHadoop(distributed);
			}

		});

		distributedMenuItem = new JRadioButtonMenuItem("Distributed (Apache Hadoop)");
		mnProperties.add(distributedMenuItem);
		distributedGroup.add(distributedMenuItem);
		distributedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Change preferences for job
				// recommenderTab = new MainRecommenderPanel();
				distributed = true;
				recommenderTab.setDistributed(distributed);
				clusterTab.setDistributed(distributed);
				ClusterBuilder.setHadoop(distributed);
				// Start Apache Hadoop
			}
		});

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mnItemAbout = new JMenuItem("About EasyMahout");
		mnItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				AboutUsPopupDialogBox dialogBox = new AboutUsPopupDialogBox();
			}
		});

		JMenuItem mntmHelpContents = new JMenuItem("Help Contents");
		mntmHelpContents.setIcon(new ImageIcon(MainGUI.class.getResource("/easyMahout/GUI/images/helpIcon32.png")));
		mnHelp.add(mntmHelpContents);

		JSeparator separator_1 = new JSeparator();
		mnHelp.add(separator_1);
		mnHelp.add(mnItemAbout);

	}

	public static boolean isDistributed() {
		return distributed;
	}

	public static void setDistributed(boolean distributed) {
		MainGUI.distributed = distributed;
	}

	public static void setMainTitle(String path) {
		main.setTitle("easyMahout " + Constants.EasyMahout.VERSION + " - " + path);
	}

	public static void setSaveItemEnabled(boolean enabled) {
		mntmSave.setEnabled(enabled);
	}

	public static void setDistributedRecommPanel(boolean distributed) {
		recommenderTab.setDistributed(distributed);
		distributedMenuItem.setSelected(distributed);
		nonDistributedMenuItem.setSelected(!distributed);
	}
	
	public static boolean isCanopy() {
		return canopy;
	}
	
	public static void setCanopy(boolean c){
		canopy=c;
	}
	
	public static void limpiar(){
		main(null);
	}

	public static JTextPane getLogTextPane() {
		return logTextPane;
	}

	public static void setLogTextPane(JTextPane logTextPane) {
		MainGUI.logTextPane = logTextPane;
	}
	
public static void clean(){
	logTextPane.setText(Constants.Log.EMPTY);
}

}
