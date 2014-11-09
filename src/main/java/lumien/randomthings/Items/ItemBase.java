package lumien.randomthings.Items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		this.setUnlocalizedName(name);
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:" + name);

		GameRegistry.registerItem(this, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List informationList, boolean extendedInformation)
	{
		super.addInformation(is, player, informationList, extendedInformation);
	}
	
	@Override
	public void onUpdate(ItemStack is, World worldObj, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		super.onUpdate(is, worldObj, entity, p_77663_4_, p_77663_5_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return super.getIconFromDamage(damage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList)
	{
		super.getSubItems(item, creativeTab, itemList);
	}

	public int getDurabilityColor(ItemStack item, double health)
	{
		int k = (int)Math.round(255.0D - health * 255.0D);
		return 255 - k << 16 | k << 8;
	}

	public boolean showDamage(ItemStack item)
	{
		return false;
	}
}
