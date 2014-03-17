package lumien.randomthings.Entity;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntitys
{
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityPfeil.class, "Pfeil", 0, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerModEntity(EntityWhitestone.class, "Whitestone", 1, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityDyeSlime.class, "dyeSlime", EntityRegistry.findGlobalUniqueEntityId(), 0, 0);
	}
}
