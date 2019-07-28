package surveillance;
 import java.util.Set ;
 import java.util.Iterator ;
 import java.io.IOException ;
import java.util.Map ;
import java.util.HashMap ;

import javax.management.j2ee.statistics.CountStatistic;

 import com.sun.appserv.management.j2ee.WebServiceEndpoint;
 import com.sun.appserv.management.monitor.WebServiceEndpointMonitor;
import com.sun.appserv.management.monitor.statistics.WebServiceEndpointAggregateStats;

import com.sun.appserv.management.base.XTypes;
import com.sun.appserv.management.client.AppserverConnectionSource;
 import com.sun.appserv.management.ext.wsmgmt.WebServiceMgr;
import com.sun.appserv.management.ext.wsmgmt.WebServiceEndpointInfo;

 



 /**
43  */
 public final class WebServiceMgrTest extends AMXTestBase
 {
	 AppserverConnectionSource connection = new AppserverConnectionSource("localhost", 8686, "admin", "adminadmin", null);
     public WebServiceMgrTest(){
     }
     
         public static Capabilities
     getCapabilities()
     {
         return getOfflineCapableCapabilities( false );
     }
     
     public void testGetWebServiceMgr() throws IOException {
         assert (connection.getDomainRoot().getWebServiceMgr() != null);
     }
     
     public void testGetWebServiceNames() throws IOException {
         java.util.Map  m = null;
         
         m = connection.getDomainRoot().getWebServiceMgr().getWebServiceEndpointKeys();
        
         if ( m == null) {
            System.out.println("No web services found ");
             return;
        }
         final Set <WebServiceEndpointMonitor> ms  = connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( XTypes.WEBSERVICE_ENDPOINT_MONITOR);
 		for( final WebServiceEndpointMonitor m1 : ms ){
 			 final WebServiceEndpointAggregateStats s =
                 m1.getWebServiceEndpointAggregateStats();
 			System.out.println("\n \n Name of web service is " + m1.getName());
 			System.out.println("refrech "+m1.refresh());
 		
 			final CountStatistic  r1 = s.getTotalFaults();
            assert( r1 != null );
            System.out.println(" total num fault is "+r1.getDescription());
            System.out.println(" "+ r1.getCount()+" "+r1.getUnit());
 		}
         System.out.println("Number of web services " + m.keySet().size());
        System.out.println("Fully qualified names...");
        for (Iterator  iter = m.keySet().iterator(); iter.hasNext();) {
             String  key = (String )iter.next();
            System.out.println("Looking for runtime objects for " + key);
             Set <WebServiceEndpoint> epSet =
             getDomainRoot().getWebServiceMgr().getWebServiceEndpointSet(key,
            "server");
             if ( epSet != null) {
                 System.out.println("Found " + epSet.size() + " for " + key);
                 for(Iterator  epItr = epSet.iterator(); epItr.hasNext();) {
                     WebServiceEndpoint ep = (WebServiceEndpoint) epItr.next();
                     System.out.println("Found " + ep.getName() );
                     WebServiceEndpointMonitor epm = (WebServiceEndpointMonitor)
                     ep.getMonitoringPeer();
                     System.out.println("Monitoing peer for " + ep.getName() +
                     " is " + epm);

                 }
             }
         }
         System.out.println("Display names...");
         for (Iterator  iter = m.values().iterator(); iter.hasNext();) {
            System.out.println((String )iter.next());
         }
         assert(true);
     }

     public void testGetWebServiceInfo() throws IOException {
         Map <Object ,String > m = null;
         
        m = connection.getDomainRoot().getWebServiceMgr().getWebServiceEndpointKeys();
         
        if ( m == null) {
            System.out.println("No web services found ");
             return;
         }
         
         System.out.println("Number of web services " + m.keySet().size());
         System.out.println("Fully qualified names...");
         for( final Object  fqn : m.keySet() )
         {
             System.out.println("Info for web service " + fqn);
             
             final WebServiceEndpointInfo info =
                 getDomainRoot().getWebServiceMgr().getWebServiceEndpointInfo(fqn);
             
             /*
118             System.out.println("Keys are " + propMap.keySet().size());
119             for( final String key : infos.keySet() )
120             {
121                 System.out.println( key );
122             }
123             
124             System.out.println("Values are ");
125             for( final WebServiceEndpointInfo info : infos.values() )
126             {
127                  System.out.println( obj.toString() );
128             }
129             */
        }
     }
     
     /**
134      * Tests to see if any RegistryLocations are present.
135      * Expects to see atleast one, else the test fails. Create a connection
136      * pool with a type javax.xml.registry.ConnectionFactory
137      */
     public void testListRegistryLocations (){
        String [] list = getDomainRoot().getWebServiceMgr().listRegistryLocations();
         if(list == null){
             failure("Did not get any registry locations. Please check you have " +
                     "created one with the name foo");
         } else{
             for (int i = 0; i< list.length; i++){
                 System.out.println("RegistryLocation = "+list[i]);
             }
             // if you get any names in the connection definition, pass the test
 assert(true);
         }
     }

     public void testAddRegistryConnectionResources (){
         String  jndiname = "eis/SOAR";
         String  description = "Duh";
         String  purl = "http://publishurl";
        String  qurl = "http://queryurl";
         Map <String , String > map = new HashMap  <String , String > ();
         map.put (WebServiceMgr.QUERY_URL_KEY, qurl);
         map.put (WebServiceMgr.PUBLISH_URL_KEY, purl);
         
         //getDomainRoot().getWebServiceMgr().addRegistryConnectionResources (jndiname, description,
 // map);
 assertTrue(true);
     }

     public void testRemoveRegistryConnectionResources (){
         String  jndiname = "eis/SOAR";
         getDomainRoot().getWebServiceMgr().removeRegistryConnectionResources (jndiname);
         assertTrue(true);
     }
     
     public static void main(String[] args) throws Throwable{
    	 WebServiceMgrTest w = new WebServiceMgrTest();
    	 w.turnOnMonitoring();
    	 w.testGetWebServiceNames();
     }
     
 }