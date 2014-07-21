package lumien.randomthings.Handler;

import java.lang.reflect.Field;
import java.util.Random;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.OverrideUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemDye;
import net.minecraft.util.ResourceLocation;

public class BackgroundHandler
{
	static Field f;
	static String[] validBlocks = new String[] {"netherrack", "wool_colored", "stonebrick", "stonebrick_mossy", "sponge", "stone", "red_sand", "sand", "bedrock", "brick", "clay", "cobblestone", "cobblestone_mossy", "dirt", "end_stone", "glowstone", "gravel", "hardened_clay", "hay_block_top", "ice", "log", "melon_side", "mycelium_top", "nether_brick", "portal" };

	static Random rng = new Random();

	static String[] logTypes = new String[] { "acacia", "big_oak", "birch", "jungle", "oak", "spruce" };

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
		if (!Settings.FIXED_BACKGROUND.equals(""))
		{
			setBackgroundBlock(Settings.FIXED_BACKGROUND);
		}
		else
		{
			String randomBlock = validBlocks[rng.nextInt(validBlocks.length)];

			if (randomBlock.equals("hardened_clay"))
			{
				randomBlock = randomBlock + "_stained_" + ItemDye.field_150921_b[rng.nextInt(ItemDye.field_150921_b.length)];
			}
			else if (randomBlock.equals("wool_colored"))
			{
				randomBlock = randomBlock + "_" + ItemDye.field_150921_b[rng.nextInt(ItemDye.field_150921_b.length)];
			}
			else if (randomBlock.equals("log") || randomBlock.equals("planks"))
			{
				randomBlock = randomBlock + "_" + logTypes[rng.nextInt(logTypes.length)];
			}

			setBackgroundBlock(randomBlock);
		}
	}
}
