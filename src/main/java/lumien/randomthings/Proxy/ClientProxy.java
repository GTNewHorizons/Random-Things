package lumien.randomthings.Proxy;

import lumien.randomthings.Client.Renderer.ItemCollectorRenderer;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	ItemCollectorRenderer renderer;

	public void registerRenderers()
	{
		renderer = new ItemCollectorRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemCollector.class, renderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvancedItemCollector.class, renderer);
	}
}
