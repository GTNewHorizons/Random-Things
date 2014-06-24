package lumien.randomthings.Library;

import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Colors
{
	public final static String BLACK = "\247" + "0";
	public final static String DARK_BLUE = "\247" + "1";
	public final static String DARK_GREEN = "\247" + "2";
	public final static String DARK_AQUA = "\247" + "3";
	public final static String DARK_RED = "\247" + "4";
	public final static String DARK_PURPLE = "\247" + "5";
	public final static String GOLD = "\247" + "6";
	public final static String GRAY = "\247" + "7";
	public final static String DARK_GRAY = "\247" + "8";
	public final static String BLUE = "\247" + "9";

	public final static String GREEN = "\247" + "a";
	public final static String AQUA = "\247" + "b";
	public final static String RED = "\247" + "c";
	public final static String LIGHT_PURPLE = "\247" + "d";
	public final static String YELLOW = "\247" + "e";
	public final static String WHITE = "\247" + "f";

	public static final String[] oreDictDyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };


	public static int getDyeColor(ItemStack is)
	{
		int[] oreDictIds = OreDictionary.getOreIDs(is);
		for (int i : oreDictIds)
		{
			for (int dyeO = 0; dyeO < Colors.oreDictDyes.length; dyeO++)
			{
				String s = Colors.oreDictDyes[dyeO];
				int dyeID = OreDictionary.getOreID(s);
				if (dyeID == i)
				{
					return ItemDye.field_150922_c[dyeO];
				}
			}
		}
		
		return 0;
	}
}
