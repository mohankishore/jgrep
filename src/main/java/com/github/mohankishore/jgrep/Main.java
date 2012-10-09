package com.github.mohankishore.jgrep;

import java.io.File;


public class Main {

    /**
     * Usage:
     *    java -cp <classpath> com.github.mohankishore.jgrep.Main [-e] <pattern> {pattern}*
     * Where,
     *    classpath : standard java classpath 
     *                e.g. "./lib/*:./classes" 
     *    pattern   : <package>/<class>.<member>(<param-types>)<return-type>
     *                e.g. "com/github/mohankishore/"
     *                e.g. "com/github/mohankishore/grep/Main.main([Ljava/lang/String;)V"
     *    -e        : By default, the pattern match is a simple str.startsWith(p)
     *                When -e is specified, the strings are interpreted as regex expressions
     *                and used as Pattern.compile(p).matcher(str).matches() 
     *                e.g. "java\\/io\\/.*\\.close\\(.*"           
     *  
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) return;

        JGrep jgrep = new JGrep();
	    String classpath = System.getProperty("java.class.path");
	    for (String path : classpath.split(File.pathSeparator)) {
	        jgrep.addClassPath(path);
	    }
	    int ndx = 0;
	    if (args[0].equals("-e")) {
	        jgrep.setRegexMode(true);
	        ndx = 1;
	    }
	    for (; ndx < args.length; ndx++) {
	        jgrep.addPattern(args[ndx]);
	    }
		jgrep.execute();
	}

}
