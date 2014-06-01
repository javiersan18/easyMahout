package easyMahout.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import easyMahout.utils.xml.XMLPreferences;

public class PreferencesPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	JFrame frmPreferences;

	private static JTextField tfJavaHome;
	private static JTextField tfMahoutHome;
	private static JTextField tfHadoopHome;
	private static String javaHome;
	private static String mahoutHome;
	private static String hadoopHome;
	private static boolean apply;

	public PreferencesPanel() {
		super("EasyMahout Preferences");
		setAlwaysOnTop(true);
		setTitle("EasyMahout Preferences");
		frmPreferences = this;
		this.setSize(510, 340);
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				PreferencesPanel.class
						.getResource("/easyMahout/GUI/images/mahoutIcon.png")));
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		apply = false;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				javaHome = tfJavaHome.getText();
				mahoutHome = tfMahoutHome.getText();
				hadoopHome = tfHadoopHome.getText();
				apply = false;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				super.windowDeactivated(e);
				if (!apply) {
					onCloseCancel();
				}
			}

			public void windowClosing(WindowEvent evt) {
				if (!apply) {
					onCloseCancel();
				}
			}
		});

		JPanel panelButtons = new JPanel();
		getContentPane().add(panelButtons, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panelButtons.add(panel);

		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseCancel();
				apply = false;
			}
		});

		JButton btnApply = new JButton("Apply");
		panel.add(btnApply);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPreferences.setVisible(false);
				apply = true;
				XMLPreferences.saveXMLFile(System.getenv("PWD")+"/easyMahout1_lib/preferences.xml");
			}
		});

		JPanel panelPreferences = new JPanel();
		panelPreferences.setMinimumSize(new Dimension(15, 15));
		getContentPane().add(panelPreferences, BorderLayout.NORTH);
		panelPreferences
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblJavahome = new JLabel("JAVA_HOME");
		panelPreferences.add(lblJavahome, "2, 2, right, default");

		tfJavaHome = new JTextField();
		panelPreferences.add(tfJavaHome, "4, 2, fill, default");
		tfJavaHome.setColumns(10);
		//tfJavaHome.setText(Constants.EnviromentVariables.JAVA_HOME);

		JLabel lblMahouthome = new JLabel("MAHOUT_HOME");
		panelPreferences.add(lblMahouthome, "2, 4, right, default");

		tfMahoutHome = new JTextField();
		panelPreferences.add(tfMahoutHome, "4, 4, fill, default");
		tfMahoutHome.setColumns(10);
		//tfMahoutHome.setText(Constants.EnviromentVariables.MAHOUT_HOME);

		JLabel lblHadoophome = new JLabel("HADOOP_HOME");
		panelPreferences.add(lblHadoophome, "2, 6, right, default");

		tfHadoopHome = new JTextField();
		panelPreferences.add(tfHadoopHome, "4, 6, fill, default");
		tfHadoopHome.setColumns(10);
		//tfHadoopHome.setText(Constants.EnviromentVariables.HADOOP_HOME);
		
		XMLPreferences.loadXMLFile(System.getenv("PWD")+"/easyMahout1_lib/preferences.xml");		
	}

	protected void onCloseCancel() {
		tfJavaHome.setText(javaHome);
		tfMahoutHome.setText(mahoutHome);
		tfHadoopHome.setText(hadoopHome);
		frmPreferences.setVisible(false);
	}

	public static String getJavaHome() {
		return tfJavaHome.getText();
	}

	public static void setJavaHome(String tfJavaHome) {
		PreferencesPanel.tfJavaHome.setText(tfJavaHome);
	}

	public static String getMahoutHome() {
		return tfMahoutHome.getText();
	}

	public static void setMahoutHome(String tfMahoutHome) {
		PreferencesPanel.tfMahoutHome.setText(tfMahoutHome);
	}

	public static String getHadoopHome() {
		return tfHadoopHome.getText();
	}

	public static void setHadoopHome(String tfHadoopHome) {
		PreferencesPanel.tfHadoopHome.setText(tfHadoopHome);
	}
}
