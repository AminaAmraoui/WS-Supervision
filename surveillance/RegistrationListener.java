package surveillance;
import java.io.IOException ;

 import java.util.Set ;
 import java.util.HashSet ;
 import java.util.Collections ;

 import javax.management.ObjectName ;
 import javax.management.MBeanServerConnection ;
 import javax.management.InstanceNotFoundException ;

 import com.sun.appserv.management.base.AMX;
 import com.sun.appserv.management.base.Util;

 import com.sun.appserv.management.util.jmx.MBeanRegistrationListener;
 import com.sun.appserv.management.util.jmx.JMXUtil;
     
 /**
42     A NotificationListener which tracks registration of MBeans.
43  */
 public class RegistrationListener extends MBeanRegistrationListener
 {
     private final MBeanServerConnection  mConn;
     
     private Set <ObjectName > mRegistered;
     private Set <ObjectName > mUnregistered;
     private Set <ObjectName > mCurrentlyRegistered;
     
         public
     RegistrationListener( final MBeanServerConnection  conn )
         throws InstanceNotFoundException , java.io.IOException 
    {
         super( conn, null );
         
         mConn = conn;
         
         mRegistered = new HashSet <ObjectName >();
         mUnregistered = new HashSet <ObjectName >();
         mCurrentlyRegistered = new HashSet <ObjectName >();
         JMXUtil.listenToMBeanServerDelegate( conn, this, null, null );
         
         queryAllAMX();
     }
     
         private void
     queryAllAMX()
     {
         try
         {
         final ObjectName  pat = Util.newObjectNamePattern( AMX.JMX_DOMAIN, "*" );
         final Set <ObjectName > all = JMXUtil.queryNames( mConn, pat, null );
         
         mCurrentlyRegistered.addAll( all );
         }
         catch( IOException  e )
         {
         }
     }

         public void
     notifsLost()
     {
         queryAllAMX();
     }
     
         private boolean
     isAMX( final ObjectName  objectName )
     {
         return objectName.getDomain().equals( AMX.JMX_DOMAIN );
     }
     
         protected synchronized void
    mbeanRegistered( final ObjectName  objectName )
     {
         if ( isAMX( objectName ) )
        {
             mRegistered.add( objectName );
             mCurrentlyRegistered.add( objectName );
         }
     }
     
         protected synchronized void
     mbeanUnregistered( final ObjectName  objectName )
     {
         if ( isAMX( objectName ) )
         {
             mUnregistered.add( objectName );
             mCurrentlyRegistered.remove( objectName );
         }
     }
     
         public Set <ObjectName >
     getRegistered()
     {
       return Collections.unmodifiableSet( mRegistered );
     }
     
         public Set <ObjectName >
     getUnregistered()
     {
         return Collections.unmodifiableSet( mUnregistered );
     }
     
         public synchronized Set <ObjectName >
     getCurrentlyRegistered()
     {
         final Set <ObjectName > all = new HashSet <ObjectName >( mCurrentlyRegistered );
         
         return all;
     }
     
}