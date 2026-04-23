package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.util.List;
import backend.Client;

public class PanelIhm extends JPanel implements ActionListener
{
	private JTextField txtIp, txtPort;
	private DefaultListModel<String> mod;
	private JList<String> liste;
	private JButton btnActu, btnDl;

	public PanelIhm()
	{
		this.setLayout(new BorderLayout());

		JPanel h = new JPanel();
		h.add(new JLabel("IP :"));
		txtIp = new JTextField("localhost", 10);
		h.add(txtIp);
		h.add(new JLabel("Port :"));
		txtPort = new JTextField("8080", 5);
		h.add(txtPort);
		this.add(h, BorderLayout.NORTH);

		mod = new DefaultListModel<String>();
		liste = new JList<String>(mod);
		liste.setBorder(BorderFactory.createTitledBorder("Fichiers Serveur"));

		liste.setTransferHandler(new TransferHandler() 
		{
			public boolean canImport(TransferSupport s) 
			{ return s.isDataFlavorSupported(DataFlavor.javaFileListFlavor); }

			public boolean importData(TransferSupport s) 
			{
				try 
				{
					List<File> fichiers = (List<File>) s.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					Client c = new Client(txtIp.getText(), Integer.parseInt(txtPort.getText()));
					for (File f : fichiers) c.envoyerFichier(f);
					rafraichir();
					return true;
				} catch (Exception e) { return false; }
			}
		});

		this.add(new JScrollPane(liste), BorderLayout.CENTER);

		JPanel p = new JPanel();
		btnActu = new JButton("Actualiser");
		btnDl = new JButton("Telecharger");
		btnActu.addActionListener(this);
		btnDl.addActionListener(this);
		p.add(btnActu); p.add(btnDl);
		this.add(p, BorderLayout.SOUTH);

		rafraichir();
	}

	private void rafraichir()
	{
		try
		{
			Client c = new Client(txtIp.getText(), Integer.parseInt(txtPort.getText()));
			String res = c.demanderListe();
			mod.clear();
			for (String f : res.split(";"))
				if (!f.isEmpty()) mod.addElement(f);
		}
		catch (Exception e)
		{
			mod.clear();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		Client c = new Client(txtIp.getText(), Integer.parseInt(txtPort.getText()));
		if (e.getSource() == btnActu) rafraichir();
		if (e.getSource() == btnDl && liste.getSelectedValue() != null)
		{
			c.telechargerFichier(liste.getSelectedValue());
			JOptionPane.showMessageDialog(this, "Fichier recupere dans Downloads !");
		}
	}
}