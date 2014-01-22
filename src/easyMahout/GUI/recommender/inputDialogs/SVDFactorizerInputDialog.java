package easyMahout.GUI.recommender.inputDialogs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

public class SVDFactorizerInputDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame frmFactorizerInputDialog;

	private static JTextField tfNoFeatures;

	private static JTextField tfNoIterations;

	private static JTextField tfLambda;

	private static JTextField tfAlpha;

	private static JTextField tfNoTrainingThreads;

	private JLabel lblAlpha;

	private JLabel lblNoTrainingThreads;

	private static JRadioButton rdbtnOption1;

	private static JRadioButton rdbtnOption2;

	private static JRadioButton rdbtnOption3;

	private JLabel lblLambda;

	private JLabel lblNoIterations;

	private JLabel lblNoFeatures;

	public SVDFactorizerInputDialog() {
		super("SVD Factorizer Inputs");
		setResizable(false);
		frmFactorizerInputDialog = this;
		this.setAlwaysOnTop(true);
		this.setType(Type.POPUP);
		this.setSize(510, 340);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/easyMahout/GUI/images/mahoutIcon45.png")));
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

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
				lblAlpha.setEnabled(false);
				tfAlpha.setEnabled(false);
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
				lblAlpha.setEnabled(true);
				tfAlpha.setEnabled(true);
				lblNoTrainingThreads.setEnabled(false);
				tfNoTrainingThreads.setEnabled(false);
			}
		});

		rdbtnOption3 = new JRadioButton("Option 3");
		rdbtnOption3.setBounds(314, 18, 72, 23);
		getContentPane().add(rdbtnOption3);
		optionsButtonGroup.add(rdbtnOption3);
		rdbtnOption3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAlpha.setEnabled(true);
				tfAlpha.setEnabled(true);
				lblNoTrainingThreads.setEnabled(true);
				tfNoTrainingThreads.setEnabled(true);
			}
		});

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

		lblNoIterations = new JLabel("No. Iterations");
		lblNoIterations.setBounds(37, 100, 67, 14);
		getContentPane().add(lblNoIterations);

		tfNoIterations = new JTextField();
		tfNoIterations.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNoIterations.setColumns(5);
		tfNoIterations.setBounds(166, 97, 62, 20);
		getContentPane().add(tfNoIterations);

		lblLambda = new JLabel("Lambda");
		lblLambda.setBounds(37, 130, 37, 14);
		getContentPane().add(lblLambda);

		tfLambda = new JTextField();
		tfLambda.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLambda.setColumns(5);
		tfLambda.setBounds(166, 127, 62, 20);
		getContentPane().add(tfLambda);

		lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(37, 160, 27, 14);
		getContentPane().add(lblAlpha);
		lblAlpha.setEnabled(false);

		tfAlpha = new JTextField();
		tfAlpha.setText("40");
		tfAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
		tfAlpha.setColumns(5);
		tfAlpha.setBounds(166, 157, 62, 20);
		getContentPane().add(tfAlpha);
		tfAlpha.setEnabled(false);

		lblNoTrainingThreads = new JLabel("No. Training Threads");
		lblNoTrainingThreads.setBounds(37, 190, 100, 14);
		getContentPane().add(lblNoTrainingThreads);
		lblNoTrainingThreads.setEnabled(false);

		tfNoTrainingThreads = new JTextField();
		tfNoTrainingThreads.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNoTrainingThreads.setColumns(5);
		tfNoTrainingThreads.setBounds(166, 187, 62, 20);
		getContentPane().add(tfNoTrainingThreads);
		tfNoTrainingThreads.setEnabled(false);

		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(383, 265, 89, 23);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO CONFIRMAR VARIABLES
				frmFactorizerInputDialog.setVisible(false);
			}
		});

	}

	public static int getSelectedOption() {
		if (rdbtnOption1.isSelected()) {
			return 1;
		} else if (rdbtnOption2.isSelected()) {
			return 2;
		} else if (rdbtnOption3.isSelected()) {
			return 3;
		} else {
			// Error
			return 0;
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

	public static double getAlpha() {
		return Double.valueOf(tfAlpha.getText());
	}

}
