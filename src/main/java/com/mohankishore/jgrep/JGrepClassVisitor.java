package com.mohankishore.jgrep;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JGrepClassVisitor extends ClassVisitor {
	protected JGrepRecorder recorder;
	protected String name;
	
	public JGrepClassVisitor(JGrepRecorder recorder) {
		super(Opcodes.ASM4);
		this.recorder = recorder;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.name = name;
		recorder.record(name, null, null, "extends", superName, null, null);
		if (interfaces != null) for (String intf : interfaces) {
			recorder.record(name, null, null, "implements", intf, null, null);
		}
	}

	@Override
	public void visitSource(String source, String debug) {
		//Util.log(this, "visitSource", source, debug);
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		recorder.record(this.name, null, null, "isInside", owner, name, desc);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		recorder.record(name, null, null, "hasAnnotation", Util.s(desc), null, null);
		return new JGrepAnnotationVisitor(api, this, null, null);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		//Util.log(this, "visitAttribute", attr);
	}

	@Override
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
		recorder.record(name, null, null, "isInside", outerName, null, null);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		recorder.record(this.name, name, null, "isOfType", Util.s(desc), null, null);
		return new JGrepFieldVisitor(api, this, name);
	}

	@Override
	// name = <clinit> for static blocks (signature/exceptions = null), 
	// name = <init> for constructors
	// the property declaration is kept at class level
	// the property definition is inlined into all the constructors
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		recorder.record(this.name, name, desc, "returns", Util.getReturnType(desc), null, null);
		String[] argTypes = Util.getArgumentTypes(desc);
		for (int i=0; i < argTypes.length; i++) {
			recorder.record(this.name, name, desc, "accepts", argTypes[i], null, null);
		}
		if (exceptions != null) for (String ex : exceptions) {
			recorder.record(this.name, name, desc, "throws", ex, null, null);
		}
		return new JGrepMethodVisitor(api, this, name, desc);
	}

	@Override
	public void visitEnd() {
		//Util.log(this, "visitEnd");
	}

}
