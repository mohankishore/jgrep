package com.github.mohankishore.jgrep;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;

public class JGrepFieldVisitor extends FieldVisitor {
	protected JGrepClassVisitor classVisitor;
	protected String name;

	public JGrepFieldVisitor(int api, JGrepClassVisitor classVisitor, String name) {
		super(api);
		this.classVisitor = classVisitor;
		this.name = name;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		classVisitor.recorder.record(classVisitor.name, this.name, null, "hasAnnotation", Util.s(desc), null, null);
		return new JGrepAnnotationVisitor(api, classVisitor, this.name, null);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		//Util.log(this, "visitAttribute", attr);
	}

	@Override
	public void visitEnd() {
		//Util.log(this, "visitEnd");
	}
	
}
