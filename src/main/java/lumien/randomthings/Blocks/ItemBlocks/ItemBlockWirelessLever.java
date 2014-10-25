package lumien.randomthings.Blocks.ItemBlocks;

import java.util.List;

import javax.vecmath.Vector3d;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.TileEntities.TileEntityWirelessLever;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
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

		this.setTextureName("RandomThings:wirelessLever");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getBoolean("hasTarget"))
		{
			par3List.add(String.format(I18n.format("text.miscellaneous.target.x", par1ItemStack.stackTagCompound.getInteger("targetX"))));
			par3List.add(String.format(I18n.format("text.miscellaneous.target.y", par1ItemStack.stackTagCompound.getInteger("targetY"))));
			par3List.add(String.format(I18n.format("text.miscellaneous.target.z", par1ItemStack.stackTagCompound.getInteger("targetZ"))));
		}
		else
		{
			par3List.add(I18n.format("text.miscellaneous.noTarget"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdate(ItemStack par1ItemStack, World worldObj, Entity par3Entity, int par4, boolean par5)
	{
		if (worldObj.isRemote && worldObj.getTotalWorldTime() % 2 == 0 && par3Entity instanceof EntityPlayer)
		{
			EntityPlayerSP player = (EntityPlayerSP) par3Entity;
			if (player.getCurrentEquippedItem() == par1ItemStack && player.isSneaking())
			{
				if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getBoolean("hasTarget"))
				{
					double targetX = par1ItemStack.stackTagCompound.getDouble("targetX");
					double targetY = par1ItemStack.stackTagCompound.getDouble("targetY");
					double targetZ = par1ItemStack.stackTagCompound.getDouble("targetZ");

					double xCoord = player.posX;
					double yCoord = player.posY;
					double zCoord = player.posZ;

					Vector3d vec = new Vector3d(targetX - xCoord, targetY - yCoord, targetZ - zCoord);
					for (double d = 0; d <= 1; d += 0.02d)
					{
						EntityReddustFX particle = new EntityReddustFX(worldObj, xCoord + 0.5 + vec.x * d, yCoord + 0.5 + vec.y * d, zCoord + 0.5 + vec.z * d, 0, 0, 0);
						Minecraft.getMinecraft().effectRenderer.addEffect(particle);
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_)
	{
		if (p_150936_6_.isSneaking())
		{
			return true;
		}
		else
		{
			return super.func_150936_a(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_, p_150936_5_, p_150936_6_, p_150936_7_);
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int posX, int posY, int posZ, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
		{
			if (!world.isRemote)
			{
				if (stack.stackTagCompound == null)
				{
					stack.stackTagCompound = new NBTTagCompound();
					stack.stackTagCompound.setBoolean("hasTarget", false);
				}
				stack.stackTagCompound.setBoolean("hasTarget", true);
				stack.stackTagCompound.setInteger("targetX", posX);
				stack.stackTagCompound.setInteger("targetY", posY);
				stack.stackTagCompound.setInteger("targetZ", posZ);
				return true;
			}
			return false;
		}
		else
		{
			return super.onItemUseFirst(stack, player, world, posX, posY, posZ, side, hitX, hitY, hitZ);
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if (player.isSneaking())
		{
			return false;
		}
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
