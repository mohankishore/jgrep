package com.mohankishore.jgrep.trace;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;

public class JGrepTraceFieldVisitor extends FieldVisitor {

	public JGrepTraceFieldVisitor(int api) {
		super(api);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		log("visitAnnotation", desc, visible);
		return new JGrepTraceAnnotationVisitor(api);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		log("visitAttribute", attr);
	}

	@Override
	public void visitEnd() {
		log("visitEnd");
	}
	
	private static void log(Object...objects) {
		if (objects != null) {
			System.out.print("  FieldVisitor: ");
			for (Object o : objects) {
				System.out.print(o);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
}
