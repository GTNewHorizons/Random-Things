package lumien.randomthings.Entity;

import net.minecraft.entity.EnumCreatureType;
import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntitys
{
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityPfeil.class, "Pfeil", 0, RandomThings.instance, 80, 1, true);
		EntityRegistry.registerModEntity(EntityHealingOrb.class, "HealingOrb", 1, RandomThings.instance, 80, 1, true);
		
		EntityRegistry.registerGlobalEntityID(EntitySpirit.class, "spirit", EntityRegistry.findGlobalUniqueEntityId(), 2631720, 10024447);
		EntityRegistry.registerModEntity(EntitySpirit.class, "spirit", 3, RandomThings.instance, 80, 1, true);
	}
}
