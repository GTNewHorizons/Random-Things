package lumien.randomthings.Transformer;

import static org.objectweb.asm.Opcodes.*;

import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.*;

public class RTClassTransformer implements IClassTransformer {

    public static final Logger coreLogger = LogManager.getLogger("RandomThingsCore");

    @Override
    public byte[] transform(String obfName, String transformedName, byte[] basicClass) {
        if (basicClass == null || transformedName == null) {
            return basicClass;
        }
        if (transformedName.equals("net.minecraft.block.BlockLeavesBase") && VanillaChanges.FASTER_LEAVEDECAY) {
            return patchLeaveClass(basicClass);
        } else if (transformedName.equals("net.minecraft.client.renderer.EntityRenderer")) {
            return patchEntityRendererClass(basicClass);
        } else if (transformedName.equals("net.minecraft.world.World")) {
            return patchWorldClass(basicClass);
        } else if (transformedName.equals("net.minecraft.item.Item")) {
            return patchItemClass(basicClass);
        } else if (transformedName.equals("net.minecraft.client.renderer.RenderGlobal")) {
            return patchRenderGlobal(basicClass);
        }
        return basicClass;
    }

    private byte[] patchRenderGlobal(byte[] basicClass) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            coreLogger.log(Level.INFO, "Transforming : " + classNode.name);
            MethodNode renderSky = null;

            for (MethodNode mn : classNode.methods) {
                if (mn.name.equals(RTLoadingPlugin.isObf ? "a" : "renderSky") && mn.desc.equals("(F)V")) {
                    renderSky = mn;
                    break;
                }
            }

            if (renderSky != null) {
                for (int i = 0; i < renderSky.instructions.size(); i++) {
                    AbstractInsnNode ain = renderSky.instructions.get(i);
                    if (ain instanceof FieldInsnNode) {
                        FieldInsnNode fin = (FieldInsnNode) ain;
                        if (fin.name.equals(RTLoadingPlugin.isObf ? "field_110927_h" : "locationMoonPhasesPng")) {
                            renderSky.instructions.insert(
                                    renderSky.instructions.get(i + 1),
                                    new MethodInsnNode(
                                            INVOKESTATIC,
                                            "lumien/randomthings/Handler/Bloodmoon/ClientBloodmoonHandler",
                                            "moonColorHook",
                                            "()V",
                                            false));
                            i++;
                        }
                    } else if (ain instanceof MethodInsnNode) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if (min.name.equals(RTLoadingPlugin.isObf ? "a" : "getSkyColor")
                                && min.desc.equals(
                                        RTLoadingPlugin.isObf
                                                ? "(Lsa;F)Lazw;"
                                                : "(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/Vec3;")) {
                            renderSky.instructions.insert(
                                    min,
                                    new MethodInsnNode(
                                            INVOKESTATIC,
                                            "lumien/randomthings/Handler/Bloodmoon/ClientBloodmoonHandler",
                                            "skyColorHook",
                                            "(Lnet/minecraft/util/Vec3;)V",
                                            false));
                            renderSky.instructions.insert(min, new InsnNode(DUP));
                            i += 2;
                        }
                    }
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicClass;
    }

    private byte[] patchItemClass(byte[] basicClass) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            coreLogger.log(Level.INFO, "Transforming : " + classNode.name);
            MethodNode getColorFromItemStack = null;

            for (MethodNode mn : classNode.methods) {
                if (mn.name.equals(RTLoadingPlugin.isObf ? "a" : "getColorFromItemStack")
                        && mn.desc.equals(RTLoadingPlugin.isObf ? "(Ladd;I)I" : "(Lnet/minecraft/item/ItemStack;I)I")) {
                    getColorFromItemStack = mn;
                }
            }

            if (getColorFromItemStack != null) {
                LabelNode l0 = new LabelNode(new Label());
                LabelNode l1 = new LabelNode(new Label());
                LabelNode l2 = new LabelNode(new Label());

                getColorFromItemStack.instructions.insert(new InsnNode(POP));
                getColorFromItemStack.instructions.insert(l2);
                getColorFromItemStack.instructions.insert(new InsnNode(IRETURN));
                getColorFromItemStack.instructions.insert(l1);
                getColorFromItemStack.instructions.insert(new JumpInsnNode(IF_ICMPEQ, l2));
                getColorFromItemStack.instructions.insert(new LdcInsnNode(16777215));
                getColorFromItemStack.instructions.insert(new InsnNode(DUP));
                getColorFromItemStack.instructions.insert(new MethodInsnNode(
                        INVOKESTATIC,
                        "lumien/randomthings/Handler/CoreHandler",
                        "getColorFromItemStack",
                        "(Lnet/minecraft/item/ItemStack;I)I",
                        false));
                getColorFromItemStack.instructions.insert(new VarInsnNode(ILOAD, 2));
                getColorFromItemStack.instructions.insert(new VarInsnNode(ALOAD, 1));
                getColorFromItemStack.instructions.insert(l0);
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicClass;
    }

    private byte[] patchWorldClass(byte[] basicClass) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            coreLogger.log(Level.INFO, "Transforming : " + classNode.name);

            int removeIndex = 0;

            MethodNode getSunBrightness = null;
            MethodNode isBlockIndirectlyGettingPowered = null;

            for (MethodNode mn : classNode.methods) {
                if (mn.name.equals("getSunBrightnessBody")) {
                    getSunBrightness = mn;
                } else if (mn.name.equals(RTLoadingPlugin.isObf ? "v" : "isBlockIndirectlyGettingPowered")
                        && mn.desc.equals("(III)Z")) {
                    isBlockIndirectlyGettingPowered = mn;
                }
            }

            if (getSunBrightness != null && VanillaChanges.HARDCORE_DARKNESS) {
                for (int i = 0; i < getSunBrightness.instructions.size(); i++) {
                    AbstractInsnNode an = getSunBrightness.instructions.get(i);
                    if (an instanceof LdcInsnNode) {
                        LdcInsnNode lin = (LdcInsnNode) an;

                        if (lin.cst.equals(new Float("0.8"))) {
                            removeIndex = i;
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    getSunBrightness.instructions.remove(getSunBrightness.instructions.get(removeIndex));
                }
            }

            if (isBlockIndirectlyGettingPowered != null && ConfigBlocks.wirelessLever) {
                LabelNode l0 = new LabelNode(new Label());
                LabelNode l1 = new LabelNode(new Label());
                LabelNode l2 = new LabelNode(new Label());

                isBlockIndirectlyGettingPowered.instructions.insert(l2);
                isBlockIndirectlyGettingPowered.instructions.insert(new InsnNode(IRETURN));
                isBlockIndirectlyGettingPowered.instructions.insert(new InsnNode(ICONST_1));
                isBlockIndirectlyGettingPowered.instructions.insert(l1);
                isBlockIndirectlyGettingPowered.instructions.insert(new JumpInsnNode(IFEQ, l2));
                isBlockIndirectlyGettingPowered.instructions.insert(new MethodInsnNode(
                        INVOKESTATIC,
                        "lumien/randomthings/Handler/CoreHandler",
                        "isBlockIndirectlyGettingPowered",
                        "(Lnet/minecraft/world/World;III)Z",
                        false));
                isBlockIndirectlyGettingPowered.instructions.insert(new VarInsnNode(ILOAD, 3));
                isBlockIndirectlyGettingPowered.instructions.insert(new VarInsnNode(ILOAD, 2));
                isBlockIndirectlyGettingPowered.instructions.insert(new VarInsnNode(ILOAD, 1));
                isBlockIndirectlyGettingPowered.instructions.insert(new VarInsnNode(ALOAD, 0));
                isBlockIndirectlyGettingPowered.instructions.insert(l0);
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicClass;
    }

    private byte[] patchEntityRendererClass(byte[] basicClass) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            coreLogger.log(Level.INFO, "Transforming : " + classNode.name);

            int removeIndex = 0;

            MethodNode updateLightmap = null;

            for (MethodNode mn : classNode.methods) {
                if (mn.name.equals(RTLoadingPlugin.isObf ? "i" : "updateLightmap") && mn.desc.equals("(F)V")) {
                    updateLightmap = mn;
                }
            }

            if (updateLightmap != null) {
                boolean insertedHook = false;
                for (int i = 0; i < updateLightmap.instructions.size(); i++) {
                    AbstractInsnNode an = updateLightmap.instructions.get(i);
                    if (an instanceof VarInsnNode && !insertedHook) {
                        VarInsnNode iin = (VarInsnNode) an;
                        if (iin.getOpcode() == ISTORE && iin.var == 21) {
                            updateLightmap.instructions.insert(iin, new VarInsnNode(ISTORE, 21));
                            updateLightmap.instructions.insert(
                                    iin,
                                    new MethodInsnNode(
                                            INVOKESTATIC,
                                            "lumien/randomthings/Handler/LightmapHandler",
                                            "manipulateBlue",
                                            "(I)I",
                                            false));
                            updateLightmap.instructions.insert(iin, new VarInsnNode(ILOAD, 21));

                            updateLightmap.instructions.insert(iin, new VarInsnNode(ISTORE, 20));
                            updateLightmap.instructions.insert(
                                    iin,
                                    new MethodInsnNode(
                                            INVOKESTATIC,
                                            "lumien/randomthings/Handler/LightmapHandler",
                                            "manipulateGreen",
                                            "(I)I",
                                            false));
                            updateLightmap.instructions.insert(iin, new VarInsnNode(ILOAD, 20));

                            updateLightmap.instructions.insert(iin, new VarInsnNode(ISTORE, 19));
                            updateLightmap.instructions.insert(
                                    iin,
                                    new MethodInsnNode(
                                            INVOKESTATIC,
                                            "lumien/randomthings/Handler/LightmapHandler",
                                            "manipulateRed",
                                            "(I)I",
                                            false));
                            updateLightmap.instructions.insert(iin, new VarInsnNode(ILOAD, 19));

                            insertedHook = true;
                        }
                    } else if (VanillaChanges.HARDCORE_DARKNESS && an instanceof LdcInsnNode) {
                        LdcInsnNode lin = (LdcInsnNode) an;

                        if (lin.cst.equals(new Float("0.95"))) {
                            removeIndex = i;
                        }
                    }
                }
                if (VanillaChanges.HARDCORE_DARKNESS) {
                    for (int i = 0; i < 4; i++) {
                        updateLightmap.instructions.remove(updateLightmap.instructions.get(removeIndex));
                    }
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicClass;
    }

    private byte[] patchLeaveClass(byte[] basicClass) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            coreLogger.log(Level.INFO, "Transforming : " + classNode.name);

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);

            String methodName = RTLoadingPlugin.isObf ? "a" : "onNeighborBlockChange";
            String worldClass = RTLoadingPlugin.isObf ? "ahb" : "net/minecraft/world/World";
            String blockClass = RTLoadingPlugin.isObf ? "aji" : "net/minecraft/block/Block";

            MethodVisitor mv = writer.visitMethod(
                    ACC_PUBLIC, methodName, "(L" + worldClass + ";IIIL" + blockClass + ";)V", null, null);
            mv.visitCode();

            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(81, l0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "lumien/randomthings/Handler/CoreHandler",
                    "handleLeaveDecay",
                    "(L" + worldClass + ";IIIL" + blockClass + ";)V",
                    false);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicClass;
    }
}
