package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Blocks.ItemBlocks.ItemBlockWirelessLever;
import lumien.randomthings.Library.RenderIds;
import lumien.randomthings.TileEntities.TileEntityWirelessLever;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWirelessLever extends BlockLever implements ITileEntityProvider
{
	public BlockWirelessLever()
	{
		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName("wirelessLever");
		this.isBlockContainer = true;
		
		GameRegistry.registerBlock(this,ItemBlockWirelessLever.class, "wirelessLever");
	}
	
	@Override
    public int getRenderType()
    {
        return RenderIds.WIRELESS_LEVER;
    }
	
	@Override
	public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		boolean activate = super.onBlockActivated(worldObj, posX, posY, posZ, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
		
		TileEntityWirelessLever te = (TileEntityWirelessLever) worldObj.getTileEntity(posX,posY,posZ);
		te.updateTarget();
		
		return activate;
    }
	
	@Override
	public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
    {
        return 0;
    }

	@Override
    public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
    {
        return 0;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityWirelessLever();
	}
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
}
