class Startmongo {
	public static void start(args, remote){
		def host = ''
		def scriptDir = System.getProperty("user.dir")
		// get mongod path
		def mongod = Args.getServerPath(remote)
		
		// get host and path to remote mongodb
		if (remote) {
			host = System.getProperty("mongodb.host")
			scriptDir = System.getProperty("mongodb.path")
			if (host == null) {
				println "Host to remote is null! Can not start mongodb!"
				return
			} else if (scriptDir == null) {
				println "Path to mongodb is null! Can not start mongodb!"
				return
			}
		} 
		def pidfilepath = scriptDir + '/run/mongod.pid'
		def logpath =  scriptDir + '/log/mongod.log'
		def dbpath = scriptDir + '/data'
		def fork = " --fork " // Enables a daemon mode for mongod that runs the proscess to the background
		def otherargs = Args.serverargs(args)
		// create file if not exist
		(new File(pidfilepath)).parentFile.exists() ? true : (new File(pidfilepath)).parentFile.mkdirs()
		(new File(logpath)).parentFile.exists() ? true : (new File(logpath)).parentFile.mkdirs()
		(new File(dbpath)).exists() ? true : (new File(dbpath)).mkdirs()
		
		pidfilepath = " --pidfilepath " + pidfilepath
		logpath = " --logpath " + logpath
		dbpath = " --dbpath " + dbpath
		def startupcmd = ""
		List<String> listCmd = new LinkedList<String>();
		// setup command start mongo remote or local
		if (System.getProperty("os.name").contains('Win')) {
			startupcmd = "net start mongodb"
		} else {
			// on Linux
			if (host != null && host != '') {
				// remote
				listCmd.add("ssh");
				listCmd.add(host);
				listCmd.add("-o");
				listCmd.add("PasswordAuthentication=no");
				listCmd.add("sudo")
				listCmd.add(mongod)
				listCmd.add(pidfilepath)
				listCmd.add(dbpath)
				listCmd.add(logpath)
				listCmd.add(fork)
				listCmd.add(otherargs)
			} else {
				// local 
				startupcmd = mongod + pidfilepath + dbpath + logpath + fork + otherargs
			}
		}
		println "Starting up mongod..."
		// run command remote or local
		if (host != null && host != '') {
			File dir = new File(scriptDir)
			def output = Args.runProcClosure(listCmd,dir,true)
			println output
		} else {
			def proc = startupcmd.execute()
			proc.waitFor()
			println proc.text
		}
		Thread.sleep(2000)
	}
}