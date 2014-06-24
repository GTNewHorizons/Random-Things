package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityDyeingMachine;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDyeingMachine extends BlockContainerBase
{

	public BlockDyeingMachine()
	{
		super("dyeingMachine", Material.rock);
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityDyeingMachine.class;
	}

	@Override
	public boolean onBlockActivated(World worldObj, int poxX, int poxY, int poxZ, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!worldObj.isRemote)
		{
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.DYEING_MACHINE, worldObj, poxX, poxY, poxZ);
		}
		return true;
	}
}
