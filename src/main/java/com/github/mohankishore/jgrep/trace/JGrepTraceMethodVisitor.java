package com.github.mohankishore.jgrep.trace;

import java.util.Arrays;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class JGrepTraceMethodVisitor extends MethodVisitor {

	public JGrepTraceMethodVisitor(int api) {
		super(api);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		log("visitAnnotationDefault");
		return new JGrepTraceAnnotationVisitor(api);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		log("visitAnnotation", desc, visible);
		return new JGrepTraceAnnotationVisitor(api);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		log("visitParameterAnnotation", parameter, desc, visible);
		return new JGrepTraceAnnotationVisitor(api);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		log("visitAttribute", attr);
	}

	@Override
	public void visitCode() {
		log("visitCode");
	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
		log("visitFrame", type, nLocal, Arrays.asList(local), nStack, Arrays.asList(stack));
	}

	@Override
	public void visitInsn(int opcode) {
		log("visitInsn", opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		log("visitIntInsn", opcode, operand);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		log("visitVarInsn", opcode, var);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		log("visitTypeInsn", opcode, type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		log("visitFieldInsn", opcode, owner, name, desc);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		log("visitMethodInsn", opcode, owner, name, desc);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String desc, Handle bsm,
			Object... bsmArgs) {
		log("visitInvokeDynamicInsn", name, desc, bsm, bsmArgs);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		log("visitJumpInsn", opcode, label);
	}

	@Override
	public void visitLabel(Label label) {
		log("visitLabel", label);
	}

	@Override
	public void visitLdcInsn(Object cst) {
		log("visitLdcInsn", cst);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		log("visitIincInsn", var, increment);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt,
			Label... labels) {
		log("visitTableSwitchInsn", min, max, dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		log("visitLookupSwitchInsn", dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		log("visitMultiANewArrayInsn", desc, dims);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		log("visitTryCatchBlock", start, end, handler, type);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		log("visitLocalVariable", name, desc, signature, start, end, index);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		log("visitLineNumber", line, start);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		log("visitMaxs", maxStack, maxLocals);
	}

	@Override
	public void visitEnd() {
		log("visitEnd");
	}

	private static void log(Object...objects) {
		if (objects != null) {
			System.out.print("  MethodVisitor: ");
			for (Object o : objects) {
				System.out.print(o);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	
}
