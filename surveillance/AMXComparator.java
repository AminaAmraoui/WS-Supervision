package surveillance;
 import javax.management.ObjectName ;

 import com.sun.appserv.management.base.AMX;
 import com.sun.appserv.management.base.Util;

 import com.sun.appserv.management.util.jmx.ObjectNameComparator;


 public final class AMXComparator<T extends AMX> implements java.util.Comparator <T>
 {
     public AMXComparator() {}
     
         public int
     compare( final T lhs, final T rhs )
     {
         final ObjectName  lhsName = Util.getObjectName( lhs );
         final ObjectName  rhsName = Util.getObjectName( rhs );
         
         return ObjectNameComparator.INSTANCE.compare( lhsName, rhsName );
     }
     
         public boolean
     equals( final Object  other )
     {
         return( other instanceof AMXComparator );
     }
 }