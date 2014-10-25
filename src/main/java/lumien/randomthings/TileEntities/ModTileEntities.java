package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityPlayerInterface.class, "playerInterface");
		GameRegistry.registerTileEntity(TileEntityCreativePlayerInterface.class, "creativePlayerInterface");
		GameRegistry.registerTileEntity(TileEntityFluidDisplay.class, "fluidDisplay");
		GameRegistry.registerTileEntity(TileEntityAdvancedFluidDisplay.class, "advancedFluidDisplay");
		GameRegistry.registerTileEntity(TileEntityOnlineDetector.class, "onlineDetector");
		GameRegistry.registerTileEntity(TileEntityNotificationInterface.class, "notificationInterface");
		GameRegistry.registerTileEntity(TileEntityWirelessLever.class, "wirelessLever");
		GameRegistry.registerTileEntity(TileEntityDyeingMachine.class, "dyeingMachine");
		GameRegistry.registerTileEntity(TileEntitySpiritRod.class, "spiritRod");
		GameRegistry.registerTileEntity(TileEntityFogGenerator.class, "fogGenerator");
		GameRegistry.registerTileEntity(TileEntityImbuingStation.class, "imbuingStation");
		GameRegistry.registerTileEntity(TileEntityCustomWorkbench.class, "customWorkbench");

		GameRegistry.registerTileEntity(TileEntityItemCollector.class, "itemcollector");
		GameRegistry.registerTileEntity(TileEntityAdvancedItemCollector.class, "advancedItemcollector");
	}
}
