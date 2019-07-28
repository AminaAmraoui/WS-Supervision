/**
 * 
 */
package modélisation;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.mxgraph.swing.mxGraphComponent;

/**
 * @author Amraoui
 *
 */
public class Principale {
	
	static Processus process ;
    String file;
	/**
	 * @param args
	 */
    
    public Principale(){
    	process = new Processus();
    	file=null;
    }
    
	public void executer(String file) {
		// TODO Auto-generated method stub
		//p.Set_structure("C:/PriceQuote.bpel");
		process.Set_structure(file);
		for(int i =0;i<process.tab.length;i++){
			System.out.println("contenu 2 "+process.tab[i]);
		}
		process.dessiner(process.racine);
		
		/*JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
        frame.add(scrollPane1);*/
	}
	public void initialiser(){
		file=null;
	    process.initialiser();
	    System.out.println("init principale");
	}

}
