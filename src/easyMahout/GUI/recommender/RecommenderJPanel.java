package easyMahout.GUI.recommender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;
import easyMahout.utils.DisabledNode;
import easyMahout.utils.DisabledRenderer;
import easyMahout.utils.DynamicTree;

import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JButton;

public class RecommenderJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelRecommender;

	private JPanel treePanel;

	private JPanel p5;

	private static TypeRecommenderPanel typePanel;

	private DataModelRecommenderPanel dataModelPanel;

	private static SimilarityRecommenderPanel similarityPanel;

	private NeighborhoodRecommenderPanel neighborhoodPanel;

	private JTree treeMenu;

	private final static Logger log = Logger.getLogger(RecommenderJPanel.class);

	private JSeparator separator;

	private JButton btnRun;

	private JButton btnEvaluate;

	public RecommenderJPanel() {
		// super();
		panelRecommender = this;

		treeMenu = new JTree(populateTree()[0]);
		DisabledRenderer renderer = new DisabledRenderer();
		treeMenu.setCellRenderer(renderer);

		treeMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		treePanel = new JPanel();
		treePanel.setBounds(0, 0, 220, 395);

		// treeMenu = new DynamicTree("Recommender");
		treeMenu.setBounds(10, 11, 210, 380);
		// populateTree(treeMenu);
		treeMenu.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		treeMenu.expandRow(0);
		treeMenu.expandRow(1);
		setLayout(null);
		treePanel.setLayout(null);
		treePanel.add(treeMenu);
		this.add(treePanel);

		separator = new JSeparator();
		separator.setBounds(225, 268, 1, 2);
		treePanel.add(separator);

		// Create different panes
		typePanel = new TypeRecommenderPanel();
		typePanel.setBounds(238, 11, 481, 382);
		panelRecommender.add(typePanel);
		typePanel.setLayout(null);
		typePanel.setVisible(false);

		dataModelPanel = new DataModelRecommenderPanel();
		dataModelPanel.setBounds(238, 11, 481, 382);
		panelRecommender.add(dataModelPanel);
		dataModelPanel.setLayout(null);
		dataModelPanel.setVisible(false);

		similarityPanel = new SimilarityRecommenderPanel();
		similarityPanel.setBounds(238, 11, 481, 382);
		panelRecommender.add(similarityPanel);
		similarityPanel.setLayout(null);
		similarityPanel.setVisible(false);

		neighborhoodPanel = new NeighborhoodRecommenderPanel();
		neighborhoodPanel.setBounds(238, 11, 481, 382);
		panelRecommender.add(neighborhoodPanel);
		neighborhoodPanel.setLayout(null);
		neighborhoodPanel.setVisible(false);

		p5 = new EvaluatorRecommenderPanel();
		p5.setBounds(238, 11, 481, 382);
		panelRecommender.add(p5);
		p5.setLayout(null);

		btnRun = new JButton("Run");
		btnRun.setBounds(630, 404, 89, 23);
		add(btnRun);

		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setBounds(531, 404, 89, 23);
		add(btnEvaluate);
		p5.setVisible(false);

		btnRun.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				List<RecommendedItem> recommendations;
				try {
					Recommender recomm= buildRecommender();
					recommendations = buildRecommender().recommend(1, 1);
					if (recommendations != null && !recommendations.isEmpty()) {

						Iterator<RecommendedItem> it = recommendations.iterator();

						while (it.hasNext()) {
							RecommendedItem item = it.next();
							System.out.println(item);
							MainGUI.writeResult(item.toString(), Constants.Log.RESULT);
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					MainGUI.writeResult(e1.toString(), Constants.Log.ERROR);
					e1.printStackTrace();
				}

			}
		});

	}

	public JPanel getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(JPanel treePanel) {
		this.treePanel = treePanel;
	}

	private Recommender buildRecommender() {
		if (typePanel.getSelectedType().equals(Constants.RecommType.USERBASED)) {
			DataModel model = dataModelPanel.getDataModel();
			if (model != null) {
				UserSimilarity similarity = similarityPanel.getUserSimilarity(model);
				UserNeighborhood neighborhood = neighborhoodPanel.getNeighborhood(similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			} else {
				log.error("Trying to run a recommender without datamodel loaded");
				MainGUI.writeResult("Trying to run a recommender without a dataModel loaded", Constants.Log.ERROR);
				return null;
			}

		} else if (typePanel.getSelectedType().equals(Constants.RecommType.ITEMBASED)) {
			DataModel model = dataModelPanel.getDataModel();
			if (model != null) {
				ItemSimilarity similarity = similarityPanel.getItemSimilarity(model);
				return new GenericItemBasedRecommender(model, similarity);
			} else {
				log.error("Trying to run a recommender without datamodel loaded");
				MainGUI.writeResult("Trying to run a recommender without a dataModel loaded", Constants.Log.ERROR);
				return null;
			}

		} else
			// mas posibles tipos de recomm
			return null;
	}

	// private RecommenderBuilder buildRecommender() {
	// if (typePanel.getSelectedType().equals(Constants.RecommType.USERBASED)) {
	// // DataModel model = dataModelPanel.getDataModel();
	//
	// RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
	// public Recommender buildRecommender(DataModel model) throws
	// TasteException {
	// UserSimilarity similarity = similarityPanel.getUserSimilarity(model);
	// UserNeighborhood neighborhood =
	// neighborhoodPanel.getNeighborhood(similarity, model);
	// return new GenericUserBasedRecommender(model, neighborhood, similarity);
	// }
	// };
	//
	// return recommenderBuilder;
	//
	// } else if
	// (typePanel.getSelectedType().equals(Constants.RecommType.USERBASED)) {
	// // DataModel model = dataModelPanel.getDataModel();
	// // final ItemSimilarity similarity = getItemSimilarity(model);
	// // //final UserNeighborhood neighborhood =
	// // neighborhoodPanel.getNeighborhood(similarity, model);
	// //
	// // RecommenderBuilder recommenderBuilder = new RecommenderBuilder()
	// // {
	// // public Recommender buildRecommender(DataModel model) throws
	// // TasteException {
	// // return new GenericItemBasedRecommender(model,similarity);
	// // }
	// // };
	// return null;
	// }// mas posibles tipos de recomm
	// else
	// return null;
	//
	// }

	public DisabledNode[] populateTree() {

		String[] strs = { "Recommender", // 0
				"Configure", // 1
				"Type", // 2
				"Data Model", // 3
				"Similarity", // 4
				"Neighborhood", // 5
				"Evaluator", // 6
				"Saves", // 7
				"Example1" // 8

		};

		DisabledNode[] nodes = new DisabledNode[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nodes[i] = new DisabledNode(strs[i]);
		}
		nodes[0].add(nodes[1]);
		nodes[1].add(nodes[2]);
		nodes[1].add(nodes[3]);
		nodes[1].add(nodes[4]);
		nodes[1].add(nodes[5]);
		nodes[1].add(nodes[6]);
		nodes[0].add(nodes[7]);
		nodes[7].add(nodes[8]);

		return nodes;
	}

	void doMouseClicked(MouseEvent me) {
		if (me.getButton() == MouseEvent.BUTTON1) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeMenu.getModel().getRoot();
			DefaultMutableTreeNode recommender = (DefaultMutableTreeNode) root.getFirstChild();
			try {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getPathForLocation(me.getX(), me.getY())
						.getLastPathComponent();
				if (node != null) {
					if (node.equals(root) || node.equals(recommender)) {
						log.info("root or recommender node B1");

					} else if (recommender.isNodeChild(node)) {
						log.info("recomender childs B1");

						String category = (String) node.getUserObject();
						if (category.equals("Type")) {
							log.info("typeB1");
							typePanel.setVisible(true);
							dataModelPanel.setVisible(false);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Data Model")) {
							log.info("dataB1");
							typePanel.setVisible(false);
							dataModelPanel.setVisible(true);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Similarity")) {
							log.info("similarityB1");
							typePanel.setVisible(false);
							dataModelPanel.setVisible(false);
							similarityPanel.setVisible(true);
							neighborhoodPanel.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Neighborhood")) {
							log.info("neighB1");
							typePanel.setVisible(false);
							dataModelPanel.setVisible(false);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(true);
							p5.setVisible(false);

						} else if (category.equals("Evaluator")) {
							log.info("evalB1");
							typePanel.setVisible(false);
							dataModelPanel.setVisible(false);
							similarityPanel.setVisible(false);
							neighborhoodPanel.setVisible(false);
							p5.setVisible(true);
						}

					}
				}
			} catch (Exception e1) {

			}

		}
		if (me.getButton() == MouseEvent.BUTTON3) {

			// DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			// (me.getTreePath().getLastPathComponent());
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
						// addPopup(popupMenuRoot);
						JMenuItem _new = new JMenuItem("New");
						_new.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String category = (String) node.getUserObject();
								// TODO: iniciar panel para cada tipo;
								if (category.equals("Type")) {
									log.info("type");
									// int i =
									// JOptionPane.showConfirmDialog(panelRecommender,
									// "You have a configuration open, do you really want to create a new one whitout saving changes?",
									// "Continue whitout saving changes",
									// JOptionPane.YES_NO_OPTION);
									// if (i == 0) {
									// System.exit(0);
									// }
									// panelRecommender.remove(configPanel);
									// configPanel = new typeRecommenderPanel();
									// panelRecommender.add(configPanel);
									// configPanel.updateUI();
									// p1.setVisible(true);
									// p2.setVisible(false);
									// p3.setVisible(true);
									// p4.setVisible(true);
									// p5.setVisible(true);

									// } else if (category.equals("Data Model"))
									// {
									// log.info("data");
									// configPanel = new typeRecommenderPanel();
									// } else if (category.equals("Similarity"))
									// {
									// log.info("sim");
									// configPanel = new typeRecommenderPanel();
									// } else if
									// (category.equals("Neighborhood")) {
									// log.info("cneigats");
									// configPanel = new typeRecommenderPanel();
									// } else if (category.equals("Evaluator"))
									// {
									// log.info("eval");
									// configPanel = new typeRecommenderPanel();
								}
							}
						});

						JMenuItem _deleteAll = new JMenuItem("Delete All");
						_deleteAll.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// TODO: borrar todas las configuraciones de
								// cierta categoria
								String category = (String) node.getUserObject();
								if (category.equals("Type")) {
									log.info("type");

								} else if (category.equals("Data Model")) {
									log.info("data");
								} else if (category.equals("Similarity")) {
									log.info("sim");
								} else if (category.equals("Neighborhood")) {
									log.info("cneigats");
								} else if (category.equals("Evaluator")) {
									log.info("eval");
								}
							}
						});

						popupMenuRoot.add(_new);
						popupMenuRoot.add(_deleteAll);

						this.add(popupMenuRoot);
						popupMenuRoot.show(me.getComponent(), me.getX(), me.getY());

					} else {
						System.out.println("elems");
					}
				}
			} catch (Exception e1) {

			}

		}

	}

	public static TypeRecommenderPanel getTypePanel() {
		return typePanel;
	}

	public static SimilarityRecommenderPanel getSimilarityPanel() {
		return similarityPanel;
	}

}
