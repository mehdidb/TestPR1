import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Classe Menu de la fenêtre principale
 */
public class Menu {
	
	/**
	 * La barre Menu
	 */
	private JMenuBar menuBar;
	
	/**
	 * Menu Analyse
	 */
	private JMenu analyse;
	
	/**
	 * Menu Options
	 */
	private JMenu options;

	/**
	 * item1 correspond au sous-menu Créer
	 */
	private JMenuItem item1;
	
	/**
	 * item2 correspond au sous-menu Consulter
	 */
	private JMenuItem item2;
	
	/**
	 * item3 correspond au sous-menu Quitter
	 */
	private JMenuItem item3;
	
	/**
	 * Constructeur du Menu 
	 * @author Mehdi Deboub
	 * @param parent
	 * 		JFrame parent.
	 */
	Menu(JFrame parent) {
		
		menuBar = new JMenuBar();
		parent.setJMenuBar(menuBar);
		
		analyse = new JMenu("Analyse");
		options = new JMenu("Options");
		item1 = new JMenuItem("Créer");
		item2 = new JMenuItem("Consulter");
		item3 = new JMenuItem("Quitter");
				
		analyse.add(item1);
		analyse.add(item2);
		analyse.add(new JSeparator());
		analyse.add(item3);	
		
		menuBar.add(analyse);
		menuBar.add(options);
				
		/**
		 * Ajout des events
		 */
		item1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Window1.ChangerTexte("Créer une nouvelle analyse");
			}
		});
		
		item2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Window1.ChangerTexte("Consulter les analyses");
			}
		});
		
		item3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Window1.ChangerTexte("Quitter le programme");
			}
		});
		
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//parent.setEnabled(false);
				new Window3(parent);
			}
		});
				
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//parent.setEnabled(false);
				new Window2(parent);
			}
		});
				
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		options.addMouseListener(new MouseListener() {

	        @Override
	        public void mouseReleased(MouseEvent e) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void mousePressed(MouseEvent e) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void mouseEntered(MouseEvent e) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	parent.setEnabled(false);
				new Window4(parent);
	        }
	    });
		
		analyse.addMenuListener(new MenuListener() {
		    @Override
		    public void menuDeselected(MenuEvent e) {
		    	Window1.ChangerTexte("Bienvenue");
		    }

		    @Override
		    public void menuCanceled(MenuEvent e) {
		    	// TODO Auto-generated method stub
		    }

			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub	
			}
		});
	}
}
