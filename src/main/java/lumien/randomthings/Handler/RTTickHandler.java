package lumien.randomthings.Handler;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RTTickHandler
{
	@SubscribeEvent
	public void tick(TickEvent event)
	{
		switch (event.side)
		{
			case SERVER:
				serverTick(event);
				break;
			case CLIENT:
				clientTick(event);
				break;
			default:
				break;
		}
	}
	
	private void clientTick(TickEvent event)
	{
		RandomThings.instance.notificationHandler.update();
	}
	
	private void serverTick(TickEvent event)
	{
		switch (event.phase)
		{
			case START:
				break;
			case END:
				RandomThings.instance.letterHandler.update();
				break;
		}
	}
}
