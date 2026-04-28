package backend;

import java.io.*;
import java.net.*;

public class ServeurDeFichiers
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket socketServeur = new ServerSocket(8080);
			System.out.println("Service de fichiers actif sur le port 8080");

			while (true)
			{
				Socket socketClient = socketServeur.accept();
				new Thread(() -> gerer(socketClient)).start();
			}
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur serveur : " + erreur.getMessage());
		}
	}

	public static void gerer(Socket socketClient)
	{
		try
		{
			DataInputStream fluxEntree = new DataInputStream(socketClient.getInputStream());
			DataOutputStream fluxSortie = new DataOutputStream(socketClient.getOutputStream());

			String commande = fluxEntree.readUTF();

			if (commande.equals("LISTE"))
				envoyerListe(fluxSortie);

			if (commande.equals("UPLOAD"))
				recevoirFichier(fluxEntree);

			if (commande.equals("DOWNLOAD"))
				envoyerFichier(fluxEntree, fluxSortie);

			socketClient.close();
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur socket client");
		}
	}

	public static void envoyerListe(DataOutputStream fluxSortie)
	{
		try
		{
			File dossier = new File("/app/partage");
			File[] tableauFichiers = dossier.listFiles();

			if (tableauFichiers != null)
			{
				fluxSortie.writeInt(tableauFichiers.length);
				for (File fichierCourant : tableauFichiers)
					fluxSortie.writeUTF(fichierCourant.getName());
			}
			else
				fluxSortie.writeInt(0);
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur envoi liste");
		}
	}

	public static void recevoirFichier(DataInputStream fluxEntree)
	{
		try
		{
			String nomFichier = fluxEntree.readUTF();
			long tailleAttendue = fluxEntree.readLong();

			FileOutputStream ecrivainFichier = new FileOutputStream("/app/partage/" + nomFichier);
			byte[] tampon = new byte[4096];
			int octetsLus;
			long cumulRecu = 0;

			while (cumulRecu < tailleAttendue && (octetsLus = fluxEntree.read(tampon)) != -1)
			{
				ecrivainFichier.write(tampon, 0, octetsLus);
				cumulRecu += octetsLus;
			}

			ecrivainFichier.close();
			System.out.println("Fichier recu : " + nomFichier);
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur reception");
		}
	}

	public static void envoyerFichier(DataInputStream fluxEntree, DataOutputStream fluxSortie)
	{
		try
		{
			String nomDemande = fluxEntree.readUTF();
			File fichierCible = new File("/app/partage/" + nomDemande);

			if (fichierCible.exists())
			{
				fluxSortie.writeLong(fichierCible.length());
				FileInputStream lecteurFichier = new FileInputStream(fichierCible);
				byte[] tampon = new byte[4096];
				int octetsLus;

				while ((octetsLus = lecteurFichier.read(tampon)) != -1)
					fluxSortie.write(tampon, 0, octetsLus);

				lecteurFichier.close();
			}
			else
				fluxSortie.writeLong(-1);
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur envoi");
		}
	}

	public static void lancer(int port, String dossier, javax.swing.JTextArea logs)
	{
		logs.append("Mode Docker actif.\n");
	}

	public static void arreter(javax.swing.JTextArea logs)
	{
		logs.append("Service arrete.\n");
	}
}