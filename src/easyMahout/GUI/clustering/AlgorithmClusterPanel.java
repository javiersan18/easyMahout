package easyMahout.GUI.clustering;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.GUI.recommender.TypeRecommenderPanel;
	import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;
import easyMahout.utils.help.RecommenderTips;

	//import easyMahout.utils.IconHelpPanel;


import javax.swing.border.TitledBorder;

	public class AlgorithmClusterPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JComboBox comboBoxAlg;
		private HelpTooltip helpTooltip;

		public AlgorithmClusterPanel() {
			// super();
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Algorithm of the cluster", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			setForeground(Color.BLACK);
			setLayout(null);
			setBounds(228, 11, 480, 408);

			comboBoxAlg = new JComboBox();
			comboBoxAlg.setModel(new DefaultComboBoxModel(new String[] { Constants.ClusterAlg.CANOPY, Constants.ClusterAlg.KMEANS,
					Constants.ClusterAlg.FUZZYKMEANS, Constants.ClusterAlg.DIRICHLET , Constants.ClusterAlg.USER_DEFINED}));
			comboBoxAlg.setBounds(38, 36, 141, 20);
			add(comboBoxAlg);
			
			final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
			btnHelp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnHelp.setPreferredSize(new Dimension(65, 40));
			btnHelp.setBounds(10, 358, 40, 40);
			add(btnHelp);

			// Help Tip
			helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_ALGORITHM);
			add(helpTooltip);


			comboBoxAlg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String alg = (String) ((JComboBox) e.getSource()).getSelectedItem();
					//ClusterJPanel.getSimilarityPanel().setModelSimilarity(alg);
					if (alg.equals(Constants.ClusterAlg.CANOPY)) {
						JOptionPane.showMessageDialog(null, "You've selected "+ alg+ " algorithm!!" );
						//ClusterJPanel.setEnableNeighborhood(false);
					} else if (alg.equals(Constants.ClusterAlg.KMEANS)) {
						JOptionPane.showMessageDialog(null, "You've selected "+ alg+ " algorithm!!" );
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					else if (alg.equals(Constants.ClusterAlg.FUZZYKMEANS)) {
						JOptionPane.showMessageDialog(null, "You've selected "+ alg+ " algorithm!!" );
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					else if (alg.equals(Constants.ClusterAlg.DIRICHLET)) {
						JOptionPane.showMessageDialog(null, "You've selected "+ alg+ " algorithm!!" );
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					else if (alg.equals(Constants.ClusterAlg.USER_DEFINED)) {
						JOptionPane.showMessageDialog(null, "You've selected "+ alg+ " algorithm!!" );
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					
				}
			});

		}


		public String getSelectedType() {
			return (String) comboBoxAlg.getSelectedItem();
		}
	}

