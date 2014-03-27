package lumien.randomthings.Items;

import java.awt.Color;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.ClientTickHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemCreativeSword extends ItemCreative
{
	int DAMAGE = 100000;

	public ItemCreativeSword()
	{
		this.setUnlocalizedName("creativeSword");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setMaxStackSize(1);
		this.setTextureName("RandomThings:creativeSword");

		GameRegistry.registerItem(this, "creativeSword");
	}

	public boolean isFull3D()
	{
		return true;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLiving)
		{
			((EntityLiving) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), DAMAGE);
		}
		return false;
	}
}
