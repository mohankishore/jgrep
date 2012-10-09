package com.github.mohankishore.jgrep;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.*;

public class Sample implements java.io.Serializable {
	private static java.io.OutputStream OUT;
	@Deprecated
	@javax.annotation.Resource(type=java.io.FileWriter.class, name="anno-name")
	private java.io.Writer WRITER;
	
	private Comparable comp;
	protected List<String> list = new ArrayList();
	public char c = File.separatorChar;
	java.lang.management.MemoryType memoryType = java.lang.management.MemoryType.HEAP;
	private Runnable[][] array = new Thread[0][0];
	
	static {
		try {
			OUT = new java.io.FileOutputStream("");
			if (OUT.hashCode() < 0) throw new IOException();
			Object.class.getMethod("toString", null).invoke(OUT, null);
			OUT.write(0);
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			//
		}
	}
	
	// instance block
	{
		try {
			WRITER = new java.io.FileWriter("");
			WRITER.write(0);
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public Sample() throws java.io.IOException {
		
	}
    public Sample(int i) throws java.io.IOException {
		InputStream is = new java.io.FileInputStream("");
		is.read();
		is.equals(new java.awt.BasicStroke());
	}
	
	@Deprecated
	public static java.io.FileFilter staticMethod(java.io.DataInput input) throws java.io.IOException {
		input.notify(); // from Object
		input.readBoolean(); // from DataInput
		return new java.io.FileFilter() {
			public boolean accept(File pathname) {
				return false;
			}
		};
	}
	
	public java.io.FilenameFilter instanceMethod() {
		return new java.io.FilenameFilter() {
			public boolean accept(File dir, String name) {
				return false;
			}
		};
	}
	
	private static class StaticInnerClass extends java.io.BufferedReader {
		public StaticInnerClass(Reader in) {
			super(in);
		}
	}
	
	private class InstanceInnerClass implements java.io.Flushable {
		public void flush() throws IOException {
			// no-op
		}
	}

}
