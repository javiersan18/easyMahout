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
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;
//import easyMahout.utils.IconHelpPanel;

import javax.swing.border.TitledBorder;

public class DistanceMeasurePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxDistance;
	private HelpTooltip helpTooltip;

	public DistanceMeasurePanel() {
		// super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Distance measure of the cluster", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxDistance = new JComboBox();
		comboBoxDistance.setModel(new DefaultComboBoxModel(new String[] { 
				Constants.ClusterDist.EUCLIDEAN, 
				Constants.ClusterDist.SQUAREDEUCLIDEAN,
				Constants.ClusterDist.MANHATTAN,
				Constants.ClusterDist.COSINE,
				Constants.ClusterDist.TANIMOTO,
				Constants.ClusterDist.WEIGHTED }));
		comboBoxDistance.setBounds(38, 36, 141, 20);
		add(comboBoxDistance);
		
		JButton next = new JButton("Next          >>");
		next.setVisible(true);
		next.setBounds(320, 380, 141, 20);
		add(next);
		
		JButton prev = new JButton("<<          Previous");
		prev.setVisible(true);
		prev.setBounds(120, 380, 141, 20);
		add(prev);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_DIST);
		add(helpTooltip);

		comboBoxDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dist = (String) ((JComboBox) e.getSource()).getSelectedItem();
				//ClusterJPanel.getSimilarityPanel().setModelSimilarity(alg);
				if (dist.equals(Constants.ClusterDist.EUCLIDEAN)) {
					//ClusterJPanel.setEnableNeighborhood(false);
				} else if (dist.equals(Constants.ClusterDist.SQUAREDEUCLIDEAN)) {
					//ClusterJPanel.setEnableNeighborhood(true);
				}
				else if (dist.equals(Constants.ClusterDist.MANHATTAN)) {
					//ClusterJPanel.setEnableNeighborhood(true);
				}
				else if (dist.equals(Constants.ClusterDist.COSINE)) {
					//ClusterJPanel.setEnableNeighborhood(true);
				}
				else if (dist.equals(Constants.ClusterDist.TANIMOTO)) {
					//ClusterJPanel.setEnableNeighborhood(true);
				}
				else if (dist.equals(Constants.ClusterDist.WEIGHTED)) {
					//ClusterJPanel.setEnableNeighborhood(true);
				}
			}
		});

	}


	public void setValue(int dist){
		this.comboBoxDistance.setSelectedIndex(dist);
	}


	public String getSelectedType() {
		return (String) comboBoxDistance.getSelectedItem();
	}
	
	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
	
	 public void setEditable(boolean enabled) {
	        comboBoxDistance.setEditable(enabled);
	 }


	public JComboBox getComboBoxDistance() {
		return comboBoxDistance;
	}


	public void setComboBoxDistance(JComboBox comboBoxDistance) {
		this.comboBoxDistance = comboBoxDistance;
	}
	 
}

