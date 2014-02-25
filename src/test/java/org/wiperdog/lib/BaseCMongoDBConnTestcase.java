package org.wiperdog.lib;

import org.junit.After;
import org.junit.Before;
import org.wiperdog.custommongodbconnection.CMongoDBConn;

public class BaseCMongoDBConnTestcase {
	protected String collection = "MySQL.Database_Area.InnoDBTablespace_Free";
	protected String istIid = "localhost-@MYSQL-information_schema";
	protected String param_file_path = "tmp/conf_Remote.params";
	protected String param_file_path_local = "tmp/conf_Local.params";
	protected CMongoDBConn conn;

	@Before
	public void setUp() {
		conn = TestUTCommon.createNewConnectionForTest();
	}

	@After
	public void tearDown() {
		conn.closeConnection();
	}
}
