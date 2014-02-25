/**
 * Utility class for unit test.
 * pase command line arguments for execute mongodb server.
 * serverargs("[--auth] [--port port_number]")
 * clientargs("[--auth] [--port port_number]")
 * 
 */
public class Args {

	/**
	 * determine the path to the mongo server.
	 * @return
	 */
	public static String getServerPath(remote) {
		def mongod = '/usr/bin/mongod'
		if (remote) {
			if (System.getProperty("mongodb.path") != null) {
				mongod = System.getProperty("mongodb.path") + "/bin/mongod"
			}
		}
		return mongod
	}

	/**
	 * determine the path to the mongo client.
	 * @return
	 */
	public static String getClientPath(remote) {
		def mongo = '/usr/bin/mongo'
		if (remote) {
			if (System.getProperty("mongodb.path") != null) {
				mongo = System.getProperty("mongodb.path") + "/bin/mongo"
			}
		}
		return mongo
	}

	/**
	 * parse command line argument for this test; especially for mongo server.
	 * @param args
	 * @return
	 */
	public static String serverargs(String [] args) {
		return parseArgs(args, true)
	}

	/**
	 * parse command line argument for this test; especially for mongo client.
	 * @param args
	 * @return
	 */
	public static String clientargs(String [] args) {
		return parseArgs(args, false)
	}

	/**
	 * parse command line argument, returns array of String.
	 * @param args
	 * @param isServer
	 * @return
	 */
	private static String [] parseArgs2Array(String [] args, boolean isServer) {
		def port = []
		def auth = []

		def stat = "ARGS"
		args.each { arg ->
			if (stat == "ARGS") {
				if (arg == "--port") {
					stat = "PORT"
				} else if (arg == "--auth") {
					if (isServer) {
						auth = [ "--auth" ]
					} else {
						auth = [ "--username", User.username, "--password", User.password, "admin" ]
					}
				}
			} else if (stat == "PORT") {
				port = [ "--port", arg ]
				stat = "ARGS"
			}
		}
		if (isServer && "".equals(auth)) {
			auth = [ "--noauth" ]
		}
		
		port.addAll(auth)

		String [] result = new String [port.size()]; 
		port.toArray(result)
		return result
	}

	/**
	 * parse command line argument returns a String.	
	 * @param args
	 * @param isServer
	 * @return
	 */
	private static String parseArgs(String [] args, boolean isServer) {
		
		def rarray = parseArgs2Array(args, isServer)
		return rarray.join(" ")
		
	}
	
	/**
	 * run command with ProcessBuider
	 * @param listCmd list command
	 * @param dir directory of project
	 * @param waitFor 
	 * @return
	 */
	public static String runProcClosure(listCmd,dir,waitFor){
		def output = [:]
		ProcessBuilder builder = new ProcessBuilder(listCmd);
		builder.redirectErrorStream(true);
		builder.directory(dir);
		Process p = builder.start();
		if(waitFor){
			output['exitVal'] = p.waitFor()
		}
		InputStream procOut  = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(procOut))
		def line = null
		StringBuffer stdin = new StringBuffer()
		while((line = br.readLine()) != null){
			stdin.append(line + "\n")
		}
		output["message"] = stdin.toString()
		return output["message"]
	}
}

