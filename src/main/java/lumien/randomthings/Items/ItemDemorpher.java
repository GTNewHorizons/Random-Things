package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import lumien.randomthings.RandomThings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDemorpher extends ItemBase
{
	public ItemDemorpher()
	{
		super("demorpher");
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		par3List.add("I will become functional when Morph updates to 1.7.10");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World worldObj, EntityPlayer player)
	{
		if (Boolean.TRUE)
		{
			return is; // Will be functional as soon as Morph is updated to
						// 1.7.10
		}
		if (!worldObj.isRemote)
		{
			if (is.stackTagCompound == null)
			{
				is.stackTagCompound = new NBTTagCompound();
			}
			List<EntityPlayer> playersInRadius = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(player.posX - 10, player.posY - 10, player.posZ - 10, player.posX + 10, player.posY + 10, player.posZ + 10));
			for (EntityPlayer foundPlayer : playersInRadius)
			{
				if (!(foundPlayer == player))
				{
					try
					{
						Class.forName("morph.api.Api").getMethod("forceDemorph", EntityPlayerMP.class).invoke(null, foundPlayer);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						RandomThings.instance.logger.warn("Couldn't reflect on Morph API, the demorpher will not work");
					}
				}
			}
		}
		return is;
	}
}
