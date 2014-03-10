package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.Settings;
import net.minecraft.block.Block;

public class ModBlocks
{
	public static BlockPlayerInterface playerInterface;
	public static BlockFluidDisplay fluidDisplay;
	public static BlockFertilizedDirt fertilizedDirt, fertilizedDirtTilled;
	public static BlockItemCollector itemCollector;
	public static BlockAdvancedItemCollector advancedItemCollector;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Blocks");

		if (ConfigBlocks.playerInterface)
			playerInterface = new BlockPlayerInterface();
		if (ConfigBlocks.fluidDisplay)
			fluidDisplay = new BlockFluidDisplay();
		if (ConfigBlocks.fertilizedDirt)
			fertilizedDirt = new BlockFertilizedDirt(false);
		if (ConfigBlocks.fertilizedDirtTilled)
			fertilizedDirtTilled = new BlockFertilizedDirt(true);
		if (ConfigBlocks.itemCollector)
		{
			itemCollector = new BlockItemCollector();
			advancedItemCollector = new BlockAdvancedItemCollector();
		}
	}
}
