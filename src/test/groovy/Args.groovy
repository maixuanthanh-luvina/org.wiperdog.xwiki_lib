/**
 * Utility class for unit test.
 * pase command line arguments for execute mongodb server.
 * serverargs("[--auth] [--port port_number]")
 * clientargs("[--auth] [--port port_number]")
 * 
 */
public class Args {

	public static String getServerPath() {
		def mongod = '/usr/bin/mongod'
		if (System.getProperty("mongod.path") != null) {
			mongod = System.getProperty("mongod.path")
		}
		return mongod
	}
	
	public static String getClientPath() {
		def mongo = '/usr/bin/mongo'
		if (System.getProperty("mongo.path") != null) {
			mongo = System.getProperty("mongo.path")
		}
		return mongo
	}

	public static String serverargs(String [] args) {
		return parseArgs(args, true)
	}

	public static String clientargs(String [] args) {
		return parseArgs(args, false)
	}

	private static String parseArgs(String [] args, boolean isServer) {
		def port = ""
		def auth = ""

		def stat = "ARGS"
		args.each { arg -> 
			if (stat == "ARGS") {
				if (arg == "--port") {
					stat = "PORT"
				} else if (arg == "--auth") {
					if (isServer) {
						auth = " --auth  "
					} else {
						auth = " --username " + User.username + " --password " + User.password + " admin"
					}
				}
			} else if (stat == "PORT") {
				port = " --port " + arg
				stat = "ARGS"
			}
		}
		if (isServer && "".equals(auth)) {
			auth = " --noauth "
		}
		return port + auth
	}
}

