import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Window5 est la classe de la fenetre qui s'affiche 
 * après l'analyse
 * @author Mehdi
 * @version 2.0
 */
public class Window5 {
	private JPanel p1;
	private JPanel p3;
	
	private GridLayout top;
	private GridLayout bottom;
	
	private JLabel label_Objet;
	private JLabel label_Date;
	private JLabel label_Description;
	
	private JLabel text_Objet;
	private JLabel text_Date;
	private JLabel text_Description;
	private JFrame f;
	private JButton quitter;
	private JButton enregister;
	private JTable tableau;
	
	private String[] title;

	/**
	 * Fonction qui vérifie le choix de l'utilisateur
	 * @return
	 * 		Choix de l'utilisateur
	 */
	public int checkChoix()
	{
		try {
			Lecture l = new Lecture("config.cfg");
			String aux = l.lire();
			return Integer.parseInt(aux);
			
		} catch (FichierException e) {
		}
		
		return 0;
	}
	
	/**
	 * Constructeur de la classe Window5
	 * @param data
	 * 			Tableau à deux dimensions regrouppant
	 * 			les données des analyses effectuées.
	 * @param txt
	 * 			Tableau contenant le texte du formulaire
	 * @param parent
	 * 			JFrame parent
	 */
	Window5(String[][] data, String[] txt, JFrame parent)
	{
		this.title = data[0];
		
		String aux[][] = new String[data.length-1][2];

		for (int i=1;i<data.length;i++)
		{
			aux[i-1][0] = data[i][0];
			aux[i-1][1] = data[i][1];
		}
		
		/** Création de la fenêtre **/
		f = new JFrame();
		f.setTitle("Résultat de l'analyse " + txt[0]);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.setSize(300,350);
		f.setResizable(false);
		f.setLayout(new BorderLayout());
		
		top = new GridLayout(3,2,10,10);
		label_Objet = new JLabel("Objet");
		label_Date = new JLabel("Date");		
		label_Description = new JLabel("Description");
		
		text_Objet = new JLabel(txt[0]);
		text_Date = new JLabel(txt[1]);
		text_Description = new JLabel(txt[2]);
		
		
		/**
		 * Création du haut de la fenêtre
		 */
		p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder("Informations"));
		p1.setLayout(top);
		
		p1.add(label_Objet);
		p1.add(text_Objet);
		
		p1.add(label_Date);
		p1.add(text_Date);
		
		p1.add(label_Description);
		p1.add(text_Description);
		f.add(p1, BorderLayout.NORTH);
		
		/**
		 * Création du tableau des analyses
		 * Milieu de fenêtre
		 */
		tableau = new JTable(aux, title);
		tableau.setEnabled(false);
		f.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		
		
		/**
		 * Création des bouttons du bas ainsi que le layout
		 */
		enregister = new JButton("Enregistrer");
		quitter = new JButton("Quitter");
		bottom = new GridLayout(0,2,10,10);
		p3 = new JPanel();
		p3.setLayout(bottom);
		p3.add(enregister);
		p3.add(quitter);
		f.add(p3, BorderLayout.SOUTH);
		
		/**
		 * On affiche la JFrame
		 */
		f.setVisible(true);
		
		/**
		 * Affichage des graphes
		 */
		String[] names = new String[aux.length];
		for (int i = 0;i<aux.length;i++)
			  names[i] = aux[i][0];
		  
		 double[] values = new double[aux.length];  	
		  
		  for (int i=0;i<aux.length;i++)
			  values[i] = Double.parseDouble(((aux[i][1]).replace("%", "")).replace(",", "."));
		Chart.creer(names,values);
		
		/**
		 * Cration des actionslistener
		 */
		enregister.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Ecriture ecriture = new Ecriture("analyses" + checkChoix() + ".txt", true);
				for (int i = 0; i < txt.length;i++)
				{
					ecriture.ecrire(txt[i]+";");
				}
				
				ecriture.nouvelle_ligne();
				
				for (int i = 0; i < data.length;i++)
				{
					for (int j = 0; j < data[i].length;j++)
					{
						ecriture.ecrire(data[i][j]+";");
					}
					ecriture.nouvelle_ligne();
				}
				
				ecriture.fermer();
				JOptionPane.showMessageDialog(null, "L'enregistrement s'est bien effectué dans le fichier analyses" + checkChoix() + ".txt", "Enregistrement effectué", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		quitter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parent.setEnabled(true);
				parent.setVisible(true);
				f.dispose();
			}
		});
	}
}
