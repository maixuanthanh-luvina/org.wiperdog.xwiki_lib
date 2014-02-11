
def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def mongo = Args.getClientPath()

def subargs = Args.parseArgs2Array(args, false)

// def userInfo = "{ user: '" + User.username + "', password: '" + User.password + "', roles: ['userAdminAnyDatabase'] }"
def userInfo = "'" + User.username + "', '" + User.password + "'"
def eval = "\"db.getSisterDB('admin').addUser(" + userInfo + ");\""

def admincmd = [mongo, "--eval", eval]
admincmd.addAll(subargs)

println admincmd

def proc = admincmd.execute()
proc.waitFor()

println proc.text

