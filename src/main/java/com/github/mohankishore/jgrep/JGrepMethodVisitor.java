package com.github.mohankishore.jgrep;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class JGrepMethodVisitor extends MethodVisitor {
	protected JGrepClassVisitor classVisitor;
	protected String name;
	protected String desc;

	public JGrepMethodVisitor(int api, JGrepClassVisitor classVisitor, String name, String desc) {
		super(api);
		this.classVisitor = classVisitor;
		this.name = name;
		this.desc = desc;
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return new JGrepAnnotationVisitor(api, classVisitor, this.name, this.desc);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasAnnotation", Util.s(desc), null, null);
		return new JGrepAnnotationVisitor(api, classVisitor, this.name, this.desc);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasParameterAnnotation", Util.s(desc), null, null);
		return new JGrepAnnotationVisitor(api, classVisitor, this.name, this.desc);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		//Util.log(this, "visitAttribute", attr);
	}

	@Override
	public void visitCode() {
		//Util.log(this, "visitCode");
	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
		// does not add any new variables; just lists out the subset of existing variables..
		// Util.log(this, "visitFrame", type, nLocal, Arrays.asList(local), nStack, Arrays.asList(stack));
	}

	@Override
	public void visitInsn(int opcode) {
		//Util.log(this, "visitInsn", opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		//Util.log(this, "visitIntInsn", opcode, operand);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		//Util.log(this, "visitVarInsn", opcode, var);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		//Util.log(this, "visitTypeInsn", opcode, type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "accesses", owner, name, desc);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "calls", owner, name, desc);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String desc, Handle h,
			Object... bsmArgs) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "callsDynamic", h.getOwner(), h.getName(), h.getDesc());
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		//Util.log(this, "visitJumpInsn", opcode, label);
	}

	@Override
	public void visitLabel(Label label) {
		//Util.log(this, "visitLabel", label);
	}

	@Override
	public void visitLdcInsn(Object cst) {
		if (cst instanceof Handle) {
			Handle h = (Handle) cst;
			classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "callsLDC", h.getOwner(), h.getName(), h.getDesc());
		} else {
			classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "callsLDC", Util.s(cst), null, null);
		}
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		//Util.log(this, "visitIincInsn", var, increment);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt,
			Label... labels) {
		//Util.log(this, "visitTableSwitchInsn", min, max, dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		//Util.log(this, "visitLookupSwitchInsn", dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasMultiDimArrayOfType", Util.s(desc), null, null);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "catches", type, null, null);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		classVisitor.recorder.record(classVisitor.name, this.name, this.desc, "hasLocalVariableOfType", Util.s(desc), null, null);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		//Util.log(this, "visitLineNumber", line, start);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		//Util.log(this, "visitMaxs", maxStack, maxLocals);
	}

	@Override
	public void visitEnd() {
		//Util.log(this, "visitEnd");
	}

}
