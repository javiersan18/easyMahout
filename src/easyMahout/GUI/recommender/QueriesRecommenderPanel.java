package easyMahout.GUI.recommender;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.hadoop.similarity.item.ItemSimilarityJob;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.builder.RecommenderBuilder;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.TableChangeListener;

public class QueriesRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;

	private JTable table;

	private JButton btnAdd;

	private JButton btnDelete;

	private JButton btnRun;

	private static DefaultTableModel tableModel;

	private HelpTooltip helpTooltip;

	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(QueriesRecommenderPanel.class);

	public QueriesRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Add Queries", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane.setBounds(38, 36, 409, 313);

		table = new JTable();
		table.setRowSelectionAllowed(false);

		table.setModel(new DefaultTableModel(new Object[][] { { false, null, null }, }, new String[] { "Select", "User ID",
				"How many recommendations" }) {

			private static final long serialVersionUID = 1L;

			Class[] columnTypes = new Class[] { Boolean.class, Long.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setMaxWidth(150);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setMaxWidth(1000);

		tableModel = (DefaultTableModel) table.getModel();
		tableModel.addTableModelListener(new TableChangeListener());

		scrollPane.setViewportView(table);
		add(scrollPane);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[] { false, null, null });
			}
		});
		btnAdd.setBounds(173, 364, 89, 23);
		add(btnAdd);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_QUERY);
		add(helpTooltip);

		btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// cuando se ejecuta el recomm con la celda recien modificada,
				// no actualiza el valor

			

				try {
					Recommender recomm = RecommenderBuilder.buildRecommender();
					if (recomm != null) {
						int i = 0;
						while (i < tableModel.getRowCount()) {
							if ((boolean) tableModel.getValueAt(i, 0)) {
								Long user = (Long) tableModel.getValueAt(i, 1);
								List<RecommendedItem> list = recomm.recommend(user, (Integer) tableModel.getValueAt(i, 2));
								if (!list.isEmpty()) {
									Iterator<RecommendedItem> it = list.iterator();
									while (it.hasNext()) {
										RecommendedItem item = it.next();
										MainGUI.writeResult("User " + user + ": " + item.toString(), Constants.Log.RESULT);
									}
								} else {
									MainGUI.writeResult("User " + user + ": No recommendatios", Constants.Log.RESULT);
								}
							}
							i++;
						}

					} else {
						// TODO sobra??? puede fallar la creacion del recomm si
						// tiene dataModel?
						MainGUI.writeResult("error building the recommender", Constants.Log.ERROR);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					MainGUI.writeResult(e1.toString(), Constants.Log.ERROR);
					e1.printStackTrace();
				}
			}
		});

		btnRun.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRun.setBounds(371, 364, 89, 23);
		add(btnRun);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				while (i < tableModel.getRowCount()) {
					if ((boolean) tableModel.getValueAt(i, 0)) {
						tableModel.removeRow(i);
					} else {
						i++;
					}
				}
			}
		});
		btnDelete.setBounds(272, 364, 89, 23);
		add(btnDelete);

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static void addRow(boolean selected, Long userID, Integer howMany) {
		tableModel.addRow(new Object[] { selected, userID, howMany });
	}

	public static void emptyTable() {
//		if (tableModel.getRowCount() > 0) {
//			tableModel.removeRow(0);
//			tableModel.getDataVector().clear();
//		}
		tableModel.getDataVector().clear();
	}

	public static Vector getQueries() {
		return tableModel.getDataVector();
	}

}
