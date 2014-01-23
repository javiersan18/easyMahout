package easyMahout.GUI.recommender.inputDialogs;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.model.DataModel;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.listeners.TextFieldChangeListener;

public class ALSWRFactorizerInputDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame frmALSWRFactorizerInputDialog;

	private static JTextField tfNoFeatures;

	private static JTextField tfNoIterations;

	private static JTextField tfLambda;

	private static JTextField tfNoTrainingThreads;

	private JLabel lblNoTrainingThreads;

	private static JRadioButton rdbtnOption1;

	private static JRadioButton rdbtnOption2;

	// private static JRadioButton rdbtnOption3;

	private JLabel lblLambda;

	private JLabel lblNoIterations;

	private JLabel lblNoFeatures;

	private final static Logger log = Logger.getLogger(ALSWRFactorizerInputDialog.class);

	public ALSWRFactorizerInputDialog() {
		super("ALSWR Factorizer Inputs");
		setResizable(false);
		frmALSWRFactorizerInputDialog = this;
		this.setAlwaysOnTop(true);
		this.setType(Type.POPUP);
		this.setSize(510, 340);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/easyMahout/GUI/images/mahoutIcon45.png")));
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onClose();
			}
		});

		ButtonGroup optionsButtonGroup = new ButtonGroup();

		JLabel lblParameters = new JLabel("Parameters options");
		lblParameters.setBounds(37, 22, 103, 14);
		getContentPane().add(lblParameters);

		rdbtnOption1 = new JRadioButton("Option 1");
		rdbtnOption1.setBounds(166, 18, 72, 23);
		getContentPane().add(rdbtnOption1);
		optionsButtonGroup.add(rdbtnOption1);
		rdbtnOption1.setSelected(true);
		rdbtnOption1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// lblAlpha.setEnabled(false);
				// tfAlpha.setEnabled(false);
				lblNoTrainingThreads.setEnabled(false);
				tfNoTrainingThreads.setEnabled(false);
			}
		});

		rdbtnOption2 = new JRadioButton("Option 2");
		rdbtnOption2.setBounds(240, 18, 72, 23);
		getContentPane().add(rdbtnOption2);
		optionsButtonGroup.add(rdbtnOption2);
		rdbtnOption2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// lblAlpha.setEnabled(true);
				// tfAlpha.setEnabled(true);
				lblNoTrainingThreads.setEnabled(true);
				tfNoTrainingThreads.setEnabled(true);
			}
		});

		// rdbtnOption3 = new JRadioButton("Option 3");
		// rdbtnOption3.setBounds(314, 18, 72, 23);
		// getContentPane().add(rdbtnOption3);
		// optionsButtonGroup.add(rdbtnOption3);
		// rdbtnOption3.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// lblAlpha.setEnabled(true);
		// tfAlpha.setEnabled(true);
		// lblNoTrainingThreads.setEnabled(true);
		// tfNoTrainingThreads.setEnabled(true);
		// }
		// });

		JSeparator separator = new JSeparator();
		separator.setBounds(37, 50, 435, 2);
		getContentPane().add(separator);

		lblNoFeatures = new JLabel("No. Features");
		lblNoFeatures.setBounds(37, 70, 63, 14);
		getContentPane().add(lblNoFeatures);

		tfNoFeatures = new JTextField();
		tfNoFeatures.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNoFeatures.setBounds(166, 67, 62, 20);
		getContentPane().add(tfNoFeatures);
		tfNoFeatures.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Integer i = Integer.valueOf(text);
					if (i > 0) {
						tfNoFeatures.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("No. Features has to be an integer number bigger than 0.", Constants.Log.ERROR);
						tfNoFeatures.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost.");
					MainGUI.writeResult("No. Features has to be an integer number bigger than 0.", Constants.Log.ERROR);
					tfNoFeatures.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfNoFeatures.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblNoIterations = new JLabel("No. Iterations");
		lblNoIterations.setBounds(37, 100, 67, 14);
		getContentPane().add(lblNoIterations);

		tfNoIterations = new JTextField();
		tfNoIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNoIterations.setColumns(5);
		tfNoIterations.setBounds(166, 97, 62, 20);
		getContentPane().add(tfNoIterations);
		tfNoIterations.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Integer i = Integer.valueOf(text);
					if (i > 0) {
						tfNoIterations.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("No. Iterations has to be an integer number bigger than 0.", Constants.Log.ERROR);
						tfNoIterations.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost.");
					MainGUI.writeResult("No. Iterations has to be an integer number bigger than 0.", Constants.Log.ERROR);
					tfNoIterations.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfNoIterations.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblLambda = new JLabel("Lambda");
		lblLambda.setBounds(37, 130, 37, 14);
		getContentPane().add(lblLambda);

		tfLambda = new JTextField();
		tfLambda.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLambda.setColumns(5);
		tfLambda.setBounds(166, 127, 62, 20);
		getContentPane().add(tfLambda);
		tfLambda.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Double d = Double.valueOf(text);
					if (d >= 0 && d <= 1) {
						tfLambda.setBackground(Color.WHITE);
						return true;
					} else {
						log.error(text + " is out of range");
						MainGUI.writeResult("Lambda factor has to be a real number in range [0..1].", Constants.Log.ERROR);
						tfLambda.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					log.error(text + " is not a number, focus not lost.");
					MainGUI.writeResult("Lambda factor has to be a real number in range [0..1].", Constants.Log.ERROR);
					tfLambda.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		tfLambda.getDocument().addDocumentListener(new TextFieldChangeListener());

		lblNoTrainingThreads = new JLabel("No. Training Threads");
		lblNoTrainingThreads.setBounds(37, 160, 100, 14);
		getContentPane().add(lblNoTrainingThreads);
		lblNoTrainingThreads.setEnabled(false);

		tfNoTrainingThreads = new JTextField();
		tfNoTrainingThreads.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNoTrainingThreads.setColumns(5);
		tfNoTrainingThreads.setBounds(166, 157, 62, 20);
		getContentPane().add(tfNoTrainingThreads);
		tfNoTrainingThreads.setEnabled(false);
		tfNoTrainingThreads.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				if (StringUtils.isNotBlank(text)) {
					try {
						Integer i = Integer.valueOf(text);
						if (i > 0) {
							tfNoTrainingThreads.setBackground(Color.WHITE);
							return true;
						} else {
							log.error(text + " is out of range");
							MainGUI.writeResult("No. Training Threads has to be an integer numbre bigger than 0.", Constants.Log.ERROR);
							tfNoTrainingThreads.setBackground(new Color(240, 128, 128));
							return false;
						}
					} catch (NumberFormatException e) {
						log.error(text + " is not a number, focus not lost.");
						MainGUI.writeResult("No. Training Threads has to be an integer numbre bigger than 0.", Constants.Log.ERROR);
						tfNoTrainingThreads.setBackground(new Color(240, 128, 128));
						return false;
					}
				} else {
					return true;
				}
			}
		});
		tfNoTrainingThreads.getDocument().addDocumentListener(new TextFieldChangeListener());

		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(383, 265, 89, 23);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});
	}

	private void onClose() {
		Color errorColor = new Color(240, 128, 128);
		if (!tfNoFeatures.getBackground().equals(errorColor) && !tfNoIterations.getBackground().equals(errorColor)
				&& !tfLambda.getBackground().equals(errorColor) && !tfLambda.getBackground().equals(errorColor)
				&& !tfNoTrainingThreads.getBackground().equals(errorColor)) {

			frmALSWRFactorizerInputDialog.setVisible(false);
		}

	}

	public static int getNoFeatures() {
		return Integer.valueOf(tfNoFeatures.getText());
	}

	public static int getNoIterations() {
		return Integer.valueOf(tfNoIterations.getText());
	}

	public static int getNoTrainingThreads() {
		return Integer.valueOf(tfNoTrainingThreads.getText());
	}

	public static double getLambda() {
		return Double.valueOf(tfLambda.getText());
	}

	public static Factorizer getFactorizer() {
		DataModel model = DataModelRecommenderPanel.getDataModel();
		if (model != null) {
			if (rdbtnOption1.isSelected()) {
				try {
					return new ALSWRFactorizer(model, getNoFeatures(), getLambda(), getNoIterations());
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (rdbtnOption2.isSelected()) {
				try {
					return new ALSWRFactorizer(model, getNoFeatures(), getLambda(), getNoIterations(), true,);
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			MainGUI.writeResult("Trying to build a Factorizer without Data Model.", Constants.Log.ERROR);
			return null;
		}
		return null;
	}
}
