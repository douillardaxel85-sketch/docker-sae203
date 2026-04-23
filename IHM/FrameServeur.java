package IHM;

import javax.swing.*;

public class FrameServeur extends JFrame
{
	public FrameServeur()
	{
		this.setTitle("Configuration Serveur");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new PanelServeur());
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		new FrameServeur();
	}
}