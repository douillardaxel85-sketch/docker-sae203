package backend;

import java.io.*;
import java.net.*;
import javax.swing.JTextArea;

public class ServeurDeFichiers
{
	private static ServerSocket srv;
	private static boolean actif = false;

	public static void lancer(int port, String dossier, JTextArea log)
	{
		new Thread(() -> demarrer(port, dossier, log)).start();
	}

	public static void main(String[] args)
	{
		demarrer(8080, "partage", null);
	}

	public static void arreter(JTextArea log)
	{
		try
		{
			actif = false;
			if (srv != null) srv.close();
			if (log != null) log.append("Serveur arrete.\n");
		}
		catch (IOException e)
		{
			if (log != null) log.append("Erreur lors de l'arret.\n");
		}
	}

	private static void demarrer(int port, String dossier, JTextArea log)
	{
		try
		{
			srv = new ServerSocket(port);
			actif = true;
			String msg = "Serveur actif sur le port " + port;

			if (log != null) log.append(msg + "\n");
			else System.out.println(msg);

			while (actif)
			{
				Socket soc = srv.accept();
				new Thread(() -> gerer(soc, dossier, log)).start();
			}
		}
		catch (IOException e)
		{
			if (actif && log != null) log.append("Erreur srv : " + e.getMessage() + "\n");
			actif = false;
		}
	}

	private static void gerer(Socket soc, String dossier, JTextArea log)
	{
		try
		{
			DataInputStream in = new DataInputStream(soc.getInputStream());
			DataOutputStream out = new DataOutputStream(soc.getOutputStream());
			String cmd = in.readUTF();

			if (cmd.equals("LISTE"))
			{
				File d = new File(dossier);
				if (!d.exists()) d.mkdir();
				String res = "";
				for (File f : d.listFiles())
					if (f.isFile()) res += f.getName() + ";";
				out.writeUTF(res);
			}

			if (cmd.equals("ENVOI"))
			{
				String nom = in.readUTF();
				long taille = in.readLong();
				if (log != null) log.append("Reception : " + nom + "\n");
				
				FileOutputStream fos = new FileOutputStream(dossier + "/" + nom);
				byte[] buf = new byte[4096];
				int lus;
				long total = 0;
				while (total < taille && (lus = in.read(buf, 0, (int)Math.min(buf.length, taille - total))) != -1)
				{
					fos.write(buf, 0, lus);
					total += lus;
				}
				fos.close();
			}

			if (cmd.equals("RECOIT"))
			{
				String nom = in.readUTF();
				File f = new File(dossier, nom);
				out.writeLong(f.length());
				FileInputStream fis = new FileInputStream(f);
				byte[] buf = new byte[4096];
				int lus;
				while ((lus = fis.read(buf)) != -1)
					out.write(buf, 0, lus);
				fis.close();
			}
			soc.close();
		}
		catch (Exception e) {}
	}
}