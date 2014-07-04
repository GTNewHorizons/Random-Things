package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityCarpentryBench;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCarpentryBench extends BlockContainerBase
{

	protected BlockCarpentryBench()
	{
		super("carpentryBench",Material.wood);
		
		this.setCreativeTab(null);
		this.setHardness(3);
	}

	@Override
	public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (worldObj.isRemote)
		{
			return true;
		}
		else
		{
			player.openGui(RandomThings.instance, GuiIds.CARPENTRY_BENCH, worldObj, posX, posY, posZ);
			return true;
		}
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityCarpentryBench.class;
	}
}
