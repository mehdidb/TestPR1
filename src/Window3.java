import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;

/**
 * Window3 est une classe créer une analyse
 * @author Mehdi
 * @version 2.0
 */
public class Window3 {
	private JFrame f;
	private GridLayout top;
	private GridLayout middle;
	private GridLayout bottom;
	private JLabel label_Objet;
	private JLabel label_Date;
	private JLabel label_Description;
	private JLabel label_File;
	private JLabel label_Vide;
	private JTextField text_Objet;
	private JFormattedTextField text_Date;
	private JTextField text_Description;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JButton button;
	private JButton button1;
	private JButton button2;
	private JFileChooser file;
	private Thread t;
	private boolean exec;
	
	/**
	 * Constructeur de la classe Window3
	 * @param parent
	 * 			C'est la JFrame parent
	 */
	public Window3(JFrame parent) {
		
		exec = false;
		
		/**
		 * Création de la fenêtre
		 **/
		f = new JFrame();
		f.setTitle("Créer une nouvelle analyse");
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.setSize(400,250);
		f.setResizable(false);
		f.setLayout(new BorderLayout(10,10));	
		f.setVisible(true);	
		
		/** 
		 * Informations
		 **/
		DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
		DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(dateFormatter, new DateFormatter(), dateFormatter);
		top = new GridLayout(3,2,10,10);
		label_Objet = new JLabel("Objet");
		label_Date = new JLabel("Date");		
		label_Description = new JLabel("Description");
		
		text_Objet = new JTextField();
		text_Description = new JTextField();
		text_Date = new JFormattedTextField(dateFormatterFactory);
		text_Date.setValue(new Date());
		
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
		 * Selection du fichier
		 **/
		middle = new GridLayout(2,2,10,10);
		label_File = new JLabel("Aucun fichier selectionné");
		label_Vide = new JLabel("");
		button = new JButton("Choisir un fichier");
		file = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers analyse (.csv, .txt)", "csv", "txt");
		file.setFileFilter(filter);
		file.setAcceptAllFileFilterUsed(false);
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createTitledBorder("Selection du fichier"));
		p2.setLayout(middle);
		p2.add(label_File);
		p2.add(label_Vide);
		p2.add(button);
		f.add(p2, BorderLayout.CENTER);
		
		/**
		 * Bouttons de la fenêtre
		 **/
		bottom = new GridLayout(0,2,10,10);
		button1 = new JButton("Analyser");
		button2 = new JButton("Annuler");
		p3 = new JPanel();
		p3.setLayout(bottom);
		p3.add(button1);
		p3.add(button2);
		f.add(p3, BorderLayout.SOUTH);
		
		/** 
		 * Création des events
		 **/
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = file.showOpenDialog(file);
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			       label_File.setText(file.getSelectedFile().getAbsolutePath());
			       button.setText("Changer de fichier");
			       exec = true;
			    }
			}
		});
		
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (exec)
				{
					try
					{
						Lecture l = new Lecture("config.cfg");
						String choix = l.lire();
						String[] lab = new String[3];
						lab[0] = text_Objet.getText();
						lab[1] = text_Date.getText();
						lab[2] = text_Description.getText();
						
						/**
						 * On check le choix
						 */
						
						if (choix.equals("1"))
						{
							if (!lab[0].isEmpty() && !lab[2].isEmpty())
							{
									try {
										t = new Thread(new Analyse1(file.getSelectedFile().getAbsolutePath(),lab,f));
										t.start();
									} catch (FichierErroneException e1) { }
									
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Vous devez remplir le formulaire.", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						else if (choix.equals("2"))
						{
							if (!lab[0].isEmpty() && !lab[2].isEmpty())
							{
								try {
									t = new Thread(new Analyse2(file.getSelectedFile().getAbsolutePath(),lab,f));
									t.start();
								} catch (FichierErroneException e1) { }
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Vous devez revoir vos paramétres d'analyse.", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Vous devez aller à Options et choisir une analyse.", "Erreur", JOptionPane.ERROR_MESSAGE);
							System.out.println(choix);
						}
					}
					catch (FichierException fiq)
					{
						
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Vous devez choisir un fichier à analyser.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (t != null)
				{
					t.interrupt();
				}
				
				f.dispose();
				parent.setEnabled(true);
				parent.setVisible(true);
			}
		});
	}
}
