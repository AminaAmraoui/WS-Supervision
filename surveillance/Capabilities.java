package surveillance;


import java.util.Map ;
 import java.util.HashMap ;
 import java.util.Collections ;


 /**
32  */
 public final class Capabilities
 {
     private final HashMap <String ,Object > mItems;
     
     public static final String  OFFLINE_KEY = "Offline";
     
         public
     Capabilities()
     {
         mItems = new HashMap <String ,Object >();
     }
     
         public
     Capabilities( final Object [] pairs )
     {
         this();
         for( int i = 0; i < pairs.length; i +=2 )
         {
             add( (String )pairs[ i ], pairs[ i + 1] );
         }
     }
     
         public Map <String ,Object >
     getAll()
     {
         return Collections.unmodifiableMap( mItems );
     }
     
     
     public boolean getOfflineCapable()
     {
         return "true".equals( "" + mItems.get( OFFLINE_KEY ) );
     }
     
     public void setOfflineCapable( boolean value )
     {
         add( OFFLINE_KEY, "" + value );
     }
     
         public void
     add( final String  key, final Object  value )
     {
         assert( ! mItems.containsKey( key ) );
         mItems.put( key, value );
     }
 };
