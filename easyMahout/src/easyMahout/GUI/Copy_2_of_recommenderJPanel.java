package easyMahout.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import easyMahout.recommender.ExtendedDataModel;
import easyMahout.utils.DynamicTree;
import MahoutInAction.Recommender.Recommender22;

import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.border.BevelBorder;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.GridBagLayout;

public class Copy_2_of_recommenderJPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelRecommender;

	private JPanel treePanel, configPanel;	

	private JLabel labelDatamodel, labelSimilarity, labelDataSource, labelEvaluator, labelNeighborhood;

	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxType, comboBoxDatamodel, comboBoxNeighborhood, comboBoxSimilarity;

	private JButton btnRun, btnImport;

	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel booleanModels, restModels;

	private JTextField textPath, usersNeigborhood;

	private JCheckBox chckbxBooleanPreference;

	private DynamicTree treeMenu;

	private final static Logger log = Logger.getLogger(Copy_2_of_recommenderJPanel.class);

	// Recommender atributes
	private Recommender recomm;

	@SuppressWarnings("unused")
	private DataModel dataModel;

	private Similarity similarityMetric;
	private JLabel label;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Copy_2_of_recommenderJPanel() {
		
		panelRecommender = new JPanel();

		treePanel = new JPanel();

		treeMenu = new DynamicTree("Recommender");
		treeMenu.setBounds(10, 11, 216, 382);
		populateTree(treeMenu);
		treeMenu.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		treeMenu.expandRow(0);
		panelRecommender.setLayout(new GridLayout(0, 1, 0, 0));
		treePanel.setLayout(null);
		treePanel.add(treeMenu);
		panelRecommender.add(treePanel);
		configPanel = new typeRecommenderPanel();
		treePanel.add(configPanel);
		configPanel.setForeground(Color.BLACK);
		configPanel.setBounds(236, 11, 483, 382);
		configPanel.setLayout(null);
		//configPanel.setLayout(null);
//		
//		label = new JLabel("New label");
//		label.setBounds(10, 157, 46, 14);
		//configPanel.add(label);

		// //Put everything together, using the content pane's BorderLayout.
		// Container contentPane = getContentPane();
		// contentPane.add(panel1, BorderLayout.CENTER);
		// contentPane.add(panel2, BorderLayout.PAGE_END);

		// panelRecommender = new typeRecommenderPanel();

		// panel = new typeRecommenderPanel();
		// panel.setBounds(246, 12, 473, 412);
		// panelRecommender.add(panel);

		// JTree tree = new JTree();
		// tree.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		// tree.setBounds(10, 12, 226, 412);
		// panelRecommender.add(tree);

		// -------------------------------------------------------------------------------
		// ----------- Action
		// Listeners---------------------------------------------------
		// -------------------------------------------------------------------------------

		// ComboBox

		// comboBoxType.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// // checkBooleanPreferenceCheckbox(comboBox_type,
		// // comboBox_datamodel, booleanModels, restModels,
		// // chckbxBooleanPreference);
		//
		// checkBooleanPreferenceCheckbox();
		//
		// }
		// });
		//
		// comboBoxDatamodel.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });
		//
		// // Buttons
		//
		// btnRun.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// try {
		// Recommender22.main(null);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// }
		// });
		//
		// btnImport.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// JFileChooser selectedFile = new JFileChooser();
		// int i = selectedFile.showOpenDialog(recommenderJPanel.this);
		// if (i == JFileChooser.APPROVE_OPTION) {
		// File data = selectedFile.getSelectedFile();
		// String absPath = data.getAbsolutePath();
		// int selected = comboBoxDatamodel.getSelectedIndex();
		//
		// try {
		// // TODO: distintos tipos de modelos...
		// switch (selected) {
		// case 0:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// case 1:
		// dataModel = new GenericDataModel(GenericDataModel.toDataMap(new
		// FileDataModel(new File(absPath))));
		// break;
		// case 2:
		// dataModel = new ExtendedDataModel(new File(absPath), ",");
		// break;
		// case 3:
		// // dataModel = new CassandraDataModel(new
		// // File(absPath));
		// break;
		// case 4:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// case 5:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// case 6:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// case 7:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// default:
		// dataModel = new FileDataModel(new File(absPath));
		// break;
		// }
		//
		// // dataModel = new FileDataModel(new File(absPath));
		// textPath.setText(absPath);
		// mainGUI.writeResult("Data Model successfully created from file",
		// "info");
		// } catch (IllegalArgumentException e1) {
		// mainGUI.writeResult("Error reading data file: " + e1.getMessage(),
		// "error");
		// log.error("Error reading data file", e1);
		// } catch (Exception e1) {
		// mainGUI.writeResult("Error reading data file", "error");
		// log.error("Error reading data file", e1);
		// }
		//
		// } else if (i == JFileChooser.ERROR_OPTION) {
		// mainGUI.writeResult("Error openig the file", "error");
		// log.error("Error opening data file");
		// }
		// }
		// });
		//
	}

	//
	// @SuppressWarnings("unchecked")
	// private void checkBooleanPreferenceCheckbox() {
	// int selectedType = comboBoxType.getSelectedIndex();
	// if (selectedType == 0 || selectedType == 1) {
	// // user or item based
	// chckbxBooleanPreference.setEnabled(true);
	// comboBoxDatamodel.setModel(restModels);
	// } else {
	// // clustering
	// chckbxBooleanPreference.setEnabled(false);
	// comboBoxDatamodel.setModel(booleanModels);
	// }
	// }

	public JPanel getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(JPanel treePanel) {
		this.treePanel = treePanel;
	}

//	public static void addComponent(JComponent comp) {
//		panelRecommender.add(comp);
//	}

	public void populateTree(DynamicTree treeMenu) {

		String cat1 = new String("Type");
		String cat2 = new String("Data Model");
		String cat3 = new String("Similarity");
		String cat4 = new String("Neighborhood");
		String cat5 = new String("Evaluator");

		DefaultMutableTreeNode pRoot, pType, pModel, pSim, pNeigh, pEval;

		pType = treeMenu.addObject(null, cat1);
		pModel = treeMenu.addObject(null, cat2);
		pSim = treeMenu.addObject(null, cat3);
		pNeigh = treeMenu.addObject(null, cat4);
		pEval = treeMenu.addObject(null, cat5);

		treeMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

	}

	void doMouseClicked(MouseEvent me) {
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
									panelRecommender.remove(configPanel);
									JPanel configPanel = new typeRecommenderPanel();
									panelRecommender.add(configPanel);
									configPanel.updateUI();
									configPanel.repaint();
									panelRecommender.updateUI();
									panelRecommender.repaint();
								} else if (category.equals("Data Model")) {
									log.info("data");
									configPanel = new typeRecommenderPanel();
								} else if (category.equals("Similarity")) {
									log.info("sim");
									configPanel = new typeRecommenderPanel();
								} else if (category.equals("Neighborhood")) {
									log.info("cneigats");
									configPanel = new typeRecommenderPanel();
								} else if (category.equals("Evaluator")) {
									log.info("eval");
									configPanel = new typeRecommenderPanel();
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
				// System.out.println("nada");
			}

			// System.out.println(node.toString());
		}

		// TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		// if (tp != null)
		// System.out.println(tp.toString());
		// else
		// System.out.println("null");
	}

//	public static JPanel getPanel() {
//		return panelRecommender;
//	}

	public void setPanel(JPanel panel_recommender) {
		this.panelRecommender = panel_recommender;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboBox_type() {
		return comboBoxType;
	}

	public void setComboBox_type(@SuppressWarnings("rawtypes") JComboBox comboBox_type) {
		this.comboBoxType = comboBox_type;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboBox_datamodel() {
		return comboBoxDatamodel;
	}

	public void setComboBox_datamodel(@SuppressWarnings("rawtypes") JComboBox comboBox_datamodel) {
		this.comboBoxDatamodel = comboBox_datamodel;
	}

	public JButton getBtnRun() {
		return btnRun;
	}

	public void setBtnRun(JButton btnRun) {
		this.btnRun = btnRun;
	}

	public JButton getBtnImport() {
		return btnImport;
	}

	public void setBtnImport(JButton btnImport) {
		this.btnImport = btnImport;
	}

	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel getBooleanModels() {
		return booleanModels;
	}

	public void setBooleanModels(@SuppressWarnings("rawtypes") DefaultComboBoxModel booleanModels) {
		this.booleanModels = booleanModels;
	}

	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel getRestModels() {
		return restModels;
	}

	public void setRestModels(@SuppressWarnings("rawtypes") DefaultComboBoxModel restModels) {
		this.restModels = restModels;
	}

	public JTextField getTextPath() {
		return textPath;
	}

	public void setTextPath(JTextField textPath) {
		this.textPath = textPath;
	}

	public JCheckBox getChckbxBooleanPreference() {
		return chckbxBooleanPreference;
	}

	public void setChckbxBooleanPreference(JCheckBox chckbxBooleanPreference) {
		this.chckbxBooleanPreference = chckbxBooleanPreference;
	}
}
