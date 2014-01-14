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
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

import easyMahout.GUI.recommender.builder.JobBuilder;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;

public class JobRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private HelpTooltip helpTooltip;
	private JTextArea txtrShelljob;
	
	private final static Logger log = Logger.getLogger(JobRecommenderPanel.class);

	public JobRecommenderPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Run Hadoop Job", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		txtrShelljob = new JTextArea();
		txtrShelljob.setBackground(Color.WHITE);
		txtrShelljob.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtrShelljob.setBounds(25, 55, 430, 200);
		add(txtrShelljob);
		txtrShelljob.setEditable(false);

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
				log.error("run");
				JobBuilder.buildRecommenderJob();				
			}
		});
		
		JButton btnExport = new JButton("Export \r\n");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setBounds(268, 358, 89, 23);
		add(btnExport);

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
}
