package lumien.randomthings.Blocks.ItemBlocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.TileEntities.TileEntityWirelessLever;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBlockWirelessLever extends ItemBlock
{

	public ItemBlockWirelessLever(Block b)
	{
		super(b);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.stackTagCompound!=null && par1ItemStack.stackTagCompound.getBoolean("hasTarget"))
		{
			par3List.add("Target X: "+par1ItemStack.stackTagCompound.getInteger("targetX"));
			par3List.add("Target Y: "+par1ItemStack.stackTagCompound.getInteger("targetY"));
			par3List.add("Target Z: "+par1ItemStack.stackTagCompound.getInteger("targetZ"));
		}
		else
		{
			par3List.add("No target selected (Shift click on a block)");
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int posX, int posY, int posZ, int par7, float par8, float par9, float par10)
	{
		System.out.println(par3World.isRemote);
		if (par2EntityPlayer.isSneaking() && !par3World.isRemote)
		{
			System.out.println("Sneak");
			if (par1ItemStack.stackTagCompound == null)
			{
				par1ItemStack.stackTagCompound = new NBTTagCompound();
				par1ItemStack.stackTagCompound.setBoolean("hasTarget", false);
			}
			par1ItemStack.stackTagCompound.setBoolean("hasTarget", true);
			par1ItemStack.stackTagCompound.setInteger("targetX", posX);
			par1ItemStack.stackTagCompound.setInteger("targetY", posY);
			par1ItemStack.stackTagCompound.setInteger("targetZ", posZ);
		}
		return false;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int posX, int posY, int posZ, int par7, float par8, float par9, float par10)
	{
		if (!par2EntityPlayer.isSneaking() && par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getBoolean("hasTarget"))
		{
			return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, posX, posY, posZ, par7, par8, par9, par10);
		}
		else
		{
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if (stack.stackTagCompound == null || !stack.stackTagCompound.getBoolean("hasTarget"))
		{
			return false;
		}
		else
		{
			boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
			if (placed)
			{
				TileEntityWirelessLever te = (TileEntityWirelessLever) world.getTileEntity(x, y, z);
				te.setTarget(stack.stackTagCompound.getInteger("targetX"), stack.stackTagCompound.getInteger("targetY"), stack.stackTagCompound.getInteger("targetZ"));
			}
			return placed;
		}
	}
}
