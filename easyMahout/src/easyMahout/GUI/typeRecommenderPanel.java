package easyMahout.GUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class typeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;



	public typeRecommenderPanel() {
		super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);
	
		
		JLabel lblSelectTypeOf = new JLabel("Select type of recommender:");
		lblSelectTypeOf.setBounds(10, 11, 216, 14);
		add(lblSelectTypeOf);
		
		JComboBox comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { "User-based", "Item-based", "Clustering" }));
		comboBoxType.setBounds(10, 36, 141, 20);
		add(comboBoxType);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(462, 357, 11, 14);
		add(lblId);
		


		
	}
}
