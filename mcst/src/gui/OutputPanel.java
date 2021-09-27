package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.ComponentOrientation;

@SuppressWarnings("serial")
public class OutputPanel extends JPanel {

	JLabel mrl, maskedRisk, umrl, unmaskedRisk, icucl, ICUcap;
	Font bigFont = new Font("sans serif", Font.BOLD, 40);
	
	public OutputPanel()
	{
		setLayout(new GridLayout(3, 2, 5, 5));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		mrl = new JLabel("Masked contraction odds:");
		mrl.setToolTipText("If you are exposed to a randomly selected sample of people from your county.");
		add(mrl);
		maskedRisk = new JLabel("?");
		maskedRisk.setFont(bigFont);
		add(maskedRisk);
		
		umrl = new JLabel("Unmasked contraction odds:");
		umrl.setAlignmentX(RIGHT_ALIGNMENT);
		add(umrl);
		unmaskedRisk = new JLabel("?");
		unmaskedRisk.setFont(bigFont);
		add(unmaskedRisk);
		
		icucl = new JLabel("Current ICU occupation:");
		icucl.setAlignmentX(RIGHT_ALIGNMENT);
		add(icucl);
		ICUcap = new JLabel("?");
		ICUcap.setFont(bigFont);
		add(ICUcap);
	}
	
}
