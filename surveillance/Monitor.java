/**
 * 
 */
package surveillance;
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.management.j2ee.statistics.CountStatistic;
import javax.swing.JFrame;

import modélisation.Splash;

import com.sun.appserv.management.base.XTypes;
import com.sun.appserv.management.client.AppserverConnectionSource;
import com.sun.appserv.management.ext.wsmgmt.MessageTrace;
import com.sun.appserv.management.ext.wsmgmt.WebServiceEndpointInfo;
import com.sun.appserv.management.j2ee.J2EETypes;
import com.sun.appserv.management.j2ee.WebServiceEndpoint;
import com.sun.appserv.management.j2ee.statistics.NumberStatistic;
import com.sun.appserv.management.monitor.WebServiceEndpointMonitor;
import com.sun.appserv.management.monitor.statistics.WebServiceEndpointAggregateStats;

/**
 * @author Amraoui
 *
 */
public class Monitor extends AMXTestBase{
	public Object[][] donnee = new Object[8][4]; 
	public Object[][] messages;
	public Object[][] comparaison= new Object[1][2];
	AppserverConnectionSource connection;
	public Vector names;
	public boolean[] ok = new boolean[8];
	public boolean[] state = new boolean[8];
	PrintWriter ecrivain;
	
	
	public Monitor(){
		connection = new AppserverConnectionSource("localhost", 8686, "admin", "adminadmin", null);
	    names = new Vector();
	    for(int i=0;i<8;i++){
	    	ok[i]=false;
	    }
	    for(int j=0;j<8;j++){
	    	state[j]=false;
	    }

	}
	
	public void evaluer() throws IOException{
		String name=null;
		long val =10000;
		final Set <WebServiceEndpointMonitor> ms  = connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( XTypes.WEBSERVICE_ENDPOINT_MONITOR);
		for( final WebServiceEndpointMonitor m1 : ms ){
			 final WebServiceEndpointAggregateStats s =m1.getWebServiceEndpointAggregateStats();
			 if(s.getAverageResponseTime().getCount()<val){
				 name=m1.getName();
				 val=s.getAverageResponseTime().getCount();				 
			 }
		}
		 comparaison[0][0]=name;
		 comparaison[0][1]=val;
		 System.out.println("in monitor  "+comparaison[0][0]+" "+comparaison[0][1]);
	}
	
	public void Get_services() throws IOException{
		final Set <WebServiceEndpointMonitor> ms  = connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( XTypes.WEBSERVICE_ENDPOINT_MONITOR);
		
		for( final WebServiceEndpointMonitor m1 : ms ){
			names.add(m1.getName());
			System.out.println("added "+m1.getName());
			
		}
		
	}
	public void surveiller(String sw,int val1,int val2,int val3
			,int val4,int val5,double val6,int val7,int val8) throws IOException{
		if(sw=="CalculatorWS")
		ecrivain =  new PrintWriter(new BufferedWriter
	    		   (new FileWriter("C:/monitor1.txt",true)));
		if(sw=="Hotel")
			ecrivain =  new PrintWriter(new BufferedWriter
		    		   (new FileWriter("C:/monitor2.txt",true)));
		if(sw=="Airline")
			ecrivain =  new PrintWriter(new BufferedWriter
		    		   (new FileWriter("C:/monitor3.txt",true)));
		if(sw=="Vehicle")
			ecrivain =  new PrintWriter(new BufferedWriter
		    		   (new FileWriter("C:/monitor4.txt",true)));
		if(sw=="SynchronousSample")
			ecrivain =  new PrintWriter(new BufferedWriter
		    		   (new FileWriter("C:/monitor5.txt",true)));
		
		
		final Set <WebServiceEndpointMonitor> ms  = connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( XTypes.WEBSERVICE_ENDPOINT_MONITOR);
		for( final WebServiceEndpointMonitor m1 : ms ){
			
			System.out.println("boolean "+m1.getName().contains(sw));
		if(m1.getName().contains(sw)){
		
			System.out.println("\n \n Name of web service is " + m1.getName());
			Date now = new Date();

			ecrivain.print("\n"+now.getDate()+"/"+now.getMonth()+";");

            final WebServiceEndpointAggregateStats s =
                    m1.getWebServiceEndpointAggregateStats();

            final CountStatistic  r1 = s.getTotalFaults();
            assert( r1 != null );
            System.out.println(" total num fault is "+r1.getDescription());
            System.out.println(" "+ r1.getCount()+" "+r1.getUnit());
            donnee[0][0]="Total faults";
            donnee[0][1]=r1.getCount();
            donnee[0][3]=r1.getDescription();
            donnee[0][2]=r1.getUnit();
            if(r1.getCount()>val1){
            	ok[0]=true;
            }
            if(r1.getCount()==0){
            	state[0]=true;
            }

            final CountStatistic  r2 = s.getTotalNumSuccess() ;
            assert( r2 != null );
            System.out.println(" total num success is "+ r2.getDescription());
            System.out.println(" "+ r2.getCount()+" "+r2.getUnit());
            donnee[1][0]="Total num success";
            donnee[1][1]=r2.getCount();
            donnee[1][3]=r2.getDescription();
            donnee[1][2]=r2.getUnit();
            if(r2.getCount()==0){
            	state[1]=true;
            }
            

            final CountStatistic  r3 = s.getAverageResponseTime();
            assert( r3 != null );
            System.out.println("avg resp is "+ r3.getDescription());
            System.out.println(" "+ r3.getCount()+" "+r3.getUnit());
            donnee[2][0]="Average response time";
            donnee[2][1]=r3.getCount();
            donnee[2][3]=r3.getDescription();
            donnee[2][2]=r3.getUnit();
            if(r3.getCount()>val3){
            	ok[3]=true;
            }
            if(r3.getCount()==0){
            	state[2]=true;
            }

            
            final CountStatistic r4 = s.getMaxResponseTime();
            assert(r4 != null);
            System.out.println("MaxResponseTime "+ r4.getDescription());
            System.out.println(" "+ r4.getCount()+" "+r4.getUnit());
            donnee[3][0]="Max Response Time";
            donnee[3][1]=r4.getCount();
            donnee[3][3]=r4.getDescription();
            donnee[3][2]=r4.getUnit();
            if(r4.getCount()>val4){
            	ok[4]=true;
            }
            if(r4.getCount()==0){
            	state[3]=true;
            }
            
            
            final CountStatistic r5 =s.getMinResponseTime();
            assert(r5 != null);
            System.out.println("MinResponseTime "+ r5.getDescription());
            System.out.println(" "+ r5.getCount()+" "+r5.getUnit());
            donnee[4][0]="Min Response Time";
            donnee[4][1]=r5.getCount();
            donnee[4][3]=r5.getDescription();
            donnee[4][2]=r5.getUnit();
            if(r5.getCount()>val5){
            	ok[5]=true;
            }
            if(r5.getCount()==0){
            	state[4]=true;
            }
            


            final NumberStatistic c1 = s.getThroughput() ;
            assert( c1 != null );
            System.out.println(" through put is "+ c1.getDescription());
            System.out.println(" "+ c1.getCurrent()+" "+c1.getUnit());
            donnee[5][0]="through put";
            donnee[5][1]=c1.getCurrent();
            donnee[5][3]="Nbre de requetes de SW traitées par sec.";
            donnee[5][2]=c1.getUnit();
            if(c1.getCurrent().intValue()<val6){
            	ok[6]=true;
            }
            if(c1.getCurrent().intValue()==0){
            	state[5]=true;
            }

            final CountStatistic c2 = s.getTotalAuthFailures();
            assert( c2 != null );
            System.out.println(" total num auth failure is "+ c2.getDescription());
            System.out.println(" "+ c2.getCount()+" "+c2.getUnit());
            donnee[6][0]="total num auth failure";
            donnee[6][1]=c2.getCount();
            donnee[6][3]=c2.getDescription();
            donnee[6][2]=c2.getUnit();
            if(c2.getCount()>val2){
            	ok[2]=true;
            }
            if(c2.getCount()==0){
            	state[6]=true;
            }


            final CountStatistic  c3 = s.getTotalAuthSuccesses();
            assert( c3 != null );
            System.out.println(" total auth success is "+ c3.getDescription());
            System.out.println(" "+ c3.getCount()+" "+c3.getUnit());
            donnee[7][0]="total auth success";
            donnee[7][1]=c3.getCount();
            donnee[7][3]=c3.getDescription();
            donnee[7][2]=c3.getUnit();
            if(c3.getCount()==0){
            	state[7]=true;
            }
		}
		}
					
		ecrivain.println(donnee[2][1]);	
		ecrivain.close();
	
	}
	
	public void testMessageTrace(String sw) throws IOException {
        assert (connection.getDomainRoot().getWebServiceMgr() != null);
        System.out.println("i am in monitor ");
       final Set <WebServiceEndpoint> s =
       	connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet(J2EETypes.WEB_SERVICE_ENDPOINT);

       for( final WebServiceEndpoint wsp : s )
       {
    		if(wsp.getName().contains(sw)){
          final MessageTrace[] msgs = wsp.getMessagesInHistory();
            if ( msgs == null)
            {
                System.out.println(" No messages found");
              
            }
            else{
            System.out.println(" Collected messages " + msgs.length);
            messages=new Object[msgs.length][5]; 
            for ( int idx =0; idx < msgs.length; idx++)
            {
                final MessageTrace msg = msgs[idx];

                System.out.println(" message id " + msg.getMessageID());
                System.out.println(" response size " + msg.getResponseSize()+" b");
                System.out.println(" request size " + msg.getRequestSize());             
                System.out.println(" request content is " +msg.getRequestContent());
                System.out.println(" response content is " +msg.getResponseContent());
                messages[idx][0]=msg.getMessageID();
                messages[idx][1]=msg.getRequestSize();
                messages[idx][2]=msg.getRequestContent();
                messages[idx][3]=msg.getResponseSize();
                messages[idx][4]=msg.getResponseContent();
            }
               
            }
       }
            }
    }
    public boolean Get_Stat(){
    	int i=0;
    	for(int j=0;j<8;j++){
    		if(state[j])
    			i++;
    	}
    	if(i==8){
    		return true;
    	}
    	else
    		return false;
    }
    
    public void initialiser(){
    names.clear();
    donnee = new Object[8][4]; 
    comparaison= new Object[1][2];
    for(int i=0;i<3;i++){
    	ok[i]=false;
    }
    for(int j=0;j<8;j++){
    	state[j]=false;
    }

    }
	
}
