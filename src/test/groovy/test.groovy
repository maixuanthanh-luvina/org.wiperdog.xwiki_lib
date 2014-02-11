// testing the test utility.
import static User.*
println username

String [] testargs = [ "--port", "2800" ]

println Args.serverargs(testargs)
println Args.clientargs(testargs)

testargs = (String [] ) [ "--auth"  ]
println Args.serverargs(testargs)
println Args.clientargs(testargs)

println Args.parseArgs2Array(testargs, false)

// println Args.serverargs(args)
// println Args.clientargs(args)

String [] nullargs = [];
if (false) {
 Stopmongo.main(nullargs);
 Startmongo.main(nullargs);
 Createuserauth.main(nullargs);
 Stopmongo.main(nullargs);
}

if (false) {
 Startmongo.main(nullargs)
 Stopmongo.main(nullargs)
}

if (true) {
  Clearmongoenv.main(nullargs)
  
  Startmongo.main(nullargs)
  if (true) {
    Createuserauth.main(nullargs)
    Stopmongo.main()
  }

  if (true) {
    Startmongo.main((String[]) ["--auth"])
    Stopmongo.main((String[])["--auth"])
  }
   
  if (true) {    
    Startmongo.main(nullargs)
    Dropuserauth.main(nullargs)
  
    Stopmongo.main(nullargs);
  }
}

