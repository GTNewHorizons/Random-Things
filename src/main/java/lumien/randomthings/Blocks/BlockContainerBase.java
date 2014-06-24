package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockContainer
{
	String blockName;

	protected BlockContainerBase(String blockName, Material material)
	{
		super(material);

		this.blockName = blockName;

		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName(blockName);
		this.setBlockTextureName("RandomThings:" + blockName);

		GameRegistry.registerBlock(this, blockName);
	}

	@Override
	public final TileEntity createNewTileEntity(World var1, int var2)
	{
		try
		{
			return (TileEntity) getTileEntityClass().newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	protected abstract <T extends TileEntity> Class getTileEntityClass();
}
