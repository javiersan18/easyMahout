package easyMahout.GUI.classification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.apache.log4j.Logger;

import easyMahout.utils.DynamicTree;

import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Color;

import javax.swing.JSeparator;

public class ClassificationJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelClassification;

	private JPanel treePanel;

	private JPanel p1, p2, p3, p4, p5;

	private DynamicTree treeMenu;

	private final static Logger log = Logger.getLogger(ClassificationJPanel.class);

	private JSeparator separator;

	public ClassificationJPanel() {
		super();
		panelClassification = this;

		treePanel = new JPanel();
		treePanel.setBounds(0, 0, 220, 395);

		treeMenu = new DynamicTree("Classification");
		treeMenu.setBounds(10, 11, 210, 380);
		populateTree(treeMenu);
		treeMenu.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		treeMenu.expandRow(0);
		setLayout(null);
		treePanel.setLayout(null);
		treePanel.add(treeMenu);
		this.add(treePanel);

		separator = new JSeparator();
		separator.setBounds(225, 268, 1, 2);
		treePanel.add(separator);

		// Create different panes
		// 1 panel por cada posible configuracion
		p1 = new TypeRecommenderPanel();
		p1.setBounds(238, 11, 481, 382);
		panelClassification.add(p1);
		p1.setLayout(null);
		p1.setVisible(false);

		/*p2 = new dataModelRecommenderPanel();
		p2.setBounds(238, 11, 481, 382);
		panelRecommender.add(p2);
		p2.setLayout(null);
		p2.setVisible(false);

		p3 = new similarityRecommenderPanel();
		p3.setBounds(238, 11, 481, 382);
		panelRecommender.add(p3);
		p3.setLayout(null);
		p3.setVisible(false);

		p4 = new neighborhoodRecommenderPanel();
		p4.setBounds(238, 11, 481, 382);
		panelRecommender.add(p4);
		p4.setLayout(null);
		p4.setVisible(false);

		p5 = new evaluatorRecommenderPanel();
		p5.setBounds(238, 11, 481, 382);
		panelRecommender.add(p5);
		p5.setLayout(null);
		p5.setVisible(false);*/
	}

	public JPanel getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(JPanel treePanel) {
		this.treePanel = treePanel;
	}

	public void populateTree(DynamicTree treeMenu) {

		String cat1 = new String("Type");
		String cat2 = new String("Data Model");
		String cat3 = new String("Similarity");
		String cat4 = new String("Neighborhood");
		String cat5 = new String("Evaluator");

		treeMenu.addObject(null, cat1);
		treeMenu.addObject(null, cat2);
		treeMenu.addObject(null, cat3);
		treeMenu.addObject(null, cat4);
		treeMenu.addObject(null, cat5);

		treeMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

	}

	void doMouseClicked(MouseEvent me) {
		if (me.getButton() == MouseEvent.BUTTON1) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeMenu.getModel().getRoot();
			try {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getPathForLocation(me.getX(), me.getY())
						.getLastPathComponent();
				if (node != null) {
					if (node.equals(root)) {
						log.info("root");

					} else if (root.isNodeChild(node)) {
						log.info("catsB1");

						String category = (String) node.getUserObject();
						if (category.equals("Type")) {
							log.info("typeB1");
							p1.setVisible(true);
							p2.setVisible(false);
							p3.setVisible(false);
							p4.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Data Model")) {
							log.info("dataB1");
							p1.setVisible(false);
							p2.setVisible(true);
							p3.setVisible(false);
							p4.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Similarity")) {
							log.info("similarityB1");
							p1.setVisible(false);
							p2.setVisible(false);
							p3.setVisible(true);
							p4.setVisible(false);
							p5.setVisible(false);

						} else if (category.equals("Neighborhood")) {
							log.info("neighB1");
							p1.setVisible(false);
							p2.setVisible(false);
							p3.setVisible(false);
							p4.setVisible(true);
							p5.setVisible(false);

						} else if (category.equals("Evaluator")) {
							log.info("evalB1");
							p1.setVisible(false);
							p2.setVisible(false);
							p3.setVisible(false);
							p4.setVisible(false);
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
}
