package com.github.mohankishore.jgrep.trace;

import java.util.Arrays;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JGrepTraceClassVisitor extends ClassVisitor {

	public JGrepTraceClassVisitor() {
		super(Opcodes.ASM4);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		log("visit", version, accessString(access), name, signature, superName, interfaces != null ?Arrays.asList(interfaces) :null);
	}

	@Override
	public void visitSource(String source, String debug) {
		log("visitSource", source, debug);
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		log("visitOuterClass", owner, name, desc);
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
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
		log("visitInnerClass", name, outerName, innerName, accessString(access));
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		log("visitField", accessString(access), name, desc, signature, value);
		return new JGrepTraceFieldVisitor(api);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		log("visitMethod", accessString(access), name, desc, signature, exceptions != null ?Arrays.asList(exceptions) :null);
		return new JGrepTraceMethodVisitor(api);
	}

	@Override
	public void visitEnd() {
		log("visitEnd");
	}

	private static void log(Object...objects) {
		if (objects != null) {
			System.out.print("ClassVisitor: ");
			for (Object o : objects) {
				System.out.print(o);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	private static String accessString(int access) {
		StringBuilder sb = new StringBuilder();
		if ((Opcodes.ACC_ABSTRACT & access) != 0) {
			sb.append("abstract ");
		} else if ((Opcodes.ACC_FINAL & access) != 0) {
			sb.append("final ");
		}
		
		if ((Opcodes.ACC_PRIVATE & access) != 0) {
			sb.append("private ");
		} else if ((Opcodes.ACC_PROTECTED & access) != 0) {
			sb.append("protected ");
		} else if ((Opcodes.ACC_PUBLIC & access) != 0) {
			sb.append("public ");
		} else {
			sb.append("package ");
		}
		
		if ((Opcodes.ACC_STATIC & access) != 0) {
			sb.append("static ");
		}
		if ((Opcodes.ACC_SYNCHRONIZED & access) != 0) {
			sb.append("synchronized ");
		}
		if ((Opcodes.ACC_TRANSIENT & access) != 0) {
			sb.append("transient ");
		}
		if ((Opcodes.ACC_VOLATILE & access) != 0) {
			sb.append("volative ");
		}
		return sb.toString();
	}
	
}
