package lumien.randomthings.Library;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils
{
	public static boolean areOreDictionaried(ItemStack item1,ItemStack item2)
	{
		int oreID1 = OreDictionary.getOreID(item1);
		int oreID2 = OreDictionary.getOreID(item2);
		
		if (oreID1==-1 || oreID2==-1)
		{
			return false;
		}
		
		return oreID1==oreID2;
	}
}
