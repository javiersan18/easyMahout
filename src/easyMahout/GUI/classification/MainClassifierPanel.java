package easyMahout.GUI.classification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.builder.JobClassifierBuilder;
import easyMahout.GUI.recommender.JobRecommenderPanel;
import easyMahout.GUI.recommender.MainRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.DisabledNode;
import easyMahout.utils.DisabledRenderer;
import easyMahout.utils.DynamicTree;
import easyMahout.utils.xml.RecommenderXMLPreferences;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JSeparator;

public class MainClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger
			.getLogger(MainClassifierPanel.class);

	private JPanel panelClassifier;

	private JPanel treePanel;

	private static DataDefinitionsClassifierPanel dataDefinitionsClassifierPanel;

	private static DataModelClassifierPanel dataModelClassifierPanel;

	private static TrainingDataClassificationPanel trainingDataClassificationPanel;

	private static AlgorithmClassifierPanel algorithmClassifierPanel;

	private static EvaluatorClassifierPanel evaluatorClassifierPanel;
	
	private JobClassifierPanel jobPanel;

	private static boolean configurationModified;

	private boolean controlModified;

	private String activeConfigutation;

	private static String fileName;

	private static JTree treeMenu;

	private static DisabledNode nodeRoot;

	private ArrayList<DisabledNode> treeNodes;

	private DisabledNode nodeConfigure;

	private DisabledNode nodeTraining;

	private DisabledNode nodeAlgorithm;

	private DisabledNode nodeDataDefs;

	private DisabledNode nodeEvaluator;

	private DisabledNode nodeJob;

	private DisabledNode nodeSelected;

	private static ConfigureClassificationPanel configPanel;

	public MainClassifierPanel() {

		this.setLayout(null);

		panelClassifier = this;

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
		treeMenu.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0),
				1, true), "Options", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		treeMenu.setRootVisible(false);
		treeMenu.setShowsRootHandles(true);
		treeMenu.expandRow(0);

		treePanel.setLayout(null);
		treePanel.add(treeMenu);

		nodeRoot = (DisabledNode) treeMenu.getModel().getRoot();

		this.add(treePanel);

		// Create different panes

		configPanel = new ConfigureClassificationPanel();
		configPanel.setBounds(228, 11, 481, 410);
		panelClassifier.add(configPanel);
		configPanel.setLayout(null);
		configPanel.setVisible(true);

		dataDefinitionsClassifierPanel = new DataDefinitionsClassifierPanel();
		dataDefinitionsClassifierPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(dataDefinitionsClassifierPanel);
		dataDefinitionsClassifierPanel.setLayout(null);
		dataDefinitionsClassifierPanel.setVisible(false);

		dataModelClassifierPanel = new DataModelClassifierPanel();
		dataModelClassifierPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(dataModelClassifierPanel);
		dataModelClassifierPanel.setLayout(null);
		dataModelClassifierPanel.setVisible(false);

		trainingDataClassificationPanel = new TrainingDataClassificationPanel();
		trainingDataClassificationPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(trainingDataClassificationPanel);
		trainingDataClassificationPanel.setLayout(null);
		trainingDataClassificationPanel.setVisible(false);

		algorithmClassifierPanel = new AlgorithmClassifierPanel();
		algorithmClassifierPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(algorithmClassifierPanel);
		algorithmClassifierPanel.setLayout(null);
		algorithmClassifierPanel.setVisible(false);

		evaluatorClassifierPanel = new EvaluatorClassifierPanel();
		evaluatorClassifierPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(evaluatorClassifierPanel);
		evaluatorClassifierPanel.setLayout(null);
		evaluatorClassifierPanel.setVisible(false);
		
		jobPanel = new JobClassifierPanel();
		jobPanel.setBounds(228, 11, 481, 410);
		panelClassifier.add(jobPanel);
		jobPanel.setLayout(null);
		jobPanel.setVisible(false);
		
	}

	public JPanel getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(JPanel treePanel) {
		this.treePanel = treePanel;
	}

	public DisabledNode[] populateTree() {

		String[] categories = { "Root", // 0
				"Configure", // 1
				"Algorithm", // 2
				"Data Definitions", // 3
				"Training Data", // 4
				"Data Model", // 5
				"Evaluator", // 6
				"Hadoop Job" // 7
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

		nodeConfigure = treeNodes.get(1);
		nodeAlgorithm = treeNodes.get(2);
		nodeDataDefs = treeNodes.get(3);
		nodeTraining = treeNodes.get(4);
		nodeEvaluator = treeNodes.get(6);
		nodeJob = treeNodes.get(7);

		return treeNodes.toArray(new DisabledNode[treeNodes.size()]);
	}

	// /////////////////
	// MOUSE CLICK EVENT
	// /////////////////

	void doMouseClicked(MouseEvent me) {
		// Left click
		if (me.getButton() == MouseEvent.BUTTON1) {
			try {
				nodeSelected = (DisabledNode) treeMenu.getPathForLocation(
						me.getX(), me.getY()).getLastPathComponent();

				if (nodeSelected != null) {
					if (nodeSelected.equals(nodeConfigure)) {
						dataDefinitionsClassifierPanel.setVisible(false);
						dataModelClassifierPanel.setVisible(false);
						trainingDataClassificationPanel.setVisible(false);
						algorithmClassifierPanel.setVisible(false);
						evaluatorClassifierPanel.setVisible(false);
						configPanel.setVisible(true);
						jobPanel.setVisible(false);
					} else if (nodeConfigure.isNodeChild(nodeSelected)) {
						
						String category = (String) nodeSelected.getUserObject();
						
						if (category.equals("Data Definitions")) {
							dataDefinitionsClassifierPanel.setVisible(true);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							evaluatorClassifierPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Data Model")) {
							log.info("dataB1");
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(true);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							evaluatorClassifierPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Training Data")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(true);
							algorithmClassifierPanel.setVisible(false);
							evaluatorClassifierPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Algorithm")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(true);
							evaluatorClassifierPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Evaluator")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							evaluatorClassifierPanel.setVisible(true);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Hadoop Job")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							evaluatorClassifierPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(true);
						}
					}
				}
			} catch (Exception e1) {
				// The place where we clicked is not a tree node, do nothing.
			}
		}
	}

	public static AlgorithmClassifierPanel getAlgorithmClassifierPanel() {
		// TODO Auto-generated method stub
		return algorithmClassifierPanel;
	}

	public static DataDefinitionsClassifierPanel getDataDefinitionsClassifierPanel() {
		// TODO Auto-generated method stub
		return dataDefinitionsClassifierPanel;
	}

	public static TrainingDataClassificationPanel getTrainingDataClassifierPanel() {
		// TODO Auto-generated method stub
		return trainingDataClassificationPanel;
	}
}
