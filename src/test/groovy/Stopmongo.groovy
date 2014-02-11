def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def mongo = Args.getClientPath()

def eval = " --eval \"db.getSisterDB('admin').shutdownServer();\" "
def otherargs = Args.clientargs(args)
def shutdowncmd = mongo + eval + otherargs

println shutdowncmd

println "Shutting down mongod, sometimes 'init call() failed' message will be displayed on successful situation"
def proc = shutdowncmd.execute()
proc.waitFor()
println proc.text
Thread.sleep(2000)
