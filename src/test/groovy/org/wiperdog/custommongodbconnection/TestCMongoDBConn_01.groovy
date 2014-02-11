/**
 * copied from CMongoDBConn_UT_01.java
 * for mongodb without auth option
 */
package org.wiperdog.custommongodbconnection;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assume.*;
import org.wiperdog.lib.BaseCMongoDBConnTestcase;

import com.gmongo.GMongo;
import com.mongodb.DB;
import com.mongodb.MongoException;

/**
 * Testcase for method getConnection(host, port, dbname, userName, password)
 *
 * @author luvina
 *
 */
public class TestCMongoDBConn_01 {
	private boolean mongdbpresent = true;
	private final String [] args = []
	public TestCMongoDBConn_01() {
	}

	@Before
	public void startup() {
		assumeTrue(mongdbpresent);
		Startmongo.main(args)
	}
	
	@After
	public void shutdown() {
		assumeTrue(mongdbpresent);
		Stopmongo.main(args)
	}
	
	/**
	 * Check connect to localhost, port default
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 */
	@Test
	public void getConnection5params001() {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			assertTrue(ret instanceof com.gmongo.GMongo);
			assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check connect to locahost, custom dbname
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 */
	@Test
	public void getConnection5params007() {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog_test";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			assertTrue(ret instanceof com.gmongo.GMongo);
			assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Check connect to non-authentication host, custom dbname with login
	 * infomation
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 */
	@Test
	public void getConnection5params010() {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog_test";
			String userName = "test";
			String password = "test";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			assertTrue(ret instanceof com.gmongo.GMongo);
			assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connect to port 0
	 * Expected: AssertionError with message "Port is null or invalid!"
	 *
	 * @throws Exception
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params011() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 0;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Port is null or invalid!"));
			throw ae;
		}
	}

	/**
	 * Connect to port < 0
	 * Expected: AssertionError with message "Port is null or invalid!"
	 *
	 * @throws Exception
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params012() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = -1;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Port is null or invalid!"));
			throw ae;
		}
	}

	/**
	 * Connect to port > MAX_PORT
	 * Expected: Exception because port is invalid
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params013() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 99999;
		String dbname = "wiperdog";
		String userName = "";
		String password = "";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
	}

	/**
	 * Connect to host null
	 * Expected: AssertionError with message "Host is null or empty!"
	 *
	 * @throws Exception
	 *
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params014() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = null;
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Host is null or empty!"));
			throw ae;
		}
	}

	/**
	 * Connect to host empty
	 * Expected: AssertionError with message "Host is null or empty!"
	 *
	 * @throws Exception
	 *
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params015() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = "";
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Host is null or empty!"));
			throw ae;
		}
	}

	/**
	 * Connect to wrong port Still have connection but can not query
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void getConnection5params016() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27000;
		String dbname = "wiperdog";
		String userName = "";
		String password = "";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		assertTrue(ret instanceof GMongo);
		assertTrue(conn.getDb() instanceof DB);
	}

	/**
	 * Connect to port null
	 * Expected: AssertionError with message "Port is null or invalid!"
	 *
	 * @throws Exception
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params017() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			Integer port = null;
			String dbname = "wiperdog";
			String userName = "";
			String password = "";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Port is null or invalid!"));
			throw ae;
		}
	}

	/**
	 * Connect fail because dbname is null
	 * Expected: Exception because cannot get dbname
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params018() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = null;
		String userName = "";
		String password = "";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
	}

	/**
	 * Connect successfully but dbname is empty
	 * Expected: No Exception
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void getConnection5params019() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "";
		String userName = "";
		String password = "";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
	}

	/**
	 * Connect to non-authentication db but still give login info
	 * Expected: Get GMongo instance and query successful
	 *
	 * @throws Exception
	 */
	@Test
	public void getConnection5params027() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = "test";
		String password = "test";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		assertTrue(ret instanceof com.gmongo.GMongo);
		assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		((DB) conn.getDb()).getCollectionNames();
	}
}
