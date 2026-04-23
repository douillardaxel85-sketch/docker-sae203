package IHM;

import javax.swing.*;

public class FrameIhm extends JFrame
{
	public FrameIhm()
	{
		this.setTitle("Partage de Fichiers SAE");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new PanelIhm());
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		new FrameIhm();
	}
}