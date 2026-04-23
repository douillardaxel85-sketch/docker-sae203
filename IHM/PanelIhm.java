package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelIhm extends JPanel implements ActionListener
{
	private JTextField txtFichier;
	private JButton btnEnvoyer;

	public PanelIhm()
	{
		this.setLayout(new FlowLayout());

		this.txtFichier = new JTextField(20);
		this.btnEnvoyer = new JButton("Envoyer le fichier");

		this.add(new JLabel("Nom du fichier :"));
		this.add(txtFichier);
		this.add(btnEnvoyer);

		this.btnEnvoyer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnEnvoyer)
			new Client("localhost", 8080, txtFichier.getText());
	}
}