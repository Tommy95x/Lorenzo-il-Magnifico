package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import server.element.CartaTerritori;
import server.element.Partita;
import test.server.DBTest;
import test.server.ImplementsServerInterfaceTest;
import test.server.LoginTest;
import test.server.RegisterTest;
import test.server.elements.DadoTest;
import test.server.elements.GiocatoreTest;
import test.server.elements.PortafoglioTest;
import test.server.elements.TesseraScomunicaTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LoginTest.class, RegisterTest.class, DBTest.class, ImplementsServerInterfaceTest.class,
		PortafoglioTest.class, DadoTest.class, TesseraScomunicaTest.class, GiocatoreTest.class})
/*CartaTerritori.class, Partita.class */
public class StartTestServer {

}
