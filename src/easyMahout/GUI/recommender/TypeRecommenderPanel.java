package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import org.apache.commons.io.IOUtils;
import java.awt.SystemColor;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxType;

	public TypeRecommenderPanel() {
		super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Type of recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED }));
		comboBoxType.setBounds(38, 36, 141, 20);
		add(comboBoxType);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help balloon tip

		FileInputStream fis;
		URL url = this.getClass().getResource("/easyMahout/GUI/images/item.html");
		System.out.println(url);

		// fis = new
		// FileInputStream(this.getClass().getResourceAsStream("item.html"));
		String tolltipText = "";
		try {
			InputStream is = this.getClass().getResourceAsStream("/easyMahout/GUI/images/item.html");
			tolltipText = IOUtils.toString(is, "UTF-8");
			System.out.println(tolltipText);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel labelHelp = new JLabel(tolltipText);
		labelHelp.setOpaque(true);
		labelHelp.setBackground(SystemColor.info);
		
		// labelHelp.setLineWrap(true);
		// labelHelp.setBackground(new Color(153, 204, 255));
		labelHelp.setVerticalAlignment(SwingConstants.TOP);
		//labelHelp.setPreferredSize(new Dimension(300, 300));
		// labelHelp.setLineWrap(true);
		// labelHelp.setSize(new Dimension(100, 100));
		// labelHelp.setMaximumSize(new Dimension(300, 300));

		JScrollPane logScrollPane = new JScrollPane(labelHelp);
		logScrollPane.setBackground(SystemColor.info);
		
		// logScrollPane.setBounds(2, 463, 100, 100);
		logScrollPane.setPreferredSize(new Dimension(420, 310));
		// JTextPane logTextPane = new JTextPane();
		//logScrollPane.setViewportView(labelHelp);
		// //logTextPane.setBackground(Color.WHITE);
		// //logTextPane.setBounds(42, 501, 90, 90);
		// //logTextPane.setPreferredSize(new Dimension(400, 310));
		// logTextPane.setEditable(false);
		// //logTextPane.
		// logTextPane.setContentType("text/html");
		// logTextPane.setText(tolltipText);

		final HelpTooltip helpTooltip = new HelpTooltip(btnHelp, logScrollPane);
		add(helpTooltip);

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) ((JComboBox) e.getSource()).getSelectedItem();
				RecommenderJPanel.getSimilarityPanel().setModelSimilarity(type);
				if (type.equals(Constants.RecommType.ITEMBASED)) {
					RecommenderJPanel.setEnableNeighborhood(false);
				} else if (type.equals(Constants.RecommType.USERBASED)) {
					RecommenderJPanel.setEnableNeighborhood(true);
				}
			}
		});

	}

	public String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}
}
