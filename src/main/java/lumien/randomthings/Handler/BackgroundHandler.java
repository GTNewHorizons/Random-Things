package lumien.randomthings.Handler;

import java.lang.reflect.Field;
import java.util.Random;

import lumien.randomthings.Library.OverrideUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class BackgroundHandler
{
	static Field f;
	static String[] validBlocks = new String[] { "bedrock", "bookshelf", "brick", "clay", "cobblestone", "cobblestone_mossy", "dirt", "end_stone", "farmland_dry", "glowstone", "gravel", "hay_block_top", "ice",  "melon_side", "mushroom_block_inside", "mycelium_top", "nether_brick", "netherrack", "obsidian", "sand", "sandstone_top", "soul_sand", "snow", "stonebrick", "stonebrick_mossy", "red_sand", "planks_big_oak", "planks_acacia", "log_acacia", "ice_packed" };
	static Random rng = new Random();

	public static void setBackgroundBlock(String block)
	{
		try
		{
			for (Field f : Gui.class.getFields())
			{
				if (f.getType() == ResourceLocation.class)
				{
					ResourceLocation r = (ResourceLocation) f.get(null);
					if (r.getResourcePath().equals("textures/gui/options_background.png"))
					{
						OverrideUtils.setFinalStatic(f, new ResourceLocation("textures/blocks/" + block + ".png"));
						return;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void setRandomBackground()
	{
		try
		{
			String newLocation = validBlocks[rng.nextInt(validBlocks.length)];
			setBackgroundBlock(newLocation);
		}
		catch (Exception e)
		{
			System.out.println("Couldn't set Background");
		}
	}
}
