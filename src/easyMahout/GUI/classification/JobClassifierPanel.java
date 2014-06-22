package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.builder.ShowCommandlineClassifierBuilder;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.GUI.recommender.builder.ShowCommandlineBuilder;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClassifierTips;

public class JobClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private HelpTooltip helpTooltip;

	private static JTextPane shellTextPane;

	private JScrollPane shellScrollPane;

	@SuppressWarnings("unused")
	private final static Logger log = Logger
			.getLogger(JobClassifierPanel.class);

	public JobClassifierPanel() {
		setVisible(false);
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Run Hadoop Job", TitledBorder.CENTER, TitledBorder.TOP, null,
				null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		// Shell pane
		shellScrollPane = new JScrollPane();
		shellScrollPane.setBounds(25, 30, 430, 290);
		this.add(shellScrollPane);

		shellTextPane = new JTextPane();
		shellScrollPane.setViewportView(shellTextPane);
		shellTextPane.setBackground(Color.WHITE);
		shellTextPane.setEditable(false);

		final JButton btnHelp = new JButton(new ImageIcon(
				TypeRecommenderPanel.class
						.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClassifierTips.CLASSIF_CONFIG);
		add(helpTooltip);

		JButton btnRun = new JButton("Run");
		btnRun.setBounds(367, 358, 89, 23);
		add(btnRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnRun.setVisible(false);

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setBounds(268, 358, 89, 23);
		add(btnExport);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(JobClassifierPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File prefs = selectedFile.getSelectedFile();
					String absPath = prefs.getAbsolutePath() + ".sh";

					FileWriter fileW = null;
					PrintWriter pw = null;
					try {
						fileW = new FileWriter(absPath);
						pw = new PrintWriter(fileW);
						pw.print(ShowCommandlineBuilder.buildCommandline()
								.toString());

					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						try {
							if (null != fileW)
								fileW.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}

					MainGUI.writeResult(
							"Shell script created as: " + prefs.getName()
									+ ".sh", Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error generating shell script",
							Constants.Log.ERROR);
				}
			}
		});

		JButton btnShow = new JButton("Show");
		btnShow.setBounds(169, 358, 89, 23);
		add(btnShow);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shellTextPane.setText(ShowCommandlineClassifierBuilder
						.buildCommandline().toString());
			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

}
