package backend;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class Client
{
	private String adresseServeur;
	private int portServeur;

	public Client(String adresseServeur, int portServeur)
	{
		this.adresseServeur = adresseServeur;
		this.portServeur = portServeur;
	}

	public List<String> demanderListe()
	{
		List<String> listeNomsFichiers = new ArrayList<>();

		try
		{
			Socket socketClient = new Socket(adresseServeur, portServeur);
			DataOutputStream fluxSortie = new DataOutputStream(socketClient.getOutputStream());
			DataInputStream fluxEntree = new DataInputStream(socketClient.getInputStream());

			fluxSortie.writeUTF("LISTE");

			int nombreFichiers = fluxEntree.readInt();

			for (int index = 0; index < nombreFichiers; index++)
				listeNomsFichiers.add(fluxEntree.readUTF());

			socketClient.close();
		}
		catch (IOException erreur)
		{
			System.out.println("Erreur client : " + erreur.getMessage());
		}
		return listeNomsFichiers;
	}

	public void envoyerFichier(File fichierAEnvoyer)
	{
		try
		{
			Socket socketEnvoi = new Socket(adresseServeur, portServeur);
			DataOutputStream fluxSortie = new DataOutputStream(socketEnvoi.getOutputStream());

			fluxSortie.writeUTF("UPLOAD");
			fluxSortie.writeUTF(fichierAEnvoyer.getName());
			fluxSortie.writeLong(fichierAEnvoyer.length());

			FileInputStream lecteurFichier = new FileInputStream(fichierAEnvoyer);
			byte[] tamponDonnees = new byte[4096];
			int octetsLus;

			while ((octetsLus = lecteurFichier.read(tamponDonnees)) != -1)
				fluxSortie.write(tamponDonnees, 0, octetsLus);

			lecteurFichier.close();
			socketEnvoi.close();
		}
		catch (Exception erreur)
		{
			System.out.println("Erreur upload");
		}
	}

	public void telechargerFichier(String nomFichierCible)
	{
		try
		{
			Socket socketReception = new Socket(adresseServeur, portServeur);
			DataOutputStream fluxSortie = new DataOutputStream(socketReception.getOutputStream());
			DataInputStream fluxEntree = new DataInputStream(socketReception.getInputStream());

			fluxSortie.writeUTF("DOWNLOAD");
			fluxSortie.writeUTF(nomFichierCible);
			
			long tailleFichier = fluxEntree.readLong();

			if (tailleFichier != -1)
			{
				String cheminDownloads = System.getProperty("user.home") + File.separator + "Downloads";
				File destination = new File(cheminDownloads, nomFichierCible);
				FileOutputStream ecrivainFichier = new FileOutputStream(destination);
				
				byte[] tamponDonnees = new byte[4096];
				int octetsLus; 
				long cumulOctetsRecus = 0;

				while (cumulOctetsRecus < tailleFichier && (octetsLus = fluxEntree.read(tamponDonnees, 0, (int)Math.min(tamponDonnees.length, tailleFichier - cumulOctetsRecus))) != -1)
				{
					ecrivainFichier.write(tamponDonnees, 0, octetsLus);
					cumulOctetsRecus += octetsLus;
				}
				ecrivainFichier.close();
			}
			socketReception.close();
		}
		catch (Exception erreur)
		{
			System.out.println("Erreur download");
		}
	}
}