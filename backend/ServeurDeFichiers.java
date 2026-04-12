import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;

public class ServeurDeFichiers
{
	public static void main(String[] args) throws IOException
	{
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		server.createContext("/", new ListeFichiersHandler());
		server.setExecutor(null);
		
		System.out.println("Serveur Java lancé sur http://localhost:8080");
		server.start();
	}
}

class ListeFichiersHandler implements HttpHandler
{
	public void handle(HttpExchange exchange) throws IOException
	{
		File dossier = new File("./partage");
		String contenu = "<html><head><meta charset='utf-8'></head><body>";
		contenu += "<h1>Dépôt de fichiers SAE 2.03</h1><ul>";
		
		if (dossier.exists() && dossier.isDirectory())
			for (File f : dossier.listFiles())
				contenu += "<li>" + f.getName() + "</li>";
		else
			contenu += "<li>Attention : Dossier de partage introuvable.</li>";

		contenu += "</ul></body></html>";
		
		exchange.sendResponseHeaders(200, contenu.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(contenu.getBytes());
		os.close();
	}
}