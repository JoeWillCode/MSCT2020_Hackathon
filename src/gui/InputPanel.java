package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import back.Forecast;

@SuppressWarnings("serial")
public class InputPanel extends JPanel implements ActionListener {

	OutputPanel op;
	JLabel zipInstruction, peopleInstruction;
	JTextField zipInput, peopleInput;
	JButton submit;
	DecimalFormat percentFormat = new DecimalFormat("0.0");
	
	public InputPanel(OutputPanel op)
	{
		this.op = op;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// ZIP input
		zipInstruction = new JLabel("Enter ZIP Code:");
		zipInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(zipInstruction);
		
		zipInput = new JTextField(6);
		zipInput.setMaximumSize(new Dimension(200, 200));
		zipInput.setFont(new Font("Sans serif", Font.BOLD, 40));
		zipInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(zipInput);
		
		peopleInstruction = new JLabel("Estimate how many people you'll be exposed to:");
		peopleInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(peopleInstruction);
		peopleInput = new JTextField(3);
		peopleInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		peopleInput.setMaximumSize(new Dimension(200, 200));
		peopleInput.setFont(new Font("Sans serif", Font.BOLD, 40));
		add(peopleInput);
		
		submit = new JButton("Forecast");
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.addActionListener(this);
		submit.setActionCommand("submit");
		add(submit);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		if (action.equals("submit"))
		{
			try {
				Forecast f = new Forecast(zipInput.getText(), peopleInput.getText());
				double[] values = f.getValues();
				op.maskedRisk.setText(percentFormat.format(values[1]) + "%");
				op.unmaskedRisk.setText(percentFormat.format(values[0]) + "%");
				if (values[2] != -1.0)
				{
					op.ICUcap.setText(percentFormat.format(values[2]) + "%");
				}
				else
				{
					op.ICUcap.setText("N/A");
				}
			} catch (Exception ex)
			{
				op.maskedRisk.setText("?");
				op.unmaskedRisk.setText("?");
				op.ICUcap.setText("?");
			}
		}
	}
}
