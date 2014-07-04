package lumien.randomthings.Blocks;

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
	public static BlockMoonSensor moonSensor;
	public static BlockNotificationInterface notificationInterface;
	public static BlockSpectreBlock spectreBlock;
	public static BlockLapisLamp spectreLamp;
	public static BlockWirelessLever wirelessLever;
	public static BlockCarpentryBench carpentryBench;
	public static BlockDyeingMachine dyeingMachine;
	public static BlockSpiritRod spiritRod;

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
		if (ConfigBlocks.moonSensor)
			moonSensor = new BlockMoonSensor();
		if (ConfigBlocks.notificationInterface)
			notificationInterface = new BlockNotificationInterface();
		if (ConfigBlocks.lapisLamp)
			spectreLamp = new BlockLapisLamp();
		if (ConfigBlocks.wirelessLever)
			wirelessLever = new BlockWirelessLever();
		if (ConfigBlocks.carpentryBench)
			carpentryBench = new BlockCarpentryBench();
		if (ConfigBlocks.dyeingMachine)
			dyeingMachine = new BlockDyeingMachine();
		if (ConfigBlocks.spiritRod)
			spiritRod = new BlockSpiritRod();
		
		spectreBlock = new BlockSpectreBlock();
	}
}
