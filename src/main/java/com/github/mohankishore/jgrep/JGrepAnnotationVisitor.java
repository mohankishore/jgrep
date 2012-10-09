package com.github.mohankishore.jgrep;

import org.objectweb.asm.AnnotationVisitor;

public class JGrepAnnotationVisitor extends AnnotationVisitor {
	protected JGrepClassVisitor classVisitor;
	protected String name;
	protected String desc;

	public JGrepAnnotationVisitor(int api, JGrepClassVisitor classVisitor, String name, String desc) {
		super(api);
		this.classVisitor = classVisitor;
		this.name = name;
		this.desc = desc;
	}

	@Override
	// AnnotationVisitor: visit, type, class org.objectweb.asm.Type: Ljava/io/FileWriter;, 
    // AnnotationVisitor: visit, name, class java.lang.String: anno-name, 
	public void visit(String name, Object value) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasAnnotationValueOfType", Util.s(value), null, null);
	}

	@Override
	public void visitEnum(String name, String desc, String value) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasEnum", Util.s(desc), null, null);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String desc) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasAnnotation", Util.s(desc), null, null);
		return this;
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		//Util.log(this, "visitArray", name);
		return this;
	}

	@Override
	public void visitEnd() {
		//Util.log(this, "visitEnd");
	}
	
}
