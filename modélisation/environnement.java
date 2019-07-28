package modélisation;



import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RefineryUtilities;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.LookUtils;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
;

final class environnement extends JFrame {
	
	 private static final long serialVersionUID = 1L;
	 BarChartDemo1 demo1=new BarChartDemo1("statistiques");
	 Principale p = new Principale();
	 Sub_service s = new Sub_service();
	 JPanel panneau = new JPanel();
	 GridLayout gridLayout ;
	 JTextField[]txt=new JTextField[8];
	 Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	 int[]vals = new int[8];		
	 JCheckBox[]cb=new JCheckBox[8];
     JFrame builder = new JFrame();
     Lecture demo= new Lecture();
	 int size=0;
	 Object[][] nvdonnee;
	 private SimpleInternalFrame managerFrame;
	 final JFrame framemsg = new JFrame();
	 final JFrame framehis = new JFrame();
	 JButton boutonp = new JButton("Afficher infos");
	 JButton boutont = new JButton("Tester");
	 JButton clear = new JButton("Clear");
	 JButton boutonI = new  JButton("Invoquer un SW");
	 JButton valider = new JButton("Valider");
	 JButton boutonmsg = new JButton("Afficher messages");
	 JButton boutonhis = new JButton("Historique");
	 Box chek = Box.createVerticalBox();
	 JLabel lblnom = new JLabel();
	 DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Les méthodes");
	 DefaultMutableTreeNode childNode;
	 DefaultMutableTreeNode childchildNode;
	 DefaultMutableTreeNode childchildchildNode;
	 JTree  myJTree = new JTree(rootNode);
	 JTextArea  textArea1= new JTextArea ();
	 JTextArea  text= new JTextArea ();
	 JTextArea  text2= new JTextArea ();
	 JScrollPane scrollPane;
	 JScrollPane scrollPane1;
	 JScrollPane sc = new JScrollPane(textArea1);
     public Container C =getContentPane(); 
     surveillance.Monitor monitor=new surveillance.Monitor();
     boolean serv = false;
     String service_inv;
	 

    
	public environnement() {	
		
		this.setTitle("Parseur");
		this.setBackground(Color.BLACK);	
		scrollPane1 = new JScrollPane(new mxGraphComponent(p.process.pars.graph));
		textArea1.setFont(new java.awt.Font("Dialog", 1, 11));
		builder.setPreferredSize(new Dimension(300,300));
		builder.setBackground(Color.LIGHT_GRAY);
		boutonp.setEnabled(true); 
		boutonI.setEnabled(true);
		boutont.setEnabled(true);		
		clear.setEnabled(true);
		boutonmsg.setEnabled(true);
		boutonhis.setEnabled(true);	
		valider.setPreferredSize(new Dimension(15,30));
		framehis.setTitle("Historique des statistiques");
		text.setBackground(Color.LIGHT_GRAY);
	    text.setFont(new java.awt.Font("Dialog", 1, 11));
	    framemsg.setTitle("Les messages SOAP");
	    text2.setBackground(Color.LIGHT_GRAY);
    	text2.setFont(new java.awt.Font("Dialog", 1, 11));
    	gridLayout = new GridLayout(8,2);
    	panneau.setLayout(gridLayout);
	   }
	
	
	
	public void setSW() throws IOException{	
		
		monitor.Get_services();	
		Object[] SWPossibles = new Object[monitor.names.size()] ;
		int j=1;
		for (int i=0;i<monitor.names.size();i++){
			SWPossibles[i]=monitor.names.elementAt(i);
			
		}
		
    	Object valeurSelectionnée =JOptionPane.showInputDialog(this, "Choisir un service déployé :",
             "invocation de service", JOptionPane.INFORMATION_MESSAGE,null,SWPossibles,SWPossibles[0]);
    	
    	
    	if(valeurSelectionnée.toString().equals("CalculatorWS")==true){
    		serv=true;
    		service_inv="CalculatorWS";
    	}
    	if(valeurSelectionnée.toString().equals("HotelReservationService")==true){
    		service_inv="Hotel";
       	}
    	if(valeurSelectionnée.toString().equals("AirlineReservationService")==true){
    		service_inv="Airline";
       	}
    	if(valeurSelectionnée.toString().equals("VehicleReservationService")==true){
    		service_inv="Vehicle";
       	}
    	if(valeurSelectionnée.toString().equals("SynchronousSample")==true){
    		service_inv="SynchronousSample";
       	}
	
	}

	
	 private Container buildContentPane() {
	        // en-tÃªte et titre
	        JPanel headerPanel = new JPanel(new BorderLayout());
	   
	        // contenu
	        SimpleInternalFrame operationsFrame =
	            new SimpleInternalFrame(UIHelper.readImageIcon(""), "Action");
	        
	        
	        JSplitPane splitPane =
	            Factory.createStrippedSplitPane(
	                JSplitPane.HORIZONTAL_SPLIT,
	                operationsFrame,
	              
	                managerFrame,
	                0.08f);
	        
	        splitPane.setContinuousLayout(true);
			
			JButton  boutont = UIHelper.createButton("Test ", "start2", true);
			boutont.addActionListener(new testAction());
			boutont.setToolTipText("Lancer le test du service invoqué");
			JButton  boutonp = UIHelper.createButton("Description de service", "info1", true);
			boutonp.addActionListener(new ParsingAction());
			boutonp.setToolTipText("la description WSDL du service");
			JButton  clear = UIHelper.createButton("Nouveau ", "new", true);
			clear.addActionListener(new Animation_clear_mouseAdapter());
			clear.setToolTipText("Redémarrer");
			JButton boutonI =UIHelper.createButton("Invocation de services ", "invoquer", true);
			boutonI.addActionListener(new InvoqAction());
			boutonI.setToolTipText("Chercher des services web");
			JButton boutonmsg =UIHelper.createButton("Les messages SOAP ", "soap", true);
			boutonmsg.addActionListener(new TraceMessage());
			JButton boutonhis=UIHelper.createButton("Historique ", "histo", true);
			boutonhis.addActionListener(new HistoriqueAction());
			
			
			boutonp.setFont(new java.awt.Font("Dialog", 1, 11));
			boutont.setFont(new java.awt.Font("Dialog", 1, 11));
			clear.setFont(new java.awt.Font("Dialog", 1, 11));
			boutonI.setFont(new java.awt.Font("Dialog", 1, 11));
			boutonmsg.setFont(new java.awt.Font("Dialog", 1, 11));
			boutonhis.setFont(new java.awt.Font("Dialog", 1, 11));
			
			Box buttonsBox = Box.createVerticalBox();
			buttonsBox.setOpaque(false);	
			buttonsBox.add(boutonI);
			buttonsBox.add(boutonp);
			buttonsBox.add(boutont);
			buttonsBox.add(boutonmsg);
			buttonsBox.add(boutonhis);
			buttonsBox.add(clear);
			JScrollPane operations = new JScrollPane(buttonsBox);
			
			operations.setOpaque(false);
			operations.getViewport().setOpaque(false);
			operations.setViewportBorder(null);
	        operationsFrame.setContent(operations);
	
	        JPanel pane = new JPanel(new BorderLayout());
	        pane.add(BorderLayout.NORTH, headerPanel);
	        pane.add(BorderLayout.CENTER, splitPane);
	        return pane;
	    }

	 void panneauSimulation_mouseClicked(MouseEvent e){
			
		
	 }
	 

	
	class ParsingAction implements ActionListener{
		private Label jTextArea1;
		
		
		public void representer(Sub_service sw){
				
			
			 int k =-1;
			 boolean ok = false;	
			 textArea1.append("*************Le nom du service est "+sw.Get_name()+"**************\n");
			 textArea1.append("\n");

			 sw.Get_msg();
			 sw.Get_portType();
			 textArea1.append("********Les message******** \n  ");
			 for (int i =0; i< sw.vmsg.size();i++)	 
			 {
				 ok=false;
				 k++;
			 textArea1.append("-Le message ' "+sw.vmsg.elementAt(i)+" ' a comme paramètre(s) \n");
			 while(!ok){
			   if(sw.vparam.elementAt(k) != " "){
				   textArea1.append(" ' "+sw.vparam.elementAt(k)+" ' de type ' "+sw.vtype.elementAt(k)+" ' \n\n");
				   k++;
			   }
			   else
				   {ok=true;}
			 }
			 }
			 int h=-1;
			 int g=-1;
			for(int i=0;i< sw.vop.size();i++){
				childNode = new DefaultMutableTreeNode(sw.vop.elementAt(i));
				rootNode.add(childNode);
				h++;
				g++;
			       if(sw.vinput.size()==0)
			    	   textArea1.append(" aucun message\n ");
			       childchildNode = new DefaultMutableTreeNode("Messages d'entrée");
			       childNode.add(childchildNode);
					while(!(sw.vinput.elementAt(h).equals(" "))&&(h<sw.vinput.size()))
		
					{
						childchildchildNode = new DefaultMutableTreeNode(sw.vinput.elementAt(h));
						childchildNode.add(childchildchildNode);
					    h++;
					}
					childchildNode = new DefaultMutableTreeNode("Messages de sortie");
					 childNode.add(childchildNode);

					while(!(sw.voutput.elementAt(g).equals(" "))&&(g<sw.voutput.size()))
		
					{
						childchildchildNode = new DefaultMutableTreeNode(sw.voutput.elementAt(g));
						childchildNode.add(childchildchildNode);
						g++;
					}
			}
			
			textArea1.append("\n");
			textArea1.append("********Les informations techniques ******** \n");
			textArea1.append("-Le style est "+sw.Get_style()+"\n");
			textArea1.append("-Le protocole de transport est "+sw.Get_protocole()+"\n");
			textArea1.append("-L'url du sw est "+sw.Get_location()+"\n");
			
			
			for(int i=0;i<myJTree.getRowCount();i++)
				myJTree.expandRow(i);
		}
		
	
		
		public void actionPerformed(ActionEvent e){
			
			if(serv==true){
				s.Set_structure("C:/CalculatorWS.wsdl");
				representer(s);	
			}
			else{
				
				 s.Set_structure("C:/"+service_inv+".wsdl");
				 representer(s);
				if(!p.process.pars.test)
				{ textArea1.append("\n  C'est un service composite de TravelReservationService\n\n");				 
				 p.executer("C:/TravelReservationService.bpel");}
				else{
					textArea1.append("   C'est un service composite de SynchronousSample\n\n");				 
					 p.executer("C:/SynchronousSample.bpel");
				}
			}
							    
			}
						 
			
	}

	
	class InvoqAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				
				environnement.this.setSW();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
	class Animation_panneauSimulation_mouseAdapter extends java.awt.event.MouseAdapter{
		Animation_panneauSimulation_mouseAdapter(){}
		public void mouseClicked(MouseEvent e){panneauSimulation_mouseClicked(e);}
		}

	
public	JComponent build() {
	JPanel panel = new JPanel(new BorderLayout());
	panel.setOpaque(false);
	panel.setBorder(Borders.DIALOG_BORDER);
	panel.add(BorderLayout.CENTER,buildHorizontalSplit());
	return panel;
		
	}
	


	

	private JComponent buildHorizontalSplit() {
		JComponent left = new JScrollPane(buildContentPane());
		left.setPreferredSize(new Dimension(200, 200));

		JComponent upperRight = new JScrollPane(buildcadre());//les boutons clear et run;
		upperRight.setPreferredSize(new Dimension(520, 520));
		
		
		JComponent lowerRight = new JScrollPane(buildcadre1());
		lowerRight.setPreferredSize(new Dimension(520, 65));
		
		
		JSplitPane verticalSplit = UIFSplitPane.createStrippedSplitPane(
					JSplitPane.VERTICAL_SPLIT, 
					upperRight, 
					lowerRight);/// null);
		//verticalSplit.setOpaque(false);
		JSplitPane horizontalSplit = UIFSplitPane.createStrippedSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
		verticalSplit,
		left);
		horizontalSplit.setOpaque(false);
		

		return horizontalSplit;
	}
	
	public void cheking(){
		
		
		valider.setFont(new java.awt.Font("Dialog", 1, 11));
		valider.addActionListener(new validation());
		cb[0]=new JCheckBox("total num fault");
		cb[1]=new JCheckBox("total num success");
		cb[2]=new JCheckBox("Average response time");
		cb[3]=new JCheckBox("MaxResponseTime");
		cb[4]=new JCheckBox("MinResponseTime");
		cb[5]=new JCheckBox("through put ");
		cb[6]=new JCheckBox("total num auth failure");
		cb[7]=new JCheckBox("total auth success");
		cb[0].setSelected(true);
		for(int i=0;i<8;i++){
			cb[i].setFont(new java.awt.Font("Dialog", 1, 11));
		}
		for(int j=0;j<8;j++){
			txt[j]=new JTextField("0");
		}
		txt[1].setEditable(false);
		txt[1].setBackground(Color.PINK);
		txt[7].setEditable(false);
		txt[7].setBackground(Color.PINK);
		builder.setLocation((screen.width - builder.getSize().width)/2,(screen.height - builder.getSize().height)/2);
		
		builder.setVisible(true);
		builder.pack();
		builder.setTitle("Les propriétés de monitoring");
		panneau.add(cb[0]);
		panneau.add(txt[0]);
		panneau.add(cb[1]);
		panneau.add(txt[1]);
		panneau.add(cb[2]);
		panneau.add(txt[2]);
		panneau.add(cb[3]);
		panneau.add(txt[3]);
		panneau.add(cb[4]);
		panneau.add(txt[4]);
		panneau.add(cb[5]);
		panneau.add(txt[5]);
		panneau.add(cb[6]);
		panneau.add(txt[6]);
		panneau.add(cb[7]);
		panneau.add(txt[7]);
		
		builder.add(panneau, BorderLayout.CENTER);
		builder.add(BorderLayout.SOUTH,valider);
		
		
		
	}
	class validation implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			try {
				monitor.surveiller(service_inv,Integer.parseInt(txt[0].getText()),
				Integer.parseInt(txt[1].getText()),
				Integer.parseInt(txt[2].getText()),Integer.parseInt(txt[3].getText())
			,Integer.parseInt(txt[4].getText()),Double.parseDouble(txt[5].getText()),
			Integer.parseInt(txt[6].getText()),Integer.parseInt(txt[7].getText()));
				monitor.evaluer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(monitor.Get_Stat()){
				builder.hide();
				JOptionPane.showMessageDialog(null, "Test Failed", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else{
			
			for(int j=0;j<8;j++){
				if (cb[j].isSelected()){
					vals[j]=j;
					size++;
				}
			}
			nvdonnee=new Object[size][4];
			int k =-1;
			for(int i=0;i<size;i++){
				k++;
				for(int j=0;j<4;j++){
		
				while(!cb[k].isSelected()){
					k++;
					}
				nvdonnee[i][j]=monitor.donnee[vals[k]][j];	
						
					
				}
			}
			  
			for(int i=0;i<size;i++){
			demo1.tableaupro.addElement(nvdonnee[i][0]);
			demo1.tableauValeur.addElement(nvdonnee[i][1]);
			}
			
			builder.hide();
			CategoryDataset dataset = demo1.createDataset();
	        JFreeChart chart = demo1.createChart(dataset);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setFillZoomRectangle(true);
	        chartPanel.setMouseWheelEnabled(true);
	        chartPanel.setPreferredSize(new Dimension(550, 450));
	        JTextArea lbl1=new JTextArea();
	        lbl1.setFont(new java.awt.Font("Dialog", 1, 11));
	    	if((monitor.ok[0])&&(cb[0].isSelected()))
				{lbl1.append("Des erreurs Soap sont produites,consulter les fichiers logs\n ");				
				}
	    	if((monitor.ok[3])&&(cb[2].isSelected()))
			{lbl1.append("Ce service met trop de temps pour répondre \n");
			lbl1.append("le service "+monitor.comparaison[0][0]+" présente un temps de réponse plus petit "+monitor.comparaison[0][1]+"\n");
			}
	    	if((monitor.ok[2])&&(cb[6].isSelected()))
			{lbl1.append("Un echec d'authentification s'est produit\n ");			
			}
	    	if((monitor.ok[4]&&(cb[3].isSelected())))
			{lbl1.append("Le temps de réponse Max est dépassé\n ");		
			}
	    	if((monitor.ok[5])&&(cb[4].isSelected()))
			{lbl1.append("Le temps de réponse Min est dépassé\n ");			
			}
	    	if((monitor.ok[6])&&(cb[5].isSelected()))
			{lbl1.append("Le nombre de requêtes traitées par seconde n'a pas atteint la valeur demandée\n ");			
			}
	    	
	        final JFrame monCadre = new JFrame();
	    	monCadre.setTitle("Statistiques de l'application");
	    	monCadre.add(BorderLayout.SOUTH,lbl1);
	    	monCadre.add(BorderLayout.CENTER,chartPanel);
	    	monCadre.pack(); 
	    	RefineryUtilities.centerFrameOnScreen(monCadre);
	    	monCadre.setVisible(true);
			}
		}
		
	}
	
	class Animation_clear_mouseAdapter implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			clear_mousePressed();
		}	
	}
	
	public void clear_mousePressed(){
		
		textArea1.setText("");		
		text.setText("");
		if(rootNode.getChildCount()!=0)
		{	rootNode.removeAllChildren();
		 childNode.removeAllChildren();
		 childchildNode.removeAllChildren();
		 childchildchildNode.removeAllChildren();
		 ((DefaultTreeModel)myJTree.getModel()).reload();}
		
		if(serv==false)
		{p.initialiser();}
		
		serv=false;		
		s.initialiser();
		monitor.initialiser();
		size=0;
		service_inv=null;
		panneau.removeAll();
		cb=new JCheckBox[8];
		txt=new JTextField[8];
		demo.initialiser();
		demo1.initialiser();
	   }
	

class HistoriqueAction implements ActionListener {
	BufferedReader lecteurAvecBuffer = null;
    String ligne;
	JScrollPane scroll = new JScrollPane(text);
	
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		    try
		      {
		    	if(service_inv=="CalculatorWS")
			{lecteurAvecBuffer = new BufferedReader
			  (new FileReader("C:/monitor1.txt"));
			demo.readfile("C:/imc1.txt");}
		    	
		    	if(service_inv=="Hotel")
					{lecteurAvecBuffer = new BufferedReader
					  (new FileReader("C:/monitor2.txt"));
					demo.readfile("C:/imc2.txt");}
		    	
		    	if(service_inv=="Airline")
					{lecteurAvecBuffer = new BufferedReader
					  (new FileReader("C:/monitor3.txt"));
					demo.readfile("C:/imc3.txt");}
		    	
		    	if(service_inv=="Vehicle")
					{lecteurAvecBuffer = new BufferedReader
					  (new FileReader("C:/monitor4.txt"));
					demo.readfile("C:/imc4.txt");}
		    	if(service_inv=="SynchronousSample")
					lecteurAvecBuffer = new BufferedReader
					  (new FileReader("C:/monitor5.txt"));
		    	
		      }
		    catch(FileNotFoundException exc)
		      {
		      } catch (IOException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			}
		      demo.chartPanel = demo.createDemoPanel();
			    demo.chartPanel.setPreferredSize(new java.awt.Dimension(550, 450));
			    demo.setContentPane(demo.chartPanel);
		        demo.pack();
		        RefineryUtilities.centerFrameOnScreen(demo);
		        demo.setVisible(true);
		}
	}
	
	
class TraceMessage implements ActionListener {
	JScrollPane scroll = new JScrollPane(text2);
		public void actionPerformed(ActionEvent e) {

			try {
				monitor.testMessageTrace(service_inv);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated method stub
			
			if(monitor.Get_Stat()){
				JOptionPane.showMessageDialog(null, "Test Failed", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else{
	    	for(int i =0;i<monitor.messages.length;i++){
	    		
	    			text2.append( "Message id "+monitor.messages[i][0].toString()+"\n");
	    			text2.append( "Response size "+monitor.messages[i][1].toString()+"\n");
	    			text2.append( "Response content "+monitor.messages[i][2].toString()+"\n");
	    			text2.append( "Request size "+monitor.messages[i][3].toString()+"\n");
	    			text2.append( "Request content "+monitor.messages[i][4].toString()+"\n");
	    		
	    		text2.append("\n");
	    	}
	    	framemsg.setPreferredSize(new java.awt.Dimension(550, 450));
	    	framemsg.add(scroll);
	        framemsg.addWindowListener(new WindowAdapter()
	  	      {
	  		public void windowClosing(WindowEvent evt)
	  		  {
	  			framemsg.hide();
	  		  }
	  	      });
	        framemsg.pack(); 
	        framemsg.setLocation((screen.width - framemsg.getSize().width)/2,(screen.height - framemsg.getSize().height)/2);
	       framemsg.setVisible(true); 
		}	
		}
	}
		
	private JPanel buildcadre(){
		
	    JPanel panel = new JPanel();
	    scrollPane = new JScrollPane(myJTree);
	    scrollPane.setPreferredSize(new Dimension(170,250));
	    sc.setPreferredSize(new Dimension(410,250));
	    textArea1.setEditable(false); 
	    panel.add(scrollPane);
	    panel.add(sc);
	    
	  return panel;}
	  
	
	
	private JComponent buildcadre1(){
		JPanel panel = new JPanel();		
		scrollPane1.setPreferredSize(new Dimension(600,400));  
	    panel.add(scrollPane1);
		
		return panel;	   
	    }
	
	 public class testAction implements ActionListener  {
		 
		 String[] nomsColonnes = {"Statistiques", "Valeur", "Unité", "Description"}; 
		
		 public void actionPerformed(ActionEvent e) {
				cheking();  
				
	        }
	        
	 }
	 
	}

	
