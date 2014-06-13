package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;



import easyMahout.utils.DisabledNode;
import easyMahout.utils.DisabledRenderer;

public class MainClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger
			.getLogger(MainClassifierPanel.class);

	private JPanel panelClassifier;

	private JPanel treePanel;

	private static DataDefinitionsClassifierPanel dataDefinitionsClassifierPanel;

	private static DataModelClassifierPanel dataModelClassifierPanel;

	private static TrainingDataClassifierPanel trainingDataClassificationPanel;

	private TestDataClassifierPanel testDataClassificationPanel;
	
	private TestModelClassifierPanel testModelClassificationPanel;
	
	private static AlgorithmClassifierPanel algorithmClassifierPanel;

	private JobClassifierPanel jobPanel;

	private static boolean configurationModified;

	private boolean controlModified;

	private String activeConfigutation;

	private static String fileName;

	private static JTree treeMenu;

	private static DisabledNode nodeRoot;

	private ArrayList<DisabledNode> treeNodes;

	private DisabledNode nodeConfigure;

	private static DisabledNode nodeTraining;

	private DisabledNode nodeAlgorithm;

	private static DisabledNode nodeDataDefs;

	private DisabledNode nodeJob;

	private static DisabledNode nodeSelected;

	private DisabledNode nodeTest;
	
	private DisabledNode nodeTestModel;

	private static ConfigureClassificationPanel configPanel;

	public MainClassifierPanel() {

		this.setLayout(null);

		panelClassifier = this;

		treeMenu = new JTree(populateTree()[0]);

		DisabledRenderer renderer = new DisabledRenderer();
		treeMenu.setCellRenderer(renderer);

		treeMenu.addMouseListener(new MouseAdapter() {
			@Override
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

		trainingDataClassificationPanel = new TrainingDataClassifierPanel();
		trainingDataClassificationPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(trainingDataClassificationPanel);
		trainingDataClassificationPanel.setLayout(null);
		trainingDataClassificationPanel.setVisible(false);
		
		testDataClassificationPanel = new TestDataClassifierPanel();
		testDataClassificationPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(testDataClassificationPanel);
		testDataClassificationPanel.setLayout(null);
		testDataClassificationPanel.setVisible(false); 
		
		testModelClassificationPanel = new TestModelClassifierPanel();
		testModelClassificationPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(testModelClassificationPanel);
		testModelClassificationPanel.setLayout(null);
		testModelClassificationPanel.setVisible(false);

		algorithmClassifierPanel = new AlgorithmClassifierPanel();
		algorithmClassifierPanel.setBounds(238, 11, 481, 410);
		panelClassifier.add(algorithmClassifierPanel);
		algorithmClassifierPanel.setLayout(null);
		algorithmClassifierPanel.setVisible(false);

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
				"Test Data", //5
				"Data Model", // 6
				"Test Model", //7
				"Hadoop Job" // 8
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

		nodeConfigure = treeNodes.get(1);
		nodeAlgorithm = treeNodes.get(2);
		nodeDataDefs = treeNodes.get(3);
		nodeTraining = treeNodes.get(4);
		nodeTest = treeNodes.get(5);
		nodeTestModel = treeNodes.get(7);
		nodeJob = treeNodes.get(8);
		
		nodeDataDefs.setEnabled(false);
		nodeTraining.setEnabled(false);

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
						testDataClassificationPanel.setVisible(false);
						testModelClassificationPanel.setVisible(false);
						configPanel.setVisible(true);
						jobPanel.setVisible(false);
					} else if (nodeConfigure.isNodeChild(nodeSelected)) {
						
						String category = (String) nodeSelected.getUserObject();
						
						if (category.equals("Data Definitions")) {
							if(nodeDataDefs.isEnabled()){							
								dataDefinitionsClassifierPanel.setVisible(true);
								dataModelClassifierPanel.setVisible(false);
								trainingDataClassificationPanel.setVisible(false);
								algorithmClassifierPanel.setVisible(false);
								testDataClassificationPanel.setVisible(false);
								testModelClassificationPanel.setVisible(false);
								configPanel.setVisible(false);
								jobPanel.setVisible(false);
							}
						} else if (category.equals("Data Model")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(true);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							testDataClassificationPanel.setVisible(false);
							testModelClassificationPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Training Data")) {
							if(nodeTraining.isEnabled()){
								dataDefinitionsClassifierPanel.setVisible(false);
								dataModelClassifierPanel.setVisible(false);
								trainingDataClassificationPanel.setVisible(true);
								algorithmClassifierPanel.setVisible(false);
								testDataClassificationPanel.setVisible(false);
								testModelClassificationPanel.setVisible(false);
								configPanel.setVisible(false);
								jobPanel.setVisible(false);
							}
						} else if (category.equals("Algorithm")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(true);
							testDataClassificationPanel.setVisible(false);
							testModelClassificationPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Test Data")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							testDataClassificationPanel.setVisible(true);
							testModelClassificationPanel.setVisible(false);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Test Model")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							testDataClassificationPanel.setVisible(false);
							testModelClassificationPanel.setVisible(true);
							configPanel.setVisible(false);
							jobPanel.setVisible(false);
						} else if (category.equals("Hadoop Job")) {
							dataDefinitionsClassifierPanel.setVisible(false);
							dataModelClassifierPanel.setVisible(false);
							trainingDataClassificationPanel.setVisible(false);
							algorithmClassifierPanel.setVisible(false);
							testDataClassificationPanel.setVisible(false);
							testModelClassificationPanel.setVisible(false);
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

	public static TrainingDataClassifierPanel getTrainingDataClassifierPanel() {
		// TODO Auto-generated method stub
		return trainingDataClassificationPanel;
	}
	
	public static TrainingDataClassifierPanel getTestDataClassifierPanel() {
		// TODO Auto-generated method stub
		return trainingDataClassificationPanel;
	}
	
	public static DisabledNode getNodeDataDefs(){
		return nodeDataDefs;
	}
	
	public static DisabledNode getNodeTrainingData(){
		return nodeTraining;
	}
}
