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

	static int[] creativeColors;
	static int[] opColors;

	public ClientTickHandler()
	{
		creativeColors = new int[255];
		opColors = new int[255];
		
		for (int i = 0; i < 255; i++)
		{
			float h = i / 255F;
			creativeColors[i] = new Color(Color.HSBtoRGB(h, 1f, 1f)).getRGB();
		}
		
		for (int i = 0; i < 255; i++)
		{
			float h = i / 255F+0.5f;
			h = Math.min(1, h);
			opColors[i] = new Color(Color.HSBtoRGB(0, h, 1f)).getRGB();
		}
	}
	
	public static int getCurrentOPColor()
	{
		return opColors[counter];
	}
	
	public static int getCurrentCreativeColor()
	{
		return creativeColors[counter];
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		if (event.phase == Phase.END &&  Minecraft.getMinecraft().thePlayer!=null)
		{
			if (counter >= creativeColors.length - 1)
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
