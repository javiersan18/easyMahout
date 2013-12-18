package easyMahout.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.examples.complete.CompleteExample;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.styles.TexturedBalloonStyle;
import net.java.balloontip.utils.FadingUtils;

public class HelpTooltip extends BalloonTip {

	private static final long serialVersionUID = 1L;

	private boolean isShown = false;

	HelpTooltip bt;

	public HelpTooltip(JComponent attachedComponent, JComponent contents) {
		super(attachedComponent, contents, new EdgedBalloonStyle(Color.WHITE, Color.BLACK), BalloonTip.Orientation.LEFT_ABOVE,
				BalloonTip.AttachLocation.ALIGNED, 38, 10, true);

		BalloonTipStyle balloontipStyle = null;

		try {
			balloontipStyle = new TexturedBalloonStyle(5, 5, CompleteExample.class.getResource("/easyMahout/GUI/images/bgPattern.png"),
					Color.BLACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (balloontipStyle != null) {
			this.setStyle(balloontipStyle);
		}

		this.setPadding(10);

		this.setOpacity(0.0f);
		
		//this.setMaximumSize(new Dimension(100,100));

		bt = this;

		((JButton) bt.getAttachedComponent()).addActionListener(new ActionListener() {

			private ActionListener onStop = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bt.setShown(!bt.isShown());
				}
			};

			public void actionPerformed(ActionEvent e) {
				bt.getAttachedComponent().setEnabled(false);
				if (bt.isShown()) {
					FadingUtils.fadeOutBalloon(bt, null, 500, 24);
				} else {
					FadingUtils.fadeInBalloon(bt, onStop, 500, 24);
				}
			}

		});

		this.setCloseButton(BalloonTip.getDefaultCloseButton(), false);
		this.getCloseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bt.isShown()) {
					bt.getAttachedComponent().setEnabled(true);
					bt.setShown(false);
				}
			}
		});

	}

	public boolean isShown() {
		return isShown;
	}

	public void setShown(boolean isShown) {
		this.isShown = isShown;
	}

}
