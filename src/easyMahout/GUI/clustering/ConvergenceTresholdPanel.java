package easyMahout.GUI.clustering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;

import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

public class ConvergenceTresholdPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel number;

	private JTextField fieldNum;

	private JLabel number2;

	private JTextField fieldNum2;

	private static double convergenceTreshold;

	private double convergenceTreshold2;

	private final static Logger log = Logger.getLogger(ConvergenceTresholdPanel.class);

	private HelpTooltip helpTooltip;

	private final static String HELP = "[0..1]";

	public ConvergenceTresholdPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Convergence Treshold", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		number = new JLabel("Convergence Treshold:");
		number.setBounds(38, 36, 189, 14);
		add(number);

		fieldNum = new JTextField();
		fieldNum.setBounds(38, 61, 86, 23);
		fieldNum.setToolTipText(HELP);
		add(fieldNum);
		fieldNum.setColumns(10);
		fieldNum.setText("0.9");
		fieldNum.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double i = Double.parseDouble(text);
					if (i >= 0 && i <= 1) {
						fieldNum.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("Size has to be a number in range [0..1]", Constants.Log.ERROR);
						fieldNum.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("Size has to be a number in range [0..1]", Constants.Log.ERROR);
					fieldNum.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		// add a new treshold section
		number2 = new JLabel("Convergence Treshold 2:");
		number2.setBounds(38, 106, 189, 14);

		fieldNum2 = new JTextField();
		fieldNum2.setBounds(38, 131, 86, 23);

		fieldNum2.setColumns(10);
		fieldNum2.setToolTipText(HELP);
		number2.setVisible(false);
		fieldNum2.setVisible(false);

		add(number2);
		add(fieldNum2);
		fieldNum2.setText("0.9");
		fieldNum2.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double i = Double.parseDouble(text);
					if (i >= 0 && i <= 1) {
						fieldNum2.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("Size has to be a real number in range [0..1]", Constants.Log.ERROR);
						fieldNum2.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost");
					MainGUI.writeResult("Size has to be a real number in range [0..1]", Constants.Log.ERROR);
					fieldNum2.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});

		if (MainClusterPanel.isCanopy()) {

			number2.setVisible(true);
			fieldNum2.setVisible(true);
		} else {
			number2.setVisible(false);
			fieldNum2.setVisible(false);
		}

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_THRESHOLD);
		add(helpTooltip);

	}

	public static boolean isInteger(String s) {
		try {
			Double.parseDouble(s);

		} catch (NumberFormatException e) {
			return false;
		}
		// if exception isn't thrown, then it is an integer
		return true;
	}

	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public static double  getConvergenceTreshold() {
		return convergenceTreshold;
	}

	public void setConvergenceTreshold(double convergenceTreshold) {
		this.convergenceTreshold = convergenceTreshold;
	}

	public JTextField getCampoNum() {
		return fieldNum;
	}

	public void setCampoNum(String s) {
		fieldNum.setText(s);
	}

	public void hazVisible(boolean b) {
		number2.setVisible(b);
		fieldNum2.setVisible(b);
	}

}
