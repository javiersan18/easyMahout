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
import easyMahout.enumTypes.RecommenderType;
import easyMahout.enumTypes.DataModelType;

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
								comboBox.setBounds(49, 8, 127, 25);
								comboBox.setModel(new DefaultComboBoxModel(RecommenderType.values()));								
								panel.add(comboBox);
								comboBox.addActionListener(new ActionListener() {											
									public void actionPerformed(ActionEvent e) {
																						
									}
								});
								
										JButton btnRun = new JButton("Run!");
										btnRun.setBounds(599, 387, 98, 26);
										panel.add(btnRun);
										
										JLabel lblDatamodel = new JLabel("Data Model");
										lblDatamodel.setBounds(186, 13, 62, 14);
										panel.add(lblDatamodel);
										
										JComboBox comboBox_1 = new JComboBox();
										comboBox_1.setModel(new DefaultComboBoxModel(DataModelType.values()));
										comboBox_1.setBounds(258, 10, 119, 20);
										panel.add(comboBox_1);
										comboBox_1.addActionListener(new ActionListener() {											
											public void actionPerformed(ActionEvent e) {
																								
											}
										});
										
										
										
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
	}
}
