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

		return basicClass;
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
