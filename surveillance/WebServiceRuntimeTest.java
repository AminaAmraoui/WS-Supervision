package surveillance;
 import java.util.Set ;
 import java.util.Iterator ;
 import java.io.IOException ;
 import java.util.Map ;
 import java.util.HashMap ;
 import java.io.Serializable ;


 import javax.management.ObjectName ;
 import javax.management.AttributeList ;
 import javax.management.MBeanServerConnection ;
 import javax.management.NotCompliantMBeanException ;


 import com.sun.appserv.management.j2ee.J2EEDomain;


 import com.sun.appserv.management.util.jmx.MBeanServerConnectionConnectionSource;

 import com.sun.enterprise.management.support.AMXNonConfigImplBase;
 import com.sun.enterprise.management.support.QueryMgrImpl;
 import com.sun.appserv.management.ext.wsmgmt.WebServiceMgr;
 import com.sun.appserv.management.util.misc.ExceptionUtil;
 import com.sun.appserv.management.util.misc.TypeCast;

 import com.sun.appserv.management.j2ee.WebServiceEndpoint;
import com.sun.appserv.management.client.AppserverConnectionSource;
 import com.sun.appserv.management.config.WebServiceEndpointConfig;
 import com.sun.appserv.management.ext.wsmgmt.MessageTrace;
 import com.sun.appserv.management.ext.wsmgmt.MessageTraceImpl;
 import com.sun.appserv.management.base.XTypes;
 import com.sun.appserv.management.j2ee.J2EETypes;
import com.sun.appserv.management.base.Util;

 

 /**
61  */
 public final class WebServiceRuntimeTest extends AMXTestBase
 {
	 AppserverConnectionSource connection = new AppserverConnectionSource("localhost", 8686, "admin", "adminadmin", null);
     public WebServiceRuntimeTest() throws IOException  {
     }
     
         public static Capabilities
     getCapabilities()
     {
         return getOfflineCapableCapabilities( false );
     }
     
     public void testRuntimeMBeans() throws IOException {
         assert (connection.getDomainRoot().getWebServiceMgr() != null);

        final Set <WebServiceEndpoint> s =
        	connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet( J2EETypes.WEB_SERVICE_ENDPOINT);

        for( final WebServiceEndpoint wsp : s )
        {
           wsp.resetStats();

           long ts = wsp.getLastResetTime();
           System.out.println("Web Service endpoint name is " + wsp.getName());
           System.out.println("Last reset time is " + ts);

           try {
               Thread.currentThread().sleep(1);
           } catch (Exception  e) {
           }

           wsp.resetStats();
           long ts2 = wsp.getLastResetTime();
           System.out.println("Last reset time is " + ts2);
           if (( ts == 0 ) && ( ts2 == 0)) {
             assert(true);
           } else {
               assert( ts != ts2);
           }
       }
     }

     public void testMessageTrace() throws IOException {
         assert (connection.getDomainRoot().getWebServiceMgr() != null);

        final Set <WebServiceEndpoint> s =
        	connection.getDomainRoot().getQueryMgr().queryJ2EETypeSet(J2EETypes.WEB_SERVICE_ENDPOINT);

        for( final WebServiceEndpoint wsp : s )
        {
           final MessageTrace[] msgs = wsp.getMessagesInHistory();
             if ( msgs == null)
             {
                 System.out.println(" No messages found");
                continue;
             }
             
             System.out.println(" Collected messages " + msgs.length);
             for ( int idx =0; idx < msgs.length; idx++)
             {
                 final MessageTrace msg = msgs[idx];

                 System.out.println(" message id " + msg.getMessageID());
                 System.out.println(" application id " + msg.getApplicationID());
                 System.out.println(" endpoint name " + msg.getEndpointName());
                 System.out.println(" response size " + msg.getResponseSize());
                 System.out.println(" request size " + msg.getRequestSize());
                 System.out.println(" transport type is " +
                     msg.getTransportType());
                 System.out.println(" request headers are " +
                     msg.getHTTPRequestHeaders() );
                 System.out.println(" response headers are " +
                     msg.getHTTPResponseHeaders() );
                 System.out.println(" fault code is " + msg.getFaultCode());
                 System.out.println(" fault string is " + msg.getFaultString());
                 System.out.println(" fault actor is " + msg.getFaultActor());
                 System.out.println(" client host is " + msg.getClientHost());
                 System.out.println(" principal name is " +
                     msg.getPrincipalName());
                 System.out.println(" request content is " +
                     msg.getRequestContent());
                 System.out.println(" response content is " +
                     msg.getResponseContent());
                 System.out.println(" call flow enabled " +
                    msg.isCallFlowEnabled());
             }
        }
     }
     
     public static void main(String[] args) throws Throwable{
    	 WebServiceRuntimeTest w = new WebServiceRuntimeTest();
    	 w.testRuntimeMBeans();
    	 w.testMessageTrace();
    	
     }
 }