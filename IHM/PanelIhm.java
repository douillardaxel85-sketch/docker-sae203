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
	private JTextField champIp, champPort;
	private DefaultListModel<String> modeleListe;
	private JList<String> composantListe;
	private JButton boutonActu, boutonDl;

	public PanelIhm()
	{
		this.setLayout(new BorderLayout());

		JPanel panelEntete = new JPanel();
		panelEntete.add(new JLabel("IP :"));
		champIp = new JTextField("localhost", 10);
		panelEntete.add(champIp);
		panelEntete.add(new JLabel("Port :"));
		champPort = new JTextField("8080", 5);
		panelEntete.add(champPort);
		this.add(panelEntete, BorderLayout.NORTH);

		modeleListe = new DefaultListModel<String>();
		composantListe = new JList<String>(modeleListe);
		composantListe.setBorder(BorderFactory.createTitledBorder("Fichiers du Serveur"));

		composantListe.setTransferHandler(new TransferHandler() 
		{
			public boolean canImport(TransferSupport support) 
			{ return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor); }

			public boolean importData(TransferSupport support) 
			{
				try 
				{
					List<File> fichiersDeposes = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					Client clientLocal = new Client(champIp.getText(), Integer.parseInt(champPort.getText()));
					
					for (File fichier : fichiersDeposes) 
						clientLocal.envoyerFichier(fichier);
						
					rafraichir();
					return true;
				} catch (Exception erreur) { return false; }
			}
		});

		this.add(new JScrollPane(composantListe), BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel();
		boutonActu = new JButton("Actualiser");
		boutonDl = new JButton("Telecharger");
		boutonActu.addActionListener(this);
		boutonDl.addActionListener(this);
		panelBoutons.add(boutonActu); 
		panelBoutons.add(boutonDl);
		this.add(panelBoutons, BorderLayout.SOUTH);

		rafraichir();
	}

	private void rafraichir()
	{
		try
		{
			Client clientLocal = new Client(champIp.getText(), Integer.parseInt(champPort.getText()));
			List<String> nomsRecuperes = clientLocal.demanderListe();
			
			modeleListe.clear();
			for (String nomFichier : nomsRecuperes)
				modeleListe.addElement(nomFichier);
		}
		catch (Exception erreur)
		{
			modeleListe.clear();
		}
	}

	public void actionPerformed(ActionEvent evenement)
	{
		Client clientLocal = new Client(champIp.getText(), Integer.parseInt(champPort.getText()));
		
		if (evenement.getSource() == boutonActu) 
			rafraichir();
		
		if (evenement.getSource() == boutonDl && composantListe.getSelectedValue() != null)
		{
			clientLocal.telechargerFichier(composantListe.getSelectedValue());
			JOptionPane.showMessageDialog(this, "Fichier recupere !");
		}
	}
}