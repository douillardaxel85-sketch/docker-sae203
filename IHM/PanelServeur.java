package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.ServeurDeFichiers;

public class PanelServeur extends JPanel implements ActionListener
{
	private JTextField txtPort, txtDossier;
	private JTextArea log;
	private JButton btnLancer, btnArreter;

	public PanelServeur()
	{
		this.setLayout(new BorderLayout());

		JPanel pNord = new JPanel(new GridLayout(2, 2));
		pNord.add(new JLabel("Port :"));
		txtPort = new JTextField("8080");
		pNord.add(txtPort);
		pNord.add(new JLabel("Dossier :"));
		txtDossier = new JTextField("partage");
		pNord.add(txtDossier);
		this.add(pNord, BorderLayout.NORTH);

		log = new JTextArea();
		log.setEditable(false);
		log.setBackground(Color.BLACK);
		log.setForeground(Color.GREEN);
		this.add(new JScrollPane(log), BorderLayout.CENTER);

		JPanel pSud = new JPanel();
		btnLancer = new JButton("Demarrer");
		btnArreter = new JButton("Arreter");
		btnArreter.setEnabled(false);
		btnLancer.addActionListener(this);
		btnArreter.addActionListener(this);
		pSud.add(btnLancer); pSud.add(btnArreter);
		this.add(pSud, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnLancer)
		{
			int port = Integer.parseInt(txtPort.getText());
			ServeurDeFichiers.lancer(port, txtDossier.getText(), log);
			btnLancer.setEnabled(false);
			btnArreter.setEnabled(true);
		}
		if (e.getSource() == btnArreter)
		{
			ServeurDeFichiers.arreter(log);
			btnLancer.setEnabled(true);
			btnArreter.setEnabled(false);
		}
	}
}