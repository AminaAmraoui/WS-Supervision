/**
 * 
 */
package modélisation;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.input.SAXBuilder;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxImageCanvas;
import com.mxgraph.swing.mxGraphComponent;



import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
/**
 * @author Amraoui
 *
 */
public class Parser_bpel {
	static int nbre_partner;
	static int nbre_var;
	static String nom_process;
	static Vector var= new Vector();
	static Vector partner= new Vector();
	static Vector partnerf= new Vector();
	static Vector partners= new Vector();
	static Vector att = new Vector();
	static Vector cancel = new Vector();
	static Object [ ] v;
	static Object [ ] C;
	static Object f,s;
	static mxGraph graph = new mxGraph();
	static Object parent = graph.getDefaultParent();
    static int sw;
    static int rep;
    static public boolean test;

	public Parser_bpel(){
		test=false;
	}
	
	public void parcourir(Element racine){
		
        nom_process=racine.getAttributeValue("name");
	    
	    List listpartner=racine.getChild("partnerLinks").getChildren("partnerLink");
	    nbre_partner=listpartner.size();
	    
	    
	    List listvar=racine.getChild("variables").getChildren("variable");
	    nbre_var=listvar.size();
	    Iterator k = listvar.iterator();
	    while(k.hasNext())
			{    Element courant = (Element)k.next(); 
			     var.addElement(courant.getAttributeValue("name"));
			}
	    
	    Iterator k1 = listpartner.iterator();
	    while(k1.hasNext())
			{    Element courant = (Element)k1.next(); 
				partner.addElement(courant.getAttributeValue("name"));
			}
	    
	    
	    if(racine.getChild("sequence").getChild("switch") == null)
	    	{sw=0;rep=0;}
	    else
	    	{sw=1;
	    	if(racine.getChild("sequence").getChild("switch").getChild("case").getChild("reply") == null)
		    	rep=0;
		    else
		    	rep=1;
	    	}
	    
	    if(racine.getChild("sequence").getChildren("if")==null)
	    	test=true;
	    else{
	    List lists = racine.getChild("sequence").getChildren("if");
	    Iterator k3 = lists.iterator();
	    while(k3.hasNext())
		{    Element courant = (Element)k3.next(); 
		    att.addElement(courant.getAttributeValue("name"));
		    cancel.addElement(courant.getChild("sequence").getChild("pick").getChild("onAlarm").getChild("sequence").getChild("assign").getAttributeValue("name"));
			partners.addElement(courant.getChild("sequence").getChild("invoke").getAttributeValue("partnerLink"));
		}
	    }
	    
	}
	
	public void dessiner_graphe(Element racine){
		parcourir(racine);
		 f = graph.insertVertex(parent, null, "Start", 250, 50, 80,
		         30);
		
		 s = graph.insertVertex(parent, null, "Reply", 250, 260, 80,
		         30);
		
		if(!test){
		C= new Object[cancel.size()];
		for(int i=0;i<cancel.size();i++){
			C[i] =graph.insertVertex(parent, null, cancel.elementAt(i), 40*5*i+100, 220, 120,
		         30);
			System.out.println("dessiner "+nom_process);
		}
		}
		
		graph.getModel().beginUpdate();
		try
		{
			
			v = new Object[partner.size()];
				for(int j=0;j<partner.size();j++){
				v[j] = graph.insertVertex(parent, null, partner.elementAt(j), 30*5*j+100, 150+10*j, 100,
				         20);
				System.out.println("partner "+partner.elementAt(j));
				
				
				System.out.println("size_p "+partners.size());
				for(int h=0;h<partners.size();h++){
					if(partners.elementAt(h).equals(partner.elementAt(j)))
						{graph.insertEdge(parent, null, att.elementAt(h), f, v[j]); 
						if(!test)
						{graph.insertEdge(parent, null, "onMsg", v[j], s);
						
						graph.insertEdge(parent, null, "onAlarm", v[j], C[h]);}
						}
					
				}
				}
				
			
		   graph.insertEdge(parent, null, racine.getChild("sequence").getChild("receive").getAttributeValue("name"), v[0], f);
		   graph.insertEdge(parent, null, racine.getChild("sequence").getChild("reply").getAttributeValue("name"), s, v[0]);

		}
		finally
		{
		   graph.getModel().endUpdate();
		}
		
	}
	
	public void initialiser(){
		System.out.println("remove in");
		for(int k=0;k<graph.getEdges(v[0], f).length;k++)
		{graph.getModel().remove(graph.getEdges(v[0], f)[k]);}
		for(int k=0;k<graph.getEdges(s, v[0]).length;k++)
		{graph.getModel().remove(graph.getEdges(s, v[0])[k]);}
		System.out.println("edges v[0] f "+graph.getEdges(v[0], f).length);
		System.out.println("edges s v[0] "+graph.getEdges(s, v[0]).length);
		
	      for(int i=0;i<C.length;i++)
	      { 
	    	  for(int j=0;j<v.length;j++)
	    	  {  for(int k=0;k<graph.getEdges(C[i], v[j]).length;k++)
	    		  graph.getModel().remove(graph.getEdges(C[i], v[j])[k]);
	    	  }
	    	  
	      }
	      for(int h=0;h<v.length;h++){
	    	  for(int k=0;k<graph.getEdges(f,v[h]).length;k++)
	    	  graph.getModel().remove( graph.getEdges(f,v[h])[k]); 
	    	 
	      }
	      for(int h=0;h<v.length;h++){
	    	  for(int k=0;k<graph.getEdges(v[h],s).length;k++)
	    	  graph.getModel().remove( graph.getEdges(v[h],s)[k]);     	 
	      }
	     
	      graph.getModel().remove( f);
	      graph.getModel().remove( s);
	      if(C!=null)
	      {   for(int i=0;i<C.length;i++)
	    	  graph.getModel().remove( C[i]);
	    	  System.out.println("remove c");  
	      }
	      if(v!=null)
	      {
	    	  for(int i=0;i<v.length;i++)
	    	  graph.getModel().remove( v[i]);
	    	  System.out.println("remove v");  
	      }
	      
		nbre_partner=0;
		nbre_var=0;
		nom_process=null;
		var.clear();
		partner.clear();
		partnerf.clear();
		partners.clear();
		att.clear();
		cancel.clear();
		C=null;
		v=null;
		sw=0;
		rep=0;
		System.out.println("init parseur");
		
	}

}



