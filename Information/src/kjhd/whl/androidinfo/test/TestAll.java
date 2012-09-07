package kjhd.whl.androidinfo.test;

import junit.framework.Test;

import junit.framework.TestSuite;
 
public class TestAll{
 
public static Test suite(){
 
TestSuite suite = new TestSuite("TestSuite Test");
 
   suite.addTestSuite(TestSample.class);
 
   return suite;
 
}
 
}
 