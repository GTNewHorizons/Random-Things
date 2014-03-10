package lumien.randomthings.Items;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Configuration.Settings;

public class ModItems
{
	public static ItemFilter filter;
	public static ItemBiomeSolution biomeSolution;
	public static ItemBiomePainter biomePainter;
	public static ItemIngredient ingredients;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Items");

		if (ConfigItems.filter)
			filter = new ItemFilter();
		if (ConfigItems.biomeSolution)
			biomeSolution = new ItemBiomeSolution();
		if (ConfigItems.biomePainter)
			biomePainter = new ItemBiomePainter();
		
		ingredients = new ItemIngredient();
	}
}
