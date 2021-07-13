package com.bambam01.DestroyThePluginCache;

import org.objectweb.asm.Opcodes;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;


import java.util.Arrays;

public class DestroyerClassTransformer implements IClassTransformer {

    private static final String[] classesBeingTransformed =
            {
                    "speiger.src.crops.prediction.NEIPlugin"
            };

    private static final int[] opCodeCheckList = new int[]{184,180,18,182};

    private static final boolean isBadMethod = false;

    @Override
    public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
        boolean isObfuscated = !name.equals(transformedName);
        int index = Arrays.asList(classesBeingTransformed).indexOf(transformedName);
        return index != -1 ? transform(index, classBeingTransformed, isObfuscated) : classBeingTransformed;
    }

    private static byte[] transform(int index, byte[] classBeingTransformed, boolean isObfuscated) {
        System.out.println("Transforming: " + classesBeingTransformed[index]);
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);

            switch (index) {
                case 0:
                    transformPluginCache(classNode, isObfuscated);
                    break;
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classBeingTransformed;
    }

    private static void transformPluginCache(ClassNode NEIPlugin, boolean isObfuscated) throws Exception {
        final String CraftingRecipes = "loadCraftingRecipes";
        final String CraftingRecipesDesc = "(Lnet/minecraft/item/ItemStack;)V";

        final String UsageRecipes = "loadUsageRecipes";
        final String UsageRecipesDesc = "(Lnet/minecraft/item/ItemStack;)V";

        for (MethodNode method : NEIPlugin.methods) {
            if ((method.name.equals(CraftingRecipes) && method.desc.equals(CraftingRecipesDesc)) || (method.name.equals(UsageRecipes) && method.desc.equals(UsageRecipesDesc))) {
                System.out.println("found method: " + method.name);
                AbstractInsnNode targetNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == Opcodes.INVOKESPECIAL) {
                        AbstractInsnNode nexInstruction = instruction.getNext().getNext().getNext();
                        if (nexInstruction.getOpcode() == Opcodes.INVOKESTATIC) {
                            targetNode = nexInstruction;
                            System.out.println("Found chat print statement");
                            break;
                        }
                    }
                }
                if (targetNode != null) {
                    System.out.println("Removing chat statement");
                    for (int i = 0; i < 4; i++) {
                        if(targetNode.getOpcode() != opCodeCheckList[i]){
                            throw new Exception("Unexpected opcode at index: " + i + ". Expected: " + opCodeCheckList[i] + " But got: " + targetNode.getOpcode());
                        }
                        targetNode = targetNode.getNext();
                        method.instructions.remove(targetNode.getPrevious());
                    }

                    //Wrapping this in a conditional
                }
            }
        }
    }
}
