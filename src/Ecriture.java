import java.io.*;

/**
 * La classe Ecriture permet d'ouvrir un fichier à ecrire soit
 * en ajoutant sous en le remplacant.
 * @author Mehdi
 */
public class Ecriture {
	
	/**
	 * Le BufferReader
	 */	
	private BufferedWriter bw;
	
	/**
	 * Fonction ecrire.
	 * @param txt
	 * 			On écrit txt dans le fichier.
	 */
	public void ecrire(String txt) {
		try {
			bw.write(txt);
		} catch (IOException e) {
			System.out.println ("Erreur lors de l'écriture : " + e.getMessage());
		}
	}
	
	/**
	 * Comme son nom l'indique elle permet de faire une nouvelle ligne
	 */
	public void nouvelle_ligne() {
		try {
			bw.newLine();
		} catch (IOException e) {
			System.out.println ("Erreur lors de l'écriture : " + e.getMessage());
		}
	}
	
	/**
	 * Celle la elle ferme un fichier deja ouvert
	 */
	public void fermer() {
		try {
			bw.close();
		} catch (IOException e) {
			System.out.println ("Erreur lors de la fermeture : " + e.getMessage());
		}
	}
	
	/**
	 * Le constructeur 
	 * @param nom
	 * 			Nom ou plutot chemin du fichier à écrire.
	 * @param choice
	 * 			Choix : Soit on écrase soit non.
	 */
	public Ecriture(String nom, boolean choice) {
		try {
			if (choice)
				bw  = new BufferedWriter(new FileWriter(nom,true));
			else
				bw  = new BufferedWriter(new FileWriter(nom));
		} catch (IOException e) {
			System.out.println ("Erreur lors de la lecture : " + e.getMessage());
		}
	}
}
