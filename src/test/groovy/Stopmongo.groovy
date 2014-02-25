class Stopmongo {
	public static void stop(args, remote){		
		def host = ''
		def scriptDir = System.getProperty("user.dir")
		
		// get mongodb path
		def mongo = Args.getClientPath(remote)
		def mongod = Args.getServerPath(remote)
		
		// get host and path to remote mongodb
		if (remote) {
			host = System.getProperty("mongodb.host")
			scriptDir = System.getProperty("mongodb.path")
			if (host == null) {
				println "Host to remote is null! Can not connect to mongodb!"
				return
			} else if (scriptDir == null) {
				println "Path to mongodb is null! Can not connect to mongodb!"
				return
			}
		}
		def dbpath = scriptDir + '/data'
		// option to shutdown service mongodb for remote
		def eval = " --eval \"db.getSisterDB('admin').shutdownServer();\""
		def otherargs = Args.clientargs(args)
		def portOption = ""
		def portValue = ""
		// setup port for mongodb
		if(args.length > 0 && args[0] == "--port"){
			portOption = args[0]
			portValue = args[1]
		}
		def shutdowncmd = ""
		List<String> listCmd = new LinkedList<String>();
		// setup command start mongo remote or local
		if (System.getProperty("os.name").contains('Win')) {
			shutdowncmd = "net stop mongodb"
		} else {
			// on Linux
			if (host != null && host != '') {
				// remote
				listCmd.add("ssh");
				listCmd.add(host);
				listCmd.add("-o");
				listCmd.add("PasswordAuthentication=no");
				listCmd.add("sudo")
				listCmd.add(mongo)
				listCmd.add(eval)
				listCmd.add(otherargs)
			} else {
				// local
				listCmd = new LinkedList<String>();
				listCmd.add(mongod)
				listCmd.add("--dbpath")
				listCmd.add(dbpath)
				if (portOption != "") {
					listCmd.add(portOption)
					listCmd.add(portValue)
				}
				listCmd.add("--shutdown")
			}
		}
		println "Shutting down mongod, somtimes 'init call() failed' message will be displayed on successful situation."
		// run command remote or local
		File dir = new File(scriptDir)
		def output = Args.runProcClosure(listCmd,dir,true)
		println output
		Thread.sleep(2000)
	}
}	
