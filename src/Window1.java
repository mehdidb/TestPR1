import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Window1 est la fenêtre principale
 * @author Mehdi
 * @version 1.0
 */
public class Window1 {
	
	/**
	 * Le texte de la barre de status
	 */
	private static JLabel statusLabel;
	
	/**
	 * Fonction qui change le texte de la barre de status
	 * @param t
	 * 			t est la chaîne à afficher
	 */
	static void ChangerTexte(String t) {
		statusLabel.setText(t);
	}
	
	/**
	 * Constructeur le classe Window1
	 */
	Window1() 
	{
		/**
		 * Création de la JFrame
		 */
		JFrame f = new JFrame();
		f.setTitle("DRIVE TEST 2.0");
		f.setSize(500,450);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Création du layout de la fenêtre
		 */
		f.setLayout(new BorderLayout());
		
		/**
		 * Création du menu
		 */
		new Menu(f);	
		
		/**
		 * Création des bouttons et on les mets dans un JPanel p
		 */
		JPanel p = new JPanel();
		JButton boutton0 = new JButton("Lancer une analyse");
		JButton boutton1 = new JButton("Consulter les analyses");
		JButton boutton2 = new JButton("Quitter");
		GridLayout t = new GridLayout(10,0,10,10);
		p.setLayout(t);	
		p.add(boutton0);
		p.add(boutton1);
		p.add(boutton2);
		
		/**
		 * On crée un JLabel qui a une icone de l'application
		 */
		JLabel icon = new JLabel(new ImageIcon("icone.png"));
		
		/**
		 * On affiche un message de bienvenue
		 */
		statusLabel = new JLabel("Bienvenue");	
		
		/**
		 * On ajoute les objets à la fenêtre
		 */
		f.add(icon, BorderLayout.CENTER);
		f.add(p, BorderLayout.EAST);
		f.add(statusLabel, BorderLayout.SOUTH);
		
		/**
		 * On crée les events lors de l'appuie sur les bouttons
		 */
		boutton0.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				f.setEnabled(false);
				new Window3(f);
			}
		});
		
		boutton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				f.setEnabled(false);
				new Window2(f);
			}
		});
		
		boutton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		/**
		 * Affichage de la fenêtre
		 */
		f.setVisible(true);
	}
}
