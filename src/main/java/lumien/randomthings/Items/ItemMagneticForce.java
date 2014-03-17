package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.item.Item;

public class ItemMagneticForce extends Item
{
	public ItemMagneticForce()
	{
		this.setUnlocalizedName("magneticForce");
		this.setCreativeTab(RandomThings.creativeTab);
		
		GameRegistry.registerItem(this, "magneticForce");
	}
}
