package easyMahout.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

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
		tabbedPane.setBounds(10, 11, 714, 569);
		frmEasymahout.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(20);
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setAlignment(FlowLayout.LEADING);
		tabbedPane.addTab("Recommendation", null, panel, null);
		
		JLabel lblType = new JLabel("Type");
		panel.add(lblType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"User-based", "Item-based"}));
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Classification", null, panel_1, null);
		
		JMenuBar menuBar = new JMenuBar();
		frmEasymahout.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
}
