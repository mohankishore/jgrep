package com.github.mohankishore.jgrep;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Util {

	public static void log(Object caller, Object...objects) {
		if (objects != null) {
			System.out.print(caller.getClass().getSimpleName() + ": ");
			for (Object o : objects) {
				System.out.print(o);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	public static String accessString(int op) {
		StringBuilder sb = new StringBuilder();
		if ((Opcodes.ACC_ABSTRACT & op) != 0) {
			sb.append("abstract ");
		} else if ((Opcodes.ACC_FINAL & op) != 0) {
			sb.append("final ");
		}
		
		if ((Opcodes.ACC_PRIVATE & op) != 0) {
			sb.append("private ");
		} else if ((Opcodes.ACC_PROTECTED & op) != 0) {
			sb.append("protected ");
		} else if ((Opcodes.ACC_PUBLIC & op) != 0) {
			sb.append("public ");
		} else {
			sb.append("package ");
		}
		
		if ((Opcodes.ACC_STATIC & op) != 0) {
			sb.append("static ");
		}
		if ((Opcodes.ACC_SYNCHRONIZED & op) != 0) {
			sb.append("synchronized ");
		}
		if ((Opcodes.ACC_TRANSIENT & op) != 0) {
			sb.append("transient ");
		}
		if ((Opcodes.ACC_VOLATILE & op) != 0) {
			sb.append("volative ");
		}
		return sb.toString();
	}
	
	public static String getReturnType(String desc) {
		Type returnType = Type.getReturnType(desc);
		return s(returnType.getDescriptor());
	}
	
	public static String[] getArgumentTypes(String desc) {
		List<String> ret = new ArrayList<String>();
		Type[] argTypes = Type.getArgumentTypes(desc);
		if (argTypes != null) for (Type t : argTypes) {
			ret.add(s(t.getDescriptor()));
		}
		return ret.toArray(new String[ret.size()]);
	}
	
	public static String s(String desc) {
		if (desc == null) {
			return null;
		} else if (desc.startsWith("L")) {
			return desc.substring(1, desc.length()-1);
		} else if (desc.startsWith("[")) {
			return s(Type.getType(desc).getElementType().getDescriptor());
		} else {
			return desc;
		}
	}
	
	public static String s(Object o) {
		if (o == null) {
			return null;
		} else if (o instanceof Type) {
			return s(((Type)o).getDescriptor());
		} else {
			return s(Type.getType(o.getClass()).getDescriptor());
		}
	}
}
