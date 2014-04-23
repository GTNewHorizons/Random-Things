package lumien.randomthings.Library;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import lumien.randomthings.RandomThings;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils
{
	public static boolean areOreDictionaried(ItemStack item1,ItemStack item2)
	{
		ArrayList<Integer> ids1 = getOreIDs(item1);
		ArrayList<Integer> ids2 = getOreIDs(item2);
		
		if (ids1==null || ids2==null)
		{
			return false;
		}
		
		for (Integer id1:ids1)
		{
			for (Integer id2:ids2)
			{
				if (id1.equals(id2))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private static ArrayList<Integer> getOreIDs(ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return null;
        }
        
        HashMap<Integer, ArrayList<ItemStack>> oreStacks;
		try
		{
			Field f = OreDictionary.class.getDeclaredField("oreStacks");
			f.setAccessible(true);
			oreStacks = (HashMap<Integer, ArrayList<ItemStack>>) f.get(null);
		}
		catch (Exception e)
		{
			RandomThings.instance.logger.log(Level.WARN, "Couldn't reflect on OreDictionary (oreStacks)");
			e.printStackTrace();
			return null;
		}
		
        ArrayList<Integer> oreIDs = new ArrayList<Integer>();
        
        for(Entry<Integer, ArrayList<ItemStack>> ore : oreStacks.entrySet())
        {
            for(ItemStack target : ore.getValue())
            {
                if(itemStack.getItem() == target.getItem() && (target.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack.getItemDamage() == target.getItemDamage()))
                {
                    oreIDs.add(ore.getKey());
                }
            }
        }
        return oreIDs; // didn't find it.
    }
}
