package lumien.randomthings.Items;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.Optional;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.Settings;
import morph.api.Api;
import net.minecraft.client.renderer.texture.IIconRegister;
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
	IIcon[] icons;

	public ItemDemorpher()
	{
		super("demorpher");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setCreativeTab(null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(is, par2EntityPlayer, par3List, par4);
		if (is.stackTagCompound != null && !is.stackTagCompound.getString("boundPlayers").equals(":"))
		{
			String[] boundPlayers = is.stackTagCompound.getString("boundPlayers").split(":");
			for (String player : boundPlayers)
			{
				if (!player.equals(""))
				{
					par3List.add(player);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return icons[damage];
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[2];
		icons[0] = ir.registerIcon("RandomThings:demorpher");
		icons[1] = ir.registerIcon("RandomThings:demorpher_active");
	}

	@Override
	@Optional.Method(modid = "Morph")
	public ItemStack onItemRightClick(ItemStack is, World worldObj, EntityPlayer player)
	{
		if (Boolean.TRUE)
		{
			return is;
		}
		if (!worldObj.isRemote)
		{
			if (is.stackTagCompound == null)
			{
				is.stackTagCompound = new NBTTagCompound();
			}
			String boundPlayers = "";
			List<EntityPlayer> playersInRadius = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(player.posX - Settings.DEMORPHER_RANGE, player.posY - Settings.DEMORPHER_RANGE, player.posZ - Settings.DEMORPHER_RANGE, player.posX + Settings.DEMORPHER_RANGE, player.posY + Settings.DEMORPHER_RANGE, player.posZ + Settings.DEMORPHER_RANGE));
			for (EntityPlayer foundPlayer : playersInRadius)
			{
				if (Api.hasMorph(foundPlayer.getCommandSenderName(), false))
				{
					Api.forceDemorph((EntityPlayerMP) foundPlayer);
					boundPlayers += foundPlayer.getCommandSenderName() + ":";
				}
			}
			if (!boundPlayers.equals(""))
			{
				is.setItemDamage(1);
				is.stackTagCompound.setString("boundPlayers", boundPlayers);
			}
		}
		return is;
	}
}
