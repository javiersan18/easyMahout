package easyMahout.GUI.recommender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.recommender.RecommenderXMLPreferences;
import easyMahout.utils.Constants;
import easyMahout.utils.DisabledNode;
import easyMahout.utils.DisabledRenderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelRecommender;

	private JPanel treePanel;

	private static ConfigureRecommenderPanel configPanel;

	private static TypeRecommenderPanel typePanel;

	private static DataModelRecommenderPanel dataModelPanel;

	private static SimilarityRecommenderPanel similarityPanel;

	private static NeighborhoodRecommenderPanel neighborhoodPanel;

	private QueriesRecommenderPanel queriesPanel;

	private EvaluatorRecommenderPanel evaluatorPanel;

	private JobRecommenderPanel jobPanel;

	private FactorizerRecommenderPanel factorizerPanel;

	private static JTree treeMenu;

	private final static Logger log = Logger.getLogger(MainRecommenderPanel.class);

	private static boolean itembased;

	private ArrayList<DisabledNode> treeNodes;

	private static DisabledNode nodeSimilarity;

	private static DisabledNode nodeNeighborhood;

	private static DisabledNode nodeEvaluator;

	private static DisabledNode nodeQueries;

	private static DisabledNode nodeSaves;

	private static DisabledNode nodeRoot;

	private static DisabledNode nodeConfigure;

	private static DisabledNode nodeSelected;

	private DisabledNode nodeJob;

	private static DisabledNode nodeFactorization;

	private static boolean configurationModified;

	private boolean controlModified;

	private String activeConfigutation;

	private static boolean matrixBased;

	private static String fileName;

	public MainRecommenderPanel() {

		this.setLayout(null);

		panelRecommender = this;

		itembased = false;
		matrixBased = false;
		configurationModified = false;
		activeConfigutation = "";
		fileName = "";
		controlModified = false;

		treeMenu = new JTree(populateTree()[0]);

		DisabledRenderer renderer = new DisabledRenderer();
		treeMenu.setCellRenderer(renderer);

		treeMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		treePanel = new JPanel();
		treePanel.setBorder(null);
		treePanel.setBounds(20, 11, 202, 410);

		treeMenu.setBounds(0, 0, 202, 410);
		treeMenu.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Options", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		treeMenu.setRootVisible(false);
		treeMenu.setShowsRootHandles(true);
		treeMenu.expandRow(0);

		treePanel.setLayout(null);
		treePanel.add(treeMenu);

		nodeRoot = (DisabledNode) treeMenu.getModel().getRoot();

		this.add(treePanel);

		// Create different panes

		configPanel = new ConfigureRecommenderPanel();
		configPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(configPanel);
		configPanel.setLayout(null);
		configPanel.setVisible(true);

		typePanel = new TypeRecommenderPanel();
		typePanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(typePanel);
		typePanel.setLayout(null);
		typePanel.setVisible(false);

		dataModelPanel = new DataModelRecommenderPanel();
		dataModelPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(dataModelPanel);
		dataModelPanel.setLayout(null);
		dataModelPanel.setVisible(false);

		similarityPanel = new SimilarityRecommenderPanel();
		similarityPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(similarityPanel);
		similarityPanel.setLayout(null);
		similarityPanel.setVisible(false);

		neighborhoodPanel = new NeighborhoodRecommenderPanel();
		neighborhoodPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(neighborhoodPanel);
		neighborhoodPanel.setLayout(null);
		neighborhoodPanel.setVisible(false);

		factorizerPanel = new FactorizerRecommenderPanel();
		factorizerPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(factorizerPanel);
		factorizerPanel.setLayout(null);
		factorizerPanel.setVisible(false);

		evaluatorPanel = new EvaluatorRecommenderPanel();
		evaluatorPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(evaluatorPanel);
		evaluatorPanel.setLayout(null);
		evaluatorPanel.setVisible(false);

		queriesPanel = new QueriesRecommenderPanel();
		queriesPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(queriesPanel);
		queriesPanel.setLayout(null);
		queriesPanel.setVisible(false);

		jobPanel = new JobRecommenderPanel();
		jobPanel.setBounds(228, 11, 481, 410);
		panelRecommender.add(jobPanel);
		jobPanel.setLayout(null);
		jobPanel.setVisible(false);

	}

	public JPanel getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(JPanel treePanel) {
		this.treePanel = treePanel;
	}

	private DisabledNode[] populateTree() {

		String[] categories = { "Root", // 0
				"Configure", // 1
				"Type", // 2
				"Data Model", // 3
				"Similarity", // 4
				"Neighborhood", // 5
				"Factorization", // 6
				"Evaluator", // 7
				"Queries", // 8
				"Hadoop Job", // 9
				"Saves", // 10
		};

		treeNodes = new ArrayList<DisabledNode>();
		for (int i = 0; i < categories.length; i++) {
			treeNodes.add(new DisabledNode(categories[i]));
		}

		treeNodes.get(0).add(treeNodes.get(1));
		treeNodes.get(1).add(treeNodes.get(2));
		treeNodes.get(1).add(treeNodes.get(3));
		treeNodes.get(1).add(treeNodes.get(4));
		treeNodes.get(1).add(treeNodes.get(5));
		treeNodes.get(1).add(treeNodes.get(6));
		treeNodes.get(1).add(treeNodes.get(7));
		treeNodes.get(1).add(treeNodes.get(8));
		treeNodes.get(1).add(treeNodes.get(9));
		treeNodes.get(0).add(treeNodes.get(10));

		nodeNeighborhood = treeNodes.get(5);
		nodeQueries = treeNodes.get(8);
		nodeEvaluator = treeNodes.get(7);
		nodeSimilarity = treeNodes.get(4);
		nodeJob = treeNodes.get(9);
		nodeJob.setEnabled(false);
		nodeFactorization = treeNodes.get(6);
		nodeFactorization.setEnabled(false);
		nodeSaves = treeNodes.get(10);
		nodeConfigure = treeNodes.get(1);

		ArrayList<DisabledNode> savesNodes = getSavesFiles();
		if (savesNodes != null) {
			treeNodes.addAll(savesNodes);
			for (int i = categories.length; i < treeNodes.size(); i++) {
				nodeSaves.add(treeNodes.get(i));
			}
		}

		return treeNodes.toArray(new DisabledNode[treeNodes.size()]);
	}

	// Mouse Events in JTree nodes
	void doMouseClicked(MouseEvent me) {
		// Left button
		if (me.getButton() == MouseEvent.BUTTON1) {
			try {

				nodeSelected = (DisabledNode) treeMenu.getPathForLocation(me.getX(), me.getY()).getLastPathComponent();

				if (nodeSelected != null) {
					if (nodeSelected.equals(nodeConfigure)) {
						log.info("configureNode");
						configPanel.setVisible(true);
						typePanel.setVisible(false);
						dataModelPanel.setVisible(false);
						similarityPanel.setVisible(false);
						neighborhoodPanel.setVisible(false);
						factorizerPanel.setVisible(false);
						evaluatorPanel.setVisible(false);
						queriesPanel.setVisible(false);
						disableHelpTips();
					} else if (nodeConfigure.isNodeChild(nodeSelected)) {
						log.info("recomender configure  children B1");

						String category = (String) nodeSelected.getUserObject();
						if (category.equals("Type")) {
							log.info("typeB1");
							configPanel.setVisible(false);
							typePanel.setVisible(true);
							dataModelPanel.setVisible(false);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(false);
							factorizerPanel.setVisible(false);
							evaluatorPanel.setVisible(false);
							queriesPanel.setVisible(false);
							jobPanel.setVisible(false);
							disableHelpTips();

						} else if (category.equals("Data Model")) {
							log.info("dataB1");
							configPanel.setVisible(false);
							typePanel.setVisible(false);
							dataModelPanel.setVisible(true);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(false);
							factorizerPanel.setVisible(false);
							evaluatorPanel.setVisible(false);
							queriesPanel.setVisible(false);
							jobPanel.setVisible(false);
							disableHelpTips();

						} else if (category.equals("Similarity")) {
							log.info("similarityB1");
							log.info(MainGUI.isDistributed());
							if (!MainGUI.isDistributed() || (MainGUI.isDistributed() && !matrixBased)) {
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(true);
								neighborhoodPanel.setVisible(false);
								factorizerPanel.setVisible(false);
								evaluatorPanel.setVisible(false);
								queriesPanel.setVisible(false);
								jobPanel.setVisible(false);
								disableHelpTips();
							}
						} else if (category.equals("Neighborhood")) {
							log.info(MainGUI.isDistributed());
							if (!MainGUI.isDistributed() && !itembased) {
								log.info("neighB1");
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(false);
								neighborhoodPanel.setVisible(true);
								factorizerPanel.setVisible(false);
								evaluatorPanel.setVisible(false);
								queriesPanel.setVisible(false);
								jobPanel.setVisible(false);
								disableHelpTips();
							}

						} else if (category.equals("Factorization")) {
							if (matrixBased) {
								log.info("neighB1");
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(false);
								neighborhoodPanel.setVisible(false);
								factorizerPanel.setVisible(true);
								evaluatorPanel.setVisible(false);
								queriesPanel.setVisible(false);
								jobPanel.setVisible(false);
								disableHelpTips();
							}
						} else if (category.equals("Evaluator")) {
							if (!MainGUI.isDistributed() && !matrixBased) {
								log.info("evalB1");
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(false);
								neighborhoodPanel.setVisible(false);
								factorizerPanel.setVisible(false);
								evaluatorPanel.setVisible(true);
								queriesPanel.setVisible(false);
								jobPanel.setVisible(false);
								disableHelpTips();
							}

						} else if (category.equals("Queries")) {
							log.info("queriesB1");
							if (!MainGUI.isDistributed()) {
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(false);
								neighborhoodPanel.setVisible(false);
								factorizerPanel.setVisible(false);
								evaluatorPanel.setVisible(false);
								queriesPanel.setVisible(true);
								jobPanel.setVisible(false);
								disableHelpTips();
							}
						}

						else if (category.equals("Hadoop Job")) {
							if (MainGUI.isDistributed()) {
								log.info("jobB1");
								configPanel.setVisible(false);
								typePanel.setVisible(false);
								dataModelPanel.setVisible(false);
								similarityPanel.setVisible(false);
								neighborhoodPanel.setVisible(false);
								factorizerPanel.setVisible(false);
								evaluatorPanel.setVisible(false);
								queriesPanel.setVisible(false);
								jobPanel.setVisible(true);
								disableHelpTips();
							}
						}
					}
				}
			} catch (Exception e1) {
				// The place where we clicked is not a tree node, do nothing.
			}
		}
		// Right button
		if (me.getButton() == MouseEvent.BUTTON3) {
			try {

				nodeSelected = (DisabledNode) treeMenu.getPathForLocation(me.getX(), me.getY()).getLastPathComponent();

				if (nodeSelected != null) {
					if (nodeSelected.equals(nodeSaves)) {
						log.info("node: " + nodeSelected.toString());
						JPopupMenu popupMenuAdd = new JPopupMenu();
						JMenuItem addItem = new JMenuItem("Add");
						addItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (configurationModified) {
									int dialogResult = JOptionPane.showConfirmDialog(null,
											"The actual Recommender configuration is not saved, would yo like to save it?",
											"Save preferences",
											JOptionPane.YES_NO_CANCEL_OPTION);
									if (dialogResult == JOptionPane.YES_OPTION) {
										if (StringUtils.isBlank(activeConfigutation)) {
											// TODO poner carpeta saves por
											// defecto en el chooser
											JFileChooser selectedFile = new JFileChooser();
											int i = selectedFile.showOpenDialog(MainRecommenderPanel.this);
											if (i == JFileChooser.APPROVE_OPTION) {
												File prefs = selectedFile.getSelectedFile();
												String absPath = prefs.getAbsolutePath();
												RecommenderXMLPreferences.saveXMLFile(absPath);
												MainGUI.writeResult("Preferences file saved as: " + prefs.getName(), Constants.Log.INFO);
											} else if (i == JFileChooser.ERROR_OPTION) {
												MainGUI.writeResult("Error saving the file", Constants.Log.ERROR);
												log.error("Error saving preferences file");
											}
											// y nuevo
											addPreferencesFile();
											log.debug("modified, default config");
										} else {
											// salvar y nuevo
											RecommenderXMLPreferences.saveXMLFile(activeConfigutation);
											// configurationNew = true;
											addPreferencesFile();
											log.debug("modified, saving added configuration");
										}
									} else if (dialogResult == JOptionPane.NO_OPTION) {
										// nuevo directamente
										// configurationNew = true;
										addPreferencesFile();
										log.debug("modified, no save");
									}
								} else {
									// añadir directamente
									// configurationNew = true;
									addPreferencesFile();
									log.debug("no modified");
								}
								treeMenu.expandRow(8);
							}
						});

						popupMenuAdd.add(addItem);
						this.add(popupMenuAdd);
						popupMenuAdd.show(me.getComponent(), me.getX(), me.getY());

					} else if (nodeSaves.isNodeChild(nodeSelected)) {
						log.info("node: " + nodeSelected.toString());
						JPopupMenu popupMenuSaves = new JPopupMenu();
						JMenuItem loadItem = new JMenuItem("Load");
						loadItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String filePath = ((DisabledNode) nodeSelected).getPathFile();
								RecommenderXMLPreferences.loadXMLFile(filePath);
								activeConfigutation = filePath;
								configurationModified = false;
								controlModified = true;
								MainGUI.setSaveItemEnabled(true);
							}
						});

						JMenuItem deleteItem = new JMenuItem("Delete");
						deleteItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String name = (String) nodeSelected.getUserObject();
								String filePath = ((DisabledNode) nodeSelected).getPathFile();
								int dialogButton = JOptionPane.YES_NO_OPTION;
								int dialogResult = JOptionPane.showConfirmDialog(null,
										"Would you like to delete \"" + name + "\"?",
										"Delete File",
										dialogButton);
								if (dialogResult == JOptionPane.YES_OPTION) {
									File fichero = new File(filePath);
									if (fichero.delete()) {

										treeNodes.remove(nodeSelected);
										nodeSaves.remove(nodeSelected);

										DefaultTreeModel model = new DefaultTreeModel(nodeRoot);

										treeMenu.setModel(model);
										treeMenu.expandRow(0);
										treeMenu.expandRow(8);

										if (filePath.equals(activeConfigutation)) {
											activeConfigutation = "";
											MainGUI.setMainTitle(activeConfigutation);
											MainGUI.setSaveItemEnabled(false);
											configurationModified = false;
											controlModified = false;
										}
										MainGUI.writeResult("Preferences file \"" + name + "\" successfully deleted.", Constants.Log.INFO);

									} else {
										MainGUI.writeResult("Preferences file \"" + name + "\" cannot be deleted.", Constants.Log.ERROR);
									}
								}
							}
						});

						popupMenuSaves.add(loadItem);
						popupMenuSaves.add(deleteItem);

						this.add(popupMenuSaves);
						popupMenuSaves.show(me.getComponent(), me.getX(), me.getY());

					}
				}
			} catch (Exception e1) {
				// The place where we clicked is not a tree node, do nothing.
			}
		}
	}

	private void disableHelpTips() {
		typePanel.getHelpTooltip().disable();
		dataModelPanel.getHelpTooltip().disable();
		similarityPanel.getHelpTooltip().disable();
		neighborhoodPanel.getHelpTooltip().disable();
		factorizerPanel.getHelpTooltip().disable();
		evaluatorPanel.getHelpTooltip().disable();
		queriesPanel.getHelpTooltip().disable();
		jobPanel.getHelpTooltip().disable();
	}

	public void setDistributed(boolean distributed) {
		nodeNeighborhood.setEnabled(!distributed);
		nodeFactorization.setEnabled(!distributed);
		nodeEvaluator.setEnabled(!distributed);
		nodeQueries.setEnabled(!distributed);
		nodeJob.setEnabled(distributed);
		treeMenu.repaint();
		typePanel.setDistributed(distributed);
		factorizerPanel.setDistributed(distributed);
		dataModelPanel.setDistributed(distributed);
		similarityPanel.setDistributed(distributed);
		configPanel.setDistributed(distributed);
		this.setConfigPanelEnabled();
	}

	private void setConfigPanelEnabled() {
		configPanel.setVisible(true);
		typePanel.setVisible(false);
		dataModelPanel.setVisible(false);
		similarityPanel.setVisible(false);
		neighborhoodPanel.setVisible(false);
		factorizerPanel.setVisible(false);
		evaluatorPanel.setVisible(false);
		queriesPanel.setVisible(false);
		jobPanel.setVisible(false);
		disableHelpTips();
	}

	private ArrayList<DisabledNode> getSavesFiles() {

		File dir = new File(Constants.SavesPaths.RECOMMENDER);

		if (dir.exists()) {
			File[] files = dir.listFiles();
			ArrayList<DisabledNode> nodes = new ArrayList<DisabledNode>();
			for (int i = 0; i < files.length; i++) {
				nodes.add(new DisabledNode(RecommenderXMLPreferences.getTagName(files[i].getPath()), files[i].getPath()));
				System.out.println(files[i].getName());

			}
			return nodes;

		} else {
			log.error("Preferences folder (" + Constants.SavesPaths.RECOMMENDER + ") doesnt exist.");
			return null;
		}

	}

	private void addPreferencesFile() {
		fileName = JOptionPane.showInputDialog(null, "Write a new preferences file name?", "Enter a name", JOptionPane.QUESTION_MESSAGE);

		if (fileName != null && !fileName.isEmpty()) {

			File directory = new File(Constants.SavesPaths.RECOMMENDER);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String filePath = Constants.SavesPaths.RECOMMENDER + fileName + Constants.SavesPaths.EXTENSION;

			activeConfigutation = filePath;
			configurationModified = false;
			controlModified = true;

			treeNodes.add(new DisabledNode(fileName, filePath));
			nodeSaves.add(treeNodes.get(treeNodes.size() - 1));
			DefaultTreeModel model = new DefaultTreeModel(nodeRoot);

			File file = new File(filePath);
			try {
				if (file.createNewFile())
					System.out.println("El fichero se ha creado correctamente");
				else
					System.out.println("No ha podido ser creado el fichero");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			treeMenu.setModel(model);
			treeMenu.expandRow(0);
			treeMenu.expandRow(9);

			MainGUI.setMainTitle(activeConfigutation);
			MainGUI.setSaveItemEnabled(true);
			MainGUI.writeResult("Preferences file added: " + fileName, Constants.Log.INFO);

		}
	}

	public static TypeRecommenderPanel getTypePanel() {
		return typePanel;
	}

	public static SimilarityRecommenderPanel getSimilarityPanel() {
		return similarityPanel;
	}

	public static void setEnableNeighborhood(boolean enabled) {
		nodeNeighborhood.setEnabled(enabled);
		itembased = !enabled;
		treeMenu.repaint();
	}

	public static void setEnableFactorization(boolean enabled) {
		nodeFactorization.setEnabled(enabled);
		matrixBased = enabled;
		treeMenu.repaint();
	}

	public boolean isConfigurationModified() {
		return configurationModified;
	}

	public boolean isControlModified() {
		return controlModified;
	}

	public static void setConfigurationModified(boolean configurationModified) {
		MainRecommenderPanel.configurationModified = configurationModified;
	}

	public String getActiveConfigutation() {
		return activeConfigutation;
	}

	public void setActiveConfigutation(String activeConfigutation) {
		this.activeConfigutation = activeConfigutation;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setEnableEvaluator(boolean enabled) {
		nodeEvaluator.setEnabled(enabled);
		treeMenu.repaint();
	}

	public static void setEnableSimilarity(boolean enabled) {
		nodeSimilarity.setEnabled(enabled);
		treeMenu.repaint();
	}

}
