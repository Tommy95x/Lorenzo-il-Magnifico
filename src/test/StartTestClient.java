package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import client.gui.StartClientGui;
import test.client.ConnectionRmiClientTest;

@RunWith(Suite.class)
@SuiteClasses({ConnectionRmiClientTest.class})
public class StartTestClient {

}
