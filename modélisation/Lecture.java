package modélisation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.plot.CombinedCategoryPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javax.xml.crypto.Data;

public class Lecture extends JFrame{
	static BufferedReader f = null;
	Data data = null;
	public JPanel chartPanel;
	static String ligne;
	static int indiceSeparateur = 0;
	static String colonne  = new String("");
	static String valeur1  = new String("");
	private static Vector     tableauValeur1 = new Vector();
	private static Vector     tableauNom     = new Vector();
	public Lecture(){
	super("Graphe");	
	
	}
	
	public static void readfile(String filename) throws IOException{
		try {
			f = new BufferedReader( new FileReader(filename) );
			} catch (FileNotFoundException e) {
			System.out.println("Fichier non trouvé.");
			System.exit(1);
			}
			try{
				do{
			ligne=f.readLine();
			indiceSeparateur = ligne.indexOf(";");
			colonne          = new String(ligne.substring(0,indiceSeparateur));
		  	valeur1          = new String(ligne.substring(indiceSeparateur+1,ligne.length()));
		  	tableauNom.addElement(colonne);
		    tableauValeur1.addElement(valeur1);
		    
				}
				 while(f.ready());
			
			}finally{
			f.close();
			}
	}
	 public static JPanel createDemoPanel() {
	        JFreeChart chart = createChart();
	        return new ChartPanel(chart);
	    }
	 private static JFreeChart createChart() {

	        CategoryDataset dataset1 = createDataset1();
	        NumberAxis rangeAxis1 = new NumberAxis("Value");
	        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
	        renderer1.setBaseToolTipGenerator(
	                new StandardCategoryToolTipGenerator());
	        CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1,
	                renderer1);
	        subplot1.setDomainGridlinesVisible(true);

	        

	        CategoryAxis domainAxis = new CategoryAxis("Date");
	        CombinedCategoryPlot plot = new CombinedCategoryPlot(
	                domainAxis, new NumberAxis("Milliseconde"));
	        plot.add(subplot1, 2);
	       

	        JFreeChart result = new JFreeChart(
	                "Historique des statistiques",
	                new Font("SansSerif", Font.BOLD, 12), plot, true);
	        return result;

	    }
	 public static CategoryDataset createDataset1() {
	        DefaultCategoryDataset result = new DefaultCategoryDataset();
	        String series1 = "Response Time";
	        //nom
	        String[] type = new String[tableauNom.size()];
	        for(int i=0;i<tableauNom.size();i++){
	        	type[i]=tableauNom.elementAt(i).toString();
	        }
	        double[] val = new double[tableauValeur1.size()];
	        for(int i=1;i<tableauValeur1.size();i++){
	        	val[i]=Double.parseDouble(tableauValeur1.elementAt(i).toString());
	        }
	       
	        for(int i=1;i<tableauValeur1.size();i++){
	        	result.addValue(val[i], series1, type[i]);
	        }
	        

	        System.out.println(tableauNom.size());
			 
			 System.out.println(tableauValeur1.size());

	        return result;
	    }
	 public void initialiser(){
		 tableauNom.clear();
		 tableauValeur1.clear();
		 indiceSeparateur = 0;
		 colonne  = new String("");
		 valeur1  = new String("");
	 }
	
}
