package org.wiperdog.lib;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wiperdog.custommongodbconnection.CMongoDBConn;

public class TestUTCommon {
	static String param_file_path = "tmp/conf_Standard.params";

	public static CMongoDBConn createNewConnectionForTest() {
		CMongoDBConn conn = new CMongoDBConn();
		try {
			conn.getConnection(param_file_path);
		} catch (Exception e) {
			fail("Cannot create connection to Mongodb:\n" + e);
		}
		return conn;
	}

	public static boolean compare2object(Object expected, Object result) {
		boolean isEquals = false;
		if (expected instanceof Map && result instanceof Map) {
			Map resultMap = (Map) result;
			Map expectedMap = (Map) expected;
			// Check set keys of 2 maps. If not equal -> return immediately
			isEquals = resultMap.keySet().equals(expectedMap.keySet());
			if (!isEquals) {
				return isEquals;
			}
			// For key sets are equal -> check value of key
			for (String key : (Set<String>) resultMap.keySet()) {
				// check primitive type or Object type
				// If object then recursive call
				// else compare
				if ((expectedMap.get(key) instanceof Map && resultMap.get(key) instanceof Map) || (expectedMap.get(key) instanceof List && resultMap.get(key) instanceof List)) {
					isEquals = compare2object(expectedMap.get(key), resultMap.get(key));
					if (!isEquals) {
						return isEquals;
					}
				} else {
					if (expectedMap.get(key) instanceof Number && resultMap.get(key) instanceof Number) {
						double expectedDblVal = new Double(expectedMap.get(key).toString());
						double resultDblVal = new Double(resultMap.get(key).toString());
						isEquals = isEquals && (expectedDblVal == resultDblVal);
					} else {
						isEquals = isEquals && expectedMap.get(key).equals(resultMap.get(key));
					}
					if (!isEquals) {
						return isEquals;
					}
				}
			}
		} else if (expected instanceof List && result instanceof List) {
			List expectedList = (List) expected;
			List resultList = (List) result;
			// If sizes are different -> false immediately
			isEquals = (expectedList.size() == resultList.size());
			if (!isEquals) {
				return isEquals;
			}

			// For sizes are equal -> Check data
			for (int i = 0; i < expectedList.size(); i++) {
				if ((expectedList.get(i) instanceof Map && resultList.get(i) instanceof Map) || (expectedList.get(i) instanceof List && resultList.get(i) instanceof List)) {
					isEquals = compare2object(expectedList.get(i), resultList.get(i));
					if (!isEquals) {
						return isEquals;
					}
				} else {
					if (!expectedList.get(i).equals(resultList.get(i))) {
						return false;
					} else {
						isEquals = isEquals && expectedList.get(i).equals(resultList.get(i));
					}
				}
			}
		}
		return isEquals;
	}

	/**
	 * Read file and convert json to Object
	 * @param filename File's path
	 * @return
	 * @throws IOException
	 */
	public static Object getObjectFromFile(String filename) throws IOException {
		Object result = null;
		if (filename != null && filename != "") {
			BufferedReader local_reader = new BufferedReader(new FileReader(filename));
			String line = null;
			String str = "";
			while ((line = local_reader.readLine()) != null) {
				str += line;
			}
			result = com.mongodb.util.JSON.parse(str);
		}
		return result;
	}
}
