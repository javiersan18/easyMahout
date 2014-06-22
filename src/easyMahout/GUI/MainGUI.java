package easyMahout.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import org.apache.commons.lang.StringUtils;

import easyMahout.GUI.classification.MainClassifierPanel;
import easyMahout.GUI.clustering.MainClusterPanel;
import easyMahout.GUI.clustering.builder.ClusterBuilder;
import easyMahout.GUI.recommender.MainRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.xml.RecommenderXMLPreferences;

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

	// private final static Logger log = Logger.getLogger(MainGUI.class);

	private static JRadioButtonMenuItem nonDistributedMenuItem;

	private static JRadioButtonMenuItem distributedMenuItem;

	private static JFrame preferencesPanel;

	private static boolean canopy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System(Windows, Mac, linux) Look and fell
			// javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

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
		setResizable(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// BasicConfigurator.configure();
		// PropertyConfigurator.configure("log4j.properties");

		distributed = false;
		main = this;

		this.setTitle("easyMahout " + Constants.EasyMahout.VERSION);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource(
						"/easyMahout/GUI/images/mahoutIcon45.png")));

		this.setBounds(100, 100, 735, 690);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		textBuilder = new StringBuilder();

		createMenuBar();

		logScrollPane = new JScrollPane();
		logScrollPane.setBounds(2, 463, 733, 200);
		this.getContentPane().add(logScrollPane);

		// Result log textField
		setLogTextPane(new JTextPane());
		logScrollPane.setViewportView(getLogTextPane());
		getLogTextPane().setBackground(Color.WHITE);
		getLogTextPane().setBounds(2, 463, 735, 131);
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

		preferencesPanel = new PreferencesPanel();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onClose();
			}
		});

		// RecommenderXMLPreferences.saveXMLFile();
		// RecommenderXMLPreferences.loadXMLFile();

	}

	public static void writeResult(String text, String type) {
		if (type.equalsIgnoreCase(Constants.Log.ERROR)) {
			textBuilder.append("<font color=red>ERROR: ").append(getDateTime())
					.append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.WARNING)) {
			textBuilder.append("<font color=yellow>WARNING: ")
					.append(getDateTime()).append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.RESULT)) {
			textBuilder.append("<font color=black>RESULT: ")
					.append(getDateTime()).append(text).append("</font><br>");
		} else if (type.equalsIgnoreCase(Constants.Log.INFO)) {
			textBuilder.append("<font color=green>INFO: ")
					.append(getDateTime()).append(text).append("</font><br>");
		}

		getLogTextPane().setText(textBuilder.toString());
		main.update(main.getGraphics());
	}

	public static void writeResultClassifier(String text) {
		textBuilder.append("<font color=black>").append(text)
				.append("</font><br>");
		getLogTextPane().setText(textBuilder.toString());
		main.update(main.getGraphics());
	}

	private void onClose() {
		// Hacer clase padre comun de las clases main, para llamar genericamente
		// al panel actual
		if (recommenderTab.isControlModified()
				&& recommenderTab.isConfigurationModified()) {
			int dialogResult = JOptionPane
					.showConfirmDialog(
							null,
							"The actual configuration is not saved, would yo like to save it?",
							"Recommender preferences",
							JOptionPane.YES_NO_CANCEL_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				// modified configuration
				if (StringUtils
						.isBlank(recommenderTab.getActiveConfigutation())) {
					JFileChooser selectedFile = new JFileChooser();
					int i = selectedFile.showOpenDialog(MainGUI.this);
					if (i == JFileChooser.APPROVE_OPTION) {
						File prefs = selectedFile.getSelectedFile();
						String absPath = prefs.getAbsolutePath();
						RecommenderXMLPreferences.saveXMLFile(absPath);
						MainGUI.writeResult("Preferences file saved as: "
								+ prefs.getName(), Constants.Log.INFO);
					} else if (i == JFileChooser.ERROR_OPTION) {
						MainGUI.writeResult("Error saving the file",
								Constants.Log.ERROR);
					}
					MainRecommenderPanel.setConfigurationModified(false);
					// crear fichero (jfilechooser)
					// salvar config en fichero
					// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
				} else {
					// salvar config en fichero
					RecommenderXMLPreferences.saveXMLFile(recommenderTab
							.getActiveConfigutation());
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
					MainGUI.writeResult(
							"Preferences file loaded: " + prefs.getName(),
							Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error loading the file",
							Constants.Log.ERROR);
				}
			}
		});
		mntmLoad.setVisible(false);

		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.setEnabled(false);
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecommenderXMLPreferences.saveXMLFile(recommenderTab
						.getActiveConfigutation());
				MainGUI.writeResult("Preferences file saved",
						Constants.Log.INFO);
			}
		});
		mntmSave.setVisible(false);

		JMenuItem mntmSaveAs = new JMenuItem("Save Log");
		mnFile.add(mntmSaveAs);
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(MainGUI.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File logFile = selectedFile.getSelectedFile();
					String absPath = logFile.getAbsolutePath();
					// RecommenderXMLPreferences.saveXMLFile(absPath);
					// MainGUI.setMainTitle(absPath);
					// mntmSave.setEnabled(true);
					saveLogAsFile(absPath);
					MainGUI.writeResult(
							"Log file saved as: " + logFile.getName(),
							Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error saving the file",
							Constants.Log.ERROR);
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

		nonDistributedMenuItem = new JRadioButtonMenuItem("Non Distributed",
				true);
		mnProperties.add(nonDistributedMenuItem);
		distributedGroup.add(nonDistributedMenuItem);
		nonDistributedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recommenderTab.isControlModified()
						&& recommenderTab.isConfigurationModified()) {
					int dialogResult = JOptionPane
							.showConfirmDialog(
									null,
									"The actual configuration is not saved, would yo like to save it?",
									"Non distributed recommender preferences",
									JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						// modified configuration
						if (StringUtils.isBlank(recommenderTab
								.getActiveConfigutation())) {
							JFileChooser selectedFile = new JFileChooser();
							int i = selectedFile.showOpenDialog(MainGUI.this);
							if (i == JFileChooser.APPROVE_OPTION) {
								File prefs = selectedFile.getSelectedFile();
								String absPath = prefs.getAbsolutePath();
								RecommenderXMLPreferences.saveXMLFile(absPath);
								MainGUI.writeResult(
										"Preferences file saved as: "
												+ prefs.getName(),
										Constants.Log.INFO);
							} else if (i == JFileChooser.ERROR_OPTION) {
								MainGUI.writeResult("Error saving the file",
										Constants.Log.ERROR);
							}
							// crear fichero (jfilechooser)
							// salvar config en fichero
							// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
							setDistributed();
							MainRecommenderPanel
									.setConfigurationModified(false);
						} else {
							// salvar config en fichero
							// RecommenderXMLPreferences.saveXMLFile(recommenderTab.getActiveConfigutation());
							setDistributed();
							MainRecommenderPanel
									.setConfigurationModified(false);
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

		distributedMenuItem = new JRadioButtonMenuItem(
				"Distributed (Apache Hadoop)");
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

		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preferencesPanel.setVisible(true);
			}
		});

		JMenuItem mntmHelpContents = new JMenuItem("Help Contents");
		mntmHelpContents.setIcon(new ImageIcon(MainGUI.class
				.getResource("/easyMahout/GUI/images/helpIcon32.png")));
		mnHelp.add(mntmHelpContents);

		JSeparator separator_1 = new JSeparator();
		mnHelp.add(separator_1);
		mnHelp.add(mnItemAbout);

	}

	protected void saveLogAsFile(String absPath) {
		FileWriter fileW = null;
		PrintWriter pw = null;
		try {
			fileW = new FileWriter(absPath);
			pw = new PrintWriter(fileW);
			pw.print(textBuilder.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (null != fileW)
					fileW.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static boolean isDistributed() {
		return distributed;
	}

	public static boolean isSequential() {
		return !distributed;
	}

	public static void setDistributed(boolean distributed) {
		MainGUI.distributed = distributed;
	}

	public static void setMainTitle(String path) {
		main.setTitle("easyMahout " + Constants.EasyMahout.VERSION + " - "
				+ path);
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

	public static void setCanopy(boolean c) {
		canopy = c;
	}

	public static void limpiar() {
		main(null);
	}

	public static JTextPane getLogTextPane() {
		return logTextPane;
	}

	public static void setLogTextPane(JTextPane logTextPane) {
		MainGUI.logTextPane = logTextPane;
	}

	public static void clean() {
		textBuilder = new StringBuilder();
	}

	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String s = dateFormat.format(date) + " ";
		return s;
	}
}
