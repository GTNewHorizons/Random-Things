package lumien.randomthings.Handler.CarpentryBench;

import lumien.randomthings.Items.ItemCarpentryPattern;
import lumien.randomthings.Library.InventoryUtils;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CarpentryManager
{
	private static final CarpentryManager instance = new CarpentryManager();

	public static CarpentryManager instance()
	{
		return instance;
	}

	public ItemStack findMatchingRecipe(ItemStack pattern, InventoryCrafting inventoryCrafting, World par2World)
	{
		if (pattern == null)
		{
			return null;
		}
		else
		{
			switch (ItemCarpentryPattern.PatternType.values()[pattern.getItemDamage()])
			{
				case CHEST:
					break;
				case WORKBENCH:
					int[] emptyRange = new int[] { 2, 5, 6, 7, 8 };
					for (int slot : emptyRange)
					{
						if (inventoryCrafting.getStackInSlot(slot) != null)
						{
							return null;
						}
					}
					int[] blockRange = new int[] { 0, 1, 3, 4 };
					ItemStack first = inventoryCrafting.getStackInSlot(0);

					for (int slot : blockRange)
					{
						ItemStack item = inventoryCrafting.getStackInSlot(slot);
						if (item == null || !(item.getItem() instanceof ItemBlock) || !InventoryUtils.areItemStackContentEqual(first, inventoryCrafting.getStackInSlot(slot)))
						{
							return null;
						}
					}

					return new ItemStack(Blocks.chest);
				default:
					break;
			}
		}
		return null;
	}
}
