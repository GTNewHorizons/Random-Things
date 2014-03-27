package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityLiquidRouter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockLiquidRouter extends BlockContainer
{
	protected BlockLiquidRouter()
	{
		super(Material.rock);
		
		this.setBlockName("liquidRouter");
		this.setCreativeTab(RandomThings.creativeTab);
		
		GameRegistry.registerBlock(this, "liquidRouter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldObj, int metadata)
	{
		return new TileEntityLiquidRouter();
	}
	
	@Override
	public boolean onBlockActivated(World worldObj, int poxX, int poxY, int poxZ, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!worldObj.isRemote)
		{
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.LIQUID_ROUTER, worldObj, poxX, poxY, poxZ);
		}
		return true;
	}

}
