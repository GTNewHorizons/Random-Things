package lumien.randomthings.Potions;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Level;

import scala.actors.threadpool.Arrays;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Library.OverrideUtils;
import lumien.randomthings.Library.PotionIds;
import lumien.randomthings.Transformer.MCPNames;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property;

public class ModPotions
{
	public static Potion imbuePoison;
	public static Potion imbueExperience;
	public static Potion imbueFire;
	public static Potion imbueWither;
	public static Potion imbueWeakness;
	public static Potion imbueSpectre;

	public static int[] imbueColors = new int[] { 10145074, 14214234, 16729344, 3881787, 16777215, 12578815 };

	public static void init()
	{
		PotionIds.IMBUE_POISON = checkPotionID(RTConfiguration.piImbuePoison).getInt();
		imbuePoison = getImbue("poison", PotionIds.IMBUE_POISON, imbueColors[0]);

		PotionIds.IMBUE_EXPERIENCE = checkPotionID(RTConfiguration.piImbueExperience).getInt();
		imbueExperience = getImbue("experience", PotionIds.IMBUE_EXPERIENCE, imbueColors[1]);

		PotionIds.IMBUE_FIRE = checkPotionID(RTConfiguration.piImbueFire).getInt();
		imbueFire = getImbue("fire", PotionIds.IMBUE_FIRE, imbueColors[2]);

		PotionIds.IMBUE_WITHER = checkPotionID(RTConfiguration.piImbueWither).getInt();
		imbueWither = getImbue("wither", PotionIds.IMBUE_WITHER, imbueColors[3]);

		PotionIds.IMBUE_WEAKNESS = checkPotionID(RTConfiguration.piImbueWeakness).getInt();
		imbueWeakness = getImbue("weakness", PotionIds.IMBUE_WEAKNESS, imbueColors[4]);

		PotionIds.IMBUE_SPECTRE = checkPotionID(RTConfiguration.piImbueSpectre).getInt();
		imbueSpectre = getImbue("spectre", PotionIds.IMBUE_SPECTRE, imbueColors[5]);
	}

	private static PotionImbue getImbue(String name, int id, int color)
	{
		return new PotionImbue("imbue." + name, id, color, new ResourceLocation("randomthings:textures/gui/imbueEffects/" + name + ".png"));
	}

	public static Property checkPotionID(Property p)
	{
		int id = p.getInt();
		int freeID = getFreePotionID();
		if (id == -1)
		{
			RandomThings.instance.logger.log(Level.INFO, "Auto Resolved " + p.getName() + " ID to " + freeID);
			p.set(freeID);
			RTConfiguration.syncConfig();
			return p;
		}

		if (Potion.potionTypes.length - 1 < id)
		{
			resizePotionArray(id + 1);
		}

		if (Potion.potionTypes[id] != null)
		{
			RandomThings.instance.logger.log(Level.INFO, "PotionID " + id + " is already occupied by " + Potion.potionTypes[id].getName());
			p.set(freeID);
			RTConfiguration.syncConfig();
			RandomThings.instance.logger.log(Level.INFO, "Moved Potion from " + id + " to " + freeID);
		}
		else
		{
			return p;
		}

		return p;
	}

	public static void resizePotionArray(int newSize)
	{
		try
		{
			OverrideUtils.setFinalStatic(Potion.class.getDeclaredField(MCPNames.field("field_76425_a")), Arrays.copyOf(Potion.potionTypes, newSize));
		}
		catch (Exception e)
		{
			RandomThings.instance.logger.log(Level.WARN, "Couldn't extend Potion ID Array for more IDs, potions might not work as expected.");
			e.printStackTrace();
		}
	}

	public static int getFreePotionID()
	{
		for (int i = 1; i < Potion.potionTypes.length; i++)
		{
			if (Potion.potionTypes[i] == null)
			{
				return i;
			}
		}

		int oldLength = Potion.potionTypes.length;
		resizePotionArray(Potion.potionTypes.length + 20);

		return oldLength;
	}
}
