import javax.swing.*;

/**
 * Classe de l'exception du fichier erron�
 * @author Mehdi
 */
@SuppressWarnings("serial")
class FichierErroneException extends Exception
{
	public FichierErroneException()
	{
	    JOptionPane.showMessageDialog(null, "Le fichier est erron�.", "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}

/**
 * C'est une analyse qui donne le pourcentage 
 * du Rx selon le tableau QoS des niveau de 
 * r�ception.
 * @author Mehdi
 */
public class Analyse1 extends Thread {
	
	/**
	 * Fichier de lecture
	 */
	private Lecture fichier;
	
	/**
	 * Tableau contenant le r�sultat des analyses 
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
	 * JFrame parent n�cessaire pour freezer une JFrame
	 */
	private JFrame parent;
	
	/**
	 * Num�ro de la colonne contenant All-RxLev Full
	 */
	private int numeroColonne;
	
	/**
	 * Fonction run vu que l'analyse est un thread
	 * elle permet d'analyser le fichier.
	 */	
	public void run() {
		String buffer;
		int[] tab = new int[5];
		int Rx;
		int nbr;
		
		
		buffer = fichier.lire();
		
		while (buffer != null)
		{	
			Rx = decomposer(buffer, numeroColonne);
			buffer = fichier.lire();
			
			if (Rx >= -110 && Rx < -95)
				tab[0]++;
			
			if (Rx >= -95 && Rx < -85)
				tab[1]++;
			
			if (Rx >= -85 && Rx < -75)
				tab[2]++;
			
			if (Rx >= -75 && Rx < -65)
				tab[3]++;
			
			if (Rx >= -65 && Rx < -46)
				tab[4]++;
		}
		
		nbr = tab[0] + tab[1] + tab[2] + tab[3] + tab[4];
		
		data[0][0] = "Niveau de couverture";
		data[0][1] = "Pourcentage";	
		data[1][0] = "Pas de couverture";
		data[2][0] = "Mauvaise couverture";
		data[3][0] = "Assez bonne couverture";
		data[4][0] = "Bonne couverture";
		data[5][0] = "Tr�s bonne couverture";
		
		for (int i = 1;i<=5;i++)
			data[i][1] = String.format("%.2f", (double)tab[i-1]*100/nbr) + "%";
			
		/**
		 * Appel de la fen�tre consulter r�sultat
		 */	
		new Window5(data, labels, parent);
	}
	
	/**
	 * Fonction qui permet de d�composer une ligne lors
	 * de la lecture du fichier elle d�componse la ligne
	 * suivant le caract�re ;
	 * @param str
	 * 			La chaine � d�composer
	 * @param champ
	 * 			Num�ro de la colonne
	 * @return Num�ro qui figure � la N-�me colonne
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
	 * 
	 */
	
	/**
	 * Constructeur de l'analyse
	 * @param emplacement
	 * 			Emplacement du fichier csv ou txt
	 * @param labels
	 * 			Texte (Objet, Date, Commentaire)
	 * @param parent
	 * 			JFrame parent	 
	 * @throws FichierErroneException
	 * 			C'est � dire le fichier est erron�
	 */
	public Analyse1(String emplacement, String[] labels, JFrame parent) throws FichierErroneException {
		data = new String[6][2];
		this.parent = parent;
		this.labels = labels;
		
		try {
			fichier = new Lecture(emplacement);
			
			String buffer = fichier.lire();
			
			/**
			 * V�rification de la validit� du fichier
			 */		
			for (numeroColonne=1;Lecture.decomposer(buffer, numeroColonne) != null;numeroColonne++)
				if (Lecture.decomposer(buffer, numeroColonne).equals("All-RxLev Full (dBm)"))
					break;
			
			if (Lecture.decomposer(buffer, numeroColonne) == null)
				throw new FichierErroneException();
		}catch (FichierException e) { }
	}
}
