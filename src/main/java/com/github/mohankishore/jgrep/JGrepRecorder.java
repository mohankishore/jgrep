package com.github.mohankishore.jgrep;

public interface JGrepRecorder {

	public void record(String srcName, String srcMember, String srcDesc, String relation, String dstName, String dstMember, String dstDesc);
	
}
