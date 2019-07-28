package surveillance;
import java.util.Map ;
 import java.util.HashMap ;

 /**
29     Property keys used to configure the unit tests.
30     
31     @see TestMain
32     @see TestRunner
33  */
 public final class PropertyKeys
 {
     private PropertyKeys() {}
     
     
     private final static String  BASE = "amxtest";
     public static final String  DEFAULT_PROPERTIES_FILE = BASE + ".properties";

     public static final String  CONNECT_KEY = BASE + ".connect";
     public static final String  HOST_KEY = CONNECT_KEY + ".host";
     public static final String  PORT_KEY = CONNECT_KEY + ".port";
     public static final String  USER_KEY = CONNECT_KEY + ".user";
     public static final String  PASSWORD_KEY = CONNECT_KEY + ".password";
     public static final String  TRUSTSTORE_KEY = CONNECT_KEY + ".truststore";
     public static final String  TRUSTSTORE_PASSWORD_KEY = CONNECT_KEY + ".truststorePassword";
     public static final String  USE_TLS_KEY = CONNECT_KEY + ".useTLS";
     public static final String  RUN_THREADED_KEY = BASE + ".threaded";
     public static final String  VERBOSE_KEY = BASE + ".verbose";
     public static final String  ITERATIONS_KEY = BASE + ".iterations";
     
    /**
55         Whether testing is for offline config utilizing
56         com.sun.appserv.management.config.OfflineConfigIniter.
57         You must also supply a value for the {@link #DOMAIN_XML_KEY}.
58      */
     public static final String  TEST_OFFLINE_KEY = BASE + ".testOffline";
     
     /**
62         A valid file path for domain.xml.
63      */
     public static final String  DOMAIN_XML_KEY = TEST_OFFLINE_KEY + ".domainXML";
     
     
     /**
68         A boolean specifying whether expanded testing is to be used. When specified,
69         tests that involve clusters, multiple standalone servers, etc are run
70         (if possible).
71      */
     public static final String  EXPANDED_TESTING_KEY = BASE + ".expandedTesting";
     
     /**
75         Comma-separated list of node-agent names to be used during testing.
76         The special name {@link #ALL_NODE_AGENTS} may be used to specify all configured node agents.
77         <p>
78         At runtime, the environment contains a Map<String,AppserverConnectionSource> available
79         via this key, where the key is the node agent name.
80      */
     public static final String  NODE_AGENTS_KEY = BASE + ".nodeAgents";
     
     public static final String  ALL_NODE_AGENTS = "ALL";
     
     
     /**
87         Name of the node agent that the DAS uses.
88      */
     public static final String  DAS_NODE_AGENT_NAME = BASE + ".dasNodeAgent";
     
     /**
92         Comma-separated list of files.
93      */
     public static final String  ARCHIVES_TO_DEPLOY_KEY = BASE + ".deploy.files";
     /**
96         Delimiter between files contained in the value for {@link #ARCHIVES_TO_DEPLOY_KEY}.
97      */
     public static final String  ARCHIVES_DELIM = ",";
     
     /**
101         The number of threads to run for DeploymentMgrTest.testDeployHeavilyThreaded()
102      */
     public static final String  DEPLOY_NUM_THREADS = BASE + ".deploy.numThreads";
     
     
     /**
107         The number of threads to run for UploadDownloadMgrTest.testHeavilyThreaded()
108      */
     public static final String  UPLOAD_DOWNLOAD_MGR_TEST_THREADS = BASE + ".UploadDownloadMgrTest.numThreads";
     /**
111         The size, in KB, of UploadDownloadMgrTest.testDownloadBigFile()
112      */
     public static final String  UPLOAD_DOWNLOAD_MGR_TEST_BIG_FILE_KB = BASE + ".UploadDownloadMgrTest.bigFileKB";
      
      
     
     /**
118         File consisting of names of tests, one per line
119      */
     public static final String  TEST_CLASSES_FILE_KEY = BASE + ".testClasses";
    
     public static final String  DEFAULT_HOST = "localhost";
     public static final String  DEFAULT_PORT = "8686";
     public static final String  DEFAULT_USER = "admin";
     public static final String  DEFAULT_PASSWORD = "adminadmin";
     public static final String  DEFAULT_TRUSTSTORE = "~/" + BASE + ".truststore";
     public static final String  DEFAULT_TRUSTSTORE_PASSWORD = "changeme";
     public static final String  DEFAULT_USE_TLS = "true";
     public static final String  DEFAULT_RUN_THREADED = "true";
     public static final String  DEFAULT_TEST_CLASSES_FILE_KEY = BASE + ".test-classes";
     public static final String  DEFAULT_VERBOSE = "false";
     public static final String  DEFAULT_ITERATIONS = "2";
     public static final String  DEFAULT_CONNECT = "true";
     public static final String  DEFAULT_NODE_AGENT_NAMES = ALL_NODE_AGENTS;
     public static final String  DEFAULT_EXPANDED_TESTING = "false";
     public static final String  DEFAULT_TEST_OFFLINE = "false";
     
     public static final String  DEFAULT_ARCHIVES_TO_DEPLOY = "";
     public static final String  DEFAULT_DEPLOY_NUM_THREADS = "10";
     
     public static final String  DEFAULT_UPLOAD_DOWNLOAD_MGR_TEST_THREADS = "10";
     public static final String  DEFAULT_UPLOAD_DOWNLOAD_MGR_TEST_BIG_FILE_KB = "1536";
     
     
         public static Map <String ,String >
     getDefaults()
     {
         final Map <String ,String > props = new HashMap <String ,String >();
         
         props.put( HOST_KEY, DEFAULT_HOST);
         props.put( PORT_KEY, DEFAULT_PORT);
         props.put( USER_KEY, DEFAULT_USER);
         props.put( PASSWORD_KEY, DEFAULT_PASSWORD);
         props.put( TRUSTSTORE_KEY, DEFAULT_TRUSTSTORE);
         props.put( TRUSTSTORE_PASSWORD_KEY, DEFAULT_TRUSTSTORE_PASSWORD);
         props.put( USE_TLS_KEY, DEFAULT_USE_TLS);
         props.put( CONNECT_KEY, DEFAULT_CONNECT);
         props.put( TEST_OFFLINE_KEY, DEFAULT_TEST_OFFLINE);
         props.put( DOMAIN_XML_KEY, "./domain.xml" );
         
         props.put( NODE_AGENTS_KEY, ALL_NODE_AGENTS);
         props.put( EXPANDED_TESTING_KEY, DEFAULT_EXPANDED_TESTING);
         
         props.put( RUN_THREADED_KEY, DEFAULT_RUN_THREADED);
         props.put( VERBOSE_KEY, DEFAULT_VERBOSE);
         props.put( TEST_CLASSES_FILE_KEY, DEFAULT_TEST_CLASSES_FILE_KEY);
         props.put( ITERATIONS_KEY, DEFAULT_ITERATIONS );
         
         props.put( ARCHIVES_TO_DEPLOY_KEY, DEFAULT_ARCHIVES_TO_DEPLOY);
         props.put( DEPLOY_NUM_THREADS, DEFAULT_DEPLOY_NUM_THREADS);
         
         props.put( UPLOAD_DOWNLOAD_MGR_TEST_THREADS, DEFAULT_UPLOAD_DOWNLOAD_MGR_TEST_THREADS);
         props.put( UPLOAD_DOWNLOAD_MGR_TEST_BIG_FILE_KB, DEFAULT_UPLOAD_DOWNLOAD_MGR_TEST_BIG_FILE_KB);
         
         props.put( DEFAULT_PROPERTIES_FILE, DEFAULT_PROPERTIES_FILE );
         return( props );
     }
 };