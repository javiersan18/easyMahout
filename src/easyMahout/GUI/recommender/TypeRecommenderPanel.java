package easyMahout.GUI.recommender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;
import easyMahout.utils.IconHelpPanel;

import javax.swing.border.TitledBorder;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxType;

	public TypeRecommenderPanel() {
		// super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Type of recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);

		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED }));
		comboBoxType.setBounds(38, 36, 141, 20);
		add(comboBoxType);
		
		JPanel panel = new IconHelpPanel(25,25);
		panel.setBounds(190, 33, 25, 25);
		add(panel);

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

//	@Override
//	public void paintComponent(Graphics g) {
//		Dimension tamanio = getSize();
//		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/easyMahout/GUI/images/help.jpg"));
//		g.drawImage(imagenFondo.getImage(), 0, 0, tamanio.width, tamanio.height, null);
//		setOpaque(false);
//		super.paintComponent(g);
//	}

	public String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}
}
