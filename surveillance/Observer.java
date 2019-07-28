package surveillance;
 import java.util.Set ;

 import javax.management.ObjectName ;


 import com.sun.appserv.management.DomainRoot;
 import com.sun.appserv.management.base.Util;
import com.sun.appserv.management.client.ConnectionSource;


 /**
35     Observes various things as tests are run.
36  */
 public final class Observer
 {
     private static Observer INSTANCE = null;
     
     private final RegistrationListener mListener;
     
     private final DomainRoot mDomainRoot;
     
         private
     Observer( final DomainRoot domainRoot )
     {
         mDomainRoot = domainRoot;
         
         final ConnectionSource connSource =
             Util.getExtra(domainRoot).getConnectionSource();
        
         try
         {
             mListener = new RegistrationListener(
                 connSource.getExistingMBeanServerConnection() );
         }
         catch ( Exception  e )
         {
             throw new RuntimeException ( e );
         }
     }
     
         public static synchronized Observer
     create( final DomainRoot domainRoot )
     {
         if ( INSTANCE == null )
         {
             INSTANCE = new Observer( domainRoot );
         }
         else
         {
             throw new IllegalArgumentException ();
         }
         return INSTANCE;
     }
     
         public static Observer
     getInstance()
    {
         return INSTANCE;
     }
     
         public RegistrationListener
     getRegistrationListener()
     {
         return mListener;
     }
     
         public Set <ObjectName >
     getCurrentlyRegisteredAMX()
     {
         return mListener.getCurrentlyRegistered();
     }
     
         public void
     notifsLost()
     {
         mListener.notifsLost();
     }
     
 }