package lumien.randomthings.Items;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigItems;

public class ModItems
{
	public static ItemFilter filter;
	public static ItemBiomeSolution biomeSolution;
	public static ItemBiomePainter biomePainter;
	public static ItemIngredient ingredients;
	public static ItemKojaku kojaku;
	public static ItemWhiteStone whitestone;
	public static ItemMagneticForce magneticForce;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Items");

		if (ConfigItems.filter)
			filter = new ItemFilter();
		if (ConfigItems.biomeSolution)
			biomeSolution = new ItemBiomeSolution();
		if (ConfigItems.biomePainter)
			biomePainter = new ItemBiomePainter();
		if (ConfigItems.whitestone)
			whitestone = new ItemWhiteStone();
		if (ConfigItems.magneticForce)
			magneticForce = new ItemMagneticForce();
		
		ingredients = new ItemIngredient();
		kojaku = new ItemKojaku();
	}
}
