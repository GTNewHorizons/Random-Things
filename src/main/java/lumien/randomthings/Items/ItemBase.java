package lumien.randomthings.Items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

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
}
