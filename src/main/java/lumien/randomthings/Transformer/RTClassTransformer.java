package lumien.randomthings.Transformer;

import lumien.randomthings.Handler.CoreHandler;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import static org.objectweb.asm.Opcodes.*;

import net.minecraft.block.Block;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.world.World;

public class RTClassTransformer implements IClassTransformer
{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if (name.equals("amp"))
		{
			return patchLeaveClass(basicClass, true);
		}
		else if (name.equals("net.minecraft.block.BlockLeavesBase"))
		{
			return patchLeaveClass(basicClass, false);
		}
		else if (name.equals("afn"))
		{
			return patchWorldClass(basicClass, true);
		}
		else if (name.equals("net.minecraft.world.World"))
		{
			return patchWorldClass(basicClass, false);
		}

		return basicClass;
	}

	private byte[] patchWorldClass(byte[] basicClass, boolean obfuscated)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		String methodName = obfuscated ? "v" : "isBlockIndirectlyGettingPowered";
		String worldClass = obfuscated ? "afn" : "net/minecraft/world/World";
		
		for (MethodNode mn:classNode.methods)
		{
			if (mn.name.equals(methodName))
			{
				classNode.methods.remove(mn);
				break;
			}
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(cw);

		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, "(III)Z", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(82, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKESTATIC, "lumien/randomthings/Handler/CoreHandler", "isIndirectlyPowered", "(L"+worldClass+";III)Z");
		Label l1 = new Label();
		mv.visitJumpInsn(IFEQ, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(84, l2);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IRETURN);
		mv.visitLabel(l1);
		mv.visitLineNumber(87, l1);
		mv.visitFrame(F_SAME, 0, null, 0, null);
		mv.visitInsn(ICONST_0);
		mv.visitInsn(IRETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", "L"+worldClass+";", null, l0, l3, 0);
		mv.visitLocalVariable("posX", "I", null, l0, l3, 1);
		mv.visitLocalVariable("posY", "I", null, l0, l3, 2);
		mv.visitLocalVariable("posZ", "I", null, l0, l3, 3);
		mv.visitMaxs(4, 4);
		mv.visitEnd();

		return cw.toByteArray();
	}

	private byte[] patchLeaveClass(byte[] basicClass, boolean obfuscated)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		System.out.println("Found Leave Class: " + classNode.name + ":" + obfuscated);

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		String methodName = obfuscated ? "a" : "onNeighborBlockChange";
		String worldClass = obfuscated ? "abw" : "net/minecraft/world/World";
		String leaveClass = obfuscated ? "amp" : "net/minecraft/block/BlockLeavesBase";
		String blockClass = obfuscated ? "ahu" : "net/minecraft/block/Block";

		MethodVisitor mv = writer.visitMethod(ACC_PUBLIC, methodName, "(L" + worldClass + ";IIIL" + blockClass + ";)V", null, null);
		mv.visitCode();

		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(81, l0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitVarInsn(ILOAD, 4);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitMethodInsn(INVOKESTATIC, "lumien/randomthings/Handler/CoreHandler", "handleLeaveDecay", "(L" + worldClass + ";IIIL" + blockClass + ";)V");
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(82, l1);
		mv.visitInsn(RETURN);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLocalVariable("this", "Llumien/randomthings/Transformer/RTClassTransformer;", null, l0, l2, 0);
		mv.visitLocalVariable("p_149695_1_", "L" + worldClass + ";", null, l0, l2, 1);
		mv.visitLocalVariable("p_149695_2_", "I", null, l0, l2, 2);
		mv.visitLocalVariable("p_149695_3_", "I", null, l0, l2, 3);
		mv.visitLocalVariable("p_149695_4_", "I", null, l0, l2, 4);
		mv.visitLocalVariable("p_149695_5_", "L" + blockClass + ";", null, l0, l2, 5);
		mv.visitMaxs(5, 6);
		mv.visitEnd();

		return writer.toByteArray();
	}

}
