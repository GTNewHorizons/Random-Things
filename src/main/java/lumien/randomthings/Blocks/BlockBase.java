package lumien.randomthings.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;

public class BlockBase extends Block {

    String blockName;

    protected BlockBase(String blockName, Material material) {
        super(material);
        this.blockName = blockName;

        this.setCreativeTab(RandomThings.creativeTab);
        this.setBlockName(blockName);
        this.setBlockTextureName("RandomThings:" + blockName);

        GameRegistry.registerBlock(this, blockName);
    }

    protected BlockBase(String blockName, Material material, Class itemBlock) {
        super(material);
        this.blockName = blockName;

        this.setCreativeTab(RandomThings.creativeTab);
        this.setBlockName(blockName);
        this.setBlockTextureName("RandomThings:" + blockName);

        GameRegistry.registerBlock(this, itemBlock, blockName);
    }
}
