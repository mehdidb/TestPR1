import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Window4 est une classe de la fenêtre Options
 * Elle liste les analyses à faire sous forme de checkbox
 * et permet soit d'enregistrer le choix de l'utilisateur
 * soit de quitter sans rien faire.
 */
public class Window4 {
	
	/**
	 * Layout de la partie d'en haut c'est à dire celle des 
	 * cases de selection des analyses.
	 */
	private GridLayout top;
	
	/**
	 * Layout de la partie d'en bas c'est à dire celle des 
	 * bouttons enregister et quitter.
	 */
	private GridLayout bottom;
	
	/**
	 * Panel qui comporte les checkbox.
	 */
	private JPanel pan1;
	
	/**
	 * Panel qui comporte les bouttons.
	 */
	private JPanel pan2;
	
	/**
	 * Checkbox analyse1
	 */
	private JCheckBox an1;
	
	/**
	 * Checkbox analyse2
	 */
	private JCheckBox an2;
	
	/**
	 * Boutton Enregistrer : Permet d'enregistrer le choix 
	 * de l'utilisateur dans config.cfg
	 * 1 : Analyse1
	 * 2 : Analyse2
	 */
	private JButton button1;
	
	/**
	 * Boutton Quitter : Permet de fermer la fenêtre.
	 */
	private JButton button2;
	
	/**
	 * Frame de la fenêtre Options.
	 */
	private JFrame f;
	
	/**
	 * Choix de l'utilisateur
	 */
	
	private int choix;
	
	/**
	 * Fonction qui cherche le choix de l'utilsiateur
	 * et coche la case à chocer si le fichier
	 * config.cfg existe
	 */
	public void remplirCheckbox()
	{
		try {
			Lecture l = new Lecture("config.cfg");
			String aux = l.lire();
			choix = Integer.parseInt(aux);
			
			if (choix == 1)
				an1.setSelected(true);
			else
				an2.setSelected(true);
		} catch (FichierException e) {
		}
	}
	
	
	/** 
	 * Constructeur de la fenêtre Options
	 * @author Mehdi
	 * @param parent
	 * 			La JFrame parent.
	 * @version 2.0
	 * C'est la fenêtre parente dans notre cas ici 
	 * c'est la fenêtre principale. 
	 **/
	public Window4(JFrame parent) {	
		/**
		 * Création de la fenêtre principale
		 * Taille : 400x250
		 * Taille modifiable : Non
		 * Action lors de l'appuie sur le bouton X : Fermer la fenêtre.
		 */
		f = new JFrame();
		f.setSize(400,350);
		f.setLayout(new BorderLayout(10,10));
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);;
		f.setVisible(true);	
		
		/**
		 * Haut de la fenêtre
		 */
		top = new GridLayout(2,0,10,10);
		pan1 = new JPanel();
		an1 = new JCheckBox("Analyse 1");
		an2 = new JCheckBox("Analyse 2");
		pan1.setBorder(BorderFactory.createTitledBorder("Choix de l'analyse"));
		pan1.setLayout(top);
		pan1.add(an1);
		pan1.add(an2);
		f.add(pan1,BorderLayout.NORTH);
	
		/** 
		 * Création des bouttons de la fenêtre ainsi que 
		 * le layout du bas.
		 **/	
		bottom = new GridLayout(0,2,10,10);
		button1 = new JButton("Enregistrer");
		button2 = new JButton("Annuler");
		pan2 = new JPanel();
		pan2.setLayout(bottom);
		pan2.add(button1);
		pan2.add(button2);
		f.add(pan2,BorderLayout.SOUTH);
		
		/** 
		 * Création des events :
		 * Enregister->Enregistrer les analyses et fermer la fenêtre.
		 * Si deux cases sont cochées on affiche un message d'erreur
		 * Si aucune case n'est cochée on affiche aussi un message 
		 * d'erreur.
		 * Quitter->Fermer la fenêtre sans enregistrer.
		 **/
		
		/**
		 * Finalement on coche la checkbox s'il existe un choix dans le fichier config.cfg
		 */
		remplirCheckbox();
		
		/**
		 * Création des actionsListener
		 */
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	    
				Ecriture fichier = new Ecriture("config.cfg",false);
				if (an1.isSelected() && an2.isSelected())
				    JOptionPane.showMessageDialog(null, "Vous devez choisir qu'une seule analyse.", "Erreur", JOptionPane.ERROR_MESSAGE);	
				else if (an1.isSelected())
				{
					fichier.ecrire("1");
				    parent.setEnabled(true);
					parent.setVisible(true);
					f.dispose();
				}
				else if (an2.isSelected())
				{
					fichier.ecrire ("2");
				    parent.setEnabled(true);
					parent.setVisible(true);
					f.dispose();
				}
				else
				{
				    JOptionPane.showMessageDialog(null, "Vous devez choisir une analyse.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				fichier.fermer();
			}
		});
		
		button2.addActionListener(new ActionListener()
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

