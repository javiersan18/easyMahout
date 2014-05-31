package easyMahout.GUI.clustering;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.clustering.builder.ClusterBuilder;
import easyMahout.GUI.clustering.builder.CreateSequenceFile;
import easyMahout.GUI.clustering.builder.ReadSequenceFile;
import easyMahout.GUI.clustering.builder.ShowCommandlineClusterBuilder;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.RecommenderTips;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class JobClusterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private HelpTooltip helpTooltip;
	private static JButton btnRun;
	private JButton btnShow;
	private JButton btnExport;
	private JScrollPane shellScrollPane;

	private JTextPane shellTextPane;

	public JobClusterPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Run Hadoop Job", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		// Shell pane
				shellScrollPane = new JScrollPane();
				shellScrollPane.setBounds(25, 88, 430, 243);
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

		btnRun = new JButton("Run");
		btnRun.setBounds(367, 358, 89, 23);
		add(btnRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// mouse loading

				btnRun.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				MainGUI.clean();
				String filePath = DataModelClusterPanel.getInputPath();
				String output = DataModelClusterPanel.getOutputPath();

				Configuration conf = new Configuration();
				FileSystem fs;
				try {
					fs = FileSystem.get(conf);
					Path path = new Path(output);
					if (fs.exists(path) && DataModelClusterPanel.removeUfExist()) {
						fs.delete(path, true);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				String delimiter = DataModelClusterPanel.getDelimiter();
				String outputFormatted = output + ".csv";
				if (!filePath.isEmpty() && !output.isEmpty()) {
					if (ClusterBuilder.isSequential()) {
						CreateSequenceFile.convert(filePath, output, delimiter);
						ReadSequenceFile.readSequenceFile(output, outputFormatted);
					}
					try {
						ClusterBuilder.buildCluster();
						// dibuja();
					} catch (ClassNotFoundException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (InterruptedException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					} catch (IOException e1) {
						MainGUI.writeResult("Not able to build the cluster", Constants.Log.ERROR);
					}

				} else {
					MainGUI.writeResult("You have to specify both input and output source file!", Constants.Log.ERROR);
				}
				// mouse finished loading
				btnRun.setCursor(Cursor.getDefaultCursor());
			}
		});

		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setBounds(268, 358, 89, 23);
		add(btnExport);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectedFile = new JFileChooser();
				int i = selectedFile.showOpenDialog(JobClusterPanel.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					File prefs = selectedFile.getSelectedFile();
					String absPath = prefs.getAbsolutePath() + ".sh";

					FileWriter fileW = null;
					PrintWriter pw = null;
					try {
						fileW = new FileWriter(absPath);
						pw = new PrintWriter(fileW);
						pw.print(ShowCommandlineClusterBuilder.buildCommandline().toString());

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

					MainGUI.writeResult("Shell script created as: " + prefs.getName() + ".sh", Constants.Log.INFO);
				} else if (i == JFileChooser.ERROR_OPTION) {
					MainGUI.writeResult("Error generating shell script", Constants.Log.ERROR);
					
				}
			}
		});
		
		btnShow = new JButton("Show");
		btnShow.setBounds(169, 358, 89, 23);
		add(btnShow);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shellTextPane.setText(ShowCommandlineClusterBuilder.buildCommandline().toString());
			}
		});

	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}
}
