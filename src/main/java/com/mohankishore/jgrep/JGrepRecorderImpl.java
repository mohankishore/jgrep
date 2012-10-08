package com.mohankishore.jgrep;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JGrepRecorderImpl implements JGrepRecorder {
	private static final char DELIM = ',';
	
	private boolean regexMode;
	private List<String> searchStrings; 
	private List<Pattern> searchPatterns;

	public void initialize(boolean regexMode, List<String> searchStrings) {
	    this.regexMode = regexMode;
	    this.searchStrings = searchStrings;
	    if (regexMode) {
	        this.searchPatterns = new ArrayList<Pattern>();
	        for (String s : searchStrings) {
	            searchPatterns.add(Pattern.compile(s));
	        }
	    }
	}
	
    public void record(String srcName, String srcMember, String srcDesc, String relation, String dstName,
			String dstMember, String dstDesc) {
		if (!shouldRecord(srcName, srcMember, srcDesc, relation, dstName, dstMember, dstDesc)) return;
		
        System.out.print(srcName);
        System.out.print(DELIM);
		System.out.print(s(srcMember));
        System.out.print(DELIM);
		System.out.print(s(srcDesc));
        System.out.print(DELIM);

        System.out.print(relation);
        System.out.print(DELIM);
        
        System.out.print(dstName);
        System.out.print(DELIM);
		System.out.print(s(dstMember));
        System.out.print(DELIM);
		System.out.print(s(dstDesc));
        //System.out.print(DELIM);

		System.out.println();
	}

    protected boolean shouldRecord(String srcName, String srcMember, String srcDesc, String relation, String dstName,
                String dstMember, String dstDesc) {
        if (srcName == null || dstName == null) return false;
        
        StringBuilder dst = new StringBuilder();
        dst.append(dstName);
        if (dstMember != null) dst.append('.').append(dstMember);
        if (dstDesc != null) dst.append(dstDesc);
        
        if (regexMode) {
            return regexCheck(dst.toString());
        } else {
            return prefixCheck(dst.toString());
        }
    }
    
    protected boolean regexCheck(String s) {
        for (Pattern p : searchPatterns) {
            if (p.matcher(s).matches()) return true;
        }
        return false;
    }
    
    protected boolean prefixCheck(String s) {
        for (String p : searchStrings) {
            if (s.startsWith(p)) return true;
        }
        return false;
    }
	
	protected static String s(String s) {
	    return (s != null) ?s :"";
	}
	
}
