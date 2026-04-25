import java.util.Scanner;
import IHM.FrameIhm;
import IHM.FrameServeur;

public class Lanceur
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("--- Lanceur SAE 2.03 ---");
		System.out.println("1 : Lancer le SERVEUR"   );
		System.out.println("2 : Lancer le CLIENT"    );
		System.out.println("3 : Lancer les DEUX"     );

		String choix = sc.nextLine();

		if(choix.equals("1") || choix.equals("3"))
			new FrameServeur();

		if(choix.equals("2") || choix.equals("3"))
			new FrameIhm();

		if(!choix.equals("1") && !choix.equals("2") && !choix.equals("3"))
			System.out.println("Choix invalide, fermeture.");
		
		sc.close();
	}
}