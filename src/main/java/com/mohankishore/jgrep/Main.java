package com.mohankishore.jgrep;

import java.io.File;


public class Main {

    /**
     * Usage:
     *    java -cp <classpath> com.mohankishore.jgrep.Main [-e] <pattern> {pattern}*
     * Where,
     *    classpath : standard java classpath 
     *                e.g. "./lib/*:./classes" 
     *    pattern   : <package>/<class>.<member>(<param-types>)<return-type>
     *                e.g. "com/mohankishore/grep/Main.main([Ljava/lang/String;)V"
     *                e.g. "com/mohankishore/"
     *    -e        : By default, the pattern match is a simple str.startsWith(p)
     *                When -e is specified, the strings are interpreted as regex
     *                expressions and used as Pattern.compile(p).matcher(str).matches()            
     *  
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
        JGrep jgrep = new JGrep();
	    String classpath = System.getProperty("java.class.path");
	    for (String path : classpath.split(File.pathSeparator)) {
	        jgrep.addClassPath(path);
	    }
		jgrep.addPattern("java/io/");
		jgrep.execute();
	}

}
