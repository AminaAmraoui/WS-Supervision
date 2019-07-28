package surveillance;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.management.j2ee.statistics.CountStatistic;

import com.sun.appserv.management.base.XTypes;
import com.sun.appserv.management.client.AppserverConnectionSource;
import com.sun.appserv.management.ext.wsmgmt.WebServiceEndpointInfo;
import com.sun.appserv.management.j2ee.statistics.NumberStatistic;
import com.sun.appserv.management.monitor.WebServiceEndpointMonitor;
import com.sun.appserv.management.monitor.statistics.WebServiceEndpointAggregateStats;

/**
 * 
 */

/**
 * @author Amraoui
 *
 */
public class Cli {

	/**
	 * @param args
	 */
	static boolean ok =false;
	
	public static void executer(String[] cmd){
		File f = new File("C:\\Program Files\\glassfish-v2.1\\bin");
		try{
			
         Runtime rt = Runtime.getRuntime();
         System.out.println("Execing " + cmd[0] + " " + cmd[1] 
                            + " " + cmd[2]);
         Process proc = rt.exec(cmd,null,f);
         
         // any error message?
         StreamGobbler errorGobbler = new 
             StreamGobbler(proc.getErrorStream(), "ERROR");            
         
         // any output?
         StreamGobbler outputGobbler = new 
             StreamGobbler(proc.getInputStream(), "OUTPUT");
             
         // kick them off
         errorGobbler.start();
         outputGobbler.start();
                                 
         // any error???
         int exitVal = proc.waitFor();
         System.out.println("ExitValue: " + exitVal); 
         if(exitVal==0){
        	 ok=true;
         }
		}catch (Throwable t)
          {
            t.printStackTrace();
          }
	}
	
	public static boolean lancer_serv(){
		String cmd1[]= new String[3];
		cmd1[0]="cmd.exe";
		cmd1[1]="/C";
		cmd1[2]="asadmin start-domain domain1";
		executer(cmd1);
		return ok;
	}
	
	public static boolean arreter_serv(){
		String[] cmd = new String[3];
		cmd[0] = "cmd.exe" ;
		cmd[1] = "/C" ;
		cmd[2] = "asadmin stop-domain";
		executer(cmd);
		
		return ok;
	}
	
/*	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		
/*		String cmd1[]= new String[3];
		cmd1[0]="cmd.exe";
		cmd1[1]="/C";
		cmd1[2]="asadmin start-domain domain1";
		executer(cmd1);
		
		String[] cmd = new String[3];
		cmd[0] = "cmd.exe" ;
		cmd[1] = "/C" ;
		cmd[2] = "asadmin list-components --type webservice";
		executer(cmd);
		
		String[] cmd2= new String[3];
		cmd2[0]="cmd.exe";
		cmd2[1]="/C";
		cmd2[2]="asadmin get -m server.applications.ReservationPartnerServices.AirlineReservationService.webservice-endpoint.*  > C:/amina.txt";
		executer(cmd2);*/
/*		Map <Object ,String > m = null;
		AppserverConnectionSource connection = new AppserverConnectionSource("localhost", 8686, "admin", "adminadmin", null);
		System.out.println(connection.getDomainRoot().getWebServiceMgr());
		m=connection.getDomainRoot().getWebServiceMgr().getWebServiceEndpointKeys();
		if ( m == null) {
            System.out.println("No web services found ");
         }
         
         System.out.println("Number of web services " + m.keySet().size());
         System.out.println("Fully qualified names...");
         for(final Object  fqn : m.keySet()){
        	 System.out.println("Info for web service " + fqn);
        	 final WebServiceEndpointInfo info =
        		 connection.getDomainRoot().getWebServiceMgr().getWebServiceEndpointInfo(fqn);
        	 System.out.println("Name "+info.getName());
        	 System.out.println("URL "+info.getServiceURL());
        	 System.out.println("wsdl "+info.getWSDLFile());
         }
         final Set <WebServiceEndpointMonitor> ms  = connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( XTypes.WEBSERVICE_ENDPOINT_MONITOR);
	
         for( final WebServiceEndpointMonitor m1 : ms )
         {
              System.out.println("\n \n Name of web service is " + m1.getName());

              final WebServiceEndpointAggregateStats s =
                      m1.getWebServiceEndpointAggregateStats();

              // verify that we can get each Statistic;
  // there was a problem at one time


              final CountStatistic  r1 = s.getTotalFaults();
              assert( r1 != null );
              System.out.println(" total num fault is "+r1.getDescription());
              System.out.println(" "+ r1.getCount()+" "+r1.getUnit());

              final CountStatistic  r2 = s.getTotalNumSuccess() ;
              assert( r2 != null );
              System.out.println(" total num success is "+ r2.getDescription());
              System.out.println(" "+ r2.getCount()+" "+r2.getUnit());
             

              final CountStatistic  r3 = s.getAverageResponseTime();
              assert( r3 != null );
              System.out.println("avg resp is "+ r3.getDescription());
              System.out.println(" "+ r3.getCount()+" "+r3.getUnit());
              
              final CountStatistic r4 = s.getMaxResponseTime();
              assert(r4 != null);
              System.out.println("MaxResponseTime "+ r4.getDescription());
              System.out.println(" "+ r4.getCount()+" "+r4.getUnit());
              
              final CountStatistic r5 =s.getMinResponseTime();
              assert(r5 != null);
              System.out.println("MinResponseTime "+ r5.getDescription());
              System.out.println(" "+ r5.getCount()+" "+r5.getUnit());

              final NumberStatistic c1 = s.getThroughput() ;
              assert( c1 != null );
              System.out.println(" through put is "+ c1.getDescription());
              System.out.println(" "+ c1.getCurrent()+" "+c1.getUnit());

              final CountStatistic c2 = s.getTotalAuthFailures();
              assert( c2 != null );
              System.out.println(" total num auth success is "+ c2.getDescription());
              System.out.println(" "+ c2.getCount()+" "+c2.getUnit());

              final CountStatistic  c3 = s.getTotalAuthSuccesses();
              assert( c3 != null );
              System.out.println(" total num auth failure is "+ c3.getDescription());
              System.out.println(" "+ c3.getCount()+" "+c3.getUnit());

         }     
         
         
	}*/

}
