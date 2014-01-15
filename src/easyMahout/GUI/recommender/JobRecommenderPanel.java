package easyMahout.GUI.recommender;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.hadoop.similarity.item.ItemSimilarityJob;

import easyMahout.GUI.recommender.builder.JobBuilder;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;

public class JobRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private HelpTooltip helpTooltip;

	private static JTextPane shellTextPane;

	private JScrollPane shellScrollPane;

	private final static Logger log = Logger.getLogger(JobRecommenderPanel.class);

	public JobRecommenderPanel() {
		setVisible(false);
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Run Hadoop Job", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		// Shell pane
		shellScrollPane = new JScrollPane();
		shellScrollPane.setBounds(25, 55, 430, 276);
		this.add(shellScrollPane);

		shellTextPane = new JTextPane();
		shellScrollPane.setViewportView(shellTextPane);
		shellTextPane.setBackground(Color.WHITE);
		shellTextPane.setEditable(false);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, RecommenderTips.RECOMM_JOB);
		add(helpTooltip);

		JButton btnRun = new JButton("Run");
		btnRun.setBounds(367, 358, 89, 23);
		add(btnRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("run");
				String[] args = JobBuilder.buildRecommenderJob();
				if (args != null) {
					if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {
						try {
							// cause exception (not hadoop started yet)
							// ToolRunner.run(new ItemSimilarityJob(), args);

						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMSIMILARITY)) {
					// TODO
				} else {
					// TODO
				}
			}
		});

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setBounds(268, 358, 89, 23);
		add(btnExport);

		JButton btnShow = new JButton("Show");
		btnShow.setBounds(169, 358, 89, 23);
		add(btnShow);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("show");
				String[] args = JobBuilder.buildRecommenderJob();
				if (args != null) {
					if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {
						try {
							StringBuilder textBuilder = new StringBuilder();
							textBuilder.append("#!/bin/bash")
									.append("\n\n")
									.append("MAHOUT=\"../../bin/mahout\"")
									.append("\n\n")
									.append("$MAHOUT recommenditembased")
									.append("\n\t")
									.append(args[0] + " " + args[1])
									.append("\n\t")
									.append(args[2] + " " + args[3])
									.append("\n\t")
									.append(args[4] + " " + args[5])
									.append("\n\t")
									.append(args[6] + " " + args[7])
									.append("\n\t")
									.append(args[8] + " " + args[9])
									.append("\n\t")
									.append(args[10] + " " + args[11])
									.append("\n\t")
									.append(args[12] + " " + args[13])
									.append("\n\t")
									.append(args[14] + " " + args[15]);

							shellTextPane.setText(textBuilder.toString());

						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMSIMILARITY)) {
					// TODO
				} else {
					// TODO
				}
			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
}
