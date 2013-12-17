package easyMahout.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;
import easyMahout.utils.ImagePanel;

public class AboutUsPopupDialogBox extends JFrame {

	private static final long serialVersionUID = 1L;

	JFrame frmAboutEasymahout;

	public AboutUsPopupDialogBox() {
		super("About easyMahout");
		frmAboutEasymahout = this;
		this.setMaximumSize(new Dimension(520, 350));
		this.setMinimumSize(new Dimension(520, 350));
		this.setAlwaysOnTop(true);
		this.setType(Type.POPUP);
		this.setSize(520, 350);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/easyMahout/GUI/images/mahoutIcon45.png")));
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		JPanel panelIcon = new JPanel();
		panelIcon.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIcon.setBounds(2, 170, 500, 140);
		this.getContentPane().add(panelIcon);
		panelIcon.setLayout(null);

		ImagePanel imageMahoutPanel = new ImagePanel(310, 140, "/easyMahout/GUI/images/apacheMahout.png");
		imageMahoutPanel.setLayout(null);
		imageMahoutPanel.setBounds(5, 0, 310, 140);
		panelIcon.add(imageMahoutPanel);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAboutEasymahout.setVisible(false);
				frmAboutEasymahout.dispose();
			}
		});
		btnOk.setBounds(400, 106, 89, 23);
		panelIcon.add(btnOk);

		JPanel panelText = new JPanel();
		panelText.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelText.setBounds(2, 2, 500, 166);
		this.getContentPane().add(panelText);
		panelText.setLayout(null);

		JLabel lblDescription = new JLabel("EasyMahout GUI for begginers and advanced users.");
		lblDescription.setBounds(20, 10, 293, 14);
		panelText.add(lblDescription);

		JLabel lblApache = new JLabel("Powered by Apache Mahout and Apache Hadoop.");
		lblApache.setBounds(20, 100, 386, 14);
		panelText.add(lblApache);

		JLabel lblLinkMahout = new JLabel("<html><a href=\"http://mahout.apache.org/\">http://mahout.apache.org/</a></html>");
		lblLinkMahout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLinkMahout.setBounds(20, 115, 185, 14);
		panelText.add(lblLinkMahout);
		lblLinkMahout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://mahout.apache.org/"));
				} catch (URISyntaxException | IOException ex) {
					// TODO log error
				}
			}
		});

		JLabel lblLinkHadoop = new JLabel("<html><a href=\"http://hadoop.apache.org/\">http://hadoop.apache.org/</a></html>");
		lblLinkHadoop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLinkHadoop.setBounds(20, 130, 131, 14);
		panelText.add(lblLinkHadoop);
		lblLinkHadoop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://hadoop.apache.org/"));
				} catch (URISyntaxException | IOException ex) {
					// TODO log error
				}
			}
		});

		JLabel lblVersion = new JLabel("Version: " + Constants.EasyMahout.VERSION + " - " + Constants.EasyMahout.VERSION_DATE);
		lblVersion.setBounds(20, 40, 169, 14);
		panelText.add(lblVersion);

		JLabel lblLicense = new JLabel("(c) License");
		lblLicense.setBounds(20, 55, 77, 14);
		panelText.add(lblLicense);

		JLabel lblVisit = new JLabel("Visit ");
		lblVisit.setBounds(20, 70, 32, 14);
		panelText.add(lblVisit);

		JLabel lblLinkEasy = new JLabel("<html><a href=\"" + Constants.EasyMahout.WEBSITE + "\">" + Constants.EasyMahout.WEBSITE
				+ "<a></html>");
		lblLinkEasy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLinkEasy.setBounds(45, 70, 231, 14);
		panelText.add(lblLinkEasy);
		lblLinkEasy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(Constants.EasyMahout.WEBSITE));
				} catch (URISyntaxException | IOException ex) {
					// TODO log error
				}
			}
		});
	}
}
