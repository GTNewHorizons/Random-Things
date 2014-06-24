package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
	String blockName;
	protected BlockBase(String blockName,Material material)
	{
		super(material);
		this.blockName = blockName;
		
		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName(blockName);
		this.setBlockTextureName("RandomThings:"+blockName);
		
		GameRegistry.registerBlock(this, blockName);
	}

}
