import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Window0 est une classe de la fen�tre qui s'ouvre
 * au lancement du programme
 * Elle comporte une image du programme qui donne
 * une id�e g�n�rale sur le programme en question
 */
public class Window0 {
	
	/**
	 * Constructeur de la classe Window0
	 * Il initalise l'image et la frame ainsi 
	 * qu'une pause d'une seconde.
	 * @author Mehdi
	 * @version 1.0
	 */
	public Window0() {	
		
		/**
		 * On cr�e la frame principale
		 */
		JFrame f = new JFrame();
		f.setSize(400,350);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setLayout(new BorderLayout());
		
		/**
		 * On initialise l'image
		 */
		JLabel icon = new JLabel(new ImageIcon("icone.png"));
		f.add(icon, BorderLayout.CENTER);
		
		/**
		 * Finalement on affiche la JFrame
		 */
		f.setVisible(true);
		
		/**
		 * On fait une pause du programme
		 * M�thode � risque donc try{...}catch{...}
		 */
		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		/**
		 * On ferme la frame f (Fen�tre du lancement)
		 */
		f.dispose();
			
		/**
		 * On appelle le constructeur de la fen�tre
		 * principale
		 */
		new Window1();
	}
}
