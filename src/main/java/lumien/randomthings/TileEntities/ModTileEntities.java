package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityPlayerInterface.class, "playerInterface");
		GameRegistry.registerTileEntity(TileEntityFluidDisplay.class, "fluidDisplay");
		
		GameRegistry.registerTileEntity(TileEntityItemCollector.class, "itemcollector");
		GameRegistry.registerTileEntity(TileEntityAdvancedItemCollector.class, "advancedItemcollector");
	}
}
