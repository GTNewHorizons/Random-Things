package lumien.randomthings.Client;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class ClientTickHandler
{
	static int counter = 0;
	static boolean raising = true;

	static int[] colors;

	public ClientTickHandler()
	{
		colors = new int[255];

		for (int i = 0; i < 255; i++)
		{
			float h = i / 255F;
			colors[i] = new Color(Color.HSBtoRGB(h, 1f, 1f)).getRGB();
		}
	}
	
	public static int getCurrentCreativeColor()
	{
		return colors[counter];
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		if (event.phase == Phase.END &&  Minecraft.getMinecraft().thePlayer!=null)
		{
			if (counter >= colors.length - 1)
			{
				raising = false;
			}

			if (counter <= 0)
			{
				raising = true;
			}

			if (raising)
			{
				counter++;
			}
			else
			{
				counter--;
			}

		}
	}
}
