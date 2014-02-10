
def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def mongo = Args.getClientPath()

def port = ""
if (args.length > 0 && args[0].toInteger() > 1000) {
	port = args[0]
}

// def userInfo = "{ user: '" + User.username + "', password: '" + User.password + "', roles: ['userAdminAnyDatabase'] }"
def userInfo = "'" + User.username + "', '" + User.password + "'"
def eval = "db.getSisterDB('admin').addUser(" + userInfo + ");"

def admincmd
if (port != "") 
	admincmd = [mongo, "--port", port, "--eval", eval]
else
	admincmd = [mongo, "--eval", eval]

println admincmd

def proc = admincmd.execute()
proc.waitFor()

println proc.text

