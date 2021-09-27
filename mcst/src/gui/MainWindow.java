package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {
	
	OutputPanel op = new OutputPanel();
	InputPanel ip = new InputPanel(op);
	JLabel credits;
	
	public MainWindow()
	{
		JPanel contentPanel = new JPanel();

		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		contentPanel.setBorder(padding);

		setContentPane(contentPanel);
		
		
		setDefaultLookAndFeelDecorated(true);
		setTitle("COVID Forecast");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		
		setMinimumSize(new Dimension(230, 200));
		setResizable(false);
		
		add(ip, BorderLayout.PAGE_START);
		add(op);
		
		credits = new JLabel("<html>Remember to wear the mask OVER your nose!<br>Data sourced from NYT and the CDC via the Covid Act Now API.</html>");
		credits.setFont(new Font("sans serif", Font.PLAIN, 10));
		add(credits, BorderLayout.PAGE_END);
		
		pack();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		if (action.equals("submit"))
		{
			System.out.println("omg");
		}
	}
}
