import javax.swing.*;

/**
 * C'est une analyse qui donne le pourcentage 
 * du RxQual selon le tableau QoS des niveau de 
 * RxQual.
 * @author Mehdi
 */
public class Analyse2 extends Thread {

	/**
	 * Fichier de lecture
	 */
	private Lecture fichier;
	
	/**
	 * Tableau contenant le résultat des analyses 
	 * ainsi que le titre des colonnes.
	 */
	private String[][] data;
	
	/**
	 * Tableau contenant le texte saisie auparavant
	 * Date
	 * Objet
	 * Commentaire
	 */
	private String[] labels;
	
	/**
	 * JFrame parent nécessaire pour freezer une JFrame
	 */
	private JFrame parent;
	
	/**
	 * Numéro de la colonne contenant All-RxLev Full
	 */
	private int numeroColonne;
	
	/**
	 * Fonction run vu que l'analyse est un thread
	 * elle permet d'analyser le fichier.
	 */
	public void run() {
		int[] tab = new int[5];
		int RxQual;
		int nbr;
		
		String buffer = fichier.lire();
		while (buffer != null)
		{	
			RxQual = decomposer(buffer, 15);
			buffer = fichier.lire();
			
			if (RxQual >= 0 && RxQual < 2)
				tab[0]++;
			
			if (RxQual >= 2 && RxQual < 4)
				tab[1]++;
			
			if (RxQual >= 4 && RxQual < 6)
				tab[2]++;
			
			if (RxQual >= 6 && RxQual < 7)
				tab[3]++;
		}
		
		nbr = tab[0] + tab[1] + tab[2] + tab[3];
		
		data[0][0] = "Qualité correspondante";
		data[0][1] = "Pourcentage";	
		data[1][0] = "Très bonne";	
		data[2][0] = "Bonne";	
		data[3][0] = "Assez bonne";	
		data[4][0] = "Mauvaise";
		
		for (int i = 1;i<=4;i++)
			data[i][1] = String.format("%.2f", (double)tab[i-1]*100/nbr) + "%";
		
		/**
		 * Appel de la fenêtre consulter résultat
		 */
		new Window5(data, labels, parent);
	}
	
	/**
	 * Fonction qui permet de décomposer une ligne lors
	 * de la lecture du fichier elle décomponse la ligne
	 * suivant le caractère ;
	 * @param str
	 * 			La chaine à décomposer
	 * @param champ
	 * 			Numéro de la colonne
	 * @return Numéro qui figure à la N-ème colonne
	 */
	private int decomposer(String str, int champ) {
		int place = 1;
		String aux = new String();
		for (int i = 0;i<str.length();i++)
		{
			if (str.charAt(i) == ';')
			{
				if (place == champ)
				{
					int n;
					try
					{
						n = Integer.parseInt(aux);
					}
					catch(NumberFormatException e)
					{
						n = 1000;
					}
					return n;
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
		return 1000;
	}
	
	/**
	 * Constructeur de l'analyse
	 * @param emplacement
	 * 			Emplacement du fichier csv ou txt
	 * @param labels
	 * 			Texte (Objet, Date, Commentaire)
	 * @param parent
	 * 			JFrame parent	 
	 * @throws FichierErroneException
	 * 			C'est à dire le fichier est erroné
	 */
	public Analyse2(String emplacement, String[] labels, JFrame parent) throws FichierErroneException {
		this.parent = parent;
		this.labels = labels;
		data = new String[5][2];
		
		try {
			fichier = new Lecture(emplacement);
			
			String buffer = fichier.lire();
			
			/**
			 * Vérification de la validité du fichier
			 */	
			for (numeroColonne=1;Lecture.decomposer(buffer, numeroColonne) != null;numeroColonne++)
				if (Lecture.decomposer(buffer, numeroColonne).equals("All-RxQual Full"))
					break;
			
			if (Lecture.decomposer(buffer, numeroColonne) == null)
				throw new FichierErroneException();
		}catch (FichierException e) { }
	}
}