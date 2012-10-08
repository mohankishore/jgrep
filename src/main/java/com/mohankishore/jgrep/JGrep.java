package com.mohankishore.jgrep;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;

public class JGrep {
    private List<String> classPath = new ArrayList<String>();
    private List<String> patterns = new ArrayList<String>();
    private boolean regexMode;
    
    public void clear() {
        classPath.clear();
        patterns.clear();
        regexMode = false;
    }
    
    public void addClassPath(String path) {
        classPath.add(path);
    }
    
    public void addPattern(String pattern) {
        patterns.add(pattern);
    }
    
    public void execute() {
        JGrepRecorderImpl recorder = new JGrepRecorderImpl();
        recorder.initialize(regexMode, patterns);
        for (String path : classPath) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    processClassDir(file, recorder);
                } else if (file.isFile() && file.getName().endsWith(".jar")) {
                    try {
                        processJarFile(file, recorder);
                    } catch (IOException e) {
                        System.err.println("Error processing jar-file: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }
        }
    }
    
    protected void processJarFile(File jarFile, JGrepRecorder recorder) throws IOException {
        JarFile jf = new JarFile(jarFile);
        for (Enumeration<JarEntry> en = jf.entries(); en.hasMoreElements();) {
            JarEntry je = en.nextElement();
            try {
                if (!je.isDirectory() && je.getName().endsWith(".class")) {
                    processStream(jf.getInputStream(je), recorder);
                }
            } catch (Exception e) {
                System.err.println("Error processing jar-entry: " + je.getName() + " - " + e.getMessage());
            }
        }
    }
    
    protected void processClassDir(File classDir, JGrepRecorder recorder) {
        for (File f : classDir.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".class")) {
                try {
                    InputStream in = new FileInputStream(f);
                    processStream(in, recorder);
                    in.close();
                } catch (IOException e) {
                    System.err.println("Error processing class-file: " + f.getName() + " - " + e.getMessage());
                }
            } else if (f.isDirectory()) {
                processClassDir(f, recorder);
            }
        }
    }
    
    protected void processStream(InputStream in, JGrepRecorder recorder) throws IOException {
        new ClassReader(in).accept(new JGrepClassVisitor(recorder), 0);
    }
    
    public List<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(List<String> classPath) {
        this.classPath = classPath;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }
    
    public boolean isRegexMode() {
        return regexMode;
    }
    
    public void setRegexMode(boolean regexMode) {
        this.regexMode = regexMode;
    }
    
    
}
