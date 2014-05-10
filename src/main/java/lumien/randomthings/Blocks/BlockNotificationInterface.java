package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityNotificationInterface;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNotificationInterface extends BlockContainer
{

	public BlockNotificationInterface()
	{
		super(Material.rock);
		
		this.setBlockName("notificationInterface");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockTextureName("RandomThings:notificationInterface");
		
		GameRegistry.registerBlock(this, "notificationInterface");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityNotificationInterface();
	}
}
