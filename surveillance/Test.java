package surveillance;
 public interface Test {
    /**
10      * Counts the number of test cases that will be run by this test.
11      */
     public abstract int countTestCases();
     /**
14      * Runs a test and collects its result in a TestResult instance.
15      */
     public abstract void run(TestResult result);
 } 