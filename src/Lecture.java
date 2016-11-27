import java.io.*;

import javax.swing.*;

/**
 * Classe FichierException est une classe qui indique 
 * à l'utilisateur que le fichier en question n'exsite pas.
 * @author Mehdi
 *
 */
@SuppressWarnings("serial")
class FichierException extends Exception{ 
	public FichierException(String nom) {
		  JOptionPane.showMessageDialog(null, "Le fichier " + nom + " n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
	  }  
}

/**
 * Classe Lecture qui permet de lire un fichier
 * @author Mehdi
 */
public class Lecture {
	
	/**
	 * Le BufferReader
	 */
	private BufferedReader br;
	
	/**
	 * Fonction lire qui lit une ligne entière
	 * @return
	 * 		Elle retourne la ligne du fichier
	 */
	public String lire() {
		String str = new String();
		try {
		    str = br.readLine();
		} catch (IOException e) {
			System.out.println ("Erreur lors de la lecture : " + e.getMessage());
		}
		return str;
	}
	
	/**
	 * Fonction fermer qui ferme le fichier deja ouvert
	 */
	public void fermer() {
		try {
			br.close();
		} catch (IOException e) {
			System.out.println ("Erreur lors de la fermeture : " + e.getMessage());
		}
	}
	
	/**
	 * Fonction statique qu'on utilise afin de décomposer
	 * la ligne qui est lue par la fonction lire ou une 
	 * ligne quelconque.
	 * @param str
	 * 			La chaine à décomposer
	 * @param champ
	 * 			La colonne ciblée
	 * @return
	 * 			Une chaine de caracteres qui est dans 
	 * 			la N-eme colonne
	 */
	public static String decomposer(String str, int champ) {
		int place = 1;
		String aux = new String();
		for (int i = 0;i < str.length();i++)
		{
			if (str.charAt(i) == ';')
			{
				if (place == champ)
				{
					return aux;
				}
				else 
				{
					aux = new String();
					place++;
				}
			}
			else
			{
				aux += str.charAt(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Constructeur de la classe
	 * @param nom
	 * 			Nom ou plutot chemin du fichier en question.
	 * @throws FichierException
	 * 			Elle renvoie un exception si le fichier n'existe pas.
	 */
	public Lecture(String nom) throws  FichierException {
		try {
			br  = new BufferedReader(new FileReader(nom));
		} catch (FileNotFoundException e) {
			throw new FichierException(nom);
		}
	}
}
