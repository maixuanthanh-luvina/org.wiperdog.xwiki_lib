class Dropuserauth {
	public static void drop(args, remote) {
		def host = ''
		def scriptDir = System.getProperty("user.dir")
		def mongo = Args.getClientPath(remote)
		
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
		def subargs = Args.parseArgs2Array(args, false)
		
		// def userInfo = "{ user: '" + User.username + "', password: '" + User.password + "', roles: ['userAdminAnyDatabase'] }"
		def eval = "db.getSisterDB('admin').removeUser('" + User.username_test + "')"
		// command to drop user
		def admincmd = [mongo, "--eval", eval]
		if (remote) {
			admincmd = [mongo, "--host", host, "--eval", eval]
		}
		//admincmd.addAll(subargs)
		println admincmd
		
		def proc = admincmd.execute()
		proc.waitFor()
		
		println proc.text
	}
}
