package easyMahout.GUI.clustering;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;
import easyMahout.utils.help.RecommenderTips;
import javax.swing.SwingConstants;

public class ConfigureClusterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblGuide;
	private HelpTooltip helpTooltip;

	public ConfigureClusterPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Configuration Info", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		lblGuide = new JLabel(ClusterTips.CLUSTER_CONFIG);
		lblGuide.setVerticalAlignment(SwingConstants.TOP);
		lblGuide.setBounds(38, 36, 405, 334);
		add(lblGuide);
		
		
		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_CONFIG);
		add(helpTooltip);
		}public static final String CLUSTER = "docs/tooltips/recommender/recommender.html";
		
		
		public static final String CLUSTER_TYPE = "<html>"
				+ "<h4>Cluster Types</h4>"
				+ "<br><p><u><b>Based on different algorithms:</b></u> The system generates clusters using information</p>"
				+ "<p>about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
				+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
				+ "</html>";

		public static final String CLUSTER_DATAMODEL = "docs/tooltips/clustering/datamodel.html";

		public static final String CLUSTER_SIMILARITY = "docs/tooltips/clustering/similaruty.html";

		public static final String CLUSTER_NEIGHBORHOOD = "docs/tooltips/clustering/neighborhood.html";

		public static final String CLUSTER_EVALUATOR = "docs/tooltips/clustering/evaluator.html";

		public static final String CLUSTER_QUERY = "docs/tooltips/clustering/query.html";

		public static final String CLUSTER_CONFIG = "<html>"
				+ "<h4>Steps to build a cluster</h4>"
				+ "<br><p><u><b>1.</b></u> The system generates clusters using an algorithm(K-Means,Canopy,Fuzzy K-Means)</p>"
				+ "<br><p><u><b>2.</b></u> Distance Measure for the similarity function.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
				+ "<br><p><u><b>3.</b></u> current user and generate clusters using this neighborhood.</p>" + "<br>"
				+ "<p><u><b>4.</b></u></p>" + "</html>";

		public static final String CLUSTER_TYPE_DIST = "<html>"
				+ "<h4>Cluster Types Distributed</h4>"
				+ "<br><p><u><b>User based:</b></u> The system generates clusters using only information</p>"
				+ "<p>about ANGHEL profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
				+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
				+ "</html>";

		public static final String CLUSTER_DATAMODEL_DIST = "docs/tooltips/clustering/datamodel_dist.html";

		public static final String CLUSTER_SIMILARITY_DIST = "docs/tooltips/clustering/similarity_dist.html";

		public static final String CLUSTER_CONFIG_DIST = "<html>"
				+ "<h4>Steps to build a DISTRIBUTED cluster</h4>"
				+ "<br><p><u><b>1.</b></u> The system generates clusters using only information</p>"
				+ "<br><p><u><b>2.</b></u> about rating LAURENTIU for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
				+ "<br><p><u><b>3.</b></u> current user and generate clusters using this neighborhood.</p>" + "<br>"
				+ "<p><u><b>4.</b></u></p>" + "</html>";

		public static final String CLUSTER_JOB = "docs/tooltips/clustering/job_dist.html";
//reClusterPanel.setVisible(false);configureClusterPanel.setVisible(false);configureClusterPanel.setVisible(false);

	public void setDistributed(boolean distributed) {
		if (distributed) {
			lblGuide.setText(ClusterTips.CLUSTER_CONFIG_DIST);
		} else {
			lblGuide.setText(ClusterTips.CLUSTER_CONFIG);
		}
	}
	
	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

}
