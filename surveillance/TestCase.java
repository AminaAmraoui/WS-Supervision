package surveillance;
 import java.lang.reflect.InvocationTargetException ;
 import java.lang.reflect.Method ;
 import java.lang.reflect.Modifier ;

 /**
8  * A test case defines the fixture to run multiple tests. To define a test case<br/>
9  * <ol>
10  * <li>implement a subclass of <code>TestCase</code></li>
11  * <li>define instance variables that store the state of the fixture</li>
12  * <li>initialize the fixture state by overriding {@link #setUp()}</li>
13  * <li>clean-up after a test by overriding {@link #tearDown()}.</li>
14  * </ol>
15  * Each test runs in its own fixture so there
16  * can be no side effects among test runs.
17  * Here is an example:
18  * <pre>
19  * public class MathTest extends TestCase {
20  * protected double fValue1;
21  * protected double fValue2;
22  *
23  * protected void setUp() {
24  * fValue1= 2.0;
25  * fValue2= 3.0;
26  * }
27  * }
28  * </pre>
29  *
30  * For each test implement a method which interacts
31  * with the fixture. Verify the expected results with assertions specified
32  * by calling {@link junit.framework.Assert#assertTrue(String, boolean)} with a boolean.
33  * <pre>
34  * public void testAdd() {
35  * double result= fValue1 + fValue2;
36  * assertTrue(result == 5.0);
37  * }
38  * </pre>
39  *
40  * Once the methods are defined you can run them. The framework supports
41  * both a static type safe and more dynamic way to run a test.
42  * In the static way you override the runTest method and define the method to
43  * be invoked. A convenient way to do so is with an anonymous inner class.
44  * <pre>
45  * TestCase test= new MathTest("add") {
46  * public void runTest() {
47  * testAdd();
48  * }
49  * };
50  * test.run();
51  * </pre>
52  *
53  * The dynamic way uses reflection to implement {@link #runTest()}. It dynamically finds
54  * and invokes a method.
55  * In this case the name of the test case has to correspond to the test method
56  * to be run.
57  * <pre>
58  * TestCase test= new MathTest("testAdd");
59  * test.run();
60  * </pre>
61  *
62  * The tests to be run can be collected into a TestSuite. JUnit provides
63  * different <i>test runners</i> which can run a test suite and collect the results.
64  * A test runner either expects a static method <code>suite</code> as the entry
65  * point to get a test to run or it will extract the suite automatically.
66  * <pre>
67  * public static Test suite() {
68  * suite.addTest(new MathTest("testAdd"));
69  * suite.addTest(new MathTest("testDivideByZero"));
70  * return suite;
71  * }
72  * </pre>
73  * @see TestResult
74  * @see TestSuite
75  */
 public abstract class TestCase extends Assert implements Test {
     /**
78      * the name of the test case
79      */
     private String  fName;

     /**
83      * No-arg constructor to enable serialization. This method
84      * is not intended to be used by mere mortals without calling setName().
85      */
     public TestCase() {
         fName= null;
     }
     /**
90      * Constructs a test case with the given name.
91      */
     public TestCase(String  name) {
         fName= name;
     }
     /**
      * Counts the number of test cases executed by run(TestResult result).
97      */
     public int countTestCases() {
         return 1;
     }
     /**
102      * Creates a default TestResult object
103      *
104      * @see TestResult
105      */
     protected TestResult createResult() {
         return new TestResult();
     }
     /**
110      * A convenience method to run this test, collecting the results with a
111      * default TestResult object.
112      *
113      * @see TestResult
114      */
     public TestResult run() {
         TestResult result= createResult();
         run(result);
         return result;
     }
     /**
121      * Runs the test case and collects the results in TestResult.
122      */
     public void run(TestResult result) {
         result.run(this);
     }
     /**
7      * Runs the bare test sequence.
128      * @throws Throwable if any exception is thrown
129      */
     public void runBare() throws Throwable  {
         Throwable  exception= null;
         setUp();
         try {
             runTest();
         } catch (Throwable  running) {
             exception= running;
         }
         finally {
             try {
                 tearDown();
             } catch (Throwable  tearingDown) {
                 if (exception == null) exception= tearingDown;
             }
         }
         if (exception != null) throw exception;
     }
     /**
148      * Override to run the test and assert its state.
149      * @throws Throwable if any exception is thrown
150      */
     protected void runTest() throws Throwable  {
         assertNotNull("TestCase.fName cannot be null", fName); // Some VMs crash when calling getMethod(null,null);
 Method  runMethod= null;
         try {
             // use getMethod to get all public inherited
 // methods. getDeclaredMethods returns all
 // methods of this class but excludes the
 // inherited ones.
 runMethod= getClass().getMethod(fName, (Class [])null);
         } catch (NoSuchMethodException  e) {
             fail("Method \""+fName+"\" not found");
         }
         if (!Modifier.isPublic(runMethod.getModifiers())) {
             fail("Method \""+fName+"\" should be public");
         }

         try {
             runMethod.invoke(this);
         }
         catch (InvocationTargetException  e) {
             e.fillInStackTrace();
             throw e.getTargetException();
         }
         catch (IllegalAccessException  e) {
             e.fillInStackTrace();
             throw e;
         }
     }
     /**
180      * Sets up the fixture, for example, open a network connection.
181      * This method is called before a test is executed.
182      */
     protected void setUp() throws Exception  {
     }
     /**
186      * Tears down the fixture, for example, close a network connection.
187      * This method is called after a test is executed.
188      */
     protected void tearDown() throws Exception  {
     }
     /**
192      * Returns a string representation of the test case
193      */
     @Override 
     public String  toString() {
         return getName() + "(" + getClass().getName() + ")";
     }
     /**
199      * Gets the name of a TestCase
200      * @return the name of the TestCase
201      */
     public String  getName() {
         return fName;
     }
     /**
6      * Sets the name of a TestCase
207      * @param name the name to set
208      */
     public void setName(String  name) {
         fName= name;
     }
 }