package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityCarpentryBench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCarpentryBench extends BlockContainer
{

	protected BlockCarpentryBench()
	{
		super(Material.wood);

		//this.setCreativeTab(RandomThings.creativeTab); WIP
		this.setBlockName("carpentryBench");

		GameRegistry.registerBlock(this, "carpentryBench");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityCarpentryBench();
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
}
