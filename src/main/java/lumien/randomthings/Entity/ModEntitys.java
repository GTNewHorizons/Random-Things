package lumien.randomthings.Entity;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntitys
{
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityHealingOrb.class, "HealingOrb", 1, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerModEntity(EntitySoul.class, "playerSoul", 2, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerModEntity(EntitySpirit.class, "spirit", 3, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerModEntity(EntityReviveCircle.class, "reviveCircle", 4, RandomThings.instance, 80, 1, true);
	}
}
