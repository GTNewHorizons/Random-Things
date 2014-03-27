package lumien.randomthings.Blocks;

import net.minecraft.block.Block;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigBlocks;

public class ModBlocks
{
	public static BlockPlayerInterface playerInterface;
	public static BlockCreativePlayerInterface creativePlayerInterface;
	public static BlockFluidDisplay fluidDisplay;
	public static BlockFertilizedDirt fertilizedDirt, fertilizedDirtTilled;
	public static BlockItemCollector itemCollector;
	public static BlockAdvancedItemCollector advancedItemCollector;
	public static BlockOnlineDetector onlineDetector;
	public static BlockImbuingStation imbuingStation;
	public static BlockLiquidRouter liquidRouter;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Blocks");

		if (ConfigBlocks.playerInterface)
		{
			playerInterface = new BlockPlayerInterface();
			creativePlayerInterface = new BlockCreativePlayerInterface();
		}
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
		if (ConfigBlocks.onlineDetector)
			onlineDetector = new BlockOnlineDetector();
		if (ConfigBlocks.imbuingStation)
			imbuingStation = new BlockImbuingStation();
		if (ConfigBlocks.liquidRouter)
			liquidRouter = new BlockLiquidRouter();
	}
}
