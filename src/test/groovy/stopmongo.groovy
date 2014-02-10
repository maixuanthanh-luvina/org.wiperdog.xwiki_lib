def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def mongo = Args.getClientPath()

def eval = " --eval 'db.getSisterDB(\"admin\").shutdownServer();'"
def otherargs = Args.clientargs(args)
def shutdowncmd = mongo + eval + otherargs

println shutdowncmd

def proc = shutdowncmd.execute()
proc.waitFor()
println proc.text
