import java.io.*;
import java.net.*;

public class ClientEcho 
{
    public static void main(String[] args) 
    {
        String adresseServeur = "localhost";
        int port = 5000;
        
        try (Socket socket = new Socket(adresseServeur, port);
             PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in))) 
             {
            
            System.out.println("Connecté au serveur echo sur " + adresseServeur + ":" + port);
            System.out.println("Entrez des messages (tapez 'quitter' pour arrêter):");
            
            String messageUtilisateur;
            while ((messageUtilisateur = clavier.readLine()) != null) 
            {
                if (messageUtilisateur.equalsIgnoreCase("quitter"))
                {
                    break;
                }
                
                sortie.println(messageUtilisateur);
                String reponseServeur = entree.readLine();
                System.out.println("Réponse du serveur: " + reponseServeur);
            }
            
            System.out.println("Déconnecté du serveur.");
            
        } 
        catch (IOException e) 
        {
            System.err.println("Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
