package easyMahout.GUI.classification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileAlreadyExistsException;
import org.apache.hadoop.util.ToolRunner;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.PreferencesPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.GUI.recommender.builder.JobBuilder;
import easyMahout.GUI.recommender.builder.ShellScriptBuilder;
import easyMahout.GUI.recommender.builder.ShowCommandlineBuilder;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClassifierTips;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class JobClassifierPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final char slash = File.separatorChar;

	private HelpTooltip helpTooltip;

	private static JTextPane shellTextPane;

	private JScrollPane shellScrollPane;

	// private final static Logger log =
	// Logger.getLogger(JobRecommenderPanel.class);

	private static JTextField tfNumRecommendations;

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
		shellScrollPane.setBounds(25, 88, 430, 243);
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
				shellTextPane.setText(ShowCommandlineBuilder.buildCommandline()
						.toString());
			}
		});

		JLabel lblNumRecommendations = new JLabel("Num. Recommendations");
		lblNumRecommendations.setBounds(25, 50, 126, 14);
		add(lblNumRecommendations);

		tfNumRecommendations = new JTextField();
		tfNumRecommendations.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNumRecommendations.setText("10");
		tfNumRecommendations.setBounds(169, 47, 45, 20);
		add(tfNumRecommendations);
		tfNumRecommendations.setColumns(10);
		tfNumRecommendations.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Integer i = Integer.valueOf(text);
					if (i > 0) {
						tfNumRecommendations.setBackground(Color.WHITE);
						return true;
					} else {
						MainGUI.writeResult(
								"No. Recommendations has to be an integer number bigger than 0 (DEFAULT = 10).",
								Constants.Log.ERROR);
						tfNumRecommendations.setBackground(new Color(240, 128,
								128));
						return false;
					}
				} catch (NumberFormatException e) {
					MainGUI.writeResult(
							"No. Recommendations has to be an integer number bigger than 0 (DEFAULT = 10).",
							Constants.Log.ERROR);
					tfNumRecommendations
							.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfNumRecommendations.getDocument().addDocumentListener(
				new TextFieldChangeListener());

	}

	protected void writeResultRecommender(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				MainGUI.writeResult("Recommendation: " + line,
						Constants.Log.RESULT);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
			e.printStackTrace();
		}

	}

	protected void writeFactorizerError(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			if (line != null) {
				MainGUI.writeResult("Factorizer RMSE: " + line,
						Constants.Log.RESULT);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			MainGUI.writeResult(e.getMessage(), Constants.Log.ERROR);
			e.printStackTrace();
		}
	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static String getNumRecommendations() {
		if (StringUtils.isNotBlank(tfNumRecommendations.getText())) {
			return tfNumRecommendations.getText();
		} else {
			return " ";
		}
	}

}
