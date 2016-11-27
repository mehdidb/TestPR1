import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Window2 est une classe qui affiche les analyses effectuées
 * @author Mehdi
 * @version 3.0
 */
public class Window2 {
	private JFrame f;
	
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
	
	private JButton suivant;
	private JButton precedent;
	private JButton quitter;
	
	private String[] title;
	private String[] txt;
	private String[][] data;
	
	private LinkedList<String[]> list_txt;
	private LinkedList<String[][]> list_data;
	
	private String buffer;
	
	private JTable tableau;

	private Lecture lec;
	
	private int curseur;
	private int position;
	private int choix;
	private int nbre;
	
	
	/**
	 * Fonction lecture
	 */
	public void lecture()
	{
		buffer = lec.lire();
		
		txt = new String[3];
		data = new String[nbre][2];
		
		if (buffer != null)
		{
			for (int i = 0;i < 3;i++)
				txt[i] = Lecture.decomposer(buffer,i+1);
			
			buffer = lec.lire();
		
			for (int i = 0;i < 2;i++)
				title[i] = Lecture.decomposer(buffer,i+1);
			
			for (int i = 0;i < nbre;i++)
			{
				buffer = lec.lire();
				for (int j = 0;j < 2;j++)
				{
					data[i][j] = Lecture.decomposer(buffer,j+1);
				}
			}
			
			list_data.add(data);
			list_txt.add(txt);
			++curseur;
			++position;
		}
	}
	
	/**
	 * Fonction précédent
	 */
	public void prev()
	{
		--position;
		if (position < 1)
			position = 1;
	}
	
	/**
	 * Fonction suivant
	 */
	public void next()
	{
		++position;
		if (position > curseur)
			position = curseur;
	}
	
	/**
	 * Met à jour la fenêtre
	 */
	public void maj()
	{
		if (position >= 1)
		{
			txt = list_txt.get(position-1);
			data = list_data.get(position-1);
			
			DefaultTableModel model = new DefaultTableModel(data, title);
			tableau.setModel(model);
			
			text_Objet.setText(txt[0]);
			text_Date.setText(txt[1]);
			text_Description.setText(txt[2]);
		}
	}
	
	/**
	 * Fonction qui si le choix est coché ou non
	 * c'est à dire si le fichier config.cfg
	 * existe ou non
	 * @return
	 * 		Le numéro du choix 1 ou 2
	 */
	public int checkChoix()
	{
		try {
			Lecture l = new Lecture("config.cfg");
			String aux = l.lire();
			return Integer.parseInt(aux);
		} catch (FichierException e) {	}
		
		return 0;
	}
	
	/**
	 * Constructeur de la classe Window2
	 * @param parent
	 * 			JFrame parent
	 */
	Window2(JFrame parent)
	{
		/**
		 * Lecture du choix de l'utilisateur
		 */
		choix = checkChoix();
		if (choix == 1)
			nbre = 5;
		else if (choix == 2)
			nbre = 4;
		else
			nbre = 0;
		
		/**
		 * Fonction lecture à risque car le fichier peut
		 * ne pas exister
		 */
		try
		{
			curseur = 0;
			position = 0;
			buffer = new String();
			title = new String[2];
			list_txt = new LinkedList<String[]>();
			list_data = new LinkedList<String[][]>();
			txt = new String[3];
			data = new String[5][2];
			
			
			lec = new Lecture("analyses"+choix+".txt");
			lecture();
			
			/**
			 * Création de la fenêtre principale
			 */
			f = new JFrame();
			f.setTitle("Consulter les analyses");
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setLayout(new BorderLayout());
			f.setSize(300,350);
			f.setResizable(false);
			f.setLayout(new BorderLayout());
			
			/**
			 * Création du haut de la fenêtre
			 */
			top = new GridLayout(3,2,10,10);
			text_Objet = new JLabel();
			text_Date = new JLabel();
			text_Description = new JLabel();
			label_Objet = new JLabel("Objet");
			label_Date = new JLabel("Date");		
			label_Description = new JLabel("Description");		
			text_Objet.setText(txt[0]);
			text_Date.setText(txt[1]);
			text_Description.setText(txt[2]);	
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
			 * C'est notre tableau qui comporte les analyses
			 */
			tableau = new JTable(data, title);
			tableau.setEnabled(false);
			f.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

			/**
			 * On crée les bouttons du bas de la fenêtre
			 */
			suivant = new JButton("Suivant");
			precedent = new JButton("Précedent");
			quitter = new JButton("Fermer");
			bottom = new GridLayout(0,3,10,10);
			p3 = new JPanel();
			p3.setLayout(bottom);
			p3.add(precedent);
			p3.add(suivant);
			p3.add(quitter);
			f.add(p3, BorderLayout.SOUTH);
							
			/**
			 * On affiche la JFrame
			 */	
			f.setVisible(true);
				
			/**
			 * Les actionslistener
			 */
			suivant.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (position == curseur)
						lecture();
					else
						next();
					
					maj();
				}
			});
			
			precedent.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					prev();
					maj();
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
		} catch (FichierException e) { }	
	}
}
