package easyMahout.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import MahoutInAction.Recommender.Recommender22;

public class mainGUI {

	private JFrame frmEasymahout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGUI window = new mainGUI();
					window.frmEasymahout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEasymahout = new JFrame();
		frmEasymahout.setTitle("easyMahout 0.1");

		frmEasymahout.setMinimumSize(new Dimension(750, 650));
		frmEasymahout.setBounds(100, 100, 95, 279);

		frmEasymahout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEasymahout.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 714, 453);
		frmEasymahout.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Recommendation", null, panel, null);
		panel.setLayout(null);

		JLabel lblType = new JLabel("Type");
		lblType.setBounds(12, 12, 27, 16);
		panel.add(lblType);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(57, 8, 94, 25);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based" }));
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Classification", null, panel_1, null);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(10, 462, 714, 122);
		frmEasymahout.getContentPane().add(formattedTextField);

		JTextPane log = new JTextPane();

		log.setBounds(12, 462, 712, 118);
		frmEasymahout.getContentPane().add(log);

		JMenuBar menuBar = new JMenuBar();
		frmEasymahout.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JButton btnRun = new JButton("Run!");
		btnRun.setBounds(599, 387, 98, 26);
		panel.add(btnRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Recommender22.main(null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}
}
