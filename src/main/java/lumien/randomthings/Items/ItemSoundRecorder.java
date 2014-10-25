package lumien.randomthings.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSoundRecorder extends ItemBase
{
	IIcon[] icons;

	public ItemSoundRecorder()
	{
		super("soundRecorder");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		icons = new IIcon[2];
		icons[0] = par1IconRegister.registerIcon("RandomThings:soundRecorderInactive");
		icons[1] = par1IconRegister.registerIcon("RandomThings:soundRecorderActive");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if (damage >= 0 && damage < icons.length)
		{
			return icons[damage];
		}
		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.isSneaking())
		{
			if (par1ItemStack.getItemDamage() == 0)
			{
				par1ItemStack.setItemDamage(1);
			}
			else
			{
				par1ItemStack.setItemDamage(0);
			}
		}
		else
		{
			par3EntityPlayer.openGui(RandomThings.instance, GuiIds.SOUND_RECORDER, par2World, 0, 0, 0);
		}
		return par1ItemStack;
	}
}
