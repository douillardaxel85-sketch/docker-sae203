import java.io.*;
import java.net.Socket;

public class Client
{
	private String machine;
	private int port;
	private String nomFichier;

	public Client(String machine, int port, String nomFichier)
	{
		this.machine = machine;
		this.port = port;
		this.nomFichier = nomFichier;
		envoyerFichier();
	}

	private void envoyerFichier()
	{
		try
		{
			File fichier = new File(nomFichier);
			if (!fichier.exists())
				return;

			Socket socket = new Socket(machine, port);
			
			InputStream inputFichier = new FileInputStream(fichier);
			OutputStream outputReseau = socket.getOutputStream();

			byte[] buffer = new byte[4096];
			int nbOctetsLus;

			while ((nbOctetsLus = inputFichier.read(buffer)) != -1)
				outputReseau.write(buffer, 0, nbOctetsLus);

			inputFichier.close();
			outputReseau.flush();
			socket.close();
			
			System.out.println("Fichier " + nomFichier + " envoyé avec succès !");
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors de l'envoi : " + e.getMessage());
		}
	}
}