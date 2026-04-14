import java.io.*;
import java.net.*;

public class ServeurEcho 
{
    private static final int PORT = 5000;

    public static void main(String[] args) 
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            System.out.println("Serveur Echo démarré sur le port " + PORT);

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté: " + clientSocket.getInetAddress());

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Erreur serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable 
    {
        private Socket socket;

        public ClientHandler(Socket socket) 
        {
            this.socket = socket;
        }

        @Override
        public void run() 
        {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) 
                {
                    System.out.println("Reçu: " + message);
                    out.println(message);
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Erreur client: " + e.getMessage());
            } 
            finally 
            {
                try 
                {
                    socket.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
