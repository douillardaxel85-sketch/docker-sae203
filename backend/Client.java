import java.io.*;
import java.net.Socket;

public class Client 
{
    private String adresseIP;
    private int port;
    private String message;
    
    /* Constructeur du client
     *  adresseIP l'adresse IP ou l'identifiant de la machine (localhost ou 127.0.0.1)
     *  port le port de connexion
     *  message le message à envoyer au serveur */
    public Client(String adresseIP, int port, String message)
    {
        this.adresseIP = adresseIP;
        this.port = port;
        this.message = message;
        envoyerMessage();
    }
    
    
    // Méthode pour envoyer le message au serveur
    private void envoyerMessage()
    {
        try
        {
            Socket socket = new Socket(adresseIP, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
            writer.flush();
            socket.close();
            System.out.println("Message envoyé au serveur: " + message);
        }
        catch (IOException e)
        {
            System.err.println("Erreur lors de la connexion au serveur: " + e.getMessage());
        }
    }
}
