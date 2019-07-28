package surveillance;
 import java.util.Collection ;
 import java.util.Set ;
 import java.util.HashSet ;
 import java.util.SortedSet ;
 import java.util.TreeSet ;
 import java.util.Arrays ;
 import java.util.Collections ;

 import javax.management.ObjectName ;

 import com.sun.appserv.management.DomainRoot;
 import com.sun.appserv.management.base.Util;
 import com.sun.appserv.management.base.AMX;

 import com.sun.appserv.management.client.ConnectionSource;
 import com.sun.appserv.management.client.ProxyFactory;

 import com.sun.appserv.management.util.misc.StringUtil;
 import com.sun.appserv.management.util.misc.GSetUtil;
 import com.sun.appserv.management.util.misc.ListUtil;
 import com.sun.appserv.management.util.misc.ClassUtil;
 import com.sun.appserv.management.util.misc.ExceptionUtil;
import com.sun.appserv.management.util.misc.TypeCast;

 import com.sun.appserv.management.util.jmx.ObjectNameComparator;

 import com.sun.enterprise.management.support.AMXDebugStuff;

 /**
54     Observes various things as tests are run.
55  */
 public final class TestUtil
 {
     private final DomainRoot mDomainRoot;
     private final String  NEWLINE;
     
         public
     TestUtil( final DomainRoot domainRoot )
     {
         mDomainRoot = domainRoot;
         NEWLINE = System.getProperty( "line.separator" );
     }
     
         private void
     trace( final Object  o)
     {
         System.out.println( "" + o );
     }
     
         public AMXDebugStuff
     asAMXDebugStuff( final AMX amx )
     {
         final String [] attrNames = Util.getExtra( amx ).getAttributeNames();
         
         AMXDebugStuff result = null;
         if ( GSetUtil.newUnmodifiableStringSet( attrNames ).contains( "AMXDebug" ) )
         {
             final ProxyFactory factory = Util.getExtra( amx ).getProxyFactory();
                 
             try
             {
                 final Class  amxClass =
                     ClassUtil.getClassFromName( Util.getExtra( amx ).getInterfaceName() );
                 final Class [] interfaces = new Class [] { amxClass, AMXDebugStuff.class };
                 
                 final ObjectName  objectName = Util.getObjectName( amx );
                 
                 return (AMXDebugStuff)
                     factory.newProxyInstance( objectName, interfaces );
             }
             catch( Exception  e )
             {
                 trace( ExceptionUtil.toString( e ) );
                 throw new RuntimeException ( e );
             }
         }
         
         return result;
     }
     
     
     /**
107         @return Set of j2eeTypes found in Set<AMX>
108      */
         public Set <String >
     getJ2EETypes( final Set <AMX> amxs )
     {
         final Set <String > registered = new HashSet <String >();
         
         for( final AMX amx : amxs )
         {
             registered.add( amx.getJ2EEType() );
         }
         
         return registered;
     }
     
     /**
123         @return Set of j2eeTypes for which no MBeans exist
124      */
         public Set <String >
     findRegisteredJ2EETypes()
     {
         return getJ2EETypes( mDomainRoot.getQueryMgr().queryAllSet() );
     }
     
         public String 
     setToSortedString( final Set <String > s, final String  delim )
     {
         final String [] a = GSetUtil.toStringArray( s );
         Arrays.sort( a );
        
         return StringUtil.toString( NEWLINE, (Object [])a );
     }
     
     
         public static SortedSet <ObjectName >
     newSortedSet( final ObjectName [] objectNames )
     {
         final SortedSet <ObjectName > s = new TreeSet <ObjectName >( ObjectNameComparator.INSTANCE );
         
         for( final ObjectName  objectName : objectNames )
         {
             s.add( objectName );
         }
         
         return s;
     }
     
         public static SortedSet <ObjectName >
     newSortedSet( final Collection <ObjectName > c )
     {
         final ObjectName [] objectNames = new ObjectName [ c.size() ];
         c.toArray( objectNames );
         
         return newSortedSet( objectNames );
     }

     /**
164         As an optimization to speed up testing, we always get the Set of AMX
165         ObjectNames using Observer, which maintains such a list.
166      */
         public SortedSet <ObjectName >
     getAllObjectNames()
     {
         final Set <ObjectName > s =
            Observer.getInstance().getCurrentlyRegisteredAMX();
         
         return newSortedSet( s );
     }


     /**
178         @return all AMX, sorted by ObjectName
179      */
         public SortedSet <AMX>
     getAllAMX()
     {
         final SortedSet <ObjectName > all = getAllObjectNames();
         
         final SortedSet <AMX> allAMX = new TreeSet <AMX>( new AMXComparator<AMX>() );
         final ProxyFactory proxyFactory = Util.getExtra( mDomainRoot ).getProxyFactory();
         for( final ObjectName  objectName : all )
         {
             try
             {
                 final AMX amx = proxyFactory.getProxy( objectName, AMX.class );
                 
                 allAMX.add( amx );
             }
             catch( Exception  e )
             {
                 trace( ExceptionUtil.toString( e ) );
             }
         }
         
         return allAMX;
     }

         public <T> SortedSet <T>
     getAllAMX( final Class <T> theInterface )
     {
         final SortedSet <AMX> all = getAllAMX();
         final TreeSet <AMX> allOfInterface = new TreeSet <AMX>( new AMXComparator<AMX>() );
         
         for( final AMX amx : all )
         {
             if ( theInterface.isAssignableFrom( amx.getClass() ) )
             {
                 allOfInterface.add( amx );
             }
         }
         
         return TypeCast.asSortedSet( allOfInterface );
         
     }
     
         public ObjectName []
     getAllAMXArray()
     {
         final SortedSet <ObjectName > s = getAllObjectNames();
         final ObjectName [] objectNames = new ObjectName [ s.size() ];
         s.toArray( objectNames );
         
         return( objectNames );
     }

         public Set <String >
     getAvailJ2EETypes()
     {
         final SortedSet <ObjectName > allObjectNames = getAllObjectNames();
         final Set <String > j2eeTypes = new HashSet <String >();
         
         for( final ObjectName  objectName : allObjectNames )
         {
             final String  value = Util.getJ2EEType( objectName );
                 
             j2eeTypes.add( value );
         }
         return( j2eeTypes );
     }
     
 }