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
	public static ItemVoidStone voidStone;
	public static ItemDropFilter dropFilter;
	public static ItemEnderLetter enderLetter;
	
	public static ItemCreativeSword creativeSword;
	public static ItemCreativeGrower creativeGrower;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Items");
		
		if (ConfigItems.biomeSolution)
			biomeSolution = new ItemBiomeSolution();
		if (ConfigItems.biomePainter)
			biomePainter = new ItemBiomePainter();
		if (ConfigItems.whitestone)
			whitestone = new ItemWhiteStone();
		if (ConfigItems.magneticForce)
			magneticForce = new ItemMagneticForce();
		if (ConfigItems.voidStone)
			voidStone = new ItemVoidStone();
		if (ConfigItems.dropFilter)
			dropFilter = new ItemDropFilter();
		if (ConfigItems.enderLetter)
			enderLetter = new ItemEnderLetter();
		
		if (ConfigItems.creativeSword)
			creativeSword = new ItemCreativeSword();
		if (ConfigItems.creativeGrower)
			creativeGrower = new ItemCreativeGrower();
		
		ingredients = new ItemIngredient();
		filter = new ItemFilter();
		
		kojaku = new ItemKojaku();
	}
}
