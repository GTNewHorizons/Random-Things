package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockItemCollector extends BlockContainer
{

	protected BlockItemCollector()
	{
		super(Material.rock);
		
		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName("itemCollector");
		
		GameRegistry.registerBlock(this, "itemCollector");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityItemCollector();
	}

}
