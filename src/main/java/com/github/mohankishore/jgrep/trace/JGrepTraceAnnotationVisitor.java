package com.github.mohankishore.jgrep.trace;

import org.objectweb.asm.AnnotationVisitor;

public class JGrepTraceAnnotationVisitor extends AnnotationVisitor {

	public JGrepTraceAnnotationVisitor(int api) {
		super(api);
	}

	@Override
	public void visit(String name, Object value) {
		log("visit", name, value);
	}

	@Override
	public void visitEnum(String name, String desc, String value) {
		log("visitEnum", name, desc, value);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String desc) {
		log("visitAnnotation", name, desc);
		return this;
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		log("visitArray", name);
		return this;
	}

	@Override
	public void visitEnd() {
		log("visitEnd");
	}

	private static void log(Object...objects) {
		if (objects != null) {
			System.out.print("  AnnotationVisitor: ");
			for (Object o : objects) {
				System.out.print(o);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	
}
