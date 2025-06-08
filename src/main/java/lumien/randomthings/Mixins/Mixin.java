package lumien.randomthings.Mixins;

import java.util.function.Supplier;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;

/**
 * IMPORTANT: Do not make any references to any mod from this file. This file is loaded quite early on and if you refer
 * to other mods you load them as well. The consequence is: You can't inject any previously loaded classes! Exception:
 * Reference.java, as long as it is used for Strings only!
 */
public enum Mixin {

    BLOCK_LEAVES_BASE("MixinBlockLeavesBase", Side.BOTH, () -> VanillaChanges.FASTER_LEAVEDECAY),
    ENTITYACCESSOR("EntityAccessor", Side.BOTH, () -> true),
    ENTITYLIVING_ACCESSOR("EntityLivingAccessor", Side.BOTH, () -> true),
    ENTITY_RENDERER("MixinEntityRenderer", Side.CLIENT, () -> true),
    GUIVIDEOSETTINGS("MixinGuiVideoSettings", Side.CLIENT, () -> VanillaChanges.LOCKED_GAMMA),
    ITEM("MixinItem", Side.CLIENT, () -> true),
    POTION_ACCESSOR("PotionAccessor", Side.BOTH, () -> true),
    RENDER_GLOBAL("MixinRenderGlobal", Side.CLIENT, () -> true),
    SPAWNERANIMALS("MixinSpawnerAnimals", Side.BOTH, () -> true),
    WORLD("MixinWorld", Side.BOTH, () -> ConfigBlocks.wirelessLever),
    WORLDSERVER_ACCESSOR("WorldServerAccessor", Side.BOTH, () -> true),
    WORLD_ACCESSOR("WorldAccessor", Side.BOTH, () -> true);

    public final String mixinClass;
    private final Side side;
    private final Supplier<Boolean> shouldApply;

    Mixin(String mixinClass, Side side, Supplier<Boolean> shouldApply) {
        this.mixinClass = mixinClass;
        this.side = side;
        this.shouldApply = shouldApply;
    }

    public boolean shouldLoad() {
        return shouldApply.get() && (side == Side.BOTH || (side == Side.SERVER && FMLLaunchHandler.side().isServer())
                || (side == Side.CLIENT && FMLLaunchHandler.side().isClient()));
    }
}

enum Side {
    BOTH,
    CLIENT,
    SERVER
}
