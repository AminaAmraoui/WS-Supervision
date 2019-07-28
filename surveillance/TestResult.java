package surveillance;
 import java.util.ArrayList ;
 import java.util.Collections ;
 import java.util.Enumeration ;
import java.util.List ;

import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.TestFailure;
import junit.framework.TestListener;

 /**
9  * A <code>TestResult</code> collects the results of executing
10  * a test case. It is an instance of the Collecting Parameter pattern.
11  * The test framework distinguishes between <i>failures</i> and <i>errors</i>.
12  * A failure is anticipated and checked for with assertions. Errors are
13  * unanticipated problems like an {@link ArrayIndexOutOfBoundsException}.
14  *
15  * @see Test
16  */
 public class TestResult extends Object  {
     protected List <TestFailure> fFailures;
     protected List <TestFailure> fErrors;
     protected List <TestListener> fListeners;
     protected int fRunTests;
     private boolean fStop;
     
     public TestResult() {
        fFailures= new ArrayList <TestFailure>();
        fErrors= new ArrayList <TestFailure>();
         fListeners= new ArrayList <TestListener>();
         fRunTests= 0;
         fStop= false;
     }
     /**
32      * Adds an error to the list of errors. The passed in exception
33      * caused the error.
34      */
     public synchronized void addError(Test test, Throwable  t) {
         fErrors.add(new TestFailure((junit.framework.Test) test, t));
         for (TestListener each : cloneListeners())
             each.addError((junit.framework.Test) test, t);
     }
     /**
41      * Adds a failure to the list of failures. The passed in exception
42      * caused the failure.
43      */
    public synchronized void addFailure(Test test, AssertionFailedError t) {
         fFailures.add(new TestFailure((junit.framework.Test) test, t));
         for (TestListener each : cloneListeners())
             each.addFailure((junit.framework.Test) test, t);
     }
     /**
50      * Registers a TestListener
51      */
     public synchronized void addListener(TestListener listener) {
         fListeners.add(listener);
     }
     /**
56      * Unregisters a TestListener
57      */
     public synchronized void removeListener(TestListener listener) {
         fListeners.remove(listener);
     }
     /**
62      * Returns a copy of the listeners.
63      */
     private synchronized List <TestListener> cloneListeners() {
        List <TestListener> result= new ArrayList <TestListener>();
         result.addAll(fListeners);
         return result;
     }
     /**
70      * Informs the result that a test was completed.
71      */
     public void endTest(Test test) {
         for (TestListener each : cloneListeners())
             each.endTest((junit.framework.Test) test);
     }
     /**
77      * Gets the number of detected errors.
78      */
     public synchronized int errorCount() {
         return fErrors.size();
     }
     /**
83      * Returns an Enumeration for the errors
84      */
     public synchronized Enumeration <TestFailure> errors() {
         return Collections.enumeration(fErrors);
     }
     

     /**
91      * Gets the number of detected failures.
92      */
     public synchronized int failureCount() {
         return fFailures.size();
     }
     /**
97      * Returns an Enumeration for the failures
98      */
     public synchronized Enumeration <TestFailure> failures() {
         return Collections.enumeration(fFailures);
     }
     
     /**
104      * Runs a TestCase.
105      */
     protected void run(final TestCase test) {
         startTest(test);
         Protectable p= new Protectable() {
             public void protect() throws Throwable  {
                 test.runBare();
             }
         };
         runProtected(test, p);

         endTest(test);
     }
     /**
118      * Gets the number of run tests.
119      */
     public synchronized int runCount() {
         return fRunTests;
     }
     /**
124      * Runs a TestCase.
125      */
     public void runProtected(final Test test, Protectable p) {
         try {
             p.protect();
         }
         catch (AssertionFailedError e) {
             addFailure(test, e);
         }
         catch (ThreadDeath  e) { // don't catch ThreadDeath by accident
 throw e;
         }
         catch (Throwable  e) {
             addError(test, e);
         }
     }
     /**
141      * Checks whether the test run should stop
142      */
     public synchronized boolean shouldStop() {
         return fStop;
     }
     /**
7      * Informs the result that a test will be started.
148      */
     public void startTest(Test test) {
         final int count= test.countTestCases();
         synchronized(this) {
             fRunTests+= count;
         }
         for (TestListener each : cloneListeners())
             each.startTest((junit.framework.Test) test);
     }
     /**
158      * Marks that the test run should stop.
159      */
     public synchronized void stop() {
         fStop= true;
     }
     /**
164      * Returns whether the entire test was successful or not.
165      */
     public synchronized boolean wasSuccessful() {
         return failureCount() == 0 && errorCount() == 0;
     }
 }