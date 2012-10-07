package com.mohankishore.jgrep;

public class JGrepRecorderImpl implements JGrepRecorder {

	public void record(String srcName, String srcMember, String srcDesc, String relation, String dstName,
			String dstMember, String dstDesc) {
		if (dstName == null) return;
		System.out.print(srcName);
		if (srcMember != null) {
			System.out.print("#" + srcMember);
			if (srcDesc != null) {
				System.out.print(":" + srcDesc);
			}
		}
		System.out.print(" --- " + relation + " ---> " + dstName);
		if (dstMember != null) {
			System.out.print("#" + dstMember);
			if (dstDesc != null) {
				System.out.print(":" + dstDesc);
			}
		}
		System.out.println();
	}
	
}
