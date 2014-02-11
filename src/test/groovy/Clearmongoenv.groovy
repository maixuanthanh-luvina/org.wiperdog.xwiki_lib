
def scriptFile = getClass().protectionDomain.codeSource.location.path
def scriptDir = new File(scriptFile).parent

def pidfilepath = scriptDir + '/run/mongod.pid'
def logpath =  scriptDir + '/log/mongod.log'
def dbpath = scriptDir + '/data'
def deletecmd = "rm -rf "
def p = (deletecmd + dbpath).execute()

p.waitFor();
println p.text

p = (deletecmd + logpath).execute()
p.waitFor();
println p.text

p = (deletecmd + pidfilepath).execute()
p.waitFor();
println p.text
