package easyMahout.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.FadingUtils;

public class HelpTooltip extends BalloonTip {

	private static final long serialVersionUID = 1L;

	private boolean shown = false;

	private HelpTooltip balloon;
	
	private JLabel labelHelp;
	
	private JScrollPane helpScrollPane;

	public HelpTooltip(JComponent attachedComponent, String text) {
		super();

		labelHelp = new JLabel(text);
		labelHelp.setOpaque(true);
		labelHelp.setBackground(SystemColor.info);

		labelHelp.setVerticalAlignment(SwingConstants.TOP);

		helpScrollPane = new JScrollPane(labelHelp);
		helpScrollPane.setBackground(SystemColor.info);
		helpScrollPane.setPreferredSize(new Dimension(420, 310));
		//helpScrollPane.getComponents();

		this.setup(attachedComponent,
				helpScrollPane,
				new RoundedBalloonStyle(5, 5, SystemColor.info, Color.BLACK),
				setupPositioner(BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 38, 10),
				getDefaultCloseButton());

		this.setPadding(10);
		this.setOpacity(0.0f);
		this.setVisible(false);

		balloon = this;

		((JButton) this.getAttachedComponent()).addActionListener(new ActionListener() {

			private ActionListener onStop = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					shown = !shown;
					// balloon.getAttachedComponent().setEnabled(true);
				}
			};

			public void actionPerformed(ActionEvent e) {
				// balloon.getAttachedComponent().setEnabled(false);
				if (shown) {
					FadingUtils.fadeOutBalloon(balloon, null, 500, 24);
					shown = false;
					balloon.setVisible(false);
					// balloon.setVisible(false);
				} else {
					FadingUtils.fadeInBalloon(balloon, onStop, 500, 24);
					// balloon.setVisible(true);
				}
			}

		});

		this.setCloseButton(BalloonTip.getDefaultCloseButton(), false);
		this.getCloseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shown) {
					// balloon.getAttachedComponent().setEnabled(true);
					shown = false;
				}
			}
		});

	}
	
	public void disable(){
		this.setVisible(false);
		this.shown = false;		
	}
	
	public void setText(String text){
		labelHelp = new JLabel(text);
		labelHelp.setOpaque(true);
		labelHelp.setBackground(SystemColor.info);

		labelHelp.setVerticalAlignment(SwingConstants.TOP);

		helpScrollPane = new JScrollPane(labelHelp);
		helpScrollPane.setBackground(SystemColor.info);
		helpScrollPane.setPreferredSize(new Dimension(420, 310));
		
		this.setContents(helpScrollPane);
	}

}
