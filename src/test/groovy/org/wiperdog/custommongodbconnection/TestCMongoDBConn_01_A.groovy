/**
 * copied from CMongoDBConn_UT_01.java
 * for mongodb with auth option
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
public class TestCMongoDBConn_01_A {
	private boolean mongdbpresent = true;
	private final String [] args = ["--auth"]
	static {
		String [] nullargs = []
		Startmongo.main(nullargs)
		Createuserauth.main()
		Stopmongo.main(nullargs)

	}
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
	 * Check connect to locahost, with authentication
	 * DB needs authenticate test/test
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 */
	@Test
	public void getConnection5params005() {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "admin";
			String password = "admin";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			assertTrue(ret instanceof com.gmongo.GMongo);
			assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check connect to authentication host, custom dbname with login infomation
	 * Expected: Connect successful result is Gmongo, getDb return a com.mongodb.DB instance
	 */
	@Test
	public void getConnection5params009() {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog_test";
			String userName = "admin";
			String password = "admin";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			assertTrue(ret instanceof com.gmongo.GMongo);
			assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connect to authentication db but give wrong user/password
	 * DB needs authenticate
	 * Expected: Get GMongo instance successful but Exception when query
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params020() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = "wronguser";
		String password = "test";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		assertTrue(ret instanceof GMongo);
		Set<String> lstCollections = ((DB)conn.getDb()).getCollectionNames();
	}

	/**
	 * Connect to authentication db but give null user
	 * DB needs authenticate
	 * Expected: Get GMongo instance successful but Exception when query
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params021() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = null;
		String password = "test";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		((DB)conn.getDb()).getCollectionNames();
	}

	/**
	 * Connect to authentication db but give empty user
	 * DB needs authenticate
	 * Expected: Get GMongo instance successful but Exception when query
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params022() throws Exception {
		assumeTrue(mongdbpresent);
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "";
			String password = "test";
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
			((DB)conn.getDb()).getCollectionNames();
	}

	/**
	 * Connect to authentication db but give space character user
	 * Expected: Get GMongo instance successful but Exception when query
	 *
	 * @throws Exception
	 *
	 */
	@Test(expected = Exception.class)
	public void getConnection5params023() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = " ";
		String password = "test";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		((DB)conn.getDb()).getCollectionNames();
	}

	/**
	 * Connect to authentication db but give wrong password
	 * DB needs authenticate
	 * Expected: Get GMongo instance successful but Exception when query
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void getConnection5params024() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = "test";
		String password = "wrongpassword";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		((DB) conn.getDb()).getCollectionNames();
	}

	/**
	 * Connect to authentication db but give null password
	 * DB needs authenticate
	 * Expected: AssertionError with message "Password is null or empty!"
	 * @throws Exception
	 */
	@Test(expected = AssertionError.class)
	public void getConnection5params025() throws Exception {
		assumeTrue(mongdbpresent);
		try {
			String host = "localhost";
			int port = 27017;
			String dbname = "wiperdog";
			String userName = "test";
			String password = null;
			CMongoDBConn conn = new CMongoDBConn();
			Object ret = conn.getConnection(host, port, dbname, userName, password);
		} catch (AssertionError ae) {
			assertTrue(ae.getMessage().contains("Password is null or empty!"));
			throw ae;
		}
	}

	/**
	 * Connect to authentication db but doesn't give login information
	 * DB needs authenticate
	 * Expected: Get GMongo instance successful but Exception when query
	 * @throws Exception
	 */
	@Test(expected = MongoException.class)
	public void getConnection5params026() throws Exception {
		assumeTrue(mongdbpresent);
		String host = "localhost";
		int port = 27017;
		String dbname = "wiperdog";
		String userName = "";
		String password = "";
		CMongoDBConn conn = new CMongoDBConn();
		Object ret = conn.getConnection(host, port, dbname, userName, password);
		assertTrue(ret instanceof com.gmongo.GMongo);
		assertTrue(conn.getDb() != null && conn.getDb() instanceof com.mongodb.DB);
		((DB) conn.getDb()).getCollectionNames();
	}
}
