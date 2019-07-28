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
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class Sub_service {
	
	private org.jdom.Document document;
	private Element racine;
	static Vector vmsg = new Vector();
	static Vector vparam = new Vector();
	static Vector vtype = new Vector();
	static Vector vop = new Vector();
	static Vector vinput = new Vector();
	static Vector voutput = new Vector();
	//Namespace soap;
	
	public Sub_service(){
		
		document=null;
		racine=null;
	}
	
	public void Set_structure(String file){
		
		SAXBuilder sxb = new SAXBuilder();
	    try
	    {	         	
	       document = sxb.build(new File(file));	      
	    }
	    catch(Exception e){}
	    
	    racine = document.getRootElement();
	}
	
	public String Get_name(){
		return racine.getAttributeValue("name");
	}
	
	public void Get_msg(){
		
		List listmsg = racine.getChildren("message");
		System.out.println("msg "+listmsg.size());
		Iterator k = listmsg.iterator();
		
		while(k.hasNext()){
			Element courant = (Element)k.next();
			vmsg.addElement(courant.getAttributeValue("name"));
			
	          
	            List listmsg1 = courant.getChildren("part");
	  			Iterator k1 = listmsg1.iterator();
	  			
	  			while(k1.hasNext())
	  			{    
	  				Element courant1 = (Element)k1.next(); 
	  			    vparam.addElement(courant1.getAttributeValue("name"));
	  			    vtype.addElement(courant1.getAttributeValue("element"));
	  			    	  				  			
	  			}
	  			
	   		vparam.addElement(" ");
	   		vtype.addElement(" ");
	   			
	   			}
		}
	
	public void Get_portType(){
		    
		    List listprt = racine.getChildren("portType");
			Iterator p = listprt.iterator();	
       while(p.hasNext())
       {
    	     Element courant2 =(Element)p.next(); 
    	     List listmsg2 = courant2.getChildren("operation");
			 Iterator k2 = listmsg2.iterator();
			 
			 while(k2.hasNext())
			 {
			     Element courant4 =(Element)k2.next(); 			 
			     vop.addElement(courant4.getAttributeValue("name"));
			     System.out.println("opinside "+courant4.getAttributeValue("name"));
			     
			     List listin = courant4.getChildren("input");
				 Iterator in = listin.iterator();
				 System.out.println("msg input "+listin.size());
				 if(listin.size()!=0){
			     while(in.hasNext())
			     {
			    	 Element elt=(Element)in.next();
			    	 vinput.addElement(elt.getAttributeValue("message"));
			    	 System.out.println("inputinside "+elt.getAttributeValue("message"));
			     }		
				 }
		         List listsortie = courant4.getChildren("output");
				 Iterator sortie = listsortie.iterator();
				 System.out.println("msg output "+listsortie.size());
				 if(listsortie.size()!=0){
				 while(sortie.hasNext())
				 {
					 Element courant3 = (Element)sortie.next();					 			 
					 voutput.addElement(courant3.getAttributeValue("message"));
					 System.out.println("outputinside "+courant3.getAttributeValue("message"));
			
				 }
				 }
				 vinput.addElement(" ");
			     voutput.addElement(" ");
			 }
			 
			 
       }
       for(int i=0;i<vinput.size();i++){
    	   System.out.println("eltinput "+i+" "+vinput.elementAt(i));
       }
       for(int i=0;i<voutput.size();i++){
    	   System.out.println("eltoutput "+i+" "+voutput.elementAt(i));
       }
	}
	
	public String Get_style(){
		return racine.getChild("binding").getChild("binding").getAttributeValue("style");
	}
	
	public String Get_protocole(){
		return racine.getChild("binding").getChild("binding").getAttributeValue("transport");
	}
	
	public String Get_location(){
		return racine.getChild("service").getChild("port").getChild("address").getAttributeValue("location");
	}
	
	public void initialiser(){
		document=null;
		racine=null;
		vmsg.clear();
		vparam.clear();
		vtype.clear();
		vop.clear();
		vinput.clear();
		voutput.clear();
	}
}
