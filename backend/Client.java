package backend;

import java.io.*;
import java.net.*;

public class Client
{
	private String hote;
	private int port;

	public Client(String hote, int port)
	{
		this.hote = hote;
		this.port = port;
	}

	public String demanderListe()
	{
		try (Socket soc = new Socket(hote, port))
		{
			DataOutputStream out = new DataOutputStream(soc.getOutputStream());
			out.writeUTF("LISTE");
			return new DataInputStream(soc.getInputStream()).readUTF();
		}
		catch (Exception e) 
		{ 
			return ""; 
		}
	}

	public void envoyerFichier(File f)
	{
		try (Socket soc = new Socket(hote, port))
		{
			DataOutputStream out = new DataOutputStream(soc.getOutputStream());
			out.writeUTF("ENVOI");
			out.writeUTF(f.getName());
			out.writeLong(f.length());
			FileInputStream fis = new FileInputStream(f);
			byte[] buf = new byte[4096];
			int l;
			while ((l = fis.read(buf)) != -1) 
				out.write(buf, 0, l);
			fis.close();
		}
		catch (Exception e) {}
	}

	public void telechargerFichier(String nom)
	{
		try (Socket soc = new Socket(hote, port))
		{
			DataOutputStream out = new DataOutputStream(soc.getOutputStream());
			out.writeUTF("RECOIT");
			out.writeUTF(nom);
			
			DataInputStream in = new DataInputStream(soc.getInputStream());
			long taille = in.readLong();

			String home = System.getProperty("user.home") + File.separator + "Downloads";
			File dest = new File(home, nom);
			FileOutputStream fos = new FileOutputStream(dest);
			
			byte[] buf = new byte[4096];
			int l; long t = 0;
			while (t < taille && (l = in.read(buf, 0, (int)Math.min(buf.length, taille-t))) != -1)
			{ 
				fos.write(buf, 0, l); 
				t += l; 
			}
			fos.close();
		}
		catch (Exception e) {}
	}
}