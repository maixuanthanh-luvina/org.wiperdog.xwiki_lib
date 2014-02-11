
def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def mongod = Args.getServerPath()

def pidfilepath = scriptDir + '/run/mongod.pid'
def logpath =  scriptDir + '/log/mongod.log'
def dbpath = scriptDir + '/data'
def fork = " --fork "
def otherargs = Args.serverargs(args)

(new File(pidfilepath)).parentFile.exists() ? true : (new File(pidfilepath)).parentFile.mkdirs()
(new File(logpath)).parentFile.exists() ? true : (new File(logpath)).parentFile.mkdirs()
(new File(dbpath)).exists() ? true : (new File(dbpath)).mkdirs()

pidfilepath = " --pidfilepath " + pidfilepath
logpath = " --logpath " + logpath
dbpath = " --dbpath " + dbpath

def startupcmd = mongod + pidfilepath  + dbpath + logpath + fork + otherargs

println "Starting up mongod..."
println startupcmd

def proc = startupcmd.execute()
proc.waitFor()

println proc.text
Thread.sleep(2000)
