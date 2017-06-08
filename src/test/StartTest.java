package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.server.DBTest;
import test.server.LoginTest;
import test.server.RegisterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LoginTest.class, RegisterTest.class, DBTest.class })

public class StartTest {

}
