package lumien.randomthings.Handler.CarpentryBench;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CarpentryManager
{
	private static final CarpentryManager instance = new CarpentryManager();
	
	public static CarpentryManager instance()
	{
		return instance;
	}
	
	public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
    {
		return null;
    }
}
