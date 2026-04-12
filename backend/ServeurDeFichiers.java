import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServeurDeFichiers
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(8080);
		System.out.println("Serveur actif sur le port 8080...");

		while (true)
		{
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			OutputStream out = client.getOutputStream();

			String line = in.readLine();
			if (line != null)
			{
				String[] requestParts = line.split(" ");
				String path = requestParts[1];

				if (path.equals("/"))
				{
					String content = "<html><head><meta charset='UTF-8'></head><body><h1>📂 Liste des fichiers</h1><ul>";
					File folder = new File("/app/partage");
					File[] list = folder.listFiles();

					if (list != null)
						for (File f : list)
							if (f.isFile())
								content += "<li><a href='/" + f.getName() + "'>" + f.getName() + "</a></li>";

					content += "</ul></body></html>";
					String header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
					out.write(header.getBytes());
					out.write(content.getBytes());
				}
				else
				{
					File file = new File("/app/partage" + path);
					if (file.exists() && file.isFile())
					{
						byte[] data = Files.readAllBytes(file.toPath());
						String header = "HTTP/1.1 200 OK\r\n" +
										"Content-Length: " + data.length + "\r\n" +
										"Content-Disposition: attachment; filename=\"" + file.getName() + "\"\r\n\r\n";
						out.write(header.getBytes());
						out.write(data);
					}
				}
			}
			client.close();
		}
	}
}