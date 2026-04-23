package IHM;

import java.awt.event.*;
import javax.swing.*;

public class FrameIhm extends JFrame
{
	private JPanel panelApp;

	public FrameIhm()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("envoi de fichier");
		this.setSize(500, 300);

		panelApp = new PanelIhm();
		this.add(panelApp);

		this.setVisible(true);
	}

	public static void main(String[] a)
	{
		new FrameIhm();
	}
}