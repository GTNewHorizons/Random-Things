package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityPlayerInterface.class, "playerInterface");
		GameRegistry.registerTileEntity(TileEntityCreativePlayerInterface.class, "creativePlayerInterface");
		GameRegistry.registerTileEntity(TileEntityFluidDisplay.class, "fluidDisplay");
		GameRegistry.registerTileEntity(TileEntityOnlineDetector.class, "onlineDetector");
		GameRegistry.registerTileEntity(TileEntityImbuingStation.class, "imbuingStation");
		GameRegistry.registerTileEntity(TileEntityFluidRouter.class, "liquidRouter");
		
		GameRegistry.registerTileEntity(TileEntityItemCollector.class, "itemcollector");
		GameRegistry.registerTileEntity(TileEntityAdvancedItemCollector.class, "advancedItemcollector");
	}
}
