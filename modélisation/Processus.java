/**
 * 
 */
package modélisation;

/**
 * @author Amraoui
 *
 */
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Processus {
	
	private org.jdom.Document document;
	Element racine;
	private Vector sw;
	public Sub_service [] tab ;
	Parser_bpel pars ;//= new Parser_bpel();
	
	public Processus(){
		document=null;
		racine=null;
		sw=new Vector();
		pars = new Parser_bpel();
		tab=new Sub_service[5];
	}
	
    public void Set_structure(String file){
		
		SAXBuilder sxb = new SAXBuilder();
	    try
	    {	         	
	       document = sxb.build(new File(file));
	       System.out.println("yes");
	    }
	    catch(Exception e){}
	    
	    racine = document.getRootElement();
	    
	   // System.out.println("ok "+racine.getChildText("documentation"));
	    
	    List listpartner=racine.getChild("partnerLinks").getChildren("partnerLink");	    
	    Iterator it=listpartner.iterator();
	    while(it.hasNext()){
	    	Element courant = (Element)it.next(); 
	    	if(courant.getAttributeValue("partnerRole")!= null){
	    		sw.addElement(courant.getAttributeValue("name"));
	    	}
	    }
	    	    
	    for (int i=0;i< sw.size();i++){
	    	//tab=new Sub_service[sw.size()];
	    	String chemin = "C:/"+sw.elementAt(i).toString()+".wsdl";
	    	System.out.println(sw.size()+"C:/"+sw.elementAt(i).toString()+".wsdl");
	    	tab[i]= new Sub_service();
	    	tab[i].Set_structure(chemin);
	    //	System.out.println("C:/"+sw.elementAt(i).toString()+".wsdl");
	    	System.out.println(tab[i].Get_name());
	    	tab[i].Get_name();
	    	tab[i].Get_msg();
	    	tab[i].Get_portType();
	    	System.out.println(tab[i].Get_name());
	    //	System.out.println(tab[i].Get_protocole());
	    //	System.out.println(tab[i].Get_location());
	    //	System.out.println("contenu 1 : "+tab[i]);
	    }
	}
    
    public void dessiner(Element racine){
    	//
    	pars.dessiner_graphe(racine);
    }
    
    public String Gat_name(){
    	return racine.getAttributeValue("name");
    }
    
    public void Afficher_info_sub_service(){
    	tab=new Sub_service[sw.size()];
    	for (int i = 0; i< tab.length;i++){
    		System.out.println("contenu 1 : "+tab[i]);
    		
    	}
    }
    public void initialiser(){
    	sw.clear();
    	document=null;
    	racine=null;
   // 	tab=null;
    	pars.initialiser();
    	System.out.println("init processus");
    }

}
