package com.mohankishore.jgrep;

import org.objectweb.asm.ClassReader;

public class Main {

	public static void main(String[] args) throws Exception {
		new ClassReader("com.mohankishore.jgrep.Sample").accept(new JGrepClassVisitor(new JGrepRecorderImpl()), 0);		
	}

}
