package lumien.randomthings.Entity;

import net.minecraft.entity.EnumCreatureType;
import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntitys
{
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityPfeil.class, "Pfeil", 0, RandomThings.instance, 80, 1, true);
		
		EntityRegistry.registerGlobalEntityID(EntityDyeSlime.class, "dyeSlime", EntityRegistry.findGlobalUniqueEntityId(), 0, 0);
		EntityRegistry.registerModEntity(EntityDyeSlime.class, "dyeSlime", 2, RandomThings.instance, 80, 1, true);
	}
}
