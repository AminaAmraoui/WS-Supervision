/**
 * 
 */
package modélisation;

/**
 * @author Amraoui
 *
 */
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.event.*;

import java.awt.Dimension;
import java.awt.event.*;
import com.jgoodies.clearlook.ClearLookManager;
import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;

public class Tableur extends JPanel implements TableModelListener {
	
//		public	Object[][] donnees ; 
//		String[] nomsColonnes = {"Statistiques", "Valeur", "Unité", "Description"};  
		public JTable table = new JTable();
		TableModel tableur;
		
		public Tableur()
		{   
		table.setPreferredScrollableViewportSize(new Dimension(300, 50));
		JScrollPane avecAsc = new JScrollPane(table);   
		add(avecAsc);
		tableur = table.getModel();
		tableur.addTableModelListener(this);
		}

		public void tableChanged(TableModelEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		}
