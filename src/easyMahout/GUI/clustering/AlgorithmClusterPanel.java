package easyMahout.GUI.clustering;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.DefaultComboBoxModel;
	import javax.swing.ImageIcon;
	import javax.swing.JComboBox;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.border.LineBorder;

	import easyMahout.utils.Constants;
	//import easyMahout.utils.IconHelpPanel;

	import javax.swing.border.TitledBorder;

	public class AlgorithmClusterPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JComboBox comboBoxType;

		public AlgorithmClusterPanel() {
			// super();
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Algorithm of the cluster", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			setForeground(Color.BLACK);
			setLayout(null);
			setBounds(228, 11, 480, 408);

			comboBoxType = new JComboBox();
			comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.ClusterAlg.CANOPY, Constants.ClusterAlg.KMEANS, Constants.ClusterAlg.FUZZYKMEANS }));
			comboBoxType.setBounds(38, 36, 141, 20);
			add(comboBoxType);
			
//			//JPanel panel = new IconHelpPanel(25,25);
//			panel.setBounds(190, 33, 25, 25);
//			add(panel);

			comboBoxType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String alg = (String) ((JComboBox) e.getSource()).getSelectedItem();
					//ClusterJPanel.getSimilarityPanel().setModelSimilarity(alg);
					if (alg.equals(Constants.ClusterAlg.CANOPY)) {
						//ClusterJPanel.setEnableNeighborhood(false);
					} else if (alg.equals(Constants.ClusterAlg.KMEANS)) {
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					else if (alg.equals(Constants.ClusterAlg.FUZZYKMEANS)) {
						//ClusterJPanel.setEnableNeighborhood(true);
					}
					
				}
			});

		}


		public String getSelectedType() {
			return (String) comboBoxType.getSelectedItem();
		}
	}

