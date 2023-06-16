package lumien.randomthings.Entity;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;
import lumien.randomthings.Handler.ModCompability.MFR.MFRRegistry;
import lumien.randomthings.RandomThings;

public class ModEntitys {

    public static void init() {
        EntityRegistry.registerModEntity(EntityHealingOrb.class, "HealingOrb", 1, RandomThings.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntitySoul.class, "playerSoul", 2, RandomThings.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntitySpirit.class, "spirit", 3, RandomThings.instance, 80, 1, true);
        EntityRegistry
                .registerModEntity(EntityReviveCircle.class, "reviveCircle", 4, RandomThings.instance, 80, 1, true);
        EntityRegistry.registerModEntity(
                EntityBloodmoonCircle.class,
                "bloodmoonCircle",
                5,
                RandomThings.instance,
                80,
                1,
                true);

        if (Loader.isModLoaded("MineFactoryReloaded")) {
            MFRRegistry.registerSafariNetBlacklist(EntitySpirit.class);
            MFRRegistry.registerSafariNetBlacklist(EntitySoul.class);
            MFRRegistry.registerSafariNetBlacklist(EntityReviveCircle.class);
            MFRRegistry.registerSafariNetBlacklist(EntityHealingOrb.class);
        }
    }
}
