package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityNotificationInterface;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockNotificationInterface extends BlockContainerBase
{

	public BlockNotificationInterface()
	{
		super("notificationInterface",Material.rock);
		
		this.setCreativeTab(RandomThings.creativeTab);
		this.setHardness(0.7f);
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityNotificationInterface.class;
	}
}
