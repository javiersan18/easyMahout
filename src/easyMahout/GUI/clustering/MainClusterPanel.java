package easyMahout.GUI.clustering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.DisabledNode;
import easyMahout.utils.DisabledRenderer;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

public class MainClusterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelCluster;

	private JPanel treePanel;
	
	private static ConfigureClusterPanel configureClusterPanel;

	private static AlgorithmClusterPanel algorithmClusterPanel;

	private static DistanceMeasurePanel distanceClusterPanel;

	private static ConvergenceTresholdPanel tresholdClusterPanel;
	
	private static NumberClusterPanel numberClusterPanel;

	private static MaxIterationsPanel maxIterationsPanel;

	private static DataModelClusterPanel dataModelClusterPanel;
	
	private static JobClusterPanel jobClusterPanel;

	private static boolean canopy;

	private static JTree treeMenu;
	
	private ArrayList<DisabledNode> treeNodes;

	private static DisabledNode nodeAlgorithm;

	private static DisabledNode nodeDistanceMeasure;
	
	private static DisabledNode nodeConvergence;

	private static DisabledNode nodeNClusters;

	private static DisabledNode nodeNIterations;

	private static DisabledNode nodeDataModel;
	
	private DisabledNode nodeJob;
	
	private static DisabledNode nodeRoot;

	private static DisabledNode nodeConfigure;

	private static DisabledNode nodeSelected;

	private final static Logger log = Logger.getLogger(MainClusterPanel.class);

	private JButton btnRun;

	private JButton btnEvaluate;

	public MainClusterPanel() {
		panelCluster = this;

		canopy = true;

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
		setLayout(null);

		treePanel.setLayout(null);
		treePanel.add(treeMenu);
		this.add(treePanel);

		// Create different panes
		configureClusterPanel = new ConfigureClusterPanel();
		configureClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(configureClusterPanel);
		configureClusterPanel.setLayout(null);
		configureClusterPanel.setVisible(true);
		
		algorithmClusterPanel = new AlgorithmClusterPanel();
		algorithmClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(algorithmClusterPanel);
		algorithmClusterPanel.setLayout(null);
		algorithmClusterPanel.setVisible(false);

		distanceClusterPanel = new DistanceMeasurePanel();
		distanceClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(distanceClusterPanel);
		distanceClusterPanel.setLayout(null);
		distanceClusterPanel.setVisible(false);
		
		
		tresholdClusterPanel = new ConvergenceTresholdPanel();
		tresholdClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(tresholdClusterPanel);
		tresholdClusterPanel.setLayout(null);
		tresholdClusterPanel.setVisible(false);

		numberClusterPanel = new NumberClusterPanel();
		numberClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(numberClusterPanel);
		numberClusterPanel.setLayout(null);
		numberClusterPanel.setVisible(false);

		maxIterationsPanel = new MaxIterationsPanel();
		maxIterationsPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(maxIterationsPanel);
		maxIterationsPanel.setLayout(null);
		maxIterationsPanel.setVisible(false);

		dataModelClusterPanel = new DataModelClusterPanel();
		dataModelClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(dataModelClusterPanel);
		dataModelClusterPanel.setLayout(null);
		dataModelClusterPanel.setVisible(false);
		
		jobClusterPanel = new JobClusterPanel();
		jobClusterPanel.setBounds(228, 11, 481, 410);
		panelCluster.add(jobClusterPanel);
		jobClusterPanel.setLayout(null);
		jobClusterPanel.setVisible(false);
	}

	public static DistanceMeasurePanel getDistanceClusterPanel() {
		return distanceClusterPanel;
	}

	public static void setDistanceClusterPanel(
			DistanceMeasurePanel distanceClusterPanel) {
		MainClusterPanel.distanceClusterPanel = distanceClusterPanel;
	}

	public static ConvergenceTresholdPanel getTresholdClusterPanel() {
		return tresholdClusterPanel;
	}

	public static void setTresholdClusterPanel(
			ConvergenceTresholdPanel tresholdClusterPanel) {
		MainClusterPanel.tresholdClusterPanel = tresholdClusterPanel;
	}

	public static NumberClusterPanel getNumberClusterPanel() {
		return numberClusterPanel;
	}

	public static void setNumberClusterPanel(NumberClusterPanel numberClusterPanel) {
		MainClusterPanel.numberClusterPanel = numberClusterPanel;
	}

	public static MaxIterationsPanel getMaxIterationsPanel() {
		return maxIterationsPanel;
	}

	public static void setMaxIterationsPanel(MaxIterationsPanel maxIterationsPanel) {
		MainClusterPanel.maxIterationsPanel = maxIterationsPanel;
	}

	public static AlgorithmClusterPanel getAlgorithmClusterPanel() {
		return algorithmClusterPanel;
	}
	
	
	
	
	
	public DisabledNode[] populateTree() {

		String[] categories = { "Root", // 0
				"Configure", // 1
				"Algorithm", // 2
				"Distance Measure", // 3
				"Convergence Treshold",//4
				"Number of Clusters", // 5
				"Maximum Iterations",// 6
				"Data Model",// 7
				"Hadoop Job"//8

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
		
		nodeJob = treeNodes.get(8);
		nodeAlgorithm = treeNodes.get(2);
		nodeDistanceMeasure = treeNodes.get(3);;
		nodeDataModel = treeNodes.get(7);
		nodeNClusters = treeNodes.get(5);
		nodeConvergence= treeNodes.get(4);
		nodeJob.setEnabled(false);
		//nodeSaves = treeNodes.get(9);
		nodeConfigure = treeNodes.get(1);

		/*ArrayList<DisabledNode> savesNodes = getSavesFiles();
		if (savesNodes != null) {
			treeNodes.addAll(savesNodes);
			for (int i = categories.length; i < treeNodes.size(); i++) {
				nodeSaves.add(treeNodes.get(i));
			}
		}*/

		return treeNodes.toArray(new DisabledNode[treeNodes.size()]);
	}

	public void doMouseClicked(MouseEvent me) {
		if (me.getButton() == MouseEvent.BUTTON1) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeMenu.getModel().getRoot();
			DefaultMutableTreeNode cluster = (DefaultMutableTreeNode) root.getFirstChild();
			try {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getPathForLocation(me.getX(), me.getY())
						.getLastPathComponent();
				if (node != null) {
					if (node.equals(root) || node.equals(cluster)) {
						log.info("root or cluster node B1");
						configureClusterPanel.setVisible(true);
						algorithmClusterPanel.setVisible(false);
						distanceClusterPanel.setVisible(false);
						tresholdClusterPanel.setVisible(false);
						numberClusterPanel.setVisible(false);
						maxIterationsPanel.setVisible(false);
						dataModelClusterPanel.setVisible(false);
						jobClusterPanel.setVisible(false);
						
					} else if (cluster.isNodeChild(node))  {
						log.info("cluster children B1");
						String category = (String) node.getUserObject();
					
						if (category.equals("Configure")) {
							log.info("configureB1");
							configureClusterPanel.setVisible(true);
							algorithmClusterPanel.setVisible(false);
							distanceClusterPanel.setVisible(false);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(false);
							maxIterationsPanel.setVisible(false);
							dataModelClusterPanel.setVisible(false);
							jobClusterPanel.setVisible(false);
						} else if (category.equals("Algorithm")) {
							log.info("algorithmB1");
							if (!MainGUI.isDistributed()) {
							configureClusterPanel.setVisible(false);
							algorithmClusterPanel.setVisible(true);
							distanceClusterPanel.setVisible(false);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(false);
							maxIterationsPanel.setVisible(false);
							dataModelClusterPanel.setVisible(false);
							jobClusterPanel.setVisible(false);
							}
						} else if (category.equals("Distance Measure")) {
							log.info("distanceB1");
							
							configureClusterPanel.setVisible(false);
							algorithmClusterPanel.setVisible(false);
							distanceClusterPanel.setVisible(true);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(false);
							maxIterationsPanel.setVisible(false);
							dataModelClusterPanel.setVisible(false);
							jobClusterPanel.setVisible(false);
							
						}
							else if (category.equals("Convergence Treshold")) {
								log.info("convergenceB1");
							
								configureClusterPanel.setVisible(false);
								algorithmClusterPanel.setVisible(false);
								distanceClusterPanel.setVisible(false);
								tresholdClusterPanel.setVisible(true);
								numberClusterPanel.setVisible(false);
								maxIterationsPanel.setVisible(false);
								dataModelClusterPanel.setVisible(false);
								jobClusterPanel.setVisible(false);
							
						} else if (category.equals("Number of Clusters")) {
							log.info("numberOfClustersB1");
							if ((!MainGUI.isDistributed()) /*&& (!isCanopy())*/){
							configureClusterPanel.setVisible(false);
							algorithmClusterPanel.setVisible(false);
							distanceClusterPanel.setVisible(false);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(true);
							maxIterationsPanel.setVisible(false);
							dataModelClusterPanel.setVisible(false);
							jobClusterPanel.setVisible(false);
							}
						} else if (category.equals("Maximum Iterations")) {
							log.info("numberOfIterationsB1");
							configureClusterPanel.setVisible(false);
							algorithmClusterPanel.setVisible(false);
							distanceClusterPanel.setVisible(false);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(false);
							maxIterationsPanel.setVisible(true);
							dataModelClusterPanel.setVisible(false);
							jobClusterPanel.setVisible(false);
							
						} else if (category.equals("Data Model")) {
							log.info("data Model B1");
							
							configureClusterPanel.setVisible(false);
							algorithmClusterPanel.setVisible(false);
							distanceClusterPanel.setVisible(false);
							tresholdClusterPanel.setVisible(false);
							numberClusterPanel.setVisible(false);
							maxIterationsPanel.setVisible(false);
							dataModelClusterPanel.setVisible(true);
							jobClusterPanel.setVisible(false);
							
						}else if (category.equals("Hadoop Job")) {
							if (MainGUI.isDistributed()){
								log.info("jobB1");
								configureClusterPanel.setVisible(false);
								algorithmClusterPanel.setVisible(false);
								distanceClusterPanel.setVisible(false);
								tresholdClusterPanel.setVisible(false);
								numberClusterPanel.setVisible(false);
								maxIterationsPanel.setVisible(false);
								dataModelClusterPanel.setVisible(false);
								jobClusterPanel.setVisible(true);
							}
						}
						

					}
				}
			} catch (Exception e1) {

			}

		}
		if (me.getButton() == MouseEvent.BUTTON3) {

			DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeMenu.getModel().getRoot();
			try {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getPathForLocation(me.getX(), me.getY())
						.getLastPathComponent();
				if (node != null) {
					if (node.equals(root)) {
						log.info("root");

					} else if (root.isNodeChild(node)) {
						log.info("cats");

						JPopupMenu popupMenuRoot = new JPopupMenu();

						JMenuItem _new = new JMenuItem("New");
						_new.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String category = (String) node.getUserObject();
								// TODO: iniciar panel para cada tipo;
								if (category.equals("Algorithm")) {
									log.info("Algorithm");
									

								}
							}
						});

					}
				}
			} catch (Exception e1) {
			}// try
		}// if

	}// doMouseClicked
	
	
	private void disableHelpTips() {
		algorithmClusterPanel.getHelpTooltip().disable();
		dataModelClusterPanel.getHelpTooltip().disable();
		distanceClusterPanel.getHelpTooltip().disable();
		tresholdClusterPanel.getHelpTooltip().disable();
		numberClusterPanel.getHelpTooltip().disable();
		maxIterationsPanel.getHelpTooltip().disable();
		jobClusterPanel.getHelpTooltip().disable();
	}

	public void setDistributed(boolean distributed) {
		nodeAlgorithm.setEnabled(!distributed);
		nodeDistanceMeasure.setEnabled(distributed);
		nodeDataModel.setEnabled(distributed);
		nodeConvergence.setEnabled(distributed);
		nodeNClusters.setEnabled(!distributed);
		nodeJob.setEnabled(distributed);
		treeMenu.repaint();
		this.setConfigPanelEnabled();
	}
	private void setConfigPanelEnabled() {
		configureClusterPanel.setVisible(true);
		algorithmClusterPanel.setVisible(false);
		dataModelClusterPanel.setVisible(false);
		distanceClusterPanel.setVisible(false);
		tresholdClusterPanel.setVisible(false);
		numberClusterPanel.setVisible(false);
		maxIterationsPanel.setVisible(false);
		jobClusterPanel.setVisible(false);
		disableHelpTips();
	}
	
	public static void setCanopy(boolean can) {
		canopy=can;
		//nodeAlgorithm.setEnabled(!can);
		nodeDistanceMeasure.setEnabled(can);
		nodeDataModel.setEnabled(can);
		nodeConvergence.setEnabled(can);
		nodeNClusters.setEnabled(!can);
		//nodeJob.setEnabled(can);
		treeMenu.repaint();
		//this.setConfigPanelEnabled();
	}

	public static boolean isCanopy() {
		return canopy;
	}
	
	public static void clean(){
		algorithmClusterPanel= new AlgorithmClusterPanel();
		distanceClusterPanel=new DistanceMeasurePanel();
		tresholdClusterPanel= new ConvergenceTresholdPanel();
		numberClusterPanel=new NumberClusterPanel();
		maxIterationsPanel=new MaxIterationsPanel();
		dataModelClusterPanel=new DataModelClusterPanel();
	}
}
